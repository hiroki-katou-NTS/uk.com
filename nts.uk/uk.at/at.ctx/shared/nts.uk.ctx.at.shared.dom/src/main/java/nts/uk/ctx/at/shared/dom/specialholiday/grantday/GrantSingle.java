package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GrantSingle extends DomainObject {

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
	
	@Override
	public void validate() {
		super.validate();
	}
	
	/**
	 * Check Fix Number Day
	 */
	public void checkFixNumberDay() {
		if (this.grantDaySingleType != GrantDaySingleType.FixDay) {
			return;
		}
		if (this.fixNumberDays == null) {
			throw new BusinessException("Msg_97");
		}
	}

	/**
	 * Create From Java Type Grant Single
	 * 
	 * @param companyId
	 * @param specialHolidayCode
	 * @param grantDaySingleType
	 * @param fixNumberDays
	 * @param makeInvitation
	 * @param holidayExclusionAtr
	 * @return
	 */
	public static GrantSingle createSimpleFromJavaType(String companyId, String specialHolidayCode,
			int grantDaySingleType, Integer fixNumberDays, int makeInvitation, int holidayExclusionAtr) {
		return new GrantSingle(companyId, new SpecialHolidayCode(specialHolidayCode),
				EnumAdaptor.valueOf(grantDaySingleType, GrantDaySingleType.class), 
				fixNumberDays != null ? new FixNumberDays(fixNumberDays) : null,
				EnumAdaptor.valueOf(makeInvitation, MakeInvitation.class),
				EnumAdaptor.valueOf(holidayExclusionAtr, HolidayExclusionAtr.class));
	}
}
