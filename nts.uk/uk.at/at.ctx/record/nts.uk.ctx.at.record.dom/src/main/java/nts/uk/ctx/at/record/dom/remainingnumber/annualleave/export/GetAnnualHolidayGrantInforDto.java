package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantInfor;

@Data
public class GetAnnualHolidayGrantInforDto {
	
	// 年休付与情報 - Annual leave grant information
	private Optional<AnnualHolidayGrantInfor> annualHolidayGrantInfor;
	
	// 抽出対象社員（true(def)、false） - Employees to be extracted (true (def), false)
	private boolean employeeExtracted;
}
