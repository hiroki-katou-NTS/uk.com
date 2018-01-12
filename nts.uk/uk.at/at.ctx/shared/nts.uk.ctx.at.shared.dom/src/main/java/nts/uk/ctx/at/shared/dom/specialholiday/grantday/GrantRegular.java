package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GrantRegular extends DomainObject {

	/* 会社ID */
	private String companyId;

	/* 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/* 付与開始日 */
	private GeneralDate grantStartDate;

	/* 月数 */
	private Months months;

	/* 年数 */
	private Years years;

	/* 付与日定期方法 */
	private GrantRegularMethod grantRegularMethod;

	/**
	 * Validate Input
	 */
	@Override
	public void validate() {
		super.validate();
	}
	
	/**
	 * Check Grant Start Date and Months & Years of Grant Regular
	 */
	public void checkTime(){
		if (this.grantRegularMethod != GrantRegularMethod.GrantStartDateSpecify) {
			return;
		}
		
		if ((this.months.v() == 0 && this.months.v() < 0) || (this.years.v() == 0 && this.years.v() < 0)) {
			throw new BusinessException("Msg_95");
		}
	}

	/**
	 * Create from Java Type of Grant Regular
	 * @param companyId
	 * @param specialHolidayCode
	 * @param grantStartDate
	 * @param months
	 * @param years
	 * @param grantRegularMethod
	 * @return
	 */
	public static GrantRegular createFromJavaType(String companyId, String specialHolidayCode,
			GeneralDate grantStartDate, int months, int years, int grantRegularMethod) {
		return new GrantRegular(companyId, new SpecialHolidayCode(specialHolidayCode), grantStartDate,
				new Months(months), new Years(years),
				EnumAdaptor.valueOf(grantRegularMethod, GrantRegularMethod.class));
	}
}
