package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeSortedMobile;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.OrderSorted;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSortedMBRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class UpdateBusTypeSortedMBCommandHandler extends CommandHandler<UpdateBusTypeSortedMBCommand>{

	@Inject
	private BusinessFormatSortedMBRepository businessFormatSortedRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateBusTypeSortedMBCommand> context) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		UpdateBusTypeSortedMBCommand command = context.getCommand();

		List<BusinessTypeSortedMobile> businessTypeSorteds = command.getBusinessTypeSortedDtos().stream().map(f -> {
			return new BusinessTypeSortedMobile(companyId, f.getAttendanceItemId(), new OrderSorted(f.getOrder()));
		}).collect(Collectors.toList());

		List<BusinessTypeSortedMobile> typeSorteds = this.businessFormatSortedRepository.findAll(companyId);

		if (typeSorteds.isEmpty()) {
			this.businessFormatSortedRepository.add(businessTypeSorteds);
		} else {
			businessTypeSorteds.forEach(f -> {
				this.businessFormatSortedRepository.update(f);
			});
		}

	}

}
