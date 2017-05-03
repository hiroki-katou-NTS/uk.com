/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;

/**
 * @author duongnd
 *
 */
public interface PaymentSalaryInsuranceGenerator {

    void generate(FileGeneratorContext fileContext, PaymentSalaryReportData reportData);
}
