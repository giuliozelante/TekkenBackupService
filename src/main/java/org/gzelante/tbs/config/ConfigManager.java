package org.gzelante.tbs.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

@Getter
public enum ConfigManager {
    INSTANCE;

    public static final String SUFFIX = ".save.game.data.location";
    public static final String CURRENT_PREFIX = "current";

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    private final Logger log = LoggerFactory.getLogger(ConfigManager.class);
    private final Parameters parameters;
    private FileBasedConfigurationBuilder<FileBasedConfiguration> builder;

    private Configuration config;

    ConfigManager() {
        parameters = new Parameters();
        this.builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(parameters.properties()
                        .setFileName("config.properties"));
        try {
            config = builder.getConfiguration();
            String currentSuffix = ConfigManager.CURRENT_PREFIX + ConfigManager.SUFFIX;
            String osSuffix = System.getProperty("os.name").toLowerCase().split("\\s")[0] + ConfigManager.SUFFIX;
            String suffixValue = StringUtils.isNotBlank(config.getString(currentSuffix))
                    ? config.getString(currentSuffix)
                    : config.getString(osSuffix);
            config.setProperty(currentSuffix, suffixValue);
            this.builder.save();
        } catch (ConfigurationException cex) {
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
