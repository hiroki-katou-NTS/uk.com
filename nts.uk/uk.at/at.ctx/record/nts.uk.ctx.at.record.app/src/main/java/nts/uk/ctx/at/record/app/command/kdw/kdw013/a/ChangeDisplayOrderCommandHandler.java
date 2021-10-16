package nts.uk.ctx.at.record.app.command.kdw.kdw013.a;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteDisplayOrder;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteTaskDisplayOrder;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteTaskDisplayOrderRepository;

/**
 * Command: 表示順を入れ替える
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.お気に入り作業.1日お気に入りセット.App.表示順を入れ替える
 * 
 * @author chungnt
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ChangeDisplayOrderCommandHandler extends CommandHandler<ChangeDisplayOrderCommand> {

	@Inject
	private OneDayFavoriteTaskDisplayOrderRepository repo;

	@Override
	protected void handle(CommandHandlerContext<ChangeDisplayOrderCommand> context) {
		ChangeDisplayOrderCommand command = context.getCommand();
		Optional<OneDayFavoriteTaskDisplayOrder> displayOrder = this.repo.get(command.getSId());

		if (displayOrder.isPresent()) {
			List<FavoriteDisplayOrder> displayOrders = command.getFavoriteDisplayOrder().stream().map(m -> {
				return new FavoriteDisplayOrder(m.getFavId(), m.getOrder());
			}).collect(Collectors.toList());
			OneDayFavoriteTaskDisplayOrder domain = new OneDayFavoriteTaskDisplayOrder(displayOrder.get().getSId(),
					displayOrders);

			this.repo.update(domain);
		}
	}
}
