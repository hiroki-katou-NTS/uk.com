/**
 * 4:17:52 PM Oct 19, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatDailyRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
import nts.uk.screen.at.app.dailyperformance.correction.dto.OperationOfDailyPerformanceDto;

/**
 * @author hungnm
 *
 */
@Stateless
public class DPUpdateColWidthCommandHandler extends CommandHandler<UpdateColWidthCommand>{

	@Inject
	private BusinessTypeFormatDailyRepository repo;
	
	@Inject
	private DailyPerformanceScreenRepo screenRepo;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateColWidthCommand> context) {
		OperationOfDailyPerformanceDto dailyPerformanceDto = screenRepo.findOperationOfDailyPerformance();
		if(dailyPerformanceDto.getSettingUnit() == SettingUnitType.AUTHORITY){
			this.screenRepo.updateColumnsWidth(context.getCommand().getLstHeader(), context.getCommand().getFormatCode()); 
		}else{
			this.repo.updateColumnsWidth(context.getCommand().getLstHeader());
		}
	}

}
