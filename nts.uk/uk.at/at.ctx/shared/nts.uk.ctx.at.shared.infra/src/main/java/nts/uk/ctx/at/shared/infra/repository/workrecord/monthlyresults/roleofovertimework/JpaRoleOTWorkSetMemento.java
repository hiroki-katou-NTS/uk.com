package nts.uk.ctx.at.shared.infra.repository.workrecord.monthlyresults.roleofovertimework;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkEnum;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkSetMemento;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleofovertimework.KrcstRoleOTWork;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthlyresults.roleofovertimework.KrcstRoleOTWorkPK;
//import nts.uk.shr.com.context.AppContexts;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaRoleOTWorkSetMemento.
 */
public class JpaRoleOTWorkSetMemento implements RoleOvertimeWorkSetMemento{

	private KrcstRoleOTWork krcstRoleOTWork;
	
	JpaRoleOTWorkSetMemento(KrcstRoleOTWork entity) {
		if (entity.krcstRoleOTWorkPK == null) {
			KrcstRoleOTWorkPK krcstRoleOTWorkPK = new KrcstRoleOTWorkPK();
			entity.krcstRoleOTWorkPK = krcstRoleOTWorkPK;
		}
		
		this.krcstRoleOTWork = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		this.krcstRoleOTWork.getKrcstRoleOTWorkPK().setCid(companyID);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkSetMemento#setOvertimeFrNo(nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.OverTimeFrameNo)
	 */
	@Override
	public void setOvertimeFrNo(OverTimeFrameNo overTimeFrameNo) {
		this.krcstRoleOTWork.getKrcstRoleOTWorkPK().setOvertimeFrNo(new BigDecimal(overTimeFrameNo.v()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkSetMemento#setRoleOTWorkEnum(nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkEnum)
	 */
	@Override
	public void setRoleOTWorkEnum(RoleOvertimeWorkEnum roleOvertimeWorkEnum) {
		this.krcstRoleOTWork.setRoleOTWork(new BigDecimal(roleOvertimeWorkEnum.value));
	}

	

}
