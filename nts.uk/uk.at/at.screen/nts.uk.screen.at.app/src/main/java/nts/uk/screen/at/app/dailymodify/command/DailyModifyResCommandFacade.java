package nts.uk.screen.at.app.dailymodify.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommandHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.audittrail.DPAttendanceItemRC;
import nts.uk.ctx.at.record.app.command.dailyperform.checkdata.RCDailyCorrectionResult;
import nts.uk.ctx.at.record.app.command.dailyperform.month.UpdateMonthDailyParam;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.erroralarm.dto.EmployeeDailyPerErrorDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.ContentApproval;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.ParamDayApproval;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.RegisterDayApproval;
import nts.uk.ctx.at.record.dom.daily.itemvalue.DailyItemValue;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.ParamIdentityConfirmDay;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.RegisterIdentityConfirmDay;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.SelfConfirmDay;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.EmpProvisionalInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RegisterProvisionalData;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQuery;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceCorrectionProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.checkdata.ValidatorDataDailyRes;
import nts.uk.screen.at.app.dailyperformance.correction.checkdata.dto.ItemFlex;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemCheckBox;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemParent;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataResultAfterIU;
import nts.uk.screen.at.app.dailyperformance.correction.dto.TypeError;
import nts.uk.screen.at.app.dailyperformance.correction.dto.month.DPMonthValue;
import nts.uk.screen.at.app.dailyperformance.correction.text.DPText;
import nts.uk.screen.at.app.monthlyperformance.correction.command.MonthModifyCommandFacade;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQuery;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyResult;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
@Transactional
/** 日別修正CommandFacade */
public class DailyModifyResCommandFacade {

	@Inject
	private DailyRecordWorkCommandHandler handler;

	@Inject
	private RegisterIdentityConfirmDay registerIdentityConfirmDay;

	@Inject
	private RegisterDayApproval registerDayApproval;

	@Inject
	private OptionalItemRepository optionalMasterRepo;

	@Inject
	private ValidatorDataDailyRes validatorDataDaily;

	@Inject
	private DailyPerformanceCorrectionProcessor processor;

	@Inject
	private MonthModifyCommandFacade monthModifyCommandFacade;

	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

	@Inject
	private RegisterProvisionalData registerProvisionalData;

	@Inject
	private InsertAllData insertAllData;

	public RCDailyCorrectionResult handleUpdate(List<DailyModifyQuery> querys, List<DailyRecordDto> dtoOlds,
			List<DailyRecordDto> dtoNews, List<DailyItemValue> dailyItems, UpdateMonthDailyParam month, int mode,
			boolean flagCalculation, Map<Integer, DPAttendanceItemRC> lstAttendanceItem) {
		String sid = AppContexts.user().employeeId();

		List<DailyRecordWorkCommand> commandNew = createCommands(sid, dtoNews, querys);

		List<DailyRecordWorkCommand> commandOld = createCommands(sid, dtoOlds, querys);
		if (!flagCalculation) {
			return this.handler.handleUpdateRes(commandNew, commandOld, dailyItems, month, mode);
		} else {
			List<EmployeeDailyPerErrorDto> lstErrorDto = dtoNews.stream().map(result -> result.getErrors())
					.flatMap(List::stream).collect(Collectors.toList());
			List<EmployeeDailyPerError> lstError = lstErrorDto.stream()
					.map(x -> x.toDomain(x.getEmployeeID(), x.getDate())).collect(Collectors.toList());
			this.handler.handlerNoCalc(commandNew, commandOld, lstError, dailyItems, true, month, mode,
					lstAttendanceItem);
			return null;
		}
	}

	private List<EditStateOfDailyPerformance> convertTo(String sid, DailyModifyQuery query) {
		List<EditStateOfDailyPerformance> editData = query.getItemValues().stream().map(x -> {
			return new EditStateOfDailyPerformance(query.getEmployeeId(), x.getItemId(), query.getBaseDate(),
					sid.equals(query.getEmployeeId()) ? EditStateSetting.HAND_CORRECTION_MYSELF
							: EditStateSetting.HAND_CORRECTION_OTHER);
		}).collect(Collectors.toList());
		return editData;
	}

