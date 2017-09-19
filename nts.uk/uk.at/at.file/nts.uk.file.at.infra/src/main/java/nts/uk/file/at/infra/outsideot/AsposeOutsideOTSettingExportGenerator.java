/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.infra.outsideot;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.outsideot.OutsideOTSettingData;
import nts.uk.file.at.app.outsideot.OutsideOTSettingExportGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeOutsideOTSettingExportGenerator.
 */
@Stateless
public class AsposeOutsideOTSettingExportGenerator extends AsposeCellsReportGenerator
		implements OutsideOTSettingExportGenerator {

	private static final String TEMPLATE_FILE = "report/KMK010_A.xlsx";
	
	private static final String REPORT_FILE_NAME = "サンプル帳票.pdf";
	
	private static final String REPORT_ID = "ReportSample";
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.file.at.app.outsideot.OutsideOTSettingExportGenerator#generate(nts
	 * .arc.layer.infra.file.export.FileGeneratorContext,
	 * nts.uk.file.at.app.outsideot.OutsideOTSettingData)
	 */
	@Override
	public void generate(FileGeneratorContext fileContext, OutsideOTSettingData data) {
		try (val reportContext = this.createContext(TEMPLATE_FILE, REPORT_ID)) {

			// process data binginds in template
			reportContext.processDesigner();

			// save as PDF file
			reportContext.saveAsPdf(this.createNewFile(fileContext, REPORT_FILE_NAME));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
		
}
