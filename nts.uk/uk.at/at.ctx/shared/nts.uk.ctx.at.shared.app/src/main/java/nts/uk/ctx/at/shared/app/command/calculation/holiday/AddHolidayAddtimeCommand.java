package nts.uk.ctx.at.shared.app.command.calculation.holiday;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.holiday.FlexWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtime;
import nts.uk.ctx.at.shared.dom.calculation.holiday.IrregularWork;
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

	/** 通常勤務の加算設定*/
	private RegularWorkCommand regularWork;

	/**フレックス勤務の加算設定*/
	private FlexWorkCommand flexWork;

	/**変形労働勤務の加算設定*/
	private IrregularWorkCommand irregularWork;
	
	public HolidayAddtime toDomain(String companyId){
		return HolidayAddtime.createFromJavaType(companyId, this.referComHolidayTime, this.oneDay, this.morning, this.afternoon, this.referActualWorkHours, this.notReferringAch, 
				this.annualHoliday, this.specialHoliday, this.yearlyReserved, this.toDomainRegularWork(companyId), this.toDomainFlexWork(companyId), this.toDomainIrregularWork(companyId));
	}
	
	private RegularWork toDomainRegularWork(String companyId) {
		if (this.regularWork == null) {
			return null;
		}
		return RegularWork.createFromJavaType(companyId, this.regularWork.getCalcActualOperationPre(),
				this.regularWork.getCalcIntervalTimePre(), 
				this.regularWork.getCalcIncludCarePre(), this.regularWork.getAdditionTimePre(), 
				this.regularWork.getNotDeductLateleavePre(), this.regularWork.getDeformatExcValuePre(), 
				this.regularWork.getCalsIntervalTimeWork(),
				this.regularWork.getCalcActualOperaWork(), 
				this.regularWork.getCalcIncludCareWork(), this.regularWork.getNotDeductLateleaveWork(), 
				this.regularWork.getAdditionTimeWork());
}
	
	private FlexWork toDomainFlexWork(String companyId) {
		if (this.flexWork == null) {
			return null;
		}
		return FlexWork.createFromJavaType(companyId, this.flexWork.getCalcActualOperationPre(), this.flexWork.getCalcIntervalTimePre(), 
				this.flexWork.getCalcIncludCarePre(),this.flexWork.getPredExcessTimeflexPre(), 
				this.flexWork.getAdditionTimePre(), this.flexWork.getNotDeductLateleavePre(), 
				this.flexWork.getCalsIntervalTimeWork(), this.flexWork.getMinusAbsenceTimeWork(), this.flexWork.getCalcActualOperaWork(), 
				this.flexWork.getCalcIncludCareWork(), this.flexWork.getNotDeductLateleaveWork(),this.flexWork.getPredeterminDeficiency(), this.flexWork.getAdditionTimeWork());
}
	
	private IrregularWork toDomainIrregularWork(String companyId) {
		if (this.irregularWork == null) {
			return null;
		}
		return IrregularWork.createFromJavaType(companyId, this.irregularWork.getCalcActualOperationPre(), this.irregularWork.getCalcIntervalTimePre(), this.irregularWork.getCalcIncludCarePre(), 
				this.irregularWork.getAdditionTimePre(), this.irregularWork.getNotDeductLateleavePre(), this.irregularWork.getDeformatExcValuePre(), 
				this.irregularWork.getCalsIntervalTimeWork(), this.irregularWork.getMinusAbsenceTimeWork(), this.irregularWork.getCalcActualOperaWork(), 
				this.irregularWork.getCalcIncludCareWork(), this.irregularWork.getNotDeductLateleaveWork(), this.irregularWork.getAdditionTimeWork());
}
}
