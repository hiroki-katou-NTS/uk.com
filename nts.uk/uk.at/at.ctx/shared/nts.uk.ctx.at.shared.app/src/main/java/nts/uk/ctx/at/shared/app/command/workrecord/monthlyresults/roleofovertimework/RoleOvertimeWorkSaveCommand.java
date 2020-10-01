package nts.uk.ctx.at.shared.app.command.workrecord.monthlyresults.roleofovertimework;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkEnum;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkGetMemento;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new role overtime work save command.
 */
@NoArgsConstructor
public class RoleOvertimeWorkSaveCommand implements RoleOvertimeWorkGetMemento{
	
	/** The overtime fr no. */
	private int overtimeFrNo;

	/** The role OT work enum. */
	private int roleOTWorkEnum;

	/**
	 * Instantiates a new role overtime work save command.
	 *
	 * @param overtimeFrNo the overtime fr no
	 * @param roleOTWorkEnum the role OT work enum
	 */
	public RoleOvertimeWorkSaveCommand(int overtimeFrNo, int roleOTWorkEnum) {
		super();
		this.overtimeFrNo = overtimeFrNo;
		this.roleOTWorkEnum = roleOTWorkEnum;
	}

	@Override
	public String getCompanyID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OverTimeFrameNo getOvertimeFrNo() {
		return new OverTimeFrameNo(overtimeFrNo);
	}

	@Override
	public RoleOvertimeWorkEnum getRoleOTWorkEnum() {
		return RoleOvertimeWorkEnum.valueOf(roleOTWorkEnum);
	}

}
