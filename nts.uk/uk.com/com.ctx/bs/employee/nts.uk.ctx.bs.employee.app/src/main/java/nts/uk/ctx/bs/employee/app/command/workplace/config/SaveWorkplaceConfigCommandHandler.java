/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.config;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistory;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.service.WkpConfigInfoService;
import nts.uk.ctx.bs.employee.dom.workplace.config.service.WkpConfigService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWorkplaceConfigCommandHandler.
 */
@Stateless
@Transactional
public class SaveWorkplaceConfigCommandHandler extends CommandHandler<WorkplaceConfigCommand> {

    /** The workplace config repository. */
    @Inject
    private WorkplaceConfigRepository workplaceConfigRepository;

    /** The wkp config info service. */
    @Inject
    private WkpConfigInfoService wkpConfigInfoService;

    /** The wkp config service. */
    @Inject
    private WkpConfigService wkpConfigService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
     * .CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<WorkplaceConfigCommand> context) {
        WorkplaceConfigCommand command = context.getCommand();
        String companyId = AppContexts.user().companyId();
        
        Optional<WorkplaceConfig> optional = workplaceConfigRepository.findLatestByCompanyId(companyId);
        // new mode
        if (!optional.isPresent()) {
            workplaceConfigRepository.add(command.toDomain(companyId));
            return;
        }
        WorkplaceConfigHistory latestWkpConfigHist = optional.get().getWkpConfigHistoryLatest();
        // add mode
        if (command.getIsAddMode()) {
            this.addHistory(companyId, command, latestWkpConfigHist);
        } else {
            this.updateHistory(companyId, command, latestWkpConfigHist);
        }
        
    }

    /**
     * Adds the history.
     *
     * @param companyId the company id
     * @param command the command
     * @param latestWkpConfigHist the latest wkp config hist
     */
    private void addHistory(String companyId, WorkplaceConfigCommand command, WorkplaceConfigHistory latestWkpConfigHist) {
        // convert command to domain
        WorkplaceConfig workplaceConfig = command.toDomain(companyId);

        // get start date of add new hist
        GeneralDate newStartDateHist = command.getWkpConfigHistory().getPeriod().getStartDate();
        
        // validate add hist and return first histId
        if (this.isInValidStartDateHistory(latestWkpConfigHist.getPeriod().getStartDate(), newStartDateHist)) {
            throw new BusinessException("Msg_102");
        }

        // add workplace config and return add new historyId
        workplaceConfigRepository.add(workplaceConfig);
        
        String latestHistIdCurrent = latestWkpConfigHist.getHistoryId();
        
        String newHistoryId = workplaceConfig.getWkpConfigHistoryLatest().getHistoryId();

        // copy latest ConfigInfoHist from fisrtHistId
        wkpConfigInfoService.copyWkpConfigInfoHist(companyId, latestHistIdCurrent, newHistoryId);

        // previous day
        int dayOfAgo = -1;
        // update previous history
        wkpConfigService.updatePrevHistory(companyId, latestHistIdCurrent, newStartDateHist.addDays(dayOfAgo));
    }
    
    /**
     * Update history.
     *
     * @param companyId the company id
     * @param command the command
     */
    private void updateHistory(String companyId, WorkplaceConfigCommand command, WorkplaceConfigHistory latestWkpConfigHist) {
        // get start date of add new hist
        GeneralDate newStartDateHist = command.getWkpConfigHistory().getPeriod().getStartDate();
        
        // validate add hist and return first histId
        if (this.isInValidStartDateHistory(latestWkpConfigHist.getPeriod().getStartDate(), newStartDateHist)) {
            throw new BusinessException("Msg_127");
        }
        // update workplace by new start date history if need
        this.wkpConfigService.updateWkpHistoryIfNeed(companyId, latestWkpConfigHist, newStartDateHist);
        
        // update history
        this.workplaceConfigRepository.update(command.toDomain(companyId), newStartDateHist);
    }

    /**
     * Checks if is in valid start date history.
     *
     * @param beforeDate the before date
     * @param afterDate the after date
     * @return true, if is in valid start date history
     */
    private boolean isInValidStartDateHistory(GeneralDate beforeDate, GeneralDate afterDate) {
        return beforeDate.after(afterDate);
    }
}