	private void processDto(List<DailyRecordDto> dailyOlds, List<DailyRecordDto> dailyEdits, DPItemParent dataParent,
			List<DailyModifyQuery> querys, Map<Pair<String, GeneralDate>, List<DPItemValue>> mapSidDate,
			List<DailyModifyQuery> queryNotChanges) {
		if (!querys.isEmpty() && !dataParent.isFlagCalculation()) {
			dailyOlds.addAll(dataParent.getDailyOlds().stream()
					.filter(x -> mapSidDate.containsKey(Pair.of(x.getEmployeeId(), x.getDate())))
					.collect(Collectors.toList()));
			List<DailyRecordDto> temp = dataParent.getDailyEdits().stream()
					.filter(x -> mapSidDate.containsKey(Pair.of(x.getEmployeeId(), x.getDate())))
					.collect(Collectors.toList());
			dailyEdits.addAll(queryNotChanges.isEmpty() ? temp : toDto(queryNotChanges, temp));
		} else {
			dailyOlds.addAll(dataParent.getDailyOlds());
			dailyEdits.addAll(dataParent.getDailyEdits());
		}
		Map<Integer, OptionalItemAtr> optionalMaster = optionalMasterRepo.findAll(AppContexts.user().companyId())
				.stream().collect(Collectors.toMap(c -> c.getOptionalItemNo().v(), c -> c.getOptionalItemAtr()));

		dailyOlds.stream().forEach(o -> {
			o.getOptionalItem().ifPresent(optional -> {
				optional.correctItemsWith(optionalMaster);
			});
		});

		dailyEdits.stream().forEach(o -> {
			o.getOptionalItem().ifPresent(optional -> {
				optional.correctItemsWith(optionalMaster);
			});
		});
	}

	private List<DailyRecordDto> toDto(List<DailyModifyQuery> querys, List<DailyRecordDto> dtoEdits) {
		List<DailyRecordDto> dtoNews = new ArrayList<>();

		dtoNews = dtoEdits.stream().map(o -> {
			val itemChanges = querys.stream()
					.filter(q -> q.getBaseDate().equals(o.workingDate()) && q.getEmployeeId().equals(o.employeeId()))
					.findFirst();
			if (!itemChanges.isPresent())
				return o;
			List<ItemValue> itemValues = itemChanges.get().getItemValues();

			AttendanceItemUtil.fromItemValues(o, itemValues);

			o.getTimeLeaving().ifPresent(dto -> {
				if (dto.getWorkAndLeave() != null)
					dto.getWorkAndLeave().removeIf(tl -> tl.getWorking() == null && tl.getLeave() == null);
			});
			return o;
		}).collect(Collectors.toList());
		return dtoNews;
	}

	private DailyRecordWorkCommand createCommand(String sid, DailyRecordDto dto, DailyModifyQuery query) {
		if (query == null) {
			return DailyRecordWorkCommand.open().withData(dto).forEmployeeIdAndDate(dto.employeeId(), dto.getDate())
					.fromItems(Collections.emptyList());
		}
		DailyRecordWorkCommand command = DailyRecordWorkCommand.open().forEmployeeId(query.getEmployeeId())
				.withWokingDate(query.getBaseDate()).withData(dto)
				.fromItems(query == null ? Collections.emptyList() : query.getItemValues());
		command.getEditState().updateDatas(convertTo(sid, query));
		return command;
	}

	private List<DailyRecordWorkCommand> createCommands(String sid, List<DailyRecordDto> lstDto,
			List<DailyModifyQuery> querys) {
		if (querys.isEmpty())
			return lstDto.stream().map(o -> {
				return createCommand(sid, o, null);
			}).collect(Collectors.toList());

		return lstDto.stream().map(o -> {
			DailyModifyQuery query = querys.stream()
					.filter(q -> q.getBaseDate().equals(o.workingDate()) && q.getEmployeeId().equals(o.employeeId()))
					.findFirst().orElse(null);
			return createCommand(sid, o, query);
		}).collect(Collectors.toList());
	}

