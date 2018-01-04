package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SphdLimit;
import nts.uk.ctx.at.shared.dom.specialholiday.SubCondition;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantPeriodic;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantSingle;

@Data
@AllArgsConstructor
public class AddSpecialHolidayCommand {

	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private String specialHolidayCode;

	/** 特別休暇名称 */
	private String specialHolidayName;

	/** 定期付与 */
	private int grantMethod;

	/** メモ */
	private String memo;

	/***/
	private List<String> workTypeList;

	/***/
	private GrantRegularCommand grantRegular;

	/***/
	private GrantPeriodicCommand grantPeriodic;

	/***/
	private SphdLimitCommand sphdLimit;

	/***/
	private SubConditionCommand subCondition;

	/***/
	private GrantSingleCommand grantSingle;

	public SpecialHoliday toDomain(String companyId) {
		return SpecialHoliday.createFromJavaType(companyId, this.specialHolidayCode, this.specialHolidayName,
				this.grantMethod, this.memo, this.workTypeList, this.toDomainGrantRegular(companyId),
				this.toDomainGrantPeriodic(companyId), this.toDomainSphdLimit(companyId),
				this.toDomainSubCondition(companyId), this.toDomainGrantSingle(companyId));
	}

	private GrantRegular toDomainGrantRegular(String companyId) {
		if (this.grantRegular == null) {
			return null;
		}

		return GrantRegular.createFromJavaType(companyId, specialHolidayCode, this.grantRegular.getGrantStartDate(),
				this.grantRegular.getMonths(), this.grantRegular.getYears(),
				this.grantRegular.getGrantRegularMethod());
	}

	private GrantPeriodic toDomainGrantPeriodic(String companyId) {
		if (this.grantPeriodic == null) {
			return null;
		}

		return GrantPeriodic.createFromJavaType(companyId, specialHolidayCode, this.grantPeriodic.getGrantDay(),
				this.grantPeriodic.getSplitAcquisition(), this.grantPeriodic.getGrantPeriodicMethod());
	}

	private SphdLimit toDomainSphdLimit(String companyId) {
		if (this.sphdLimit == null) {
			return null;
		}
		return SphdLimit.createFromJavaType(companyId, specialHolidayCode, this.sphdLimit.getSpecialVacationMonths(),
				this.sphdLimit.getSpecialVacationYears(), this.sphdLimit.getGrantCarryForward(),
				this.sphdLimit.getLimitCarryoverDays(), this.sphdLimit.getSpecialVacationMethod());
	}

	private SubCondition toDomainSubCondition(String companyId) {
		if (this.subCondition == null) {
			return null;
		}

		return SubCondition.createFromJavaType(companyId, specialHolidayCode, this.subCondition.getUseGender(),
				this.subCondition.getUseEmployee(), this.subCondition.getUseCls(),
				this.subCondition.getUseAge(), this.subCondition.getGenderAtr(),
				this.subCondition.getLimitAgeFrom(), this.subCondition.getLimitAgeTo(),
				this.subCondition.getAgeCriteriaAtr(), this.subCondition.getAgeBaseYearAtr(),
				this.subCondition.getAgeBaseDates(), 
				this.subCondition.getEmploymentList() == null ? Collections.emptyList() : this.subCondition.getEmploymentList(), 
				this.subCondition.getClassificationList() == null ? Collections.emptyList() : this.subCondition.getClassificationList());
	}

	private GrantSingle toDomainGrantSingle(String companyId) {
		if (this.grantSingle == null) {
			return null;
		}
		return GrantSingle.createSimpleFromJavaType(companyId, specialHolidayCode,
				this.grantSingle.getGrantDaySingleType(), this.grantSingle.getFixNumberDays(),
				this.grantSingle.getMakeInvitation(), this.grantSingle.getHolidayExclusionAtr());
	}
}
