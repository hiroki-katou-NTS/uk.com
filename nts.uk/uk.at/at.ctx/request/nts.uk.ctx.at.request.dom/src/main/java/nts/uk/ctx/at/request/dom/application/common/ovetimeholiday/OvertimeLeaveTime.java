package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム(残業・休出).パラメータ.起動パラメータ.残業休出時間
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class OvertimeLeaveTime {
	
	/**
	 * 1枠No
	 */
	private int frameNo;
	
	/**
	 * 2枠名
	 */
	private int frameName;
	
	/**
	 * 3時間
	 */
	private int time;
	
	/**
	 * 4勤怠種類
	 */
	private int attendanceType;
	
}