	public DataResultAfterIU insertItemDomain(DPItemParent dataParent) {
		Map<Integer, List<DPItemValue>> resultError = new HashMap<>();
		DataResultAfterIU dataResultAfterIU = new DataResultAfterIU();
		// insert flex
		UpdateMonthDailyParam monthParam = null;
		if (dataParent.getMonthValue() != null) {
			val month = dataParent.getMonthValue();
			if (month != null && month.getItems() != null) {
				MonthlyModifyQuery monthQuery = new MonthlyModifyQuery(month.getItems().stream().map(x -> {
					return ItemValue.builder().itemId(x.getItemId()).layout(x.getLayoutCode()).value(x.getValue())
							.valueType(ValueType.valueOf(x.getValueType())).withPath("");
				}).collect(Collectors.toList()), month.getYearMonth(), month.getEmployeeId(), month.getClosureId(),
						month.getClosureDate());
				IntegrationOfMonthly domainMonth = monthModifyCommandFacade.toDto(monthQuery).toDomain(
						month.getEmployeeId(), new YearMonth(month.getYearMonth()), month.getClosureId(),
						month.getClosureDate());
				Optional<IntegrationOfMonthly> domainMonthOpt = Optional.of(domainMonth);
				monthParam = new UpdateMonthDailyParam(month.getYearMonth(), month.getEmployeeId(),
						month.getClosureId(), month.getClosureDate(), domainMonthOpt,
						new DatePeriod(dataParent.getDateRange().getStartDate(),
								dataParent.getDateRange().getEndDate()),
						month.getRedConditionMessage(), month.getHasFlex(), month.getNeedCallCalc());
			} else {
				monthParam = new UpdateMonthDailyParam(month.getYearMonth(), month.getEmployeeId(),
						month.getClosureId(), month.getClosureDate(), Optional.empty(),
						new DatePeriod(dataParent.getDateRange().getStartDate(),
								dataParent.getDateRange().getEndDate()),
						month.getRedConditionMessage(), month.getHasFlex(), month.getNeedCallCalc());
			}
		}

		if (dataParent.getSpr() != null) {
			processor.insertStampSourceInfo(dataParent.getSpr().getEmployeeId(), dataParent.getSpr().getDate(),
					dataParent.getSpr().isChange31(), dataParent.getSpr().isChange34());
		}

		Map<Pair<String, GeneralDate>, List<DPItemValue>> mapSidDate = dataParent.getItemValues().stream()
				.collect(Collectors.groupingBy(x -> Pair.of(x.getEmployeeId(), x.getDate())));

		Map<Pair<String, GeneralDate>, List<DPItemValue>> mapSidDateNotChange = dataParent.getItemValues().stream()
				.filter(x -> !DPText.ITEM_CHANGE.contains(x.getItemId()))
				.collect(Collectors.groupingBy(x -> Pair.of(x.getEmployeeId(), x.getDate())));

		List<DailyModifyQuery> querys = createQuerys(mapSidDate);
		List<DailyModifyQuery> queryNotChanges = createQuerys(mapSidDateNotChange);
		// map to list result -> check error;
		List<DailyRecordDto> dailyOlds = new ArrayList<>(), dailyEdits = new ArrayList<>();

		processDto(dailyOlds, dailyEdits, dataParent, querys, mapSidDate, queryNotChanges);

		List<DailyModifyResult> resultOlds = AttendanceItemUtil.toItemValues(dailyOlds).entrySet().stream()
				.map(dto -> DailyModifyResult.builder().items(dto.getValue()).employeeId(dto.getKey().getEmployeeId())
						.workingDate(dto.getKey().getDate()).completed())
				.collect(Collectors.toList());

		Map<Pair<String, GeneralDate>, List<DailyModifyResult>> mapSidDateData = resultOlds.stream()
				.collect(Collectors.groupingBy(x -> Pair.of(x.getEmployeeId(), x.getDate())));

		// check error care item
		List<DPItemValue> itemErrors = new ArrayList<>();
		List<DPItemValue> itemInputErors = new ArrayList<>();
		List<DPItemValue> itemInputError28 = new ArrayList<>();
		// List<DPItemValue> itemInputDeviation = new ArrayList<>();
		if (!dataParent.isFlagCalculation()) {
			mapSidDate.entrySet().forEach(x -> {
				List<DPItemValue> itemCovert = x.getValue().stream().filter(y -> y.getValue() != null)
						.collect(Collectors.toList()).stream().filter(distinctByKey(p -> p.getItemId()))
						.collect(Collectors.toList());
				List<DailyModifyResult> itemValues = itemCovert.isEmpty() ? Collections.emptyList()
						: mapSidDateData.get(Pair.of(itemCovert.get(0).getEmployeeId(), itemCovert.get(0).getDate()));
				List<DPItemValue> items = validatorDataDaily.checkCareItemDuplicate(itemCovert);
				if (!items.isEmpty()) {
					itemErrors.addAll(items);
				} else {
					List<DPItemValue> itemInputs = validatorDataDaily.checkInputData(itemCovert, itemValues);
					itemInputErors.addAll(itemInputs);
				}

				List<DPItemValue> itemInputs28 = validatorDataDaily.checkInput28And1(itemCovert, itemValues);
				itemInputError28.addAll(itemInputs28);

			});
		}
		// insert , update item
		boolean hasError = false;
		RCDailyCorrectionResult resultIU = new RCDailyCorrectionResult();
		List<DailyItemValue> dailyItems = resultOlds.stream().map(
				x -> DailyItemValue.build().createEmpAndDate(x.getEmployeeId(), x.getDate()).createItems(x.getItems()))
				.collect(Collectors.toList());
		if (itemErrors.isEmpty() && itemInputErors.isEmpty() && itemInputError28.isEmpty()) {
			if (querys.isEmpty() && !dataParent.isFlagCalculation()
					&& (dataParent.getMonthValue() == null || dataParent.getMonthValue().getItems() == null)) {
				// only insert check box
				// insert sign
				insertSign(dataParent.getDataCheckSign());
				// insert approval
				insertApproval(dataParent.getDataCheckApproval());
			} else {
				// if (querys.isEmpty() ? !dataParent.isFlagCalculation() :
				// true) {
				Map<Integer, DPAttendanceItemRC> itemAtr = dataParent.getLstAttendanceItem().entrySet().stream()
						.collect(Collectors.toMap(x -> x.getKey(), x -> convertItemAtr(x.getValue())));
				resultIU = handleUpdate(querys, dailyOlds, dailyEdits, dailyItems, monthParam, dataParent.getMode(),
						dataParent.isFlagCalculation(), itemAtr);
				if (resultIU != null) {
					val errorDivergence = validatorDataDaily.errorCheckDivergence(resultIU.getLstDailyDomain(),
							resultIU.getLstMonthDomain());
					if (!errorDivergence.isEmpty()) {
						resultError.putAll(errorDivergence);
						hasError = true;
					}
					if (dataParent.getMode() == 0 && monthParam.getHasFlex()) {
						val flexShortageRCDto = validatorDataDaily.errorCheckFlex(resultIU.getLstMonthDomain(),
								monthParam);
						dataResultAfterIU.setFlexShortage(flexShortageRCDto);
						if (flexShortageRCDto.isError() || !flexShortageRCDto.getMessageError().isEmpty()) {
							hasError = true;
							if(!resultIU.getLstMonthDomain().isEmpty()) flexShortageRCDto.createDataCalc(convertMonthToItem(MonthlyRecordWorkDto.fromOnlyAttTime(resultIU.getLstMonthDomain().get(0)), dataParent.getMonthValue()));
						}
					}
					val errorMonth = validatorDataDaily.errorMonth(resultIU.getLstMonthDomain(), monthParam);
					if (!errorMonth.isEmpty()) {
						resultError.putAll(errorMonth);
						hasError = true;
					}

					if (!hasError) {
						this.insertAllData.handlerInsertAll(resultIU.getCommandNew(), resultIU.getLstDailyDomain(),
								resultIU.getCommandOld(), dailyItems, resultIU.getLstMonthDomain(), resultIU.isUpdate(),
								monthParam, itemAtr);
						// insert sign
						insertSign(dataParent.getDataCheckSign());
						// insert approval
						insertApproval(dataParent.getDataCheckApproval());
					}
				} else {
					if (dataParent.getDataCheckSign() != null && !dataParent.getDataCheckSign().isEmpty())
						insertSign(dataParent.getDataCheckSign());
					// insert approval
					if (dataParent.getDataCheckApproval() != null && !dataParent.getDataCheckApproval().isEmpty())
						insertApproval(dataParent.getDataCheckApproval());
				}
			}
			// 暫定データを登録する - Register provisional data
			List<DailyModifyResult> resultNews = dailyEdits.stream()
					.map(c -> DailyModifyResult.builder().items(AttendanceItemUtil.toItemValues(c))
							.workingDate(c.workingDate()).employeeId(c.employeeId()).completed())
					.collect(Collectors.toList());
			registerTempData(dataParent.getMode(), resultOlds, resultNews);
		} else {
			resultError.put(TypeError.DUPLICATE.value, itemErrors);
			resultError.put(TypeError.COUPLE.value, itemInputErors);
			resultError.put(TypeError.ITEM28.value, itemInputError28);
			hasError = true;
			// return resultError;
		}

		if (hasError) {
			dataResultAfterIU.setErrorMap(resultError);
			return dataResultAfterIU;
		}

		if (dataParent.getMode() == 0 && !dataParent.isFlagCalculation() && resultIU.getCommandNew() != null) {
			val dataCheck = validatorDataDaily.checkContinuousHolidays(dataParent.getEmployeeId(),
					dataParent.getDateRange(), resultIU.getCommandNew().stream().map(c -> c.getWorkInfo().getData())
							.filter(c -> c != null).collect(Collectors.toList()));
			if (!dataCheck.isEmpty()) {
				resultError.put(TypeError.CONTINUOUS.value, dataCheck);
			}
		}
		dataResultAfterIU.setErrorMap(resultError);
		return dataResultAfterIU;
	}

