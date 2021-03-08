package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe;


import lombok.Getter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameSetting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AggregateRoot : 作業枠利用設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業枠.作業枠利用設定
 */
@Getter
public class TaskFrameUsageSetting extends AggregateRoot {
    // 枠設定 : 作業枠設定
    private List<TaskFrameSetting> frameSettingList;

    public TaskFrameUsageSetting(List<TaskFrameSetting> frameSettingList) {

        this.frameSettingList = frameSettingList;
        // [inv-1]  case @枠設定.作業枠NO　は重複してはいけない
        List<Integer> taskFrameNo = frameSettingList.stream().map(e -> e.getTaskFrameNo().v()).collect(Collectors.toList());
        List<Integer> dps = taskFrameNo.stream().distinct()
                .filter(entry -> Collections.frequency(taskFrameNo, entry) > 1).collect(Collectors.toList());

        if (!dps.isEmpty()) {
            throw new BusinessException("Msg_2064");
        }
        // [inv-3]	case @枠設定 : 昇順($.作業枠NO)
        Comparator<TaskFrameSetting> compareByTaskFrameNo = Comparator.comparing(o -> o.getTaskFrameNo().v());
        frameSettingList.sort(compareByTaskFrameNo);

        // [inv-2] 	case 上位枠の「@枠設定.利用区分 = しない」の場合、「@枠設定.利用区分 = する」に設定できない
        for (int i = frameSettingList.size() - 1; i >= 1; i--) {
            val upper = frameSettingList.get(i - 1);
            val lower = frameSettingList.get(i);
            if (upper.getUseAtr() == UseAtr.USE) continue;
            if (lower.getUseAtr() == UseAtr.USE)
                throw new BusinessException("Msg_2063", lower.getTaskFrameName().v(), upper.getTaskFrameName().v());
        }
    }

}

