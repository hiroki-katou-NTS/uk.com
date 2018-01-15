/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.command.workplace.dto.WorkplaceDto;
import nts.uk.ctx.bs.employee.app.command.workplace.dto.WorkplaceHierarchyDto;
import nts.uk.ctx.bs.employee.app.command.workplace.dto.WorkplaceInfoDto;
import nts.uk.ctx.bs.employee.dom.workplace.CreateWorkpceType;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;

/**
 * The Class SaveWorkplaceCommand.
 */
@Setter
public class SaveWorkplaceCommand {

    /** The is add mode. */
    @Getter
    private Boolean isAddMode;

    /** The create type. */
    private Integer createType;

    /** The start date. */
    @Getter
    private GeneralDate startDate;

    /** The wkp config info hist id. */
    @Getter
    private String wkpConfigInfoHistId;

    /** The wkp id selected. */
    @Getter
    private String wkpIdSelected;

    /** The workplace. */
    @Getter
    private WorkplaceDto workplace;

    /** The wkp infor. */
    @Getter
    private WorkplaceInfoDto wkpInfor;

    /**
     * Gets the creates the type.
     *
     * @return the creates the type
     */
    public CreateWorkpceType getCreateType() {
        if (this.createType == null) {
            return CreateWorkpceType.CREATE_TO_LIST;
        }
        return CreateWorkpceType.valueOf(this.createType);
    }

    /**
     * Gets the wkp config info.
     *
     * @param companyId the company id
     * @param wkpHierarchyDto the wkp hierarchy dto
     * @return the wkp config info
     */
    public WorkplaceConfigInfo getWkpConfigInfo(String companyId, WorkplaceHierarchyDto wkpHierarchyDto) {
        return new WorkplaceConfigInfo(
                new WorkplaceConfigInfoGetMememtoImpl(companyId, this.wkpConfigInfoHistId, wkpHierarchyDto));
    }

    /**
     * The Class WorkplaceConfigInfoGetMememtoImpl.
     */
    class WorkplaceConfigInfoGetMememtoImpl implements WorkplaceConfigInfoGetMemento {

        /** The company id. */
        private String companyId;
        
        /** The history id. */
        private String historyId;
        
        /** The lst wkp hierarchy. */
        private List<WorkplaceHierarchy> lstWkpHierarchy;

        /**
         * Instantiates a new workplace config info get mememto impl.
         *
         * @param companyId the company id
         * @param historyId the history id
         * @param wkpHierarchyDto the wkp hierarchy dto
         */
        public WorkplaceConfigInfoGetMememtoImpl(String companyId, String historyId,
                WorkplaceHierarchyDto wkpHierarchyDto) {
            this.companyId = companyId;
            this.historyId = historyId;
            this.lstWkpHierarchy = Arrays.asList(wkpHierarchyDto.toDomain());
        }

        /* (non-Javadoc)
         * @see nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoGetMemento#getCompanyId()
         */
        @Override
        public String getCompanyId() {
            return this.companyId;
        }

        /* (non-Javadoc)
         * @see nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoGetMemento#getHistoryId()
         */
        @Override
        public String getHistoryId() {
            return this.historyId;
        }

        /* (non-Javadoc)
         * @see nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoGetMemento#getWkpHierarchy()
         */
        @Override
        public List<WorkplaceHierarchy> getWkpHierarchy() {
            return this.lstWkpHierarchy;
        }

    }
}