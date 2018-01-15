package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GrantPeriodic extends DomainObject {

	/* 会社ID */
	private String companyId;

	/* 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/* 付与日数定期 */
	private GrantDay grantDay;

	/* 固定付与日数 */
	private SplitAcquisition splitAcquisition;

	/* 付与日数定期方法 */
	private GrantPeriodicMethod grantPeriodicMethod;

	@Override
	public void validate() {
		super.validate();
	}

	/**
	 * Check Grant Day of Grant Periodic
	 */
	public void checkGrantDay() {
		if (this.splitAcquisition == SplitAcquisition.FixedDay) {
			if (this.grantDay == null) {
				throw new BusinessException("Msg_97");
			}
		}
	}
	


	/**
	 * Create from Java Type of Grant Periodic
	 * 
	 * @param companyId
	 * @param specialHolidayCode
	 * @param grantDay
	 * @param splitAcquisition
	 * @param grantPeriodicMethod
	 * @return
	 */
	public static GrantPeriodic createFromJavaType(String companyId, String specialHolidayCode, int grantDay,
			int splitAcquisition, int grantPeriodicMethod) {
		return new GrantPeriodic(companyId, new SpecialHolidayCode(specialHolidayCode), new GrantDay(grantDay),
				EnumAdaptor.valueOf(splitAcquisition, SplitAcquisition.class),
				EnumAdaptor.valueOf(grantPeriodicMethod, GrantPeriodicMethod.class));
	}

}
