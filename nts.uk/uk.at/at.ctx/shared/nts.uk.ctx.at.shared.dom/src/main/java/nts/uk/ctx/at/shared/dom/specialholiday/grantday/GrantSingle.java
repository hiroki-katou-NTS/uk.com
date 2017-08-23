package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

@AllArgsConstructor
@Getter
public class GrantSingle {

	/* 会社ID */
	private String companyId;

	/* 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/* 種類 */
	private GrantDaySingleType grantDaySingleType;

	/* 固定付与日数 */
	private FixNumberDays fixNumberDays;

	/* 忌引とする */
	private MakeInvitation makeInvitation;

	/* 休日除外区分 */
	private HolidayExclusionAtr holidayExclusionAtr;
	
	public static GrantSingle createSimpleFromJavaType(String companyId,
			String specialHolidayCode,
			int grantDaySingleType,
			int fixNumberDays,
			int makeInvitation,
			int holidayExclusionAtr){
					return new GrantSingle(companyId,
							new SpecialHolidayCode(specialHolidayCode),
							EnumAdaptor.valueOf(grantDaySingleType, GrantDaySingleType.class),
							new FixNumberDays(fixNumberDays),
							EnumAdaptor.valueOf(makeInvitation, MakeInvitation.class),
							EnumAdaptor.valueOf(holidayExclusionAtr, HolidayExclusionAtr.class));
	}

}
