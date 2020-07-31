package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
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
@Setter
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

    public WorkCycle(){};

    /**
     * [C-1] 作る
     * @param 会社ID cid
     * @param コード code
     * @param 名称 name
     * @param 勤務情報リスト infos
     */
    public WorkCycle(String cid, String code, String name, List<WorkCycleInfo> infos) {
        this.cid = cid;
        this.code = new WorkCycleCode(code);
        this.name = new WorkCycleName(name);
        if (infos.size() < 1 || infos.size() > 99) {
            throw new BusinessException("Msg_1688");
        } else {
            this.infos = infos;
        }
    }

    /**
     * 	[1] 勤務情報を取得する
     * @param 位置 position
     * @param スライド日数 slideDays
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
     * @param 	位置 position
     * @return 	勤務情報
     */
    private WorkCycleInfo getWorkInfoByPosition(int position) {
        boolean endLoop = false;
        while (endLoop) {
            for (WorkCycleInfo info : this.getInfos()) {
                if (position < info.getDays().v()) {
                    endLoop = true;
                    return info;
                }
                position = position - info.getDays().v();
            }
        }
        return null;
    }

    /**
     * [prv-2] 総日数を計算する
     * @return 勤務情報リスト：sum $.日数
     */
    private int calculateTotalDays() {
        if (this.infos.size() > 0)
            return this.infos.stream().mapToInt(i -> i.getDays().v()).sum();
        return 0;
    }

}
