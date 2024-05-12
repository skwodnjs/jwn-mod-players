package net.jwn.mod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.jwn.mod.Main;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class GetOpponentCommand {
    public GetOpponentCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("opponent").then(Commands.literal("get").executes(this::getOpponent)));
    }

    private int getOpponent(CommandContext<CommandSourceStack> command) {
        if (command.getSource().getEntity() instanceof Player player) {
            if (player.getPersistentData().hasUUID(Main.MOD_ID + "_opponent")) {
                UUID targetUUID = player.getPersistentData().getUUID(Main.MOD_ID + "_opponent");
                Player target = player.level().getPlayerByUUID(targetUUID);
                if (target != null) {
                    player.sendSystemMessage(
                            Component.translatable("message.mod_players.get_opponent", target.getName().getString())
                    );
                } else {
                    player.sendSystemMessage(Component.translatable("message.mod_players.get_opponent_not_in_game"));
                }
            } else {
                player.sendSystemMessage(Component.translatable("message.mod_players.get_opponent_empty"));
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
