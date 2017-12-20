/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.history;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.command.workplace.service.WorkplaceService;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.policy.HistoryPolicy;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteWkpHistoryCommandHandler.
 */
@Stateless
@Transactional
public class DeleteWkpHistoryCommandHandler extends CommandHandler<DeleteWkpHistoryCommand> {

    /** The wkp repo. */
    @Inject
    private WorkplaceRepository wkpRepo;
    
    /** The wkp service. */
    @Inject
    private WorkplaceService wkpService;
    
    /** The Constant DATE_FORMAT. */
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    
    /** The Constant MAX_DATE. */
    private static final String MAX_DATE = "9999/12/31";
    
    /* (non-Javadoc)
     * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<DeleteWkpHistoryCommand> context) {
        String companyId = AppContexts.user().companyId();
        DeleteWkpHistoryCommand command = context.getCommand();
        
        Optional<Workplace> optionalWorkplace = this.wkpRepo.findByWorkplaceId(companyId, command.getWorkplaceId());
        if (!optionalWorkplace.isPresent()) {
            throw new RuntimeException(String.format("Didn't exist workplce %s.", command.getWorkplaceId()));
        }
        Workplace workplace = optionalWorkplace.get();
        
        // valid history latest
        List<String> lstHistoryId = workplace.items().stream()
                .map(item -> item.identifier())
                .collect(Collectors.toList());
        HistoryPolicy.validHistoryLatest(lstHistoryId, command.getHistoryId());
        
        // remove workplace history (remove parent) and workplace infor
        this.wkpRepo.removeWkpHistory(companyId, command.getWorkplaceId(), command.getHistoryId());
        
        // update end date of previous workplace history
        int idxPrevHistLatest = 1;
        this.wkpService.updatePreviousHistory(companyId, lstHistoryId.get(idxPrevHistLatest),
                GeneralDate.fromString(MAX_DATE, DATE_FORMAT));
    }

}
