package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RoleOfOpenPeriod.
 */
// 休出枠の役割
@Getter
public class RoleOfOpenPeriod extends AggregateRoot{

	/** The company ID. */
	// 会社ID
	private String companyID;
	
	
	/** The breakout fr no. */
	// NO
	private BreakoutFrameNo breakoutFrNo;

	// 役割	
	/** The role of open period enum. */
	private RoleOfOpenPeriodEnum roleOfOpenPeriodEnum;
	
	/**
	 * Instantiates a new role of open period.
	 *
	 * @param memento the memento
	 */
	public RoleOfOpenPeriod(RoleOfOpenPeriodGetMemento memento) {
		this.companyID = memento.getCompanyID();
		this.breakoutFrNo = memento.getBreakoutFrNo();
		this.roleOfOpenPeriodEnum = memento.getRoleOfOpenPeriodEnum();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(RoleOfOpenPeriodSetMemento memento) {
		memento.setCompanyID(this.companyID == null ? AppContexts.user().companyId() : this.companyID);
		memento.setBreakoutFrNo(this.breakoutFrNo.v());
		memento.setRoleOfOpenPeriodEnum(this.roleOfOpenPeriodEnum.value);
	}
}
