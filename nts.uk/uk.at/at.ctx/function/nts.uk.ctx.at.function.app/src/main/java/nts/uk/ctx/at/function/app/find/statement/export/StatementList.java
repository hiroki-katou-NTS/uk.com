package nts.uk.ctx.at.function.app.find.statement.export;

import lombok.Data;
import nts.arc.time.GeneralDateTime;

@Data
public class StatementList {
	// 職場コード
	private String wkpCode;
	// 職場名
	private String wkpName;
	// 社員コード
	private String empCode;
	// 社員名
	private String empName;
	// カードNO
	private String cardNo;
	// 年月日
	private GeneralDateTime date;
	// 時刻
	private String time;
	// 出退勤区分
	private String atdType;
	// 就業時間帯
	private String workTimeZone;
	// 設置場所
	private String installPlace;
	// 位置情報
	private String localInfor;
	// 残業時間
	private String otTime;
	// 深夜時間
	private String midnightTime;
	// 応援カード
	private String supportCard;
}

