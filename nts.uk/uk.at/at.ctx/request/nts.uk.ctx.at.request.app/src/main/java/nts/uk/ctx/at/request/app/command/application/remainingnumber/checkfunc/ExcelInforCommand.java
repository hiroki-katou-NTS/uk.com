package nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.NumberOfWorkTypeUsedImport;

@Data
public class ExcelInforCommand {
	//氏名(1列目)
		private String name;
		//入社日(2列目)
		private String dateStart;
		//退職日(3列目)	
		private String dateEnd;
		// 年休付与日(4列目)
		private String dateOffYear;
		//残数の対象日(5列目)			
		private String dateTargetRemaining;
		//年休付与後残数(6列目)				
		private Double dateAnnualRetirement;
		//年休残数 (7列目)										
		private Double dateAnnualRest;
		//年休使用数(8列目)
		private List<NumberOfWorkTypeUsedImport> numberOfWorkTypeUsedImport;
		//上限日数(9列目)
		private List<PlannedVacationListCommand> plannedVacationListCommand;
}
