/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.outsideot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.outsideot.OutsideOTSettingFinder;
import nts.uk.ctx.at.shared.app.find.outsideot.holiday.SuperHD60HConMedFinder;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTCalMed;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.language.OutsideOTBRDItemLangRepository;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.language.OvertimeNameLangRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.file.at.app.outsideot.data.OutsideOTBRDItemNameLangData;
import nts.uk.file.at.app.outsideot.data.OutsideOTSettingData;
import nts.uk.file.at.app.outsideot.data.OutsideOTSettingReport;
import nts.uk.file.at.app.outsideot.data.OutsideOTSettingReportData;
import nts.uk.file.at.app.outsideot.data.OvertimeNameLanguageData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.i18n.TextResource;

/**
 * The Class OutsideOTSettingExportService.
 */
public class OutsideOTSettingExportService extends ExportService<OutsideOTSettingQuery> {
	
    /** The generator. */
    @Inject
    private OutsideOTSettingExportImpl generator;

    /** The finder. */
    @Inject
    private OutsideOTSettingFinder finder;
    
    /** The overtime name lang repository. */
    @Inject
    private OvertimeNameLangRepository overtimeNameLangRepository;
    
    /** The outside OTBRD item lang repository. */
    @Inject
    private OutsideOTBRDItemLangRepository outsideOTBRDItemLangRepository;
        
    /** The super HD 60 H con med finder. */
    @Inject
    private SuperHD60HConMedFinder superHD60HConMedFinder;
    
    /** The daily attendance item repository. */
    @Inject
    private DailyAttendanceItemRepository dailyAttendanceItemRepository;
 
    
    /** The Constant LANGUAGE_ID_JAPAN. */
    public static final String LANGUAGE_ID_JAPAN = "ja"; 
    
    
    
    /** The start row. */
	private int startRow = 0;
	
	/** The start col. */
	private int startCol = 0;
	
	
	/** The Constant START_OVERTIME. */
	public static final int START_OVERTIME = 13;
	
	/** The Constant START_OVERTIME_RATE. */
	public static final int START_OVERTIME_RATE = 33;
	
	/** The Constant START_OVERTIME_RATE_NEXT. */
	public static final int START_OVERTIME_RATE_NEXT = 34;
		
	/** The Constant START_BREAKDOWN_ITEM. */
	public static final int START_BREAKDOWN_ITEM = 21;
	
	/** The Constant START_PREMIUM_RATE. */
	public static final int START_PREMIUM_RATE = 35;
	
	/** The Constant START_COL. */
	public static final int START_COL = 1;
	
	/** The Constant START_COL_ZERO. */
	public static final int START_COL_ZERO = 0;
	
	/** The Constant START_COL_BREADOWN_LANG. */
	public static final int START_COL_BREADOWN_LANG = 104;
	
	/** The Constant NUMBER_ROWS_A5_1. */
	private static final int NUMBER_ROWS_A5_1 = 8;
	
	/** The Constant NUMBER_COLS_A5_1. */
	private static final int NUMBER_COLS_A5_1 = 0;
	
	/** The Constant NAME_VALUE_A5_1. */
	private static final String NAME_VALUE_A5_1 ="KMK010_49";
	
	/** The Constant NUMBER_ROWS_A6_1. */
	private static final int NUMBER_ROWS_A6_1 = 9;
	
	/** The Constant NUMBER_COLS_A6_1. */
	private static final int NUMBER_COLS_A6_1 = 0;
	
	/** The Constant NUMBER_ROWS_A7_1. */
	private static final int NUMBER_ROWS_A7_1 = 12;

	/** The Constant NUMBER_COLS_A7_1. */
	private static final int NUMBER_COLS_A7_1 = 0;

	/** The Constant NAME_VALUE_A7_1. */
	private static final String NAME_VALUE_A7_1 = "KMK010_51";

	/** The Constant NUMBER_ROWS_A7_2. */
	private static final int NUMBER_ROWS_A7_2 = 12;

	/** The Constant NUMBER_COLS_A7_2. */
	private static final int NUMBER_COLS_A7_2 = 1;

	/** The Constant NAME_VALUE_A7_2. */
	private static final String NAME_VALUE_A7_2 = "KMK010_52";

