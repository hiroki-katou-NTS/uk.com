/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.outsideot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language.OutsideOTBRDItemLangRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMedRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.OvertimeNameLangRepository;
import nts.uk.file.at.app.export.outsideot.data.OutsideOTBRDItemNameLangData;
import nts.uk.file.at.app.export.outsideot.data.OvertimeNameLanguageData;
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
 * The Class OutsideOTSettingExportImpl.
 */
@Stateless
@DomainID(value = "outsideot")
public class OutsideOTSettingExportImpl implements MasterListData {

	
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
	
	/** The Constant TOTA_NUMBER_COLS_A9_5. */
	private static final int TOTA_NUMBER_COLS_A9_5 = 100;

	/** The Constant LANGUAGE_ID_JAPAN. */
	public static final String LANGUAGE_ID_JAPAN = "ja";

	/** The Constant TABLE_ONE. */
	private static final String TABLE_ONE = "Table 001";

	/** The Constant TABLE_TWO. */
	private static final String TABLE_TWO = "Table 002";
	
	/** The Constant TABLE_THREE. */
	private static final String TABLE_THREE = "Table 003";
	
	/** The Constant TABLE_FOUR. */
	private static final String TABLE_FOUR = "Table 004";

	/** The Constant START_COL. */
	public static final int START_COL = 1;
	
	/** The Constant START_BREAKDOWN_ITEM. */
	public static final int START_BREAKDOWN_ITEM = 4;

	/** The Constant NUMBER_COLS_1. */
	private static final String NUMBER_COLS_1 = "Column 1";
	
	/** The Constant NUMBER_COLS. */
	private static final String NUMBER_COLS = "Column ";

	/** The Constant NUMBER_COLS_12. */
	private static final String NUMBER_COLS_2 = "Column 2";

	/** The Constant NUMBER_COLS_3. */
	private static final String NUMBER_COLS_3 = "Column 3";

	/** The Constant NUMBER_COLS_4. */
	private static final String NUMBER_COLS_4 = "Column 4";
	
	/** The Constant NUMBER_COLS_END. */
	private static final String NUMBER_COLS_END = "Column End";
	
	/** The Constant NUMBER_COLS_START. */
	private static final String NUMBER_COLS_START = "Column Start";

	/** The Constant NAME_VALUE_A5_1. */
	private static final String NAME_VALUE_A5_1 = "KMK010_49";

	/** The Constant NAME_VALUE_A7_1. */
	private static final String NAME_VALUE_A7_1 = "KMK010_51";

	/** The Constant NAME_VALUE_A7_2. */
	private static final String NAME_VALUE_A7_2 = "KMK010_52";

	/** The Constant NAME_VALUE_A7_3. */
	private static final String NAME_VALUE_A7_3 = "KMK010_53";

	/** The Constant NAME_VALUE_A7_4. */
	private static final String NAME_VALUE_A7_4 = "KMK010_54";

	/** The Constant NAME_VALUE_A7_5. */
	private static final String NAME_VALUE_A7_5 = "KMK010_66";

	/** The Constant NAME_VALUE_A9_1. */
	private static final String NAME_VALUE_A9_1 = "KMK010_55";

	/** The Constant NAME_VALUE_A9_2. */
	private static final String NAME_VALUE_A9_2 = "KMK010_56";

	/** The Constant NAME_VALUE_A9_3. */
	private static final String NAME_VALUE_A9_3 = "KMK010_57";

	/** The Constant NAME_VALUE_A9_4. */
	private static final String NAME_VALUE_A9_4 = "KMK010_58";

	/** The Constant NAME_VALUE_A9_4. */
	private static final String NAME_VALUE_A9_5 = "KMK010_59";

	/** The Constant NAME_VALUE_A9_4. */
	private static final String NAME_VALUE_A9_6 = "KMK010_67";

	/** The Constant NAME_VALUE_A15_1. */
	private static final String NAME_VALUE_A15_1 = "KMK010_60";

	/** The Constant NAME_VALUE_A15_1. */
	private static final String NAME_VALUE_A15_2 = "KMK010_61";

	/** The Constant NAME_VALUE_A15_3. */
	private static final String NAME_VALUE_A15_3 = "KMK010_62";

