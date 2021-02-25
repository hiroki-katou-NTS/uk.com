package nts.uk.ctx.at.shared.app.command.workrecord.monthlyresults.roleopenperiod;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.BreakoutFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodEnum;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodGetMemento;

/**
 * Instantiates a new role of open period save command.
 */
@NoArgsConstructor
public class RoleOfOpenPeriodSaveCommand implements RoleOfOpenPeriodGetMemento{
	
	/** The breakout fr no. */
	private int breakoutFrNo;
	
	/** The role of open period enum. */
	private int roleOfOpenPeriodEnum;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodGetMemento#getCompanyID()
	 */
	public String getCompanyID() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodGetMemento#getBreakoutFrNo()
	 */
	@Override
	public BreakoutFrameNo getBreakoutFrNo() {
		return new BreakoutFrameNo(this.breakoutFrNo);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodGetMemento#getRoleOfOpenPeriodEnum()
	 */
	@Override
	public RoleOfOpenPeriodEnum getRoleOfOpenPeriodEnum() {
		return RoleOfOpenPeriodEnum.valueOf(this.roleOfOpenPeriodEnum);
	}

	public RoleOfOpenPeriodSaveCommand(int breakoutFrNo, int roleOfOpenPeriodEnum) {
		super();
		this.breakoutFrNo = breakoutFrNo;
		this.roleOfOpenPeriodEnum = roleOfOpenPeriodEnum;
	}

}
