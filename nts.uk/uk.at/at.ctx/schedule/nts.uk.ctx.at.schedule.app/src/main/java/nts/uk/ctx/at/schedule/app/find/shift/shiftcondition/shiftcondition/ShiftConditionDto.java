package nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftCondition;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftConditionDto {
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * カテゴリNO
	 */
	private int categoryNo;
	/**
	 * 条件NO
	 */
	private int conditionNo;
	/**
	 * 条件エラーメッセージ
	 */
	private String conditionErrorMessage;
	/**
	 * 条件名称
	 */
	private String conditionName;
	/**
	 * 条件詳細NO
	 */
	private int conditionDetailsNo;
	
	

	public static ShiftConditionDto fromDomain(ShiftCondition domain) {
		return new ShiftConditionDto(domain.getCompanyId(), domain.getCategoryNo(), domain.getConditionNo(),
				domain.getConditionErrorMessage().v(), domain.getConditionName().v(), domain.getConditionDetailsNo());
	}



	public ShiftConditionDto(int conditionNo, String conditionName) {
		super();
		this.conditionNo = conditionNo;
		this.conditionName = conditionName;
	}
}
