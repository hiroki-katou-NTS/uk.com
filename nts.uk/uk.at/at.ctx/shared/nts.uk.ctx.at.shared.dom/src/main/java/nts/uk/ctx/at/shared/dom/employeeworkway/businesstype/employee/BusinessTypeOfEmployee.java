package nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;

/**
 * 社員の勤務種別
 * 
 * @author Trung Tran
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessTypeOfEmployee extends AggregateRoot {
	/** 勤務種別コード */
	private BusinessTypeCode businessTypeCode;

	/** 履歴ID */
	private String historyId;

	/** 社員ID */
	private String sId;

	public static BusinessTypeOfEmployee createFromJavaType(String businessTypeCode, String historyId, String sId) {
		return new BusinessTypeOfEmployee(new BusinessTypeCode(businessTypeCode), historyId, sId);
	}
}
