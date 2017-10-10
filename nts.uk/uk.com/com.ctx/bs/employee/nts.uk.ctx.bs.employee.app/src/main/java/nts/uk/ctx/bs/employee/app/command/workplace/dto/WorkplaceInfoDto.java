/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.dto;

import lombok.Data;
import nts.gul.text.IdentifierUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId;
import nts.uk.ctx.bs.employee.dom.workplace.info.OutsideWorkplaceCode;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceCode;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceDisplayName;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceGenericName;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceName;

/**
 * The Class WorkplaceInfoDto.
 */

/**
 * Instantiates a new workplace info dto.
 */
@Data
public class WorkplaceInfoDto {

    /** The history id. */
    private String historyId;

    /** The workplace code. */
    private String workplaceCode;

    /** The workplace name. */
    private String workplaceName;

    /** The wkp generic name. */
    private String wkpGenericName;

    /** The wkp display name. */
    private String wkpDisplayName;

    /** The outside wkp code. */
    private String outsideWkpCode;

    /**
     * To domain.
     *
     * @param companyId the company id
     * @param wkpId the wkp id
     * @return the workplace info
     */
    public WorkplaceInfo toDomain(String companyId, WorkplaceId wkpId) {
        return new WorkplaceInfo(new WorkplaceInfoGetMementoImpl(companyId, wkpId, this));
    }

    /**
     * The Class WorkplaceInfoGetMementoImpl.
     */
    class WorkplaceInfoGetMementoImpl implements WorkplaceInfoGetMemento {

        /** The company id. */
        private String companyId;

        /** The wkp id. */
        private WorkplaceId wkpId;

        /** The dto. */
        private WorkplaceInfoDto dto;

        /**
         * Instantiates a new workplace info get memento impl.
         *
         * @param companyId
         *            the company id
         * @param wkpId
         *            the wkp id
         * @param historyId
         *            the history id
         * @param dto
         *            the dto
         */
        public WorkplaceInfoGetMementoImpl(String companyId, WorkplaceId wkpId, WorkplaceInfoDto dto) {
            this.companyId = companyId;
            this.wkpId = wkpId;
            this.dto = dto;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#
         * getCompanyId()
         */
        @Override
        public String getCompanyId() {
            return this.companyId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#
         * getHistoryId()
         */
        @Override
        public HistoryId getHistoryId() {
            String historyId = this.dto.historyId;
            if (StringUtil.isNullOrEmpty(historyId, true)) {
                historyId = IdentifierUtil.randomUniqueId();
            }
            return new HistoryId(historyId);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#
         * getWorkplaceId()
         */
        @Override
        public WorkplaceId getWorkplaceId() {
            return this.wkpId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#
         * getWorkplaceCode()
         */
        @Override
        public WorkplaceCode getWorkplaceCode() {
            return new WorkplaceCode(this.dto.workplaceCode);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#
         * getWorkplaceName()
         */
        @Override
        public WorkplaceName getWorkplaceName() {
            return new WorkplaceName(this.dto.workplaceName);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#
         * getWkpGenericName()
         */
        @Override
        public WorkplaceGenericName getWkpGenericName() {
            return new WorkplaceGenericName(this.dto.wkpGenericName);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#
         * getWkpDisplayName()
         */
        @Override
        public WorkplaceDisplayName getWkpDisplayName() {
            return new WorkplaceDisplayName(this.dto.wkpDisplayName);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoGetMemento#
         * getOutsideWkpCode()
         */
        @Override
        public OutsideWorkplaceCode getOutsideWkpCode() {
            return new OutsideWorkplaceCode(this.dto.outsideWkpCode);
        }
    }
}
