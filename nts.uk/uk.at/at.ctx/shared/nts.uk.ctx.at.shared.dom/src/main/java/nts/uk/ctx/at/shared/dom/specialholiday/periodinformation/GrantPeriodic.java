package nts.uk.ctx.at.shared.dom.specialholiday.periodinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.RegularGrantDays;

/**
 * 期限情報
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GrantPeriodic extends DomainObject {

	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/** 期限指定方法 */
	private TimeLimitSpecification timeSpecifyMethod;

	/** 蓄積上限 */
	private Optional<LimitAccumulationDays> limitAccumulationDays;

//	/** 使用可能期間 */
//	private AvailabilityPeriod availabilityPeriod;

	/** 有効期限 */
	private Optional<SpecialVacationDeadline> expirationDate;


	@Override
	public void validate() {
		super.validate();
	}

	/**
	 * Validate input data
	 */
	public List<String> validateInput() {
		List<String> errors = new ArrayList<>();

		// 0年0ヶ月は登録不可
		if(this.getTimeSpecifyMethod() == TimeLimitSpecification.AVAILABLE_GRANT_DATE_DESIGNATE) {
			if (this.expirationDate.isPresent()) {
				if (this.expirationDate.get().getMonths().v() == 0 && this.expirationDate.get().getYears().v() == 0) {
					errors.add("Msg_95");
				}
			}
		}

		return errors;
	}

	/**
	 * Create from Java Type
	 * @return
	 */
	public static GrantPeriodic createFromJavaType(
			String companyId,
			int specialHolidayCode,
			int timeSpecifyMethod,
			int limitCarryoverDays,
			GeneralDate expirationDate ) {

		return new GrantPeriodic(
				companyId,
				new SpecialHolidayCode(specialHolidayCode),
				EnumAdaptor.valueOf(timeSpecifyMethod, TimeLimitSpecification.class),
				Optional.of(
						new LimitAccumulationDays(
								true, Optional.of(new LimitCarryoverDays(limitCarryoverDays)))
								),
				Optional.of(SpecialVacationDeadline.createFromJavaType(
						expirationDate.month(), expirationDate.year())));


	}
}
