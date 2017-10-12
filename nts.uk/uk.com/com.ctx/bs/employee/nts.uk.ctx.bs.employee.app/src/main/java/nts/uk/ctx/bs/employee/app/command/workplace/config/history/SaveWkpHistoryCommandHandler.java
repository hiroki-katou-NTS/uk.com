/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.config.history;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.service.WorkplaceService;
import nts.uk.ctx.bs.employee.dom.workplace.util.HistoryUtil;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWkpHistoryCommandHandler.
 */
@Stateless
@Transactional
public class SaveWkpHistoryCommandHandler extends CommandHandler<SaveWkpHistoryCommand> {

    /** The workplace repository. */
    @Inject
    private WorkplaceRepository workplaceRepository;

    /** The workplace info repository. */
    @Inject
    private WorkplaceInfoRepository workplaceInfoRepository;

    /** The wkp service. */
    @Inject
    private WorkplaceService wkpService;

    /** The Constant MIN_SIZE. */
    private static final Integer MIN_SIZE = 1;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
     * .CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<SaveWkpHistoryCommand> context) {
        SaveWkpHistoryCommand command = context.getCommand();
        String companyId = AppContexts.user().companyId();

        Optional<Workplace> optionalWorkplace = workplaceRepository.findByWorkplaceId(companyId,
                command.getWorkplaceId());
        if (!optionalWorkplace.isPresent()) {
            throw new RuntimeException(String.format("Didn't existed workplace %s .", command.getWorkplaceId()));
        }
        Workplace workplace = optionalWorkplace.get();

        if (command.getIsAddMode()) {
            this.addkpHistory(companyId, command, workplace);
        } else {
            this.updateWkpHistory(companyId, command, workplace);
        }
    }

    /**
     * Addkp history.
     *
     * @param companyId the company id
     * @param command the command
     * @param latestWkpHistory the latest wkp history
     */
    private void addkpHistory(String companyId, SaveWkpHistoryCommand command, Workplace workplace) {
        Workplace wkpCommand = command.toDomain(companyId);
        // add workplace
        this.workplaceRepository.add(wkpCommand);
        
        if (CollectionUtil.isEmpty(workplace.getWorkplaceHistory())) {
            return;
        }
        
        WorkplaceHistory latestWkpHistory = workplace.getWkpHistoryLatest();
        
        // validate add new history
        HistoryUtil.validStartDate(Boolean.TRUE, latestWkpHistory.getPeriod().start(),
                command.getWorkplaceHistory().getPeriod().getStartDate());
        
        // Update endDate previous history
        int dayOfAgo = -1;
        this.wkpService.updatePreviousHistory(companyId, latestWkpHistory.getHistoryId().v(),
                command.getWorkplaceHistory().getPeriod().getStartDate().addDays(dayOfAgo));

        // copy workplace info by historyId
        this.copyWorkplaceInfo(companyId, latestWkpHistory.getHistoryId().v(), wkpCommand);
    }

    /**
     * Update wkp history.
     *
     * @param companyId the company id
     * @param command the command
     * @param workplaceDatabase the workplace database
     */
    private void updateWkpHistory(String companyId, SaveWkpHistoryCommand command, Workplace workplaceDatabase) {
        this.workplaceRepository.update(command.toDomain(companyId));
        
        if (workplaceDatabase.getWorkplaceHistory().size() == MIN_SIZE) {
            return;
        }
        
        int idxPrevLatestHist = 1;
        WorkplaceHistory prevWkpLatest = workplaceDatabase.getWorkplaceHistory().get(idxPrevLatestHist);
        
        // validate new start date of history
        HistoryUtil.validStartDate(workplaceDatabase.getWkpHistoryLatest().getPeriod(), prevWkpLatest.getPeriod(),
                command.getWorkplaceHistory().getPeriod().getStartDate());

        // set end date of previous history (below of history latest)
        int dayOfAgo = -1;
        // update previous history latest
        this.wkpService.updatePreviousHistory(companyId, prevWkpLatest.getHistoryId().v(),
                command.getWorkplaceHistory().getPeriod().getStartDate().addDays(dayOfAgo));
    }

    /**
     * Copy workplace info.
     *
     * @param companyId the company id
     * @param latestHistId the latest hist id
     * @param wkpCommand the wkp command
     */
    private void copyWorkplaceInfo(String companyId, String latestHistId, Workplace wkpCommand) {
        Optional<WorkplaceInfo> optionalWkpInfo = workplaceInfoRepository.find(companyId,
                wkpCommand.getWorkplaceId().v(), latestHistId);
        if (optionalWkpInfo.isPresent()) {
            WorkplaceInfo wkpInfo = optionalWkpInfo.get()
                    .cloneWithHistId(wkpCommand.getWkpHistoryLatest().getHistoryId().v());
            workplaceInfoRepository.update(wkpInfo);
        }
    }
}
