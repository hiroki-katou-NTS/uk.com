package nts.uk.ctx.at.record.app.find.remainingnumber.annualleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveGrantRule;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.HalfdayAnnualLeaveMax;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.TimeAnnualLeaveMax;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnualLeaveDto extends PeregDomainDto{
	
	/**
	 * 年休残数
	 */
	@PeregItem("IS00276")
	private String annualLeaveNumber;
	
	/**
	 * 前回年休付与日
	 */
	@PeregItem("IS00277")	
	private String lastGrantDate;
	
	/**
	 * 年休付与
	 */
	@PeregItem("IS00278")	
	private String annualLeave;
	
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
	 * 次回年休付与日
	 */
	@PeregItem("IS00281")	
	private String nextTimeGrantDate;
	
	/**
	 * 次回年休付与日数
	 */
	@PeregItem("IS00282")	
	private String nextTimeGrantDays;
	
	/**
	 * 次回時間年休付与上限
	 */
	@PeregItem("IS00283")	
	private String nextTimeMaxTime;
	
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
	 * 時間年休上限
	 */
	@PeregItem("IS00286")	
	private String timeAnnualLeaveMax;
	
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
	 * 時間年休残時間
	 */
	@PeregItem("IS00289")	
	private int remainingMinutes;
	
	/**
	 * 半休上限
	 */
	@PeregItem("IS00290")	
	private String halfdayAnnLeaMax;
	
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
	
	/**
	 * 半休残回数
	 */
	@PeregItem("IS00293")	
	private int remainingTimes;
	
	/**
	 * 積立年休残数
	 */
	@PeregItem("IS00294")	
	private String resvLeaRemainNumber;
	
	public AnnualLeaveDto(String employeeId) {
		super(employeeId);
	}
	
	public static AnnualLeaveDto createFromDomains(AnnualLeaveEmpBasicInfo basicInfo, AnnualLeaveMaxData maxData) {
		
		AnnualLeaveDto dto = new AnnualLeaveDto(basicInfo.getEmployeeId());
		
		dto.standardDate = basicInfo.getGrantRule().getGrantStandardDate();
		dto.grantTable = basicInfo.getGrantRule().getGrantTableCode().v();
		dto.workingDaysPerYear = basicInfo.getWorkingDaysPerYear().v();
		dto.workingDayBeforeIntro = basicInfo.getWorkingDayBeforeIntroduction().v();
		
		AnnualLeaveGrantRule grantRule = basicInfo.getGrantRule();
		
		dto.setNextTimeGrantDate(grantRule.nextTimeGrantDate());
		dto.setNextTimeGrantDays(grantRule.nextTimeGrantDays());
		dto.setNextTimeMaxTime(grantRule.nextTimeMaxTime());
		
		if ( maxData.getTimeAnnualLeaveMax().isPresent()) {
			TimeAnnualLeaveMax maxTime = maxData.getTimeAnnualLeaveMax().get();
			dto.maxMinutes = maxTime.getMaxMinutes().v();
			dto.usedMinutes = maxTime.getUsedMinutes().v();
			dto.remainingMinutes = maxTime.getRemainingMinutes().v();
		}
		if (maxData.getHalfdayAnnualLeaveMax().isPresent()) {
			HalfdayAnnualLeaveMax maxHalfDay = maxData.getHalfdayAnnualLeaveMax().get();
			dto.maxTimes = maxHalfDay.getMaxTimes().v();
			dto.usedTimes = maxHalfDay.getUsedTimes().v();
			dto.remainingTimes = maxHalfDay.getRemainingTimes().v();
		}
		return dto;
	} 
	
}
