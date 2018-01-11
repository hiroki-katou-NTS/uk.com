/*
 * 
 */
package nts.uk.file.at.app.export.outsideot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.language.OutsideOTBRDItemLangRepository;
import nts.uk.ctx.at.shared.dom.outsideot.holiday.SuperHD60HConMedRepository;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.language.OvertimeNameLangRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.file.at.app.outsideot.data.OutsideOTBRDItemNameLangData;
import nts.uk.file.at.app.outsideot.data.OvertimeNameLanguageData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

/**
 * The Interface OutsideOTSettingExportGenerator.
 */
@Stateless
@DomainID(value = "outsideot")
public class OutsideOTSettingExportImpl implements MasterListData {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.shr.infra.file.report.masterlist.data.MasterListData#
	 * getMasterDatas(nts.uk.shr.infra.file.report.masterlist.webservice.
	 * MasterListExportQuery)
	 */
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> masterDatas = new ArrayList<>();

		Optional<OutsideOTSetting> optionalSetting = this.getSettingByLogin();
		if (optionalSetting.isPresent()) {
			OutsideOTSetting setting = optionalSetting.get();

			Map<String, Object> dataA61 = new HashMap<>();
			dataA61.put(NUMBER_COLS_1, setting.getCalculationMethod().nameId);
			masterDatas.add(new MasterData(dataA61, null, ""));
		}
		return masterDatas;
	}
	
	/**
	 * Gets the setting by login.
	 *
	 * @return the setting by login
	 */
	private Optional<OutsideOTSetting> getSettingByLogin() {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get query
		return this.outsideOTSettingRepository.findById(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.shr.infra.file.report.masterlist.data.MasterListData#
	 * getExtraMasterData(nts.uk.shr.infra.file.report.masterlist.webservice.
	 * MasterListExportQuery)
	 */
	@Override
	public Map<String, List<MasterData>> getExtraMasterData(MasterListExportQuery query) {
		Map<String, List<MasterData>> mapTableData = new HashMap<>();
		Optional<OutsideOTSetting> optionalSetting = this.getSettingByLogin();
		if (optionalSetting.isPresent()) {
			mapTableData.put(TABLE_ONE, this.getMasterDataOne(query, optionalSetting.get()));
			mapTableData.put(TABLE_TWO, this.getMasterDataTwo(query, optionalSetting.get()));
		}
		return mapTableData;
	};
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.shr.infra.file.report.masterlist.data.MasterListData#
	 * getExtraHeaderColumn(nts.uk.shr.infra.file.report.masterlist.webservice.
	 * MasterListExportQuery)
	 */
	@Override
	public Map<String, List<MasterHeaderColumn>> getExtraHeaderColumn(MasterListExportQuery query){
		Map<String, List<MasterHeaderColumn>> mapColum = new HashMap<>();
		mapColum.put(TABLE_ONE, this.getHeaderColumnOnes());
		mapColum.put(TABLE_TWO, this.getHeaderColumnTwos());
		return mapColum;
	};
	/**
	 * Gets the master data one.
	 *
	 * @return the master data one
	 */
	private List<MasterData> getMasterDataOne(MasterListExportQuery query, OutsideOTSetting outsideOTSetting) {
		List<MasterData> masterDatas = new ArrayList<>();

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get query
		List<OvertimeNameLanguageData> overtimeNameLanguageData = new ArrayList<>();

		// for each all data overtime language
		for (int index = OvertimeNo.ONE.value; index <= OvertimeNo.FIVE.value; index++) {
			OvertimeNameLanguageData language = new OvertimeNameLanguageData("", index, false);
			language.setViewTime("");
			overtimeNameLanguageData.add(language);
		}


		if (!query.getLanguageId().equals(LANGUAGE_ID_JAPAN)) {
			this.overtimeNameLangRepository.findAll(companyId, query.getLanguageId()).forEach(languageRepository -> {
				overtimeNameLanguageData.forEach(language -> {
					if (languageRepository.getOvertimeNo().value == language.getOvertimeNo()) {
						language.setLanguage(languageRepository.getName().v());
					}
				});
			});
		}
		outsideOTSetting.getOvertimes().forEach(overtime -> {
			overtimeNameLanguageData.forEach(overtimeLange -> {
				if (overtimeLange.getOvertimeNo() == overtime.getOvertimeNo().value) {
					overtimeLange.setIsUse(true);
					overtimeLange.setViewTime(this.toTimeView(overtime.getOvertime().v()));
					if (query.getLanguageId().equals(LANGUAGE_ID_JAPAN)) {
						overtimeLange.setLanguage(overtime.getName().v());
					}
				}
			});
		});
		overtimeNameLanguageData.forEach(overtimeLanguage->{
			Map<String, Object> dataA71 = new HashMap<>();
			dataA71.put(NUMBER_COLS_1, overtimeLanguage.getOvertimeNo());
			dataA71.put(NUMBER_COLS_2, this.toUse(overtimeLanguage.getIsUse()));
			dataA71.put(NUMBER_COLS_3, overtimeLanguage.getLanguage());
			dataA71.put(NUMBER_COLS_4, overtimeLanguage.getViewTime());
			masterDatas.add(new MasterData(dataA71, null, ""));
		});
		
		return masterDatas;
	}
	/**
	 * Gets the master data one.
	 *
	 * @return the master data one
	 */
	private List<MasterData> getMasterDataTwo(MasterListExportQuery query, OutsideOTSetting outsideOTSetting) {
		List<MasterData> masterDatas = new ArrayList<>();
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		
		List<OutsideOTBRDItemNameLangData> breakdownNameLanguageData = new ArrayList<>();
		
		// for each all data overtime language
		for (int index = BreakdownItemNo.ONE.value; index <= BreakdownItemNo.TEN.value; index++) {
			OutsideOTBRDItemNameLangData language = new OutsideOTBRDItemNameLangData("", index);
			language.setIsUse(false);
			breakdownNameLanguageData.add(language);
		}
		
		if (!query.getLanguageId().equals(LANGUAGE_ID_JAPAN)) {
			this.outsideOTBRDItemLangRepository.findAll(companyId, query.getLanguageId())
			.forEach(languageRepository -> {
				breakdownNameLanguageData.forEach(language -> {
					if (languageRepository.getBreakdownItemNo().value == language.getBreakdownItemNo()) {
						language.setLanguage(languageRepository.getName().v());
					}
				});
			});
		}
		outsideOTSetting.getBreakdownItems().forEach(breakdownItem -> {
			breakdownNameLanguageData.forEach(overtimeLange -> {
				if (overtimeLange.getBreakdownItemNo() == breakdownItem.getBreakdownItemNo().value) {
					overtimeLange.setIsUse(true);
					if (query.getLanguageId().equals(LANGUAGE_ID_JAPAN)) {
						overtimeLange.setLanguage(breakdownItem.getName().v());
					}
				}
			});
		});
		breakdownNameLanguageData.forEach(overtimeLanguage->{
			Map<String, Object> dataA91 = new HashMap<>();
			dataA91.put(NUMBER_COLS_11, overtimeLanguage.getBreakdownItemNo());
			dataA91.put(NUMBER_COLS_21, this.toUse(overtimeLanguage.getIsUse()));
			dataA91.put(NUMBER_COLS_31, overtimeLanguage.getLanguage());
			dataA91.put(NUMBER_COLS_41, overtimeLanguage.getLanguage());
			masterDatas.add(new MasterData(dataA91, null, ""));
		});
		
		return masterDatas;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.shr.infra.file.report.masterlist.data.MasterListData#
	 * getHeaderColumns(nts.uk.shr.infra.file.report.masterlist.webservice.
	 * MasterListExportQuery)
	 */
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(NUMBER_COLS_1, TextResource.localize(NAME_VALUE_A5_1),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	/**
	 * Gets the header column ones.
	 *
	 * @return the header column ones
	 */
	public List<MasterHeaderColumn> getHeaderColumnOnes() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(NUMBER_COLS_1, TextResource.localize(NAME_VALUE_A7_1), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_2, TextResource.localize(NAME_VALUE_A7_2), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_3, TextResource.localize(NAME_VALUE_A7_3), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_4, TextResource.localize(NAME_VALUE_A7_4), ColumnTextAlign.LEFT,
				"", true));
		return columns;
	}
	
	/**
	 * Gets the header column twos.
	 *
	 * @return the header column twos
	 */
	public List<MasterHeaderColumn> getHeaderColumnTwos() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(NUMBER_COLS_11, TextResource.localize(NAME_VALUE_A9_1), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_21, TextResource.localize(NAME_VALUE_A9_2), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_31, TextResource.localize(NAME_VALUE_A9_3), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_41, TextResource.localize(NAME_VALUE_A9_4), ColumnTextAlign.LEFT,
				"", true));
		return columns;
	}
	
    
    /** The overtime name lang repository. */
    @Inject
    private OvertimeNameLangRepository overtimeNameLangRepository;
    
    /** The outside OTBRD item lang repository. */
    @Inject
    private OutsideOTBRDItemLangRepository outsideOTBRDItemLangRepository;
        
    /** The super HD 60 H con med finder. */
    @Inject
    private SuperHD60HConMedRepository superHD60HConMedRepository;
    
    /** The daily attendance item repository. */
    @Inject
    private DailyAttendanceItemRepository dailyAttendanceItemRepository;
    
    /** The outside OT setting repository. */
    @Inject
    private OutsideOTSettingRepository outsideOTSettingRepository;
 
    
    /** The Constant LANGUAGE_ID_JAPAN. */
    public static final String LANGUAGE_ID_JAPAN = "ja"; 
    
	/** The Constant TABLE_ONE. */
	private static final String TABLE_ONE = "Table 001";
	
	/** The Constant TABLE_TWO. */
	private static final String TABLE_TWO = "Table 002";
    
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
	
	/** The Constant NUMBER_COLS_1. */
	private static final String NUMBER_COLS_1 = "Column 1";
	
	/** The Constant NUMBER_COLS_12. */
	private static final String NUMBER_COLS_2 = "Column 2";
	
	/** The Constant NUMBER_COLS_3. */
	private static final String NUMBER_COLS_3 = "Column 3";
	
	/** The Constant NUMBER_COLS_4. */
	private static final String NUMBER_COLS_4 = "Column 4";
	
	/** The Constant NUMBER_COLS_1. */
	private static final String NUMBER_COLS_11 = "Column 11";
	
	/** The Constant NUMBER_COLS_21. */
	private static final String NUMBER_COLS_21 = "Column 21";
	
	/** The Constant NUMBER_COLS_31. */
	private static final String NUMBER_COLS_31 = "Column 31";
	
	/** The Constant NUMBER_COLS_41. */
	private static final String NUMBER_COLS_41 = "Column 41";
	
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
	 * Gets the header report.
	 *
	 * @return the header report
	 */
	private List<MasterData> getHeaderReport() {

		

		/*
		// add header
		reportData.add(new OutsideOTSettingReport(NUMBER_ROWS_A5_1, NUMBER_COLS_A5_1,
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
		*/
		return new ArrayList<>();
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

