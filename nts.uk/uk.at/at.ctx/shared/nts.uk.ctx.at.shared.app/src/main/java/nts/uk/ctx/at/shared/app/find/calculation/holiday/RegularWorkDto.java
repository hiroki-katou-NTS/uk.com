package nts.uk.ctx.at.shared.app.find.calculation.holiday;
/**
 * @author phongtq
 * 通常勤務の加算設定
 */
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegularWorkDto {
	/** 会社ID */
	private String companyId = "dummy";
	
	/** 実働のみで計算する */
	private int calcActualOperationPre = 0;
	
	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTimePre = 0;
	
	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCarePre = 0;
	
	/** 加算する */
	private int additionTimePre = 0;
	
	/** 遅刻・早退を控除しない */
	private int notDeductLateleavePre = 0;
	
	/** 通常、変形の所定超過時 */
	private Integer deformatExcValuePre = null;
	
	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTimeWork = 0;
	
	/** 実働のみで計算する */
	private int calcActualOperationWork = 0;
	
	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCareWork = 0;
	
	/** 遅刻・早退を控除しない */
	private int notDeductLateleaveWork = 0;
	
	/** 加算する */
	private int additionTimeWork = 0;
	
	/*B5_22*/
	/*就業時間帯毎の設定を可能とする*/
	private int enableSetPerWorkHour1 = 0;
	
	/*B5_23*/
	/*就業時間帯毎の設定を可能とする*/
	private int enableSetPerWorkHour2 = 0;

	// B6_1
	// 割増計算方法を設定する
	private int useAtr = 0;
	
	public RegularWorkDto(String companyId) {
		this.companyId = companyId;
	}

}
