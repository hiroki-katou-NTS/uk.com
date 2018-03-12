package nts.uk.ctx.at.request.app.command.application.triprequestsetting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripRequestSetCommand {
	/** コメント１ */
	private String comment1;
	/** コメント2 */
	private String comment2;
	private String color1;
	// 上部コメント.太字
	private int weight1;
	private String color2;
	// 上部コメント.太字
	private int weight2;
	/** WORK_TYPE */
	private int workType;
	/** 勤務の変更 */
	private int workChange;
	/** 勤務の変更申請時 */
	private int workChangeTime;
	/** 申請対象の矛盾チェック */
	private int contractCheck;
	/** 遅刻早退設定 */
	private int lateLeave;
}
