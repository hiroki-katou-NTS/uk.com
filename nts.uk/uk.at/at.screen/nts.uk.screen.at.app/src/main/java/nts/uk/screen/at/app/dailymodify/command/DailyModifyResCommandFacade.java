package nts.uk.screen.at.app.dailymodify.command;

import java.util.ArrayList;
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
import nts.uk.ctx.at.record.app.command.dailyperform.checkdata.RCDailyCorrectionResult;
import nts.uk.ctx.at.record.app.command.dailyperform.month.UpdateMonthDailyParam;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.ContentApproval;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.ParamDayApproval;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.RegisterDayApproval;
import nts.uk.ctx.at.record.dom.daily.itemvalue.DailyItemValue;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.ParamIdentityConfirmDay;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.RegisterIdentityConfirmDay;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.SelfConfirmDay;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQuery;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceCorrectionProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.checkdata.ValidatorDataDailyRes;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.CodeName;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.DataDialogWithTypeProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.ParamDialog;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemCheckBox;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemParent;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataResultAfterIU;
import nts.uk.screen.at.app.dailyperformance.correction.dto.TypeError;
import nts.uk.screen.at.app.dailyperformance.correction.dto.type.TypeLink;
import nts.uk.screen.at.app.monthlyperformance.correction.command.MonthModifyCommandFacade;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
@Transactional
/** 日別修正CommandFacade */
public class DailyModifyResCommandFacade {

	/** finder */
	@Inject
	private DailyRecordWorkFinder finder;

	@Inject
	private DailyRecordWorkCommandHandler handler;

	@Inject
	private RegisterIdentityConfirmDay registerIdentityConfirmDay;

	@Inject
	private RegisterDayApproval registerDayApproval;

	@Inject
	private OptionalItemRepository optionalMasterRepo;

	@Inject
	private DataDialogWithTypeProcessor dataDialogWithTypeProcessor;

	@Inject
	private ValidatorDataDailyRes validatorDataDaily;

	@Inject
	private DailyPerformanceCorrectionProcessor processor;

	@Inject
	private MonthModifyCommandFacade monthModifyCommandFacade;
	
