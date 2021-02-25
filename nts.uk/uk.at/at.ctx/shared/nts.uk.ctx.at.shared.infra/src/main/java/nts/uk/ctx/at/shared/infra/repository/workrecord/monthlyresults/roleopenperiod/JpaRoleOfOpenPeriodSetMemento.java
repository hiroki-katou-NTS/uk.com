package nts.uk.ctx.at.shared.infra.repository.workrecord.monthlyresults.roleopenperiod;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodSetMemento;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleopenperiod.KrcstRoleOfOpenPeriod;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleopenperiod.KrcstRoleOfOpenPeriodPK;

/**
 * The Class JpaRoleOfOpenPeriodSetMemento.
 */
public class JpaRoleOfOpenPeriodSetMemento implements RoleOfOpenPeriodSetMemento{

	/** The krcst role of open period. */
	private KrcstRoleOfOpenPeriod krcstRoleOfOpenPeriod; 
	
	/**
	 * Instantiates a new jpa role of open period set memento.
	 *
	 * @param entity the entity
	 */
	public JpaRoleOfOpenPeriodSetMemento(KrcstRoleOfOpenPeriod entity) {
		if (entity.krcstRoleOfOpenPeriodPK == null) {
			KrcstRoleOfOpenPeriodPK krcstRoleOfOpenPeriodPK = new KrcstRoleOfOpenPeriodPK();
			entity.krcstRoleOfOpenPeriodPK = krcstRoleOfOpenPeriodPK;
		}
		this.krcstRoleOfOpenPeriod = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		this.krcstRoleOfOpenPeriod.getKrcstRoleOfOpenPeriodPK().setCid(companyID);
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodSetMemento#getBreakoutFrNo(int)
	 */
	@Override
	public void setBreakoutFrNo(int breakoutFrNo) {
		 this.krcstRoleOfOpenPeriod.getKrcstRoleOfOpenPeriodPK().setBreakoutOffFrNo(new BigDecimal(breakoutFrNo));
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodSetMemento#getRoleOfOpenPeriodEnum(int)
	 */
	@Override
	public void setRoleOfOpenPeriodEnum(int roleOfOpenPeriodEnum) {
		this.krcstRoleOfOpenPeriod.setRoleOfOpenPeriod(new BigDecimal(roleOfOpenPeriodEnum));
	}

}
