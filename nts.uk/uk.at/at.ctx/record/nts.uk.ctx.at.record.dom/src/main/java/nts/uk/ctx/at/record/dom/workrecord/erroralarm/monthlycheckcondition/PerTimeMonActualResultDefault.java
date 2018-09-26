package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.monthly.MonthlyRecordValueImport;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.AttendanceItemCondition;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Stateless
public class PerTimeMonActualResultDefault implements PerTimeMonActualResultService {

	@Inject
	private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;
	
	@Inject
	private AttendanceItemConvertFactory attendanceItemConvertFactory;
	
	@Inject 
	private AnyItemOfMonthlyRepository anyItemOfMonthlyRepo;
	
	
	@Override
	public boolean checkPerTimeMonActualResult(YearMonth yearMonth, int closureID, ClosureDate closureDate,
			String employeeID,AttendanceItemCondition attendanceItemCondition) {
		
		//社員に一致する月別実績を取得する
		Optional<AttendanceTimeOfMonthly> attendanceTimeOfMonthly = attendanceTimeOfMonthlyRepo.find(
				employeeID, 
				yearMonth,
				EnumAdaptor.valueOf(closureID,ClosureId.class),
				closureDate);
		//存在しない場合
		if(!attendanceTimeOfMonthly.isPresent()) {
			return false;
		}
		
		MonthlyRecordToAttendanceItemConverter monthly = attendanceItemConvertFactory.createMonthlyConverter();
		monthly.withAttendanceTime(attendanceTimeOfMonthly.get());
		
		//勤怠項目をチェックする
		boolean check = attendanceItemCondition.check(item->{
			if (item.isEmpty()) {
				return item;
			}
			return monthly.convert(item).stream().map(iv -> getValue(iv))
					.collect(Collectors.toList());
			
		});
		return check;
	}
	//HoiDD No.257
	@Override
	public Map<String, Integer> checkPerTimeMonActualResult(YearMonth yearMonth, String employeeID,
			AttendanceItemCondition attendanceItemCondition, List<Integer> attendanceIds) {
		Map<String, Integer> results = new HashMap<String,Integer>();
		List<MonthlyRecordValueImport> monthlyRecords = new ArrayList<>();
		List<AttendanceTimeOfMonthly> attendanceTimeOfMonthlys = new ArrayList<>();
		List<AnyItemOfMonthly> anyItems = new ArrayList<>();
		//締めをチェックする
			attendanceTimeOfMonthlys = attendanceTimeOfMonthlyRepo.findByYearMonthOrderByStartYmd(employeeID,
					yearMonth);
			anyItems = anyItemOfMonthlyRepo.findByMonthly(employeeID, yearMonth);

		if (!CollectionUtil.isEmpty(attendanceTimeOfMonthlys)) {
			for (AttendanceTimeOfMonthly attendanceTimeOfMonthly : attendanceTimeOfMonthlys) {
				AttendanceTimeOfMonthlyKey key = new AttendanceTimeOfMonthlyKey(
						attendanceTimeOfMonthly.getEmployeeId(),
						attendanceTimeOfMonthly.getYearMonth(),
						attendanceTimeOfMonthly.getClosureId(),
						attendanceTimeOfMonthly.getClosureDate());
				MonthlyRecordToAttendanceItemConverter monthly = attendanceItemConvertFactory.createMonthlyConverter();
					monthly.withAttendanceTime(attendanceTimeOfMonthly);
				if (!CollectionUtil.isEmpty(anyItems)){
					Map<AttendanceTimeOfMonthlyKey, List<AnyItemOfMonthly>> anyItemsMap = new HashMap<>();
					for (AnyItemOfMonthly anyItem : anyItems){
						AttendanceTimeOfMonthlyKey key2 = new AttendanceTimeOfMonthlyKey(
								anyItem.getEmployeeId(),
								anyItem.getYearMonth(),
								anyItem.getClosureId(),
								anyItem.getClosureDate());
						if(anyItemsMap.containsKey(key)){
							anyItemsMap.get(key2).add(anyItem);
						}else {
							List<AnyItemOfMonthly> anyItemsType = new ArrayList<>();
							anyItemsType.add(anyItem);
							anyItemsMap.put(key2, anyItemsType);
						}
					}
					if(anyItemsMap.containsKey(key)){
						monthly.withAnyItem(anyItemsMap.get(key));
					}
				}
				monthlyRecords.add(MonthlyRecordValueImport.of(yearMonth, attendanceTimeOfMonthly.getClosureId(),
						attendanceTimeOfMonthly.getClosureDate(), monthly.convert(attendanceIds)));
				
			}
		}
		//存在しない場合
		if(CollectionUtil.isEmpty(monthlyRecords)) {
			return results;
		}
		//取得した件数分ループする
		for (MonthlyRecordValueImport monthlyRecord : monthlyRecords) {
			//勤怠項目をチェックする
			boolean check = attendanceItemCondition.check(item->{
				if (item.isEmpty()) {
					return item;
				}
				return monthlyRecord.getItemValues().stream().map(iv -> getValueNew(iv))
						.collect(Collectors.toList());
			});
			if (check == true) {
				results.put(employeeID + yearMonth.toString() +  monthlyRecord.getClosureId().toString(),1);
			}
		}
		return results;
	}
	//HoiDD
	private Integer getValueNew(ItemValue value) {
		if(value.getValueType()==ValueType.DATE){
			return 0;
		}
		if (value.value() == null) {
			return 0;
		}
		else if (value.getValueType().isDouble()||value.getValueType().isInteger()) {
			return value.getValueType().isDouble() ? ((Double) value.value()).intValue() : (Integer) value.value();
		}
		return 0;
	}
	
	private Integer getValue(ItemValue value) {
		if (value.value() == null) {
			return 0;
		}
		return value.getValueType().isDouble() ? ((Double) value.value()).intValue()
				: (Integer) value.value();
	}
}
