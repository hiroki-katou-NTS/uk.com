/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.payment;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.payment.data.PaymentReportData;

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
    void generate(FileGeneratorContext fileContext, PaymentReportData reportData);
}
