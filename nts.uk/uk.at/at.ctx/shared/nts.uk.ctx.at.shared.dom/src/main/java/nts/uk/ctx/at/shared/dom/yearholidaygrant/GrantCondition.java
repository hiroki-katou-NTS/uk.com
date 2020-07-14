package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import java.io.Serializable;

import lombok.Getter;

/**
 * 付与条件
 * 
 * @author TanLV
 *
 */

@Getter
public class GrantCondition implements Serializable{
	/* 会社ID */
	private String companyId;
	
	/* 年休付与テーブル設定コード */
	private YearHolidayCode yearHolidayCode;
	
	/* 条件NO */
	private int conditionNo;
	
	/* 条件値 */
	private ConditionValue conditionValue;
	
	/* 条件利用区分 */
	private UseConditionAtr useConditionAtr;
	
	private boolean hadSet;

	public GrantCondition(String companyId, YearHolidayCode yearHolidayCode, int conditionNo, ConditionValue conditionValue,
			UseConditionAtr useConditionAtr, boolean hadSet) {
		this.companyId = companyId;
		this.yearHolidayCode = yearHolidayCode;
		this.conditionNo = conditionNo;
		this.conditionValue = conditionValue;
		this.useConditionAtr = useConditionAtr;
		this.hadSet = hadSet;
	}	
}
