/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ac.budget.external.actualresult;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.WorkplaceAdapter;

/**
 * The Class WorkplaceAdapterImpl.
 */
@Stateless
public class WorkplaceAdapterImpl implements WorkplaceAdapter {

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.uk.ctx.at.schedule.dom.budget.external.actualresult.WorkplaceAdapter#
     * findWpkIdList(java.lang.String, java.util.Date)
     */
    @Override
    public List<String> findWpkIdList(String wpkCode, Date date) {
        // TODO: fake return lstWpkId.
        List<String> lstWpkId = Arrays.asList(wpkCode);
        return lstWpkId;
    }

}
