package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class AddBusinessTypeMonthlyCommandHandler extends CommandHandler<AddBusinessTypeMonthlyCommand> {

	@Inject
	private BusinessTypeFormatMonthlyRepository businessTypeFormatMonthlyRepository;

	@Override
	protected void handle(CommandHandlerContext<AddBusinessTypeMonthlyCommand> context) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		AddBusinessTypeMonthlyCommand command = context.getCommand();

		List<BusinessTypeFormatMonthly> businessTypeFormatMonthlyAdds = command.getBusinessTypeFormatDetailDtos().stream()
				.map(f -> {
					return new BusinessTypeFormatMonthly(companyId, new BusinessTypeCode(command.getBusinesstypeCode()),
							f.getAttendanceItemId(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());
		
		this.businessTypeFormatMonthlyRepository.add(businessTypeFormatMonthlyAdds);
	}

}
