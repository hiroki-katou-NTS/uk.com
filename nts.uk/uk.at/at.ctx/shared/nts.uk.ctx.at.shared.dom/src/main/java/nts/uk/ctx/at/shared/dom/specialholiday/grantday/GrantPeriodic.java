package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

@AllArgsConstructor
@Getter
public class GrantPeriodic {

	/* 会社ID */
	private String companyId;

	/* 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/* 固定付与日数 */
	private FixedDayGrant fixedDayGrant;

	/* 付与日数定期 */
	private GrantDay grantDay;

	/* 付与日数定期方法 */
	private GrantPeriodicMethod grantPeriodicMethod;

	public static GrantPeriodic createSimpleFromJavaType(String companyId,
			String specialHolidayCode,
			int fixedDayGrant,
			int grantDay,
			int grantPeriodicMethod){
					return new GrantPeriodic(companyId,
							new SpecialHolidayCode(specialHolidayCode),
							EnumAdaptor.valueOf(fixedDayGrant, FixedDayGrant.class),
							new GrantDay(grantDay),
							EnumAdaptor.valueOf(grantPeriodicMethod, GrantPeriodicMethod.class));
	}
}
