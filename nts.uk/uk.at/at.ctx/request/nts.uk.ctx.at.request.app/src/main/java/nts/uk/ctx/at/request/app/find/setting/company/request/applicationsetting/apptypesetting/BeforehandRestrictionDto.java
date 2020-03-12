package nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.BeforehandRestriction;

@AllArgsConstructor
@NoArgsConstructor
public class BeforehandRestrictionDto {
	/**
	 * チェック方法
	 */
	public int methodCheck;

	/**
	 * 利用する
	 */
	public boolean toUse;

	/**
	 * 日数
	 */
	public int dateBeforehandRestriction;

	/**
	 * 時刻（早出残業・通常残業）
	 */
	public Integer timeBeforehandRestriction;

	/**
	 * 時刻（早出残業）
	 */
	public Integer preOtTime;

	/**
	 * 時刻（通常残業）
	 */
	public Integer normalOtTime;

	/**
	 * 日数 - 残業申請事前の受付制限
	 */
	public int otRestrictPreDay;

	/**
	 * 利用する - 残業申請事前の受付制限
	 */
	public boolean otToUse;
	
	public static BeforehandRestrictionDto fromDomain(BeforehandRestriction beforehandRestriction) {
		BeforehandRestrictionDto beforehandRestrictionDto = new BeforehandRestrictionDto();
		beforehandRestrictionDto.methodCheck = beforehandRestriction.getMethodCheck().value;
		beforehandRestrictionDto.toUse = beforehandRestriction.getToUse();
		beforehandRestrictionDto.dateBeforehandRestriction = beforehandRestriction.getDateBeforehandRestriction().value;
		beforehandRestrictionDto.timeBeforehandRestriction = beforehandRestriction.getTimeBeforehandRestriction() == null ? null : beforehandRestriction.getTimeBeforehandRestriction().v();
		beforehandRestrictionDto.preOtTime = beforehandRestriction.getPreOtTime() == null ? null : beforehandRestriction.getPreOtTime().v();
		beforehandRestrictionDto.normalOtTime = beforehandRestriction.getNormalOtTime() == null ? null : beforehandRestriction.getNormalOtTime().v();
		beforehandRestrictionDto.otRestrictPreDay = beforehandRestriction.getOtRestrictPreDay().value;
		beforehandRestrictionDto.otToUse = beforehandRestriction.getOtToUse();
		return beforehandRestrictionDto;
	}
}
