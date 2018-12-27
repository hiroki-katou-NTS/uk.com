/**
 * 4:17:52 PM Oct 19, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlyRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
import nts.uk.screen.at.app.dailyperformance.correction.dto.OperationOfDailyPerformanceDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class DPUpdateColWidthCommandHandler extends CommandHandler<UpdateColWidthCommand> {

	@Inject
	private BusinessTypeFormatDailyRepository repo;

	@Inject
	private DailyPerformanceScreenRepo screenRepo;

	@Inject
	private BusinessTypeFormatMonthlyRepository businessTypeFormatMonthlyRepository;

	@Inject
	private AuthorityFormatMonthlyRepository authorityFormatMonthlyRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateColWidthCommand> context) {
		String companyId = AppContexts.user().companyId();
		Map<Integer, Integer> lstHeader = context.getCommand().getLstHeader();
		Map<Integer, Integer> lstHeaderMIGrid = context.getCommand().getLstHeaderMiGrid();
		List<String> listFormatCode = context.getCommand().getFormatCode();
		
		OperationOfDailyPerformanceDto dailyPerformanceDto = screenRepo.findOperationOfDailyPerformance();
		if (dailyPerformanceDto.getSettingUnit() == SettingUnitType.AUTHORITY) {
			if(!lstHeader.isEmpty() && !listFormatCode.isEmpty()) this.screenRepo.updateColumnsWidth(lstHeader, listFormatCode);
			if(!lstHeaderMIGrid.isEmpty() && !listFormatCode.isEmpty()) this.authorityFormatMonthlyRepository.updateColumnsWidth(companyId, lstHeaderMIGrid, listFormatCode);
		} else {
			if(!lstHeader.isEmpty())  this.repo.updateColumnsWidth(lstHeader);
			if(!lstHeaderMIGrid.isEmpty() && !listFormatCode.isEmpty())  this.businessTypeFormatMonthlyRepository.updateColumnsWidth(companyId, lstHeaderMIGrid, listFormatCode);
		}
	}

}
