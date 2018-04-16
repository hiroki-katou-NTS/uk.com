package nts.uk.ctx.at.record.app.command.remainingnumber.annualeave;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregPersonId;

@Getter
public class UpdateAnnuaLeaveCommand {
	
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
	private BigDecimal workingDaysPerYear;
	
	/**
	 * 導入前労働日数
	 */
	@PeregItem("IS00285")	
	private BigDecimal workingDayBeforeIntro;
	
	/**
	 * 時間年休上限時間
	 */
	@PeregItem("IS00287")	
	private BigDecimal maxMinutes;
	
	/**
	 * 時間年休使用時間
	 */
	@PeregItem("IS00288")	
	private BigDecimal usedMinutes;
	
	/**
	 * 半休上限回数
	 */
	@PeregItem("IS00291")	
	private BigDecimal maxTimes;
	
	/**
	 * 半休使用回数
	 */
	@PeregItem("IS00292")	
	private BigDecimal usedTimes;
	
}
