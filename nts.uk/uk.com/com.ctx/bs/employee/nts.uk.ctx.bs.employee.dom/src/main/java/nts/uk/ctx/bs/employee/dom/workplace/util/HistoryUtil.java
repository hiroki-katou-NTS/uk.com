/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.util;

import java.util.List;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.Period;

/**
 * The Class HistoryUtil.
 */
public class HistoryUtil {

    /** The Constant NUMBER_ELEMENT_MIN. */
    private static final Integer NUMBER_ELEMENT_MIN = 1;
    
    /** The Constant ELEMENT_FIRST. */
    private static final Integer ELEMENT_FIRST = 0;
    
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
    
    /**
     * Valid start date.
     *
     * @param latestPeriod the latest period
     * @param prevPeriod the prev period
     * @param newStartDate the new start date
     */
    public static void validStartDate(Period latestPeriod, Period prevPeriod, GeneralDate newStartDate) {
        boolean isHasError = false;
        BundledBusinessException exceptions = BundledBusinessException.newInstance();
        
        if (newStartDate.beforeOrEquals(prevPeriod.getStartDate())) {
            exceptions.addMessage("Msg_127");
            isHasError = true;
        }
        if (latestPeriod.getEndDate().beforeOrEquals(newStartDate)) {
            exceptions.addMessage("Msg_667");
            isHasError = true;
        }
        if (latestPeriod.getEndDate().beforeOrEquals(prevPeriod.getStartDate())) {
            exceptions.addMessage("Msg_666");
            isHasError = true;
        }
        // has error, throws message
        if (isHasError) {
            exceptions.throwExceptions();
        }
    }
    
    /**
     * Valid history latest.
     *
     * @param wkpConfig the wkp config
     * @param historyIdDeletion the history id deletion
     */
    public static void validHistoryLatest(List<String> lstHistoryId, String historyIdDeletion) {
        boolean isHasError = false;
        BundledBusinessException exceptions = BundledBusinessException.newInstance();
        
        // Don't remove when only has 1 history
        if (lstHistoryId.size() == NUMBER_ELEMENT_MIN) {
            exceptions.addMessage("Msg_57");
            isHasError = true;
        }
        // check history remove latest ?
        if (!historyIdDeletion.equals(lstHistoryId.get(ELEMENT_FIRST))) {
            exceptions.addMessage("Msg_55");
            isHasError = true;
        }
        // has error, throws message
        if (isHasError) {
            exceptions.throwExceptions();
        }
    }
}
