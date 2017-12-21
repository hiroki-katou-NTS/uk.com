package nts.uk.ctx.at.shared.app.command.calculation.holiday;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.holiday.FlexWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtion;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDepLabor;
import nts.uk.ctx.at.shared.dom.calculation.holiday.RegularWork;

@Data
@AllArgsConstructor
public class AddHolidayAddtimeCommand {

	/** 会社単位の休暇時間を参照する */
	private int referComHolidayTime;

	/** 1日 */
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
		return RegularWork.createFromJavaType(companyId, this.regularWork.getCalcActualOperation1(),
				this.regularWork.getExemptTaxTime1(), this.regularWork.getIncChildNursingCare1(),
				this.regularWork.getAdditionTime1(), this.regularWork.getNotDeductLateleave1(),
				this.regularWork.getDeformatExcValue1(), this.regularWork.getExemptTaxTime2(),
				this.regularWork.getCalcActualOperation2(), this.regularWork.getIncChildNursingCare2(),
				this.regularWork.getNotDeductLateleave2(), this.regularWork.getAdditionTime2());
	}

	private FlexWork toDomainFlexWork(String companyId) {
		if (this.flexWork == null) {
			return null;
		}
		return FlexWork.createFromJavaType(companyId, this.flexWork.getCalcActualOperation1(),
				this.flexWork.getExemptTaxTime1(), this.flexWork.getIncChildNursingCare1(),
				this.flexWork.getPredeterminedOvertime1(), this.flexWork.getAdditionTime1(),
				this.flexWork.getNotDeductLateleave1(), this.flexWork.getExemptTaxTime2(),
				this.flexWork.getMinusAbsenceTime2(), this.flexWork.getCalcActualOperation2(),
				this.flexWork.getIncChildNursingCare2(), this.flexWork.getNotDeductLateleave2(),
				this.flexWork.getPredeterminDeficiency2(), this.flexWork.getAdditionTime2());
	}

	private WorkDepLabor toDomainIrregularWork(String companyId) {
		if (this.irregularWork == null) {
			return null;
		}
		return WorkDepLabor.createFromJavaType(companyId, this.irregularWork.getCalcActualOperation1(),
				this.irregularWork.getExemptTaxTime1(), this.irregularWork.getIncChildNursingCare1(),
				this.irregularWork.getPredeterminedOvertime1(), this.irregularWork.getAdditionTime1(),
				this.irregularWork.getNotDeductLateleave1(), this.irregularWork.getExemptTaxTime2(),
				this.irregularWork.getMinusAbsenceTime2(), this.irregularWork.getCalcActualOperation2(),
				this.irregularWork.getIncChildNursingCare2(), this.irregularWork.getNotDeductLateleave2(),
				this.irregularWork.getPredeterminDeficiency2(),this.irregularWork.getAdditionTime2());
	}
}
