package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodCorrectionEditStateCreateService;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodCorrectionEditStateRegisterService;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodCorrectionEditingState;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodEditingStateRepository;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任意期間別実績と編集状態を登録する
 */
public class AnyPeriodResultRegisterAndStateEditService {
    /**
     * 登録する
     * @param require
     * @param anyPeriodTotalFrameCode 任意集計枠コード
     * @param correctingEmployeeId 修正者
     * @param targetEmployeeId 対象社員
     * @param items 編集項目値リスト
     * @return 任意期間実績の登録内容
     */
    public static AnyPeriodResultRegistrationDetail register(Require require, String anyPeriodTotalFrameCode, String correctingEmployeeId, String targetEmployeeId, List<ItemValue> items) {
        List<AtomTask> processes = new ArrayList<>();
        AnyPeriodActualResultCorrectionDetail attdTime = AnyPeriodActualResultCorrectionService.create(
                require,
                anyPeriodTotalFrameCode,
                targetEmployeeId,
                items
        );

        if (attdTime.getAfterCalculation() != null) {
            AtomTask task = AtomTask.of(() -> {
                require.update(attdTime.getAfterCalculation());
            });
            processes.add(task);

            List<Integer> editedItems = items.stream().map(ItemValue::getItemId).distinct().collect(Collectors.toList());
            List<AnyPeriodCorrectionEditingState> editingStates = AnyPeriodCorrectionEditStateCreateService.create(
                    anyPeriodTotalFrameCode,
                    correctingEmployeeId,
                    targetEmployeeId,
                    editedItems
            );

            processes.addAll(AnyPeriodCorrectionEditStateRegisterService.register(
                    require,
                    editingStates
            ));

            return new AnyPeriodResultRegistrationDetail(processes, attdTime);
        }
        return new AnyPeriodResultRegistrationDetail(new ArrayList<>(), attdTime);
    }

    public interface Require extends AnyPeriodActualResultCorrectionService.Require, AnyPeriodCorrectionEditStateRegisterService.Require {
        void update(AttendanceTimeOfAnyPeriod attendanceTime);
    }
}
