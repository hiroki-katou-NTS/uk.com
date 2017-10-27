/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.app.command.workplace.dto.WorkplaceHierarchyDto;
import nts.uk.ctx.bs.employee.dom.workplace.CreateWorkpceType;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.service.WkpConfigInfoService;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWorkplaceCommandHandler.
 */
@Stateless
@Transactional
public class SaveWorkplaceCommandHandler extends CommandHandler<SaveWorkplaceCommand> {

    /** The wkp config info service. */
    @Inject
    private WkpConfigInfoService wkpConfigInfoService;
    
    /** The wkp config info repo. */
    @Inject
    private WorkplaceConfigInfoRepository wkpConfigInfoRepo;
    
    /** The wkp repo. */
    @Inject
    private WorkplaceRepository wkpRepo;
    
    /** The wkp info repo. */
    @Inject
    private WorkplaceInfoRepository wkpInfoRepo;
    
    /** The Constant HIERARCHY_ORIGIN. */
    private static final String HIERARCHY_ORIGIN = "001";
    
    /* (non-Javadoc)
     * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<SaveWorkplaceCommand> context) {
        String companyId = AppContexts.user().companyId();
        SaveWorkplaceCommand command = context.getCommand();
        
        if (command.getIsAddMode()) {
            this.addWorkplace(companyId, command);
        } else {
            this.wkpInfoRepo.update(command.getWkpInfor().toDomain(companyId,
                    command.getWkpIdSelected(), command.getWkpInfor().getHistoryId()));
            // TODO: register workplace hierarchy when drag & drop workplace in tree
        }
    }

    /**
     * Adds the workplace.
     *
     * @param companyId the company id
     * @param command the command
     */
    private void addWorkplace(String companyId, SaveWorkplaceCommand command) {
        
        // valid existed workplace code
        if (this.wkpInfoRepo.isExistedWkpCd(companyId, command.getWkpInfor().getWorkplaceCode())) {
            throw new BusinessException("Msg_3");
        }
        
        // insert domain workplace
        Workplace newWorkplace = command.getWorkplace().toDomain(companyId);
        this.wkpRepo.add(newWorkplace);
        
        // insert domain workplace infor
        WorkplaceInfo workplaceInfo = command.getWkpInfor().toDomain(companyId, newWorkplace.getWorkplaceId(),
                newWorkplace.getWkpHistoryLatest().identifier());
        this.wkpInfoRepo.add(workplaceInfo);
        
        if (command.getCreateType() == CreateWorkpceType.CREATE_TO_LIST) {
            // new workplace hierarchy
            WorkplaceHierarchyDto wkpHierarchyDto = new WorkplaceHierarchyDto();
            wkpHierarchyDto.setWorkplaceId(newWorkplace.getWorkplaceId());
            wkpHierarchyDto.setHierarchyCode(HIERARCHY_ORIGIN);
            
            WorkplaceConfigInfo wkpConfigInfo = command.getWkpConfigInfo(companyId, wkpHierarchyDto);
            
            // add workplace config infor
            this.wkpConfigInfoRepo.add(wkpConfigInfo);
        } else {
            // update hierarchy of workplace config infor
            this.wkpConfigInfoService.updateWkpHierarchy(command.getWkpConfigInfoHistId(), command.getWkpIdSelected(),
                    newWorkplace, command.getCreateType());
        }
    }

}