	private List<DailyModifyQuery> createQuerys(Map<Pair<String, GeneralDate>, List<DPItemValue>> mapSidDate) {
		List<DailyModifyQuery> querys = new ArrayList<>();
		mapSidDate.entrySet().forEach(x -> {
			List<ItemValue> itemCovert = x.getValue().stream()
					.map(y -> new ItemValue(y.getValue(), ValueType.valueOf(y.getValueType()), y.getLayoutCode(),
							y.getItemId()))
					.collect(Collectors.toList()).stream().filter(distinctByKey(p -> p.itemId()))
					.collect(Collectors.toList());
			if (!itemCovert.isEmpty())
				querys.add(new DailyModifyQuery(x.getKey().getKey(), x.getKey().getValue(), itemCovert));
		});
		return querys;
	}

	public void insertSign(List<DPItemCheckBox> dataCheckSign) {
		if (dataCheckSign.isEmpty())
			return;
		ParamIdentityConfirmDay day = new ParamIdentityConfirmDay(AppContexts.user().employeeId(), dataCheckSign
				.stream().map(x -> new SelfConfirmDay(x.getDate(), x.isValue())).collect(Collectors.toList()));
		registerIdentityConfirmDay.registerIdentity(day);
	}

	public void insertApproval(List<DPItemCheckBox> dataCheckApproval) {
		if (dataCheckApproval.isEmpty())
			return;
		ParamDayApproval param = new ParamDayApproval(AppContexts.user().employeeId(),
				dataCheckApproval.stream()
						.map(x -> new ContentApproval(x.getDate(), x.isValue(), x.getEmployeeId(), x.isFlagRemoveAll()))
						.collect(Collectors.toList()));
		registerDayApproval.registerDayApproval(param);
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		final Set<Object> seen = new HashSet<>();
		return t -> seen.add(keyExtractor.apply(t));
	}