	/** The Constant TRUE_SETTING_RATE. */
	private static final String TRUE_SETTING_RATE = "休暇発生する";

	/** The Constant FALSE_SETTING_RATE. */
	private static final String FALSE_SETTING_RATE = "休暇発生しない";
	
	/** The start col. */
	private int startCol = 0;
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
		Map<String, List<MasterData>> mapTableData = new LinkedHashMap<>();
		Optional<OutsideOTSetting> optionalSetting = this.getSettingByLogin();
		if (optionalSetting.isPresent()) {
			mapTableData.put(TABLE_ONE, this.getMasterDataOne(query, optionalSetting.get()));
			mapTableData.put(TABLE_TWO, this.getMasterDataTwo(query, optionalSetting.get()));
			mapTableData.put(TABLE_THREE, this.getMasterDataThree(query));
			mapTableData.put(TABLE_FOUR, this.getMasterDataFour());
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
	public Map<String, List<MasterHeaderColumn>> getExtraHeaderColumn(MasterListExportQuery query) {
		Map<String, List<MasterHeaderColumn>> mapColum = new LinkedHashMap<>();
		mapColum.put(TABLE_ONE, this.getHeaderColumnOnes(query));
		mapColum.put(TABLE_TWO, this.getHeaderColumnTwos(query));
		mapColum.put(TABLE_THREE, this.getHeaderColumnThrees(query));
		mapColum.put(TABLE_FOUR, this.getHeaderColumnFours());
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
			language.setLanguageOther("");
			overtimeNameLanguageData.add(language);
		}

		if (!this.isLanugeJapan(query.getLanguageId())) {
			this.overtimeNameLangRepository.findAll(companyId, query.getLanguageId()).forEach(languageRepository -> {
				overtimeNameLanguageData.forEach(language -> {
					if (languageRepository.getOvertimeNo().value == language.getOvertimeNo()) {
						language.setLanguageOther(languageRepository.getName().v());
					}
				});
			});
		}
		outsideOTSetting.getOvertimes().forEach(overtime -> {
			overtimeNameLanguageData.forEach(overtimeLange -> {
				if (overtimeLange.getOvertimeNo() == overtime.getOvertimeNo().value) {
					overtimeLange.setIsUse(true);
					overtimeLange.setViewTime(this.toTimeView(overtime.getOvertime().v()));
					overtimeLange.setLanguage(overtime.getName().v());
				}
			});
		});
		overtimeNameLanguageData.forEach(overtimeLanguage -> {
			Map<String, Object> dataA71 = new HashMap<>();
			dataA71.put(NUMBER_COLS_1, overtimeLanguage.getOvertimeNo());
			dataA71.put(NUMBER_COLS_2, this.toUse(overtimeLanguage.getIsUse()));
			dataA71.put(NUMBER_COLS_3, overtimeLanguage.getLanguage());
			dataA71.put(NUMBER_COLS_4, overtimeLanguage.getViewTime());
			if (overtimeLanguage.getIsUse() && !this.isLanugeJapan(query.getLanguageId())) {
				dataA71.put(NUMBER_COLS_END, overtimeLanguage.getLanguageOther());
			}
			masterDatas.add(new MasterData(dataA71, null, ""));
		});

		return masterDatas;
	}
	
