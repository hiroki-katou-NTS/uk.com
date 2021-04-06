package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare;

import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 子の看護休暇使用数データ
 * @author yuri_tamakoshi
 */
public class ChildCareUsedNumberData extends ChildCareNurseUsedNumber implements DomainAggregate {
	/** 社員ID */
	private String employeeId;

	/**
	 * コンストラクタ
	 */
	public ChildCareUsedNumberData(String employeeId){
		super();
		this.employeeId = employeeId;
	}

}
