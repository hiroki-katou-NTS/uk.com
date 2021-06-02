package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;

/**
 * 子の看護休暇使用数データ
 */
@Getter
@Setter
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

	public ChildCareUsedNumberData(String employeeId, ChildCareNurseUsedNumber c){
		super(c);
		this.employeeId = employeeId;
	}


}
