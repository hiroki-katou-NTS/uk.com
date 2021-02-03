package nts.uk.file.at.app.export.alarm.checkcondition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.divergence.time.DivergenceAttendanceItemFinder;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeCheckVacation;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeMonCheckItem;
import nts.uk.file.at.app.export.alarm.checkcondition.Agree36ReportData.Agree36CondError;
import nts.uk.file.at.app.export.alarm.checkcondition.Agree36ReportData.Agree36OTError;
import nts.uk.file.at.app.export.alarm.checkcondition.AlarmCheckAnnualHolidayData.AlarmCheckHdpaid;
import nts.uk.file.at.app.export.alarm.checkcondition.AlarmCheckAnnualHolidayData.AlarmCheckHdpaidObl;
import nts.uk.shr.com.context.AppContexts;
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

/**
 *
 * 
 * @author HiepTH
 *
 */
@Stateless
@DomainID(value = "AlarmCheckCondition")
public class AlarmCheckConditionExportImpl implements MasterListData {

	@Inject
	private AlarmCheckConditionReportRepository alarmCheckConditionReportRepository;

	// get attendance item name
	@Inject
	private DivergenceAttendanceItemFinder divergenceItemSetFinder;

	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query) {
		List<SheetData> sheetDatas = new ArrayList<>();
		SheetData sheetDailyData = new SheetData(getMasterDatasDaily(query), getHeaderColumnsDaily(query), null, null,
				TextResource.localize("KAL003_258"), MasterListMode.NONE);
		SheetData sheetMonthData = new SheetData(getMasterDatasMonth(query), getHeaderColumnsMonth(query),
				null, null, TextResource.localize("KAL003_275"), MasterListMode.NONE);
		SheetData sheetMulMonthData = new SheetData(getMasterDatasMulMonth(query), getHeaderColumnsMulMonth(query),
				null, null, TextResource.localize("KAL003_280"), MasterListMode.NONE);
		SheetData sheetAgree36Data = new SheetData(getMasterDatasAgree36(query), getHeaderColumnsAgree36(query),
				null, null, TextResource.localize("KAL003_312"), MasterListMode.NONE);
		
		SheetData sheetAnnualHoliday = new SheetData(getMasterDatasAnnualHoliday(query), getHeaderColumnsAnnualHoliday(query),
				null, null, TextResource.localize("KAL003_316"));
		
		sheetDatas.add(sheetDailyData);
		sheetDatas.add(sheetMonthData);
		sheetDatas.add(sheetMulMonthData);
		sheetDatas.add(sheetAnnualHoliday);
		sheetDatas.add(sheetAgree36Data);
		
		return sheetDatas;
	}

	@Override
	public String mainSheetName() {
		return TextResource.localize("KAL003_241");
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_212, TextResource.localize("KAL003_225"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_213, TextResource.localize("KAL003_226"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_214, TextResource.localize("KAL003_227"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_215, TextResource.localize("KAL003_228"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_216, TextResource.localize("KAL003_229"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_217, TextResource.localize("KAL003_230"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_218, TextResource.localize("KAL003_231"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_219, TextResource.localize("KAL003_232"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_220, TextResource.localize("KAL003_233"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_221, TextResource.localize("KAL003_234"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_222, TextResource.localize("KAL003_235"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_223, TextResource.localize("KAL003_236"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<Schedule4WeekReportData> schedule4WeekReportDatas = alarmCheckConditionReportRepository
				.getSchedule4WeekConditions(companyId);
		return buildSchedule4WeekMasterData(schedule4WeekReportDatas);
	}

	private List<MasterData> buildSchedule4WeekMasterData(List<Schedule4WeekReportData> schedule4WeekReportDatas) {
		List<MasterData> datas = new ArrayList<>();
		schedule4WeekReportDatas.stream().sorted(Comparator.comparing(Schedule4WeekReportData::getCode))
				.forEachOrdered(x -> {
					datas.add(buildSchedule4WeekRow(x));
				});
		return datas;
	}

	private MasterData buildSchedule4WeekRow(Schedule4WeekReportData row) {
		Map<String, MasterCellData> data = new HashMap<>();
		data.put(AlarmCheckConditionUtils.KAL003_212,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_212).value(row.getCode())
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_213,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_213).value(row.getName())
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_214,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_214)
						.value(TextResource.localize("Enum_AlarmCategory_SCHEDULE_4WEEK"))
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_215,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_215).value(row.getFilterEmp())
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_216,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_216).value(row.getEmployees())
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_217,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_217).value(row.getFilterClas())
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_218,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_218).value(row.getClassifications())
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_219,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_219).value(row.getFilterJobTitles())
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_220,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_220).value(row.getJobtitles())
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_221,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_221).value(row.getFilterWorkType())
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_222,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_222).value(row.getWorktypeselections())
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		
		if (row.getW4k4CheckCond() == 0) {
			data.put(AlarmCheckConditionUtils.KAL003_223,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_223)
							.value(TextResource.localize("KAL003_240"))
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}
		else {
			data.put(AlarmCheckConditionUtils.KAL003_223,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_223)
							.value("")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}

		return MasterData.builder().rowData(data).build();
	}

	// daily
	public List<MasterHeaderColumn> getHeaderColumnsDaily(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_212, TextResource.localize("KAL003_225"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_213, TextResource.localize("KAL003_226"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_214, TextResource.localize("KAL003_227"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_215, TextResource.localize("KAL003_228"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_216, TextResource.localize("KAL003_229"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_217, TextResource.localize("KAL003_230"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_218, TextResource.localize("KAL003_231"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_219, TextResource.localize("KAL003_232"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_220, TextResource.localize("KAL003_233"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_221, TextResource.localize("KAL003_234"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_222, TextResource.localize("KAL003_235"),
				ColumnTextAlign.LEFT, "", true));

		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_229, TextResource.localize("KAL003_242"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_230, TextResource.localize("KAL003_243"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_231, TextResource.localize("KAL003_244"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_232, TextResource.localize("KAL003_245"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_233, TextResource.localize("KAL003_246"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_234, TextResource.localize("KAL003_247"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_235, TextResource.localize("KAL003_248"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_236, TextResource.localize("KAL003_249"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_237, TextResource.localize("KAL003_250"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_238, TextResource.localize("KAL003_251"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_239, TextResource.localize("KAL003_252"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_240, TextResource.localize("KAL003_253"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_241, TextResource.localize("KAL003_253"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_242, TextResource.localize("KAL003_253"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_243, TextResource.localize("KAL003_254"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_244, TextResource.localize("KAL003_259"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_245, TextResource.localize("KAL003_253"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_246, TextResource.localize("KAL003_253"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_247, TextResource.localize("KAL003_253"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_248, TextResource.localize("KAL003_255"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_249, TextResource.localize("KAL003_256"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_250, TextResource.localize("KAL003_257"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	public List<MasterData> getMasterDatasDaily(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<DailyReportData> dailyReportDatas = alarmCheckConditionReportRepository.getDailyConditions(companyId);
		return buildDailyMasterData(dailyReportDatas);
	}

	private List<MasterData> buildDailyMasterData(List<DailyReportData> dailyReportDatas) {
		List<MasterData> datas = new ArrayList<>();
		if (!CollectionUtil.isEmpty(dailyReportDatas)) {
			Map<Integer, AttendanceNameDivergenceDto> attendanceNameDivergenceDtos = divergenceItemSetFinder
					.getAtName(AlarmCheckConditionUtils.getAttendanceIds(dailyReportDatas)).stream()
					.collect(Collectors.toMap(AttendanceNameDivergenceDto::getAttendanceItemId, Function.identity()));

			dailyReportDatas.stream().collect(Collectors.groupingBy(DailyReportData::getCode)).entrySet().stream()
					.sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> {
						List<DailyReportData> listRowPerCode = x.getValue();
						List<DailyReportData> listRowUsePerCode = listRowPerCode.stream()
								.filter(y -> y.getUseAtrCond().isPresent() && y.getUseAtrCond().get() == 1)
								.collect(Collectors.toList());
						if (!CollectionUtil.isEmpty(listRowUsePerCode)) {
							AtomicInteger index = new AtomicInteger(0);
							listRowUsePerCode.stream()
									.forEachOrdered(row -> {
										datas.add(buildDailyReportData(row, index.get(), attendanceNameDivergenceDtos));
										index.getAndIncrement();
									});

						} else {
							datas.add(buildDailyReportData(listRowPerCode.get(0), 0, attendanceNameDivergenceDtos));
						}
					});
		}
		return datas;
	}

	private MasterData buildDailyReportData(DailyReportData row, int rowIndex,
			Map<Integer, AttendanceNameDivergenceDto> attendanceNameDivergenceDtos) {
		Map<String, MasterCellData> data = new HashMap<>();
		putEmptyDaily(data);
		if (rowIndex == 0) {
			data.put(AlarmCheckConditionUtils.KAL003_212,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_212).value(row.getCode())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_213,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_213).value(row.getName())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_214,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_214)
							.value(TextResource.localize("Enum_AlarmCategory_DAILY"))
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_215,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_215).value(row.getFilterEmp())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_216,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_216)
							.value(row.getEmployees().isPresent() ? row.getEmployees().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_217,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_217).value(row.getFilterClas())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_218,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_218)
							.value(row.getClassifications().isPresent() ? row.getClassifications().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_219,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_219)
							.value(row.getFilterJobTitles())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_220,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_220)
							.value(row.getJobtitles().isPresent() ? row.getJobtitles().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_221,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_221)
							.value(row.getFilterWorkType())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_222,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_222)
							.value(row.getWorktypeselections().isPresent() ? row.getWorktypeselections().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			// 12: 日別実績のエラーアラーム
			data.put(AlarmCheckConditionUtils.KAL003_229,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_229)
							.value(row.getDailyErrorAlarms().isPresent() ? row.getDailyErrorAlarms().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			
			// 33: 固定チェック条件
			data.put(AlarmCheckConditionUtils.KAL003_250,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_250)
							.value(row.getFixedCheckCond().isPresent() ? row.getFixedCheckCond().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}

		if (row.getUseAtrCond().isPresent() && row.getUseAtrCond().get() == 1) {
			// 13: チェック条件 NO
			data.put(AlarmCheckConditionUtils.KAL003_230,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_230).value(rowIndex+1)
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());

			// 14: チェック条件 名称
			data.put(AlarmCheckConditionUtils.KAL003_231,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_231)
							.value(row.getNameCond().isPresent() ? row.getNameCond().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			// 15: チェック条件 チェック項目
			data.put(AlarmCheckConditionUtils.KAL003_232,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_232)
							.value(row.getCheckItem().isPresent() ? row.getCheckItem().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			// 16: チェック条件 対象とする勤務種類(TargetServiceType.java)
			data.put(AlarmCheckConditionUtils.KAL003_233,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_233)
							.value(row.getTargetServiceType().isPresent() ? row.getTargetServiceType().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			// 17: チェック条件 勤務種類
			data.put(AlarmCheckConditionUtils.KAL003_234,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_234)
							.value(row.getWorktypes().isPresent() ? row.getWorktypes().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			// 18: チェック条件 チェック条件
			data.put(AlarmCheckConditionUtils.KAL003_235, MasterCellData.builder()
					.columnId(AlarmCheckConditionUtils.KAL003_235)
					.value(row.getTargetAttendances().isPresent() ? AlarmCheckConditionUtils.getAttendanceStrFromTarget(
							row.getTargetAttendances().get(), attendanceNameDivergenceDtos, true) : "")
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			// 19: チェック条件 条件
			data.put(AlarmCheckConditionUtils.KAL003_236,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_236)
							.value(row.getCompareAtr().isPresent() ? row.getCompareAtr().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			// 20: チェック条件 値１
			data.put(AlarmCheckConditionUtils.KAL003_237,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_237)
							.value(row.getStartValue().isPresent() ? AlarmCheckConditionUtils.getValueWithConditionAtr(
									row.getStartValue().get(), row.getConditionAtr().get()) : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());

			// 21: チェック条件 値２
			data.put(AlarmCheckConditionUtils.KAL003_238,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_238)
							.value(row.getEndValue().isPresent() ? AlarmCheckConditionUtils.getValueWithConditionAtr(
									row.getEndValue().get(), row.getConditionAtr().get()) : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
			// 22: チェック条件 複合条件 グループ１
			data.put(AlarmCheckConditionUtils.KAL003_239,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_239).value(
							row.getConditionOperatorGroup1().isPresent() ? row.getConditionOperatorGroup1().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			// 23:チェック条件 複合条件 計算式
			data.put(AlarmCheckConditionUtils.KAL003_240, MasterCellData.builder()
					.columnId(AlarmCheckConditionUtils.KAL003_240)
					.value(row.getTargetAttendances1Group1().isPresent() ? AlarmCheckConditionUtils.getCondition(
							row.getTargetAttendances1Group1(), row.getCompareAtr1Group1(), row.getConditionAtr1Group1(),
							row.getStart1Group1(), row.getEnd1Group1(), row.getConditionType1Group1(),
							row.getAttendanceItem1Group1(), attendanceNameDivergenceDtos) : "")
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			// 24:チェック条件 複合条件 計算式
			data.put(AlarmCheckConditionUtils.KAL003_241, MasterCellData.builder()
					.columnId(AlarmCheckConditionUtils.KAL003_241)
					.value(row.getTargetAttendances2Group1().isPresent() ? AlarmCheckConditionUtils.getCondition(
							row.getTargetAttendances2Group1(), row.getCompareAtr2Group1(), row.getConditionAtr2Group1(),
							row.getStart2Group1(), row.getEnd2Group1(), row.getConditionType2Group1(),
							row.getAttendanceItem2Group1(), attendanceNameDivergenceDtos) : "")
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			// 25:チェック条件 複合条件 計算式
			data.put(AlarmCheckConditionUtils.KAL003_242, MasterCellData.builder()
					.columnId(AlarmCheckConditionUtils.KAL003_242)
					.value(row.getTargetAttendances3Group1().isPresent() ? AlarmCheckConditionUtils.getCondition(
							row.getTargetAttendances3Group1(), row.getCompareAtr3Group1(), row.getConditionAtr3Group1(),
							row.getStart3Group1(), row.getEnd3Group1(), row.getConditionType3Group1(),
							row.getAttendanceItem3Group1(), attendanceNameDivergenceDtos) : "")
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			// 26: チェック条件 複合条件 グループ２
			data.put(AlarmCheckConditionUtils.KAL003_243,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_243)
							.value(row.getGroup2UseAtr().isPresent() ? row.getGroup2UseAtr().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			if (row.getGroup2UseAtrInt().isPresent() && row.getGroup2UseAtrInt().get() == 1) {
				// 27: チェック条件 複合条件 グループ2
				data.put(AlarmCheckConditionUtils.KAL003_244,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_244)
								.value(row.getConditionOperatorGroup2().isPresent()
										? row.getConditionOperatorGroup2().get() : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				// 28:チェック条件 複合条件 計算式
				data.put(AlarmCheckConditionUtils.KAL003_245, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_245)
						.value(row.getTargetAttendances1Group2().isPresent()
								? AlarmCheckConditionUtils.getCondition(row.getTargetAttendances1Group2(),
										row.getCompareAtr1Group2(), row.getConditionAtr1Group2(), row.getStart1Group2(),
										row.getEnd1Group2(), row.getConditionType1Group2(),
										row.getAttendanceItem1Group2(), attendanceNameDivergenceDtos)
								: "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				// 29:チェック条件 複合条件 計算式
				data.put(AlarmCheckConditionUtils.KAL003_246, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_246)
						.value(row.getTargetAttendances2Group2().isPresent()
								? AlarmCheckConditionUtils.getCondition(row.getTargetAttendances2Group2(),
										row.getCompareAtr2Group2(), row.getConditionAtr2Group2(), row.getStart2Group2(),
										row.getEnd2Group2(), row.getConditionType2Group2(),
										row.getAttendanceItem2Group2(), attendanceNameDivergenceDtos)
								: "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				// 30:チェック条件 複合条件 計算式
				data.put(AlarmCheckConditionUtils.KAL003_247, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_247)
						.value(row.getTargetAttendances3Group2().isPresent()
								? AlarmCheckConditionUtils.getCondition(row.getTargetAttendances3Group2(),
										row.getCompareAtr3Group2(), row.getConditionAtr3Group2(), row.getStart3Group2(),
										row.getEnd3Group2(), row.getConditionType3Group2(),
										row.getAttendanceItem3Group2(), attendanceNameDivergenceDtos)
								: "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				// 31: チェック条件 複合条件 グループ1とグループ2の条件
				data.put(AlarmCheckConditionUtils.KAL003_248, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_248)
						.value(row.getOperatorBetweenGroups().isPresent() ? row.getOperatorBetweenGroups().get() : "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			}
			// 32: チェック条件 表示するメッセージ
			data.put(AlarmCheckConditionUtils.KAL003_249,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_249)
							.value(row.getMessage().isPresent() ? row.getMessage().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}

		return MasterData.builder().rowData(data).build();
	}

	private void putEmptyDaily(Map<String, MasterCellData> data) {
		data.put(AlarmCheckConditionUtils.KAL003_212,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_212).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_213,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_213).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_214,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_214).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_215,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_215).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_216,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_216).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_217,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_217).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_218,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_218).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_219,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_219).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_220,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_220).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_221,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_221).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_222,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_222).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		// 12: 日別実績のエラーアラーム
		data.put(AlarmCheckConditionUtils.KAL003_229,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_229).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		// 13: チェック条件 NO
		data.put(AlarmCheckConditionUtils.KAL003_230,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_230).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		// 14: チェック条件 名称
		data.put(AlarmCheckConditionUtils.KAL003_231,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_231).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		// 15: チェック条件 チェック項目
		data.put(AlarmCheckConditionUtils.KAL003_232,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_232).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 16: チェック条件 対象とする勤務種類(TargetServiceType.java)
		data.put(AlarmCheckConditionUtils.KAL003_233,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_233).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		// 17: チェック条件 勤務種類
		data.put(AlarmCheckConditionUtils.KAL003_234,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_234).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		// 18: チェック条件 チェック条件
		data.put(AlarmCheckConditionUtils.KAL003_235,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_235).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		// 19: チェック条件 条件
		data.put(AlarmCheckConditionUtils.KAL003_236,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_236).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 20: チェック条件 値１
		data.put(AlarmCheckConditionUtils.KAL003_237,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_237).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		// 21: チェック条件 値２
		data.put(AlarmCheckConditionUtils.KAL003_238,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_238).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 22: チェック条件 複合条件 グループ１
		data.put(AlarmCheckConditionUtils.KAL003_239,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_239).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 23:チェック条件 複合条件 計算式
		data.put(AlarmCheckConditionUtils.KAL003_240,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_240).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		// 24:チェック条件 複合条件 計算式
		data.put(AlarmCheckConditionUtils.KAL003_241,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_241).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 25:チェック条件 複合条件 計算式
		data.put(AlarmCheckConditionUtils.KAL003_242,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_242).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 26: チェック条件 複合条件 グループ２
		data.put(AlarmCheckConditionUtils.KAL003_243,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_243).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 27: チェック条件 複合条件 グループ2
		data.put(AlarmCheckConditionUtils.KAL003_244,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_244).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 28:チェック条件 複合条件 計算式
		data.put(AlarmCheckConditionUtils.KAL003_245,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_245).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 29:チェック条件 複合条件 計算式
		data.put(AlarmCheckConditionUtils.KAL003_246,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_246).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 30:チェック条件 複合条件 計算式
		data.put(AlarmCheckConditionUtils.KAL003_247,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_247).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 31: チェック条件 複合条件 グループ1とグループ2の条件
		data.put(AlarmCheckConditionUtils.KAL003_248,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_248).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 32: チェック条件 表示するメッセージ
		data.put(AlarmCheckConditionUtils.KAL003_249,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_249).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		// 33: 固定チェック条件
		data.put(AlarmCheckConditionUtils.KAL003_250,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_250).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
	}

	// multiple month
	public List<MasterHeaderColumn> getHeaderColumnsMulMonth(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_212, TextResource.localize("KAL003_225"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_213, TextResource.localize("KAL003_226"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_214, TextResource.localize("KAL003_227"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_215, TextResource.localize("KAL003_228"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_216, TextResource.localize("KAL003_229"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_217, TextResource.localize("KAL003_230"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_218, TextResource.localize("KAL003_231"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_219, TextResource.localize("KAL003_232"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_220, TextResource.localize("KAL003_233"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_221, TextResource.localize("KAL003_234"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_222, TextResource.localize("KAL003_235"),
				ColumnTextAlign.LEFT, "", true));

		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_251, TextResource.localize("KAL003_260"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_252, TextResource.localize("KAL003_261"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_253, TextResource.localize("KAL003_245"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_254, TextResource.localize("KAL003_315"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_255, TextResource.localize("KAL003_249"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_256, TextResource.localize("KAL003_250"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_257, TextResource.localize("KAL003_251"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_258, TextResource.localize("KAL003_276"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_259, TextResource.localize("KAL003_277"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_260, TextResource.localize("KAL003_278"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_261, TextResource.localize("KAL003_279"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	public List<MasterData> getMasterDatasMulMonth(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MulMonthReportData> mulMonthReportDatas = alarmCheckConditionReportRepository
				.getMulMonthConditions(companyId);
		return buildMulMonthMasterData(mulMonthReportDatas);
	}

	private List<MasterData> buildMulMonthMasterData(List<MulMonthReportData> mulMonthReportDatas) {
		List<MasterData> datas = new ArrayList<>();
		if (!CollectionUtil.isEmpty(mulMonthReportDatas)) {
			Map<Integer, AttendanceNameDivergenceDto> attendanceNameDivergenceDtos = divergenceItemSetFinder
					.getMonthlyAtName(AlarmCheckConditionUtils.getAttendanceIdsMulMonth(mulMonthReportDatas)).stream()
					.collect(Collectors.toMap(AttendanceNameDivergenceDto::getAttendanceItemId, Function.identity()));

			mulMonthReportDatas.stream().collect(Collectors.groupingBy(MulMonthReportData::getCode)).entrySet().stream()
					.sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> {
						List<MulMonthReportData> listRowPerCode = x.getValue();
						List<MulMonthReportData> listRowUsePerCode = listRowPerCode.stream()
								.filter(y -> y.getUseAtrCond().isPresent() && y.getUseAtrCond().get() == 1
										|| y.getUseAtrAvg().isPresent() && y.getUseAtrAvg().get() == 1
										|| y.getUseAtrCont().isPresent() && y.getUseAtrCont().get() == 1
										|| y.getUseAtrCosp().isPresent() && y.getUseAtrCosp().get() == 1)
								.collect(Collectors.toList());
						if (!CollectionUtil.isEmpty(listRowUsePerCode)) {
							AtomicInteger index = new AtomicInteger(0);
							listRowUsePerCode.stream()
									.sorted(Comparator.nullsLast(Comparator.comparing(MulMonthReportData::getInsDate)))
									.forEachOrdered(row -> {
										datas.add(buildMulMonthReportData(row, index.get(),
												attendanceNameDivergenceDtos));
										index.getAndIncrement();
									});

						} else {
							datas.add(buildMulMonthReportData(listRowPerCode.get(0), 0, attendanceNameDivergenceDtos));
						}
					});
		}
		return datas;
	}

	private MasterData buildMulMonthReportData(MulMonthReportData row, int rowIndex,
			Map<Integer, AttendanceNameDivergenceDto> attendanceNameDivergenceDtos) {
		Map<String, MasterCellData> data = new HashMap<>();
		putEmptyMulMonth(data);
		if (rowIndex == 0) {
			data.put(AlarmCheckConditionUtils.KAL003_212,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_212).value(row.getCode())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_213,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_213).value(row.getName())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_214,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_214)
							.value(TextResource.localize("Enum_AlarmCategory_MULTIPLE_MONTH"))
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_215,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_215).value(row.getFilterEmp())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_216,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_216)
							.value(row.getEmployees().isPresent() ? row.getEmployees().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_217,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_217).value(row.getFilterClas())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_218,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_218)
							.value(row.getClassifications().isPresent() ? row.getClassifications().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_219,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_219)
							.value(row.getFilterJobTitles())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_220,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_220)
							.value(row.getJobtitles().isPresent() ? row.getJobtitles().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_221,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_221)
							.value(row.getFilterWorkType())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_222,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_222)
							.value(row.getWorktypeselections().isPresent() ? row.getWorktypeselections().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}

		if (row.getUseAtrCond().isPresent() && row.getUseAtrCond().get() == 1
				|| row.getUseAtrAvg().isPresent() && row.getUseAtrAvg().get() == 1
				|| row.getUseAtrCont().isPresent() && row.getUseAtrCont().get() == 1
				|| row.getUseAtrCosp().isPresent() && row.getUseAtrCosp().get() == 1) {

			// 12: アラームリストのチェック条件 NO
			data.put(AlarmCheckConditionUtils.KAL003_251,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_251).value(rowIndex+1)
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());

			// 13: アラームリストのチェック条件 名称
			data.put(AlarmCheckConditionUtils.KAL003_252,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_252)
							.value(row.getNameCond().isPresent() ? row.getNameCond().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			// 14: チェック条件 チェック項目
			data.put(AlarmCheckConditionUtils.KAL003_253,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_253)
							.value(row.getCheckItem().isPresent() ? row.getCheckItem().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			// 15: チェック条件 勤務種類
			data.put(AlarmCheckConditionUtils.KAL003_254, MasterCellData.builder()
					.columnId(AlarmCheckConditionUtils.KAL003_254)
					.value(row.getTargetAttendances().isPresent() ? AlarmCheckConditionUtils.getAttendanceStrFromTarget(
							row.getTargetAttendances().get(), attendanceNameDivergenceDtos, true) : "")
					.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			if (row.getUseAtrCond().isPresent() && row.getUseAtrCond().get() == 1) {
				// 16: チェック条件 条件
				data.put(AlarmCheckConditionUtils.KAL003_255,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_255)
								.value(row.getCompareAtrCond().isPresent() ? row.getCompareAtrCond().get() : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

				// 17: チェック条件 値1
				data.put(AlarmCheckConditionUtils.KAL003_256, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_256)
						.value(row.getStartValueCond().isPresent() ? AlarmCheckConditionUtils.getValueWithConditionAtr(
								row.getStartValueCond().get(), row.getConditionAtrCond().get()) : "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
				
				if (row.getCompareAtrCondInt().isPresent() && 
						row.getCompareAtrCondInt().get() > SingleValueCompareType.GREATER_THAN.value) {
					// 18: チェック条件 値2
					data.put(AlarmCheckConditionUtils.KAL003_257, MasterCellData.builder()
							.columnId(AlarmCheckConditionUtils.KAL003_257)
							.value(row.getEndValueCond().isPresent()
									? AlarmCheckConditionUtils.getValueWithConditionAtr(row.getEndValueCond().get(),
											row.getConditionAtrCond().get())
									: "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
				}
			}

			if (row.getUseAtrAvg().isPresent() && row.getUseAtrAvg().get() == 1) {
				// 16: チェック条件 条件
				data.put(AlarmCheckConditionUtils.KAL003_255,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_255)
								.value(row.getCompareAtrAvg().isPresent() ? row.getCompareAtrAvg().get() : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

				// 17: チェック条件 値1
				data.put(AlarmCheckConditionUtils.KAL003_256, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_256)
						.value(row.getStartValueAvg().isPresent() ? AlarmCheckConditionUtils.getValueWithConditionAtr(
								row.getStartValueAvg().get(), row.getConditionAtrAvg().get()) : "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());

				if (row.getCompareAtrAvgInt().isPresent() && 
						row.getCompareAtrAvgInt().get() > SingleValueCompareType.GREATER_THAN.value) {
					// 18: チェック条件 値2
					data.put(AlarmCheckConditionUtils.KAL003_257, MasterCellData.builder()
							.columnId(AlarmCheckConditionUtils.KAL003_257)
							.value(row.getEndValueAvg().isPresent() ? AlarmCheckConditionUtils.getValueWithConditionAtr(
									row.getEndValueAvg().get(), row.getConditionAtrAvg().get()) : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
				}
			}

			if (row.getUseAtrCont().isPresent() && row.getUseAtrCont().get() == 1) {
				// 16: チェック条件 条件
				data.put(AlarmCheckConditionUtils.KAL003_255,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_255)
								.value(row.getCompareAtrCont().isPresent() ? row.getCompareAtrCont().get() : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

				// 17: チェック条件 値1
				data.put(AlarmCheckConditionUtils.KAL003_256, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_256)
						.value(row.getStartValueCont().isPresent() ? AlarmCheckConditionUtils.getValueWithConditionAtr(
								row.getStartValueCont().get(), row.getConditionAtrCont().get()) : "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());

				if (row.getCompareAtrContInt().isPresent() && 
						row.getCompareAtrContInt().get() > SingleValueCompareType.GREATER_THAN.value) {
					// 18: チェック条件 値2
					data.put(AlarmCheckConditionUtils.KAL003_257, MasterCellData.builder()
							.columnId(AlarmCheckConditionUtils.KAL003_257)
							.value(row.getEndValueCont().isPresent() ? AlarmCheckConditionUtils.getValueWithConditionAtr(
									row.getEndValueCont().get(), row.getConditionAtrCont().get()) : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
				}

				// 21: チェック条件 連続期間
				data.put(AlarmCheckConditionUtils.KAL003_260,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_257)
								.value(row.getContinueMonth().isPresent()
										? row.getContinueMonth().get() + TextResource.localize("KAL003_297") : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
			}

			if (row.getUseAtrCosp().isPresent() && row.getUseAtrCosp().get() == 1) {
				// 16: チェック条件 条件
				data.put(AlarmCheckConditionUtils.KAL003_255,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_255)
								.value(row.getCompareAtrCosp().isPresent() ? row.getCompareAtrCosp().get() : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

				// 17: チェック条件 値1
				data.put(AlarmCheckConditionUtils.KAL003_256, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_256)
						.value(row.getStartValueCosp().isPresent() ? AlarmCheckConditionUtils.getValueWithConditionAtr(
								row.getStartValueCosp().get(), row.getConditionAtrCosp().get()) : "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());

				if (row.getCompareAtrCospInt().isPresent() && 
						row.getCompareAtrCospInt().get() > SingleValueCompareType.GREATER_THAN.value) {
					// 18: チェック条件 値2
					data.put(AlarmCheckConditionUtils.KAL003_257, MasterCellData.builder()
							.columnId(AlarmCheckConditionUtils.KAL003_257)
							.value(row.getEndValueCosp().isPresent() ? AlarmCheckConditionUtils.getValueWithConditionAtr(
									row.getEndValueCosp().get(), row.getConditionAtrCosp().get()) : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
				}

				// 19: チェック条件 計算式 条件
				data.put(AlarmCheckConditionUtils.KAL003_258, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_258)
						.value(row.getConditionCalculateCosp().isPresent() ? row.getConditionCalculateCosp().get() : "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

				// 20: チェック条件 計算式 回数
				data.put(AlarmCheckConditionUtils.KAL003_259,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_259)
								.value(row.getTimes().isPresent() ? row.getTimes().get() + TextResource.localize("KAL003_311") : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
			}
			// 22: チェック条件 表示するメッセージ
			data.put(AlarmCheckConditionUtils.KAL003_261,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_261)
							.value(row.getMessage().isPresent() ? row.getMessage().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}

		return MasterData.builder().rowData(data).build();
	}

	private void putEmptyMulMonth(Map<String, MasterCellData> data) {
		data.put(AlarmCheckConditionUtils.KAL003_212,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_212).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_213,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_213).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_214,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_214).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_215,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_215).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_216,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_216).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_217,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_217).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_218,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_218).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_219,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_219).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_220,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_220).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_221,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_221).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_222,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_222).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		data.put(AlarmCheckConditionUtils.KAL003_250,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_250).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_251,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_251).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_252,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_252).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_253,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_253).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_254,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_254).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_255,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_255).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_256,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_256).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_257,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_257).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_258,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_258).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_259,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_259).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_260,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_260).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_261,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_261).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
	}

	// Month
	public List<MasterHeaderColumn> getHeaderColumnsMonth(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_212, TextResource.localize("KAL003_225"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_213, TextResource.localize("KAL003_226"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_214, TextResource.localize("KAL003_227"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_215, TextResource.localize("KAL003_228"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_216, TextResource.localize("KAL003_229"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_217, TextResource.localize("KAL003_230"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_218, TextResource.localize("KAL003_231"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_219, TextResource.localize("KAL003_232"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_220, TextResource.localize("KAL003_233"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_221, TextResource.localize("KAL003_234"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_222, TextResource.localize("KAL003_235"),
				ColumnTextAlign.LEFT, "", true));

		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_262, TextResource.localize("KAL003_257"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_263, TextResource.localize("KAL003_260"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_264, TextResource.localize("KAL003_261"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_265, TextResource.localize("KAL003_262"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_266, TextResource.localize("KAL003_263"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_267, TextResource.localize("KAL003_264"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_268, TextResource.localize("KAL003_265"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_269, TextResource.localize("KAL003_266"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_270, TextResource.localize("KAL003_267"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_271, TextResource.localize("KAL003_268"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_272, TextResource.localize("KAL003_269"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_273, TextResource.localize("KAL003_270"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_274, TextResource.localize("KAL003_270"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_275, TextResource.localize("KAL003_270"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_276, TextResource.localize("KAL003_271"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_277, TextResource.localize("KAL003_272"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_278, TextResource.localize("KAL003_270"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_279, TextResource.localize("KAL003_270"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_280, TextResource.localize("KAL003_270"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_281, TextResource.localize("KAL003_273"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_282, TextResource.localize("KAL003_274"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	public List<MasterData> getMasterDatasMonth(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MonthReportData> monthReportDatas = alarmCheckConditionReportRepository.getMonthConditions(companyId);
		return buildMonthMasterData(monthReportDatas);
	}

	private List<MasterData> buildMonthMasterData(List<MonthReportData> monthReportDatas) {
		List<MasterData> datas = new ArrayList<>();
		if (!CollectionUtil.isEmpty(monthReportDatas)) {
			Map<Integer, AttendanceNameDivergenceDto> attendanceNameDivergenceDtos = divergenceItemSetFinder
					.getMonthlyAtName(AlarmCheckConditionUtils.getAttendanceIdsMonth(monthReportDatas)).stream()
					.collect(Collectors.toMap(AttendanceNameDivergenceDto::getAttendanceItemId, Function.identity()));

			monthReportDatas.stream().collect(Collectors.groupingBy(MonthReportData::getCode)).entrySet().stream()
					.sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> {
						List<MonthReportData> listRowPerCode = x.getValue();
						List<MonthReportData> listRowUsePerCode = listRowPerCode.stream()
								.filter(y -> y.getUseAtrCond().isPresent() && y.getUseAtrCond().get() == 1).collect(Collectors.toList());
						if (!CollectionUtil.isEmpty(listRowUsePerCode)) {
							AtomicInteger index = new AtomicInteger(0);
							listRowUsePerCode.stream()
									.sorted(Comparator.nullsLast(Comparator.comparing(a -> a.getSortBy().get())))
									.forEachOrdered(row -> {
										datas.add(buildMonthReportData(row, index.get(), attendanceNameDivergenceDtos));
										index.getAndIncrement();
									});

						} else {
							datas.add(buildMonthReportData(listRowPerCode.get(0), 0, attendanceNameDivergenceDtos));
						}
					});
		}
		return datas;
	}

	private MasterData buildMonthReportData(MonthReportData row, int rowIndex,
			Map<Integer, AttendanceNameDivergenceDto> attendanceNameDivergenceDtos) {
		Map<String, MasterCellData> data = new HashMap<>();
		putEmptyMonth(data);
		if (rowIndex == 0) {
			data.put(AlarmCheckConditionUtils.KAL003_212,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_212).value(row.getCode())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_213,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_213).value(row.getName())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_214,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_214)
							.value(TextResource.localize("Enum_AlarmCategory_MONTHLY"))
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_215,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_215).value(row.getFilterEmp())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_216,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_216)
							.value(row.getEmployees().isPresent() ? row.getEmployees().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_217,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_217).value(row.getFilterClas())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_218,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_218)
							.value(row.getClassifications().isPresent() ? row.getClassifications().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_219,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_219)
							.value(row.getFilterJobTitles())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_220,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_220)
							.value(row.getJobtitles().isPresent() ? row.getJobtitles().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_221,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_221)
							.value(row.getFilterWorkType())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_222,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_222)
							.value(row.getWorktypeselections().isPresent() ? row.getWorktypeselections().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			// 12: 固定チェック条件
			data.put(AlarmCheckConditionUtils.KAL003_262,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_262)
							.value(row.getFixedExtraMonth().isPresent() ? row.getFixedExtraMonth().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}

		if (row.getUseAtrCond().isPresent() && row.getUseAtrCond().get() == 1) {
			// 13: アラームリストのチェック条件 NO
			data.put(AlarmCheckConditionUtils.KAL003_263,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_262).value(row.getSortBy().get())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());

			// 14: アラームリストのチェック条件 名称
			data.put(AlarmCheckConditionUtils.KAL003_264,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_264)
							.value(row.getNameCond().isPresent() ? row.getNameCond().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			// 15: アラームリストのチェック条件 チェック項目
			data.put(AlarmCheckConditionUtils.KAL003_265,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_265)
							.value(row.getCheckItem().isPresent() ? row.getCheckItem().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			Optional<Integer> checkItem = row.getCheckItemInt();

			if (checkItem.isPresent() && checkItem.get() == TypeMonCheckItem.CERTAIN_DAY_OFF.value) {
				// HOLIDAY_COMPARE_OPERATOR_19, NUMBER_DAY_DIFF_HOLIDAY_1_20,
				// NUMBER_DAY_DIFF_HOLIDAY_2_21
				// 19: アラームリストのチェック条件 条件
				data.put(AlarmCheckConditionUtils.KAL003_269, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_269)
						.value(row.getCompareOperatorHoliday().isPresent() ? row.getCompareOperatorHoliday().get() : "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				// 20: アラームリストのチェック条件 値１
				data.put(AlarmCheckConditionUtils.KAL003_270,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_270)
								.value(row.getNumdayHoliday1().isPresent() ? row.getNumdayHoliday1().get() + TextResource.localize("KAL003_314") : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());

				if (row.getCompareOperatorInt().isPresent() 
						&& row.getCompareOperatorInt().get() > SingleValueCompareType.GREATER_THAN.value) {
					// 21: アラームリストのチェック条件 値２
					data.put(AlarmCheckConditionUtils.KAL003_271,
							MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_271)
									.value(row.getNumdayHoliday2().isPresent() ? row.getNumdayHoliday2().get() + TextResource.localize("KAL003_314") : "")
									.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
				}
			}

			if (checkItem.isPresent() && checkItem.get() == TypeMonCheckItem.CHECK_REMAIN_NUMBER.value) {
				/* 残数チェック */
				// VACATION_COMPARE_ATR_19, VACATION_START_VALUE_20,
				// VACATION_END_VALUE_21
				// 16: アラームリストのチェック条件 休暇種類(TypeCheckVacation.java)
				data.put(AlarmCheckConditionUtils.KAL003_266,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_266)
								.value(row.getTypeCheckVacation().isPresent() ? row.getTypeCheckVacation().get() : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				if (row.getTypeCheckVacationInt().isPresent() 
						&& row.getTypeCheckVacationInt().get() == TypeCheckVacation.SPECIAL_HOLIDAY.value) {
					// 17: アラームリストのチェック条件 条件
					data.put(AlarmCheckConditionUtils.KAL003_267,
							MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_267)
									.value(row.getVacationItems().isPresent() ? row.getVacationItems().get() : "")
									.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				}

				// 19: アラームリストのチェック条件 条件
				data.put(AlarmCheckConditionUtils.KAL003_269,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_269)
								.value(row.getVacationCompareAtr().isPresent() ? row.getVacationCompareAtr().get() : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				// 20: アラームリストのチェック条件 値１
				data.put(AlarmCheckConditionUtils.KAL003_270,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_270)
								.value(row.getVacationStartValue().isPresent() ? row.getVacationStartValue().get() + TextResource.localize("KAL003_314") : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());

				if (row.getVacationCompareAtrInt().isPresent() 
						&& row.getVacationCompareAtrInt().get() > SingleValueCompareType.GREATER_THAN.value) {
					// 21: アラームリストのチェック条件 値２
					data.put(AlarmCheckConditionUtils.KAL003_271,
							MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_271)
									.value(row.getVacationEndValue().isPresent() ? row.getVacationEndValue().get() + TextResource.localize("KAL003_314") : "")
									.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
				}

			}

			if (row.getCheckItemInt().isPresent()
					&& row.getCheckItemInt().get() != TypeMonCheckItem.CERTAIN_DAY_OFF.value
					&& row.getCheckItemInt().get() != TypeMonCheckItem.CHECK_REMAIN_NUMBER.value) {
				// 18: アラームリストのチェック条件 チェック条件
				data.put(AlarmCheckConditionUtils.KAL003_268,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_268)
								.value(row.getTargetAttendances().isPresent()
										? AlarmCheckConditionUtils.getAttendanceStrFromTarget(
												row.getTargetAttendances().get(), attendanceNameDivergenceDtos, true)
										: "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

				// 19: アラームリストのチェック条件 条件
				data.put(AlarmCheckConditionUtils.KAL003_269,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_269)
								.value(row.getCompareAtr().isPresent() ? row.getCompareAtr().get() : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				// 20: アラームリストのチェック条件 値１
				data.put(AlarmCheckConditionUtils.KAL003_270, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_270)
						.value(row.getStartValue().isPresent() ? AlarmCheckConditionUtils
								.getValueWithConditionAtrMonth(row.getStartValue().get(), row.getCheckItemInt().get()) : "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());

				if (row.getCompareAtrInt().isPresent() && row.getCompareAtrInt().get() > SingleValueCompareType.GREATER_THAN.value) {
					// 21: アラームリストのチェック条件 値２
					data.put(AlarmCheckConditionUtils.KAL003_271, MasterCellData.builder()
							.columnId(AlarmCheckConditionUtils.KAL003_271)
							.value(row.getEndValue().isPresent() ? AlarmCheckConditionUtils
									.getValueWithConditionAtrMonth(row.getEndValue().get(), row.getCheckItemInt().get()) : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
				}
				
				// 22: アラームリストのチェック条件 複合条件 グループ１
				data.put(AlarmCheckConditionUtils.KAL003_272,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_272)
								.value(row.getConditionOperatorGroup1().isPresent()
										? row.getConditionOperatorGroup1().get() : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				// 23:アラームリストのチェック条件 複合条件 計算式
				data.put(AlarmCheckConditionUtils.KAL003_273, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_273)
						.value(row.getTargetAttendances1Group1().isPresent()
								? AlarmCheckConditionUtils.getCondition(row.getTargetAttendances1Group1(),
										row.getCompareAtr1Group1(), row.getConditionAtr1Group1(), row.getStart1Group1(),
										row.getEnd1Group1(), row.getConditionType1Group1(),
										row.getAttendanceItem1Group1(), attendanceNameDivergenceDtos)
								: "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				// 24:アラームリストのチェック条件 複合条件 計算式
				data.put(AlarmCheckConditionUtils.KAL003_274, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_274)
						.value(row.getTargetAttendances2Group1().isPresent()
								? AlarmCheckConditionUtils.getCondition(row.getTargetAttendances2Group1(),
										row.getCompareAtr2Group1(), row.getConditionAtr2Group1(), row.getStart2Group1(),
										row.getEnd2Group1(), row.getConditionType2Group1(),
										row.getAttendanceItem2Group1(), attendanceNameDivergenceDtos)
								: "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				// 25:アラームリストのチェック条件 複合条件 計算式
				data.put(AlarmCheckConditionUtils.KAL003_275, MasterCellData.builder()
						.columnId(AlarmCheckConditionUtils.KAL003_275)
						.value(row.getTargetAttendances3Group1().isPresent()
								? AlarmCheckConditionUtils.getCondition(row.getTargetAttendances3Group1(),
										row.getCompareAtr3Group1(), row.getConditionAtr3Group1(), row.getStart3Group1(),
										row.getEnd3Group1(), row.getConditionType3Group1(),
										row.getAttendanceItem3Group1(), attendanceNameDivergenceDtos)
								: "")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				// 26: アラームリストのチェック条件 複合条件 グループ２
				data.put(AlarmCheckConditionUtils.KAL003_276,
						MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_276)
								.value(row.getGroup2UseAtr().isPresent() ? row.getGroup2UseAtr().get() : "")
								.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

				if (row.getGroup2UseAtrInt().isPresent() && row.getGroup2UseAtrInt().get() == 1) {
					// 27: アラームリストのチェック条件 複合条件 グループ２
					data.put(AlarmCheckConditionUtils.KAL003_277,
							MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_277)
									.value(row.getConditionOperatorGroup2().isPresent()
											? row.getConditionOperatorGroup2().get() : "")
									.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
					// 28:アラームリストのチェック条件 複合条件 計算式
					data.put(AlarmCheckConditionUtils.KAL003_278, MasterCellData.builder()
							.columnId(AlarmCheckConditionUtils.KAL003_278)
							.value(row.getTargetAttendances1Group2().isPresent()
									? AlarmCheckConditionUtils.getCondition(row.getTargetAttendances1Group2(),
											row.getCompareAtr1Group2(), row.getConditionAtr1Group2(),
											row.getStart1Group2(), row.getEnd1Group2(), row.getConditionType1Group2(),
											row.getAttendanceItem1Group2(), attendanceNameDivergenceDtos)
									: "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
					// 29:アラームリストのチェック条件 複合条件 計算式
					data.put(AlarmCheckConditionUtils.KAL003_279, MasterCellData.builder()
							.columnId(AlarmCheckConditionUtils.KAL003_279)
							.value(row.getTargetAttendances2Group2().isPresent()
									? AlarmCheckConditionUtils.getCondition(row.getTargetAttendances2Group2(),
											row.getCompareAtr2Group2(), row.getConditionAtr2Group2(),
											row.getStart2Group2(), row.getEnd2Group2(), row.getConditionType2Group2(),
											row.getAttendanceItem2Group2(), attendanceNameDivergenceDtos)
									: "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
					// 30:アラームリストのチェック条件 複合条件 計算式
					data.put(AlarmCheckConditionUtils.KAL003_280, MasterCellData.builder()
							.columnId(AlarmCheckConditionUtils.KAL003_280)
							.value(row.getTargetAttendances3Group2().isPresent()
									? AlarmCheckConditionUtils.getCondition(row.getTargetAttendances3Group2(),
											row.getCompareAtr3Group2(), row.getConditionAtr3Group2(),
											row.getStart3Group2(), row.getEnd3Group2(), row.getConditionType3Group2(),
											row.getAttendanceItem3Group2(), attendanceNameDivergenceDtos)
									: "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
					// 31: アラームリストのチェック条件 複合条件 グループ1とグループ2の条件
					data.put(AlarmCheckConditionUtils.KAL003_281,
							MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_281)
									.value(row.getOperatorBetweenGroups().isPresent()
											? row.getOperatorBetweenGroups().get() : "")
									.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
				}
			}
			// 32: アラームリストのチェック条件 表示するメッセージ
			data.put(AlarmCheckConditionUtils.KAL003_282,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_282)
							.value(row.getMessage().isPresent() ? row.getMessage().get() : "")
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}

		return MasterData.builder().rowData(data).build();
	}

	private void putEmptyMonth(Map<String, MasterCellData> data) {
		data.put(AlarmCheckConditionUtils.KAL003_212,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_212).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_213,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_213).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_214,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_214).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_215,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_215).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_216,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_216).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_217,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_217).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_218,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_218).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_219,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_219).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_220,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_220).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_221,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_221).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_222,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_222).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		data.put(AlarmCheckConditionUtils.KAL003_262,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_262).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		data.put(AlarmCheckConditionUtils.KAL003_263,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_263).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		data.put(AlarmCheckConditionUtils.KAL003_264,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_264).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		data.put(AlarmCheckConditionUtils.KAL003_265,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_265).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_266,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_266).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_267,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_267).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		data.put(AlarmCheckConditionUtils.KAL003_268,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_268).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		data.put(AlarmCheckConditionUtils.KAL003_269,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_269).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_270,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_270).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		data.put(AlarmCheckConditionUtils.KAL003_271,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_271).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_272,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_272).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_273,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_273).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		data.put(AlarmCheckConditionUtils.KAL003_274,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_274).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		
		data.put(AlarmCheckConditionUtils.KAL003_275,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_275).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_276,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_276).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_277,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_277).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_278,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_278).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_279,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_279).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_280,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_280).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_281,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_281).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_282,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_282).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
	}
	
	
	//Agree 36
	public List<MasterHeaderColumn> getHeaderColumnsAgree36(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_212, TextResource.localize("KAL003_225"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_213, TextResource.localize("KAL003_226"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_214, TextResource.localize("KAL003_227"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_215, TextResource.localize("KAL003_228"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_216, TextResource.localize("KAL003_229"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_217, TextResource.localize("KAL003_230"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_218, TextResource.localize("KAL003_231"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_219, TextResource.localize("KAL003_232"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_220, TextResource.localize("KAL003_233"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_221, TextResource.localize("KAL003_234"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_222, TextResource.localize("KAL003_235"),
				ColumnTextAlign.LEFT, "", true));
		
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_283, TextResource.localize("KAL003_304"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_284, TextResource.localize("KAL003_305"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_285, TextResource.localize("KAL003_306"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_286, TextResource.localize("KAL003_307"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_287, TextResource.localize("KAL003_308"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_288, TextResource.localize("KAL003_309"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_289, TextResource.localize("KAL003_310"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	public List<MasterData> getMasterDatasAgree36(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<Agree36ReportData> agree36ReportDatas = alarmCheckConditionReportRepository
				.getAgree36Conditions(companyId);
		return buildAgree36MasterData(agree36ReportDatas);
	}

	private List<MasterData> buildAgree36MasterData(List<Agree36ReportData> agree36ReportDatas) {
		List<MasterData> datas = new ArrayList<>();
		agree36ReportDatas.stream().sorted(Comparator.comparing(Agree36ReportData::getCode))
				.forEachOrdered(x -> {					
					int max = x.getAgree36CondErrors().size() > x.getAgree36OTErrors().size() 
							? x.getAgree36CondErrors().size() : x.getAgree36OTErrors().size();
					IntStream.range(0, max).forEach(idx -> {
						datas.add(buildAgree36Row(x, idx));
					});
					
				});
		return datas;
	}

	private MasterData buildAgree36Row(Agree36ReportData row, int no) {
		Map<String, MasterCellData> data = new HashMap<>();
		putEmptyAgree36(data);
		if (no == 0) {
			data.put(AlarmCheckConditionUtils.KAL003_212,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_212).value(row.getCode())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_213,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_213).value(row.getName())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_214,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_214)
							.value(TextResource.localize("Enum_AlarmCategory_AGREEMENT"))
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_215,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_215).value(row.getFilterEmp())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_216,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_216).value(row.getEmployees())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_217,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_217).value(row.getFilterClas())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_218,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_218).value(row.getClassifications())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_219,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_219).value(row.getFilterJobTitles())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_220,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_220).value(row.getJobtitles())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_221,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_221).value(row.getFilterWorkType())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_222,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_222).value(row.getWorktypeselections())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}
		
		if (row.getAgree36CondErrors().size() > no) {
			Agree36CondError condError = row.getAgree36CondErrors().get(no);
			data.put(AlarmCheckConditionUtils.KAL003_283,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_283)
							.value(no+1)
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_284,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_284)
							.value(condError.getName())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_285,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_285)
							.value(condError.getMessage())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}
		
		if (row.getAgree36OTErrors().size() > no) {
			Agree36OTError condOTError = row.getAgree36OTErrors().get(no);
			data.put(AlarmCheckConditionUtils.KAL003_286,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_286)
							.value(condOTError.getNo())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_287,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_287)
							.value(AlarmCheckConditionUtils.getValueWithConditionAtr(condOTError.getOvertime(), 
									ConditionAtr.TIME_DURATION.value))
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_288,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_288)
							.value(condOTError.getExcessNum() + TextResource.localize("KAL003_311"))
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_289,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_289)
							.value(condOTError.getMessage())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		}
		
		return MasterData.builder().rowData(data).build();
	}
	
	
	private void putEmptyAgree36(Map<String, MasterCellData> data) {
		data.put(AlarmCheckConditionUtils.KAL003_212,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_212).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_213,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_213).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_214,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_214).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_215,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_215).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_216,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_216).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_217,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_217).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_218,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_218).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_219,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_219).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_220,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_220).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_221,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_221).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_222,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_222).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

	
		data.put(AlarmCheckConditionUtils.KAL003_283,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_283).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_284,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_284).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_285,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_285).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_286,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_286).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_287,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_287).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_288,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_288).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_289,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_289).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
	}
	
	// AnnualHoliday
	public List<MasterHeaderColumn> getHeaderColumnsAnnualHoliday(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_212, TextResource.localize("KAL003_225"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_213, TextResource.localize("KAL003_226"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_214, TextResource.localize("KAL003_227"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_215, TextResource.localize("KAL003_228"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_216, TextResource.localize("KAL003_229"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_217, TextResource.localize("KAL003_230"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_218, TextResource.localize("KAL003_231"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_219, TextResource.localize("KAL003_232"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_220, TextResource.localize("KAL003_233"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_221, TextResource.localize("KAL003_234"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_222, TextResource.localize("KAL003_235"),
				ColumnTextAlign.LEFT, "", true));

		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_317, TextResource.localize("KAL003_317"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_318, TextResource.localize("KAL003_318"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_319, TextResource.localize("KAL003_319"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_320, TextResource.localize("KAL003_320"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_321, TextResource.localize("KAL003_321"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_322, TextResource.localize("KAL003_322"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(AlarmCheckConditionUtils.KAL003_323, TextResource.localize("KAL003_323"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	public List<MasterData> getMasterDatasAnnualHoliday(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<AlarmCheckAnnualHolidayData> annualHolidayDatas = alarmCheckConditionReportRepository
				.getAnnualHoliday(companyId);
		return buildAnnualHolidayMasterData(annualHolidayDatas);
	}

	private List<MasterData> buildAnnualHolidayMasterData(List<AlarmCheckAnnualHolidayData> annualHolidayDatas) {
		List<MasterData> datas = new ArrayList<>();
		annualHolidayDatas.stream().sorted(Comparator.comparing(AlarmCheckAnnualHolidayData::getCode)).forEachOrdered(x -> {
				if(x.getAlarmCheckHdpaidObl() != null) datas.add(buildAnnualHolidayRow(x));

		});
		return datas;
	}

	private MasterData buildAnnualHolidayRow(AlarmCheckAnnualHolidayData row) {
		Map<String, MasterCellData> data = new HashMap<>();
		putEmptyAnnualHoliday(data);
			data.put(AlarmCheckConditionUtils.KAL003_212,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_212).value(row.getCode())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_213,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_213).value(row.getName())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_214,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_214)
							.value(TextResource.localize("Enum_AlarmCategory_ATTENDANCE_RATE_FOR_HOLIDAY"))
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_215,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_215).value(row.getFilterEmp())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_216,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_216).value(row.getEmployees())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_217,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_217).value(row.getFilterClas())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_218,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_218)
							.value(row.getClassifications())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_219,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_219)
							.value(row.getFilterJobTitles())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_220,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_220).value(row.getJobtitles())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_221,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_221)
							.value(row.getFilterWorkType())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_222,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_222)
							.value(row.getWorktypeselections())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

			AlarmCheckHdpaid hdPaid = row.getAlarmCheckHdpaid();
			data.put(AlarmCheckConditionUtils.KAL003_317,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_317).value(hdPaid.getNextPeriodAtr())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_318,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_318).value(hdPaid.getNextPeriod())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_319,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_319).value(hdPaid.getLastTimeDayAtr())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_320,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_320).value(hdPaid.getLastTimeDay())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());

			AlarmCheckHdpaidObl hdPaidObl = row.getAlarmCheckHdpaidObl();
			data.put(AlarmCheckConditionUtils.KAL003_321,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_321).value(hdPaidObl.getUnderLimitDay())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_322,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_322)
							.value(hdPaidObl.getDisMessage())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
			data.put(AlarmCheckConditionUtils.KAL003_323,
					MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_323)
							.value(hdPaidObl.getDivideAtr())
							.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		return MasterData.builder().rowData(data).build();
	}

	private void putEmptyAnnualHoliday(Map<String, MasterCellData> data) {
		data.put(AlarmCheckConditionUtils.KAL003_212,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_212).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_213,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_213).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_214,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_214).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_215,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_215).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_216,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_216).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_217,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_217).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_218,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_218).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_219,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_219).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_220,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_220).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_221,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_221).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_222,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_222).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());

		data.put(AlarmCheckConditionUtils.KAL003_317,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_317).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_318,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_318).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_319,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_319).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_320,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_320).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_321,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_321).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_322,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_322).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
		data.put(AlarmCheckConditionUtils.KAL003_323,
				MasterCellData.builder().columnId(AlarmCheckConditionUtils.KAL003_323).value("")
						.style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT)).build());
	}
}
