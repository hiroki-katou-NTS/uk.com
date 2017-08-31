package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeSorted;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.OrderSorted;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSortedRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class UpdateBusinessTypeSortedCommandHandler extends CommandHandler<UpdateBusinessTypeSortedCommand> {

	@Inject
	private BusinessFormatSortedRepository businessFormatSortedRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateBusinessTypeSortedCommand> context) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		UpdateBusinessTypeSortedCommand command = context.getCommand();

		List<BusinessTypeSorted> businessTypeSorteds = command.getBusinessTypeSortedDtos().stream().map(f -> {
			return new BusinessTypeSorted(companyId, f.getAttendanceItemId(), new OrderSorted(f.getOrder()));
		}).collect(Collectors.toList());

		List<BusinessTypeSorted> typeSorteds = this.businessFormatSortedRepository.findAll(companyId);

		if (typeSorteds.isEmpty()) {
			this.businessFormatSortedRepository.add(businessTypeSorteds);
		} else {
			businessTypeSorteds.forEach(f -> {
				this.businessFormatSortedRepository.update(f);
			});
		}

	}

}
