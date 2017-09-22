/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.infra.outsideot;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.outsideot.OutsideOTSettingExportGenerator;
import nts.uk.file.at.app.outsideot.data.OutsideOTSettingReport;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeOutsideOTSettingExportGenerator.
 */
@Stateless
public class AsposeOutsideOTSettingExportGenerator extends AsposeCellsReportGenerator
		implements OutsideOTSettingExportGenerator {
	

	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/KMK010_A.xlsx";
	
	/** The Constant REPORT_FILE_NAME. */
	private static final String REPORT_FILE_NAME = "KMK010_A.xlsx";
  
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.file.at.app.outsideot.OutsideOTSettingExportGenerator#generate(nts
	 * .arc.layer.infra.file.export.FileGeneratorContext,
	 * nts.uk.file.at.app.outsideot.OutsideOTSettingData)
	 */
	@Override
	public void generate(FileGeneratorContext fileContext, List<OutsideOTSettingReport> data) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			val workbook = reportContext.getWorkbook();
			val sheet = workbook.getWorksheets().get(0);
			val cells = sheet.getCells();

			data.forEach(dataItem -> {
				cells.get(dataItem.getNumberRows(), dataItem.getNumberCols())
						.setValue(dataItem.getValue());
			});

			// save as PDF file
			reportContext.saveAsExcel(this.createNewFile(fileContext, REPORT_FILE_NAME));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
		
}
