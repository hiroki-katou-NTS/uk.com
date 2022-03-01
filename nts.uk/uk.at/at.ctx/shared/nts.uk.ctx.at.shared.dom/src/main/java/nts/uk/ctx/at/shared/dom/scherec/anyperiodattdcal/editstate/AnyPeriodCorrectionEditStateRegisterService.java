package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate;

import nts.arc.task.tran.AtomTask;

import java.util.ArrayList;
import java.util.List;

/**
 * 任意期間修正の編集状態を登録する
 */
public class AnyPeriodCorrectionEditStateRegisterService {
    /**
     * 登録する
     * @param require
     * @param editingStates 編集状態リスト
     * @return
     */
    public static List<AtomTask> register(Require require, List<AnyPeriodCorrectionEditingState> editingStates) {
        List<AtomTask> tasks = new ArrayList<>();
        editingStates.forEach(state -> {
            tasks.add(AtomTask.of(() -> {
                require.persist(state);
            }));
        });
        return tasks;
    }

    public interface Require {
        void persist(AnyPeriodCorrectionEditingState state);
    }
}
