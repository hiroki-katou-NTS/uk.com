package nts.uk.ctx.at.shared.app.command.workrecord.monthlyresults.roleopenperiod;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.find.workdayoff.frame.WorkdayoffFrameFindDto;
import nts.uk.ctx.at.shared.app.find.workdayoff.frame.WorkdayoffFrameFinder;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RoleOfOpenPeriodSaveCommandHandler.
 */
@Stateless
public class RoleOfOpenPeriodSaveCommandHandler extends CommandHandler<List<RoleOfOpenPeriodSaveCommand>>{

	/** The repository. */
	@Inject
	private RoleOfOpenPeriodRepository repository;

	/**  The finder of CMM007. */
	@Inject
	private WorkdayoffFrameFinder finderWorkdayOff;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<List<RoleOfOpenPeriodSaveCommand>> context) {
		String companyId = AppContexts.user().companyId();
		
		List<RoleOfOpenPeriodSaveCommand> lstCommand = context.getCommand();
		
		List<RoleOfOpenPeriod> lstDomain = repository.findByCID(companyId);
		
		// add
		if (lstDomain.isEmpty()) {
			lstDomain = lstCommand.stream().map(e -> {
				RoleOfOpenPeriod roleOfOpenPeriod = new RoleOfOpenPeriod(e);
				return roleOfOpenPeriod;
			}).collect(Collectors.toList());
			
			this.repository.add(lstDomain);
		}
		// udpate
		else {
			List<WorkdayoffFrameFindDto> lstWorkdayOff = finderWorkdayOff.findAll();
			List<RoleOfOpenPeriod> lstObjUpdate = lstWorkdayOff.stream().filter(e -> e.getUseAtr() == USE)
									.map(e -> {
										int pos = e.getWorkdayoffFrNo();
										RoleOfOpenPeriod roleOfOpenPeriod = new RoleOfOpenPeriod(lstCommand.get(pos-1));
										return roleOfOpenPeriod;
									}).collect(Collectors.toList());
			this.repository.update(lstObjUpdate);
		}
		
	}
	
	private static final int USE = 1;

}
