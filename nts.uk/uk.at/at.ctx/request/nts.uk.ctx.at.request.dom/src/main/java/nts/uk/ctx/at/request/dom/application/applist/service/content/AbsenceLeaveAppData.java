package nts.uk.ctx.at.request.dom.application.applist.service.content;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 5
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面ver4.アルゴリズム.パラメータ.振休申請データ
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AbsenceLeaveAppData {
	
	/**
	 * 勤務種類コード
	 */
	private String workTypeCD;
	
	/**
	 * 勤務種類名称
	 */
	private String workTypeName;
	
	/**
	 * 申請ID
	 */
	private String appID;
	
	/**
	 * 勤務開始時間
	 */
	private Optional<Integer> opStartTime;
	
	/**
	 * 勤務終了時間
	 */
	private Optional<Integer> opEndTime;
}
