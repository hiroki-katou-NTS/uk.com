package nts.uk.ctx.office.ws.favorite;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.office.app.command.favorite.FavoriteSpecifyCommand;
import nts.uk.ctx.office.app.command.favorite.FavoriteSpecifyDelCommand;
import nts.uk.ctx.office.app.command.favorite.FavoriteSpecifyDeleteCommandHandler;
import nts.uk.ctx.office.app.command.favorite.FavoriteSpecifyInsertOrUpdateCommandHandler;

@Path("ctx/office/favorite")
@Produces("application/json")
public class FavoriteSpecifyWs extends WebService {
	
	@Inject
	private FavoriteSpecifyDeleteCommandHandler favoriteDelCommand;
	
	@Inject
	private FavoriteSpecifyInsertOrUpdateCommandHandler favoriteSaveOrUpdateCommand;
	
	@POST
	@Path("delete")
	public void deleteFavoriteSpecify(FavoriteSpecifyDelCommand command) {
		this.favoriteDelCommand.handle(command);
	}
	
	@POST
	@Path("save")
	public void saveOrUpdateFavoriteSpecify(List<FavoriteSpecifyCommand> command) {
		this.favoriteSaveOrUpdateCommand.handle(command);
	}
}
