package nts.uk.file.at.app.export.calculationsetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.calculationsetting.StampReflectionManagementDto;
import nts.uk.ctx.at.record.app.find.calculationsetting.StampReflectionManagementFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.midnight.MidnightTimeSheetDto;
import nts.uk.ctx.at.record.app.find.dailyperform.midnight.MidnightTimeSheetFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.DivergenceAttendanceItemFinder;
import nts.uk.ctx.at.record.app.find.holiday.roundingmonth.RoundingMonthDto;
import nts.uk.ctx.at.record.app.find.holiday.roundingmonth.RoundingMonthFinder;
import nts.uk.ctx.at.record.app.find.holiday.roundingmonth.TimeRoundingOfExcessOutsideTimeDto;
import nts.uk.ctx.at.record.app.find.holiday.roundingmonth.TimeRoundingOfExcessOutsideTimeFinder;
import nts.uk.ctx.at.record.app.find.monthly.vtotalmethod.PayItemCountOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.vtotalmethod.PayItemCountOfMonthlyFinder;
import nts.uk.ctx.at.record.app.find.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyFinder;
import nts.uk.ctx.at.record.app.find.workrecord.goout.OutManageDto;
import nts.uk.ctx.at.record.app.find.workrecord.goout.OutManageFinder;
import nts.uk.ctx.at.record.app.find.workrecord.temporarywork.ManageWorkTemporaryDto;
import nts.uk.ctx.at.record.app.find.workrecord.temporarywork.ManageWorkTemporaryFinder;
import nts.uk.ctx.at.record.app.find.workrule.specific.SpecificWorkRuleDto;
import nts.uk.ctx.at.record.app.find.workrule.specific.SpecificWorkRuleFinder;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.HolidayAddtionDto;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.HolidayAddtionFinder;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.flex.FlexSetDto;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.flex.FlexSetFinder;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.flex.InsufficientFlexHolidayMntDto;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.flex.InsufficientFlexHolidayMntFinder;
import nts.uk.ctx.at.shared.app.find.calculation.setting.DeformLaborOTDto;
import nts.uk.ctx.at.shared.app.find.calculation.setting.DeformLaborOTFinder;
import nts.uk.ctx.at.shared.app.find.entranceexit.ManageEntryExitDto;
import nts.uk.ctx.at.shared.app.find.entranceexit.ManageEntryExitFinder;
import nts.uk.ctx.at.shared.app.find.ot.frame.OvertimeWorkFrameFindDto;
import nts.uk.ctx.at.shared.app.find.ot.frame.OvertimeWorkFrameFinder;
import nts.uk.ctx.at.shared.app.find.ot.zerotime.HdFromHdDto;
import nts.uk.ctx.at.shared.app.find.ot.zerotime.HdFromWeekdayDto;
import nts.uk.ctx.at.shared.app.find.ot.zerotime.WeekdayHolidayDto;
import nts.uk.ctx.at.shared.app.find.ot.zerotime.ZeroTimeDto;
import nts.uk.ctx.at.shared.app.find.ot.zerotime.ZeroTimeFinder;
import nts.uk.ctx.at.shared.app.find.workdayoff.frame.WorkdayoffFrameFindDto;
import nts.uk.ctx.at.shared.app.find.workdayoff.frame.WorkdayoffFrameFinder;
import nts.uk.ctx.at.shared.app.find.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkDto;
import nts.uk.ctx.at.shared.app.find.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkFinder;
import nts.uk.ctx.at.shared.app.find.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodDto;
import nts.uk.ctx.at.shared.app.find.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodFinder;
import nts.uk.ctx.at.shared.app.find.workrule.func.SelectFunctionDto;
import nts.uk.ctx.at.shared.app.find.workrule.func.SelectFunctionFinder;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeFinder;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkEnum;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodEnum;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSetting;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.shared.dom.workrule.specific.TimeOffVacationPriorityOrder;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkMntSetRepository;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkSet;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManageRepository;
import nts.uk.screen.at.app.worktype.WorkTypeDto;
import nts.uk.screen.at.app.worktype.WorkTypeProcessor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@Stateless
@DomainID(value = "CalculationSetting")
public class CalculationSettingExportImpl implements MasterListData {
	@Inject
	SelectFunctionFinder finder;
	@Inject
	private HolidayAddtionFinder holidayAddtimeFinder;
	@Inject
	private MidnightTimeSheetFinder midnightTimeSheetFinder;
	@Inject
	private ManageEntryExitFinder manageEntryExitFinder;
	@Inject
	private OutManageFinder outManageFinder;
	@Inject
	private StampReflectionManagementFinder stampReflectionManagementFinder;
	@Inject
	private ManageWorkTemporaryFinder manageWorkTemporaryFinder;
	@Inject
	TemporaryWorkUseManageRepository tempWorkRepo;
	@Inject
	AggDeformedLaborSettingRepository aggSettingRepo;
	@Inject
	FlexWorkMntSetRepository flexWorkRepo;

	@Inject
	private DeformLaborOTFinder deformLaborOTFinder;
	@Inject
	private FlexSetFinder flexSetFinder;

	@Inject
	InsufficientFlexHolidayMntFinder insuffFinder;
	@Inject
	private RoleOvertimeWorkFinder roleOvertimeWorkFinder;
	@Inject
	private WorkdayoffFrameFinder workdayoffFrameFinder;
	@Inject
	private OvertimeWorkFrameFinder overtimeWorkFrameFinder;
	@Inject
	private RoleOfOpenPeriodFinder roleOfOpenPeriodFinder;
	@Inject
	SpecificWorkRuleFinder specificWorkRuleFinder;
	@Inject
	VerticalTotalMethodOfMonthlyFinder verticalTotalMethodOfMonthlyFinder;
	@Inject
	PayItemCountOfMonthlyFinder payItemCountOfMonthlyFinder;
	@Inject
	private WorkTypeFinder workTypeFinder;
	@Inject
	private RoundingMonthFinder roundingMonthFinder;
	@Inject
	private DivergenceAttendanceItemFinder divergenceItemSetFinder;

	@Inject
	private OutsideOtSetRepository outsideOtSetRepositoryFinder;
	@Inject
	private TimeRoundingOfExcessOutsideTimeFinder timeRoundingOfExcessOutsideTimeFinder;
	@Inject
	private ZeroTimeFinder zeroTimeFinder;

	@Inject
	SpecificWorkRuleRepository specificWorkRuleRepository;

	@Inject
	private WorkTypeProcessor workTypeProcessor;

	private static final String select = "○";
	private static final String unselect = "-";

	private static final String column1Sheet1 = "1";
	private static final String column2Sheet1 = "2";
	private static final String column3Sheet1 = "3";
	private static final String column1Sheet2 = "1";
	private static final String column2Sheet2 = "2";
	private static final String column3Sheet2 = "3";
	private static final String column4Sheet2 = "4";
	private static final String column5Sheet2 = "5";
	//
	private static final String column1Sheet3 = "1";
	private static final String column2Sheet3 = "2";
	private static final String column3Sheet3 = "3";
	//
	private static final String column1Sheet4 = "1";
	private static final String column2Sheet4 = "2";
	private static final String column3Sheet4 = "3";
	//
	private static final String column1Sheet5 = "1";
	private static final String column2Sheet5 = "2";
	private static final String column3Sheet5 = "3";

	private static final String column1Sheet6 = "1";
	private static final String column2Sheet6 = "2";
	private static final String column3Sheet6 = "3";

	private static final String column1Sheet7 = "1";
	private static final String column2Sheet7 = "2";
	private static final String column3Sheet7 = "3";

	private static final String column1Sheet8 = "1";
	private static final String column2Sheet8 = "2";
	private static final String column3Sheet8 = "3";

	private static final String column1Sheet9 = "1";
	private static final String column2Sheet9 = "2";
	private static final String column3Sheet9 = "3";
	private static final String column4Sheet9 = "4";
	// sheet11
	private static final String column1Sheet11 = "1";
	private static final String column2Sheet11 = "2";
	private static final String column3Sheet11 = "3";
	//
	private static final String column1Sheet12 = "1";
	private static final String column2Sheet12 = "2";
	private static final String column3Sheet12 = "3";
	//
	private static final String column1Sheet10 = "c1";
	private static final String column2Sheet10 = "c2";
	private static final String column3Sheet10 = "c3";
	private static final String column4Sheet10 = "c4";
	private static final String column5Sheet10 = "c5";
	private static final String column6Sheet10 = "c6";
	private static final String column7Sheet10 = "c7";
	private static final String column8Sheet10 = "c8";
	private static final String column09Sheet10 = "c09";
	private static final String column10Sheet10 = "c10";
	private static final String column11Sheet10 = "c11";
	private static final String column12Sheet10 = "c12";
	private static final String column13Sheet10 = "c13";
	private static final String column14Sheet10 = "c14";
	private static final String column15Sheet10 = "c15";
	private static final String column16Sheet10 = "c16";
	private static final String column17Sheet10 = "c17";
	private static final String column18Sheet10 = "c18";
	private static final String column19Sheet10 = "c19";
	private static final String column20Sheet10 = "c20";
	private static final String column21Sheet10 = "c21";
	private static final String column22Sheet10 = "c22";
	private static final String column23Sheet10 = "c23";
	private static final double defautsuppDays=2.0;

