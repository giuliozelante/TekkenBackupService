package org.gzelante.tbs.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import lombok.Getter;

@Getter
public enum ConfigManager {
    INSTANCE;

    public static final String SUFFIX = ".save.game.data.location";
    public static final String CURRENT_PREFIX = "current";

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    private Parameters parameters;

    private Configuration config;

    private FileBasedConfigurationBuilder<FileBasedConfiguration> builder;

    private ConfigManager() {
        final Logger log = (Logger) LoggerFactory.getLogger(ConfigManager.class);
        parameters = new Parameters();
        this.builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(parameters.properties()
                        .setFileName("config.properties"));
        try {
            config = builder.getConfiguration();

            config.setProperty(ConfigManager.CURRENT_PREFIX + ConfigManager.SUFFIX, StringUtils.isNotBlank(
                    config.getString(ConfigManager.CURRENT_PREFIX + ConfigManager.SUFFIX))
                            ? config.getString(ConfigManager.CURRENT_PREFIX + ConfigManager.SUFFIX)
                            : config.getString(System.getProperty("os.name").toLowerCase()
                                    .split("\\s")[0] + ConfigManager.SUFFIX));
            this.builder.save();
        } catch (org.apache.commons.configuration2.ex.ConfigurationException cex) {
            log.error(cex.getMessage(), cex);
        }
    }

    public void save() throws ConfigurationException {
        this.builder.save();
    }

    public String getDefaultOSSaveDir() {
        return config.getString(System.getProperty("os.name").toLowerCase().split("\\s")[0] + ConfigManager.SUFFIX);
    }
}
