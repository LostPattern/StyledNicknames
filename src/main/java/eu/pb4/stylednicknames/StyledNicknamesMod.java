package eu.pb4.stylednicknames;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import eu.pb4.stylednicknames.command.Commands;
import eu.pb4.stylednicknames.config.ConfigManager;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("stylednicknames")
@EventBusSubscriber
public class StyledNicknamesMod {
	public static final String ID = "stylednicknames";

	public static final Logger LOGGER = LogManager.getLogger("StyledNicknames");
	public static String VERSION;

	public StyledNicknamesMod(ModContainer container) {
		VERSION = container.getModInfo().getVersion().toString();

		Placeholders.register(Identifier.of("styled-nicknames","display_name"), (ctx, arg) -> {
			if (ctx.hasPlayer()) {
				if (ctx.player().networkHandler != null) {
					return PlaceholderResult.value(NicknameHolder.of(ctx.player().networkHandler).styledNicknames$getOutputOrVanilla());
				} else {
					return PlaceholderResult.value(ctx.player().getName());
				}
			} else {
				return PlaceholderResult.invalid("Not a player!");
			}
		});
	}

	@SubscribeEvent
	public static void onServerStarting(ServerStartingEvent event) {
		ConfigManager.loadConfig();
	}

	public static final Identifier id(String path) {
		return Identifier.of(ID, path);
	}

}
