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
	
	/* 付与日数定期 */
	private GrantDay grantDay;
	
	/* 固定付与日数 */
	private SplitAcquisition splitAcquisition;


	/* 付与日数定期方法 */
	private GrantPeriodicMethod grantPeriodicMethod;

	public static GrantPeriodic createFromJavaType(String companyId,
			int specialHolidayCode,
			int grantDay,
			int fixedDayGrant,
			int grantPeriodicMethod){
					return new GrantPeriodic(companyId,
							new SpecialHolidayCode(specialHolidayCode),
							new GrantDay(grantDay),
							EnumAdaptor.valueOf(fixedDayGrant, SplitAcquisition.class),
							EnumAdaptor.valueOf(grantPeriodicMethod, GrantPeriodicMethod.class));
	}

	public GrantPeriodic() {
		// TODO Auto-generated constructor stub
	}
}
