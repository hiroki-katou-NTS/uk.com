package nts.uk.file.at.app.export.calculationsetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.divergence.time.DivergenceAttendanceItemFinder;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.midnight.MidnightTimeSheetRepo;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManage;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManageRepository;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporary;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryRepository;
import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.shared.dom.calculationsetting.repository.StampReflectionManagementRepository;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExit;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetManageWorkHour;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetManageWorkHourRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.CalcurationByActualTimeAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HourlyPaymentAdditionSetRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TimeHolidayAddingMethod;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TimeHolidayAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.VacationAdditionTimeRef;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkClassOfTimeHolidaySet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSetRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSetRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor.DeformLaborOT;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor.DeformLaborOTRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexCalcMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexCalcSetOfTimeCompLeave;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.HolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.HdFromHd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.HdFromWeekday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.WeekdayHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.ZeroTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.ZeroTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMnt;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMntRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSetting;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.shared.dom.workrule.specific.UpperLimitTotalWorkingHour;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagementRepo;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkMntSetRepository;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkSet;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManageRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.TimeWithDayAttr;
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
	private FlexWorkMntSetRepository flexWorkRepo;

	@Inject
	private AggDeformedLaborSettingRepository aggSettingRepo;

	@Inject
	private WorkManagementMultipleRepository multipleWorkRepo;

	@Inject
	private TemporaryWorkUseManageRepository tempWorkRepo;

	@Inject
	private ManageWorkTemporaryRepository tempWorkManageRepo;

	@Inject
	private MidnightTimeSheetRepo midnightTimeSheetRepo;

	@Inject
    private WeekRuleManagementRepo weekRuleManagementRepo;

	@Inject
    private OutManageRepository outManageRepository;

	@Inject
	private ManageEntryExitRepository entryExitRepo;

	@Inject
	private OvertimeWorkFrameRepository overtimeWorkFrameRepo;

	@Inject
	private WorkdayoffFrameRepository workdayoffFrameRepo;

	@Inject
    private HolidayAddtionRepository holidayAddtionRepo;

	@Inject
	private AddSetManageWorkHourRepository addSetManageWorkHourRepo;

	@Inject
	private WorkRegularAdditionSetRepository workRegularAdditionSetRepo;

	@Inject
    private HourlyPaymentAdditionSetRepository hourlyPaymentAdditionSetRepo;

	@Inject
    private WorkFlexAdditionSetRepository workFlexAdditionSetRepo;

	@Inject
    private WorkDeformedLaborAdditionSetRepository workDeformedLaborAdditionSetRepo;

	@Inject
	private DivergenceAttendanceItemFinder monthlyAttendanceItemRepo;

	@Inject
	private RoundingSetOfMonthlyRepository roundingSetOfMonthlyRepo;

	@Inject
	private DeformLaborOTRepository deformLaborOTRepo;

	@Inject
	private FlexSetRepository flexSetRepo;

	@Inject
	private InsufficientFlexHolidayMntRepository insufficientFlexHolidayMntRepo;

	@Inject
    private StampReflectionManagementRepository stampReflectionManagementRepo;

	@Inject
    private SpecificWorkRuleRepository specificWorkRuleRepo;

	@Inject
    private VerticalTotalMethodOfMonthlyRepository verticalTotalMethodOfMonthlyRepo;

	@Inject
    private ZeroTimeRepository zeroTimeRepo;

	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query) {
		List<SheetData> sheetData = new ArrayList<>();

		sheetData.add(SheetData.builder()
				.mainDataColumns(this.getSheet2HeaderColumns())
				.mainData(this.getSheet2MasterDatas())
				.sheetName(TextResource.localize("KMK013_435"))
				.mode(this.mainSheetMode())
				.build());

		sheetData.add(SheetData.builder()
				.mainDataColumns(this.getSheet3HeaderColumns())
				.mainData(this.getSheet3MasterDatas())
				.sheetName(TextResource.localize("KMK013_427"))
				.mode(this.mainSheetMode())
				.build());

		sheetData.add(SheetData.builder()
				.mainDataColumns(this.getSheet4HeaderColumns())
				.mainData(this.getSheet4MasterDatas())
				.sheetName(TextResource.localize("KMK013_440"))
				.mode(this.mainSheetMode())
				.build());

		sheetData.add(SheetData.builder()
				.mainDataColumns(this.getSheet5HeaderColumns())
				.mainData(this.getSheet5MasterDatas())
				.sheetName(TextResource.localize("KMK013_438"))
				.mode(this.mainSheetMode())
				.build());

		sheetData.add(SheetData.builder()
				.mainDataColumns(this.getSheet6HeaderColumns())
				.mainData(this.getSheet6MasterDatas())
				.sheetName(TextResource.localize("KMK013_432"))
				.mode(this.mainSheetMode())
				.build());

		sheetData.add(SheetData.builder()
				.mainDataColumns(this.getSheet7HeaderColumns())
				.mainData(this.getSheet7MasterDatas())
				.sheetName(TextResource.localize("KMK013_433"))
				.mode(this.mainSheetMode())
				.build());

        sheetData.add(SheetData.builder()
                .mainDataColumns(this.getSheet8HeaderColumns())
                .mainData(this.getSheet8MasterDatas())
                .sheetName(TextResource.localize("KMK013_484"))
                .mode(this.mainSheetMode())
                .build());

        sheetData.add(SheetData.builder()
                .mainDataColumns(this.getSheet9HeaderColumns())
                .mainData(this.getSheet9MasterDatas())
                .sheetName(TextResource.localize("KMK013_485"))
                .mode(this.mainSheetMode())
                .build());

		return sheetData;
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		Optional<FlexWorkSet> optFlexWorkSet = flexWorkRepo.find(companyId);
		Optional<AggDeformedLaborSetting> optAggSetting = aggSettingRepo.findByCid(companyId);
		Optional<WorkManagementMultiple> optWorkMultiple = multipleWorkRepo.findByCode(companyId);
		Optional<TemporaryWorkUseManage> optTempWorkUse = tempWorkRepo.findByCid(companyId);
		Optional<ManageWorkTemporary> optTempWorkManage = tempWorkManageRepo.findByCID(companyId);
		Optional<MidNightTimeSheet> midNightTimeSheet = midnightTimeSheetRepo.findByCId(companyId);
		Optional<WeekRuleManagement> weekRuleManagement = weekRuleManagementRepo.find(companyId);
		Optional<OutManage> outManage = outManageRepository.findByID(companyId);
		Optional<ManageEntryExit> entryExit = entryExitRepo.findByID(companyId);
		List<MasterData> data = new ArrayList<>();
		for (int row = 0; row < 13; row++) {
			Map<String, MasterCellData> rowData = new HashMap<>();
			for (int col = 0; col < 4; col++) {
				String value = "";
				if (row == 0) {
					if (col == 0) value = TextResource.localize("KMK013_206");
					else if (col == 1) value = TextResource.localize("KMK013_486");
					else if (col == 3 && optFlexWorkSet.isPresent())
						value = optFlexWorkSet.get().getUseFlexWorkSetting() == UseAtr.USE
								? TextResource.localize("KMK013_209")
								: TextResource.localize("KMK013_210");
				} else if (row == 1) {
					if (col == 1) value = TextResource.localize("KMK013_211");
					else if (col == 3 && optAggSetting.isPresent())
						value = optAggSetting.get().getUseDeformedLabor() == UseAtr.USE
								? TextResource.localize("KMK013_209")
								: TextResource.localize("KMK013_210");
				} else if (row == 2) {
					if (col == 1) value = TextResource.localize("KMK013_216");
					else if (col == 3 && optWorkMultiple.isPresent())
						value = optWorkMultiple.get().getUseATR().value == UseAtr.USE.value
								? TextResource.localize("KMK013_209")
								: TextResource.localize("KMK013_210");
				} else if (row == 3) {
					if (col == 1) value = TextResource.localize("KMK013_220");
					else if (col == 3 && optTempWorkUse.isPresent())
						value = optTempWorkUse.get().getUseClassification() == UseAtr.USE
								? TextResource.localize("KMK013_209")
								: TextResource.localize("KMK013_210");
				} else if (row == 4) {
					if (col == 2) value = TextResource.localize("KMK013_348");
					else if (col == 3 && optTempWorkUse.isPresent() && optTempWorkUse.get().getUseClassification() == UseAtr.USE) {
						if (optTempWorkManage.isPresent()) value = optTempWorkManage.get().getMaxUsage().v() + TextResource.localize("KMK013_471");
					}
				} else if (row == 5) {
					if (col == 2) value = TextResource.localize("KMK013_349");
					else if (col == 3 && optTempWorkUse.isPresent() && optTempWorkUse.get().getUseClassification() == UseAtr.USE) {
						if (optTempWorkManage.isPresent()) value = new TimeWithDayAttr(optTempWorkManage.get().getTimeTreatTemporarySame().v()).getRawTimeWithFormat();
					}
				} else if (row == 6) {
					if (col == 0) value = TextResource.localize("KMK013_487");
					else if (col == 1) value = TextResource.localize("KMK013_364");
					else if (col == 3 && midNightTimeSheet.isPresent()) value = midNightTimeSheet.get().getStart().getFullText();
				} else if (row == 7) {
					if (col == 1) value = TextResource.localize("KMK013_366");
					else if (col == 3 && midNightTimeSheet.isPresent()) value = midNightTimeSheet.get().getEnd().getFullText();
				} else if (row == 8) {
					if (col == 0) value = TextResource.localize("KMK013_488");
					else if (col == 3 && weekRuleManagement.isPresent())
					    value = TextResource.localize(EnumAdaptor.valueOf(weekRuleManagement.get().getWeekStart().value, DayOfWeek.class).nameId);
				} else if (row == 9) {
					if (col == 0) value = TextResource.localize("KMK013_489");
					else if (col == 1) value = TextResource.localize("KMK013_289");
					else if (col == 3) value = TextResource.localize("ENUM_APPLYATR_USE");
				} else if (row == 10) {
					if (col == 2) value = TextResource.localize("KMK013_290");
					else if (col == 3 && outManage.isPresent()) value = outManage.get().getMaxUsage().v() + TextResource.localize("KMK013_471");
				} else if (row == 11) {
					if (col == 2) value = TextResource.localize("KMK013_292");
					else if (col == 3 && outManage.isPresent()) value = TextResource.localize(outManage.get().getInitValueReasonGoOut().nameId);
				} else {
					if (col == 1) value = TextResource.localize("KMK013_227");
					else if (col == 3 && entryExit.isPresent())
						value = entryExit.get().getUseClassification().value == 1
								? TextResource.localize("ENUM_APPLYATR_USE")
								: TextResource.localize("ENUM_APPLYATR_NOT_USE");
				}
				rowData.put(
						col + "",
						MasterCellData.builder()
								.columnId(col + "")
								.value(value)
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
								.build());
			}

			data.add(MasterData.builder().rowData(rowData).build());
		}
		return data;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		return Arrays.asList(
				new MasterHeaderColumn("0", TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("1", "", ColumnTextAlign.CENTER, "", true),
				new MasterHeaderColumn("2", "", ColumnTextAlign.CENTER, "", true),
				new MasterHeaderColumn("3", TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "", true)
		);
	}

	@Override
	public String mainSheetName() {
		return TextResource.localize("KMK013_481");
	}

	private List<MasterHeaderColumn> getSheet2HeaderColumns() {
		return Arrays.asList(
				new MasterHeaderColumn("0", TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("1", "", ColumnTextAlign.CENTER, "", true),
				new MasterHeaderColumn("2", TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "", true)
		);
	}

	private List<MasterData> getSheet2MasterDatas() {
		String companyId = AppContexts.user().companyId();
		List<MasterData> data = new ArrayList<>();

		List<OvertimeWorkFrame> overtimeWorkFrames = overtimeWorkFrameRepo.getOvertimeWorkFrameByFrameByCom(companyId, NotUseAtr.USE.value);
		for (int row = 0; row < overtimeWorkFrames.size(); row ++) {
			OvertimeWorkFrame frame = overtimeWorkFrames.get(row);
			Map<String, MasterCellData> rowData = new HashMap<>();
			for (int col = 0; col < 3; col++) {
				String value = "";
				if (col == 0 && row == 0) value = TextResource.localize("KMK013_383");
				else if (col == 1) value = frame.getOvertimeWorkFrName().v();
				else if (col == 2) value = TextResource.localize(frame.getRole().nameId);
				rowData.put(
						col + "",
						MasterCellData.builder()
								.columnId(col + "")
								.value(value)
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
								.build());
			}
			data.add(MasterData.builder().rowData(rowData).build());
		}

		List<WorkdayoffFrame> workdayoffFrames = workdayoffFrameRepo.findByUseAtr(companyId, NotUseAtr.USE.value);
		for (int row = 0; row < workdayoffFrames.size(); row ++) {
			WorkdayoffFrame frame = workdayoffFrames.get(row);
			Map<String, MasterCellData> rowData = new HashMap<>();
			for (int col = 0; col < 3; col++) {
				String value = "";
				if (col == 0 && row == 0) value = TextResource.localize("KMK013_384");
				else if (col == 1) value = frame.getWorkdayoffFrName().v();
				else if (col == 2) value = TextResource.localize(frame.getRole().nameId);
				rowData.put(
						col + "",
						MasterCellData.builder()
								.columnId(col + "")
								.value(value)
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
								.build());
			}
			data.add(MasterData.builder().rowData(rowData).build());
		}

		return data;
	}

	private List<MasterHeaderColumn> getSheet3HeaderColumns() {
		return Arrays.asList(
				new MasterHeaderColumn("0", TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("1", "", ColumnTextAlign.CENTER, "", true),
				new MasterHeaderColumn("2", "", ColumnTextAlign.CENTER, "", true),
				new MasterHeaderColumn("3", "", ColumnTextAlign.CENTER, "", true),
				new MasterHeaderColumn("4", TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "", true)
		);
	}

	private List<MasterData> getSheet3MasterDatas() {
		String companyId = AppContexts.user().companyId();
        Optional<HolidayAddtionSet> holidayAddtionSet = holidayAddtionRepo.findByCId(companyId);
        Optional<AddSetManageWorkHour> addSetManageWorkHour = addSetManageWorkHourRepo.findByCid(companyId);
        Optional<WorkRegularAdditionSet> workRegularAdditionSet = workRegularAdditionSetRepo.findByCID(companyId);
        Optional<HourlyPaymentAdditionSet> hourlyPaymentAdditionSet = hourlyPaymentAdditionSetRepo.findByCid(companyId);
        Optional<WorkFlexAdditionSet> workFlexAdditionSet = workFlexAdditionSetRepo.findByCid(companyId);
        Optional<WorkDeformedLaborAdditionSet> workDeformedLaborAdditionSet = workDeformedLaborAdditionSetRepo.findByCid(companyId);
		Optional<FlexWorkSet> optFlexWorkSet = flexWorkRepo.find(companyId);
        Optional<AggDeformedLaborSetting> optAggSetting = aggSettingRepo.findByCid(companyId);
		List<MasterData> data = new ArrayList<>();
        for (int row = 0; row < 39; row++) {
            Map<String, MasterCellData> rowData = new HashMap<>();
            for (int col = 0; col < 5; col++) {
                String value = "";
                ColumnTextAlign textAlign = ColumnTextAlign.LEFT;
                if (row == 0) {
					if (col == 0) value = TextResource.localize("KMK013_545");
					else if (col == 1) value = TextResource.localize("KMK013_15");
					else if (col == 4 && holidayAddtionSet.isPresent()) {
                        value = new TimeWithDayAttr(holidayAddtionSet.get().getReference().getComUniformAdditionTime().getOneDay().v()).getRawTimeWithFormat();
                        textAlign = ColumnTextAlign.RIGHT;
                    }
				} else if (row == 1) {
					if (col == 1) value = TextResource.localize("KMK013_17");
					else if (col == 4 && holidayAddtionSet.isPresent()) {
                        value = new TimeWithDayAttr(holidayAddtionSet.get().getReference().getComUniformAdditionTime().getMorning().v()).getRawTimeWithFormat();
                        textAlign = ColumnTextAlign.RIGHT;
                    }
				} else if (row == 2) {
					if (col == 1) value = TextResource.localize("KMK013_19");
					else if (col == 4 && holidayAddtionSet.isPresent()) {
                        value = new TimeWithDayAttr(holidayAddtionSet.get().getReference().getComUniformAdditionTime().getAfternoon().v()).getRawTimeWithFormat();
                        textAlign = ColumnTextAlign.RIGHT;
                    }
				} else if (row == 3) {
					if (col == 0) value = TextResource.localize("KMK013_3");
					else if (col == 4 && holidayAddtionSet.isPresent())
					    value = holidayAddtionSet.get().getReference().getReferenceSet().name;
				} else if (row == 4) {
					if (col == 0) value = TextResource.localize("KMK013_7");
					else if (col == 4
							&& holidayAddtionSet.isPresent() && holidayAddtionSet.get().getReference().getReferIndividualSet().isPresent()
							&& holidayAddtionSet.get().getReference().getReferenceSet() == VacationAdditionTimeRef.REFER_PERSONAL_SET)
					    value = holidayAddtionSet.get().getReference().getReferIndividualSet().get().value == 1
								? TextResource.localize("KMK013_9")
								: TextResource.localize("KMK013_10");
				} else if (row == 5) {
					if (col == 0) value = TextResource.localize("KMK013_21");
					else if (col == 1) value = TextResource.localize("KMK013_22");
					else if (col == 4 && holidayAddtionSet.isPresent())
						value = holidayAddtionSet.get().getAdditionVacationSet().getAnnualHoliday() == NotUseAtr.USE ? "○" : "-";
				} else if (row == 6) {
					if (col == 1) value = TextResource.localize("KMK013_23");
					else if (col == 4 && holidayAddtionSet.isPresent())
						value = holidayAddtionSet.get().getAdditionVacationSet().getYearlyReserved() == NotUseAtr.USE ? "○" : "-";
				} else if (row == 7) {
					if (col == 1) value = TextResource.localize("KMK013_24");
					else if (col == 4 && holidayAddtionSet.isPresent())
						value = holidayAddtionSet.get().getAdditionVacationSet().getSpecialHoliday() == NotUseAtr.USE ? "○" : "-";
				} else if (row == 8) {
					if (col == 0) value = TextResource.localize("KMK013_246");
					else if (col == 1) value = TextResource.localize("KMK013_251");
					else if (col == 4 && holidayAddtionSet.isPresent())
						for (TimeHolidayAdditionSet setting : holidayAddtionSet.get().getTimeHolidayAddition()) {
							if (setting.getWorkClass() == WorkClassOfTimeHolidaySet.WORK_FOR_FLOW) {
								value = setting.getAddingMethod() == TimeHolidayAddingMethod.ADD_ALL_TIME_USED
										? TextResource.localize("KMK013_249")
										: TextResource.localize("KMK013_248");
							}
						}
				} else if (row == 9) {
					if (col == 1) value = TextResource.localize("KMK013_252");
					else if (col == 4 && holidayAddtionSet.isPresent())
						for (TimeHolidayAdditionSet setting : holidayAddtionSet.get().getTimeHolidayAddition()) {
							if (setting.getWorkClass() == WorkClassOfTimeHolidaySet.WORK_FOR_FLEX) {
								value = setting.getAddingMethod() == TimeHolidayAddingMethod.ADD_ALL_TIME_USED
										? TextResource.localize("KMK013_249")
										: TextResource.localize("KMK013_248");
							}
						}
				} else if (row == 10) {
					if (col == 0) value = TextResource.localize("KMK013_253");
					else if (col == 4 && addSetManageWorkHour.isPresent())
						value = addSetManageWorkHour.get().getAdditionSettingOfOvertime() == NotUseAtr.USE
								? TextResource.localize("KMK013_256")
								: TextResource.localize("KMK013_257");
				} else if (row == 11) {
					if (col == 0) value = TextResource.localize("KMK013_25");
					else if (col == 1) value = TextResource.localize("KMK013_519");
					else if (col == 4 && workRegularAdditionSet.isPresent())
						value = workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
								? TextResource.localize("KMK013_42")
								: TextResource.localize("KMK013_43");
				} else if (row == 12) {
					if (col == 2) value = TextResource.localize("KMK013_523");
					else if (col == 4 && workRegularAdditionSet.isPresent()
							&& workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
							&& workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().isPresent())
						value = workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().get().getAddition() == NotUseAtr.USE ? "○" : "-";
				} else if (row == 13) {
					if (col == 2) value = TextResource.localize("KMK013_524");
					else if (col == 4 && workRegularAdditionSet.isPresent()
							&& workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
							&& workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent())
						value = workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getCalculateIncludCareTime() == NotUseAtr.USE ? "○" : "-";
				} else if (row == 14) {
					if (col == 2) value = TextResource.localize("KMK013_525");
					else if (col == 4 && workRegularAdditionSet.isPresent()
							&& workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
							&& workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent())
						value = workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getCalculateIncludIntervalExemptionTime() == NotUseAtr.USE ? "○" : "-";
				} else if (row == 15) {
					if (col == 2) value = TextResource.localize("KMK013_526");
					else if (col == 4 && workRegularAdditionSet.isPresent()
							&& workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
							&& workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent())
						value = workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getTreatLateEarlyTimeSet().getTreatSet().isInclude() ? "○" : "-";
				} else if (row == 16) {
					if (col == 3) value = TextResource.localize("KMK013_527");
					else if (col == 4 && workRegularAdditionSet.isPresent()
							&& workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
							&& workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent()
                            && workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getTreatLateEarlyTimeSet().getTreatSet().isInclude())
                        value = workRegularAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() ? "○" : "-";
				} else if (row == 17) {
                    if (col == 0) value = TextResource.localize("KMK013_422");
                    else if (col == 1) value = TextResource.localize("KMK013_519");
                    else if (col == 4 && hourlyPaymentAdditionSet.isPresent())
                        value = hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                                ? TextResource.localize("KMK013_42")
                                : TextResource.localize("KMK013_43");
                } else if (row == 18) {
                    if (col == 2) value = TextResource.localize("KMK013_523");
                    else if (col == 4 && hourlyPaymentAdditionSet.isPresent()
                            && hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().isPresent())
                        value = hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().get().getAddition() == NotUseAtr.USE ? "○" : "-";
                } else if (row == 19) {
                    if (col == 2) value = TextResource.localize("KMK013_524");
                    else if (col == 4 && hourlyPaymentAdditionSet.isPresent()
                            && hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent())
                    value = hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getCalculateIncludCareTime() == NotUseAtr.USE ? "○" : "-";
                } else if (row == 20) {
                    if (col == 2) value = TextResource.localize("KMK013_525");
                    else if (col == 4 && hourlyPaymentAdditionSet.isPresent()
                            && hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent())
                        value = hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getCalculateIncludIntervalExemptionTime() == NotUseAtr.USE ? "○" : "-";
                } else if (row == 21) {
                    if (col == 2) value = TextResource.localize("KMK013_526");
                    else if (col == 4 && hourlyPaymentAdditionSet.isPresent()
                            && hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent())
                        value = hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getTreatLateEarlyTimeSet().getTreatSet().isInclude() ? "○" : "-";
                } else if (row == 22) {
                    if (col == 3) value = TextResource.localize("KMK013_527");
                    else if (col == 4 && hourlyPaymentAdditionSet.isPresent()
                            && hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent()
                            && hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getTreatLateEarlyTimeSet().getTreatSet().isInclude())
                        value = hourlyPaymentAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() ? "○" : "-";
                } else if (row == 23) {
                    if (col == 0) value = TextResource.localize("KMK013_26");
                    else if (col == 1) value = TextResource.localize("KMK013_534");
                    else if (col == 4 && optFlexWorkSet.isPresent()
                            && optFlexWorkSet.get().getUseFlexWorkSetting() == UseAtr.USE
                            && workFlexAdditionSet.isPresent())
                        value = workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                                ? TextResource.localize("KMK013_42")
                                : TextResource.localize("KMK013_43");
                } else if (row == 24) {
                    if (col == 2) value = TextResource.localize("KMK013_523");
                    else if (col == 4 && optFlexWorkSet.isPresent()
                            && optFlexWorkSet.get().getUseFlexWorkSetting() == UseAtr.USE
                            && workFlexAdditionSet.isPresent()
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().isPresent())
                        value = workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().get().getAddition() == NotUseAtr.USE ? "○" : "-";
                } else if (row == 25) {
                    if (col == 3) value = TextResource.localize("KMK013_536");
                    else if (col == 4 && optFlexWorkSet.isPresent()
                            && optFlexWorkSet.get().getUseFlexWorkSetting() == UseAtr.USE
                            && workFlexAdditionSet.isPresent()
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().isPresent()
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().get().getPredeterminedDeficiencyOfFlex().isPresent())
                        value = TextResource.localize(workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().get().getPredeterminedDeficiencyOfFlex().get().nameId);
                } else if (row == 26) {
                    if (col == 3) value = TextResource.localize("KMK013_258");
                    else if (col == 4 && optFlexWorkSet.isPresent()
                            && optFlexWorkSet.get().getUseFlexWorkSetting() == UseAtr.USE
                            && workFlexAdditionSet.isPresent()
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().isPresent()
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().get().getAdditionWithinMonthlyStatutory().isPresent())
                        value = workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().get().getAdditionWithinMonthlyStatutory().get() == NotUseAtr.USE ? "○" : "-";
                } else if (row == 27) {
                    if (col == 2) value = TextResource.localize("KMK013_524");
                    else if (col == 4 && optFlexWorkSet.isPresent()
                            && optFlexWorkSet.get().getUseFlexWorkSetting() == UseAtr.USE
                            && workFlexAdditionSet.isPresent()
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent())
                        value = workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getCalculateIncludCareTime() == NotUseAtr.USE ? "○" : "-";
                } else if (row == 28) {
                    if (col == 2) value = TextResource.localize("KMK013_525");
                    else if (col == 4 && optFlexWorkSet.isPresent()
                            && optFlexWorkSet.get().getUseFlexWorkSetting() == UseAtr.USE
                            && workFlexAdditionSet.isPresent()
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent())
                        value = workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getCalculateIncludIntervalExemptionTime() == NotUseAtr.USE ? "○" : "-";
                } else if (row == 29) {
                    if (col == 2) value = TextResource.localize("KMK013_537");
                    else if (col == 4 && optFlexWorkSet.isPresent()
                            && optFlexWorkSet.get().getUseFlexWorkSetting() == UseAtr.USE
                            && workFlexAdditionSet.isPresent()
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().isPresent()
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().get().getMinusAbsenceTime().isPresent())
                        value = workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().get().getMinusAbsenceTime().get() == NotUseAtr.USE ? "○" : "-";
                } else if (row == 30) {
                    if (col == 2) value = TextResource.localize("KMK013_526");
                    else if (col == 4 && optFlexWorkSet.isPresent()
                            && optFlexWorkSet.get().getUseFlexWorkSetting() == UseAtr.USE
                            && workFlexAdditionSet.isPresent()
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent())
                        value = workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getTreatLateEarlyTimeSet().getTreatSet().isInclude() ? "○" : "-";
                } else if (row == 31) {
                    if (col == 3) value = TextResource.localize("KMK013_527");
                    else if (col == 4 && optFlexWorkSet.isPresent()
                            && optFlexWorkSet.get().getUseFlexWorkSetting() == UseAtr.USE
                            && workFlexAdditionSet.isPresent()
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent()
                            && workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getTreatLateEarlyTimeSet().getTreatSet().isInclude())
                        value = workFlexAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() ? "○" : "-";
                } else if (row == 32) {
                    if (col == 0) value = TextResource.localize("KMK013_27");
                    else if (col == 1) value = TextResource.localize("KMK013_541");
                    else if (col == 4 && optAggSetting.isPresent()
                            && optAggSetting.get().getUseDeformedLabor() == UseAtr.USE
                            && workDeformedLaborAdditionSet.isPresent())
                        value = workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                                ? TextResource.localize("KMK013_42")
                                : TextResource.localize("KMK013_43");
                } else if (row == 33) {
                    if (col == 2) value = TextResource.localize("KMK013_523");
                    else if (col == 4 && optAggSetting.isPresent()
                            && optAggSetting.get().getUseDeformedLabor() == UseAtr.USE
                            && workDeformedLaborAdditionSet.isPresent()
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().isPresent())
                        value = workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().get().getAddition() == NotUseAtr.USE ? "○" : "-";
                } else if (row == 34) {
                    if (col == 2) value = TextResource.localize("KMK013_524");
                    else if (col == 4 && optAggSetting.isPresent()
                            && optAggSetting.get().getUseDeformedLabor() == UseAtr.USE
                            && workDeformedLaborAdditionSet.isPresent()
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent())
                        value = workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getCalculateIncludCareTime() == NotUseAtr.USE ? "○" : "-";
                } else if (row == 35) {
                    if (col == 2) value = TextResource.localize("KMK013_525");
                    else if (col == 4 && optAggSetting.isPresent()
                            && optAggSetting.get().getUseDeformedLabor() == UseAtr.USE
                            && workDeformedLaborAdditionSet.isPresent()
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent())
                        value = workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getCalculateIncludIntervalExemptionTime() == NotUseAtr.USE ? "○" : "-";
                } else if (row == 36) {
                    if (col == 2) value = TextResource.localize("KMK013_537");
                    else if (col == 4 && optAggSetting.isPresent()
                            && optAggSetting.get().getUseDeformedLabor() == UseAtr.USE
                            && workDeformedLaborAdditionSet.isPresent()
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().isPresent()
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().get().getMinusAbsenceTime().isPresent())
                        value = workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatVacation().get().getMinusAbsenceTime().get() == NotUseAtr.USE ? "○" : "-";
                } else if (row == 37) {
                    if (col == 2) value = TextResource.localize("KMK013_526");
                    else if (col == 4 && optAggSetting.isPresent()
                            && optAggSetting.get().getUseDeformedLabor() == UseAtr.USE
                            && workDeformedLaborAdditionSet.isPresent()
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent())
                        value = workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getTreatLateEarlyTimeSet().getTreatSet().isInclude() ? "○" : "-";
                } else if (row == 38) {
                    if (col == 3) value = TextResource.localize("KMK013_527");
                    else if (col == 4 && optAggSetting.isPresent()
                            && optAggSetting.get().getUseDeformedLabor() == UseAtr.USE
                            && workDeformedLaborAdditionSet.isPresent()
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getCalculateActualOperation() == CalcurationByActualTimeAtr.CALCULATION_OTHER_THAN_ACTUAL_TIME
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().isPresent()
                            && workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getTreatLateEarlyTimeSet().getTreatSet().isInclude())
                        value = workDeformedLaborAdditionSet.get().getAddSetOfWorkingTime().getAddSetOfWorkTime().getTreatDeduct().get().getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() ? "○" : "-";
                }
                rowData.put(
                        col + "",
                        MasterCellData.builder()
                                .columnId(col + "")
                                .value(value)
                                .style(MasterCellStyle.build().horizontalAlign(textAlign))
                                .build());
            }
            data.add(MasterData.builder().rowData(rowData).build());
        }
		return data;
	}

	private List<MasterHeaderColumn> getSheet4HeaderColumns() {
		return Arrays.asList(
				new MasterHeaderColumn("0", TextResource.localize("KMK013_447"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("1", TextResource.localize("KMK013_448"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("2", TextResource.localize("KMK013_449"), ColumnTextAlign.LEFT, "", true)
		);
	}

	private List<MasterData> getSheet4MasterDatas() {
		String companyId = AppContexts.user().companyId();
		List<MasterData> data = new ArrayList<>();
		Optional<RoundingSetOfMonthly> roundingSetOfMonthly = roundingSetOfMonthlyRepo.find(companyId);
		List<AttendanceNameDivergenceDto> monthlyAttendanceItems = roundingSetOfMonthly.isPresent()
				? monthlyAttendanceItemRepo.getMonthlyAtName(new ArrayList<>(roundingSetOfMonthly.get().getItemRoundingSet().keySet()))
				: new ArrayList<>();
		monthlyAttendanceItems.sort(Comparator.comparing(AttendanceNameDivergenceDto::getAttendanceItemDisplayNumber));
		for (int row = 0; row < monthlyAttendanceItems.size(); row++) {
			AttendanceNameDivergenceDto item = monthlyAttendanceItems.get(row);
			Map<String, MasterCellData> rowData = new HashMap<>();
			for (int col = 0; col < 3; col++) {
				String value = "";
				if (col == 0) value = item.getAttendanceItemName();
				else if (col == 1 && roundingSetOfMonthly.isPresent() && roundingSetOfMonthly.get().getItemRoundingSet().get(item.getAttendanceItemId()) != null)
					value = TextResource.localize(roundingSetOfMonthly.get().getItemRoundingSet().get(item.getAttendanceItemId()).getRoundingSet().getRoundingTime().nameId);
				else if (col == 2 && roundingSetOfMonthly.isPresent() && roundingSetOfMonthly.get().getItemRoundingSet().get(item.getAttendanceItemId()) != null)
					value = TextResource.localize(roundingSetOfMonthly.get().getItemRoundingSet().get(item.getAttendanceItemId()).getRoundingSet().getRounding().nameId);
				rowData.put(
						col + "",
						MasterCellData.builder()
								.columnId(col + "")
								.value(value)
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
								.build());
			}
			data.add(MasterData.builder().rowData(rowData).build());
		}
		return data;
	}

	private List<MasterHeaderColumn> getSheet5HeaderColumns() {
		return Arrays.asList(
				new MasterHeaderColumn("0", TextResource.localize("KMK013_89"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("1", TextResource.localize("KMK013_450"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("2", TextResource.localize("KMK013_451"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("3", TextResource.localize("KMK013_452"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("4", TextResource.localize("KMK013_453"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("5", TextResource.localize("KMK013_454"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("6", TextResource.localize("KMK013_455"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("7", TextResource.localize("KMK013_456"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("8", TextResource.localize("KMK013_457"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("9", TextResource.localize("KMK013_458"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("10", TextResource.localize("KMK013_459"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("11", TextResource.localize("KMK013_460"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("12", TextResource.localize("KMK013_461"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("13", TextResource.localize("KMK013_462"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("14", TextResource.localize("KMK013_463"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("15", TextResource.localize("KMK013_464"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("16", TextResource.localize("KMK013_465"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("17", TextResource.localize("KMK013_466"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("18", TextResource.localize(""), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("19", TextResource.localize("KMK013_467"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("20", TextResource.localize("KMK013_468"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("21", TextResource.localize("KMK013_469"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("22", TextResource.localize("KMK013_470"), ColumnTextAlign.LEFT, "", true)
		);
	}

	private List<MasterData> getSheet5MasterDatas() {
		String companyId = AppContexts.user().companyId();
		Optional<ZeroTime> zeroTime = zeroTimeRepo.findByCId(companyId);
		List<WorkdayoffFrame> workdayoffFrames = workdayoffFrameRepo.findByUseAtr(companyId, UseAtr.USE.value);
		List<OvertimeWorkFrame> otWorkFrames = overtimeWorkFrameRepo.getOvertimeWorkFrameByFrameByCom(companyId, UseAtr.USE.value);
		int rows = Math.max(1, Math.max(workdayoffFrames.size(), otWorkFrames.size()));
		List<MasterData> data = new ArrayList<>();
		for (int row = 0; row < rows; row++) {
			Map<String, MasterCellData> rowData = new HashMap<>();
			for (int col = 0; col < 23; col++) {
				String value = "";
				if (row == 0 && col == 0 && zeroTime.isPresent())
					value = zeroTime.get().getCalcFromZeroTime() == 1
							? TextResource.localize("KMK013_91")
							: TextResource.localize("KMK013_92");
				else if (row == 0 && col == 1 && zeroTime.isPresent() && zeroTime.get().getCalcFromZeroTime() == 1)
					value = zeroTime.get().getLegalHd() == 1
							? TextResource.localize("KMK013_96")
							: TextResource.localize("KMK013_97");
				else if (row == 0 && col == 2 && zeroTime.isPresent() && zeroTime.get().getCalcFromZeroTime() == 1)
					value = zeroTime.get().getNonLegalHd() == 1
							? TextResource.localize("KMK013_96")
							: TextResource.localize("KMK013_97");
				else if (row == 0 && col == 3 && zeroTime.isPresent() && zeroTime.get().getCalcFromZeroTime() == 1)
					value = zeroTime.get().getNonLegalPublicHd() == 1
							? TextResource.localize("KMK013_96")
							: TextResource.localize("KMK013_97");
				else if (row == 0 && col == 4 && zeroTime.isPresent() && zeroTime.get().getCalcFromZeroTime() == 1)
					value = zeroTime.get().getWeekday1() == 1
							? TextResource.localize("KMK013_96")
							: TextResource.localize("KMK013_97");
				else if (row == 0 && col == 5 && zeroTime.isPresent() && zeroTime.get().getCalcFromZeroTime() == 1)
					value = zeroTime.get().getNonLegalHd1() == 1
							? TextResource.localize("KMK013_96")
							: TextResource.localize("KMK013_97");
				else if (row == 0 && col == 6 && zeroTime.isPresent() && zeroTime.get().getCalcFromZeroTime() == 1)
					value = zeroTime.get().getNonLegalPublicHd1() == 1
							? TextResource.localize("KMK013_96")
							: TextResource.localize("KMK013_97");
				else if (row == 0 && col == 7 && zeroTime.isPresent() && zeroTime.get().getCalcFromZeroTime() == 1)
					value = zeroTime.get().getWeekday2() == 1
							? TextResource.localize("KMK013_96")
							: TextResource.localize("KMK013_97");
				else if (row == 0 && col == 8 && zeroTime.isPresent() && zeroTime.get().getCalcFromZeroTime() == 1)
					value = zeroTime.get().getLegalHd2() == 1
							? TextResource.localize("KMK013_96")
							: TextResource.localize("KMK013_97");
				else if (row == 0 && col == 9 && zeroTime.isPresent() && zeroTime.get().getCalcFromZeroTime() == 1)
					value = zeroTime.get().getNonLegalHd2() == 1
							? TextResource.localize("KMK013_96")
							: TextResource.localize("KMK013_97");
				else if (row == 0 && col == 10 && zeroTime.isPresent() && zeroTime.get().getCalcFromZeroTime() == 1)
					value = zeroTime.get().getWeekday3() == 1
							? TextResource.localize("KMK013_96")
							: TextResource.localize("KMK013_97");
				else if (row == 0 && col == 11 && zeroTime.isPresent() && zeroTime.get().getCalcFromZeroTime() == 1)
					value = zeroTime.get().getLegalHd3() == 1
							? TextResource.localize("KMK013_96")
							: TextResource.localize("KMK013_97");
				else if (row == 0 && col == 12 && zeroTime.isPresent() && zeroTime.get().getCalcFromZeroTime() == 1)
					value = zeroTime.get().getNonLegalPublicHd3() == 1
							? TextResource.localize("KMK013_96")
							: TextResource.localize("KMK013_97");
				else if (col == 13 && otWorkFrames.size() > row) {
					value = otWorkFrames.get(row).getOvertimeWorkFrName().v();
				} else if (col == 14 && otWorkFrames.size() > row && zeroTime.isPresent()) {
					OvertimeWorkFrame otFrame = otWorkFrames.get(row);
					Optional<WeekdayHoliday> weekdayHoliday = zeroTime.get().getWeekdayHoliday().stream().filter(i -> i.getOverworkFrameNo().intValue() == otFrame.getOvertimeWorkFrNo().v().intValue()).findFirst();
					if (weekdayHoliday.isPresent()) {
						if (weekdayHoliday.get().getWeekdayNo() == 0) value = TextResource.localize("KMK013_235");
						else {
							Optional<WorkdayoffFrame> workdayoffFrame = workdayoffFrames.stream().filter(i -> i.getWorkdayoffFrNo().v().intValue() == weekdayHoliday.get().getWeekdayNo()).findFirst();
							if (workdayoffFrame.isPresent()) value = workdayoffFrame.get().getWorkdayoffFrName().v();
						}
					}
				} else if (col == 15 && otWorkFrames.size() > row && zeroTime.isPresent()) {
					OvertimeWorkFrame otFrame = otWorkFrames.get(row);
					Optional<WeekdayHoliday> weekdayHoliday = zeroTime.get().getWeekdayHoliday().stream().filter(i -> i.getOverworkFrameNo().intValue() == otFrame.getOvertimeWorkFrNo().v().intValue()).findFirst();
					if (weekdayHoliday.isPresent()) {
						if (weekdayHoliday.get().getExcessHolidayNo() == 0) value = TextResource.localize("KMK013_235");
						else {
							Optional<WorkdayoffFrame> workdayoffFrame = workdayoffFrames.stream().filter(i -> i.getWorkdayoffFrNo().v().intValue() == weekdayHoliday.get().getExcessHolidayNo()).findFirst();
							if (workdayoffFrame.isPresent()) value = workdayoffFrame.get().getWorkdayoffFrName().v();
						}
					}
				} else if (col == 16 && otWorkFrames.size() > row && zeroTime.isPresent()) {
					OvertimeWorkFrame otFrame = otWorkFrames.get(row);
					Optional<WeekdayHoliday> weekdayHoliday = zeroTime.get().getWeekdayHoliday().stream().filter(i -> i.getOverworkFrameNo().intValue() == otFrame.getOvertimeWorkFrNo().v().intValue()).findFirst();
					if (weekdayHoliday.isPresent()) {
						if (weekdayHoliday.get().getExcessSphdNo() == 0) value = TextResource.localize("KMK013_235");
						else {
							Optional<WorkdayoffFrame> workdayoffFrame = workdayoffFrames.stream().filter(i -> i.getWorkdayoffFrNo().v().intValue() == weekdayHoliday.get().getExcessSphdNo()).findFirst();
							if (workdayoffFrame.isPresent()) value = workdayoffFrame.get().getWorkdayoffFrName().v();
						}
					}
				} else if (col == 17 && workdayoffFrames.size() > row) {
					value = workdayoffFrames.get(row).getWorkdayoffFrName().v();
				} else if (col == 18 && workdayoffFrames.size() > row && zeroTime.isPresent()) {
					WorkdayoffFrame workdayoffFrame = workdayoffFrames.get(row);
					Optional<HdFromWeekday> hdFromWeekday = zeroTime.get().getOverdayHolidayAtten().stream().filter(i -> i.getHolidayWorkFrameNo() == workdayoffFrame.getWorkdayoffFrNo().v().intValue()).findFirst();
					if (hdFromWeekday.isPresent()) {
						if (hdFromWeekday.get().getOverWorkNo() == null || hdFromWeekday.get().getOverWorkNo().intValue() == 0)
							value = TextResource.localize("KMK013_235");
						else {
							Optional<OvertimeWorkFrame> otFrame = otWorkFrames.stream().filter(i -> i.getOvertimeWorkFrNo().v().intValue() == hdFromWeekday.get().getOverWorkNo().intValue()).findFirst();
							if (otFrame.isPresent()) value = otFrame.get().getOvertimeWorkFrName().v();
						}
					}
				} else if (col == 19 && workdayoffFrames.size() > row) {
					value = workdayoffFrames.get(row).getWorkdayoffFrName().v();
				} else if (col == 20 && workdayoffFrames.size() > row && zeroTime.isPresent()) {
					WorkdayoffFrame workdayoffFrame = workdayoffFrames.get(row);
					Optional<HdFromHd> hdFromHd = zeroTime.get().getOverdayCalcHoliday().stream().filter(i -> i.getHolidayWorkFrameNo() == workdayoffFrame.getWorkdayoffFrNo().v().intValue()).findFirst();
					if (hdFromHd.isPresent()) {
						if (hdFromHd.get().getCalcOverDayEnd() == 0) value = TextResource.localize("KMK013_235");
						else {
							Optional<WorkdayoffFrame> dayoffFrame = workdayoffFrames.stream().filter(i -> i.getWorkdayoffFrNo().v().intValue() == hdFromHd.get().getCalcOverDayEnd()).findFirst();
							if (dayoffFrame.isPresent()) value = dayoffFrame.get().getWorkdayoffFrName().v();
						}
					}
				} else if (col == 21 && workdayoffFrames.size() > row && zeroTime.isPresent()) {
					WorkdayoffFrame workdayoffFrame = workdayoffFrames.get(row);
					Optional<HdFromHd> hdFromHd = zeroTime.get().getOverdayCalcHoliday().stream().filter(i -> i.getHolidayWorkFrameNo() == workdayoffFrame.getWorkdayoffFrNo().v().intValue()).findFirst();
					if (hdFromHd.isPresent()) {
						if (hdFromHd.get().getStatutoryHd() == 0) value = TextResource.localize("KMK013_235");
						else {
							Optional<WorkdayoffFrame> dayoffFrame = workdayoffFrames.stream().filter(i -> i.getWorkdayoffFrNo().v().intValue() == hdFromHd.get().getStatutoryHd()).findFirst();
							if (dayoffFrame.isPresent()) value = dayoffFrame.get().getWorkdayoffFrName().v();
						}
					}
				} else if (col == 22 && workdayoffFrames.size() > row && zeroTime.isPresent()) {
					WorkdayoffFrame workdayoffFrame = workdayoffFrames.get(row);
					Optional<HdFromHd> hdFromHd = zeroTime.get().getOverdayCalcHoliday().stream().filter(i -> i.getHolidayWorkFrameNo() == workdayoffFrame.getWorkdayoffFrNo().v().intValue()).findFirst();
					if (hdFromHd.isPresent()) {
						if (hdFromHd.get().getExcessHd() == 0) value = TextResource.localize("KMK013_235");
						else {
							Optional<WorkdayoffFrame> dayoffFrame = workdayoffFrames.stream().filter(i -> i.getWorkdayoffFrNo().v().intValue() == hdFromHd.get().getExcessHd()).findFirst();
							if (dayoffFrame.isPresent()) value = dayoffFrame.get().getWorkdayoffFrName().v();
						}
					}
				}
				rowData.put(
						col + "",
						MasterCellData.builder()
								.columnId(col + "")
								.value(value)
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
								.build());
			}
			data.add(MasterData.builder().rowData(rowData).build());
		}
		return data;
	}

	private List<MasterHeaderColumn> getSheet6HeaderColumns() {
		return Arrays.asList(
				new MasterHeaderColumn("0", TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("1", "", ColumnTextAlign.CENTER, "", true),
				new MasterHeaderColumn("2", TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "", true)
		);
	}

	private List<MasterData> getSheet6MasterDatas() {
		String companyId = AppContexts.user().companyId();
		List<MasterData> data = new ArrayList<>();
		Map<String, MasterCellData> rowData = new HashMap<>();
		for (int col = 0; col < 3; col++){
			String value = "";
			if (col == 0) value = TextResource.localize("KMK013_306");
			else if (col == 1) value = TextResource.localize("KMK013_307");
			else {
				Optional<AggDeformedLaborSetting> optAggSetting = aggSettingRepo.findByCid(companyId);
				Optional<DeformLaborOT> domain = deformLaborOTRepo.findByCId(companyId);
				if (optAggSetting.isPresent()
						&& optAggSetting.get().getUseDeformedLabor() == UseAtr.USE
						&& domain.isPresent()) {
					value = domain.get().getLegalOtCalc().value == 1
							? TextResource.localize("KMK013_209")
							: TextResource.localize("KMK013_210");
				}
			}
			rowData.put(
					col + "",
					MasterCellData.builder()
							.columnId(col + "")
							.value(value)
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
							.build());
		}
		data.add(MasterData.builder().rowData(rowData).build());
		return data;
	}

	private List<MasterHeaderColumn> getSheet7HeaderColumns() {
		return Arrays.asList(
				new MasterHeaderColumn("0", TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "", true),
				new MasterHeaderColumn("1", "", ColumnTextAlign.CENTER, "", true),
				new MasterHeaderColumn("2", TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "", true)
		);
	}

	private List<MasterData> getSheet7MasterDatas() {
		String companyId = AppContexts.user().companyId();
		Optional<FlexSet> flexSet = flexSetRepo.findByCId(companyId);
		Optional<InsufficientFlexHolidayMnt> insufficientFlexHolidayMnt = insufficientFlexHolidayMntRepo.findByCId(companyId);
		List<MasterData> data = new ArrayList<>();
		for (int row = 0; row < 5; row++) {
            Map<String, MasterCellData> rowData = new HashMap<>();
			for (int col = 0; col < 3; col++) {
				String value = "";
				if (row == 0) {
					if (col == 0) value = TextResource.localize("KMK013_578");
					else if (col == 2 && flexSet.isPresent()) {
						value = flexSet.get().getCompLeave().isDeductFromPred()
					    		? TextResource.localize("KMK013_580")
					    		: TextResource.localize("KMK013_579");
					}
				} else if (row == 1) {
					if (col == 0) value = TextResource.localize("KMK013_178");
					else if (col == 2 && flexSet.isPresent()) {
						value = flexSet.get().getHalfHoliday().getCalcLack().equals(FlexCalcMethod.PREDETERMINED_TIME_HALF_DAY)
								? TextResource.localize("KMK013_492")
								: TextResource.localize("KMK013_493");
					}
				} else if (row == 2) {
					if (col == 0) value = TextResource.localize("KMK013_188");
					else if (col == 2 && flexSet.isPresent()) {
						value = flexSet.get().getCompLeave().getCalcPremium().equals(FlexCalcMethod.PREDETERMINED_TIME_HALF_DAY)
								? TextResource.localize("KMK013_492")
								: TextResource.localize("KMK013_493");
					}
				} else if (row == 3) {
					if (col == 0) value = TextResource.localize("KMK013_262");
					else if (col == 2 && flexSet.isPresent()) {
						value = flexSet.get().getCompLeave().getCalcSetOfTimeCompLeave().equals(FlexCalcSetOfTimeCompLeave.DEDUCT_ON_COMPENSATORY_TIME)
								? TextResource.localize("KMK013_265")
								: TextResource.localize("KMK013_264");
					}
				} else {
					if (col == 0) value = TextResource.localize("KMK013_270");
					else if (col == 1) value = TextResource.localize("KMK013_271");
					else if (insufficientFlexHolidayMnt.isPresent())
					    value = insufficientFlexHolidayMnt.get().getSupplementableDays().v() + TextResource.localize("KMK013_472");
				}
				rowData.put(
						col + "",
						MasterCellData.builder()
								.columnId(col + "")
								.value(value)
								.style(MasterCellStyle.build().horizontalAlign(row == 6 && col == 2 ? ColumnTextAlign.RIGHT : ColumnTextAlign.LEFT))
								.build());
			}
			data.add(MasterData.builder().rowData(rowData).build());
		}
		return data;
	}

    private List<MasterHeaderColumn> getSheet8HeaderColumns() {
        return Arrays.asList(
                new MasterHeaderColumn("0", TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "", true),
                new MasterHeaderColumn("1", "", ColumnTextAlign.CENTER, "", true),
                new MasterHeaderColumn("2", TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "", true)
        );
    }

    private List<MasterData> getSheet8MasterDatas() {
        String companyId = AppContexts.user().companyId();
        Optional<StampReflectionManagement> stampReflectionMng = stampReflectionManagementRepo.findByCid(companyId);
        List<MasterData> data = new ArrayList<>();
        for (int row = 0; row < 6; row++) {
            Map<String, MasterCellData> rowData = new HashMap<>();
            for (int col = 0; col < 3; col++) {
                String value = "";
                if (row == 0){
                    if (col == 0) value = TextResource.localize("KMK013_277");
                    else if (col == 2 && stampReflectionMng.isPresent())
                        value = stampReflectionMng.get().getAutoStampReflectionClass().value == 1
                                ? TextResource.localize("KMK013_279")
                                : TextResource.localize("KMK013_280");
                } else if (row == 1){
                    if (col == 0) value = TextResource.localize("KMK013_298");
                    else if (col == 2 && stampReflectionMng.isPresent())
                        value = stampReflectionMng.get().getActualStampOfPriorityClass().value == 1
                                ? TextResource.localize("KMK013_301")
                                : TextResource.localize("KMK013_300");
                } else if (row == 2){
                    if (col == 0) value = TextResource.localize("KMK013_281");
                    else if (col == 2 && stampReflectionMng.isPresent())
                        value = stampReflectionMng.get().getAutoStampForFutureDayClass().value == 1
                                ? TextResource.localize("KMK013_283")
                                : TextResource.localize("KMK013_284");
                } else if (row == 3){
                    if (col == 0) value = TextResource.localize("KMK013_294");
                    else if (col == 1) value = TextResource.localize("KMK013_507");
                    else if (stampReflectionMng.isPresent())
                        value = stampReflectionMng.get().getGoBackOutCorrectionClass().value == 1
                                ? TextResource.localize("KMK013_209")
                                : TextResource.localize("KMK013_210");
                } else if (row == 4){
                    if (col == 0) value = TextResource.localize("KMK013_508");
                    else if (col == 1) value = TextResource.localize("KMK013_509");
                    else if (stampReflectionMng.isPresent())
                        value = stampReflectionMng.get().getReflectWorkingTimeClass().value == 1
                                ? TextResource.localize("KMK013_209")
                                : TextResource.localize("KMK013_210");
                } else {
                    if (col == 0) value = TextResource.localize("KMK013_302");
                    else if (col == 2 && stampReflectionMng.isPresent())
                        value = stampReflectionMng.get().getBreakSwitchClass().value == 1
                                ? TextResource.localize("KMK013_305")
                                : TextResource.localize("KMK013_304");
                }
                rowData.put(
                        col + "",
                        MasterCellData.builder()
                                .columnId(col + "")
                                .value(value)
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
            }
            data.add(MasterData.builder().rowData(rowData).build());
        }
        return data;
    }

    private List<MasterHeaderColumn> getSheet9HeaderColumns() {
        return Arrays.asList(
                new MasterHeaderColumn("0", TextResource.localize("KMK013_445"), ColumnTextAlign.LEFT, "", true),
                new MasterHeaderColumn("1", "", ColumnTextAlign.CENTER, "", true),
                new MasterHeaderColumn("2", TextResource.localize("KMK013_446"), ColumnTextAlign.LEFT, "", true)
        );
    }

    private List<MasterData> getSheet9MasterDatas() {
        String companyId = AppContexts.user().companyId();
        Optional<UpperLimitTotalWorkingHour> upperLimitTotalWorkingHour = specificWorkRuleRepo.findUpperLimitWkHourByCid(companyId);
        Optional<CalculateOfTotalConstraintTime> calculateOfTotalConstraintTime = specificWorkRuleRepo.findCalcMethodByCid(companyId);
        Optional<CompanyHolidayPriorityOrder> timeOffVacationPriorityOrder = specificWorkRuleRepo.findTimeOffVacationOrderByCid(companyId);
        Map<Integer, String> timeOffMap = new HashMap<>();
        if (timeOffVacationPriorityOrder.isPresent()) {
        	val orders = timeOffVacationPriorityOrder.get().getHolidayPriorityOrders();
            timeOffMap.put(orders.indexOf(HolidayPriorityOrder.SUB_HOLIDAY) + 1, TextResource.localize("KMK013_378"));
            timeOffMap.put(orders.indexOf(HolidayPriorityOrder.SIXTYHOUR_HOLIDAY) + 1, TextResource.localize("KMK013_378"));
            timeOffMap.put(orders.indexOf(HolidayPriorityOrder.ANNUAL_HOLIDAY) + 1, TextResource.localize("KMK013_377"));
            timeOffMap.put(orders.indexOf(HolidayPriorityOrder.SPECIAL_HOLIDAY) + 1, TextResource.localize("KMK013_379"));
            timeOffMap.put(orders.indexOf(HolidayPriorityOrder.CHILD_CARE) + 1, TextResource.localize("KMK013_476"));
            timeOffMap.put(orders.indexOf(HolidayPriorityOrder.CARE) + 1, TextResource.localize("KMK013_477"));
        } else {
            timeOffMap.put(1, TextResource.localize("KMK013_378"));
            timeOffMap.put(2, TextResource.localize("KMK013_377"));
            timeOffMap.put(3, TextResource.localize("KMK013_376"));
            timeOffMap.put(4, TextResource.localize("KMK013_379"));
            timeOffMap.put(5, TextResource.localize("KMK013_476"));
            timeOffMap.put(6, TextResource.localize("KMK013_477"));
        }
        Optional<AggregateMethodOfMonthly> aggregateMethodOfMonthly = verticalTotalMethodOfMonthlyRepo.findByCid(companyId);
        List<MasterData> data = new ArrayList<>();
        for (int row = 0; row < 10; row++) {
            Map<String, MasterCellData> rowData = new HashMap<>();
            for (int col = 0; col < 3; col++) {
                String value = "";
                if (row == 0){
                    if (col == 0) value = TextResource.localize("KMK013_351");
                    else if (col == 2 && upperLimitTotalWorkingHour.isPresent())
                        value = upperLimitTotalWorkingHour.get().getLimitSet().value == 0
                                ? TextResource.localize("上限制御しない")
                                : (upperLimitTotalWorkingHour.get().getLimitSet().value == 1
                                ? TextResource.localize("総労働時間を上限にする")
                                : TextResource.localize("総計算時間を上限にする"));
                } else if (row == 1){
                    if (col == 0) value = TextResource.localize("KMK013_356");
                    else if (col == 2 && calculateOfTotalConstraintTime.isPresent())
                        value = calculateOfTotalConstraintTime.get().getCalcMethod().value == 0
                                ? TextResource.localize("PCログイン・ログオフから求める（出勤退勤よりも外側の場合のみ）")
                                : (calculateOfTotalConstraintTime.get().getCalcMethod().value == 1
                                ? TextResource.localize("入門退門から求める")
                                : TextResource.localize("入門退門から求める（出勤退勤よりも外側の場合のみ）"));
                } else if (row == 2){
                    if (col == 0) value = TextResource.localize("KMK013_361");
                    else if (col == 1) value = TextResource.localize("KMK013_372");
                    else value = timeOffMap.get(1);
                } else if (row == 3){
                    if (col == 1) value = TextResource.localize("KMK013_373");
                    else if (col == 2) value = timeOffMap.get(2);
                } else if (row == 4){
                    if (col == 1) value = TextResource.localize("KMK013_374");
                    else if (col == 2) value = timeOffMap.get(3);
                } else if (row == 5){
                    if (col == 1) value = TextResource.localize("KMK013_375");
                    else if (col == 2) value = timeOffMap.get(4);
                } else if (row == 6){
                    if (col == 0) value = TextResource.localize("KMK013_309");
                    else if (col == 1) value = TextResource.localize("KMK013_310");
                    else if (aggregateMethodOfMonthly.isPresent())
                        value = aggregateMethodOfMonthly.get().getTransferAttendanceDays().getTADaysCountCondition().value == 1
                                ? TextResource.localize("KMK013_312")
                                : TextResource.localize("KMK013_311");
                } else if (row == 7){
                    if (col == 0) value = TextResource.localize("KMK013_313");
                    else if (col == 1) value = TextResource.localize("KMK013_314");
                    else if (aggregateMethodOfMonthly.isPresent())
                        value = aggregateMethodOfMonthly.get().getSpecTotalCountMonthly().getSpecCount().value == 0
                                ? TextResource.localize("勤務日のみカウントする")
                                : (aggregateMethodOfMonthly.get().getSpecTotalCountMonthly().getSpecCount().value == 1
                                ? TextResource.localize("無条件にカウントしない")
                                : TextResource.localize("無条件にカウントする"));
                } else if (row == 8){
                    if (col == 1) value = TextResource.localize("KMK013_319");
                    else if (col == 2 && aggregateMethodOfMonthly.isPresent())
                        value = aggregateMethodOfMonthly.get().getSpecTotalCountMonthly().isContinuousCount()
                                ? TextResource.localize("KMK013_209")
                                : TextResource.localize("KMK013_210");
                } else {
                    if (col == 1) value = TextResource.localize("KMK013_321");
                    else if (col == 2 && aggregateMethodOfMonthly.isPresent())
                        value = aggregateMethodOfMonthly.get().getSpecTotalCountMonthly().isNotWorkCount()
                                ? TextResource.localize("KMK013_209")
                                : TextResource.localize("KMK013_210");
                }
                rowData.put(
                        col + "",
                        MasterCellData.builder()
                                .columnId(col + "")
                                .value(value)
                                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                                .build());
            }
            data.add(MasterData.builder().rowData(rowData).build());
        }
        return data;
    }

}
