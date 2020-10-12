package nts.uk.ctx.at.shared.infra.repository.workrecord.monthlyresults.roleopenperiod;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.BreakoutFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodEnum;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodGetMemento;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleopenperiod.KrcstRoleOfOpenPeriod;

/**
 * The Class JpaRoleOfOpenPeriodGetMemento.
 */
public class JpaRoleOfOpenPeriodGetMemento implements RoleOfOpenPeriodGetMemento{

	/** The krcst role of open period. */
	private KrcstRoleOfOpenPeriod krcstRoleOfOpenPeriod;
	
	/**
	 * Instantiates a new jpa role of open period get memento.
	 *
	 * @param entity the entity
	 */
	public JpaRoleOfOpenPeriodGetMemento(KrcstRoleOfOpenPeriod entity) {
		this.krcstRoleOfOpenPeriod = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return this.krcstRoleOfOpenPeriod.getKrcstRoleOfOpenPeriodPK().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodGetMemento#getBreakoutFrNo()
	 */
	@Override
	public BreakoutFrameNo getBreakoutFrNo() {
		return new BreakoutFrameNo(this.krcstRoleOfOpenPeriod.getKrcstRoleOfOpenPeriodPK().getBreakoutOffFrNo().intValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodGetMemento#getRoleOfOpenPeriodEnum()
	 */
	@Override
	public RoleOfOpenPeriodEnum getRoleOfOpenPeriodEnum() {
		return RoleOfOpenPeriodEnum.valueOf(this.krcstRoleOfOpenPeriod.getRoleOfOpenPeriod().intValue());
	}

}