	public void registerTempData(int displayFormat, List<DailyModifyResult> resultOlds,
			List<DailyModifyResult> resultNews) {
		switch (displayFormat) {
		case 0: // person
			List<Pair<String, GeneralDate>> listEmpDate = checkEditedItems(resultOlds, resultNews);
			if (!listEmpDate.isEmpty()) {
				interimRemainDataMngRegisterDateChange.registerDateChange(AppContexts.user().companyId(),
						listEmpDate.get(0).getLeft(),
						listEmpDate.stream().map(i -> i.getRight()).collect(Collectors.toList()));
			}
			break;
		case 1: // date
			listEmpDate = checkEditedItems(resultOlds, resultNews);
			if (!listEmpDate.isEmpty()) {
				registerProvisionalData.registerProvisionalData(AppContexts.user().companyId(),
						listEmpDate.stream().map(i -> new EmpProvisionalInput(i.getLeft(), Arrays.asList(i.getRight())))
								.collect(Collectors.toList()));
			}
			break;
		default: // error
			listEmpDate = checkEditedItems(resultOlds, resultNews);
			Map<String, List<Pair<String, GeneralDate>>> mapEmpDate = listEmpDate.stream()
					.collect(Collectors.groupingBy(x -> x.getLeft()));
			mapEmpDate.entrySet().forEach(x -> {
				interimRemainDataMngRegisterDateChange.registerDateChange(AppContexts.user().companyId(), x.getKey(),
						x.getValue().stream().map(i -> i.getRight()).collect(Collectors.toList()));
			});
			break;
		}
	}

