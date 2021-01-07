package nts.uk.ctx.at.request.dom.application.applist.service.content;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.パラメータ.残業申請データ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppOverTimeData {
	/**
	 * 開始時刻
	 */
	private Integer startTime;
	
	/**
	 * 残業区分
	 */
	private int overtimeAtr;
	
	/**
	 * 終了時刻
	 */
	private Integer endTime;
	
	/**
	 * 申請ID
	 */
	private String appID;
	
	/**
	 * 超過回数
	 */
	private Integer excessTimeNumber;
	
	/**
	 * 超過時間
	 */
	private Integer excessTime;
	
	/**
	 * フレックス超過時間
	 */
	private Optional<Integer> opFlexOvertime;
	
	/**
	 * 勤務種類コード
	 */
	private Optional<String> opWorkTypeCD;
	
	/**
	 * 勤務種類名称
	 */
	private Optional<String> opWorkTypeName;
	
	/**
	 * 事後申請の実績
	 */
	private Optional<PostAppData> opPostAppData;
	
	/**
	 * 事前申請データ
	 */
	private Optional<AppOverTimeData> opPreAppData;
	
	/**
	 * 就業時間外深夜時間
	 */
	private Optional<Integer> opMidnightHoursOutside;
	
	/**
	 * 就業時間帯コード
	 */
	private Optional<String> opWorkTimeCD;
	
	/**
	 * 就業時間帯名称
	 */
	private Optional<String> opWorkTimeName;
	
	/**
	 * 背景色
	 */
	private Optional<String> opBackgroundColor;
	
	/**
	 * 残業申請時間
	 */
	private List<AppTimeFrameData> appTimeFrameDataLst;
}
