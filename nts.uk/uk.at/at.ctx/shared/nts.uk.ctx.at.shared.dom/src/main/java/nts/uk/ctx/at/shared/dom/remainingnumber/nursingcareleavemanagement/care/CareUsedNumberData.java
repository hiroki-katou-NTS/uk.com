package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;

/**
 * 介護休暇使用数データ
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
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

	/**
	 * コンストラクタ
	 */
	public CareUsedNumberData(String employeeId, ChildCareNurseUsedNumber c){
		super(c);
		this.employeeId = employeeId;
	}

}
