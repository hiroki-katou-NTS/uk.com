package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.monthly.MonthlyRecordValueImport;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
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
	public Map<String, Integer> checkPerTimeMonActualResult(YearMonth yearMonth,
			Optional<ClosureId> closureID, Optional<ClosureDate> closureDate, String employeeID,
			AttendanceItemCondition attendanceItemCondition, List<Integer> attendanceIds) {
		Map<String, Integer> results = new HashMap<String,Integer>();
		List<MonthlyRecordValueImport> monthlyRecords = new ArrayList<>();
		List<AttendanceTimeOfMonthly> attendanceTimeOfMonthlys = new ArrayList<>();
		List<AnyItemOfMonthly> anyItems = new ArrayList<>();
		//締めをチェックする
		//INPUT.締めID＝設定なし or INPUT.締め日＝設定なし
		if (!closureID.isPresent() || !closureDate.isPresent()) {
			
			attendanceTimeOfMonthlys = attendanceTimeOfMonthlyRepo.findByYearMonthOrderByStartYmd(employeeID,
					yearMonth);
			anyItems = anyItemOfMonthlyRepo.findByMonthly(employeeID, yearMonth);

		//INPUT.締めID＝設定あり and INPUT.締め日＝設定あり
		}else {
			anyItems = anyItemOfMonthlyRepo.findByMonthlyAndClosure(employeeID, yearMonth,
					closureID.get(), closureDate.get());
			Optional<AttendanceTimeOfMonthly> attendanceTimeOfMonthly = attendanceTimeOfMonthlyRepo.find(employeeID, yearMonth,
					closureID.get(), closureDate.get());
			if(attendanceTimeOfMonthly.isPresent()){
			attendanceTimeOfMonthlys.add(attendanceTimeOfMonthly.get());
			}
		}
		if (!CollectionUtil.isEmpty(attendanceTimeOfMonthlys)) {
			for (AttendanceTimeOfMonthly attendanceTimeOfMonthly : attendanceTimeOfMonthlys) {
				MonthlyRecordToAttendanceItemConverter monthly = attendanceItemConvertFactory.createMonthlyConverter();
					monthly.withAttendanceTime(attendanceTimeOfMonthly);
				if (!CollectionUtil.isEmpty(anyItems)){
					monthly.withAnyItem(anyItems);
					monthlyRecords.add(MonthlyRecordValueImport.of(yearMonth, attendanceTimeOfMonthly.getClosureId(),
							attendanceTimeOfMonthly.getClosureDate(), monthly.convert(attendanceIds)));
				}
				
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
				return monthlyRecord.getItemValues().stream().map(iv -> getValue(iv))
						.collect(Collectors.toList());
			});
			results.put(employeeID+yearMonth.toString()+closureID.toString(),check==true ? 1:0);
		}
		return results;
	}

	private Integer getValue(ItemValue value) {
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
}
