/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.insurance.salary;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.insurance.data.SalarySocialInsuranceReportData;

/**
 * The Interface SocialInsuranceGenerator.
 *
 * @author duongnd
 */
public interface SalarySocialInsuranceGenerator {

    /**
     * Generate.
     *
     * @param fileContext the file context
     * @param socialInsuranceReportData the social insurance report data
     */
    void generate(FileGeneratorContext fileContext, SalarySocialInsuranceReportData reportData);
}