	/** The Constant NUMBER_ROWS_A7_3. */
	private static final int NUMBER_ROWS_A7_3 = 12;

	/** The Constant NUMBER_COLS_A7_3. */
	private static final int NUMBER_COLS_A7_3 = 2;

	/** The Constant NAME_VALUE_A7_3. */
	private static final String NAME_VALUE_A7_3 = "KMK010_53";
	
	/** The Constant NUMBER_ROWS_A7_4. */
	private static final int NUMBER_ROWS_A7_4 = 12;

	/** The Constant NUMBER_COLS_A7_4. */
	private static final int NUMBER_COLS_A7_4 = 3;

	/** The Constant NAME_VALUE_A7_4. */
	private static final String NAME_VALUE_A7_4 = "KMK010_54";
	
	/** The Constant NUMBER_ROWS_A7_5. */
	private static final int NUMBER_ROWS_A7_5 = 12;

	/** The Constant NUMBER_COLS_A7_5. */
	private static final int NUMBER_COLS_A7_5 = 4;

	/** The Constant NAME_VALUE_A7_5. */
	private static final String NAME_VALUE_A7_5 = "KMK010_66";
	
	/** The Constant NUMBER_ROWS_A9_1. */
	private static final int NUMBER_ROWS_A9_1 = 20;
	
	/** The Constant NUMBER_COLS_A9_1. */
	private static final int NUMBER_COLS_A9_1 = 0;
	
	/** The Constant NAME_VALUE_A9_1. */
	private static final String NAME_VALUE_A9_1 = "KMK010_55";
	
	/** The Constant NUMBER_ROWS_A9_2. */
	private static final int NUMBER_ROWS_A9_2 = 20;
	
	/** The Constant NUMBER_COLS_A9_2. */
	private static final int NUMBER_COLS_A9_2 = 1;
	
	/** The Constant NAME_VALUE_A9_2. */
	private static final String NAME_VALUE_A9_2 = "KMK010_56";
	
	/** The Constant NUMBER_ROWS_A9_3. */
	private static final int NUMBER_ROWS_A9_3 = 20;
	
	/** The Constant NUMBER_COLS_A9_3. */
	private static final int NUMBER_COLS_A9_3 = 2;
	
	/** The Constant NAME_VALUE_A9_3. */
	private static final String NAME_VALUE_A9_3 = "KMK010_57";
	
	/** The Constant NUMBER_ROWS_A9_4. */
	private static final int NUMBER_ROWS_A9_4 = 20;
	
	/** The Constant NUMBER_COLS_A9_4. */
	private static final int NUMBER_COLS_A9_4 = 3;
	
	/** The Constant NAME_VALUE_A9_4. */
	private static final String NAME_VALUE_A9_4 = "KMK010_58";
	/** The Constant NUMBER_ROWS_A9_4. */
	private static final int NUMBER_ROWS_A9_5 = 20;
	
	/** The Constant NUMBER_COLS_A9_4. */
	private static final int NUMBER_COLS_A9_5 = 3;
	
	/** The Constant TOTA_NUMBER_COLS_A9_5. */
	private static final int TOTA_NUMBER_COLS_A9_5 = 100;
	
	/** The Constant NAME_VALUE_A9_4. */
	private static final String NAME_VALUE_A9_5 = "KMK010_59";
	

	/** The Constant NUMBER_ROWS_A9_4. */
	private static final int NUMBER_ROWS_A9_6 = 20;
	
	/** The Constant NUMBER_COLS_A9_4. */
	private static final int NUMBER_COLS_A9_6 = 104;
	
	/** The Constant NAME_VALUE_A9_4. */
	private static final String NAME_VALUE_A9_6 = "KMK010_67";
	
	/** The Constant NUMBER_ROWS_A15_1. */
	private static final int NUMBER_ROWS_A15_1 = 47;
	
	/** The Constant NUMBER_COLS_A15_1. */
	private static final int NUMBER_COLS_A15_1 = 0;
	
	/** The Constant NAME_VALUE_A15_1. */
	private static final String NAME_VALUE_A15_1 = "KMK010_60";
	
