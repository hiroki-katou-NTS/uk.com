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
import nts.uk.file.pr.app.export.payment.data.PaymentReportData;

/**
 * The Class PaymentReportService.
 */

@Stateless
public class PaymentReportService extends ExportService<PaymentReportQuery> {

    /** The generator. */
    @Inject
    private PaymentReportGenerator generator;
   
    /** The repository. */
    @Inject
    private PaymentReportRepository repository;
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
	 * .export.ExportServiceContext)
	 */
    @Override
    protected void handle(ExportServiceContext<PaymentReportQuery> context) {
    	PaymentReportQuery query = context.getQuery();
    	PaymentReportData reportData = this.repository.findData(query);
    	this.generator.generate(context.getGeneratorContext(), reportData);
    }
    
   
}
