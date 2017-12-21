package nts.uk.ctx.at.shared.app.find.calculation.holiday;
/**
 * @author phongtq
 * フレックス勤務の加算設定
 */
import lombok.Data;

@Data
public class FlexWorkDto {
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
}
