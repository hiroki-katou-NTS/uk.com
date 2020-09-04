package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import lombok.*;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;

import java.util.List;
import java.util.stream.Collectors;

/*
    勤務サイクル
 */
@Getter
@AllArgsConstructor
public class WorkCycle extends AggregateRoot {

    /*
        会社ID
     */
    private String cid;

    /*
        勤務サイクルコード
     */
    private WorkCycleCode code;

    /*
        勤務サイクル名称
     */
    private WorkCycleName name;

    /*
        勤務サイクルの勤務情報
     */
    private List<WorkCycleInfo> infos;

    /**
     * [C-1] 作る
     */
    public static WorkCycle create(String cid, String code, String name, List<WorkCycleInfo> infos) {
        if (infos.size() < 1 || infos.size() > 99) {
            throw new BusinessException("Msg_1688");
        }
        return new WorkCycle(cid, new WorkCycleCode(code), new WorkCycleName(name), infos );
    }


    /**
     * 	[1] 勤務情報を取得する
     * @return 勤務情報
     */
    public WorkCycleInfo getWorkInfo(int position, int slideDays) {
        position = position - slideDays;
        if (position < 1) {
            position += this.calculateTotalDays();
        }
        return this.getWorkInfoByPosition(position);
    }

    /**
     *
     * @param require
     * @return 	List<勤務情報のエラー状態>
     */
    public List<ErrorStatusWorkInfo> checkError(WorkInformation.Require require) {
        val listWorkInfo = this.getInfos().stream().map(i -> i.getWorkInformation().checkErrorCondition(require)).collect(Collectors.toList());
        return listWorkInfo;
    }


    /**
     * [pvt-1] 指定した位置の勤務情報を取得する
     * @return 	勤務情報
     */
    private WorkCycleInfo getWorkInfoByPosition(int position) {
        while (true) {
            for (WorkCycleInfo info : this.getInfos()) {
                if (position <= info.getDays().v()) {
                    return info;
                }
                position = position - info.getDays().v();
            }
        }
    }

    /**
     * [prv-2] 総日数を計算する
     * @return 勤務情報リスト：sum $.日数
     */
    private int calculateTotalDays() {
            return this.infos.stream().mapToInt(i -> i.getDays().v()).sum();
    }

}