	/** The Constant NUMBER_ROWS_A15_1. */
	private static final int NUMBER_ROWS_A15_2 = 47;
	
	/** The Constant NUMBER_COLS_A15_1. */
	private static final int NUMBER_COLS_A15_2 = 1;
	
	/** The Constant NAME_VALUE_A15_1. */
	private static final String NAME_VALUE_A15_2 = "KMK010_61";
	
	/** The Constant NUMBER_ROWS_A15_1. */
	private static final int NUMBER_ROWS_A15_3 = 47;
	
	/** The Constant NUMBER_ROWS_A16_1. */
	private static final int NUMBER_ROWS_A16_1 = 48;
	
	/** The Constant NUMBER_ROWS_A16_2. */
	private static final int NUMBER_ROWS_A16_2 = 48;
	
	/** The Constant NUMBER_ROWS_A16_2. */
	private static final int NUMBER_ROWS_A16_3 = 48;
	
	/** The Constant NUMBER_COLS_A16_1. */
	private static final int NUMBER_COLS_A16_1 = 0;
	
	/** The Constant NUMBER_COLS_A16_2. */
	private static final int NUMBER_COLS_A16_2 = 1;
	
	/** The Constant NUMBER_COLS_A16_3. */
	private static final int NUMBER_COLS_A16_3 = 2;
	
	/** The Constant NUMBER_COLS_A15_1. */
	private static final int NUMBER_COLS_A15_3 = 2;
	
	/** The Constant NAME_VALUE_A15_1. */
	private static final String NAME_VALUE_A15_3= "KMK010_62";

	/** The Constant TRUE_SETTING_RATE. */
	private static final String TRUE_SETTING_RATE= "休暇発生する";
	
	/** The Constant FALSE_SETTING_RATE. */
	private static final String FALSE_SETTING_RATE= "休暇発生しない";
	
	
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
     * .export.ExportServiceContext)
     */
    @Override
    protected void handle(ExportServiceContext<OutsideOTSettingQuery> context) {
    	
    	// get login user
    	LoginUserContext loginUserContext = AppContexts.user();
    	
    	// get company id
    	String companyId = loginUserContext.companyId();
    	
    	// get query
    	OutsideOTSettingQuery query = context.getQuery();
    	
    	OutsideOTSettingData data = new OutsideOTSettingData();
       
        data.setSetting(this.finder.reportById());
        
        
        
		List<OvertimeNameLanguageData> overtimeNameLanguageData = new ArrayList<>();
		
		
		List<OutsideOTBRDItemNameLangData> breakdownNameLanguageData = new ArrayList<>();
		
		// for each all data overtime language
		for (int index = BreakdownItemNo.ONE.value; index <= BreakdownItemNo.TEN.value; index++) {
			OutsideOTBRDItemNameLangData language = new OutsideOTBRDItemNameLangData("", index);
			breakdownNameLanguageData.add(language);
		}

		if (!query.getLanguageId().equals(LANGUAGE_ID_JAPAN)) {
			this.overtimeNameLangRepository.findAll(companyId, query.getLanguageId())
					.forEach(languageRepository -> {
						overtimeNameLanguageData.forEach(language -> {
							if (languageRepository.getOvertimeNo().value == language
									.getOvertimeNo()) {
								language.setLanguage(languageRepository.getName().v());
							}
						});
					});
			this.outsideOTBRDItemLangRepository.findAll(companyId, query.getLanguageId())
			.forEach(languageRepository -> {
				breakdownNameLanguageData.forEach(language -> {
					if (languageRepository.getBreakdownItemNo().value == language
							.getBreakdownItemNo()) {
						language.setLanguage(languageRepository.getName().v());
					}
				});
			});
		}
        data.setOvertimeLanguageData(overtimeNameLanguageData);
        data.setBreakdownLanguageData(breakdownNameLanguageData);
		if (query.isManage()) {
			data.setPremiumExtraRates(this.superHD60HConMedFinder.findAll());
			data.setSuperHD60HConMed(this.superHD60HConMedFinder.findById());
		}

		Map<Integer, DailyAttendanceItem> mapAttendanceItem = this.dailyAttendanceItemRepository
				.getList(companyId).stream().collect(Collectors.toMap((attendanceItem -> {
					return attendanceItem.getAttendanceItemId();
				}), Function.identity()));
		
		data.setMapAttendanceItem(mapAttendanceItem);
        // generate file
//		this.generator.generate(context.getGeneratorContext(),
//				this.convertToListReport(query.getLanguageId().equals(LANGUAGE_ID_JAPAN), data, context.getQuery().getNameCompany()));
    }

	/**
	 * To percent.
	 *
	 * @param percent the percent
	 * @return the string
	 */
	private String toPercent(int percent) {
		return percent + "%";
	}

	/**
	 * To use.
	 *
	 * @param use the use
	 * @return the string
	 */
	private String toUse(Boolean use) {
		if (use) {
			return "o";
		}
		return "-";
	}

