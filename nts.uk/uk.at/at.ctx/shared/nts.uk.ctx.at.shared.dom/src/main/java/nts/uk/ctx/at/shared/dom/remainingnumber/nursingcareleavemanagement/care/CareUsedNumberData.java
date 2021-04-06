package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care;

import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;

/**
 * 介護休暇使用数データ
 * @author masaaki_jinno
 *
 */
public class CareUsedNumberData extends ChildCareNurseUsedNumber implements DomainAggregate {
	/** 社員ID */
	private String employeeId;

	/**
	 * コンストラクタ
	 */
	public CareUsedNumberData(String employeeId){
		super();
		this.employeeId = employeeId;
	}

}
