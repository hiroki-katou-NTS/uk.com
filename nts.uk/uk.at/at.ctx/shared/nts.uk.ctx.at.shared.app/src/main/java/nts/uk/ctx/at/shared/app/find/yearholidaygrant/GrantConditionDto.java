package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantCondition;

/**
 * 
 * @author TanLV
 *
 */
@Value
public class GrantConditionDto {
	/* 会社ID */
	private String companyId;
	
	/* 年休付与テーブル設定コード */
	private String yearHolidayCode;
	
	/* 条件NO */
	private int conditionNo;
	
	/* 条件値 */
	private Double conditionValue;
	
	/* 条件利用区分 */
	private int useConditionAtr;
	
	/** 未設定 **/
	private boolean hadSet;
	
	public static GrantConditionDto fromDomain(GrantCondition domain){
		return new GrantConditionDto(
			domain.getCompanyId(),
			domain.getYearHolidayCode().v(),
			domain.getConditionNo(),
			domain.getConditionValue() != null ? domain.getConditionValue().v() : null,
			domain.getUseConditionAtr().value,
			domain.isHadSet()
		);
	}
}
