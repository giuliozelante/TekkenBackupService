package org.gzelante.tbs.config;

import lombok.Data;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.lang3.StringUtils;

@Data
public class ConfigManager {
    private Parameters parameters;
    private Configuration config;
    public static final String SUFFIX = ".save.game.data.location";
    public static final String CURRENT_PREFIX = "current";

    public ConfigManager() {
        parameters = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(parameters.properties()
                                .setFileName("config.properties"));
        try
        {
            config = builder.getConfiguration();

            config.setProperty(ConfigManager.CURRENT_PREFIX + ConfigManager.SUFFIX, StringUtils.isNotBlank(
                    config.getString(ConfigManager.CURRENT_PREFIX + ConfigManager.SUFFIX)
            ) ?
                    config.getString(ConfigManager.CURRENT_PREFIX + ConfigManager.SUFFIX) :
                    config.getString(System.getProperty("os.name").toLowerCase()
                            .split("\\s")[0] + ConfigManager.SUFFIX));
            builder.save();
        }
        catch(org.apache.commons.configuration2.ex.ConfigurationException cex)
        {
            // loading of the configuration file failed
        }

    }
}
