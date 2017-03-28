/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.pr.file.infra.detailpaymentsalary;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.detailpaymentsalary.PaymentSalaryInsuranceGenerator;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * @author duongnd
 *
 */
@Stateless
public class AsposePaymentSalaryReportGenerator extends AsposeCellsReportGenerator
        implements PaymentSalaryInsuranceGenerator {

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.file.pr.app.export.detailpaymentsalary.
     * PaymentSalaryInsuranceGenerator#generate(nts.arc.layer.infra.file.export.
     * FileGeneratorContext, nts.uk.file.pr.app.export.detailpaymentsalary.data.
     * PaymentSalaryReportData)
     */
    @Override
    public void generate(FileGeneratorContext fileContext, PaymentSalaryReportData reportData) {
        // TODO Auto-generated method stub

    }

}
