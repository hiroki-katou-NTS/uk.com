package nts.uk.ctx.at.shared.app.command.calculation.holiday;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.holiday.FlexWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtion;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDepLabor;
import nts.uk.ctx.at.shared.dom.calculation.holiday.RegularWork;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class AddHolidayAddtimeCommand {

	/** 会社単位の休暇時間を参照する */
	private int referComHolidayTime;

	/** Pre日 */
	private BigDecimal oneDay;

	/** 午前 */
	private BigDecimal morning;

	/** 午後 */
	private BigDecimal afternoon;

	/** 実績の就業時間帯を参照する */
	private int referActualWorkHours;

	/** 実績を参照しない場合の参照先 */
	private int notReferringAch;

	/** 年休 */
	private int annualHoliday;

	/** 特別休暇 */
	private int specialHoliday;

	/** 積立年休 */
	private int yearlyReserved;

	/** 通常勤務の加算設定 */
	private RegularWorkCommand regularWork;

	/** フレックス勤務の加算設定 */
	private FlexWorkCommand flexWork;

	/** 変形労働勤務の加算設定 */
	private WorkDepLaborCommand irregularWork;

	public HolidayAddtion toDomain(String companyId) {
		return HolidayAddtion.createFromJavaType(companyId, this.referComHolidayTime, this.oneDay, this.morning,
				this.afternoon, this.referActualWorkHours, this.notReferringAch, this.annualHoliday,
				this.specialHoliday, this.yearlyReserved, this.toDomainRegularWork(companyId),
				this.toDomainFlexWork(companyId), this.toDomainIrregularWork(companyId));
	}

	private RegularWork toDomainRegularWork(String companyId) {
		if (this.regularWork == null) {
			return null;
		}
		return RegularWork.createFromJavaType(companyId, this.regularWork.getCalcActualOperationPre(),
				this.regularWork.getExemptTaxTimePre(), this.regularWork.getIncChildNursingCarePre(),
				this.regularWork.getAdditionTimePre(), this.regularWork.getNotDeductLateleavePre(),
				this.regularWork.getDeformatExcValuePre(), this.regularWork.getExemptTaxTimeWork(),
				this.regularWork.getCalcActualOperationWork(), this.regularWork.getIncChildNursingCareWork(),
				this.regularWork.getNotDeductLateleaveWork(), this.regularWork.getAdditionTimeWork());
	}

	private FlexWork toDomainFlexWork(String companyId) {
		if (this.flexWork == null) {
			return null;
		}
		return FlexWork.createFromJavaType(companyId, this.flexWork.getCalcActualOperationPre(),
				this.flexWork.getExemptTaxTimePre(), this.flexWork.getIncChildNursingCarePre(),
				this.flexWork.getPredeterminedOvertimePre(), this.flexWork.getAdditionTimePre(),
				this.flexWork.getNotDeductLateleavePre(), this.flexWork.getExemptTaxTimeWork(),
				this.flexWork.getMinusAbsenceTimeWork(), this.flexWork.getCalcActualOperationWork(),
				this.flexWork.getIncChildNursingCareWork(), this.flexWork.getNotDeductLateleaveWork(),
				this.flexWork.getPredeterminDeficiencyWork(), this.flexWork.getAdditionTimeWork());
	}

	private WorkDepLabor toDomainIrregularWork(String companyId) {
		if (this.irregularWork == null) {
			return null;
		}
		return WorkDepLabor.createFromJavaType(companyId, this.irregularWork.getCalcActualOperationPre(),
				this.irregularWork.getExemptTaxTimePre(), this.irregularWork.getIncChildNursingCarePre(), this.irregularWork.getAdditionTimePre(),
				this.irregularWork.getNotDeductLateleavePre(),this.irregularWork.getDeformatExcValue(), this.irregularWork.getExemptTaxTimeWork(),
				this.irregularWork.getMinusAbsenceTimeWork(), this.irregularWork.getCalcActualOperationWork(),
				this.irregularWork.getIncChildNursingCareWork(), this.irregularWork.getNotDeductLateleaveWork(),this.irregularWork.getAdditionTimeWork());
	}
}
