package org.gzelante.tbs.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

@Data
@Log4j2
public class ConfigManager  implements Serializable {
    private static final long serialVersionUID = -7604766932017737115L;


    private Parameters parameters;
    private Configuration config;
    public static final String SUFFIX = ".save.game.data.location";
    public static final String CURRENT_PREFIX = "current";
    private FileBasedConfigurationBuilder<FileBasedConfiguration> builder;

    private ConfigManager(){
        parameters = new Parameters();
        this.builder =
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
            this.builder.save();
        }
        catch(org.apache.commons.configuration2.ex.ConfigurationException cex)
        {
            log.error(cex.getMessage(), cex);
        }
    }

    private static class SingletonHelper{
        private static final ConfigManager instance = new ConfigManager();
    }

    public static ConfigManager getInstance(){
        return SingletonHelper.instance;
    }

    public void save() throws ConfigurationException {
        this.builder.save();
    }

    public String getDefaultOSSaveDir() {
        return config.getString(System.getProperty("os.name").toLowerCase().split("\\s")[0] + ConfigManager.SUFFIX);
    }
}
