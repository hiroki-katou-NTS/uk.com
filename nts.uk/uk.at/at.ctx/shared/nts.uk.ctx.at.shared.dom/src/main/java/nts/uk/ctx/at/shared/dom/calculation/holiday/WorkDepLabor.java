package nts.uk.ctx.at.shared.dom.calculation.holiday;
/**
 * @author phongtq
 * 変形労働勤務の加算設定
 */
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WorkDepLabor {
	/** 会社ID */
	private String companyId;

	/** 実働のみで計算する */
	private int calcActualOperation1;

	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTime1;

	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCare1;

	/** フレックスの所定超過時 */
	private int predeterminedOvertime1;
	
	/** 加算する */
	private int additionTime1;

	/** 遅刻・早退を控除しない */
	private int notDeductLateleave1;

	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTime2;

	/** 欠勤時間をマイナスする */
	private int minusAbsenceTime2;

	/** 実働のみで計算する */
	private int calcActualOperation2;

	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCare2;

	/** 遅刻・早退を控除しない */
	private int notDeductLateleave2;
	
	/**フレックスの所定不足時*/
	private int predeterminDeficiency2;	

	/** 加算する */
	private int additionTime2;

	public static WorkDepLabor createFromJavaType(String companyId, int calcActualOperation1,
			int exemptTaxTime1, int incChildNursingCare1, int predeterminedOvertime1, int additionTime1,
			int notDeductLateleave1, int exemptTaxTime2, int minusAbsenceTime2, int calcActualOperation2,
			int incChildNursingCare2, int notDeductLateleave2, int predeterminDeficiency2, int additionTime2) {
		return new WorkDepLabor(companyId, calcActualOperation1, exemptTaxTime1, incChildNursingCare1,
				predeterminedOvertime1, additionTime1, notDeductLateleave1, exemptTaxTime2, minusAbsenceTime2,
				calcActualOperation2, incChildNursingCare2, notDeductLateleave2, predeterminDeficiency2, additionTime2);
	}
}
