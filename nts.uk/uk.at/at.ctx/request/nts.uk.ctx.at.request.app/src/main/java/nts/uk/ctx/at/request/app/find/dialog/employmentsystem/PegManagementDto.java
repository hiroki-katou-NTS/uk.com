package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

import lombok.Data;
import nts.arc.time.GeneralDate;

// class 紐付け管理
@Data
public class PegManagementDto {

	// field 使用日
	private GeneralDate usageDate;
	
	// field 使用日数
	private double usageDay;
	
	// field 使用時間数
	private double usageHour;
	
	// field 開発日
	private GeneralDate occurrenceDate;
	
}
