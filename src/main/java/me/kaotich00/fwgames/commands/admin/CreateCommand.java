package me.kaotich00.fwgames.commands.admin;

import me.kaotich00.fwgames.Fwgames;
import me.kaotich00.fwgames.api.game.GameService;
import me.kaotich00.fwgames.commands.FwGameCommand;
import me.kaotich00.fwgames.game.SimpleGameService;
import me.kaotich00.fwgames.utils.ChatUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class CreateCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if( !(src instanceof Player) ) {
            src.sendMessage(ChatUtils.formatErrorMessage("Only players can run this command"));
            return CommandResult.empty();
        }

        GameService gameService = Sponge.getServiceManager().provideUnchecked(GameService.class);
        String eventName = args.<String>getOne("evento").get();

        if( !gameService.isNameAvailable(eventName) ) {
            src.sendMessage(ChatUtils.formatErrorMessage("Esiste gia' un evento con questo nome"));
            return CommandResult.empty();
        }

        gameService.createGame( (Player) src, eventName );
        return CommandResult.success();
    }

    public static CommandSpec build() {

        return CommandSpec.builder()
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("evento"))))
                .permission(Fwgames.NAME + ".admin")
                .executor(new CreateCommand())
                .build();
    }

}
