/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary;

import javax.ejb.Stateless;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

/**
 * @author duongnd
 *
 */
@Stateless
public class PaymentSalaryReportService extends ExportService<PaymentSalaryQuery>{

    /* (non-Javadoc)
     * @see nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file.export.ExportServiceContext)
     */
    @Override
    protected void handle(ExportServiceContext<PaymentSalaryQuery> context) {
        // TODO Auto-generated method stub
        
    }

}
