/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement;

import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;

/**
 * 超過状態チェックする条件
 *
 * @author raiki_asada
 */
@AllArgsConstructor
public abstract class ExcessStateChecker<V> {
    private final Set<ExcessState> targets;
    protected final MessageForAlarm message;

    public ResultOfChecker check(Threshold<V> threhsold, V targetTimes) {
        ExcessState state = threhsold.check(targetTimes);

        if (!this.targets.contains(state)) {
            return ResultOfChecker.empty();
        }

        return this.createResult(threhsold, targetTimes, state);
    }

    // 対象とする項目、閾値、３６協定時間、超過状態
    // これだけあれば、何でも出せるやろ
    protected abstract ResultOfChecker createResult(Threshold<V> threhsold, V targetTimes, ExcessState state);
    
    public interface ResultOfChecker {

        static ResultOfChecker empty() {
            return (employeeId, date, item) -> Optional.empty();
        }

        Optional<AlarmRecordByEmployee> getRecord(String employeeId, DateInfo date, TargetOfAlarm item);
    }
}
