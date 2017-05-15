/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.payment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

/**
 * The Class PaymentReportService.
 */

@Stateless
public class PaymentReportService extends ExportService<PaymentReportQuery> {

    /** The generator. */
    @Inject
    private PaymentReportGenerator generator;
   
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
	 * .export.ExportServiceContext)
	 */
    @Override
    protected void handle(ExportServiceContext<PaymentReportQuery> context) {
    	this.generator.generate(context);
    }
    
   
}
