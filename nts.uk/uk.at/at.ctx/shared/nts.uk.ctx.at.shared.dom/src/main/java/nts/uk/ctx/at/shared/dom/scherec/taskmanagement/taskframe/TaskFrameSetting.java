package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 作業枠設定
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).作業管理.作業枠
 */

@Getter
@AllArgsConstructor
public class TaskFrameSetting extends DomainObject {
    // 作業枠NO
    private TaskFrameNo taskFrameNo;

    // 作業枠名
    private TaskFrameName taskFrameName;

    // 利用区分
    private NotUseAtr useAtr;

    public TaskFrameSetting(int frameNo, String frameName, int useAtr) {
        this.taskFrameNo = new TaskFrameNo(frameNo);
        this.taskFrameName = new TaskFrameName(frameName);
        this.useAtr = EnumAdaptor.valueOf(useAtr, NotUseAtr.class);
    }
}
