package nts.uk.ctx.at.record.app.command.remainingnumber.annualeave;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregPersonId;

@Getter
public class AnnuaLeaveCommand {
	
	@PeregPersonId
	private String personId;
	
	@PeregEmployeeId
	private String employeeId;
	
	/**
	 * 年休付与基準日
	 */
	@PeregItem("IS00279")	
	private GeneralDate standardDate;
	
	/**
	 * 年休付与テーブル
	 */
	@PeregItem("IS00280")	
	private String grantTable;
	
	/**
	 * 年間所定労働日数
	 */
	@PeregItem("IS00284")	
	private int workingDaysPerYear;
	
	/**
	 * 導入前労働日数
	 */
	@PeregItem("IS00285")	
	private int workingDayBeforeIntro;
	
	/**
	 * 時間年休上限時間
	 */
	@PeregItem("IS00287")	
	private int maxMinutes;
	
	/**
	 * 時間年休使用時間
	 */
	@PeregItem("IS00288")	
	private int usedMinutes;
	
	/**
	 * 半休上限回数
	 */
	@PeregItem("IS00291")	
	private int maxTimes;
	
	/**
	 * 半休使用回数
	 */
	@PeregItem("IS00292")	
	private int usedTimes;
	
}
