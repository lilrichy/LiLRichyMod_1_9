package lilrichy.lilrichymod;

import lilrichy.lilrichymod.handler.ConfigurationHandler;
import lilrichy.lilrichymod.handler.EventHandler;
import lilrichy.lilrichymod.handler.FuelHandler;
import lilrichy.lilrichymod.handler.GuiHandler;
import lilrichy.lilrichymod.init.ModBlocks;
import lilrichy.lilrichymod.init.ModItems;
import lilrichy.lilrichymod.proxy.CommonProxy;
import lilrichy.lilrichymod.recipes.Recipes;
import lilrichy.lilrichymod.reference.Reference;
import lilrichy.lilrichymod.utility.LogHelper;
import lilrichy.lilrichymod.utility.OreDicLogMaker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION,
        acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class LiLRichyMod {
    @Mod.Instance(Reference.MOD_ID)
    public static LiLRichyMod instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.info("LILRICHY MOD IS BOOTING UP!!!!!!!!!");
        LogHelper.info("PreInit:");
        proxy.preInit();

        //Config File
        LogHelper.info("Loading Config File");
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());

        //Mod Blocks
        LogHelper.info("Blocks Loading");
        ModBlocks.register();

        //Mod Items
        LogHelper.info("Loading Items");
        ModItems.init();
        ModItems.register();

        //Handlers
        LogHelper.info("Loading Handlers");
        //PacketDescriptionHandler.init();
        //NetworkHandler.init();
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        GameRegistry.registerFuelHandler(new FuelHandler());

        //Gui
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        LogHelper.info("Pre Initialization Complete");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LogHelper.info("Init:");
        proxy.init();

        LogHelper.info("Recipes Loading");
        //Recipes - All recipes are loaded from Sub Classes of this file.
        Recipes.init();

        LogHelper.info("Initialization Complete");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LogHelper.info("PostInit:");

        if (ConfigurationHandler.makeOreDicLog) OreDicLogMaker.CreateOreDicLog();

        LogHelper.info("Post Initialization Complete");
        LogHelper.info("Good News Everybody!");
        LogHelper.info("LiLRichy Mod Loaded!");
    }

    @Mod.EventHandler
    public void init(FMLServerStoppingEvent event) {
        EventHandler.animalWards.clear();
        EventHandler.mobWards.clear();
    }
}