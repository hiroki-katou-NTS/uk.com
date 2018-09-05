package nts.uk.ctx.at.shared.app.find.remainingnumber.annualleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveGrantRule;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.HalfdayAnnualLeaveMax;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.TimeAnnualLeaveMax;
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
	private Integer workingDaysPerYear;
	
	/**
	 * 導入前労働日数
	 */
	@PeregItem("IS00285")	
	private Integer workingDayBeforeIntro;
	
	/**
	 * 時間年休上限
	 */
	@PeregItem("IS00286")	
	private String timeAnnualLeaveMax;
	
	/**
	 * 時間年休上限時間
	 */
	@PeregItem("IS00287")	
	private Integer maxMinutes;
	
	/**
	 * 時間年休使用時間
	 */
	@PeregItem("IS00288")	
	private Integer usedMinutes;
	
	/**
	 * 時間年休残時間
	 */
	@PeregItem("IS00289")	
	private Integer remainingMinutes;
	
	/**
	 * 半休上限
	 */
	@PeregItem("IS00290")	
	private String halfdayAnnLeaMax;
	
	/**
	 * 半休上限回数
	 */
	@PeregItem("IS00291")	
	private Integer maxTimes;
	
	/**
	 * 半休使用回数
	 */
	@PeregItem("IS00292")	
	private Integer usedTimes;
	
	/**
	 * 半休残回数
	 */
	@PeregItem("IS00293")	
	private Integer remainingTimes;
	
	/**
	 * 積立年休残数
	 */
	@PeregItem("IS00294")	
	private String resvLeaRemainNumber;
	
	public AnnualLeaveDto(String employeeId) {
		super(employeeId);
	}
	
	public void pullDataFromBasicInfo(AnnualLeaveEmpBasicInfo basicInfo) {
		// 付与ルール
//		AnnualLeaveGrantRule grantRule = basicInfo.getGrantRule();
		this.standardDate = basicInfo.getGrantRule().getGrantStandardDate();
		this.grantTable = basicInfo.getGrantRule().getGrantTableCode().v();
		
//		this.nextTimeGrantDate = grantRule.nextTimeGrantDate();
//		this.nextTimeGrantDays = grantRule.nextTimeGrantDays();
//		this.nextTimeMaxTime = grantRule.nextTimeMaxTime();
		
		// 年間所定労働日数
		this.workingDaysPerYear = basicInfo.getWorkingDaysPerYear().isPresent()
				? basicInfo.getWorkingDaysPerYear().get().v() : null;
		this.workingDayBeforeIntro = basicInfo.getWorkingDayBeforeIntroduction().isPresent()
				? basicInfo.getWorkingDayBeforeIntroduction().get().v() : null;
	} 
	
	
	public void pullDataFromMaxData(AnnualLeaveMaxData maxData) {
		if ( maxData.getTimeAnnualLeaveMax().isPresent()) {
			TimeAnnualLeaveMax maxTime = maxData.getTimeAnnualLeaveMax().get();
			this.maxMinutes = maxTime.getMaxMinutes().v();
			this.usedMinutes = maxTime.getUsedMinutes().v();
			this.remainingMinutes = maxTime.getRemainingMinutes().v();
		}
		
		if (maxData.getHalfdayAnnualLeaveMax().isPresent()) {
			HalfdayAnnualLeaveMax maxHalfDay = maxData.getHalfdayAnnualLeaveMax().get();
			this.maxTimes = maxHalfDay.getMaxTimes().v();
			this.usedTimes = maxHalfDay.getUsedTimes().v();
			this.remainingTimes = maxHalfDay.getRemainingTimes().v();
		}
	} 
}
