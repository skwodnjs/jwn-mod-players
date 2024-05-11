package net.jwn.mod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.jwn.mod.Main;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class GetOpponentCommand {
    public GetOpponentCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("opponent").then(Commands.literal("get").executes(this::getOpponent)));
    }

    private int getOpponent(CommandContext<CommandSourceStack> command) {
        if (command.getSource().getEntity() instanceof Player player) {
            String targetName = player.getPersistentData().getString(Main.MOD_ID + "_opponent");
            boolean hasTarget = !targetName.isEmpty();

            if (hasTarget) {
                player.sendSystemMessage(Component.literal("opponent: " + targetName));
            } else {
                player.sendSystemMessage(Component.literal("no opponent"));
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
