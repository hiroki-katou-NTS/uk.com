package nts.uk.ctx.at.shared.dom.calculation.holiday;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FlexWork {

	/** 会社ID */
	private String companyId;

	/** 実働のみで計算する */
	private int calcActualOperationPre;

	/** インターバル免除時間を含めて計算する */
	private int calcIntervalTimePre;

	/** 育児・介護時間を含めて計算する */
	private int calcIncludCarePre;

	/** フレックスの所定超過時 */
	private int predExcessTimeflexPre;

	/** 加算する */
	private int additionTimePre;

	/** 遅刻・早退を控除しない */
	private int notDeductLateleavePre;

	/** インターバル免除時間を含めて計算する */
	private int calsIntervalTimeWork;

	/** 欠勤時間をマイナスする */
	private int minusAbsenceTimeWork;

	/** 実働のみで計算する */
	private int calcActualOperaWork;

	/** 育児・介護時間を含めて計算する */
	private int calcIncludCareWork;

	/** 遅刻・早退を控除しない */
	private int notDeductLateleaveWork;

	/**フレックスの所定不足時*/
	private int predeterminDeficiency;	
	
	/** 加算する */
	private int additionTimeWork;

	public static FlexWork createFromJavaType(String companyId,
			int calcActualOperationPre, int calcIntervalTimePre, int calcIncludCarePre, 
			int predExcessTimeflexPre,
			int additionTimePre, int notDeductLateleavePre, int calsIntervalTimeWork,
			int minusAbsenceTimeWork, int calcActualOperaWork, int calcIncludCareWork, 
			int notDeductLateleaveWork,
			int predeterminDeficiency,int additionTimeWork) {
		return new FlexWork(companyId, calcActualOperationPre,
				calcIntervalTimePre, calcIncludCarePre, predExcessTimeflexPre, additionTimePre, notDeductLateleavePre
				, calsIntervalTimeWork, minusAbsenceTimeWork, calcActualOperaWork,
				calcIncludCareWork, notDeductLateleaveWork, predeterminDeficiency, additionTimeWork);
	}
}
