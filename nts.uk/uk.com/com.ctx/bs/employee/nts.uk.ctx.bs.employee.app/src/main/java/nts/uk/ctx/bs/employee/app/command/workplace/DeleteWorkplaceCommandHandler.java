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
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.service.WorkplaceService;
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
    
    /** The wkp info repo. */
    @Inject
    private WorkplaceInfoRepository wkpInfoRepo;
    
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
        GeneralDate startDWkpHistLatest = workplace.getWkpHistoryLatest().getPeriod().start();
        
        if (command.getStartDWkpConfigInfo().equals(startDWkpHistLatest)) {
            // delete workplace infor
            this.wkpInfoRepo.remove(companyId, command.getWkpIdSelected(),
                    workplace.getWkpHistoryLatest().getHistoryId());
            
            // delete workplace
//            this.wkpRepo.removeWkpHistory(companyId, command.getWkpIdSelected(),
//                    workplace.getWkpHistoryLatest().getHistoryId());
        } else {
            if (command.getStartDWkpConfigInfo().before(startDWkpHistLatest)) {
                List<String> lstHistIdRemove = this.findHistory(workplace, command.getStartDWkpConfigInfo());
                // remove workplace after start date of workplace config history.
                lstHistIdRemove.forEach(historyId -> {
                    // delete workplace infor
                    this.wkpInfoRepo.remove(companyId, command.getWkpIdSelected(), historyId);
                    
                    // delete workplace
//                    this.wkpRepo.removeWkpHistory(companyId, command.getWkpIdSelected(), historyId);
                });
            }
            int dayOfAgo = -1;
            // update end date of workplace history latest
            this.wkpService.updatePreviousHistory(companyId, workplace.getWkpHistoryLatest().getHistoryId(),
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
        List<String> lstHistoryId = workplace.getWorkplaceHistory().stream()
                .filter(wkpHistory -> startDWkpConfigInfo.before(wkpHistory.getPeriod().start()))
                .map(wkpHistory -> wkpHistory.getHistoryId())
                .collect(Collectors.toList());
        // delete object
        workplace.getWorkplaceHistory().removeIf(wkpHistory -> lstHistoryId.contains(wkpHistory.getHistoryId()));
        
        return lstHistoryId;
    }
    
}