	/**
	 * Checks if is lanuge japan.
	 *
	 * @param languageId the language id
	 * @return true, if is lanuge japan
	 */
	private boolean isLanugeJapan(String languageId) {
		return languageId.equals(LANGUAGE_ID_JAPAN);
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
		Map<Integer, DailyAttendanceItem> mapAttendanceItem = this.dailyAttendanceItemRepository
				.getList(companyId).stream().collect(Collectors.toMap((attendanceItem -> {
					return attendanceItem.getAttendanceItemId();
				}), Function.identity()));
		
		

		if (!this.isLanugeJapan(query.getLanguageId())) {
			this.outsideOTBRDItemLangRepository.findAll(companyId, query.getLanguageId())
					.forEach(languageRepository -> {
						breakdownNameLanguageData.forEach(language -> {
							if (languageRepository.getBreakdownItemNo().value == language.getBreakdownItemNo()) {
								language.setLanguageOther(languageRepository.getName().v());
							}
						});
					});
		}
		outsideOTSetting.getBreakdownItems().forEach(breakdownItem -> {
			breakdownNameLanguageData.forEach(breakdownItemLange -> {
				if (breakdownItemLange.getBreakdownItemNo() == breakdownItem.getBreakdownItemNo().value) {
					breakdownItemLange.setIsUse(true);
					breakdownItemLange.setLanguage(breakdownItem.getName().v());
					breakdownItemLange.setProductNumber(breakdownItem.getProductNumber().value);
				}
			});
		});
		breakdownNameLanguageData.forEach(breakdownItemLange -> {
			Map<String, Object> dataA91 = new HashMap<>();
			dataA91.put(NUMBER_COLS_1, breakdownItemLange.getBreakdownItemNo());
			dataA91.put(NUMBER_COLS_2, this.toUse(breakdownItemLange.getIsUse()));
			dataA91.put(NUMBER_COLS_3, breakdownItemLange.getLanguage());
			dataA91.put(NUMBER_COLS_4, breakdownItemLange.getProductNumber());
			if (breakdownItemLange.getIsUse()) {
				outsideOTSetting.getBreakdownItems().forEach(breakdownItem -> {
					if (breakdownItem.getBreakdownItemNo().value == breakdownItemLange.getBreakdownItemNo()) {
						startCol = START_BREAKDOWN_ITEM;
						breakdownItem.getAttendanceItemIds().forEach(attendanceItemId -> {
							String attendanceItemName = "";
							if (mapAttendanceItem.containsKey(attendanceItemId)) {
								attendanceItemName = mapAttendanceItem.get(attendanceItemId).getAttendanceName().v();
							}
							startCol++;
							dataA91.put(NUMBER_COLS + startCol, attendanceItemName);
						});
						if (!this.isLanugeJapan(query.getLanguageId())) {
							dataA91.put(NUMBER_COLS_END, breakdownItemLange.getLanguageOther());
						}
					}
				});

			}
			masterDatas.add(new MasterData(dataA91, null, ""));
		});

		return masterDatas;
	}

	/**
	 * Gets the master data three.
	 *
	 * @param query the query
	 * @param outsideOTSetting the outside OT setting
	 * @return the master data three
	 */
	private List<MasterData> getMasterDataThree(MasterListExportQuery query) {
		List<MasterData> masterDatas = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		Map<String, Object> dataA121 = new HashMap<>();
		List<Overtime> lstOvertime = this.outsideOTSettingRepository.findAllOvertime(companyId);
		lstOvertime.forEach(overtime -> {
			dataA121.put(NUMBER_COLS + (overtime.getOvertimeNo().value),
					overtime.isSuperHoliday60HOccurs() ? TRUE_SETTING_RATE : FALSE_SETTING_RATE);
		});
		masterDatas.add(new MasterData(dataA121, null, ""));
		List<PremiumExtra60HRate> lstExtra60Rate = this.superHD60HConMedRepository.findAllPremiumRate(companyId);
		this.outsideOTSettingRepository.findAllBRDItem(companyId).forEach(breakdownItem -> {
			Map<String, Object> dataA141 = new HashMap<>();
			dataA141.put(NUMBER_COLS_START, breakdownItem.getName().v());
			if (breakdownItem.isUseClass()) {
				lstExtra60Rate.forEach(extraRate -> {
					if (extraRate.getBreakdownItemNo() == breakdownItem.getBreakdownItemNo()) {
						dataA141.put(NUMBER_COLS + extraRate.getOvertimeNo().value,
								this.toPercent(extraRate.getPremiumRate().v()));
					}
				});
			}
			masterDatas.add(new MasterData(dataA141, null, ""));
		});

		return masterDatas;
	}
	
