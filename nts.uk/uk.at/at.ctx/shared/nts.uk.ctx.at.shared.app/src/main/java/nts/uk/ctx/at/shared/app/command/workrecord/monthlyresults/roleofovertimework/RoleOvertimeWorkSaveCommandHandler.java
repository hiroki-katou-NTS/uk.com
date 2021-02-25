package nts.uk.ctx.at.shared.app.command.workrecord.monthlyresults.roleofovertimework;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.find.ot.frame.OvertimeWorkFrameFindDto;
import nts.uk.ctx.at.shared.app.find.ot.frame.OvertimeWorkFrameFinder;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkRepository;
//import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriod;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RoleOvertimeWorkSaveCommandHandler.
 */
@Stateless
public class RoleOvertimeWorkSaveCommandHandler extends CommandHandler<List<RoleOvertimeWorkSaveCommand>>{

	/** The repository. */
	@Inject
	private RoleOvertimeWorkRepository repository;
	
	@Inject
	private OvertimeWorkFrameFinder finder;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<List<RoleOvertimeWorkSaveCommand>> context) {
		String companyId = AppContexts.user().companyId();
		
		// get dto from client
		List<RoleOvertimeWorkSaveCommand> lstCommand = context.getCommand();
		
		// get domain from DB
		List<RoleOvertimeWork> lstDomain = repository.findByCID(companyId);
		
		// add
		if (lstDomain.isEmpty()) {
			lstDomain = lstCommand.stream().map(e -> {
				RoleOvertimeWork roleOvertimeWork = new RoleOvertimeWork(e);
				return roleOvertimeWork;
			}).collect(Collectors.toList());
			
			this.repository.add(lstDomain);
		}
		// udpate
		else {
			List<OvertimeWorkFrameFindDto> lstOTWork = finder.findAll();
			List<RoleOvertimeWork> lstObjUpdate = lstOTWork.stream().filter(e -> e.getUseAtr() == USE)
									.map(e -> {
										int pos = e.getOvertimeWorkFrNo();
										RoleOvertimeWork roleOvertimeWork = new RoleOvertimeWork(lstCommand.get(pos-1));
										return roleOvertimeWork;
									}).collect(Collectors.toList());
			this.repository.update(lstObjUpdate);
		}
	}
	
	private static final int USE = 1;
}
