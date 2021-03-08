package nts.uk.ctx.at.shared.dom.taskmanagement.aggregateroot.taskframe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;

/**
 * 作業枠設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業枠.作業枠設定
 *
 * @author chinh.hm
 */
@AllArgsConstructor
@Getter
public class TaskFrameSetting {
    // 作業枠NO
    private TaskFrameNo taskFrameNo;

    // 作業枠名
    private TaskFrameName taskFrameName;

    // するしない区分
    private UseAtr useAtr;

}
