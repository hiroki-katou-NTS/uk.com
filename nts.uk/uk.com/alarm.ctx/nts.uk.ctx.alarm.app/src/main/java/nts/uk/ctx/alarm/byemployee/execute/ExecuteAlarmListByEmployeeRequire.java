package nts.uk.ctx.alarm.byemployee.execute;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.AlarmListPatternCode;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.daily.DailyCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.master.FixLogicMasterByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.master.MasterCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthly;
import nts.uk.ctx.alarm.dom.byemployee.execute.ExecuteAlarmListByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.CheckConditionKey;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExecuteAlarmListByEmployeeRequire {

    public Require create() {
        return EmbedStopwatch.embed(new RequireImpl(AppContexts.user().companyId()));
    }

    public interface Require extends ExecuteAlarmListByEmployee.Require {

    }

    @RequiredArgsConstructor
    public class RequireImpl implements Require {

    	private final String companyId;
    	
    	@Inject
    	private WorkTypeRepository workTypeRepo;
    	
    	@Inject
    	private WorkingConditionRepository workingConditionRepo;
    	
        @Override
        public Optional<AlarmListPatternByEmployee> getAlarmListPatternByEmployee(AlarmListPatternCode patternCode) {
        	CheckingPeriod period = new CheckingPeriod(new CheckingPeriodMonthly(), new CheckingPeriodDaily(), new CheckingPeriodMonthly());
        	List<CheckConditionKey> keys = Arrays.asList(new CheckConditionKey(AlarmListCategoryByEmployee.MASTER, 
        																														new AlarmListCheckerCode("20")));
        	return Optional.of(new AlarmListPatternByEmployee(this.companyId, patternCode, keys, period));
//            return Optional.empty();
        }

        @Override
        public Optional<AlarmListCheckerByEmployee> getAlarmListChecker(AlarmListCategoryByEmployee category, AlarmListCheckerCode checkerCode) {
        	return Optional.of(new MasterCheckerByEmployee(
        			Arrays.asList(new FixedLogicSetting<>(FixLogicMasterByEmployee.平日時勤務種類確認, true, "メメメ")))
        			);
//            return Optional.empty();
        }

		@Override
		public void save(AlarmRecordByEmployee alarmRecord) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void save(List<AlarmRecordByEmployee> alarmRecords) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Optional<IntegrationOfDaily> getIntegrationOfDaily(String employeeId, GeneralDate date) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean existsWorkType(WorkTypeCode workTypeCode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean existsWorkTime(WorkTimeCode workTimeCode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<WorkingConditionItemWithPeriod> getWorkingConditions(String employeeId, DatePeriod period) {
			return workingConditionRepo.getWorkingConditionItemWithPeriod(this.companyId, Arrays.asList(employeeId), period);
		}

		@Override
		public Optional<WorkType> get(String workTypeCode) {
			return workTypeRepo.findByPK(this.companyId, workTypeCode);
		}
    }
}
