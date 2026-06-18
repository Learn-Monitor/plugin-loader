package de.igslandstuhl.database.plugins.meta;

import de.igslandstuhl.database.plugins.PluginLoader;
import de.igslandstuhl.database.plugins.config.PluginConfig;
import de.igslandstuhl.database.plugins.config.ShortAnswerSetting;

public class PluginLoaderConfig extends PluginConfig<PluginLoader> {
    public final ShortAnswerSetting pluginDir;
    public PluginLoaderConfig() {
        super(PluginLoader.getInstance(),
            new ShortAnswerSetting("plugin-dir", "Plugin Folder", "The Directory that contains plugin JARs", "plugins")
        );
        pluginDir = (ShortAnswerSetting) getSetting("plugin-dir");
    }
}
