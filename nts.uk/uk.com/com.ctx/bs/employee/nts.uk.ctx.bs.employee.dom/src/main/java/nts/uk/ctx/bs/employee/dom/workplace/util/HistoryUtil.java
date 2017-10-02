/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.util;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;

/**
 * The Class HistoryUtil.
 */
public class HistoryUtil {

    /** The Constant NUMBER_ELEMENT_MIN. */
    private static final Integer NUMBER_ELEMENT_MIN = 1;
    
    /**
     * Valid start date.
     *
     * @param isAddMode the is add mode
     * @param currentStartDate the current start date
     * @param newStartDate the new start date
     */
    public static void validStartDate(boolean isAddMode, GeneralDate currentStartDate, GeneralDate newStartDate) {
        String messageId = "Msg_127";
        if (isAddMode) {
            messageId = "Msg_102";
        }
        if (currentStartDate.afterOrEquals(newStartDate)) {
            throw new BusinessException(messageId);
        }
    }
    
    public static void validStartDate(GeneralDate startDate, GeneralDate endDateMySelf, GeneralDate endDateLatest) {
        // TODO: throw error list
//        if (startDate.afterOrEquals(endDateMySelf)) {
//            throw new BusinessException("Msg_667");
//        }
//        if (startDate.afterOrEquals(endDateMySelf)) {
//            throw new BusinessException("Msg_666");
//        }
    }
    
    /**
     * Valid history latest.
     *
     * @param wkpConfig the wkp config
     * @param historyIdDeletion the history id deletion
     */
    public static void validHistoryLatest(WorkplaceConfig wkpConfig, String historyIdDeletion) {
        // Don't remove when only has 1 history
        if (wkpConfig.getWkpConfigHistory().size() == NUMBER_ELEMENT_MIN) {
            throw new BusinessException("Msg_57");
        }
        // check history remove latest ?
        if (!historyIdDeletion.equals(wkpConfig.getWkpConfigHistoryLatest().getHistoryId())) {
            throw new BusinessException("Msg_55");
        }
    }
}
