package nts.uk.ctx.at.shared.dom.calculation.holiday;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegularWork {
	/** 会社ID */
	private String companyId;
	
	/** 実働のみで計算する */
	private int calcActualOperationPre;
	
	/** インターバル免除時間を含めて計算する */
	private int calcIntervalTimePre;
	
	/** 育児・介護時間を含めて計算する */
	private int calcIncludCarePre;
	
	/** 加算する */
	private int additionTimePre;
	
	/** 遅刻・早退を控除しない */
	private int notDeductLateleavePre;
	
	/** 通常、変形の所定超過時 */
	private int deformatExcValuePre;
	
	/** インターバル免除時間を含めて計算する */
	private int calsIntervalTimeWork;
	
	/** 実働のみで計算する */
	private int calcActualOperaWork;
	
	/** 育児・介護時間を含めて計算する */
	private int calcIncludCareWork;
	
	/** 遅刻・早退を控除しない */
	private int notDeductLateleaveWork;
	
	/** 加算する */
	private int additionTimeWork;
	
	public static RegularWork createFromJavaType(String companyId, int calcActualOperationPre,
			int calcIntervalTimePre, int calcIncludCarePre, int additionTimePre, int notDeductLateleavePre,
			int deformatExcValuePre, int calsIntervalTimeWork, int calcActualOperaWork,
			int calcIncludCareWork, int notDeductLateleaveWork, int additionTimeWork){
		return new RegularWork(companyId, calcActualOperationPre, calcIntervalTimePre, calcIncludCarePre,
				additionTimePre, notDeductLateleavePre, deformatExcValuePre, calsIntervalTimeWork,
				calcActualOperaWork, calcIncludCareWork, notDeductLateleaveWork, additionTimeWork);
	}
}
