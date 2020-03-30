package nts.uk.ctx.at.function.app.find.statement.outputitemsetting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeEngravingInfor {
	// 職場コード
	private String workplaceCd;
	// 職場名
	private String workplaceName;
	// 社員コード
	private String employeeCode;
	// 社員名
	private String employeeName;
	// カードNo
	private String cardNo;
	// 日時
	private String dateAndTime;
	// 出退勤区分
	private String attendanceAtr;
	// 打刻手段
	private int stampMeans;
	// 認証方法
	private int authcMethod;
	// 設置場所
	private String installPlace;
	// 位置情報
	private String localInfor;
	// 応援カード
	private String supportCard;
	//就業時間帯
	private String workTimeDisplayName;
	//残業時間
	private String overtimeHours;
	//深夜時間
	private String lateNightTime;
	
}
