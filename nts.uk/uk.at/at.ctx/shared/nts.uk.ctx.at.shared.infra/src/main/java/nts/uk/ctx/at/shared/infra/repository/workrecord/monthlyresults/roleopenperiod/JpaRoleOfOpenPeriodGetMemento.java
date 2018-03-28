package nts.uk.ctx.at.shared.infra.repository.workrecord.monthlyresults.roleopenperiod;

import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.BreakoutFrameNo;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodEnum;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodGetMemento;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleopenperiod.KrcstRoleOfOpenPeriod;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaRoleOfOpenPeriodGetMemento.
 */
public class JpaRoleOfOpenPeriodGetMemento implements RoleOfOpenPeriodGetMemento{

	private KrcstRoleOfOpenPeriod krcstRoleOfOpenPeriod;
	
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
