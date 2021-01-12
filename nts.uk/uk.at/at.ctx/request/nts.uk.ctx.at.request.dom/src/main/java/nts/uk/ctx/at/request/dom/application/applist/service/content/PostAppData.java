package nts.uk.ctx.at.request.dom.application.applist.service.content;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.パラメータ.事後申請の実績データ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class PostAppData {
	/**
	 * 開始時刻
	 */
	private Integer startTime;
	
	/**
	 * 勤務種類コード
	 */
	private String workTypeCD;
	
	/**
	 * 勤務種類名称
	 */
	private String workTypeName;
	
	/**
	 * 計算フレックス
	 */
	private Integer calculationFlex;
	
	/**
	 * 計算就業外深夜
	 */
	private Integer calculationMidnightOutsideWork;
	
	/**
	 * 終了時刻
	 */
	private Integer endTime;
	
	/**
	 * 就業時間帯コード
	 */
	private Optional<String> opWorkTimeCD;
	
	/**
	 * 就業時間帯名称
	 */
	private Optional<String> opWorkTimeName;
	
	/**
	 * 申請時間
	 */
	private List<AppTimeFrameData> appTimeFrameDataLst;
}
