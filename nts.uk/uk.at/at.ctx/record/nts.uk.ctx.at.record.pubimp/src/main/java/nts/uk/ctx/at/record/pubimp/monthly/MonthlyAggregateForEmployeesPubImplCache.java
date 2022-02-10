package nts.uk.ctx.at.record.pubimp.monthly;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;

@Setter
@Getter
public class MonthlyAggregateForEmployeesPubImplCache {
	Map<String, WorkInfoOfDailyPerformance> workInfoOfDailyPerformanceMap = new ConcurrentHashMap<String, WorkInfoOfDailyPerformance>();
	Map<String, TimeLeavingOfDailyPerformance> timeLeavingOfDailyPerformanceMap = new ConcurrentHashMap<String, TimeLeavingOfDailyPerformance>();
	Map<String, TemporaryTimeOfDailyPerformance> temporaryTimeOfDailyPerformanceMap = new ConcurrentHashMap<String, TemporaryTimeOfDailyPerformance>();
	Map<String, SpecificDateAttrOfDailyPerfor> specificDateAttrOfDailyPerforMap = new ConcurrentHashMap<String, SpecificDateAttrOfDailyPerfor>();
	Map<String, EmployeeDailyPerError> employeeDailyPerErrorMap = new ConcurrentHashMap<String, EmployeeDailyPerError>();
	Map<String, AnyItemValueOfDaily> anyItemValueOfDailyMap= new ConcurrentHashMap<String, AnyItemValueOfDaily>();
	Map<String, PCLogOnInfoOfDaily> pCLogOnInfoOfDailyMap= new ConcurrentHashMap<String, PCLogOnInfoOfDaily>();
	Map<String, AffiliationInforOfDailyPerfor> affiliationInforOfDailyPerforMap= new ConcurrentHashMap<String, AffiliationInforOfDailyPerfor>();


	
}

