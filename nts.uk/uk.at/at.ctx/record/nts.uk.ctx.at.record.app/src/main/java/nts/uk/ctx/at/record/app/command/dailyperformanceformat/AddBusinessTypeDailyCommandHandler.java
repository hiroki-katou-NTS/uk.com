package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessFormatSheet;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSheetRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt
 *
 */
@Stateless
public class AddBusinessTypeDailyCommandHandler extends CommandHandler<AddBusinessTypeDailyCommand> {

	@Inject
	private BusinessTypeFormatDailyRepository businessTypeFormatDailyRepository;

	@Inject
	private BusinessFormatSheetRepository businessFormatSheetRepository;

	@Override
	protected void handle(CommandHandlerContext<AddBusinessTypeDailyCommand> context) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		AddBusinessTypeDailyCommand command = context.getCommand();

		List<BusinessTypeFormatDaily> businessTypeFormatDailies = command.getBusinessTypeFormatDetailDtos().stream()
				.map(f -> {
					return new BusinessTypeFormatDaily(companyId, new BusinessTypeCode(command.getBusinesstypeCode()),
							f.getAttendanceItemId(), command.getSheetNo(), f.getOrder(), f.getColumnWidth());
				}).collect(Collectors.toList());

		this.businessTypeFormatDailyRepository.add(businessTypeFormatDailies);

		BusinessFormatSheet businessFormatSheet = new BusinessFormatSheet(companyId,
				new BusinessTypeCode(command.getBusinesstypeCode()), command.getSheetNo(), command.getSheetName());

		if(this.businessFormatSheetRepository.checkExistData(companyId, new BusinessTypeCode(command.getBusinesstypeCode()), command.getSheetNo())){
			this.businessFormatSheetRepository.update(businessFormatSheet);
		} else {
			this.businessFormatSheetRepository.add(businessFormatSheet);			
		}
	}

}
