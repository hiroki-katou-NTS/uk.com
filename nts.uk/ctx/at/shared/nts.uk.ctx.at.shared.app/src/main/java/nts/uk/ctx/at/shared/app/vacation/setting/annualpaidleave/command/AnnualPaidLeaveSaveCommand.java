/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.command.dto.ManageAnnualSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;

/**
 * The Class AnnualPaidLeaveUpateCommand.
 */
@Setter
@Getter
public class AnnualPaidLeaveSaveCommand {

    /** The annual manage. */
    private ManageDistinct annualManage;

    /** The manage setting. */
    private ManageAnnualSettingDto setting;

    /**
     * To domain.
     *
     * @param companyId the company id
     * @return the annual paid leave setting
     */
    public AnnualPaidLeaveSetting toDomain(String companyId) {
        return new AnnualPaidLeaveSetting(new AnnualPaidLeaveSettingGetMementoImpl(companyId, this));
    }

    /**
     * The Class AnnualPaidLeaveSettingGetMementoImpl.
     */
    public class AnnualPaidLeaveSettingGetMementoImpl implements AnnualPaidLeaveSettingGetMemento {

        /** The company id. */
        private String companyId;
        
        /** The command. */
        private AnnualPaidLeaveSaveCommand command;

        /**
         * Instantiates a new annual paid leave setting get memento impl.
         *
         * @param companyId the company id
         * @param command the command
         */
        public AnnualPaidLeaveSettingGetMementoImpl(String companyId, AnnualPaidLeaveSaveCommand command) {
            this.companyId = companyId;
            this.command = command;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * AnnualPaidLeaveSettingGetMemento#getCompanyId()
         */
        @Override
        public String getCompanyId() {
            return this.companyId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * AnnualPaidLeaveSettingGetMemento#getYearManageType()
         */
        @Override
        public ManageDistinct getYearManageType() {
            return command.getAnnualManage();
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * AnnualPaidLeaveSettingGetMemento#getYearManageSetting()
         */
        @Override
        public ManageAnnualSetting getYearManageSetting() {
            return null;
        }
    }
}
