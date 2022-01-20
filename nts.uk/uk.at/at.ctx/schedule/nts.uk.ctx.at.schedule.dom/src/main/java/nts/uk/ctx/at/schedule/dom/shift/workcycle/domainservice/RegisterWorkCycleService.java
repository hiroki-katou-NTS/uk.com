package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;

/**
 * 	勤務サイクルを登録する
 */
@Stateless
public class RegisterWorkCycleService {

    /**
     * 	[1] 登録する
     * @param workRequire
     * @param require
     * @param workCycle
     * @param isNewMode
     * @return
     */
    public static WorkCycleCreateResult register(Require require, WorkCycle workCycle, boolean isNewMode) {
        if (isNewMode && require.exists(workCycle.getCid(), workCycle.getCode().v())) {
            throw new BusinessException("Msg_3");
        }
        val listErrorStatus =  workCycle.checkError(require, workCycle.getCid());
        if(listErrorStatus.stream().filter(i -> i.value != ErrorStatusWorkInfo.NORMAL.value).findAny().isPresent()) {
            return new WorkCycleCreateResult(listErrorStatus);
        }
        Optional<AtomTask> atomTask = Optional.of(AtomTask.of(() -> {
            if (isNewMode) {
                require.insert(workCycle);
            } else {
                require.update(workCycle);
            }
        }));
        return new WorkCycleCreateResult(atomTask);
    }

    public static interface Require extends WorkInformation.Require{

        /**
         * [R1] 勤務サイクルが既に登録されているか
         * @param cid
         * @param code
         * @return
         */
        boolean exists(String cid, String code);

        /**
         * [R2] 勤務サイクルを追加する
         * @param item
         */
        void insert(WorkCycle item);

        /**
         * 	[R3] 勤務サイクルを変更する
         * @param item
         */
        void update(WorkCycle item);


    }

}
