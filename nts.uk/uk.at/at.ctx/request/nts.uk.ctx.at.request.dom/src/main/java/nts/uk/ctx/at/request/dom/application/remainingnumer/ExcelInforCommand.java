package nts.uk.ctx.at.request.dom.application.remainingnumer;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.NumberOfWorkTypeUsedImport;

@Data
public class ExcelInforCommand {
		//社員コード　ASC(0列目) - ver6　追加
		private String employeeCode;
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
		public ExcelInforCommand(){
			
		}
		public ExcelInforCommand(String employeeCode, String name, String dateStart, String dateEnd, String dateOffYear,
				String dateTargetRemaining, Double dateAnnualRetirement, Double dateAnnualRest,
				List<NumberOfWorkTypeUsedImport> numberOfWorkTypeUsedImport,
				List<PlannedVacationListCommand> plannedVacationListCommand) {
			super();
			this.employeeCode = employeeCode;
			this.name = name;
			this.dateStart = dateStart;
			this.dateEnd = dateEnd;
			this.dateOffYear = dateOffYear;
			this.dateTargetRemaining = dateTargetRemaining;
			this.dateAnnualRetirement = dateAnnualRetirement;
			this.dateAnnualRest = dateAnnualRest;
			this.numberOfWorkTypeUsedImport = numberOfWorkTypeUsedImport;
			this.plannedVacationListCommand = plannedVacationListCommand;
		}
		
		
}
