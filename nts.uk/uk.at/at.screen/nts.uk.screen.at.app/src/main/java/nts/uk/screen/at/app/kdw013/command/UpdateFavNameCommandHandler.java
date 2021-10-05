package nts.uk.screen.at.app.kdw013.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskName;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteSet;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteTaskSetRepository;

/**
 * 
 * @author tutt
 *
 */
@Stateless
@Transactional
public class UpdateFavNameCommandHandler extends CommandHandler<UpdateFavNameCommand> {

	@Inject
	private OneDayFavoriteTaskSetRepository repo;

	@Override
	protected void handle(CommandHandlerContext<UpdateFavNameCommand> context) {

		UpdateFavNameCommand command = context.getCommand();

		// 1. Get(お気に入りID)
		Optional<OneDayFavoriteSet> optOneDayFavoriteSet = repo.getByFavoriteId(command.getFavId());

		// 2. 1日お気に入り作業セット.isPresent
		if (optOneDayFavoriteSet.isPresent()) {
			optOneDayFavoriteSet.get().setTaskName(new FavoriteTaskName(command.getFavName()));

			// 3. persist()
			repo.update(optOneDayFavoriteSet.get());
		}
	}

}
