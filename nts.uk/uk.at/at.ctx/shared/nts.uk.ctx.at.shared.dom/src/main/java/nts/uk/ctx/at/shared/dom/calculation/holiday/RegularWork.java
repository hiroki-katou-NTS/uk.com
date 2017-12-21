package nts.uk.ctx.at.shared.dom.calculation.holiday;
/**
 * @author phongtq
 * 通常勤務の加算設定
 */
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegularWork {
	/** 会社ID */
	private String companyId;
	
	/** 実働のみで計算する */
	private int calcActualOperation1;
	
	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTime1;
	
	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCare1;
	
	/** 加算する */
	private int additionTime1;
	
	/** 遅刻・早退を控除しない */
	private int notDeductLateleave1;
	
	/** 通常、変形の所定超過時 */
	private int deformatExcValue1;
	
	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTime2;
	
	/** 実働のみで計算する */
	private int calcActualOperation2;
	
	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCare2;
	
	/** 遅刻・早退を控除しない */
	private int notDeductLateleave2;
	
	/** 加算する */
	private int additionTime2;

	
	public static RegularWork createFromJavaType(String companyId, int calcActualOperation1,
			int exemptTaxTime1, int incChildNursingCare1, int additionTime1, int notDeductLateleave1,
			int deformatExcValue1, int exemptTaxTime2, int calcActualOperation2,
			int incChildNursingCare2, int notDeductLateleave2, int additionTime2){
		return new RegularWork(companyId, calcActualOperation1, exemptTaxTime1, incChildNursingCare1,
				additionTime1, notDeductLateleave1, deformatExcValue1, exemptTaxTime2,
				calcActualOperation2, incChildNursingCare2, notDeductLateleave2, additionTime2);
	}
}
