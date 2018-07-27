package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@NoArgsConstructor
public class SaveExecutionTaskSettingCommand {
	private boolean newMode;
	
	/* 会社ID */
	private String companyId;
	
	/* コード */
	private String execItemCd;
	
	/* 開始日 */
	private GeneralDate startDate;
	
	/* 開始時刻 */
	private Integer startTime;
	
	/* 実行タスク終了時刻設定 */
	private Integer endTimeCls;
	
	/* 終了時刻 */
	private Integer endTime;
	
	/* 実行タスク終了時刻設定 */
	private Integer oneDayRepCls;
	
	/* 繰り返し間隔 */
	private Integer oneDayRepInterval;
	
	/* 繰り返しする */
	private boolean repeatCls;
	
	/* 繰り返し内容 */
	private Integer repeatContent;
	
	/* 実行タスク終了日区分 */
	private Integer endDateCls;
	
	/* 終了日日付指定 */
	private GeneralDate endDate;
	
	/* 更新処理有効設定 */
	private boolean enabledSetting;
	
	/* 次回実行日時 */
//	private GeneralDateTime nextExecDateTime;
	
	
	/* 日 */
	private boolean monday;
	
	/* 月 */
	private boolean tuesday;
	
	/* 火 */
	private boolean wednesday;
	
	/* 水 */
	private boolean thursday;
	
	/* 木 */
	private boolean friday;
	
	/* 金 */
	private boolean saturday;
	
	/* 土 */
	private boolean sunday;
	
	/* 1月 */
	private boolean january;
	
	/* 2月 */
	private boolean february;
	
	/* 3月 */
	private boolean march;
	
	/* 4月 */
	private boolean april;
	
	/* 5月 */
	private boolean may;
	
	/* 6月 */
	private boolean june;
	
	/* 7月 */
	private boolean july;
	
	/* 8月 */
	private boolean august;
	
	/* 9月 */
	private boolean september;
	
	/* 10月 */
	private boolean october;
	
	/* 11月 */
	private boolean november;
	
	/* 12月 */
	private boolean december;
	
	private List<Integer> repeatMonthDateList;
}
