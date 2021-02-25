package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RoleOvertimeWork.
 */
// 残業枠の役割
@Getter
public class RoleOvertimeWork extends AggregateRoot{
	/** The company ID. */
	// 会社ID
	private String companyID;
	
	
	/** The overtime fr no. */
	// NO
	private OverTimeFrameNo overtimeFrNo;

	/** The role. */
	// 役割
	private RoleOvertimeWorkEnum roleOTWorkEnum;
	
	public RoleOvertimeWork(RoleOvertimeWorkGetMemento memento) {
		this.companyID = memento.getCompanyID();
		this.overtimeFrNo = memento.getOvertimeFrNo();
		this.roleOTWorkEnum = memento.getRoleOTWorkEnum();
	}
	
	public void saveToMemento(RoleOvertimeWorkSetMemento memento) {
		memento.setCompanyID(this.companyID == null ? AppContexts.user().companyId() : this.companyID);
		memento.setOvertimeFrNo(this.overtimeFrNo);
		memento.setRoleOTWorkEnum(this.roleOTWorkEnum);
	}
}
