package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantInfor;

// Output: アルゴリズム「年休付与情報を取得」を実行する I
@Data
public class GetAnnualHolidayGrantInforDto {
	
	// 年休付与情報 - Annual leave grant information
	private Optional<AnnualHolidayGrantInfor> annualHolidayGrantInfor;
	
	// 抽出対象社員（true(def)、false） - Employees to be extracted (true (default), false)
	private boolean employeeExtracted = true;
}
