package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class TimeHdAppSetCommand {
	// 会社ID
	private String companyId;
	// 1日休超過をチェックする
	private int checkDay;
	// 60H超休を利用する
	private int use60h;
	// 出勤前2を利用する
	private int useAttend2;
	// 出勤前2名称
	private String nameBefore2;
	// 出勤前を利用する
	private int useBefore;
	// 出勤前名称
	private String nameBefore;
	// 実績表示区分
	private int actualDisp;
	// 実績超過をチェックする 
	private int checkOver;
	// 時間代休を利用する
	private int useTimeHd;
	// 時間年休を利用する
	private int useTimeYear;
	// 私用外出を利用する
	private int usePrivate;
	// 私用外出名称
	private String privateName;
	// 組合外出を利用する
	private int unionLeave;
	// 組合外出名称
	private String unionName;
	// 退勤後2を利用する
	private int useAfter2;
	// 退勤後2名称
	private String nameAfter2;
	// 退勤後を利用する
	private int useAfter;
	// 退勤後名称
	private String nameAfter;
}