/**
 * Convert to list report.
 *
 * @param isLanugeJapan the is lanuge japan
 * @param data the data
 * @return the list
 */
private OutsideOTSettingReportData convertToListReport(Boolean isLanugeJapan, OutsideOTSettingData data, String nameCompany){
		List<OutsideOTSettingReport> reportData = new ArrayList<>();
		
		// add header
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A5_1, NUMBER_COLS_A5_1,
				TextResource.localize(NAME_VALUE_A5_1)));
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A7_1, NUMBER_COLS_A7_1,
				TextResource.localize(NAME_VALUE_A7_1)));
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A7_2, NUMBER_COLS_A7_2,
				TextResource.localize(NAME_VALUE_A7_2)));
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A7_3, NUMBER_COLS_A7_3,
				TextResource.localize(NAME_VALUE_A7_3)));
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A7_4, NUMBER_COLS_A7_4,
				TextResource.localize(NAME_VALUE_A7_4)));
		if (!isLanugeJapan) {
			reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A7_5, NUMBER_COLS_A7_5,
					TextResource.localize(NAME_VALUE_A7_5)));
		}
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A9_1, NUMBER_COLS_A9_1,
				TextResource.localize(NAME_VALUE_A9_1)));
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A9_2, NUMBER_COLS_A9_2,
				TextResource.localize(NAME_VALUE_A9_2)));
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A9_3, NUMBER_COLS_A9_3,
				TextResource.localize(NAME_VALUE_A9_3)));
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A9_4, NUMBER_COLS_A9_4,
				TextResource.localize(NAME_VALUE_A9_4)));
		for (int index = START_COL; index <= TOTA_NUMBER_COLS_A9_5; index++) {
			reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A9_5, NUMBER_COLS_A9_5 + index,
					TextResource.localize(NAME_VALUE_A9_5) + index));
		}
		if (!isLanugeJapan) {
			reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A9_6, NUMBER_COLS_A9_6,
					TextResource.localize(NAME_VALUE_A9_6)));
		}
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A15_1, NUMBER_COLS_A15_1,
				TextResource.localize(NAME_VALUE_A15_1)));
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A15_2, NUMBER_COLS_A15_2,
				TextResource.localize(NAME_VALUE_A15_2)));
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A15_3, NUMBER_COLS_A15_3,
				TextResource.localize(NAME_VALUE_A15_3)));

		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A6_1, NUMBER_COLS_A6_1,
				OutsideOTCalMed.valueOf(data.getSetting().getCalculationMethod()).nameId));

		startRow = START_OVERTIME;
		startCol = START_COL;

		// export overtime
		data.getSetting().getOvertimes().forEach(overtime -> {
			startCol = START_COL;
			reportData.add(new OutsideOTSettingReport(startRow, startCol,
					this.toUse(overtime.getUseClassification())));
			if(overtime.getUseClassification()){
				startCol++;
				reportData.add(new OutsideOTSettingReport(startRow, startCol, overtime.getName()));
				startCol++;
				reportData.add(new OutsideOTSettingReport(startRow, startCol,
						this.toTimeView(overtime.getOvertime())));
				data.getOvertimeLanguageData().forEach(language -> {
					if (language.getOvertimeNo() == overtime.getOvertimeNo()) {
						startCol++;
						reportData.add(new OutsideOTSettingReport(startRow, startCol,
								language.getLanguage()));
					}
				});
			}

			startRow++;
		});

		startRow = START_BREAKDOWN_ITEM;
		startCol = START_COL;
		data.getSetting().getBreakdownItems().forEach(breakdownItem -> {
			startCol = START_COL;
			reportData.add(new OutsideOTSettingReport(startRow, startCol,
					this.toUse(breakdownItem.getUseClassification())));
			if(breakdownItem.getUseClassification()){
				startCol++;
				reportData.add(
						new OutsideOTSettingReport(startRow, startCol, breakdownItem.getName()));
				startCol++;
				reportData.add(new OutsideOTSettingReport(startRow, startCol,
						breakdownItem.getProductNumber()));
				startCol++;
				breakdownItem.getAttendanceItemIds().forEach(attendanceItemId -> {
					String attendanceItemName = "";
					if (data.getMapAttendanceItem().containsKey(attendanceItemId)) {
						attendanceItemName = data.getMapAttendanceItem().get(attendanceItemId)
								.getAttendanceName().v();
					}
					reportData.add(
							new OutsideOTSettingReport(startRow, startCol, attendanceItemName));
					startCol++;
				});
				data.getBreakdownLanguageData().forEach(breakdownLanguage -> {
					if (breakdownLanguage.getBreakdownItemNo() == breakdownItem
							.getBreakdownItemNo()) {
						reportData.add(new OutsideOTSettingReport(startRow, START_COL_BREADOWN_LANG,
								breakdownLanguage.getLanguage()));
					}
				});
			}
			startRow++;
		});

		startRow = START_OVERTIME_RATE;
		startCol = START_COL;
		data.getSetting().getOvertimes().forEach(overtime -> {
			reportData.add(new OutsideOTSettingReport(startRow, startCol, overtime.getName()));
			startCol++;
		});
		startRow = START_OVERTIME_RATE_NEXT;
		startCol = START_COL;
		data.getSetting().getOvertimes().forEach(overtime -> {
			reportData.add(new OutsideOTSettingReport(startRow, startCol,
					overtime.getSuperHoliday60HOccurs() ? TRUE_SETTING_RATE : FALSE_SETTING_RATE));
			startCol++;
		});
		startRow = START_PREMIUM_RATE;
		startCol = START_COL_ZERO;
		data.getSetting().getBreakdownItems().forEach(breakdownItem -> {
			startCol = START_COL_ZERO;
			reportData.add(new OutsideOTSettingReport(startRow, startCol, breakdownItem.getName()));
			if(breakdownItem.getUseClassification()){
				if (!CollectionUtil.isEmpty(data.getPremiumExtraRates())) {
					data.getSetting().getOvertimes().forEach(overtime->{
						startCol++;
						if(overtime.getUseClassification() && overtime.getSuperHoliday60HOccurs()){
							data.getPremiumExtraRates().forEach(premiumExtraRate -> {
								if (premiumExtraRate.getBreakdownItemNo() == breakdownItem
										.getBreakdownItemNo()
										&& premiumExtraRate.getOvertimeNo() == overtime
												.getOvertimeNo()) {
									reportData.add(new OutsideOTSettingReport(startRow, startCol,
											this.toPercent(premiumExtraRate.getPremiumRate())));
								}
							});
						}
					});

					reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A16_1, NUMBER_COLS_A16_1,
							Unit.valueOf(data.getSuperHD60HConMed().getRoundingTime()).nameId));
					reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A16_2, NUMBER_COLS_A16_2,
							Rounding.valueOf(data.getSuperHD60HConMed().getRounding()).nameId));
					reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A16_3, NUMBER_COLS_A16_3,
							this.toTimeView(
									data.getSuperHD60HConMed().getSuperHolidayOccurrenceUnit())));
				}
			}
			startRow++;
		});
		OutsideOTSettingReportData dataReport = new OutsideOTSettingReportData();
		
		reportData.add(new OutsideOTSettingReport(2, 1, nameCompany));
		reportData.add(new OutsideOTSettingReport(5, 1, isLanugeJapan ? "ja" : "en"));
		dataReport.setData(reportData);
		dataReport.setIsLanguageJapan(isLanugeJapan);
		return dataReport;
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

}

