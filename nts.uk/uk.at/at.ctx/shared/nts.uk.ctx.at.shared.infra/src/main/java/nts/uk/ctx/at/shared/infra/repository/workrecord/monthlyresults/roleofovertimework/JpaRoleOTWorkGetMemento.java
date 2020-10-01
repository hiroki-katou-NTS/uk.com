package nts.uk.ctx.at.shared.infra.repository.workrecord.monthlyresults.roleofovertimework;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkEnum;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkGetMemento;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleofovertimework.KrcstRoleOTWork;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaRoleOTWorkGetMemento.
 */

public class JpaRoleOTWorkGetMemento implements RoleOvertimeWorkGetMemento{

	/** The krcst role OT work. */
	private KrcstRoleOTWork krcstRoleOTWork;
	
	/**
	 * Instantiates a new jpa role OT work get memento.
	 *
	 * @param entity the entity
	 */
	public JpaRoleOTWorkGetMemento(KrcstRoleOTWork entity) {
		 this.krcstRoleOTWork = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		return this.krcstRoleOTWork.getKrcstRoleOTWorkPK().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkGetMemento#getOvertimeFrNo()
	 */
	@Override
	public OverTimeFrameNo getOvertimeFrNo() {
		return new OverTimeFrameNo(this.krcstRoleOTWork.getKrcstRoleOTWorkPK().getOvertimeFrNo().intValue());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkGetMemento#getRoleOTWorkEnum()
	 */
	@Override
	public RoleOvertimeWorkEnum getRoleOTWorkEnum() {
		return RoleOvertimeWorkEnum.valueOf(this.krcstRoleOTWork.getRoleOTWork().intValue());
	}
}
