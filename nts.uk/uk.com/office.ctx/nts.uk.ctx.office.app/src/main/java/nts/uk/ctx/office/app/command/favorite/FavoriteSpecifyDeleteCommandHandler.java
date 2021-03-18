package nts.uk.ctx.office.app.command.favorite;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecifyRepository;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.App.お気に入りを削除する.お気に入りを削除する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class FavoriteSpecifyDeleteCommandHandler extends CommandHandler<FavoriteSpecifyDelCommand> {

	@Inject
	private FavoriteSpecifyRepository favoriteSpecifyRepository;

	@Override
	protected void handle(CommandHandlerContext<FavoriteSpecifyDelCommand> context) {
		FavoriteSpecifyDelCommand command = context.getCommand();
		favoriteSpecifyRepository.getBySidAndDate(command.getCreatorId(), command.getInputDate())
			.ifPresent(domain -> favoriteSpecifyRepository.delete(domain));
	}
}
