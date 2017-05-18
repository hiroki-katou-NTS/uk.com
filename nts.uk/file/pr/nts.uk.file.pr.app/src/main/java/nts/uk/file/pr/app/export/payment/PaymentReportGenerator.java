/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.payment;

import nts.arc.layer.app.file.export.ExportServiceContext;

/**
 * The Interface PaymentReportGenerator.
 */
public interface PaymentReportGenerator {

    /**
     * Generate.
     *
     * @param fileContext the file context
     * @param reportData the report data
     */
    void generate(ExportServiceContext<PaymentReportQuery> context);
}
