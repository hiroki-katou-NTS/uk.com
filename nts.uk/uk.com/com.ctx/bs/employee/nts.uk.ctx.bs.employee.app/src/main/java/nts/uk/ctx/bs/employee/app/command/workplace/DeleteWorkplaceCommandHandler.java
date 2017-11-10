/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace;

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
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteWorkplaceCommandHandler.
 */
@Stateless
@Transactional
public class DeleteWorkplaceCommandHandler extends CommandHandler<DeleteWorkplaceCommand> {

    /** The wkp repo. */
    @Inject
    private WorkplaceRepository wkpRepo;
    
    /** The wkp service. */
    @Inject
    private WorkplaceService wkpService;
    
    /** The wkp config info repo. */
    @Inject
    private WorkplaceConfigInfoRepository wkpConfigInfoRepo;
    
    /* (non-Javadoc)
     * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<DeleteWorkplaceCommand> context) {
        String companyId = AppContexts.user().companyId();
        DeleteWorkplaceCommand command = context.getCommand();
        
        Optional<Workplace> optionalWkp = this.wkpRepo.findByWorkplaceId(companyId, command.getWkpIdSelected());
        if (!optionalWkp.isPresent()) {
            throw new RuntimeException(String.format("Workplace %s not existed.", command.getWkpIdSelected()));
        }
        Workplace workplace = optionalWkp.get();
        GeneralDate startDWkpHistLatest = workplace.getWkpHistoryLatest().span().start();
        
        if (command.getStartDWkpConfigInfo().equals(startDWkpHistLatest)) {
            // remove workplace history and workplace infor
            this.wkpRepo.removeWkpHistory(companyId, command.getWkpIdSelected(),
                    workplace.getWkpHistoryLatest().identifier());
        } else {
            if (command.getStartDWkpConfigInfo().before(startDWkpHistLatest)) {
                List<String> lstHistIdRemove = this.findHistory(workplace, command.getStartDWkpConfigInfo());
                // remove workplace after start date of workplace config history.
                lstHistIdRemove.forEach(historyId -> {
                    // remove workplace history and workplace infor
                    this.wkpRepo.removeWkpHistory(companyId, command.getWkpIdSelected(), historyId);
                });
            }
            int dayOfAgo = -1;
            // update end date of workplace history latest
            this.wkpService.updatePreviousHistory(companyId, workplace.getWkpHistoryLatest().identifier(),
                    command.getStartDWkpConfigInfo().addDays(dayOfAgo));
        }
        
        // delete workplace hierarchy that workplace is selected.
        this.wkpConfigInfoRepo.removeWkpHierarchy(companyId, command.getHistoryIdWkpConfigInfo(),
                command.getWkpIdSelected());
    }

    /**
     * Find history.
     *
     * @param workplace the workplace
     * @param startDWkpConfigInfo the start D wkp config info
     * @return the list
     */
    private List<String> findHistory(Workplace workplace, GeneralDate startDWkpConfigInfo) {
        List<String> lstHistoryId = workplace.items().stream()
                .filter(wkpHistory -> startDWkpConfigInfo.before(wkpHistory.span().start()))
                .map(wkpHistory -> wkpHistory.identifier())
                .collect(Collectors.toList());
        // delete object
        workplace.items().removeIf(wkpHistory -> lstHistoryId.contains(wkpHistory.identifier()));
        
        return lstHistoryId;
    }
    
}
