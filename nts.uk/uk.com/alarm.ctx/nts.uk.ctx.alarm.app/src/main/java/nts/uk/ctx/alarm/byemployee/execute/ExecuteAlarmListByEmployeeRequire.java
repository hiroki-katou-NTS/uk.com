package nts.uk.ctx.alarm.byemployee.execute;

import lombok.Getter;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.AlarmListPatternCode;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.execute.ExecuteAlarmListByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternByEmployee;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExecuteAlarmListByEmployeeRequire {

    public Require create() {
        return EmbedStopwatch.embed(new RequireImpl());
    }

    public interface Require extends ExecuteAlarmListByEmployee.Require {

        // テスト用
        List<AlarmRecordByEmployee> getAlarms();
    }

    public class RequireImpl implements Require {

        @Getter
        private List<AlarmRecordByEmployee> alarms = new ArrayList<>();

        @Override
        public Optional<AlarmListPatternByEmployee> getAlarmListPatternByEmployee(AlarmListPatternCode patternCode) {
            return Optional.empty();
        }

        @Override
        public Optional<AlarmListCheckerByEmployee> getAlarmListChecker(AlarmListCategoryByEmployee category, AlarmListCheckerCode checkerCode) {
            return Optional.empty();
        }

        @Override
        public void save(AlarmRecordByEmployee alarmRecord) {
            alarms.add(alarmRecord);
        }

        @Override
        public void save(List<AlarmRecordByEmployee> alarmRecords) {
            alarms.addAll(alarmRecords);
        }

        @Override
        public Optional<IntegrationOfDaily> getIntegrationOfDaily(String employeeId, GeneralDate date) {
            return Optional.empty();
        }

        @Override
        public boolean existsWorkType(WorkTypeCode workTypeCode) {
            return false;
        }

        @Override
        public boolean existsWorkTime(WorkTimeCode workTimeCode) {
            return false;
        }
    }
}
