package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;

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
        return this.getWorkInfoByPosition(position - slideDays);
    }

    /**
     *
     * @param require
     * @param companyId
     * @return 	List<勤務情報のエラー状態>
     */
    public List<ErrorStatusWorkInfo> checkError(WorkInformation.Require require, String companyId) {
        val listWorkInfo = this.getInfos().stream()
        		.map(i -> i.getWorkInformation().checkErrorCondition(require, companyId))
        		.collect(Collectors.toList());
        return listWorkInfo;
    }


    /**
     * [pvt-1] 指定した位置の勤務情報を取得する
     * @return 	勤務情報
     */
    private WorkCycleInfo getWorkInfoByPosition(int position) {
    	if (this.infos == null || this.infos.size() == 0) {
    		throw new RuntimeException("Work cycle information doesn't exist.");
		}

        val cloneListInfo = new ArrayList<WorkCycleInfo>(this.infos);

		if (position <= 0) {
            position = Math.abs(position) + 1;
            Collections.reverse(cloneListInfo);
        }

        while (true) {
            for (WorkCycleInfo info : cloneListInfo) {
                if (position <= info.getDays().v()) {
                    return info;
                }
                position = position - info.getDays().v();
            }
        }
    }
}