	public RCDailyCorrectionResult handleUpdate(List<DailyModifyQuery> querys, List<DailyRecordDto> dtoOlds,
			List<DailyRecordDto> dtoNews, List<DailyItemValue> dailyItems, UpdateMonthDailyParam month, int mode, boolean flagCalculation) {
		String sid = AppContexts.user().employeeId();

		List<DailyRecordWorkCommand> commandNew = createCommands(sid, dtoNews, querys);

		List<DailyRecordWorkCommand> commandOld = createCommands(sid, dtoOlds, querys);
		if (!flagCalculation) {
			return this.handler.handleUpdateRes(commandNew, commandOld, dailyItems, month, mode);
		}else{
			this.handler.handlerNoCalc(commandNew, commandOld, dailyItems, true, month, mode);
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

	private Pair<List<DailyRecordDto>, List<DailyRecordDto>> toDto(List<DailyModifyQuery> query) {
		List<DailyRecordDto> dtoNews, dtoOlds = new ArrayList<>();
		Map<Integer, OptionalItem> optionalMaster = optionalMasterRepo
				.findByPerformanceAtr(AppContexts.user().companyId(), PerformanceAtr.DAILY_PERFORMANCE).stream()
				.collect(Collectors.toMap(c -> c.getOptionalItemNo().v(), c -> c));
		dtoOlds = finder.find(query.stream()
				.collect(Collectors.groupingBy(c -> c.getEmployeeId(), Collectors.collectingAndThen(Collectors.toList(),
						c -> c.stream().map(q -> q.getBaseDate()).collect(Collectors.toList())))));
		dtoNews = dtoOlds.stream().map(o -> {

			List<ItemValue> itemValues = query.stream()
					.filter(q -> q.getBaseDate().equals(o.workingDate()) && q.getEmployeeId().equals(o.employeeId()))
					.findFirst().get().getItemValues();
			DailyRecordDto dtoClone = o.clone();
			AttendanceItemUtil.fromItemValues(dtoClone, itemValues);
			dtoClone.getOptionalItem().ifPresent(optional -> {
				optional.correctItems(optionalMaster);
			});
			dtoClone.getTimeLeaving().ifPresent(dto -> {
				if (dto.getWorkAndLeave() != null)
					dto.getWorkAndLeave().removeIf(tl -> tl.getWorking() == null && tl.getLeave() == null);
			});
			return dtoClone;
		}).collect(Collectors.toList());
		return Pair.of(dtoOlds, dtoNews);
	}

	private DailyRecordWorkCommand createCommand(String sid, DailyRecordDto dto, DailyModifyQuery query) {
		if(query == null){
			return  DailyRecordWorkCommand.open().withData(dto).forEmployeeIdAndDate(dto.employeeId(), dto.getDate()).fromItems(Collections.emptyList());
		}
		DailyRecordWorkCommand command = DailyRecordWorkCommand.open().forEmployeeId(query.getEmployeeId())
				.withWokingDate(query.getBaseDate()).withData(dto).fromItems(query == null ? Collections.emptyList(): query.getItemValues());
		command.getEditState().updateDatas(convertTo(sid, query));
		return command;
	}

	private List<DailyRecordWorkCommand> createCommands(String sid, List<DailyRecordDto> lstDto,
			List<DailyModifyQuery> querys) {
		if(querys.isEmpty()) return lstDto.stream().map(o -> {
			return createCommand(sid, o, null);
		}).collect(Collectors.toList());
		
		return lstDto.stream().map(o -> {
			DailyModifyQuery query = querys.stream()
					.filter(q -> q.getBaseDate().equals(o.workingDate()) && q.getEmployeeId().equals(o.employeeId()))
					.findFirst().orElse(null);
			return createCommand(sid, o, query);
		}).collect(Collectors.toList());
	}

	public Map<Integer, List<DPItemValue>> insertItemValues(DPItemParent dataParent) {
		Map<Integer, List<DPItemValue>> resultError = new HashMap<>();
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
				IntegrationOfMonthly domainMonth = monthModifyCommandFacade.toDto(monthQuery).toDomain(month.getEmployeeId(), new YearMonth(month.getYearMonth()), month.getClosureId(), month.getClosureDate());
				Optional<IntegrationOfMonthly> domainMonthOpt = Optional.of(domainMonth);
				monthParam = new UpdateMonthDailyParam(month.getYearMonth(), month.getEmployeeId(),
						month.getClosureId(), month.getClosureDate(), domainMonthOpt, new DatePeriod(
								dataParent.getDateRange().getStartDate(), dataParent.getDateRange().getEndDate()));
//				monthModifyCommandFacade.handleUpdate(new MonthlyModifyQuery(month.getItems().stream().map(x -> {
//					return ItemValue.builder().itemId(x.getItemId()).layout(x.getLayoutCode()).value(x.getValue())
//							.valueType(ValueType.valueOf(x.getValueType())).withPath("");
//				}).collect(Collectors.toList()), month.getYearMonth(), month.getEmployeeId(), month.getClosureId(),
//						month.getClosureDate()));
			}
		}

		if (dataParent.getSpr() != null) {
			processor.insertStampSourceInfo(dataParent.getSpr().getEmployeeId(), dataParent.getSpr().getDate(),
					dataParent.getSpr().isChange31(), dataParent.getSpr().isChange34());
		}

		// map id -> code possition and workplace
		List<DPItemValue> itemValueChild = dataParent.getItemValues().stream().map(x -> {
			DPItemValue item = x;
			if (x.getTypeGroup() == TypeLink.POSSITION.value) {
				CodeName codeName = dataDialogWithTypeProcessor.getTypeDialog(x.getTypeGroup(),
						new ParamDialog(x.getDate(), x.getValue()));
				item.setValue(codeName == null ? null : codeName.getId());
				return item;
			} else if (x.getTypeGroup() == TypeLink.WORKPLACE.value) {
				CodeName codeName = dataDialogWithTypeProcessor.getTypeDialog(x.getTypeGroup(),
						new ParamDialog(x.getDate(), x.getValue()));
				item.setValue(codeName == null ? null : codeName.getId());
				return item;
			}
			return item;
		}).collect(Collectors.toList());
		
		Map<Pair<String, GeneralDate>, List<DPItemValue>> mapSidDate = itemValueChild.stream()
				.collect(Collectors.groupingBy(x -> Pair.of(x.getEmployeeId(), x.getDate())));

		List<DailyModifyQuery> querys = createQuerys(mapSidDate);
		Pair<List<DailyRecordDto>, List<DailyRecordDto>> mergeDto;
		List<DailyRecordDto> dtoOlds = new ArrayList<>();
		List<DailyRecordDto> dtoNews = new ArrayList<>();
		mergeDto = toDto(querys);
		dtoOlds = mergeDto.getLeft();
		dtoNews = mergeDto.getRight();
		// map to list result -> check error;
		List<DailyModifyResult> resultOlds = dtoOlds.stream()
				.map(c -> DailyModifyResult.builder().items(AttendanceItemUtil.toItemValues(c))
						.workingDate(c.workingDate()).employeeId(c.employeeId()).completed())
				.collect(Collectors.toList());
		Map<Pair<String, GeneralDate>, List<DailyModifyResult>> mapSidDateData = resultOlds.stream()
				.collect(Collectors.groupingBy(x -> Pair.of(x.getEmployeeId(), x.getDate())));

		// check error care item
		List<DPItemValue> itemErrors = new ArrayList<>();
		List<DPItemValue> itemInputErors = new ArrayList<>();
		List<DPItemValue> itemInputError28 = new ArrayList<>();
		List<DPItemValue> itemInputDeviation = new ArrayList<>();
		mapSidDate.entrySet().forEach(x -> {
			List<DPItemValue> itemCovert = x.getValue().stream().filter(y -> y.getValue() != null)
					.collect(Collectors.toList()).stream().filter(distinctByKey(p -> p.getItemId()))
					.collect(Collectors.toList());
			List<DailyModifyResult> itemValues =  itemCovert.isEmpty() ? Collections.emptyList() : mapSidDateData.get(Pair.of(itemCovert.get(0).getEmployeeId(), itemCovert.get(0).getDate()));
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

		// insert , update item
		List<DailyItemValue> dailyItems = resultOlds.stream().map(x ->  DailyItemValue.build().createEmpAndDate(x.getEmployeeId(), x.getDate()).createItems(x.getItems())).collect(Collectors.toList());
		if (itemErrors.isEmpty() && itemInputErors.isEmpty() && itemInputError28.isEmpty()) {
			RCDailyCorrectionResult resultUpdate = handleUpdate(querys, dtoOlds, dtoNews, dailyItems, monthParam, dataParent.getMode(), dataParent.isFlagCalculation());
//			itemInputDeviation = resultUpdate.getLstDivergence().stream().map(x -> new DPItemValue(x.getRowId(), x.getEmployeeId(),
//					x.getDate(), x.getItemId(), x.getValue(), x.getNameMessage())).collect(Collectors.toList());
		} else {
			resultError.put(TypeError.DUPLICATE.value, itemErrors);
			resultError.put(TypeError.COUPLE.value, itemInputErors);
			resultError.put(TypeError.ITEM28.value, itemInputError28);
			// return resultError;
		}

		if (!itemInputDeviation.isEmpty()) {
			resultError.put(TypeError.DEVIATION_REASON.value, itemInputDeviation);
		}
		// insert sign
		insertSign(dataParent.getDataCheckSign());

		// insert approval
		insertApproval(dataParent.getDataCheckApproval());

		if (dataParent.getMode() == 0) {
			val dataCheck = validatorDataDaily.checkContinuousHolidays(dataParent.getEmployeeId(),
					dataParent.getDateRange());
			if (!dataCheck.isEmpty()) {
				resultError.put(TypeError.CONTINUOUS.value, dataCheck);
			}
		}

		return resultError;
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
				IntegrationOfMonthly domainMonth = monthModifyCommandFacade.toDto(monthQuery).toDomain(month.getEmployeeId(), new YearMonth(month.getYearMonth()), month.getClosureId(), month.getClosureDate());
				Optional<IntegrationOfMonthly> domainMonthOpt = Optional.of(domainMonth);
				monthParam = new UpdateMonthDailyParam(month.getYearMonth(), month.getEmployeeId(),
						month.getClosureId(), month.getClosureDate(), domainMonthOpt, new DatePeriod(
								dataParent.getDateRange().getStartDate(), dataParent.getDateRange().getEndDate()));
			}
		}

		if (dataParent.getSpr() != null) {
			processor.insertStampSourceInfo(dataParent.getSpr().getEmployeeId(), dataParent.getSpr().getDate(),
					dataParent.getSpr().isChange31(), dataParent.getSpr().isChange34());
		}

		Map<Pair<String, GeneralDate>, List<DPItemValue>> mapSidDate = dataParent.getItemValues().stream()
				.collect(Collectors.groupingBy(x -> Pair.of(x.getEmployeeId(), x.getDate())));

		List<DailyModifyQuery> querys = createQuerys(mapSidDate);
		// map to list result -> check error;
		List<DailyRecordDto> dailyOlds, dailyEdits = new ArrayList<>();
		if(!querys.isEmpty() && !dataParent.isFlagCalculation()){
			dailyOlds = dataParent.getDailyOlds().stream()
					.filter(x -> mapSidDate.containsKey(Pair.of(x.getEmployeeId(), x.getDate())))
					.collect(Collectors.toList());
			dailyEdits = dataParent.getDailyEdits().stream()
					.filter(x -> mapSidDate.containsKey(Pair.of(x.getEmployeeId(), x.getDate())))
					.collect(Collectors.toList());
		} else {
			dailyOlds = dataParent.getDailyOlds();
			dailyEdits = dataParent.getDailyEdits();
		}
		List<DailyModifyResult> resultOlds = dailyOlds.stream()
				.map(c -> DailyModifyResult.builder().items(AttendanceItemUtil.toItemValues(c))
						.workingDate(c.workingDate()).employeeId(c.employeeId()).completed())
				.collect(Collectors.toList());
		Map<Pair<String, GeneralDate>, List<DailyModifyResult>> mapSidDateData = resultOlds.stream()
				.collect(Collectors.groupingBy(x -> Pair.of(x.getEmployeeId(), x.getDate())));

		// check error care item
		List<DPItemValue> itemErrors = new ArrayList<>();
		List<DPItemValue> itemInputErors = new ArrayList<>();
		List<DPItemValue> itemInputError28 = new ArrayList<>();
		List<DPItemValue> itemInputDeviation = new ArrayList<>();
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
		List<DailyItemValue> dailyItems = resultOlds.stream().map(x ->  DailyItemValue.build().createEmpAndDate(x.getEmployeeId(), x.getDate()).createItems(x.getItems())).collect(Collectors.toList());
		if (itemErrors.isEmpty() && itemInputErors.isEmpty() && itemInputError28.isEmpty()) {
			if(querys.isEmpty() ? dataParent.isFlagCalculation() : true){
				RCDailyCorrectionResult resultIU = handleUpdate(querys, dailyOlds, dailyEdits, dailyItems, monthParam,
						dataParent.getMode(), dataParent.isFlagCalculation());
				val errorDivergence = validatorDataDaily.errorCheckDivergence(resultIU.getLstDailyDomain(),
						resultIU.getLstMonthDomain());
				if (!errorDivergence.isEmpty())
					resultError.putAll(errorDivergence);
				if (dataParent.getMode() == 0)
					dataResultAfterIU.setFlexShortage(
							validatorDataDaily.errorCheckFlex(resultIU.getLstMonthDomain(), monthParam));
			}
		} else {
			resultError.put(TypeError.DUPLICATE.value, itemErrors);
			resultError.put(TypeError.COUPLE.value, itemInputErors);
			resultError.put(TypeError.ITEM28.value, itemInputError28);
			// return resultError;
		}
		
		// insert sign
		insertSign(dataParent.getDataCheckSign());

		// insert approval
		insertApproval(dataParent.getDataCheckApproval());

		if (dataParent.getMode() == 0 && !dataParent.isFlagCalculation()) {
			val dataCheck = validatorDataDaily.checkContinuousHolidays(dataParent.getEmployeeId(),
					dataParent.getDateRange());
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
		if(dataCheckSign.isEmpty()) return;
		ParamIdentityConfirmDay day = new ParamIdentityConfirmDay(AppContexts.user().employeeId(), dataCheckSign
				.stream().map(x -> new SelfConfirmDay(x.getDate(), x.isValue())).collect(Collectors.toList()));
		registerIdentityConfirmDay.registerIdentity(day);
	}

	public void insertApproval(List<DPItemCheckBox> dataCheckApproval) {
		if(dataCheckApproval.isEmpty()) return;
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
}
