/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

/**
 * The Interface PaymentReportPerviewGenerator.
 */
public interface PaymentReportPreviewGenerator {
	
	/**
	 * Generate.
	 *
	 * @param fileContext the file context
	 */
    void generate(FileGeneratorContext fileContext, PaymentReportPreviewQuery query);

}
