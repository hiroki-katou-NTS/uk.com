package nts.uk.ctx.at.shared.app.find.calculation.holiday;
/**
 * @author phongtq
 * 通常勤務の加算設定
 */
import lombok.Data;

@Data
public class RegularWorkDto {
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
}
