package nts.uk.ctx.at.shared.dom.calculation.holiday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * 
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter
public class RegularWork {
	/** 会社ID */
	private String companyId;

	/** 実働のみで計算する */
	private CalcActualOperationAtr calcActualOperation1;

	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTime1;

	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCare1;

	/** 加算する */
	private int additionTime1;

	/** 遅刻・早退を控除しない */
	private int notDeductLateleave1;

	/** 通常、変形の所定超過時 */
	private DeformatExcValueAtr deformatExcValue1;

	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTime2;

	/** 実働のみで計算する */
	private CalcActualOperationAtr calcActualOperation2;

	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCare2;

	/** 遅刻・早退を控除しない */
	private int notDeductLateleave2;

	/** 加算する */
	private int additionTime2;

	/**
	 * create From Java Type Regular Work
	 * 
	 * @param companyId
	 * @param calcActualOperation1
	 * @param exemptTaxTime1
	 * @param incChildNursingCare1
	 * @param additionTime1
	 * @param notDeductLateleave1
	 * @param deformatExcValue1
	 * @param exemptTaxTime2
	 * @param calcActualOperation2
	 * @param incChildNursingCare2
	 * @param notDeductLateleave2
	 * @param additionTime2
	 * @return
	 */
	public static RegularWork createFromJavaType(String companyId, int calcActualOperation1, int exemptTaxTime1,
			int incChildNursingCare1, int additionTime1, int notDeductLateleave1, int deformatExcValue1,
			int exemptTaxTime2, int calcActualOperation2, int incChildNursingCare2, int notDeductLateleave2,
			int additionTime2) {
		return new RegularWork(companyId, EnumAdaptor.valueOf(calcActualOperation1, CalcActualOperationAtr.class),
				exemptTaxTime1, incChildNursingCare1, additionTime1, notDeductLateleave1,
				EnumAdaptor.valueOf(deformatExcValue1, DeformatExcValueAtr.class), exemptTaxTime2,
				EnumAdaptor.valueOf(calcActualOperation2, CalcActualOperationAtr.class), incChildNursingCare2,
				notDeductLateleave2, additionTime2);
	}
}
