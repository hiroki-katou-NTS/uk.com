/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.infra.outsideot;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.at.app.outsideot.OutsideOTSettingExportGenerator;
import nts.uk.file.at.app.outsideot.data.OutsideOTSettingData;
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
	
	/** The start row. */
	private int startRow = 0;
	
	/** The start col. */
	private int startCol = 0;
	
	
	/** The Constant START_OVERTIME. */
	public static final int START_OVERTIME = 13;
	
	/** The Constant START_OVERTIME_RATE. */
	public static final int START_OVERTIME_RATE = 33;
		
	/** The Constant START_OVERTIME. */
	public static final int START_BREAKDOWN_ITEM = 21;
	
	/** The Constant START_PREMIUM_RATE. */
	public static final int START_PREMIUM_RATE = 35;
	
	/** The Constant START_COL. */
	public static final int START_COL = 1;
	
	/** The Constant START_COL_ZERO. */
	public static final int START_COL_ZERO = 0;
	
	public static final int START_COL_BREADOWN_LANG = 104;
	
	
	
	
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
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			
			val workbook = reportContext.getWorkbook();
			val sheet = workbook.getWorksheets().get(0);
			val cells = sheet.getCells();

			startRow = START_OVERTIME;
			startCol = START_COL;
			
			data.getSetting().getOvertimes().forEach(overtime->{
				startCol = START_COL;
				cells.get(startRow, startCol).setValue(overtime.getUseClassification());
				startCol++;
				cells.get(startRow, startCol).setValue(overtime.getName());
				startCol++;
				cells.get(startRow, startCol).setValue(this.toTimeView(overtime.getOvertime()));
				
				data.getOvertimeLanguageData().forEach(language->{
					if(language.getOvertimeNo() == overtime.getOvertimeNo()){
						startCol++;
						cells.get(startRow, startCol).setValue(language.getLanguage());
					}
				});
				
				startRow++;
			});
					
			startRow = START_BREAKDOWN_ITEM;
			startCol = START_COL;
			data.getSetting().getBreakdownItems().forEach(breakdownItem->{
				startCol = START_COL;
				cells.get(startRow, startCol).setValue(breakdownItem.getUseClassification());
				startCol++;
				cells.get(startRow, startCol).setValue(breakdownItem.getName());
				startCol++;
				cells.get(startRow, startCol).setValue(breakdownItem.getProductNumber());
				startCol++;
				breakdownItem.getAttendanceItemIds().forEach(attendanceItemId->{
					String attendanceItemName = "";
					if(data.getMapAttendanceItem().containsKey(attendanceItemId)){
						attendanceItemName = data.getMapAttendanceItem().get(attendanceItemId)
								.getAttendanceName().v();
					}
					cells.get(startRow, startCol).setValue(attendanceItemName);
					startCol++;
				});
				data.getBreakdownLanguageData().forEach(breakdownLanguage->{
					if(breakdownLanguage.getBreakdownItemNo() == breakdownItem.getBreakdownItemNo()){
						cells.get(startRow, START_COL_BREADOWN_LANG).setValue(breakdownLanguage.getLanguage());
					}
				});
				startRow++;
			});
			// process data binginds in template
			reportContext.processDesigner();

			startRow = START_OVERTIME_RATE;
			startCol = START_COL;
			data.getSetting().getOvertimes().forEach(overtime->{
				cells.get(startRow, startCol).setValue(overtime.getName());
				startCol++;
			});
			startRow = START_PREMIUM_RATE;
			startCol = START_COL_ZERO;
			data.getSetting().getBreakdownItems().forEach(breakdownItem->{
				startCol = START_COL_ZERO;
				cells.get(startRow, startCol).setValue(breakdownItem.getName());
				startCol++;
				data.getPremiumExtraRates().forEach(premiumExtraRate->{
					if(premiumExtraRate.getBreakdownItemNo() == breakdownItem.getBreakdownItemNo()){
						cells.get(startRow, startCol).setValue(this.toPercent(premiumExtraRate.getPremiumRate()));
						startCol++;
					}
				});
				startRow++;
			});
			// save as PDF file
			reportContext.saveAsExcel(this.createNewFile(fileContext, REPORT_FILE_NAME));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * To time view.
	 *
	 * @param time the time
	 * @return the string
	 */
	private String toTimeView(int time) {
		if (time < 10) {
			return "00:0" + time;
		}
		if (time < 60) {
			return "00:" + time;
		}
		int hourd = time / 60;
		int muni = time % 60;
		String h = "";
		String m = "";
		if (hourd < 10) {
			h = "0" + hourd;
		} else {
			h = "" + hourd;
		}
		if (muni < 10) {
			m = "0" + muni;
		} else {
			m = "" + muni;
		}
		return h + ":" + m;

	}
	
	/**
	 * To percent.
	 *
	 * @param percent the percent
	 * @return the string
	 */
	private String toPercent(int percent){
		return percent+"%";
	}
		
}
