package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

import lombok.Data;
import nts.arc.time.GeneralDate;

// class 残数詳細
@Data
public class RemainNumberDetailDto {

	// field 当月で期限切れ
	private boolean isExpiredInCurrentMonth;
	
	// field 期限日
	private GeneralDate expirationDate;
	
	// field 消化数
	private Double digestionNumber;
	
	// field 消化日
	private GeneralDate digestionDate;
	
	// field 消化時間
	private Integer digestionHour;
	
	// field 発生数
	private Double occurrenceNumber;
	
	// field 発生日
	private GeneralDate occurrenceDate;
	
	// field 発生時間
	private Integer occurrenceHour;
	
	// field 管理データ状態区分
	private int managementDataStatus;
	
}