	public SelectFunctionDto getFucntionDto() {
		SelectFunctionDto selectFunctionDto = finder.findAllSetting();
		return selectFunctionDto;
	}

	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query) {
		List<SheetData> sheetDatas = new ArrayList<>();
		// sheet 2
		SheetData settingwrokhour = new SheetData(getDataSettingWorkingHours(query),
				getHeaderColumnsSettingWorkingHours(query), null, null, TextResource.localize("KMK013_427"), MasterListMode.NONE);
		sheetDatas.add(settingwrokhour);
		// sheet 3
		SheetData timeZoneSetting = new SheetData(getDataLateNightTimeZoneSetting(query),
				getHeaderColumnsLateNightTimeZoneSetting(query), null, null, TextResource.localize("KMK013_428"), MasterListMode.NONE);
		sheetDatas.add(timeZoneSetting);
		// sheet 4
		SheetData dataEmbossSetting = new SheetData(getDataEmbossSetting(query), getHeaderColumnsEmbossSetting(query),
				null, null, TextResource.localize("KMK013_429"), MasterListMode.NONE);
		sheetDatas.add(dataEmbossSetting);
		// sheet 5
		String companyId = AppContexts.user().companyId();
		Optional<TemporaryWorkUseManage> optTempWorkUse = tempWorkRepo.findByCid(companyId);
		if (optTempWorkUse.isPresent() && !Objects.isNull(optTempWorkUse.get().getUseClassification())
				&& optTempWorkUse.get().getUseClassification().value == 1) {
			SheetData dataTemporaryWorkSetting = new SheetData(getDataTemporaryWorkSetting(query),
					getHeaderColumnsTemporaryWorkSetting(query), null, null, TextResource.localize("KMK013_430"), MasterListMode.NONE);
			sheetDatas.add(dataTemporaryWorkSetting);
		}
		// sheet6
		Optional<AggDeformedLaborSetting> optAggSetting = aggSettingRepo.findByCid(companyId);
		if (optAggSetting.isPresent() && !Objects.isNull(optAggSetting.get().getUseDeformedLabor())
				&& optAggSetting.get().getUseDeformedLabor().value == 1) {
			SheetData dataSettingOfDeformedLabor = new SheetData(getDataSettingOfDeformedLabor(query),
					getHeaderColumnsSettingOfDeformedLabor(query), null, null, TextResource.localize("KMK013_432"), MasterListMode.NONE);
			sheetDatas.add(dataSettingOfDeformedLabor);
		}
		// sheet7
		Optional<FlexWorkSet> optFlexWorkSet = flexWorkRepo.find(companyId);
		if (optFlexWorkSet.isPresent() && !Objects.isNull(optFlexWorkSet.get().getUseFlexWorkSetting())
				&& optFlexWorkSet.get().getUseFlexWorkSetting().value == 1) {
			SheetData dataFlexWorkSetting = new SheetData(getDataFlexWorkSetting(query),
					getHeaderColumnsFlexWorkSetting(query), null, null, TextResource.localize("KMK013_433"), MasterListMode.NONE);
			sheetDatas.add(dataFlexWorkSetting);
		}
		// sheet8
		SheetData dataStatutorySettings = new SheetData(getDataStatutorySettings(query),
				getHeaderColumnsStatutorySettings(query), null, null, TextResource.localize("KMK013_435"), MasterListMode.NONE);
		sheetDatas.add(dataStatutorySettings);
		// sheet9

		SheetData dataCalculationProjectInherentSetting = new SheetData(getDataCalculationProjectInherentSetting(query),
				getHeaderColumnsCalculationProjectInherentSetting(query), null, null,
				TextResource.localize("KMK013_437"), MasterListMode.NONE);
		sheetDatas.add(dataCalculationProjectInherentSetting);
		// sheet 10

		SheetData dataZerotimeCrossingcalculationSettin = new SheetData(
				getDataZerotimeCrossingcalculationSetting(query),
				getHeaderColumnsZerotimeCrossingcalculationSetting(query), null, null,
				TextResource.localize("KMK013_438"), MasterListMode.NONE);
		sheetDatas.add(dataZerotimeCrossingcalculationSettin);
		// sheet 11
		SheetData dataSettingOfverticalmonthly = new SheetData(getDataSettingOfverticalmonthly(query),
				getHeaderColumnsSettingOfverticalmonthly(query), null, null, TextResource.localize("KMK013_439"), MasterListMode.NONE);
		sheetDatas.add(dataSettingOfverticalmonthly);
		// sheet 12
		SheetData dataRoundingSettingOfMonthly = new SheetData(getDataRoundingSettingOfMonthly(query),
				getHeaderColumnsRoundingSettingOfMonthly(query), null, null, TextResource.localize("KMK013_440"), MasterListMode.NONE);
		sheetDatas.add(dataRoundingSettingOfMonthly);
		return sheetDatas;
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		SelectFunctionDto selectFunctionDto = getFucntionDto();
		for (int i = 1; i <= 4; i++) {
			Map<String, Object> data = new HashMap<>();
			switch (i) {
			case 1:
				data.put(column1Sheet1, TextResource.localize("KMK013_206"));
				data.put(column2Sheet1, TextResource.localize("KMK013_207"));
				if (!Objects.isNull(selectFunctionDto)) {
					if (!Objects.isNull(selectFunctionDto.getFlexWorkManagement())) {
						data.put(column3Sheet1, getFunctionSelect(selectFunctionDto.getFlexWorkManagement()));
					}
				}
				break;
			case 2:
				data.put(column1Sheet1, "");
				data.put(column2Sheet1, TextResource.localize("KMK013_211"));
				if (!Objects.isNull(selectFunctionDto)) {
					if (!Objects.isNull(selectFunctionDto.getUseAggDeformedSetting())) {
						data.put(column3Sheet1, getFunctionSelect(selectFunctionDto.getUseAggDeformedSetting()));
					}
				}

				break;
			case 3:
				data.put(column1Sheet1, TextResource.localize("KMK013_215"));
				data.put(column2Sheet1, TextResource.localize("KMK013_216"));
				if (!Objects.isNull(selectFunctionDto)) {
					if (!Objects.isNull(selectFunctionDto.getUseWorkManagementMultiple())) {
						data.put(column3Sheet1, getFunctionSelect(selectFunctionDto.getUseWorkManagementMultiple()));
					}
				}
				break;
			case 4:
				data.put(column1Sheet1, "");
				data.put(column2Sheet1, TextResource.localize("KMK013_220"));
				if (!Objects.isNull(selectFunctionDto)) {
					if (!Objects.isNull(selectFunctionDto.getUseTempWorkUse())) {
						data.put(column3Sheet1, getFunctionSelect(selectFunctionDto.getUseTempWorkUse()));
					}
				}
				break;
			default:
				break;
			}

			MasterData masterData = new MasterData(data, null, "");
			Map<String, MasterCellData> rowData = masterData.getRowData();
			rowData.get(column1Sheet1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get(column2Sheet1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get(column3Sheet1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			datas.add(masterData);

		}

		return datas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet1, TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(column2Sheet1, "", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn(column3Sheet1, TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "",
				true));
		return columns;
	}

	@Override
	public String mainSheetName() {
		return TextResource.localize("KMK013_425");
	}

	private List<MasterHeaderColumn> getHeaderColumnsSettingWorkingHours(MasterListExportQuery query) {

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet2, TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(column2Sheet2, "", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn(column3Sheet2, "", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn(column4Sheet2, "", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn(column5Sheet2, TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "",
				true));
		return columns;

	}

	private List<MasterData> getDataSettingWorkingHours(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		List<HolidayAddtionDto> listHolidayAddtionDto = holidayAddtimeFinder.findAllHolidayAddtime();
		if (!CollectionUtil.isEmpty(listHolidayAddtionDto)) {
			HolidayAddtionDto holidayAddtionDto = listHolidayAddtionDto.get(0);
			// holidayAddtionDto.getReferActualWorkHours();
			Map<String, Object> line1 = new HashMap<>();
			line1.put(column1Sheet2, TextResource.localize("KMK013_3")); // B2_1
			line1.put(column2Sheet2, "");
			line1.put(column3Sheet2, "");
			line1.put(column4Sheet2, "");
			if (holidayAddtionDto.getReferActualWorkHours() == 1) {
				line1.put(column5Sheet2, TextResource.localize("KMK013_5"));
			} else {
				line1.put(column5Sheet2, TextResource.localize("KMK013_6"));
			}
			MasterData masterData1 = new MasterData(line1, null, "");
			Map<String, MasterCellData> rowData1 = masterData1.getRowData();
			getAlignsheet2(rowData1);
			datas.add(masterData1);

			if (holidayAddtionDto.getReferActualWorkHours() == 0) {
				Map<String, Object> line2 = new HashMap<>();
				line2.put(column1Sheet2, TextResource.localize("KMK013_7"));
				line2.put(column2Sheet2, "");
				line2.put(column3Sheet2, "");
				line2.put(column4Sheet2, "");
				if (holidayAddtionDto.getNotReferringAch() == 1) {
					line2.put(column5Sheet2, TextResource.localize("KMK013_9"));
				} else {
					line2.put(column5Sheet2, TextResource.localize("KMK013_10"));
				}
				MasterData masterData2 = new MasterData(line2, null, "");
				Map<String, MasterCellData> rowData2 = masterData2.getRowData();
				getAlignsheet2(rowData2);
				datas.add(masterData2);
				//
				if (holidayAddtionDto.getNotReferringAch() == 0) {
					Map<String, Object> line3 = new HashMap<>();
					line3.put(column1Sheet2, TextResource.localize("KMK013_11"));
					line3.put(column2Sheet2, "");
					line3.put(column3Sheet2, "");
					line3.put(column4Sheet2, "");
					if (holidayAddtionDto.getReferComHolidayTime() == 1) {
						line3.put(column5Sheet2, TextResource.localize("KMK013_13"));
					} else {
						line3.put(column5Sheet2, TextResource.localize("KMK013_14"));
					}
					MasterData masterData3 = new MasterData(line3, null, "");
					Map<String, MasterCellData> rowData3 = masterData3.getRowData();
					getAlignsheet2(rowData3);
					datas.add(masterData3);
					//
					if (holidayAddtionDto.getReferComHolidayTime() == 0) {
						Map<String, Object> line4 = new HashMap<>();
						line4.put(column1Sheet2, "");
						line4.put(column2Sheet2, TextResource.localize("KMK013_15"));
						line4.put(column3Sheet2, "");
						line4.put(column4Sheet2, "");
						line4.put(column5Sheet2, formatValueAttendance(holidayAddtionDto.getOneDay().intValue()));
						MasterData masterData4 = new MasterData(line4, null, "");
						Map<String, MasterCellData> rowData4 = masterData4.getRowData();
						getAlignRightsheet2(rowData4);
						datas.add(masterData4);

						//
						Map<String, Object> line5 = new HashMap<>();
						line5.put(column1Sheet2, "");
						line5.put(column2Sheet2, TextResource.localize("KMK013_17"));
						line5.put(column3Sheet2, "");
						line5.put(column4Sheet2, "");
						line5.put(column5Sheet2, formatValueAttendance(holidayAddtionDto.getMorning().intValue()));
						MasterData masterData5 = new MasterData(line5, null, "");
						Map<String, MasterCellData> rowData5 = masterData5.getRowData();
						getAlignRightsheet2(rowData5);
						datas.add(masterData5);
						//
						Map<String, Object> line6 = new HashMap<>();
						line6.put(column1Sheet2, "");
						line6.put(column2Sheet2, TextResource.localize("KMK013_19"));
						line6.put(column3Sheet2, "");
						line6.put(column4Sheet2, "");
						line6.put(column5Sheet2, formatValueAttendance(holidayAddtionDto.getAfternoon().intValue()));
						MasterData masterData6 = new MasterData(line6, null, "");
						Map<String, MasterCellData> rowData6 = masterData6.getRowData();
						getAlignRightsheet2(rowData6);
						datas.add(masterData6);

					}
				}

			}

			//
			Map<String, Object> line7 = new HashMap<>();
			line7.put(column1Sheet2, TextResource.localize("KMK013_246"));
			line7.put(column2Sheet2, "");
			line7.put(column3Sheet2, TextResource.localize("KMK013_251"));
			line7.put(column4Sheet2, "");
			if (holidayAddtionDto.getAddingMethod1() == 1) {
				line7.put(column5Sheet2, TextResource.localize("KMK013_249"));
			} else {
				line7.put(column5Sheet2, TextResource.localize("KMK013_248"));
			}

			MasterData masterData7 = new MasterData(line7, null, "");
			Map<String, MasterCellData> rowData7 = masterData7.getRowData();
			getAlignsheet2(rowData7);
			datas.add(masterData7);
			//
			Map<String, Object> line8 = new HashMap<>();
			line8.put(column1Sheet2, "");
			line8.put(column2Sheet2, "");
			line8.put(column3Sheet2, TextResource.localize("KMK013_252"));
			line8.put(column4Sheet2, "");
			if (holidayAddtionDto.getAddingMethod2() == 1) {
				line8.put(column5Sheet2, TextResource.localize("KMK013_249"));
			} else {
				line8.put(column5Sheet2, TextResource.localize("KMK013_248"));
			}

			MasterData masterData8 = new MasterData(line8, null, "");
			Map<String, MasterCellData> rowData8 = masterData8.getRowData();
			getAlignsheet2(rowData8);
			datas.add(masterData8);

			//

			Map<String, Object> line9 = new HashMap<>();
			line9.put(column1Sheet2, TextResource.localize("KMK013_21"));
			line9.put(column2Sheet2, TextResource.localize("KMK013_22"));
			line9.put(column3Sheet2, "");
			line9.put(column4Sheet2, "");
			if (holidayAddtionDto.getAnnualHoliday() == 1) {
				line9.put(column5Sheet2, select);
			} else {
				line9.put(column5Sheet2, unselect);
			}

			MasterData masterData9 = new MasterData(line9, null, "");
			Map<String, MasterCellData> rowData9 = masterData9.getRowData();
			getAlignsheet2(rowData9);
			datas.add(masterData9);
			//
			Map<String, Object> line10 = new HashMap<>();
			line10.put(column1Sheet2, "");
			line10.put(column2Sheet2, TextResource.localize("KMK013_23"));
			line10.put(column3Sheet2, "");
			line10.put(column4Sheet2, "");
			if (holidayAddtionDto.getSpecialHoliday() == 1) {
				line10.put(column5Sheet2, select);
			} else {
				line10.put(column5Sheet2, unselect);
			}

			MasterData masterData10 = new MasterData(line10, null, "");
			Map<String, MasterCellData> rowData10 = masterData10.getRowData();
			getAlignsheet2(rowData10);
			datas.add(masterData10);
			//

			Map<String, Object> line11 = new HashMap<>();
			line11.put(column1Sheet2, "");
			line11.put(column2Sheet2, TextResource.localize("KMK013_24"));
			line11.put(column3Sheet2, "");
			line11.put(column4Sheet2, "");
			if (holidayAddtionDto.getYearlyReserved() == 1) {
				line11.put(column5Sheet2, select);
			} else {
				line11.put(column5Sheet2, unselect);
			}

			MasterData masterData11 = new MasterData(line11, null, "");
			Map<String, MasterCellData> rowData11 = masterData11.getRowData();
			getAlignsheet2(rowData11);
			datas.add(masterData11);
			//
			Map<String, Object> line12 = new HashMap<>();
			line12.put(column1Sheet2, TextResource.localize("KMK013_253"));
			line12.put(column2Sheet2, "");
			line12.put(column3Sheet2, "");
			line12.put(column4Sheet2, "");
			if (holidayAddtionDto.getAddSetManageWorkHour() == 1) {
				line12.put(column5Sheet2, TextResource.localize("KMK013_256"));
			} else {
				line12.put(column5Sheet2, TextResource.localize("KMK013_255"));
			}
			MasterData masterData12 = new MasterData(line12, null, "");
			Map<String, MasterCellData> rowData12 = masterData12.getRowData();
			getAlignsheet2(rowData12);
			datas.add(masterData12);

			// start tab conten1
			// block1
			Map<String, Object> line13 = new HashMap<>();
			line13.put(column1Sheet2, TextResource.localize("KMK013_25"));
			line13.put(column2Sheet2, TextResource.localize("KMK013_28"));
			line13.put(column3Sheet2, "");
			line13.put(column4Sheet2, "");
			if (!Objects.isNull(holidayAddtionDto.getRegularWork())
					&& holidayAddtionDto.getRegularWork().getCalcActualOperationPre() == 1) {
				line13.put(column5Sheet2, TextResource.localize("KMK013_31"));
			} else {
				line13.put(column5Sheet2, TextResource.localize("KMK013_32"));
			}
			MasterData masterData13 = new MasterData(line13, null, "");
			Map<String, MasterCellData> rowData13 = masterData13.getRowData();
			getAlignsheet2(rowData13);
			datas.add(masterData13);

			//
			if (!Objects.isNull(holidayAddtionDto.getRegularWork())
					&& holidayAddtionDto.getRegularWork().getCalcActualOperationPre() == 1) {
				Map<String, Object> line14 = new HashMap<>();
				line14.put(column1Sheet2, "");
				line14.put(column2Sheet2, "");
				line14.put(column3Sheet2, TextResource.localize("KMK013_33"));
				line14.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getRegularWork())
						&& !Objects.isNull(holidayAddtionDto.getRegularWork().getAdditionTimePre())
						&& holidayAddtionDto.getRegularWork().getAdditionTimePre() == 1) {
					line14.put(column5Sheet2, select);
				} else {
					line14.put(column5Sheet2, unselect);
				}
				MasterData masterData14 = new MasterData(line14, null, "");
				Map<String, MasterCellData> rowData14 = masterData14.getRowData();
				getAlignsheet2(rowData14);
				datas.add(masterData14);
				//
				if (!Objects.isNull(holidayAddtionDto.getRegularWork())
						&& !Objects.isNull(holidayAddtionDto.getRegularWork().getAdditionTimePre())
						&& holidayAddtionDto.getRegularWork().getAdditionTimePre() == 1) {
					Map<String, Object> line15 = new HashMap<>();
					line15.put(column1Sheet2, "");
					line15.put(column2Sheet2, "");
					line15.put(column3Sheet2, "");

					line15.put(column4Sheet2, TextResource.localize("KMK013_34"));
					if (!Objects.isNull(holidayAddtionDto.getRegularWork())
							&& !Objects.isNull(holidayAddtionDto.getRegularWork().getDeformatExcValuePre())
							&& holidayAddtionDto.getRegularWork().getDeformatExcValuePre() == 1) {
						line15.put(column5Sheet2, TextResource.localize("KMK013_37"));
					} else {
						line15.put(column5Sheet2, TextResource.localize("KMK013_36"));
					}
					MasterData masterData15 = new MasterData(line15, null, "");
					Map<String, MasterCellData> rowData15 = masterData15.getRowData();
					getAlignsheet2(rowData15);
					datas.add(masterData15);
				}

				//

				Map<String, Object> line16 = new HashMap<>();
				line16.put(column1Sheet2, "");
				line16.put(column2Sheet2, "");
				line16.put(column3Sheet2, TextResource.localize("KMK013_38"));
				line16.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getRegularWork())
						&& !Objects.isNull(holidayAddtionDto.getRegularWork().getIncChildNursingCarePre())
						&& holidayAddtionDto.getRegularWork().getIncChildNursingCarePre() == 1) {
					line16.put(column5Sheet2, select);
				} else {
					line16.put(column5Sheet2, unselect);
				}
				MasterData masterData16 = new MasterData(line16, null, "");
				Map<String, MasterCellData> rowData16 = masterData16.getRowData();
				getAlignsheet2(rowData16);
				datas.add(masterData16);

				Map<String, Object> line17 = new HashMap<>();
				line17.put(column1Sheet2, "");
				line17.put(column2Sheet2, "");
				line17.put(column3Sheet2, TextResource.localize("KMK013_39"));
				line17.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getRegularWork())
						&& !Objects.isNull(holidayAddtionDto.getRegularWork().getNotDeductLateleavePre())
						&& holidayAddtionDto.getRegularWork().getNotDeductLateleavePre() == 1) {
					line17.put(column5Sheet2, select);
				} else {
					line17.put(column5Sheet2, unselect);
				}
				MasterData masterData17 = new MasterData(line17, null, "");
				Map<String, MasterCellData> rowData17 = masterData17.getRowData();
				getAlignsheet2(rowData17);
				datas.add(masterData17);

				if (!Objects.isNull(holidayAddtionDto.getRegularWork())
						&& !Objects.isNull(holidayAddtionDto.getRegularWork().getNotDeductLateleavePre())
						&& holidayAddtionDto.getRegularWork().getNotDeductLateleavePre() == 1) {
					//
					Map<String, Object> line18 = new HashMap<>();
					line18.put(column1Sheet2, "");
					line18.put(column2Sheet2, "");
					line18.put(column3Sheet2, "");
					line18.put(column4Sheet2, TextResource.localize("KMK013_257"));
					if (!Objects.isNull(holidayAddtionDto.getRegularWork())
							&& !Objects.isNull(holidayAddtionDto.getRegularWork().getEnableSetPerWorkHour1())
							&& holidayAddtionDto.getRegularWork().getEnableSetPerWorkHour1() == 1) {
						line18.put(column5Sheet2, select);
					} else {
						line18.put(column5Sheet2, unselect);
					}
					MasterData masterData18 = new MasterData(line18, null, "");
					Map<String, MasterCellData> rowData18 = masterData18.getRowData();
					getAlignsheet2(rowData18);
					datas.add(masterData18);

				}

				//
				Map<String, Object> line19 = new HashMap<>();
				line19.put(column1Sheet2, "");
				line19.put(column2Sheet2, "");
				line19.put(column3Sheet2, TextResource.localize("KMK013_40"));
				line19.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getRegularWork())
						&& !Objects.isNull(holidayAddtionDto.getRegularWork().getExemptTaxTimePre())
						&& holidayAddtionDto.getRegularWork().getExemptTaxTimePre() == 1) {
					line19.put(column5Sheet2, select);
				} else {
					line19.put(column5Sheet2, unselect);
				}
				MasterData masterData19 = new MasterData(line19, null, "");
				Map<String, MasterCellData> rowData19 = masterData19.getRowData();
				getAlignsheet2(rowData19);
				datas.add(masterData19);

			}

			// block2
			Map<String, Object> line20 = new HashMap<>();
			line20.put(column1Sheet2, "");
			line20.put(column2Sheet2, TextResource.localize("KMK013_29"));
			line20.put(column3Sheet2, "");
			line20.put(column4Sheet2, "");
			if (!Objects.isNull(holidayAddtionDto.getRegularWork())
					&& !Objects.isNull(holidayAddtionDto.getRegularWork().getCalcActualOperationWork())
					&& holidayAddtionDto.getRegularWork().getCalcActualOperationWork() == 1) {
				line20.put(column5Sheet2, TextResource.localize("KMK013_42"));
			} else {
				line20.put(column5Sheet2, TextResource.localize("KMK013_43"));
			}
			MasterData masterData20 = new MasterData(line20, null, "");
			Map<String, MasterCellData> rowData20 = masterData20.getRowData();
			getAlignsheet2(rowData20);
			datas.add(masterData20);
			//

			if (!Objects.isNull(holidayAddtionDto.getRegularWork())
					&& !Objects.isNull(holidayAddtionDto.getRegularWork().getCalcActualOperationWork())
					&& holidayAddtionDto.getRegularWork().getCalcActualOperationWork() == 1) {
				// if check
				Map<String, Object> line21 = new HashMap<>();
				line21.put(column1Sheet2, "");
				line21.put(column2Sheet2, "");
				line21.put(column3Sheet2, TextResource.localize("KMK013_44"));
				line21.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getRegularWork().getAdditionTimeWork())
						&& holidayAddtionDto.getRegularWork().getAdditionTimeWork() == 1) {

					line21.put(column5Sheet2, select);
				} else {
					line21.put(column5Sheet2, unselect);
				}
				MasterData masterData21 = new MasterData(line21, null, "");
				Map<String, MasterCellData> rowData21 = masterData21.getRowData();
				getAlignsheet2(rowData21);
				datas.add(masterData21);
				//
				Map<String, Object> line22 = new HashMap<>();
				line22.put(column1Sheet2, "");
				line22.put(column2Sheet2, "");
				line22.put(column3Sheet2, TextResource.localize("KMK013_45"));
				line22.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getRegularWork().getIncChildNursingCareWork())
						&& holidayAddtionDto.getRegularWork().getIncChildNursingCareWork() == 1) {

					line22.put(column5Sheet2, select);
				} else {
					line22.put(column5Sheet2, unselect);
				}
				MasterData masterData22 = new MasterData(line22, null, "");
				Map<String, MasterCellData> rowData22 = masterData22.getRowData();
				getAlignsheet2(rowData22);
				datas.add(masterData22);

				//
				Map<String, Object> line23 = new HashMap<>();
				line23.put(column1Sheet2, "");
				line23.put(column2Sheet2, "");
				line23.put(column3Sheet2, TextResource.localize("KMK013_46"));
				line23.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getRegularWork().getNotDeductLateleaveWork())
						&& holidayAddtionDto.getRegularWork().getNotDeductLateleaveWork() == 1) {

					line23.put(column5Sheet2, select);
				} else {
					line23.put(column5Sheet2, unselect);
				}
				MasterData masterData23 = new MasterData(line23, null, "");
				Map<String, MasterCellData> rowData23 = masterData23.getRowData();
				getAlignsheet2(rowData23);
				datas.add(masterData23);
				//
				if (!Objects.isNull(holidayAddtionDto.getRegularWork().getNotDeductLateleaveWork())
						&& holidayAddtionDto.getRegularWork().getNotDeductLateleaveWork() == 1) {

					Map<String, Object> line24 = new HashMap<>();
					line24.put(column1Sheet2, "");
					line24.put(column2Sheet2, "");
					line24.put(column3Sheet2, "");
					line24.put(column4Sheet2, TextResource.localize("KMK013_257"));
					if (!Objects.isNull(holidayAddtionDto.getRegularWork().getEnableSetPerWorkHour2())
							&& holidayAddtionDto.getRegularWork().getEnableSetPerWorkHour2() == 1) {

						line24.put(column5Sheet2, select);
					} else {
						line24.put(column5Sheet2, unselect);
					}
					MasterData masterData24 = new MasterData(line24, null, "");
					Map<String, MasterCellData> rowData24 = masterData24.getRowData();
					getAlignsheet2(rowData24);
					datas.add(masterData24);
				}

				//
				Map<String, Object> line25 = new HashMap<>();
				line25.put(column1Sheet2, "");
				line25.put(column2Sheet2, "");
				line25.put(column3Sheet2, TextResource.localize("KMK013_47"));
				line25.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getRegularWork().getExemptTaxTimeWork())
						&& holidayAddtionDto.getRegularWork().getExemptTaxTimeWork() == 1) {

					line25.put(column5Sheet2, select);
				} else {
					line25.put(column5Sheet2, unselect);
				}
				MasterData masterData25 = new MasterData(line25, null, "");
				Map<String, MasterCellData> rowData25 = masterData25.getRowData();
				getAlignsheet2(rowData25);
				datas.add(masterData25);
			}
			// end tab1
			/////////////////////////////////////////////
			// start tab2
			// block1
			Map<String, Object> line26 = new HashMap<>();
			line26.put(column1Sheet2, TextResource.localize("KMK013_422"));
			line26.put(column2Sheet2, TextResource.localize("KMK013_28"));
			line26.put(column3Sheet2, "");
			line26.put(column4Sheet2, "");
			if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet())
					&& holidayAddtionDto.getHourlyPaymentAdditionSet().getCalcPremiumVacation() == 1) {
				line26.put(column5Sheet2, TextResource.localize("KMK013_31"));
			} else {
				line26.put(column5Sheet2, TextResource.localize("KMK013_32"));
			}
			MasterData masterData26 = new MasterData(line26, null, "");
			Map<String, MasterCellData> rowData26 = masterData26.getRowData();
			getAlignsheet2(rowData26);
			datas.add(masterData26);

			//
			if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet())
					&& holidayAddtionDto.getHourlyPaymentAdditionSet().getCalcPremiumVacation() == 1) {
				Map<String, Object> line27 = new HashMap<>();
				line27.put(column1Sheet2, "");
				line27.put(column2Sheet2, "");
				line27.put(column3Sheet2, TextResource.localize("KMK013_33"));
				line27.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getRegularWork())
						&& !Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getAddition1())
						&& holidayAddtionDto.getHourlyPaymentAdditionSet().getAddition1() == 1) {
					line27.put(column5Sheet2, select);
				} else {
					line27.put(column5Sheet2, unselect);
				}
				MasterData masterData27 = new MasterData(line27, null, "");
				Map<String, MasterCellData> rowData27 = masterData27.getRowData();
				getAlignsheet2(rowData27);
				datas.add(masterData27);
				//
				Map<String, Object> line28 = new HashMap<>();
				line28.put(column1Sheet2, "");
				line28.put(column2Sheet2, "");
				line28.put(column3Sheet2, "");

				line28.put(column4Sheet2, TextResource.localize("KMK013_34"));
				if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet())
						&& !Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getDeformatExcValue())
						&& holidayAddtionDto.getHourlyPaymentAdditionSet().getDeformatExcValue() == 1) {
					line28.put(column5Sheet2, TextResource.localize("KMK013_37"));
				} else {
					line28.put(column5Sheet2, TextResource.localize("KMK013_36"));
				}
				MasterData masterData28 = new MasterData(line28, null, "");
				Map<String, MasterCellData> rowData28 = masterData28.getRowData();
				getAlignsheet2(rowData28);
				datas.add(masterData28);
				//

				Map<String, Object> line29 = new HashMap<>();
				line29.put(column1Sheet2, "");
				line29.put(column2Sheet2, "");
				line29.put(column3Sheet2, TextResource.localize("KMK013_38"));
				line29.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet())
						&& !Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getIncChildNursingCare())
						&& holidayAddtionDto.getHourlyPaymentAdditionSet().getIncChildNursingCare() == 1) {
					line29.put(column5Sheet2, select);
				} else {
					line29.put(column5Sheet2, unselect);
				}
				MasterData masterData29 = new MasterData(line29, null, "");
				Map<String, MasterCellData> rowData29 = masterData29.getRowData();
				getAlignsheet2(rowData29);
				datas.add(masterData29);
				//
				Map<String, Object> line30 = new HashMap<>();
				line30.put(column1Sheet2, "");
				line30.put(column2Sheet2, "");
				line30.put(column3Sheet2, TextResource.localize("KMK013_39"));
				line30.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet())
						&& !Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getDeduct())
						&& holidayAddtionDto.getHourlyPaymentAdditionSet().getDeduct() == 1) {
					line30.put(column5Sheet2, select);
				} else {
					line30.put(column5Sheet2, unselect);
				}
				MasterData masterData30 = new MasterData(line30, null, "");
				Map<String, MasterCellData> rowData30 = masterData30.getRowData();
				getAlignsheet2(rowData30);
				datas.add(masterData30);

				if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet())
						&& !Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getDeduct())
						&& holidayAddtionDto.getHourlyPaymentAdditionSet().getDeduct() == 1) {
					//
					Map<String, Object> line31 = new HashMap<>();
					line31.put(column1Sheet2, "");
					line31.put(column2Sheet2, "");
					line31.put(column3Sheet2, "");
					line31.put(column4Sheet2, TextResource.localize("KMK013_257"));
					if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet())
							&& !Objects
									.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getEnableSetPerWorkHour1())
							&& holidayAddtionDto.getHourlyPaymentAdditionSet().getEnableSetPerWorkHour1() == 1) {
						line31.put(column5Sheet2, select);
					} else {
						line31.put(column5Sheet2, unselect);
					}
					MasterData masterData31 = new MasterData(line31, null, "");
					Map<String, MasterCellData> rowData31 = masterData31.getRowData();
					getAlignsheet2(rowData31);
					datas.add(masterData31);

				}

				//
				Map<String, Object> line32 = new HashMap<>();
				line32.put(column1Sheet2, "");
				line32.put(column2Sheet2, "");
				line32.put(column3Sheet2, TextResource.localize("KMK013_40"));
				line32.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet())
						&& !Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet()
								.getCalculateIncludeIntervalExemptionTime1())
						&& holidayAddtionDto.getHourlyPaymentAdditionSet()
								.getCalculateIncludeIntervalExemptionTime1() == 1) {
					line32.put(column5Sheet2, select);
				} else {
					line32.put(column5Sheet2, unselect);
				}
				MasterData masterData32 = new MasterData(line32, null, "");
				Map<String, MasterCellData> rowData32 = masterData32.getRowData();
				getAlignsheet2(rowData32);
				datas.add(masterData32);
			}
			// block 2

			//
			Map<String, Object> line31_1 = new HashMap<>();
			line31_1.put(column1Sheet2, "");
			line31_1.put(column2Sheet2, TextResource.localize("KMK013_29"));
			line31_1.put(column3Sheet2, "");
			line31_1.put(column4Sheet2, "");
			if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet())
					&& !Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getCalcWorkHourVacation())
					&& holidayAddtionDto.getHourlyPaymentAdditionSet().getCalcWorkHourVacation() == 1) {
				line31_1.put(column5Sheet2, TextResource.localize("KMK013_42"));
			} else {
				line31_1.put(column5Sheet2, TextResource.localize("KMK013_43"));
			}
			MasterData masterData31_1 = new MasterData(line31_1, null, "");
			Map<String, MasterCellData> rowData31_1 = masterData31_1.getRowData();
			getAlignsheet2(rowData31_1);
			datas.add(masterData31_1);
			//

			if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet())
					&& !Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getCalcWorkHourVacation())
					&& holidayAddtionDto.getHourlyPaymentAdditionSet().getCalcWorkHourVacation() == 1) {
				// if check
				Map<String, Object> line32_1 = new HashMap<>();
				line32_1.put(column1Sheet2, "");
				line32_1.put(column2Sheet2, "");
				line32_1.put(column3Sheet2, TextResource.localize("KMK013_44"));
				line32_1.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getAddition2())
						&& holidayAddtionDto.getHourlyPaymentAdditionSet().getAddition2() == 1) {

					line32_1.put(column5Sheet2, select);
				} else {
					line32_1.put(column5Sheet2, unselect);
				}
				MasterData masterData32_1 = new MasterData(line32_1, null, "");
				Map<String, MasterCellData> rowData32_1 = masterData32_1.getRowData();
				getAlignsheet2(rowData32_1);
				datas.add(masterData32_1);
				//
				Map<String, Object> line33 = new HashMap<>();
				line33.put(column1Sheet2, "");
				line33.put(column2Sheet2, "");
				line33.put(column3Sheet2, TextResource.localize("KMK013_45"));
				line33.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getCalculateIncludCareTime())
						&& holidayAddtionDto.getHourlyPaymentAdditionSet().getCalculateIncludCareTime() == 1) {

					line33.put(column5Sheet2, select);
				} else {
					line33.put(column5Sheet2, unselect);
				}
				MasterData masterData33 = new MasterData(line33, null, "");
				Map<String, MasterCellData> rowData33 = masterData33.getRowData();
				getAlignsheet2(rowData33);
				datas.add(masterData33);

				//
				Map<String, Object> line34 = new HashMap<>();
				line34.put(column1Sheet2, "");
				line34.put(column2Sheet2, "");
				line34.put(column3Sheet2, TextResource.localize("KMK013_46"));
				line34.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getNotDeductLateLeaveEarly())
						&& holidayAddtionDto.getHourlyPaymentAdditionSet().getNotDeductLateLeaveEarly() == 1) {

					line34.put(column5Sheet2, select);
				} else {
					line34.put(column5Sheet2, unselect);
				}
				MasterData masterData34 = new MasterData(line34, null, "");
				Map<String, MasterCellData> rowData34 = masterData34.getRowData();
				getAlignsheet2(rowData34);
				datas.add(masterData34);
				//
				if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getNotDeductLateLeaveEarly())
						&& holidayAddtionDto.getHourlyPaymentAdditionSet().getNotDeductLateLeaveEarly() == 1) {
					Map<String, Object> line35 = new HashMap<>();
					line35.put(column1Sheet2, "");
					line35.put(column2Sheet2, "");
					line35.put(column3Sheet2, "");
					line35.put(column4Sheet2, TextResource.localize("KMK013_257"));
					if (!Objects.isNull(holidayAddtionDto.getHourlyPaymentAdditionSet().getEnableSetPerWorkHour2())
							&& holidayAddtionDto.getHourlyPaymentAdditionSet().getEnableSetPerWorkHour2() == 1) {

						line35.put(column5Sheet2, select);
					} else {
						line35.put(column5Sheet2, unselect);
					}
					MasterData masterData35 = new MasterData(line35, null, "");
					Map<String, MasterCellData> rowData35 = masterData35.getRowData();
					getAlignsheet2(rowData35);
					datas.add(masterData35);
				}

				//
				Map<String, Object> line36 = new HashMap<>();
				line36.put(column1Sheet2, "");
				line36.put(column2Sheet2, "");
				line36.put(column3Sheet2, TextResource.localize("KMK013_47"));
				line36.put(column4Sheet2, "");
				if (!Objects.isNull(
						holidayAddtionDto.getHourlyPaymentAdditionSet().getCalculateIncludeIntervalExemptionTime2())
						&& holidayAddtionDto.getHourlyPaymentAdditionSet()
								.getCalculateIncludeIntervalExemptionTime2() == 1) {

					line36.put(column5Sheet2, select);
				} else {
					line36.put(column5Sheet2, unselect);
				}
				MasterData masterData36 = new MasterData(line36, null, "");
				Map<String, MasterCellData> rowData36 = masterData36.getRowData();
				getAlignsheet2(rowData36);
				datas.add(masterData36);
				// end block2
			}
			// end tab2

			// start tab3
			// block1
			LoginUserContext loginUserContext = AppContexts.user();
			// get company id
			String companyId = loginUserContext.companyId();
			Optional<FlexWorkSet> optFlexWorkSet = flexWorkRepo.find(companyId);
			if (optFlexWorkSet.isPresent() && !Objects.isNull(optFlexWorkSet.get().getUseFlexWorkSetting())
					&& optFlexWorkSet.get().getUseFlexWorkSetting().value == 1) {

				Map<String, Object> tab3block1line1 = new HashMap<>();
				tab3block1line1.put(column1Sheet2, TextResource.localize("KMK013_26"));
				tab3block1line1.put(column2Sheet2, TextResource.localize("KMK013_48"));
				tab3block1line1.put(column3Sheet2, "");
				tab3block1line1.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getFlexWork())
						&& holidayAddtionDto.getFlexWork().getCalcActualOperationPre() == 1) {
					tab3block1line1.put(column5Sheet2, TextResource.localize("KMK013_51"));
				} else {
					tab3block1line1.put(column5Sheet2, TextResource.localize("KMK013_52"));
				}
				MasterData masterDatatab3block1line1 = new MasterData(tab3block1line1, null, "");
				Map<String, MasterCellData> rowDatatab3block1line1 = masterDatatab3block1line1.getRowData();
				getAlignsheet2(rowDatatab3block1line1);
				datas.add(masterDatatab3block1line1);
				if (!Objects.isNull(holidayAddtionDto.getFlexWork())
						&& holidayAddtionDto.getFlexWork().getCalcActualOperationPre() == 1) {

					Map<String, Object> tab3block1line2 = new HashMap<>();
					tab3block1line2.put(column1Sheet2, "");
					tab3block1line2.put(column2Sheet2, "");
					tab3block1line2.put(column3Sheet2, TextResource.localize("KMK013_53"));
					tab3block1line2.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getAdditionTimePre())
							&& holidayAddtionDto.getFlexWork().getAdditionTimePre() == 1) {
						tab3block1line2.put(column5Sheet2, select);
					} else {
						tab3block1line2.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab3block1line2 = new MasterData(tab3block1line2, null, "");
					Map<String, MasterCellData> rowDatatab3block1line2 = masterDatatab3block1line2.getRowData();
					getAlignsheet2(rowDatatab3block1line2);
					datas.add(masterDatatab3block1line2);
					//
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getAdditionTimePre())
							&& holidayAddtionDto.getFlexWork().getAdditionTimePre() == 1) {
						//
						Map<String, Object> tab3block1line3 = new HashMap<>();
						tab3block1line3.put(column1Sheet2, "");
						tab3block1line3.put(column2Sheet2, "");
						tab3block1line3.put(column3Sheet2, "");
						tab3block1line3.put(column4Sheet2, TextResource.localize("KMK013_54"));
						if (!Objects.isNull(holidayAddtionDto.getFlexWork())
								&& !Objects.isNull(holidayAddtionDto.getFlexWork().getPredeterminedOvertimePre())
								&& holidayAddtionDto.getFlexWork().getPredeterminedOvertimePre() == 1) {
							tab3block1line3.put(column5Sheet2, select);
						} else {
							tab3block1line3.put(column5Sheet2, unselect);
						}
						MasterData masterDatatab3block1line3 = new MasterData(tab3block1line3, null, "");
						Map<String, MasterCellData> rowDatatab3block1line3 = masterDatatab3block1line3.getRowData();
						getAlignsheet2(rowDatatab3block1line3);
						datas.add(masterDatatab3block1line3);
					}

					//
					Map<String, Object> tab3block1line4 = new HashMap<>();
					tab3block1line4.put(column1Sheet2, "");
					tab3block1line4.put(column2Sheet2, "");
					tab3block1line4.put(column3Sheet2, TextResource.localize("KMK013_55"));
					tab3block1line4.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getIncChildNursingCarePre())
							&& holidayAddtionDto.getFlexWork().getIncChildNursingCarePre() == 1) {
						tab3block1line4.put(column5Sheet2, select);
					} else {
						tab3block1line4.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab3block1line4 = new MasterData(tab3block1line4, null, "");
					Map<String, MasterCellData> rowDatatab3block1line4 = masterDatatab3block1line4.getRowData();
					getAlignsheet2(rowDatatab3block1line4);
					datas.add(masterDatatab3block1line4);
					//
					Map<String, Object> tab3block1line5 = new HashMap<>();
					tab3block1line5.put(column1Sheet2, "");
					tab3block1line5.put(column2Sheet2, "");
					tab3block1line5.put(column3Sheet2, TextResource.localize("KMK013_56"));
					tab3block1line5.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getNotDeductLateleavePre())
							&& holidayAddtionDto.getFlexWork().getNotDeductLateleavePre() == 1) {
						tab3block1line5.put(column5Sheet2, select);
					} else {
						tab3block1line5.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab3block1line5 = new MasterData(tab3block1line5, null, "");
					Map<String, MasterCellData> rowDatatab3block1line5 = masterDatatab3block1line5.getRowData();
					getAlignsheet2(rowDatatab3block1line5);
					datas.add(masterDatatab3block1line5);
					//
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getNotDeductLateleavePre())
							&& holidayAddtionDto.getFlexWork().getNotDeductLateleavePre() == 1) {
						//
						Map<String, Object> tab3block1line6 = new HashMap<>();
						tab3block1line6.put(column1Sheet2, "");
						tab3block1line6.put(column2Sheet2, "");
						tab3block1line6.put(column3Sheet2, "");
						tab3block1line6.put(column4Sheet2, TextResource.localize("KMK013_257"));
						if (!Objects.isNull(holidayAddtionDto.getFlexWork())
								&& !Objects.isNull(holidayAddtionDto.getFlexWork().getEnableSetPerWorkHour1())
								&& holidayAddtionDto.getFlexWork().getEnableSetPerWorkHour1() == 1) {
							tab3block1line6.put(column5Sheet2, select);
						} else {
							tab3block1line6.put(column5Sheet2, unselect);
						}
						MasterData masterDatatab3block1line6 = new MasterData(tab3block1line6, null, "");
						Map<String, MasterCellData> rowDatatab3block1line6 = masterDatatab3block1line6.getRowData();
						getAlignsheet2(rowDatatab3block1line6);
						datas.add(masterDatatab3block1line6);
					}

					//
					Map<String, Object> tab3block1line7 = new HashMap<>();
					tab3block1line7.put(column1Sheet2, "");
					tab3block1line7.put(column2Sheet2, "");
					tab3block1line7.put(column3Sheet2, TextResource.localize("KMK013_40"));
					tab3block1line7.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getExemptTaxTimePre())
							&& holidayAddtionDto.getFlexWork().getExemptTaxTimePre() == 1) {
						tab3block1line7.put(column5Sheet2, select);
					} else {
						tab3block1line7.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab3block1line7 = new MasterData(tab3block1line7, null, "");
					Map<String, MasterCellData> rowDatatab3block1line7 = masterDatatab3block1line7.getRowData();
					getAlignsheet2(rowDatatab3block1line7);
					datas.add(masterDatatab3block1line7);

				}

				// end block1

				// start block2
				Map<String, Object> tab3block2line1 = new HashMap<>();
				tab3block2line1.put(column1Sheet2, "");
				tab3block2line1.put(column2Sheet2, TextResource.localize("KMK013_49"));
				tab3block2line1.put(column3Sheet2, "");
				tab3block2line1.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getFlexWork())
						&& holidayAddtionDto.getFlexWork().getCalcActualOperationWork() == 1) {
					tab3block2line1.put(column5Sheet2, TextResource.localize("KMK013_58"));
				} else {
					tab3block2line1.put(column5Sheet2, TextResource.localize("KMK013_59"));
				}
				MasterData masterDatatab3block2line1 = new MasterData(tab3block2line1, null, "");
				Map<String, MasterCellData> rowDatatab3block2line1 = masterDatatab3block2line1.getRowData();
				getAlignsheet2(rowDatatab3block2line1);
				datas.add(masterDatatab3block2line1);
				//
				if (!Objects.isNull(holidayAddtionDto.getFlexWork())
						&& holidayAddtionDto.getFlexWork().getCalcActualOperationWork() == 1) {
					//
					Map<String, Object> tab3block2line2 = new HashMap<>();
					tab3block2line2.put(column1Sheet2, "");
					tab3block2line2.put(column2Sheet2, "");
					tab3block2line2.put(column3Sheet2, TextResource.localize("KMK013_60"));
					tab3block2line2.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getAdditionTimeWork())
							&& holidayAddtionDto.getFlexWork().getAdditionTimeWork() == 1) {
						tab3block2line2.put(column5Sheet2, select);
					} else {
						tab3block2line2.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab3block2line2 = new MasterData(tab3block2line2, null, "");
					Map<String, MasterCellData> rowDatatab3block2line2 = masterDatatab3block2line2.getRowData();
					getAlignsheet2(rowDatatab3block2line2);
					datas.add(masterDatatab3block2line2);
					//
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getAdditionTimeWork())
							&& holidayAddtionDto.getFlexWork().getAdditionTimeWork() == 1) {
						//
						Map<String, Object> tab3block2line3 = new HashMap<>();
						tab3block2line3.put(column1Sheet2, "");
						tab3block2line3.put(column2Sheet2, "");
						tab3block2line3.put(column3Sheet2, "");
						tab3block2line3.put(column4Sheet2, TextResource.localize("KMK013_61"));
						if (!Objects.isNull(holidayAddtionDto.getFlexWork())
								&& !Objects.isNull(holidayAddtionDto.getFlexWork().getPredeterminDeficiencyWork())
								&& holidayAddtionDto.getFlexWork().getPredeterminDeficiencyWork() == 1) {
							tab3block2line3.put(column5Sheet2, select);
						} else {
							tab3block2line3.put(column5Sheet2, unselect);
						}
						MasterData masterDatatab3block2line3 = new MasterData(tab3block2line3, null, "");
						Map<String, MasterCellData> rowDatatab3block2line3 = masterDatatab3block2line3.getRowData();
						getAlignsheet2(rowDatatab3block2line3);
						datas.add(masterDatatab3block2line3);
						//
						Map<String, Object> tab3block2line4 = new HashMap<>();
						tab3block2line4.put(column1Sheet2, "");
						tab3block2line4.put(column2Sheet2, "");
						tab3block2line4.put(column3Sheet2, "");
						tab3block2line4.put(column4Sheet2, TextResource.localize("KMK013_258"));
						if (!Objects.isNull(holidayAddtionDto.getFlexWork())
								&& !Objects.isNull(holidayAddtionDto.getFlexWork().getAdditionWithinMonthlyStatutory())
								&& holidayAddtionDto.getFlexWork().getAdditionWithinMonthlyStatutory() == 1) {
							tab3block2line4.put(column5Sheet2, select);
						} else {
							tab3block2line4.put(column5Sheet2, unselect);
						}
						MasterData masterDatatab3block2line4 = new MasterData(tab3block2line4, null, "");
						Map<String, MasterCellData> rowDatatab3block2line4 = masterDatatab3block2line4.getRowData();
						getAlignsheet2(rowDatatab3block2line4);
						datas.add(masterDatatab3block2line4);
					}

					//
					Map<String, Object> tab3block2line5 = new HashMap<>();
					tab3block2line5.put(column1Sheet2, "");
					tab3block2line5.put(column2Sheet2, "");
					tab3block2line5.put(column3Sheet2, TextResource.localize("KMK013_62"));
					tab3block2line5.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getIncChildNursingCareWork())
							&& holidayAddtionDto.getFlexWork().getIncChildNursingCareWork() == 1) {
						tab3block2line5.put(column5Sheet2, select);
					} else {
						tab3block2line5.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab3block2line5 = new MasterData(tab3block2line5, null, "");
					Map<String, MasterCellData> rowDatatab3block2line5 = masterDatatab3block2line5.getRowData();
					getAlignsheet2(rowDatatab3block2line5);
					datas.add(masterDatatab3block2line5);
					//
					Map<String, Object> tab3block2line6 = new HashMap<>();
					tab3block2line6.put(column1Sheet2, "");
					tab3block2line6.put(column2Sheet2, "");
					tab3block2line6.put(column3Sheet2, TextResource.localize("KMK013_63"));
					tab3block2line6.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getNotDeductLateleaveWork())
							&& holidayAddtionDto.getFlexWork().getNotDeductLateleaveWork() == 1) {
						tab3block2line6.put(column5Sheet2, select);
					} else {
						tab3block2line6.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab3block2line6 = new MasterData(tab3block2line6, null, "");
					Map<String, MasterCellData> rowDatatab3block2line6 = masterDatatab3block2line6.getRowData();
					getAlignsheet2(rowDatatab3block2line6);
					datas.add(masterDatatab3block2line6);
					//
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getNotDeductLateleaveWork())
							&& holidayAddtionDto.getFlexWork().getNotDeductLateleaveWork() == 1) {
						Map<String, Object> tab3block2line7 = new HashMap<>();
						tab3block2line7.put(column1Sheet2, "");
						tab3block2line7.put(column2Sheet2, "");
						tab3block2line7.put(column3Sheet2, "");
						tab3block2line7.put(column4Sheet2, TextResource.localize("KMK013_257"));
						if (!Objects.isNull(holidayAddtionDto.getFlexWork())
								&& !Objects.isNull(holidayAddtionDto.getFlexWork().getEnableSetPerWorkHour2())
								&& holidayAddtionDto.getFlexWork().getEnableSetPerWorkHour2() == 1) {
							tab3block2line7.put(column5Sheet2, select);
						} else {
							tab3block2line7.put(column5Sheet2, unselect);
						}
						MasterData masterDatatab3block2line7 = new MasterData(tab3block2line7, null, "");
						Map<String, MasterCellData> rowDatatab3block2line7 = masterDatatab3block2line7.getRowData();
						getAlignsheet2(rowDatatab3block2line7);
						datas.add(masterDatatab3block2line7);

					}

					//
					Map<String, Object> tab3block2line8 = new HashMap<>();
					tab3block2line8.put(column1Sheet2, "");
					tab3block2line8.put(column2Sheet2, "");
					tab3block2line8.put(column3Sheet2, TextResource.localize("KMK013_64"));
					tab3block2line8.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getExemptTaxTimeWork())
							&& holidayAddtionDto.getFlexWork().getExemptTaxTimeWork() == 1) {
						tab3block2line8.put(column5Sheet2, select);
					} else {
						tab3block2line8.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab3block2line8 = new MasterData(tab3block2line8, null, "");
					Map<String, MasterCellData> rowDatatab3block2line8 = masterDatatab3block2line8.getRowData();
					getAlignsheet2(rowDatatab3block2line8);
					datas.add(masterDatatab3block2line8);
					//
					Map<String, Object> tab3block2line9 = new HashMap<>();
					tab3block2line9.put(column1Sheet2, "");
					tab3block2line9.put(column2Sheet2, "");
					tab3block2line9.put(column3Sheet2, TextResource.localize("KMK013_65"));
					tab3block2line9.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getFlexWork())
							&& !Objects.isNull(holidayAddtionDto.getFlexWork().getMinusAbsenceTimeWork())
							&& holidayAddtionDto.getFlexWork().getMinusAbsenceTimeWork() == 1) {
						tab3block2line9.put(column5Sheet2, select);
					} else {
						tab3block2line9.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab3block2line9 = new MasterData(tab3block2line9, null, "");
					Map<String, MasterCellData> rowDatatab3block2line9 = masterDatatab3block2line9.getRowData();
					getAlignsheet2(rowDatatab3block2line9);
					datas.add(masterDatatab3block2line9);

				}
			}
			// end tab3
			// star tab4
			// block1
			Optional<AggDeformedLaborSetting> optAggSetting = aggSettingRepo.findByCid(companyId);
			if (optAggSetting.isPresent() && !Objects.isNull(optAggSetting.get().getUseDeformedLabor())
					&& optAggSetting.get().getUseDeformedLabor().value == 1) {

				Map<String, Object> tab4block1line1 = new HashMap<>();
				tab4block1line1.put(column1Sheet2, TextResource.localize("KMK013_27"));
				tab4block1line1.put(column2Sheet2, TextResource.localize("KMK013_66"));
				tab4block1line1.put(column3Sheet2, "");
				tab4block1line1.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
						&& holidayAddtionDto.getIrregularWork().getCalcActualOperationPre() == 1) {
					tab4block1line1.put(column5Sheet2, TextResource.localize("KMK013_69"));
				} else {
					tab4block1line1.put(column5Sheet2, TextResource.localize("KMK013_70"));
				}
				MasterData masterDatatab4block1line1 = new MasterData(tab4block1line1, null, "");
				Map<String, MasterCellData> rowDatatab4block1line1 = masterDatatab4block1line1.getRowData();
				getAlignsheet2(rowDatatab4block1line1);
				datas.add(masterDatatab4block1line1);
				//
				if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
						&& holidayAddtionDto.getIrregularWork().getCalcActualOperationPre() == 1) {

					Map<String, Object> tab4block1line2 = new HashMap<>();
					tab4block1line2.put(column1Sheet2, "");
					tab4block1line2.put(column2Sheet2, "");
					tab4block1line2.put(column3Sheet2, TextResource.localize("KMK013_33"));
					tab4block1line2.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
							&& holidayAddtionDto.getIrregularWork().getAdditionTimePre() == 1) {
						tab4block1line2.put(column5Sheet2, select);
					} else {
						tab4block1line2.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab4block1line2 = new MasterData(tab4block1line2, null, "");
					Map<String, MasterCellData> rowDatatab4block1line2 = masterDatatab4block1line2.getRowData();
					getAlignsheet2(rowDatatab4block1line2);
					datas.add(masterDatatab4block1line2);
					//

					if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
							&& holidayAddtionDto.getIrregularWork().getAdditionTimePre() == 1) {
						Map<String, Object> tab4block1line3 = new HashMap<>();
						tab4block1line3.put(column1Sheet2, "");
						tab4block1line3.put(column2Sheet2, "");
						tab4block1line3.put(column3Sheet2, "");
						tab4block1line3.put(column4Sheet2, TextResource.localize("KMK013_34"));
						if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
								&& holidayAddtionDto.getIrregularWork().getDeformatExcValue() == 1) {
							tab4block1line3.put(column5Sheet2, TextResource.localize("KMK013_37"));
						} else {
							tab4block1line3.put(column5Sheet2, TextResource.localize("KMK013_36"));
						}
						MasterData masterDatatab4block1line3 = new MasterData(tab4block1line3, null, "");
						Map<String, MasterCellData> rowDatatab4block1line3 = masterDatatab4block1line3.getRowData();
						getAlignsheet2(rowDatatab4block1line3);
						datas.add(masterDatatab4block1line3);
					}

					//
					Map<String, Object> tab4block1line4 = new HashMap<>();
					tab4block1line4.put(column1Sheet2, "");
					tab4block1line4.put(column2Sheet2, "");
					tab4block1line4.put(column3Sheet2, TextResource.localize("KMK013_76"));
					tab4block1line4.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
							&& holidayAddtionDto.getIrregularWork().getIncChildNursingCarePre() == 1) {
						tab4block1line4.put(column5Sheet2, select);
					} else {
						tab4block1line4.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab4block1line4 = new MasterData(tab4block1line4, null, "");
					Map<String, MasterCellData> rowDatatab4block1line4 = masterDatatab4block1line4.getRowData();
					getAlignsheet2(rowDatatab4block1line4);
					datas.add(masterDatatab4block1line4);
					//
					Map<String, Object> tab4block1line5 = new HashMap<>();
					tab4block1line5.put(column1Sheet2, "");
					tab4block1line5.put(column2Sheet2, "");
					tab4block1line5.put(column3Sheet2, TextResource.localize("KMK013_77"));
					tab4block1line5.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
							&& holidayAddtionDto.getIrregularWork().getNotDeductLateleavePre() == 1) {
						tab4block1line5.put(column5Sheet2, select);
					} else {
						tab4block1line5.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab4block1line5 = new MasterData(tab4block1line5, null, "");
					Map<String, MasterCellData> rowDatatab4block1line5 = masterDatatab4block1line5.getRowData();
					getAlignsheet2(rowDatatab4block1line5);
					datas.add(masterDatatab4block1line5);

					//
					if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
							&& holidayAddtionDto.getIrregularWork().getNotDeductLateleavePre() == 1) {
						//
						Map<String, Object> tab4block1line6 = new HashMap<>();
						tab4block1line6.put(column1Sheet2, "");
						tab4block1line6.put(column2Sheet2, "");
						tab4block1line6.put(column3Sheet2, "");
						tab4block1line6.put(column4Sheet2, TextResource.localize("KMK013_257"));
						if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
								&& holidayAddtionDto.getIrregularWork().getEnableSetPerWorkHour1() == 1) {
							tab4block1line6.put(column5Sheet2, select);
						} else {
							tab4block1line6.put(column5Sheet2, unselect);
						}
						MasterData masterDatatab4block1line6 = new MasterData(tab4block1line6, null, "");
						Map<String, MasterCellData> rowDatatab4block1line6 = masterDatatab4block1line6.getRowData();
						getAlignsheet2(rowDatatab4block1line6);
						datas.add(masterDatatab4block1line6);
					}

					//
					Map<String, Object> tab4block1line7 = new HashMap<>();
					tab4block1line7.put(column1Sheet2, "");
					tab4block1line7.put(column2Sheet2, "");
					tab4block1line7.put(column3Sheet2, TextResource.localize("KMK013_78"));
					tab4block1line7.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
							&& holidayAddtionDto.getIrregularWork().getExemptTaxTimePre() == 1) {
						tab4block1line7.put(column5Sheet2, select);
					} else {
						tab4block1line7.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab4block1line7 = new MasterData(tab4block1line7, null, "");
					Map<String, MasterCellData> rowDatatab4block1line7 = masterDatatab4block1line7.getRowData();
					getAlignsheet2(rowDatatab4block1line7);
					datas.add(masterDatatab4block1line7);
				}
				// end block1
				// start blok2
				Map<String, Object> tab4block2line1 = new HashMap<>();
				tab4block2line1.put(column1Sheet2, "");
				tab4block2line1.put(column2Sheet2, TextResource.localize("KMK013_67"));
				tab4block2line1.put(column3Sheet2, "");
				tab4block2line1.put(column4Sheet2, "");
				if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
						&& holidayAddtionDto.getIrregularWork().getCalcActualOperationWork() == 1) {
					tab4block2line1.put(column5Sheet2, TextResource.localize("KMK013_80"));
				} else {
					tab4block2line1.put(column5Sheet2, TextResource.localize("KMK013_81"));
				}
				MasterData masterDatatab4block2line1 = new MasterData(tab4block2line1, null, "");
				Map<String, MasterCellData> rowDatatab4block2line1 = masterDatatab4block2line1.getRowData();
				getAlignsheet2(rowDatatab4block2line1);
				datas.add(masterDatatab4block2line1);
				if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
						&& holidayAddtionDto.getIrregularWork().getCalcActualOperationWork() == 1) {
					//
					Map<String, Object> tab4block2line2 = new HashMap<>();
					tab4block2line2.put(column1Sheet2, "");
					tab4block2line2.put(column2Sheet2, "");
					tab4block2line2.put(column3Sheet2, TextResource.localize("KMK013_82"));
					tab4block2line2.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
							&& holidayAddtionDto.getIrregularWork().getAdditionTimeWork() == 1) {
						tab4block2line2.put(column5Sheet2, select);
					} else {
						tab4block2line2.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab4block2line2 = new MasterData(tab4block2line2, null, "");
					Map<String, MasterCellData> rowDatatab4block2line2 = masterDatatab4block2line2.getRowData();
					getAlignsheet2(rowDatatab4block2line2);
					datas.add(masterDatatab4block2line2);
					//
					Map<String, Object> tab4block2line3 = new HashMap<>();
					tab4block2line3.put(column1Sheet2, "");
					tab4block2line3.put(column2Sheet2, "");
					tab4block2line3.put(column3Sheet2, TextResource.localize("KMK013_83"));
					tab4block2line3.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
							&& holidayAddtionDto.getIrregularWork().getIncChildNursingCareWork() == 1) {
						tab4block2line3.put(column5Sheet2, select);
					} else {
						tab4block2line3.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab4block2line3 = new MasterData(tab4block2line3, null, "");
					Map<String, MasterCellData> rowDatatab4block2line3 = masterDatatab4block2line3.getRowData();
					getAlignsheet2(rowDatatab4block2line3);
					datas.add(masterDatatab4block2line3);
					//
					Map<String, Object> tab4block2line4 = new HashMap<>();
					tab4block2line4.put(column1Sheet2, "");
					tab4block2line4.put(column2Sheet2, "");
					tab4block2line4.put(column3Sheet2, TextResource.localize("KMK013_84"));
					tab4block2line4.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
							&& holidayAddtionDto.getIrregularWork().getNotDeductLateleaveWork() == 1) {
						tab4block2line4.put(column5Sheet2, select);
					} else {
						tab4block2line4.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab4block2line4 = new MasterData(tab4block2line4, null, "");
					Map<String, MasterCellData> rowDatatab4block2line4 = masterDatatab4block2line4.getRowData();
					getAlignsheet2(rowDatatab4block2line4);
					datas.add(masterDatatab4block2line4);
					//
					if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
							&& holidayAddtionDto.getIrregularWork().getNotDeductLateleaveWork() == 1) {
						Map<String, Object> tab4block2line5 = new HashMap<>();
						tab4block2line5.put(column1Sheet2, "");
						tab4block2line5.put(column2Sheet2, "");
						tab4block2line5.put(column3Sheet2, "");
						tab4block2line5.put(column4Sheet2, TextResource.localize("KMK013_257"));
						if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
								&& holidayAddtionDto.getIrregularWork().getEnableSetPerWorkHour2() == 1) {
							tab4block2line5.put(column5Sheet2, select);
						} else {
							tab4block2line5.put(column5Sheet2, unselect);
						}
						MasterData masterDatatab4block2line5 = new MasterData(tab4block2line5, null, "");
						Map<String, MasterCellData> rowDatatab4block2line5 = masterDatatab4block2line5.getRowData();
						getAlignsheet2(rowDatatab4block2line5);
						datas.add(masterDatatab4block2line5);
					}

					//
					Map<String, Object> tab4block2line6 = new HashMap<>();
					tab4block2line6.put(column1Sheet2, "");
					tab4block2line6.put(column2Sheet2, "");
					tab4block2line6.put(column3Sheet2, TextResource.localize("KMK013_85"));
					tab4block2line6.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
							&& holidayAddtionDto.getIrregularWork().getExemptTaxTimeWork() == 1) {
						tab4block2line6.put(column5Sheet2, select);
					} else {
						tab4block2line6.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab4block2line6 = new MasterData(tab4block2line6, null, "");
					Map<String, MasterCellData> rowDatatab4block2line6 = masterDatatab4block2line6.getRowData();
					getAlignsheet2(rowDatatab4block2line6);
					datas.add(masterDatatab4block2line6);
					//
					Map<String, Object> tab4block2line7 = new HashMap<>();
					tab4block2line7.put(column1Sheet2, "");
					tab4block2line7.put(column2Sheet2, "");
					tab4block2line7.put(column3Sheet2, TextResource.localize("KMK013_86"));
					tab4block2line7.put(column4Sheet2, "");
					if (!Objects.isNull(holidayAddtionDto.getIrregularWork())
							&& holidayAddtionDto.getIrregularWork().getMinusAbsenceTimeWork() == 1) {
						tab4block2line7.put(column5Sheet2, select);
					} else {
						tab4block2line7.put(column5Sheet2, unselect);
					}
					MasterData masterDatatab4block2line7 = new MasterData(tab4block2line7, null, "");
					Map<String, MasterCellData> rowDatatab4block2line7 = masterDatatab4block2line7.getRowData();
					getAlignsheet2(rowDatatab4block2line7);
					datas.add(masterDatatab4block2line7);

				}
				// end block2
			}
			// end tab4

		}

		return datas;

	}

	// star sheet 3

	private List<MasterHeaderColumn> getHeaderColumnsLateNightTimeZoneSetting(MasterListExportQuery query) {

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet3, TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(column2Sheet3, "", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn(column3Sheet3, TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "",
				true));

		return columns;

	}

	private List<MasterData> getDataLateNightTimeZoneSetting(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();

		List<MidnightTimeSheetDto> rs = midnightTimeSheetFinder.findAllMidnightTimeSheet();
		if (!CollectionUtil.isEmpty(rs)) {
			//
			Map<String, Object> data1 = new HashMap<>();
			MidnightTimeSheetDto midnightTimeSheetDto = rs.get(0);
			data1.put(column1Sheet3, TextResource.localize("KMK013_363"));
			data1.put(column2Sheet3, TextResource.localize("KMK013_364"));
			data1.put(column3Sheet3, formatDay(midnightTimeSheetDto.getStartTime(), false));
			MasterData masterData1 = new MasterData(data1, null, "");
			Map<String, MasterCellData> rowData1 = masterData1.getRowData();
			rowData1.get(column1Sheet3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData1.get(column2Sheet3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData1.get(column3Sheet3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
			datas.add(masterData1);
			//
			Map<String, Object> data2 = new HashMap<>();
			data2.put(column1Sheet3, "");
			data2.put(column2Sheet3, TextResource.localize("KMK013_366"));
			data2.put(column3Sheet3, formatDay(midnightTimeSheetDto.getEndTime(), true));

			MasterData masterData2 = new MasterData(data2, null, "");
			Map<String, MasterCellData> rowData2 = masterData2.getRowData();
			rowData2.get(column1Sheet3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData2.get(column2Sheet3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData2.get(column3Sheet3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
			datas.add(masterData2);
		}

		return datas;
	}
	// end shet3

	// start sheet 4

	private List<MasterHeaderColumn> getHeaderColumnsEmbossSetting(MasterListExportQuery query) {

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet4, TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(column2Sheet4, "", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn(column3Sheet4, TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "",
				true));

		return columns;

	}

	private List<MasterData> getDataEmbossSetting(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		ManageEntryExitDto manageEntryExitDto = manageEntryExitFinder.findData();
		manageEntryExitDto.getUseClassification();

		//
		Map<String, Object> data1 = new HashMap<>();
		data1.put(column1Sheet4, TextResource.localize("KMK013_226"));
		data1.put(column2Sheet4, TextResource.localize("KMK013_227"));
		if (!Objects.isNull(manageEntryExitDto) && !Objects.isNull(manageEntryExitDto.getUseClassification())) {
			data1.put(column3Sheet4, getUseClassificationAt(manageEntryExitDto.getUseClassification()));
		} else {
			data1.put(column3Sheet4, "");
		}
		MasterData masterData1 = new MasterData(data1, null, "");
		Map<String, MasterCellData> rowData1 = masterData1.getRowData();
		getAlignLeftsheet4(rowData1);
		datas.add(masterData1);
		//
		Map<String, Object> data2 = new HashMap<>();
		data2.put(column1Sheet4, TextResource.localize("KMK013_289"));
		data2.put(column2Sheet4, TextResource.localize("KMK013_290"));
		OutManageDto outManageDto = outManageFinder.findData();
		if (!Objects.isNull(outManageDto) && !Objects.isNull(outManageDto.getMaxUsage())) {
			data2.put(column3Sheet4, outManageDto.getMaxUsage() + "回");
		} else {
			data2.put(column3Sheet4, "");
		}
		MasterData masterData2 = new MasterData(data2, null, "");
		Map<String, MasterCellData> rowData2 = masterData2.getRowData();
		getAlignRightsheet4(rowData2);
		datas.add(masterData2);
		//

		Map<String, Object> data3 = new HashMap<>();
		data3.put(column1Sheet4, "");
		data3.put(column2Sheet4, TextResource.localize("KMK013_292"));

		if (!Objects.isNull(outManageDto) && !Objects.isNull(outManageDto.getInitValueReasonGoOut())) {
			data3.put(column3Sheet4, getInitValueReasonGoOut(outManageDto.getInitValueReasonGoOut()));
		} else {
			data3.put(column3Sheet4, "");
		}
		MasterData masterData3 = new MasterData(data3, null, "");
		Map<String, MasterCellData> rowData3 = masterData3.getRowData();
		getAlignLeftsheet4(rowData3);
		datas.add(masterData3);
		//
		StampReflectionManagementDto stampReflectionManagementDto = stampReflectionManagementFinder.findByCode();
		if (!Objects.isNull(stampReflectionManagementDto)) {
			Map<String, Object> data4 = new HashMap<>();
			data4.put(column1Sheet4, TextResource.localize("KMK013_277"));
			data4.put(column2Sheet4, "");
			if (!Objects.isNull(stampReflectionManagementDto.getAutoStampReflectionClass())) {

				data4.put(column3Sheet4, stampReflectionManagementDto.getAutoStampReflectionClass() == 1
						? TextResource.localize("KMK013_279") : TextResource.localize("KMK013_280"));
			} else {
				data4.put(column3Sheet4, "");
			}
			MasterData masterData4 = new MasterData(data4, null, "");
			Map<String, MasterCellData> rowData4 = masterData4.getRowData();
			getAlignLeftsheet4(rowData4);
			datas.add(masterData4);
			//

			Map<String, Object> data5 = new HashMap<>();
			data5.put(column1Sheet4, TextResource.localize("KMK013_281"));
			data5.put(column2Sheet4, "");
			if (!Objects.isNull(stampReflectionManagementDto.getAutoStampForFutureDayClass())) {

				data5.put(column3Sheet4, stampReflectionManagementDto.getAutoStampForFutureDayClass() == 1
						? TextResource.localize("KMK013_283") : TextResource.localize("KMK013_284"));
			} else {
				data5.put(column3Sheet4, "");
			}
			MasterData masterData5 = new MasterData(data5, null, "");
			Map<String, MasterCellData> rowData5 = masterData5.getRowData();
			getAlignLeftsheet4(rowData5);
			datas.add(masterData5);
			//
			Map<String, Object> data6 = new HashMap<>();
			data6.put(column1Sheet4, TextResource.localize("KMK013_294"));
			data6.put(column2Sheet4, "");
			if (!Objects.isNull(stampReflectionManagementDto.getGoBackOutCorrectionClass())) {
				data6.put(column3Sheet4, stampReflectionManagementDto.getGoBackOutCorrectionClass() == 1
						? TextResource.localize("KMK013_296") : TextResource.localize("KMK013_297"));
			} else {
				data6.put(column3Sheet4, "");
			}
			MasterData masterData6 = new MasterData(data6, null, "");
			Map<String, MasterCellData> rowData6 = masterData6.getRowData();
			getAlignLeftsheet4(rowData6);
			datas.add(masterData6);
			//
			Map<String, Object> data7 = new HashMap<>();
			data7.put(column1Sheet4, TextResource.localize("KMK013_285"));
			data7.put(column2Sheet4, "");
			if (!Objects.isNull(stampReflectionManagementDto.getReflectWorkingTimeClass())) {
				data7.put(column3Sheet4, stampReflectionManagementDto.getReflectWorkingTimeClass() == 1
						? TextResource.localize("KMK013_287") : TextResource.localize("KMK013_288"));
			} else {
				data7.put(column3Sheet4, "");
			}
			MasterData masterData7 = new MasterData(data7, null, "");
			Map<String, MasterCellData> rowData7 = masterData7.getRowData();
			getAlignLeftsheet4(rowData7);
			datas.add(masterData7);
			//
			Map<String, Object> data8 = new HashMap<>();
			data8.put(column1Sheet4, TextResource.localize("KMK013_298"));
			data8.put(column2Sheet4, "");
			if (!Objects.isNull(stampReflectionManagementDto.getActualStampOfPriorityClass())) {
				data8.put(column3Sheet4, stampReflectionManagementDto.getActualStampOfPriorityClass() == 1
						? TextResource.localize("KMK013_301") : TextResource.localize("KMK013_300"));
			} else {
				data8.put(column3Sheet4, "");
			}
			MasterData masterData8 = new MasterData(data8, null, "");
			Map<String, MasterCellData> rowData8 = masterData8.getRowData();
			getAlignLeftsheet4(rowData8);
			datas.add(masterData8);
			//
			Map<String, Object> data9 = new HashMap<>();
			data9.put(column1Sheet4, TextResource.localize("KMK013_302"));
			data9.put(column2Sheet4, "");
			if (!Objects.isNull(stampReflectionManagementDto.getBreakSwitchClass())) {
				data9.put(column3Sheet4, stampReflectionManagementDto.getBreakSwitchClass() == 1
						? TextResource.localize("KMK013_305") : TextResource.localize("KMK013_304"));
			} else {
				data9.put(column3Sheet4, "");
			}
			MasterData masterData9 = new MasterData(data9, null, "");
			Map<String, MasterCellData> rowData9 = masterData9.getRowData();
			getAlignLeftsheet4(rowData9);
			datas.add(masterData9);
		}

		return datas;
	}

	private void getAlignLeftsheet4(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet4).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet4).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet4).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));

	}

	private void getAlignRightsheet4(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet4).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet4).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet4).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));

	}

	private String getInitValueReasonGoOut(int attr) {
		String value = "";
		switch (attr) {
		case 0:
			value = "私用";
			break;
		case 1:
			value = "公用";
			break;
		case 2:
			value = "有償";
			break;
		case 3:
			value = "組合";
			break;
		default:
			break;
		}
		return value;

	}

	// end sheet 4

	// start sheet5
	private List<MasterHeaderColumn> getHeaderColumnsTemporaryWorkSetting(MasterListExportQuery query) {

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet5, TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(column2Sheet5, "", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn(column3Sheet5, TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "",
				true));
		return columns;
	}

	private List<MasterData> getDataTemporaryWorkSetting(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		ManageWorkTemporaryDto manageWorkTemporaryDto = manageWorkTemporaryFinder.findData();
		if (!Objects.isNull(manageWorkTemporaryDto)) {
			Map<String, Object> data1 = new HashMap<>();
			data1.put(column1Sheet5, TextResource.localize("KMK013_345"));
			data1.put(column2Sheet5, TextResource.localize("KMK013_348"));
			if (!Objects.isNull(manageWorkTemporaryDto.getMaxUsage())) {
				data1.put(column3Sheet5, manageWorkTemporaryDto.getMaxUsage());
			} else {
				data1.put(column3Sheet5, "");
			}
			MasterData masterData1 = new MasterData(data1, null, "");
			Map<String, MasterCellData> rowData1 = masterData1.getRowData();
			getAlignRightsheet5(rowData1);
			datas.add(masterData1);
			//
			Map<String, Object> data2 = new HashMap<>();
			data2.put(column1Sheet5, "");
			data2.put(column2Sheet5, TextResource.localize("KMK013_349"));
			if (!Objects.isNull(manageWorkTemporaryDto.getTimeTreatTemporarySame())) {
				data2.put(column3Sheet5, formatValueSheet5(manageWorkTemporaryDto.getTimeTreatTemporarySame()));
			} else {
				data2.put(column3Sheet5, "");
			}
			MasterData masterData2 = new MasterData(data2, null, "");
			Map<String, MasterCellData> rowData2 = masterData2.getRowData();
			getAlignRightsheet5(rowData2);
			datas.add(masterData2);
		}
		return datas;
	}

	private void getAlignRightsheet5(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet5).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet5).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet5).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));

	}
	// end sheet5

	// start sheet 6
	private List<MasterHeaderColumn> getHeaderColumnsSettingOfDeformedLabor(MasterListExportQuery query) {

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet6, TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(column2Sheet6, "", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn(column3Sheet6, TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "",
				true));
		return columns;
	}

	private List<MasterData> getDataSettingOfDeformedLabor(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		List<DeformLaborOTDto> rs = deformLaborOTFinder.findAllDeformLaborOT();
		if (!CollectionUtil.isEmpty(rs)) {
			DeformLaborOTDto deformLaborOTDto = rs.get(0);
			Map<String, Object> data1 = new HashMap<>();
			data1.put(column1Sheet6, TextResource.localize("KMK013_306"));
			data1.put(column2Sheet6, TextResource.localize("KMK013_307"));
			if (!Objects.isNull(deformLaborOTDto.getLegalOtCalc())) {
				if (deformLaborOTDto.getLegalOtCalc().equals(NotUseAtr.USE)) {
					data1.put(column3Sheet6, TextResource.localize("KMK013_209"));
				} else if (deformLaborOTDto.getLegalOtCalc().equals(NotUseAtr.NOT_USE)) {
					data1.put(column3Sheet6, TextResource.localize("KMK013_210"));
				} else {
					data1.put(column3Sheet6, "");
				}

			} else {
				data1.put(column3Sheet6, "");
			}
			MasterData masterData1 = new MasterData(data1, null, "");
			Map<String, MasterCellData> rowData1 = masterData1.getRowData();
			getAlignLeftsheet6(rowData1);
			datas.add(masterData1);
		}
		return datas;
	}

	private void getAlignLeftsheet6(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet5).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet5).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet5).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));

	}
	// end sheet6

	// star sheet 7
	private List<MasterHeaderColumn> getHeaderColumnsFlexWorkSetting(MasterListExportQuery query) {

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet7, TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(column2Sheet7, "", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn(column3Sheet7, TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "",
				true));
		return columns;
	}

	private List<MasterData> getDataFlexWorkSetting(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		List<FlexSetDto> rs = flexSetFinder.findAllFlexSet();
		if (!CollectionUtil.isEmpty(rs)) {
			FlexSetDto flexSetDto = rs.get(0);
			Map<String, Object> data1 = new HashMap<>();
			data1.put(column1Sheet7, TextResource.localize("KMK013_178"));
			data1.put(column2Sheet7, TextResource.localize("KMK013_179"));
			if (!Objects.isNull(flexSetDto.getPremiumCalcHd())) {
				if (flexSetDto.getPremiumCalcHd() == 1) {
					data1.put(column3Sheet7, TextResource.localize("KMK013_182"));
				} else if (flexSetDto.getPremiumCalcHd() == 0) {
					data1.put(column3Sheet7, TextResource.localize("KMK013_181") + TextResource.localize("KMK013_183"));
				} else {
					data1.put(column3Sheet7, "");
				}

			} else {
				data1.put(column3Sheet7, "");
			}
			MasterData masterData1 = new MasterData(data1, null, "");
			Map<String, MasterCellData> rowData1 = masterData1.getRowData();
			getAlignLeftsheet7(rowData1);
			datas.add(masterData1);
			//

			Map<String, Object> data2 = new HashMap<>();
			data2.put(column1Sheet7, "");
			data2.put(column2Sheet7, TextResource.localize("KMK013_184"));
			if (!Objects.isNull(flexSetDto.getMissCalcHd())) {
				if (flexSetDto.getMissCalcHd() == 1) {
					data2.put(column3Sheet7, TextResource.localize("KMK013_187"));
				} else if (flexSetDto.getMissCalcHd() == 0) {
					data2.put(column3Sheet7, TextResource.localize("KMK013_186"));
				} else {
					data2.put(column3Sheet7, "");
				}

			} else {
				data2.put(column3Sheet7, "");
			}
			MasterData masterData2 = new MasterData(data2, null, "");
			Map<String, MasterCellData> rowData2 = masterData2.getRowData();
			getAlignLeftsheet7(rowData2);
			datas.add(masterData2);
			//

			Map<String, Object> data3 = new HashMap<>();
			data3.put(column1Sheet7, TextResource.localize("KMK013_188"));
			data3.put(column2Sheet7, TextResource.localize("KMK013_189"));
			if (!Objects.isNull(flexSetDto.getPremiumCalcSubhd())) {
				if (flexSetDto.getPremiumCalcSubhd() == 1) {
					data3.put(column3Sheet7, TextResource.localize("KMK013_192"));
				} else if (flexSetDto.getPremiumCalcSubhd() == 0) {
					data3.put(column3Sheet7, TextResource.localize("KMK013_191") + TextResource.localize("KMK013_193"));
				} else {
					data3.put(column3Sheet7, "");
				}

			} else {
				data3.put(column3Sheet7, "");
			}
			MasterData masterData3 = new MasterData(data3, null, "");
			Map<String, MasterCellData> rowData3 = masterData3.getRowData();
			getAlignLeftsheet7(rowData3);
			datas.add(masterData3);
			//
			Map<String, Object> data4 = new HashMap<>();
			data4.put(column1Sheet7, "");
			data4.put(column2Sheet7, TextResource.localize("KMK013_194"));
			if (!Objects.isNull(flexSetDto.getMissCalcSubhd())) {
				if (flexSetDto.getMissCalcSubhd() == 1) {
					data4.put(column3Sheet7, TextResource.localize("KMK013_197"));
				} else if (flexSetDto.getMissCalcSubhd() == 0) {
					data4.put(column3Sheet7, TextResource.localize("KMK013_196"));
				} else {
					data4.put(column3Sheet7, "");
				}

			} else {
				data4.put(column3Sheet7, "");
			}
			MasterData masterData4 = new MasterData(data4, null, "");
			Map<String, MasterCellData> rowData4 = masterData4.getRowData();
			getAlignLeftsheet7(rowData4);
			datas.add(masterData4);
			//
			Map<String, Object> data5 = new HashMap<>();
			data5.put(column1Sheet7, TextResource.localize("KMK013_267"));
			data5.put(column2Sheet7, "");
			if (!Objects.isNull(flexSetDto.getFlexNonworkingDayCalc())) {
				if (flexSetDto.getFlexNonworkingDayCalc() == 1) {
					data5.put(column3Sheet7, TextResource.localize("KMK013_269"));
				} else if (flexSetDto.getFlexNonworkingDayCalc() == 0) {
					data5.put(column3Sheet7, TextResource.localize("KMK013_268"));
				} else {
					data5.put(column3Sheet7, "");
				}

			} else {
				data5.put(column3Sheet7, "");
			}
			MasterData masterData5 = new MasterData(data5, null, "");
			Map<String, MasterCellData> rowData5 = masterData5.getRowData();
			getAlignLeftsheet7(rowData5);
			datas.add(masterData5);
			//
			Map<String, Object> data6 = new HashMap<>();
			data6.put(column1Sheet7, TextResource.localize("KMK013_262"));
			data6.put(column2Sheet7, "");
			if (!Objects.isNull(flexSetDto.getFlexDeductTimeCalc())) {
				if (flexSetDto.getFlexDeductTimeCalc() == 0) {
					data6.put(column3Sheet7, TextResource.localize("KMK013_264"));
				} else if (flexSetDto.getFlexDeductTimeCalc() == 1) {
					data6.put(column3Sheet7, TextResource.localize("KMK013_265"));
				} else if (flexSetDto.getFlexDeductTimeCalc() == 2) {
					data6.put(column3Sheet7, TextResource.localize("KMK013_266"));
				} else {
					data6.put(column3Sheet7, "");
				}
			} else {
				data6.put(column3Sheet7, "");
			}
			MasterData masterData6 = new MasterData(data6, null, "");
			Map<String, MasterCellData> rowData6 = masterData6.getRowData();
			getAlignLeftsheet7(rowData6);
			datas.add(masterData6);
			//
			List<InsufficientFlexHolidayMntDto> listrs = insuffFinder.findAllInsufficientFlexHolidayMnt();
			if (!CollectionUtil.isEmpty(listrs)) {
				InsufficientFlexHolidayMntDto insufficientFlexHolidayMntDto = listrs.get(0);
				Map<String, Object> data8 = new HashMap<>();
				data8.put(column1Sheet7, TextResource.localize("KMK013_270"));
				data8.put(column2Sheet7, TextResource.localize("KMK013_271"));
				if (!Objects.isNull(insufficientFlexHolidayMntDto.getSupplementableDays())) {
					data8.put(column3Sheet7, insufficientFlexHolidayMntDto.getSupplementableDays() + "日");
				} else {
					data8.put(column3Sheet7,"");
				}
				MasterData masterData8 = new MasterData(data8, null, "");
				Map<String, MasterCellData> rowData8 = masterData8.getRowData();
				getAlignRightsheet7(rowData8);
				datas.add(masterData8);
			}else{				
				Map<String, Object> data8 = new HashMap<>();
				data8.put(column1Sheet7, TextResource.localize("KMK013_270"));
				data8.put(column2Sheet7, TextResource.localize("KMK013_271"));				
				data8.put(column3Sheet7,defautsuppDays+"日");		
				MasterData masterData8 = new MasterData(data8, null, "");
				Map<String, MasterCellData> rowData8 = masterData8.getRowData();
				getAlignRightsheet7(rowData8);
				datas.add(masterData8);
			}

		}

		return datas;

	}

	private void getAlignRightsheet7(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet7).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet7).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet7).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));

	}

	private void getAlignLeftsheet7(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet7).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet7).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet7).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));

	}

	// end sheet 7
	// start sheet 8

	private List<MasterHeaderColumn> getHeaderColumnsStatutorySettings(MasterListExportQuery query) {

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet8, TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(column2Sheet8, "", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn(column3Sheet8, TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "",
				true));
		return columns;
	}

	private List<MasterData> getDataStatutorySettings(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		List<RoleOvertimeWorkDto> rs = roleOvertimeWorkFinder.findData();
		List<OvertimeWorkFrameFindDto> rsName = overtimeWorkFrameFinder.findAll().stream()
				.filter(x -> x.getUseAtr() == 1).collect(Collectors.toList());
		rsName.sort((OvertimeWorkFrameFindDto o1, OvertimeWorkFrameFindDto o2) -> o1.getOvertimeWorkFrNo()
				- o2.getOvertimeWorkFrNo());
		// tab2
		List<WorkdayoffFrameFindDto> rsworkday = workdayoffFrameFinder.findAll().stream()
				.filter(x -> x.getUseAtr() == 1).collect(Collectors.toList());
		rsworkday.sort((WorkdayoffFrameFindDto o1, WorkdayoffFrameFindDto o2) -> o1.getWorkdayoffFrNo()
				- o2.getWorkdayoffFrNo());
		List<RoleOfOpenPeriodDto> listRoleOpen = roleOfOpenPeriodFinder.findData();
		if (!CollectionUtil.isEmpty(rs) && !CollectionUtil.isEmpty(rsName) && rs.size() >= rsName.size()) {

			for (int i = 0; i < rsName.size(); i++) {
				/*
				 * RoleOvertimeWorkDto roleOvertimeWorkDto=rs.get(i); if(i==0){
				 * Map<String, Object> data = new HashMap<>();
				 * data.put(column1Sheet8,TextResource.localize("KMK013_383"));
				 * data.put(column2Sheet8,TextResource.localize("KMK013_152")+
				 * roleOvertimeWorkDto.getOvertimeFrNo()+rsName.get(i).
				 * getOvertimeWorkFrName());
				 * data.put(column3Sheet8,getNameRoleOTWork(roleOvertimeWorkDto.
				 * getRoleOTWork())); MasterData masterData = new
				 * MasterData(data, null, ""); Map<String, MasterCellData>
				 * rowData = masterData.getRowData();
				 * getAlignLeftsheet8(rowData); datas.add(masterData); }else{
				 * Map<String, Object> data = new HashMap<>();
				 * data.put(column1Sheet8,"");
				 * data.put(column2Sheet8,TextResource.localize("KMK013_152")+
				 * roleOvertimeWorkDto.getOvertimeFrNo()+rsName.get(i).
				 * getOvertimeWorkFrName());
				 * data.put(column3Sheet8,getNameRoleOTWork(roleOvertimeWorkDto.
				 * getRoleOTWork())); MasterData masterData = new
				 * MasterData(data, null, ""); Map<String, MasterCellData>
				 * rowData = masterData.getRowData();
				 * getAlignLeftsheet8(rowData); datas.add(masterData); }
				 */
				OvertimeWorkFrameFindDto overtimeWorkFrameFindDto = rsName.get(i);
				// RoleOvertimeWorkDto roleOvertimeWorkDto=rs.get(i);
				if (i == 0) {

					for (RoleOvertimeWorkDto roleOvertimeWorkDto : rs) {
						if (roleOvertimeWorkDto.getOvertimeFrNo() == overtimeWorkFrameFindDto.getOvertimeWorkFrNo()) {
							Map<String, Object> data = new HashMap<>();
							data.put(column1Sheet8, TextResource.localize("KMK013_383"));
							data.put(column2Sheet8,
									TextResource.localize("KMK013_152") + overtimeWorkFrameFindDto.getOvertimeWorkFrNo()
											+ overtimeWorkFrameFindDto.getOvertimeWorkFrName());
							data.put(column3Sheet8, getNameRoleOTWork(roleOvertimeWorkDto.getRoleOTWork()));
							MasterData masterData = new MasterData(data, null, "");
							Map<String, MasterCellData> rowData = masterData.getRowData();
							getAlignLeftsheet8(rowData);
							datas.add(masterData);
							break;
						}

					}

				} else {
					for (RoleOvertimeWorkDto roleOvertimeWorkDto : rs) {
						if (roleOvertimeWorkDto.getOvertimeFrNo() == overtimeWorkFrameFindDto.getOvertimeWorkFrNo()) {
							Map<String, Object> data = new HashMap<>();
							data.put(column1Sheet8, "");
							data.put(column2Sheet8,
									TextResource.localize("KMK013_152") + overtimeWorkFrameFindDto.getOvertimeWorkFrNo()
											+ overtimeWorkFrameFindDto.getOvertimeWorkFrName());
							data.put(column3Sheet8, getNameRoleOTWork(roleOvertimeWorkDto.getRoleOTWork()));
							MasterData masterData = new MasterData(data, null, "");
							Map<String, MasterCellData> rowData = masterData.getRowData();
							getAlignLeftsheet8(rowData);
							datas.add(masterData);
							break;
						}
					}

				}

			}
		} else {
			Map<String, Object> data = new HashMap<>();
			data.put(column1Sheet8, TextResource.localize("KMK013_383"));
			data.put(column2Sheet8, "");
			data.put(column3Sheet8, "");
			MasterData masterData = new MasterData(data, null, "");
			Map<String, MasterCellData> rowData = masterData.getRowData();
			getAlignLeftsheet8(rowData);
			datas.add(masterData);
		}

		//
		if (!CollectionUtil.isEmpty(rsworkday) && !CollectionUtil.isEmpty(listRoleOpen)
				&& listRoleOpen.size() >= rsworkday.size()) {

			for (int i = 0; i < rsworkday.size(); i++) {
				WorkdayoffFrameFindDto workdayoffFrameFindDto = rsworkday.get(i);
				if (i == 0) {
					for (RoleOfOpenPeriodDto roleOfOpenPeriodDto : listRoleOpen) {
						if (roleOfOpenPeriodDto.getBreakoutFrNo() == workdayoffFrameFindDto.getWorkdayoffFrNo()) {
							Map<String, Object> data = new HashMap<>();
							data.put(column1Sheet8, TextResource.localize("KMK013_384"));
							data.put(column2Sheet8,
									TextResource.localize("KMK013_157") + workdayoffFrameFindDto.getWorkdayoffFrNo()
											+ workdayoffFrameFindDto.getWorkdayoffFrName());
							data.put(column3Sheet8,
									getNameRoleOfOpenPeriodEnum(roleOfOpenPeriodDto.getRoleOfOpenPeriod()));
							MasterData masterData = new MasterData(data, null, "");
							Map<String, MasterCellData> rowData = masterData.getRowData();
							getAlignLeftsheet8(rowData);
							datas.add(masterData);
							break;
						}
					}

				} else {
					for (RoleOfOpenPeriodDto roleOfOpenPeriodDto : listRoleOpen) {
						if (roleOfOpenPeriodDto.getBreakoutFrNo() == workdayoffFrameFindDto.getWorkdayoffFrNo()) {
							Map<String, Object> data = new HashMap<>();
							data.put(column1Sheet8, "");
							data.put(column2Sheet8,
									TextResource.localize("KMK013_157") + workdayoffFrameFindDto.getWorkdayoffFrNo()
											+ workdayoffFrameFindDto.getWorkdayoffFrName());
							data.put(column3Sheet8,
									getNameRoleOfOpenPeriodEnum(roleOfOpenPeriodDto.getRoleOfOpenPeriod()));
							MasterData masterData = new MasterData(data, null, "");
							Map<String, MasterCellData> rowData = masterData.getRowData();
							getAlignLeftsheet8(rowData);
							datas.add(masterData);
							break;
						}
					}

				}

			}

			/*
			 * for( int i=0;i<rsworkday.size(); i++){ WorkdayoffFrameFindDto
			 * workdayoffFrameFindDto=rsworkday.get(i); if(i==0){
			 * 
			 * 
			 * Map<String, Object> data = new HashMap<>();
			 * data.put(column1Sheet8,TextResource.localize("KMK013_384"));
			 * data.put(column2Sheet8,TextResource.localize("KMK013_157")+
			 * workdayoffFrameFindDto.getWorkdayoffFrNo()+workdayoffFrameFindDto
			 * .getWorkdayoffFrName());
			 * data.put(column3Sheet8,getNameRoleOfOpenPeriodEnum(listRoleOpen.
			 * get(i).getRoleOfOpenPeriod())); MasterData masterData = new
			 * MasterData(data, null, ""); Map<String, MasterCellData> rowData =
			 * masterData.getRowData(); getAlignLeftsheet8(rowData);
			 * datas.add(masterData);
			 * 
			 * 
			 * 
			 * 
			 * }else{
			 * 
			 * Map<String, Object> data = new HashMap<>();
			 * data.put(column1Sheet8,"");
			 * data.put(column2Sheet8,TextResource.localize("KMK013_157")+
			 * workdayoffFrameFindDto.getWorkdayoffFrNo()+workdayoffFrameFindDto
			 * .getWorkdayoffFrName());
			 * data.put(column3Sheet8,getNameRoleOfOpenPeriodEnum(listRoleOpen.
			 * get(i).getRoleOfOpenPeriod())); MasterData masterData = new
			 * MasterData(data, null, ""); Map<String, MasterCellData> rowData =
			 * masterData.getRowData(); getAlignLeftsheet8(rowData);
			 * datas.add(masterData);
			 * 
			 * }
			 * 
			 * }
			 */

		} else {
			Map<String, Object> data = new HashMap<>();
			data.put(column1Sheet8, TextResource.localize("KMK013_384"));
			data.put(column2Sheet8, "");
			data.put(column3Sheet8, "");
			MasterData masterData = new MasterData(data, null, "");
			Map<String, MasterCellData> rowData = masterData.getRowData();
			getAlignLeftsheet8(rowData);
			datas.add(masterData);
		}

		return datas;
	}

	public String getNameRoleOTWork(int attr) {

		RoleOvertimeWorkEnum roleOvertimeWorkEnum = RoleOvertimeWorkEnum.valueOf(attr);
		switch (roleOvertimeWorkEnum) {
		case OT_STATUTORY_WORK:
			return TextResource.localize("Enum_OvertimeStatutoryWork");
		case OUT_OT_STATUTORY:
			return TextResource.localize("Enum_OutOvertimeStatutory");
		case MIX_IN_OUT_STATUTORY:
			return TextResource.localize("Enum_MixInOutStatutory");
		default:
			return "";
		}
	}

	public String getNameRoleOfOpenPeriodEnum(int attr) {

		RoleOfOpenPeriodEnum roleOfOpenPeriodEnum = RoleOfOpenPeriodEnum.valueOf(attr);
		switch (roleOfOpenPeriodEnum) {
		case NON_STATUTORY_HOLIDAYS:
			return TextResource.localize("Enum_NonStatutoryHolidays");
		case STATUTORY_HOLIDAYS:
			return TextResource.localize("Enum_StatutoryHolidays");
		case MIX_WITHIN_OUTSIDE_STATUTORY:
			return TextResource.localize("Enum_MixWithinOutsideStatutory");
		default:
			return "";
		}
	}

	private void getAlignLeftsheet8(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet8).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet8).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet8).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));

	}
	// end sheet 8

	// star sheet 9
	private List<MasterHeaderColumn> getHeaderColumnsCalculationProjectInherentSetting(MasterListExportQuery query) {

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet9, TextResource.localize("KMK013_351"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(column2Sheet9, TextResource.localize("KMK013_356"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(column3Sheet9, TextResource.localize("KMK013_361"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(column4Sheet9, TextResource.localize("KMK013_473"), ColumnTextAlign.LEFT, "",
				true));
		return columns;
	}

	private List<MasterData> getDataCalculationProjectInherentSetting(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		SpecificWorkRuleDto pecificWorkRuleDto = specificWorkRuleFinder.findSpecificWorkRule();
		Integer listrs[] = { pecificWorkRuleDto.getSpecialHoliday(), pecificWorkRuleDto.getSixtyHourVacation(),
				pecificWorkRuleDto.getSubstituteHoliday(), pecificWorkRuleDto.getAnnualHoliday() };
		List<Integer> listValue = Arrays.asList(listrs).stream().sorted((o1, o2) -> o1.compareTo(o2))
				.collect(Collectors.toList());
		// listValue.forEach(x ->System.out.println("gt: " +x));
		String companyId = AppContexts.user().companyId();
		Optional<TimeOffVacationPriorityOrder> optVacationOrder = specificWorkRuleRepository
				.findTimeOffVacationOrderByCid(companyId);
		boolean check = true;
		if (!optVacationOrder.isPresent()) {
			check = false;
		}
		for (int i = 0; i <= 3; i++) {
			if (i == 0) {
				Map<String, Object> data = new HashMap<>();
				data.put(column1Sheet9, getNameUpperLimitSet(pecificWorkRuleDto.getUpperLimitSet()));
				data.put(column2Sheet9, getNameConstraintCalcMethod(pecificWorkRuleDto.getConstraintCalcMethod()));
				if (check) {
					data.put(column3Sheet9, listValue.get(i) + 1);
					data.put(column4Sheet9, getNamePriority(i, pecificWorkRuleDto));
				} else {
					data.put(column3Sheet9, "");
					data.put(column4Sheet9, "");
				}

				MasterData masterData = new MasterData(data, null, "");
				Map<String, MasterCellData> rowData = masterData.getRowData();
				getAlignsheet9(rowData);
				datas.add(masterData);
			} else {
				Map<String, Object> data = new HashMap<>();
				data.put(column1Sheet9, "");
				data.put(column2Sheet9, "");
				if (check) {
					data.put(column3Sheet9, listValue.get(i) + 1);
					data.put(column4Sheet9, getNamePriority(i, pecificWorkRuleDto));
				} else {
					data.put(column3Sheet9, "");
					data.put(column4Sheet9, "");
				}
				MasterData masterData = new MasterData(data, null, "");
				Map<String, MasterCellData> rowData = masterData.getRowData();
				getAlignsheet9(rowData);
				datas.add(masterData);

			}

		}
		return datas;

	}

	public String getNameConstraintCalcMethod(int att) {
		String name = "";
		switch (att) {
		case 0:
			name = TextResource.localize("KMK013_360");
			break;
		case 1:
			name = TextResource.localize("KMK013_358");
			break;
		case 2:
			name = TextResource.localize("KMK013_359");
			break;
		default:
			break;
		}
		return name;
	}

	public String getNameUpperLimitSet(int att) {
		String name = "";
		switch (att) {
		case 0:
			name = TextResource.localize("KMK013_353");
			break;
		case 1:
			name = TextResource.localize("KMK013_354");
			break;
		case 2:
			name = TextResource.localize("KMK013_355");
			break;
		default:
			break;
		}
		return name;
	}

	public String getNamePriority(int att, SpecificWorkRuleDto pecificWorkRuleDto) {
		String name = "";

		if (pecificWorkRuleDto.getSubstituteHoliday() == att) {
			name = TextResource.localize("KMK013_376");
		} else if (pecificWorkRuleDto.getSixtyHourVacation() == att) {
			name = TextResource.localize("KMK013_377");
		} else if (pecificWorkRuleDto.getAnnualHoliday() == att) {
			name = TextResource.localize("KMK013_378");
		} else if (pecificWorkRuleDto.getSpecialHoliday() == att) {
			name = TextResource.localize("KMK013_379");
		}

		return name;
	}

	private void getAlignsheet9(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet9).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet9).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet9).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		rowData.get(column4Sheet9).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));

	}

	// end sheet 9

	// star sheet 11
	private List<MasterHeaderColumn> getHeaderColumnsSettingOfverticalmonthly(MasterListExportQuery query) {

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet11, TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column2Sheet11, "", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn(column3Sheet11, TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT,
				"", true));
		return columns;

	}

	private List<MasterData> getDataSettingOfverticalmonthly(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		VerticalTotalMethodOfMonthlyDto verticalTotalMethodOfMonthlyDto = verticalTotalMethodOfMonthlyFinder
				.findSetting();
		if (!Objects.isNull(verticalTotalMethodOfMonthlyDto)) {
			Map<String, Object> data1 = new HashMap<>();
			data1.put(column1Sheet11, TextResource.localize("KMK013_309"));
			data1.put(column2Sheet11, TextResource.localize("KMK013_310"));
			if (verticalTotalMethodOfMonthlyDto.getAttendanceItemCountingMethod() == 1) {
				data1.put(column3Sheet11, TextResource.localize("KMK013_312"));
			} else {
				data1.put(column3Sheet11, TextResource.localize("KMK013_311"));
			}

			MasterData masterData1 = new MasterData(data1, null, "");
			Map<String, MasterCellData> rowData1 = masterData1.getRowData();
			getAlignsheet11(rowData1);
			datas.add(masterData1);
		}
		List<WorkTypeDto> rs = workTypeProcessor.findWorkTypeAll();
		Map<String, WorkTypeDto> mapvalue = new HashMap<>();
		for (WorkTypeDto workTypeDto : rs) {
			mapvalue.put(workTypeDto.getWorkTypeCode(), workTypeDto);
		}
		// List<String> listworktype=rs.stream().map(x
		// ->x.getWorkTypeCode()).collect(Collectors.toList());
		//
		PayItemCountOfMonthlyDto payItemCountOfMonthlyDto = payItemCountOfMonthlyFinder.findSetting();
		if (!Objects.isNull(payItemCountOfMonthlyDto)
				&& !CollectionUtil.isEmpty(payItemCountOfMonthlyDto.getPayAbsenceDays())) {
			List<workTypeDto> listcodeName = new ArrayList<>();
			List<String> listPayAbsenceDays = payItemCountOfMonthlyDto.getPayAbsenceDays().stream()
					.sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());
			for (String code : listPayAbsenceDays) {
				if (mapvalue.containsKey(code)) {
					listcodeName.add(new workTypeDto(code, mapvalue.get(code).getName()));
				} else {
					listcodeName.add(new workTypeDto(code, TextResource.localize("KMK013_475")));
				}

			}

			Map<String, Object> data2 = new HashMap<>();
			data2.put(column1Sheet11, TextResource.localize("KMK013_335"));
			data2.put(column2Sheet11, TextResource.localize("KMK013_336"));
			data2.put(column3Sheet11, "");
			if (!CollectionUtil.isEmpty(listcodeName)) {
				String codeNameAbs = "";
				codeNameAbs = listcodeName.get(0).getWorkTypeCode() + listcodeName.get(0).getName();
				for (int i = 1; i < listcodeName.size(); i++) {
					codeNameAbs = codeNameAbs + "," + listcodeName.get(i).getWorkTypeCode()
							+ listcodeName.get(i).getName();
				}
				data2.put(column3Sheet11, codeNameAbs);
			}
			MasterData masterData2 = new MasterData(data2, null, "");
			Map<String, MasterCellData> rowData2 = masterData2.getRowData();
			getAlignsheet11(rowData2);
			datas.add(masterData2);
		} else {
			Map<String, Object> data2 = new HashMap<>();
			data2.put(column1Sheet11, TextResource.localize("KMK013_335"));
			data2.put(column2Sheet11, TextResource.localize("KMK013_336"));
			data2.put(column3Sheet11, "");
			MasterData masterData2 = new MasterData(data2, null, "");
			Map<String, MasterCellData> rowData2 = masterData2.getRowData();
			getAlignsheet11(rowData2);
			datas.add(masterData2);
		}
		//

		if (!Objects.isNull(payItemCountOfMonthlyDto)
				&& !CollectionUtil.isEmpty(payItemCountOfMonthlyDto.getPayAttendanceDays())) {
			// List<WorkTypeInfor>
			// listAtt=workTypeFinder.getPossibleWorkType(payItemCountOfMonthlyDto.getPayAttendanceDays());
			List<workTypeDto> listcodeName = new ArrayList<>();
			List<String> listPayAttendanceDays = payItemCountOfMonthlyDto.getPayAttendanceDays().stream()
					.sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());
			for (String code : listPayAttendanceDays) {
				if (mapvalue.containsKey(code)) {
					listcodeName.add(new workTypeDto(code, mapvalue.get(code).getName()));
				} else {
					listcodeName.add(new workTypeDto(code, TextResource.localize("KMK013_475")));
				}

			}
			Map<String, Object> data3 = new HashMap<>();
			data3.put(column1Sheet11, "");
			data3.put(column2Sheet11, TextResource.localize("KMK013_338"));
			data3.put(column3Sheet11, "");
			if (!CollectionUtil.isEmpty(listcodeName)) {
				String codeNameAbs = "";
				codeNameAbs = listcodeName.get(0).getWorkTypeCode() + listcodeName.get(0).getName();
				for (int i = 1; i < listcodeName.size(); i++) {
					codeNameAbs = codeNameAbs + "," + listcodeName.get(i).getWorkTypeCode()
							+ listcodeName.get(i).getName();
				}
				data3.put(column3Sheet11, codeNameAbs);
			}

			MasterData masterData3 = new MasterData(data3, null, "");
			Map<String, MasterCellData> rowData3 = masterData3.getRowData();
			getAlignsheet11(rowData3);
			datas.add(masterData3);
		} else {
			Map<String, Object> data3 = new HashMap<>();
			data3.put(column1Sheet11, "");
			data3.put(column2Sheet11, TextResource.localize("KMK013_338"));
			data3.put(column3Sheet11, "");
			MasterData masterData3 = new MasterData(data3, null, "");
			Map<String, MasterCellData> rowData3 = masterData3.getRowData();
			getAlignsheet11(rowData3);
			datas.add(masterData3);
		}

		return datas;
	}

	private void getAlignsheet11(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet11).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet11).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet11).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));

	}

	// end sheet 11
	// sart sheet 12
	private List<MasterHeaderColumn> getHeaderColumnsRoundingSettingOfMonthly(MasterListExportQuery query) {

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet12, TextResource.localize("KMK013_447"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column2Sheet12, TextResource.localize("KMK013_448"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column3Sheet12, TextResource.localize("KMK013_449"), ColumnTextAlign.LEFT,
				"", true));
		return columns;

	}

	private List<MasterData> getDataRoundingSettingOfMonthly(MasterListExportQuery query) {
		LoginUserContext loginUserContext = AppContexts.user();
		// get company id
		String companyId = loginUserContext.companyId();
		List<MasterData> datas = new ArrayList<>();
		List<RoundingMonthDto> rs = roundingMonthFinder.findAllRounding();
		Map<Integer, RoundingMonthDto> map = new HashMap<>();
		for (RoundingMonthDto roundingMonthDto : rs) {
			map.put(roundingMonthDto.getTimeItemId(), roundingMonthDto);
		}
		if (!CollectionUtil.isEmpty(rs)) {
			List<Integer> listAttendaneId = rs.stream().map(x -> x.getTimeItemId()).collect(Collectors.toList());
			List<AttendanceNameDivergenceDto> listattName = divergenceItemSetFinder.getMonthlyAtName(listAttendaneId)
					.stream()
					.sorted((o1, o2) -> o1.getAttendanceItemDisplayNumber() - o2.getAttendanceItemDisplayNumber())
					.collect(Collectors.toList());
			for (int i = 0; i < listattName.size(); i++) {
				AttendanceNameDivergenceDto attendanceNameDivergenceDto = listattName.get(i);
				RoundingMonthDto roundingMonth = map.get(attendanceNameDivergenceDto.getAttendanceItemId());
				Map<String, Object> data = new HashMap<>();
				data.put(column1Sheet12, attendanceNameDivergenceDto.getAttendanceItemName());
				data.put(column2Sheet12, getNameEnumRoundingUnit(roundingMonth.getUnit()));
				data.put(column3Sheet12, getNameEnumRounding(roundingMonth.getRounding()));
				MasterData masterData = new MasterData(data, null, "");
				Map<String, MasterCellData> rowData = masterData.getRowData();
				getAlignsheet12(rowData);
				datas.add(masterData);

			}

		}
		Optional<OutsideOtSetDto> oprs = outsideOtSetRepositoryFinder.findById(companyId);
		if (oprs.isPresent()) {
			OutsideOtSetDto outsideOtSetDto = oprs.get();
			if (outsideOtSetDto.getCalculationMethod() == 1) {
				TimeRoundingOfExcessOutsideTimeDto timeRoundingOfExcessOutsideTimeDto = timeRoundingOfExcessOutsideTimeFinder
						.findTimeRounding();
				if (!Objects.isNull(timeRoundingOfExcessOutsideTimeDto)) {
					Map<String, Object> data1 = new HashMap<>();
					data1.put(column1Sheet12, TextResource.localize("KMK013_274"));
					data1.put(column2Sheet12,
							getNameEnumRoundingUnit(timeRoundingOfExcessOutsideTimeDto.getRoundingUnit()));
					data1.put(column3Sheet12, getNameEnumProcessOfExcessOutsideTime(
							timeRoundingOfExcessOutsideTimeDto.getRoundingProcess()));
					MasterData masterData1 = new MasterData(data1, null, "");
					Map<String, MasterCellData> rowData1 = masterData1.getRowData();
					getAlignsheet12(rowData1);
					datas.add(masterData1);
				}

			}
		}

		return datas;
	}

	private void getAlignsheet12(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet12).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet12).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		rowData.get(column3Sheet12).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));

	}

	public String getNameEnumRoundingUnit(int att) {

		Unit roundingTimeUnit = Unit.valueOf(att);
		switch (roundingTimeUnit) {
		case ROUNDING_TIME_1MIN:
			return TextResource.localize("Enum_RoundingTime_1Min");
		case ROUNDING_TIME_5MIN:
			return TextResource.localize("Enum_RoundingTime_5Min");
		case ROUNDING_TIME_6MIN:
			return TextResource.localize("Enum_RoundingTime_6Min");
		case ROUNDING_TIME_10MIN:
			return TextResource.localize("Enum_RoundingTime_10Min");
		case ROUNDING_TIME_15MIN:
			return TextResource.localize("Enum_RoundingTime_15Min");
		case ROUNDING_TIME_20MIN:
			return TextResource.localize("Enum_RoundingTime_20Min");
		case ROUNDING_TIME_30MIN:
			return TextResource.localize("Enum_RoundingTime_30Min");
		case ROUNDING_TIME_60MIN:
			return TextResource.localize("Enum_RoundingTime_60Min");
		default:
			return "";
		}
	}

	public String getNameEnumRounding(int attr) {
		Rounding rounding = Rounding.valueOf(attr);
		switch (rounding) {
		case ROUNDING_DOWN:
			return TextResource.localize("Enum_Rounding_Down");
		case ROUNDING_UP:
			return TextResource.localize("Enum_Rounding_Up");
		case ROUNDING_DOWN_OVER:
			return TextResource.localize("Enum_Rounding_Down_Over");
		default:
			return "";
		}
	}

	public String getNameEnumProcessOfExcessOutsideTime(int attr) {

		switch (attr) {
		case 0:
			return TextResource.localize("Enum_Exc_Rounding_Down");
		case 1:
			return TextResource.localize("Enum_Exc_Rounding_Up");
		case 2:
			return TextResource.localize("Enum_Exc_Follow_Element");
		default:
			return "";
		}
	}

	// end sheet12

	// start sheet 10
	private List<MasterHeaderColumn> getHeaderColumnsZerotimeCrossingcalculationSetting(MasterListExportQuery query) {

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(column1Sheet10, TextResource.localize("KMK013_89"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(column2Sheet10, TextResource.localize("KMK013_450"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column3Sheet10, TextResource.localize("KMK013_451"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column4Sheet10, TextResource.localize("KMK013_452"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column5Sheet10, TextResource.localize("KMK013_453"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column6Sheet10, TextResource.localize("KMK013_454"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column7Sheet10, TextResource.localize("KMK013_455"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column8Sheet10, TextResource.localize("KMK013_456"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column09Sheet10, TextResource.localize("KMK013_457"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column10Sheet10, TextResource.localize("KMK013_458"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column11Sheet10, TextResource.localize("KMK013_459"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column12Sheet10, TextResource.localize("KMK013_460"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column13Sheet10, TextResource.localize("KMK013_461"), ColumnTextAlign.LEFT,
				"", true));
		//
		columns.add(new MasterHeaderColumn(column14Sheet10, TextResource.localize("KMK013_462"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column15Sheet10, TextResource.localize("KMK013_463"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column16Sheet10, TextResource.localize("KMK013_464"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column17Sheet10, TextResource.localize("KMK013_465"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column18Sheet10, TextResource.localize("KMK013_466"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column19Sheet10, "", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(column20Sheet10, TextResource.localize("KMK013_467"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column21Sheet10, TextResource.localize("KMK013_468"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column22Sheet10, TextResource.localize("KMK013_469"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(column23Sheet10, TextResource.localize("KMK013_470"), ColumnTextAlign.LEFT,
				"", true));

		return columns;

	}

	private List<MasterData> getDataZerotimeCrossingcalculationSetting(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		List<ZeroTimeDto> rs = zeroTimeFinder.findAllCalc();
		List<OvertimeWorkFrameFindDto> rsovertimeFrame = overtimeWorkFrameFinder.findAll().stream()
				.filter(x -> x.getUseAtr() == 1).collect(Collectors.toList());
		List<WorkdayoffFrameFindDto> rsworkdayFrame = workdayoffFrameFinder.findAll().stream()
				.filter(x -> x.getUseAtr() == 1).collect(Collectors.toList());
		int count = rsovertimeFrame.size() < rsworkdayFrame.size() ? rsworkdayFrame.size() : rsovertimeFrame.size();
		if (CollectionUtil.isEmpty(rsovertimeFrame) && CollectionUtil.isEmpty(rsworkdayFrame) && count == 0) {
			if (!CollectionUtil.isEmpty(rs)) {
				ZeroTimeDto zeroTimeDto = rs.get(0);
				Map<String, Object> data = putEntrySheet10();
				if (zeroTimeDto.getCalcFromZeroTime() == 1) {
					data.put(column1Sheet10, TextResource.localize("KMK013_91"));
					if (zeroTimeDto.getLegalHd() == 1) {
						data.put(column2Sheet10, TextResource.localize("KMK013_96"));
					} else {
						data.put(column2Sheet10, TextResource.localize("KMK013_97"));
					}
					if (zeroTimeDto.getNonLegalHd() == 1) {
						data.put(column3Sheet10, TextResource.localize("KMK013_96"));
					} else {
						data.put(column3Sheet10, TextResource.localize("KMK013_97"));
					}
					//
					if (zeroTimeDto.getNonLegalPublicHd() == 1) {
						data.put(column4Sheet10, TextResource.localize("KMK013_96"));
					} else {
						data.put(column4Sheet10, TextResource.localize("KMK013_97"));
					}
					//

					if (zeroTimeDto.getWeekday1() == 1) {
						data.put(column5Sheet10, TextResource.localize("KMK013_96"));
					} else {
						data.put(column5Sheet10, TextResource.localize("KMK013_97"));
					}
					//

					if (zeroTimeDto.getNonLegalHd1() == 1) {
						data.put(column6Sheet10, TextResource.localize("KMK013_96"));
					} else {
						data.put(column6Sheet10, TextResource.localize("KMK013_97"));
					}
					//
					if (zeroTimeDto.getNonLegalPublicHd1() == 1) {
						data.put(column7Sheet10, TextResource.localize("KMK013_96"));
					} else {
						data.put(column7Sheet10, TextResource.localize("KMK013_97"));
					}
					//
					if (zeroTimeDto.getWeekday2() == 1) {
						data.put(column8Sheet10, TextResource.localize("KMK013_96"));
					} else {
						data.put(column8Sheet10, TextResource.localize("KMK013_97"));
					}
					//
					if (zeroTimeDto.getLegalHd2() == 1) {
						data.put(column09Sheet10, TextResource.localize("KMK013_96"));
					} else {
						data.put(column09Sheet10, TextResource.localize("KMK013_97"));
					}
					//
					if (zeroTimeDto.getNonLegalHd2() == 1) {
						data.put(column10Sheet10, TextResource.localize("KMK013_96"));
					} else {
						data.put(column10Sheet10, TextResource.localize("KMK013_97"));
					}
					//

					if (zeroTimeDto.getWeekday3() == 1) {
						data.put(column11Sheet10, TextResource.localize("KMK013_96"));
					} else {
						data.put(column11Sheet10, TextResource.localize("KMK013_97"));
					}
					//
					if (zeroTimeDto.getLegalHd3() == 1) {
						data.put(column12Sheet10, TextResource.localize("KMK013_96"));
					} else {
						data.put(column12Sheet10, TextResource.localize("KMK013_97"));
					}
					if (zeroTimeDto.getNonLegalPublicHd3() == 1) {
						data.put(column13Sheet10, TextResource.localize("KMK013_96"));
					} else {
						data.put(column13Sheet10, TextResource.localize("KMK013_97"));
					}
					MasterData masterData = new MasterData(data, null, "");
					Map<String, MasterCellData> rowData = masterData.getRowData();
					getAlignsheet10(rowData);
					datas.add(masterData);

				} else {
					data.put(column1Sheet10, TextResource.localize("KMK013_92"));

				}
			}
		} else if (count > 0) {
			for (int i = 0; i < count; i++) {
				if (i == 0) {					
					if (!CollectionUtil.isEmpty(rs)) {
						Map<String, Object> data = putEntrySheet10();
						ZeroTimeDto zeroTimeDto = rs.get(0);
						List<WeekdayHolidayDto> listWeekdayHolidayDto = zeroTimeDto.getWeekdayHoliday(); // 1
						List<HdFromWeekdayDto> listHdFromWeekdayDto = zeroTimeDto.getOverdayHolidayAtten(); // 2
						List<HdFromHdDto> listHdFromHdDto = zeroTimeDto.getOverdayCalcHoliday(); // 3
						//
						if (zeroTimeDto.getCalcFromZeroTime() == 1) {
							data.put(column1Sheet10, TextResource.localize("KMK013_91"));
							if (zeroTimeDto.getLegalHd() == 1) {
								data.put(column2Sheet10, TextResource.localize("KMK013_96"));
							} else {
								data.put(column2Sheet10, TextResource.localize("KMK013_97"));
							}
							if (zeroTimeDto.getNonLegalHd() == 1) {
								data.put(column3Sheet10, TextResource.localize("KMK013_96"));
							} else {
								data.put(column3Sheet10, TextResource.localize("KMK013_97"));
							}
							//
							if (zeroTimeDto.getNonLegalPublicHd() == 1) {
								data.put(column4Sheet10, TextResource.localize("KMK013_96"));
							} else {
								data.put(column4Sheet10, TextResource.localize("KMK013_97"));
							}
							//

							if (zeroTimeDto.getWeekday1() == 1) {
								data.put(column5Sheet10, TextResource.localize("KMK013_96"));
							} else {
								data.put(column5Sheet10, TextResource.localize("KMK013_97"));
							}
							//

							if (zeroTimeDto.getNonLegalHd1() == 1) {
								data.put(column6Sheet10, TextResource.localize("KMK013_96"));
							} else {
								data.put(column6Sheet10, TextResource.localize("KMK013_97"));
							}
							//
							if (zeroTimeDto.getNonLegalPublicHd1() == 1) {
								data.put(column7Sheet10, TextResource.localize("KMK013_96"));
							} else {
								data.put(column7Sheet10, TextResource.localize("KMK013_97"));
							}
							//
							if (zeroTimeDto.getWeekday2() == 1) {
								data.put(column8Sheet10, TextResource.localize("KMK013_96"));
							} else {
								data.put(column8Sheet10, TextResource.localize("KMK013_97"));
							}
							//
							if (zeroTimeDto.getLegalHd2() == 1) {
								data.put(column09Sheet10, TextResource.localize("KMK013_96"));
							} else {
								data.put(column09Sheet10, TextResource.localize("KMK013_97"));
							}
							//
							if (zeroTimeDto.getNonLegalHd2() == 1) {
								data.put(column10Sheet10, TextResource.localize("KMK013_96"));
							} else {
								data.put(column10Sheet10, TextResource.localize("KMK013_97"));
							}
							//

							if (zeroTimeDto.getWeekday3() == 1) {
								data.put(column11Sheet10, TextResource.localize("KMK013_96"));
							} else {
								data.put(column11Sheet10, TextResource.localize("KMK013_97"));
							}
							//
							if (zeroTimeDto.getLegalHd3() == 1) {
								data.put(column12Sheet10, TextResource.localize("KMK013_96"));
							} else {
								data.put(column12Sheet10, TextResource.localize("KMK013_97"));
							}
							if (zeroTimeDto.getNonLegalPublicHd3() == 1) {
								data.put(column13Sheet10, TextResource.localize("KMK013_96"));
							} else {
								data.put(column13Sheet10, TextResource.localize("KMK013_97"));
							}						

						} else {
							data.put(column1Sheet10, TextResource.localize("KMK013_92"));

						}
						//star tab1
						if (i < rsovertimeFrame.size()) {
							OvertimeWorkFrameFindDto overtimeWorkFrameFindDto = rsovertimeFrame.get(i);						
							if (!CollectionUtil.isEmpty(listWeekdayHolidayDto)) {
								data.put(column14Sheet10,
										TextResource.localize("KMK013_152") + overtimeWorkFrameFindDto.getOvertimeWorkFrNo()
												+ overtimeWorkFrameFindDto.getOvertimeWorkFrName());
								
								List<WeekdayHolidayDto> rsfiter = listWeekdayHolidayDto.stream()
										.filter(x -> x != null && x.getOverworkFrameNo()
												.intValue() == overtimeWorkFrameFindDto.getOvertimeWorkFrNo())
										.collect(Collectors.toList());
								if (!CollectionUtil.isEmpty(rsfiter)) {
									// rsworkdayFrame.stream().filter()
									WeekdayHolidayDto weekdayHolidayDto = rsfiter.get(0);
									data.put(column15Sheet10,
											getNamecomboTab1(rsworkdayFrame, weekdayHolidayDto.getWeekdayNo()));
									data.put(column16Sheet10,
											getNamecomboTab1(rsworkdayFrame, weekdayHolidayDto.getExcessHolidayNo()));
									data.put(column17Sheet10,
											getNamecomboTab1(rsworkdayFrame, weekdayHolidayDto.getExcessSphdNo()));
								} else {
									data.put(column15Sheet10, TextResource.localize("KMK013_235"));
									data.put(column16Sheet10, TextResource.localize("KMK013_235"));
									data.put(column17Sheet10, TextResource.localize("KMK013_235"));
								}

							} 
						}
						// end tab1
						// star tar2 tab3
						if(i<rsworkdayFrame.size()){
							WorkdayoffFrameFindDto workdayoffFrameFindDto = rsworkdayFrame.get(i);
							//
							data.put(column18Sheet10,
									TextResource.localize("KMK013_157") + rsworkdayFrame.get(i).getWorkdayoffFrNo()
											+ rsworkdayFrame.get(i).getWorkdayoffFrName());
							
							if (!CollectionUtil.isEmpty(listHdFromWeekdayDto)) {
								
								List<HdFromWeekdayDto> listfilter = listHdFromWeekdayDto.stream().filter(x -> x != null
										&& x.getHolidayWorkFrameNo() == workdayoffFrameFindDto.getWorkdayoffFrNo())
										.collect(Collectors.toList());
								if (!CollectionUtil.isEmpty(listfilter)) {
									HdFromWeekdayDto hdFromWeekdayDto = listfilter.get(0);
									data.put(column19Sheet10, getNamecomboTab2(rsovertimeFrame,
											hdFromWeekdayDto.getOverWorkNo().intValue()));
								} else {
									data.put(column19Sheet10, TextResource.localize("KMK013_235"));
								}
							} else {
								data.put(column19Sheet10, TextResource.localize("KMK013_235"));
							}

							//
							if (!CollectionUtil.isEmpty(listHdFromHdDto)) {
								data.put(column20Sheet10,
										TextResource.localize("KMK013_163") + rsworkdayFrame.get(i).getWorkdayoffFrNo()
												+ rsworkdayFrame.get(i).getWorkdayoffFrName());
								List<HdFromHdDto> listfilter = listHdFromHdDto.stream().filter(x -> x != null
										&& x.getHolidayWorkFrameNo() == workdayoffFrameFindDto.getWorkdayoffFrNo())
										.collect(Collectors.toList());
								if (!CollectionUtil.isEmpty(listfilter)) {
									HdFromHdDto hdFromHdDto = listfilter.get(0);
									data.put(column21Sheet10,
											getNamecomboTab1(rsworkdayFrame, hdFromHdDto.getCalcOverDayEnd()));
									data.put(column22Sheet10,
											getNamecomboTab1(rsworkdayFrame, hdFromHdDto.getStatutoryHd()));
									data.put(column23Sheet10,
											getNamecomboTab1(rsworkdayFrame, hdFromHdDto.getExcessHd()));
								} else {
									data.put(column21Sheet10, TextResource.localize("KMK013_235"));
									data.put(column22Sheet10, TextResource.localize("KMK013_235"));
									data.put(column23Sheet10, TextResource.localize("KMK013_235"));
								}
							} 					
							
						}
						// end tab2 tab3
						
						MasterData masterData = new MasterData(data, null, "");
						Map<String, MasterCellData> rowData = masterData.getRowData();
						getAlignsheet10(rowData);
						datas.add(masterData);

					}

				} else {
					//
					if (!CollectionUtil.isEmpty(rs)) {
						Map<String, Object> data = putEntrySheet10();
						ZeroTimeDto zeroTimeDto = rs.get(0);
						List<WeekdayHolidayDto> listWeekdayHolidayDto = zeroTimeDto.getWeekdayHoliday(); // 1
						List<HdFromWeekdayDto> listHdFromWeekdayDto = zeroTimeDto.getOverdayHolidayAtten(); // 2
						List<HdFromHdDto> listHdFromHdDto = zeroTimeDto.getOverdayCalcHoliday(); // 3
						//star tab1
						if (i < rsovertimeFrame.size()) {
							OvertimeWorkFrameFindDto overtimeWorkFrameFindDto = rsovertimeFrame.get(i);						
							if (!CollectionUtil.isEmpty(listWeekdayHolidayDto)) {
								data.put(column14Sheet10,
										TextResource.localize("KMK013_152") + overtimeWorkFrameFindDto.getOvertimeWorkFrNo()
												+ overtimeWorkFrameFindDto.getOvertimeWorkFrName());
								
								List<WeekdayHolidayDto> rsfiter = listWeekdayHolidayDto.stream()
										.filter(x -> x != null && x.getOverworkFrameNo()
												.intValue() == overtimeWorkFrameFindDto.getOvertimeWorkFrNo())
										.collect(Collectors.toList());
								if (!CollectionUtil.isEmpty(rsfiter)) {
									// rsworkdayFrame.stream().filter()
									WeekdayHolidayDto weekdayHolidayDto = rsfiter.get(0);
									data.put(column15Sheet10,
											getNamecomboTab1(rsworkdayFrame, weekdayHolidayDto.getWeekdayNo()));
									data.put(column16Sheet10,
											getNamecomboTab1(rsworkdayFrame, weekdayHolidayDto.getExcessHolidayNo()));
									data.put(column17Sheet10,
											getNamecomboTab1(rsworkdayFrame, weekdayHolidayDto.getExcessSphdNo()));
								} else {
									data.put(column15Sheet10, TextResource.localize("KMK013_235"));
									data.put(column16Sheet10, TextResource.localize("KMK013_235"));
									data.put(column17Sheet10, TextResource.localize("KMK013_235"));
								}

							} 
						}
						// end tab1
						// star tar2 tab3
						if(i<rsworkdayFrame.size()){
							WorkdayoffFrameFindDto workdayoffFrameFindDto = rsworkdayFrame.get(i);
							//
							data.put(column18Sheet10,
									TextResource.localize("KMK013_157") + rsworkdayFrame.get(i).getWorkdayoffFrNo()
											+ rsworkdayFrame.get(i).getWorkdayoffFrName());
							
							if (!CollectionUtil.isEmpty(listHdFromWeekdayDto)) {
								
								List<HdFromWeekdayDto> listfilter = listHdFromWeekdayDto.stream().filter(x -> x != null
										&& x.getHolidayWorkFrameNo() == workdayoffFrameFindDto.getWorkdayoffFrNo())
										.collect(Collectors.toList());
								if (!CollectionUtil.isEmpty(listfilter)) {
									HdFromWeekdayDto hdFromWeekdayDto = listfilter.get(0);
									data.put(column19Sheet10, getNamecomboTab2(rsovertimeFrame,
											hdFromWeekdayDto.getOverWorkNo().intValue()));
								} else {
									data.put(column19Sheet10, TextResource.localize("KMK013_235"));
								}
							} else {
								data.put(column19Sheet10, TextResource.localize("KMK013_235"));
							}

							//
							if (!CollectionUtil.isEmpty(listHdFromHdDto)) {
								data.put(column20Sheet10,
										TextResource.localize("KMK013_163") + rsworkdayFrame.get(i).getWorkdayoffFrNo()
												+ rsworkdayFrame.get(i).getWorkdayoffFrName());
								List<HdFromHdDto> listfilter = listHdFromHdDto.stream().filter(x -> x != null
										&& x.getHolidayWorkFrameNo() == workdayoffFrameFindDto.getWorkdayoffFrNo())
										.collect(Collectors.toList());
								if (!CollectionUtil.isEmpty(listfilter)) {
									HdFromHdDto hdFromHdDto = listfilter.get(0);
									data.put(column21Sheet10,
											getNamecomboTab1(rsworkdayFrame, hdFromHdDto.getCalcOverDayEnd()));
									data.put(column22Sheet10,
											getNamecomboTab1(rsworkdayFrame, hdFromHdDto.getStatutoryHd()));
									data.put(column23Sheet10,
											getNamecomboTab1(rsworkdayFrame, hdFromHdDto.getExcessHd()));
								} else {
									data.put(column21Sheet10, TextResource.localize("KMK013_235"));
									data.put(column22Sheet10, TextResource.localize("KMK013_235"));
									data.put(column23Sheet10, TextResource.localize("KMK013_235"));
								}
							} 					
							
						}
						// end tab2 tab3					
						MasterData masterData = new MasterData(data, null, "");
						Map<String, MasterCellData> rowData = masterData.getRowData();
						getAlignsheet10(rowData);
						datas.add(masterData);

					}


				}

			}

		}

		return datas;

	}

	private Map<String, Object> putEntrySheet10() {
		Map<String, Object> data = new HashMap<>();
		data.put(column1Sheet10, "");
		data.put(column2Sheet10, "");
		data.put(column3Sheet10, "");
		data.put(column4Sheet10, "");
		data.put(column5Sheet10, "");
		data.put(column6Sheet10, "");
		data.put(column7Sheet10, "");
		data.put(column8Sheet10, "");
		data.put(column09Sheet10, "");
		data.put(column10Sheet10, "");
		data.put(column11Sheet10, "");
		data.put(column12Sheet10, "");
		data.put(column13Sheet10, "");
		data.put(column14Sheet10, "");
		data.put(column15Sheet10, "");
		data.put(column16Sheet10, "");
		data.put(column17Sheet10, "");
		data.put(column18Sheet10, "");
		data.put(column19Sheet10, "");
		data.put(column20Sheet10, "");
		data.put(column21Sheet10, "");
		data.put(column22Sheet10, "");
		data.put(column23Sheet10, "");
		return data;
	}

	public String getNamecomboTab1(List<WorkdayoffFrameFindDto> rsworkdayFrame, int att) {
		String name = TextResource.localize("KMK013_235");
		List<WorkdayoffFrameFindDto> rs = rsworkdayFrame.stream().filter(x -> x.getWorkdayoffFrNo() == att)
				.collect(Collectors.toList());
		if (att == 0) {
			name = TextResource.localize("KMK013_235");
		}
		if (!CollectionUtil.isEmpty(rs)) {
			name = rs.get(0).getWorkdayoffFrName();
		}
		return name;
	}

	public String getNamecomboTab2(List<OvertimeWorkFrameFindDto> rsovertimeFrame, int att) {
		String name = TextResource.localize("KMK013_235");
		List<OvertimeWorkFrameFindDto> rs = rsovertimeFrame.stream().filter(x -> x.getOvertimeWorkFrNo() == att)
				.collect(Collectors.toList());
		if (att == 0) {
			name = TextResource.localize("KMK013_235");
		}
		if (!CollectionUtil.isEmpty(rs)) {
			name = rs.get(0).getOvertimeWorkFrName();
		}
		return name;
	}

	private void getAlignsheet10(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column4Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column5Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column6Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column7Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column8Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column09Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column10Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column11Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column12Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column13Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column14Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column15Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column16Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column17Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column18Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column19Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column20Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column21Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column22Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column23Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));

	}
	// end sheet 10

	private void getAlignsheet2(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column4Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column5Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));

	}

	private void getAlignRightsheet2(Map<String, MasterCellData> rowData) {
		rowData.get(column1Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column2Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column3Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column4Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get(column5Sheet2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));

	}

	public String getFunctionSelect(int attr) {
		UseAtr useAtr = UseAtr.valueOf(attr);
		switch (useAtr) {
		case NOTUSE:
			return TextResource.localize("KMK013_210");
		case USE:
			return TextResource.localize("KMK013_209");
		default:
			return "";
		}
	}

	private String formatValueAttendance(int att) {
		Integer hours = att / 60, minutes = att % 60;
		return String.join("", hours.toString(), ":", minutes < 10 ? "0" : "", minutes.toString());
	}

	private String formatValueSheet5(int att) {
		Integer hours = att / 60, minutes = att % 60;
		return String.join("", hours.toString(), ":", minutes < 10 ? "0" : "", minutes.toString());
	}

	private String formatDay(int att, boolean check) {
		Integer hours = att / 60, minutes = att % 60;
		if (hours >= 24 && check) {
			hours = hours - 24;
			return String.join("", "翌日",hours.toString(), ":", minutes < 10 ? "0" : "",
					minutes.toString());
		} else {
			return String.join("", "当日", hours.toString(), ":", minutes < 10 ? "0" : "",
					minutes.toString());
		}
	}

	public String getUseClassificationAt(int attr) {

		NotUseAtr notUseAtr = NotUseAtr.valueOf(attr);
		switch (notUseAtr) {
		case NOT_USE:
			// return
			// TextResource.localize("Enum_UseClassificationAtr_NOT_USE");
			return TextResource.localize("KMK013_210");
		case USE:
			return TextResource.localize("KMK013_209");
		// return TextResource.localize("Enum_UseClassificationAtr_USE");
		default:
			return "";
		}
	}

}
