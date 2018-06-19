package nts.uk.screen.at.app.monthlyperformance.correction;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.ColumnWidtgByMonthlyRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformanceRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author nampt
 *
 */
@Stateless
public class MPUpdateColWidthCommandHandler extends CommandHandler<MPUpdateColWidthCommand>{
	
	@Inject
	private FormatPerformanceRepository formatPerformanceRepository;
	
	@Inject
	private MonPfmCorrectionFormatRepository monPfmCorrectionFormatRepository;
	
	@Inject
	private ColumnWidtgByMonthlyRepository columnWidtgByMonthlyRepository;

	@Override
	protected void handle(CommandHandlerContext<MPUpdateColWidthCommand> context) {
		Optional<FormatPerformance> formatPerformance = this.formatPerformanceRepository.getFormatPerformanceById(AppContexts.user().companyId());
		if (formatPerformance.isPresent()) {
			if (formatPerformance.get().getSettingUnitType() == SettingUnitType.AUTHORITY) {
				this.monPfmCorrectionFormatRepository.updateWidthMonthly(context.getCommand().getLstHeader(), context.getCommand().getFormatCode());
			} else {
				this.columnWidtgByMonthlyRepository.updateColumnWidtgByMonthly(context.getCommand().getLstHeader());
			}
		}
		
	}

}
