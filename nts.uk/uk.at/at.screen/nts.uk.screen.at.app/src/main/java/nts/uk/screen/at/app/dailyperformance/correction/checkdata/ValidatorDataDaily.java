package nts.uk.screen.at.app.dailyperformance.correction.checkdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckService;
import nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck.result.ContinuousHolidayCheckResult;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ValidatorDataDaily {
	
	@Inject
	private ErAlWorkRecordCheckService erAlWorkRecordCheckService;
	
	@Inject
	private DailyRecordWorkFinder fullFinder;
	
//	@Inject
//	private DivergenceTimeRepository divergenceTimeRepository;
//	
//	@Inject
//	private DivergenceReasonInputMethodService divergenceReasonInputMethodService;
	
	@Inject
	private BasicScheduleService basicScheduleService;

	private static final Integer[] CHILD_CARE = { 759, 760, 761, 762 };
	private static final Integer[] CARE = { 763, 764, 765, 766 };
	private static final Integer[] INPUT_CHECK = { 759, 760, 761, 762, 763, 764, 765, 766, 157, 159, 163, 165, 171, 169,
			177, 175, 183, 181, 189, 187, 195, 193, 199, 201, 205, 207, 211, 213 };
	
	static final Map<Integer, Integer> INPUT_CHECK_MAP = IntStream.range(0, INPUT_CHECK.length).boxed().collect(Collectors.toMap(x -> INPUT_CHECK[x], x -> x%2 == 0 ? INPUT_CHECK[x+1] : INPUT_CHECK[x-1]));
	
	private static final Integer[] DEVIATION_REASON  = {436, 438, 439, 441, 443, 444, 446, 448, 449, 451, 453, 454, 456, 458, 459, 799, 801, 802, 804, 806, 807, 809, 811, 812, 814, 816, 817, 819, 821, 822};
	static final Map<Integer, Integer> DEVIATION_REASON_MAP = IntStream.range(0, DEVIATION_REASON.length).boxed().collect(Collectors.toMap(x -> DEVIATION_REASON[x], x -> x/3 +1));
	//Arrays.stream(INPUT_CHECK).
	
	// 育児と介護の時刻が両方入力されていないかチェックする
	public List<DPItemValue> checkCareItemDuplicate(List<DPItemValue> items) {
		List<DPItemValue> childCares = hasChildCare(items);
		List<DPItemValue> cares = hasCare(items);
		if (!childCares.isEmpty() && !cares.isEmpty()) {
			// throw new BusinessException("Msg_996");
			childCares.addAll(cares);
			return childCares;
		} else
			return Collections.emptyList();

	}

	private List<DPItemValue> hasChildCare(List<DPItemValue> items) {
		List<DPItemValue> itemChild = items.stream()
				.filter(x -> x.getValue() != null && (x.getItemId() == CHILD_CARE[0] || x.getItemId() == CHILD_CARE[1]
						|| x.getItemId() == CHILD_CARE[2] || x.getItemId() == CHILD_CARE[3]))
				.collect(Collectors.toList());
		return itemChild.isEmpty() ? Collections.emptyList() : itemChild;
	}

	private List<DPItemValue> hasCare(List<DPItemValue> items) {
		List<DPItemValue> itemCare = items.stream().filter(x -> x.getValue() != null && (x.getItemId() == CARE[0]
				|| x.getItemId() == CARE[1] || x.getItemId() == CARE[2] || x.getItemId() == CARE[3]))
				.collect(Collectors.toList());
		return itemCare.isEmpty() ? Collections.emptyList() : itemCare;
	}

	public List<DPItemValue> checkCareInputData(List<DPItemValue> items) {
		List<DPItemValue> childCares = hasChildCare(items);
		List<DPItemValue> cares = hasCare(items);
		List<DPItemValue> result = new ArrayList<>();
		if (!childCares.isEmpty()) {
			Map<Integer, DPItemValue> childMap= childCares.stream().collect(Collectors.toMap(DPItemValue::getItemId, x -> x));
		    boolean childCare759 = childMap.containsKey(CHILD_CARE[0]);
		    boolean childCare760 = childMap.containsKey(CHILD_CARE[1]);
		    boolean childCare761 = childMap.containsKey(CHILD_CARE[2]);
		    boolean childCare762 = childMap.containsKey(CHILD_CARE[3]);
		    if(!(childCare759 && childCare760)){
		    	if(childCare759){
		    		result.add(childMap.get(CHILD_CARE[0]));
		    	}else if(childCare760){
		    		result.add(childMap.get(CHILD_CARE[1]));
		    	}
		    }
			if (!(childCare761 && childCare762)) {
				if(childCare761){
		    		result.add(childMap.get(CHILD_CARE[2]));
		    	}else if(childCare762){
		    		result.add(childMap.get(CHILD_CARE[3]));
		    	}
			}
			return result;
		} else if (!cares.isEmpty()) {
			Map<Integer, DPItemValue> caresMap= cares.stream().collect(Collectors.toMap(DPItemValue::getItemId, x -> x));
		    boolean care763 = caresMap.containsKey(CARE[0]);
		    boolean care764 = caresMap.containsKey(CARE[1]);
		    boolean care765 = caresMap.containsKey(CARE[2]);
		    boolean care766 = caresMap.containsKey(CARE[3]);
		    if(!(care763 && care764)){
		    	if(care763){
		    		result.add(caresMap.get(CARE[0]));
		    	}else if(care764){
		    		result.add(caresMap.get(CARE[1]));
		    	}
		    }
			if (!(care765 && care766)) {
				if(care765){
		    		result.add(caresMap.get(CARE[2]));
		    	}else if(care766){
		    		result.add(caresMap.get(CARE[3]));
		    	}
			}
			return result;
		}
		return Collections.emptyList();
	}
	
	//check trong 1 ngay nhom item yeu cau nhap theo cap
	public List<DPItemValue> checkInputData(List<DPItemValue> items) {
		List<DPItemValue> result = new ArrayList<>();
		//loc chua item can check
		List<DPItemValue> itemCanCheck = items.stream().filter(x -> INPUT_CHECK_MAP.containsKey(x.getItemId())).collect(Collectors.toList());
		if (itemCanCheck.isEmpty())
			return result;
		Map<Integer, String> itemCheckMap = itemCanCheck.stream().collect(Collectors.toMap(x -> x.getItemId(), x -> x.getValue()));
		List<DPItemValue> itemCheckDBs = new ArrayList<>();
		// loc nhung thang chi duoc insert 1 trong 1 cap 
		itemCanCheck.forEach(x ->{
			Integer itemCheck = INPUT_CHECK_MAP.get(x.getItemId());
			if(!itemCheckMap.containsKey(itemCheck)) itemCheckDBs.add(x);
		});
		if(itemCheckDBs.isEmpty()) return result;
		
		List<GeneralDate> dates = items.stream().map(x -> x.getDate()).sorted((x, y) -> x.compareTo(y))
				.collect(Collectors.toSet()).stream().collect(Collectors.toList());
		List<String> employees = items.stream().map(x -> x.getEmployeeId()).collect(Collectors.toSet()).stream().collect(Collectors.toList());
		
		List<DailyModifyResult> itemValues =  this.fullFinder.find(employees, new DatePeriod(dates.get(0), dates.get(dates.size() - 1))).stream()
				.map(c -> DailyModifyResult.builder().items(AttendanceItemUtil.toItemValues(c, Arrays.asList(INPUT_CHECK)))
						.workingDate(c.workingDate()).employeeId(c.employeeId()).completed())
				.collect(Collectors.toList());
		if(itemValues.isEmpty()) return result;
		Map<Integer, String> valueGetFromDBMap = itemValues.get(0).getItems().stream().collect(Collectors.toMap(x -> x.getItemId(), x -> x.getValue() == null ? "" : x.getValue()));
		itemCheckDBs.stream().forEach( x ->{
			if(valueGetFromDBMap.containsKey(INPUT_CHECK_MAP.get(x.getItemId())) && valueGetFromDBMap.get(INPUT_CHECK_MAP.get(x.getItemId())).equals("")){
				result.add(x);
			}
		});
		return result;
	}
	
	public List<DPItemValue> checkContinuousHolidays(String employeeId, DateRange date, List<WorkInfoOfDailyPerformance> workInfos) {
		ContinuousHolidayCheckResult result = erAlWorkRecordCheckService.checkContinuousHolidays(employeeId,
				new DatePeriod(date.getStartDate(), date.getEndDate()), workInfos);
		if(result == null) return Collections.emptyList();
		Map<GeneralDate, Integer> resultMap = result.getCheckResult();
		if (!resultMap.isEmpty()) {
			return resultMap.entrySet().stream()
					.map(x -> new DPItemValue("勤務種類", employeeId, x.getKey(), 0, String.valueOf(x.getValue()), result.message()))
					.collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}
	
	//乖離理由が選択、入力されているかチェックする
	public void checkReasonInput(List<DPItemValue> items){
//		String companyId = AppContexts.user().companyId();
//		List<DivergenceTime> divergenceTime = divergenceTimeRepository.getDivTimeListByUseSet(companyId);
//		items.stream().forEach(x -> {
//			JudgmentResult judgmentResult = divergenceReasonInputMethodService.determineLeakageReason(x.getEmployeeId(), x.getDate(), divergenceTimeNo, divergenceReasonCode, divergenceReasonContent, justmentResult)
//		});
	}
	public List<DPItemValue> checkInput28And1(List<DPItemValue> items){
		List<DPItemValue> result = new ArrayList<>();
		result = checkInputItem28(items);
		result.addAll(checkInputItem1(items));
		return result;
	}
	public List<DPItemValue> checkInputItem28(List<DPItemValue> items) {
		List<DPItemValue> result = new ArrayList<>();
		String textResource = TextResource.localize("Msg_1270");
		String textResourceItem28Null = TextResource.localize("Msg_1329");
		DPItemValue valueTemp;
		Optional<DPItemValue> item28 = items.stream().filter(x -> x.getItemId() == 28).findFirst();
		Optional<DPItemValue> item29 = items.stream().filter(x -> x.getItemId() == 29).findFirst();
		if(!item28.isPresent() && !item29.isPresent()){
			return result;
		}
		
		String workTypeCode ="";
		if(item28.isPresent()){
			workTypeCode = item28.get().getValue();
			if (workTypeCode == null || workTypeCode.equals("")) {
				valueTemp = item28.get();
				valueTemp.setLayoutCode(textResourceItem28Null);
				result.add(valueTemp);
				return result;
			}
		}else{
			List<DailyModifyResult> itemValue28s =  this.fullFinder.find(Arrays.asList(item29.get().getEmployeeId()), new DatePeriod(item29.get().getDate(), item29.get().getDate())).stream()
					.map(c -> DailyModifyResult.builder().items(AttendanceItemUtil.toItemValues(c, Arrays.asList(28)))
							.workingDate(c.workingDate()).employeeId(c.employeeId()).completed())
					.collect(Collectors.toList());
			if (!itemValue28s.isEmpty() && !itemValue28s.get(0).getItems().isEmpty())
				workTypeCode = itemValue28s.get(0).getItems().get(0).getValue();
		}
		
		if(workTypeCode.equals("")) return result;
		
		SetupType setupType = basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		if(setupType.value == SetupType.NOT_REQUIRED.value || setupType.value == SetupType.OPTIONAL.value){
			return result;
		}
		
		if (item29.isPresent()) {
			if (item29.get().getValue() == null || item29.get().getValue().equals("")) {
				valueTemp = item29.get();
				valueTemp.setLayoutCode(textResource);
				result.add(valueTemp);
				return result;
			}
			return result;
		}
		
		// check DB item 29
		List<DailyModifyResult> itemValues29 =  this.fullFinder.find(Arrays.asList(item28.get().getEmployeeId()), new DatePeriod(item28.get().getDate(), item28.get().getDate())).stream()
				.map(c -> DailyModifyResult.builder().items(AttendanceItemUtil.toItemValues(c, Arrays.asList(29)))
						.workingDate(c.workingDate()).employeeId(c.employeeId()).completed())
				.collect(Collectors.toList());
		if(itemValues29.isEmpty() || itemValues29.get(0).getItems().isEmpty()) return result;
		ItemValue value = itemValues29.get(0).getItems().get(0);
		if(value.getValue() == null || value.getValue().equals("")){
			valueTemp = item28.get();
			valueTemp.setLayoutCode(textResource);
			result.add(valueTemp);
			return result;
		}
		return result;
	}
	
	public List<DPItemValue> checkInputItem1(List<DPItemValue> items) {
		List<DPItemValue> result = new ArrayList<>();
		String textResourceItem1Null = TextResource.localize("Msg_1328");
		String textResource = TextResource.localize("Msg_1308");
		DPItemValue valueTemp;
		Optional<DPItemValue> item1 = items.stream().filter(x -> x.getItemId() == 1).findFirst();
		Optional<DPItemValue> item2 = items.stream().filter(x -> x.getItemId() == 2).findFirst();
		if(!item1.isPresent() && !item2.isPresent()){
			return result;
		}
		
		String workTypeCode ="";
		if(item1.isPresent()){
			workTypeCode = item1.get().getValue();
			if (workTypeCode == null || workTypeCode.equals("")) {
				valueTemp = item1.get();
				valueTemp.setLayoutCode(textResourceItem1Null);
				result.add(valueTemp);
				return result;
			}
		}else{
			List<DailyModifyResult> itemValue1s =  this.fullFinder.find(Arrays.asList(item2.get().getEmployeeId()), new DatePeriod(item2.get().getDate(), item2.get().getDate())).stream()
					.map(c -> DailyModifyResult.builder().items(AttendanceItemUtil.toItemValues(c, Arrays.asList(1)))
							.workingDate(c.workingDate()).employeeId(c.employeeId()).completed())
					.collect(Collectors.toList());
			if (!itemValue1s.isEmpty() && !itemValue1s.get(0).getItems().isEmpty())
				workTypeCode = itemValue1s.get(0).getItems().get(0).getValue();
		}
		
		if(workTypeCode.equals("")) return result;
		
		SetupType setupType = basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		if(setupType.value == SetupType.NOT_REQUIRED.value || setupType.value == SetupType.OPTIONAL.value){
			return result;
		}
		
		if (item2.isPresent()) {
			if (item2.get().getValue() == null || item2.get().getValue().equals("")) {
				valueTemp = item2.get();
				valueTemp.setLayoutCode(textResource);
				result.add(valueTemp);
				return result;
			}
			return result;
		}
		
		// check DB item 2
		List<DailyModifyResult> itemValues2 =  this.fullFinder.find(Arrays.asList(item1.get().getEmployeeId()), new DatePeriod(item1.get().getDate(), item1.get().getDate())).stream()
				.map(c -> DailyModifyResult.builder().items(AttendanceItemUtil.toItemValues(c, Arrays.asList(2)))
						.workingDate(c.workingDate()).employeeId(c.employeeId()).completed())
				.collect(Collectors.toList());
		if(itemValues2.isEmpty() || itemValues2.get(0).getItems().isEmpty()) return result;
		ItemValue value = itemValues2.get(0).getItems().get(0);
		if(value.getValue() == null || value.getValue().equals("")){
			valueTemp = item1.get();
			valueTemp.setLayoutCode(textResource);
			result.add(valueTemp);
			return result;
		}
		return result;
	}
	
}