	/**
	 * Gets the master data four.
	 *
	 * @param query the query
	 * @return the master data four
	 */
	private List<MasterData> getMasterDataFour() {
		List <MasterData> masterDatas = new ArrayList<>();
		Map<String, Object> dataA161 = new HashMap<>();
		String companyId = AppContexts.user().companyId();
		Optional<SuperHD60HConMed> optionalHD60ConMed = this.superHD60HConMedRepository.findById(companyId);
		if(optionalHD60ConMed.isPresent()){
			SuperHD60HConMed superHD60HConMed = optionalHD60ConMed.get();
			dataA161.put(NUMBER_COLS_1, superHD60HConMed.getTimeRoundingSetting().getRoundingTime().nameId);
			dataA161.put(NUMBER_COLS_2, superHD60HConMed.getTimeRoundingSetting().getRounding().nameId);
			dataA161.put(NUMBER_COLS_3, this.toTimeView(superHD60HConMed.getSuperHolidayOccurrenceUnit().v()));
		}
		masterDatas.add(new MasterData(dataA161, null, ""));
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
		columns.add(new MasterHeaderColumn(NUMBER_COLS_1, TextResource.localize(NAME_VALUE_A5_1), ColumnTextAlign.LEFT,
				"", true));
		return columns;
	}

	
	/**
	 * Gets the header column ones.
	 *
	 * @return the header column ones
	 */
	public List<MasterHeaderColumn> getHeaderColumnOnes(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(NUMBER_COLS_1, TextResource.localize(NAME_VALUE_A7_1), ColumnTextAlign.RIGHT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_2, TextResource.localize(NAME_VALUE_A7_2), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_3, TextResource.localize(NAME_VALUE_A7_3), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_4, TextResource.localize(NAME_VALUE_A7_4), ColumnTextAlign.LEFT,
				"", true));
		if (!this.isLanugeJapan(query.getLanguageId())) {
			columns.add(new MasterHeaderColumn(NUMBER_COLS_END, TextResource.localize(NAME_VALUE_A7_5),
					ColumnTextAlign.LEFT, "", true));
		}
		return columns;
	}

	/**
	 * Gets the header column twos.
	 *
	 * @return the header column twos
	 */
	public List<MasterHeaderColumn> getHeaderColumnTwos(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(NUMBER_COLS_1, TextResource.localize(NAME_VALUE_A9_1), ColumnTextAlign.RIGHT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_2, TextResource.localize(NAME_VALUE_A9_2), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_3, TextResource.localize(NAME_VALUE_A9_3), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_4, TextResource.localize(NAME_VALUE_A9_4), ColumnTextAlign.LEFT,
				"", true));
		
		for (int index = START_COL; index <= TOTA_NUMBER_COLS_A9_5; index++) {
			columns.add(new MasterHeaderColumn(NUMBER_COLS + (START_BREAKDOWN_ITEM + index),
					TextResource.localize(NAME_VALUE_A9_5) + index, ColumnTextAlign.LEFT, "", true));
		}
		
		if (!this.isLanugeJapan(query.getLanguageId())) {
			columns.add(new MasterHeaderColumn(NUMBER_COLS_END, TextResource.localize(NAME_VALUE_A9_6),
					ColumnTextAlign.LEFT, "", true));
		}
		return columns;
	}
	
	/**
	 * Gets the header column threes.
	 *
	 * @param query the query
	 * @return the header column threes
	 */
	public List<MasterHeaderColumn> getHeaderColumnThrees(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(NUMBER_COLS_START, "", ColumnTextAlign.LEFT, "", true));
		String companyId = AppContexts.user().companyId();
		this.outsideOTSettingRepository.findAllOvertime(companyId).forEach(overtime -> {
			columns.add(new MasterHeaderColumn(NUMBER_COLS + overtime.getOvertimeNo().value, overtime.getName().v(),
					ColumnTextAlign.LEFT, "", true));
		});
		return columns;
	}
	
	/**
	 * Gets the header column fours.
	 *
	 * @return the header column fours
	 */
	public List<MasterHeaderColumn> getHeaderColumnFours() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(NUMBER_COLS_1, TextResource.localize(NAME_VALUE_A15_1), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_2, TextResource.localize(NAME_VALUE_A15_2), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(NUMBER_COLS_3, TextResource.localize(NAME_VALUE_A15_3), ColumnTextAlign.RIGHT,
				"", true));
		return columns;
	}


	/**
	 * To percent.
	 *
	 * @param percent
	 *            the percent
	 * @return the string
	 */
	private String toPercent(int percent) {
		return percent + "%";
	}

	/**
	 * To use.
	 *
	 * @param use
	 *            the use
	 * @return the string
	 */
	private String toUse(Boolean use) {
		if (use) {
			return "○";
		}
		return "-";
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