	public List<Pair<String, GeneralDate>> checkEditedItems(List<DailyModifyResult> resultOlds,
			List<DailyModifyResult> resultNews) {
		Set<Pair<String, GeneralDate>> editedDate = new HashSet<>();
		for (DailyModifyResult r : resultOlds) {
			val newR = resultNews.stream()
					.filter(n -> n.getEmployeeId().equals(r.getEmployeeId()) && n.getDate().equals(r.getDate()))
					.findFirst();
			if (newR.isPresent()) {
				List<ItemValue> oldItems = r.getItems();
				List<ItemValue> newItems = newR.get().getItems();
				oldItems.forEach(ov -> {
					val nv = newItems.stream().filter(n -> n.getItemId() == ov.getItemId()).findFirst();
					if (nv.isPresent() && nv.get().getValue() != null && !nv.get().getValue().equals(ov.getValue())
							&& DPText.TMP_DATA_CHECK_ITEMS.contains(nv.get().getItemId())) {
						editedDate.add(Pair.of(r.getEmployeeId(), r.getDate()));
					}
				});
			}
		}
		return new ArrayList<>(editedDate);
	}

	private DPAttendanceItemRC convertItemAtr(DPAttendanceItem item) {
		return new DPAttendanceItemRC(item.getId(), item.getName(), item.getDisplayNumber(), item.isUserCanSet(),
				item.getLineBreakPosition(), item.getAttendanceAtr(), item.getTypeGroup(), item.getPrimitive());
	}

	private ItemFlex convertMonthToItem(MonthlyRecordWorkDto monthDto, DPMonthValue monthValue) {
		ItemFlex itemResult = new ItemFlex();
		MonthlyModifyResult result = MonthlyModifyResult.builder()
				.items(AttendanceItemUtil.toItemValues(monthDto, Arrays.asList(18, 21, 189, 190, 191),
						AttendanceItemUtil.AttendanceItemType.MONTHLY_ITEM))
				.employeeId(monthValue.getEmployeeId()).yearMonth(monthValue.getYearMonth())
				.closureId(monthValue.getClosureId()).closureDate(monthValue.getClosureDate()).completed();
		mapValue(result.getItems(), itemResult);
		return itemResult;
	}

	private void mapValue(List<ItemValue> items, ItemFlex dataCalc) {
		for (ItemValue item : items) {
			setValueMonth(dataCalc, item);
		}
	}

	private void setValueMonth(ItemFlex dataCalc, ItemValue item) {
		switch (item.getItemId()) {
		case 18:
			dataCalc.setValue18(item);
			break;
		case 21:
			dataCalc.setValue21(item);
			break;
		case 189:
			dataCalc.setValue189(item);
			break;
		case 190:
			dataCalc.setValue190(item);
			break;
		case 191:
			dataCalc.setValue191(item);
			break;
		default:
			break;
		}
	}
}
