package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.networking.ModMessages;
import net.jwn.mod.networking.packet.ResetOpponentC2SPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
public class ModClientOnlyEvents {
    @SubscribeEvent
    public static void onClientPlayerLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        ModMessages.sendToServer(new ResetOpponentC2SPacket());
    }
}
