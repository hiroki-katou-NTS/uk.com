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
	private int calcActualOperationPre;
	
	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTimePre;
	
	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCarePre;
	
	/** 加算する */
	private int additionTimePre;
	
	/** 遅刻・早退を控除しない */
	private int notDeductLateleavePre;
	
	/** 通常、変形の所定超過時 */
	private int deformatExcValuePre;
	
	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTimeWork;
	
	/** 実働のみで計算する */
	private int calcActualOperationWork;
	
	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCareWork;
	
	/** 遅刻・早退を控除しない */
	private int notDeductLateleaveWork;
	
	/** 加算する */
	private int additionTimeWork;
	
	/*B5_22*/
	/*就業時間帯毎の設定を可能とする*/
	private int enableSetPerWorkHour1;
	
	/*B5_23*/
	/*就業時間帯毎の設定を可能とする*/
	private int enableSetPerWorkHour2;
}
