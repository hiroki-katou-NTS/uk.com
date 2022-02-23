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
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 任意期間別実績と編集状態を登録する
 */
@Stateless
public class AnyPeriodResultRegisterAndStateEditService {
    @Inject
    private AttendanceTimeOfAnyPeriodRepository anyPeriodRepository;

    @Inject
    private AnyPeriodActualResultCorrectionService correctionService;

    @Inject
    private AnyPeriodCorrectionEditStateCreateService editStateCreateService;

    @Inject
    private AnyPeriodCorrectionEditStateRegisterService editStateRegisterService;

    @Inject
    private AnyPeriodEditingStateRepository anyPeriodEditingStateRepo;

    /**
     * 登録する
     * @param require
     * @param anyPeriodTotalFrameCode 任意集計枠コード
     * @param correctingEmployeeId 修正者
     * @param targetEmployeeId 対象社員
     * @param items 編集項目値リスト
     * @return 任意期間実績の登録内容
     */
    public AnyPeriodResultRegistrationDetail register(Require require, String anyPeriodTotalFrameCode, String correctingEmployeeId, String targetEmployeeId, List<ItemValue> items) {
        List<AtomTask> processes = new ArrayList<>();
        AnyPeriodActualResultCorrectionDetail attdTime = correctionService.create(
                new AnyPeriodActualResultCorrectionService.Require() {
                    @Override
                    public Optional<AttendanceTimeOfAnyPeriod> find(String employeeId, String frameCode) {
                        return anyPeriodRepository.find(employeeId, frameCode);
                    }
                },
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
            List<AnyPeriodCorrectionEditingState> editingStates = editStateCreateService.create(
                    anyPeriodTotalFrameCode,
                    correctingEmployeeId,
                    targetEmployeeId,
                    editedItems
            );

            processes.addAll(editStateRegisterService.register(
                    new AnyPeriodCorrectionEditStateRegisterService.Require() {
                        @Override
                        public void persist(AnyPeriodCorrectionEditingState state) {
                            anyPeriodEditingStateRepo.persist(state);
                        }
                    }, editingStates
            ));

            return new AnyPeriodResultRegistrationDetail(processes, attdTime);
        }
        return new AnyPeriodResultRegistrationDetail(new ArrayList<>(), attdTime);
    }

    public interface Require {
        void update(AttendanceTimeOfAnyPeriod attendanceTime);
    }
}
