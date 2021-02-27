package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.List;

/**
 * 作業枠利用設定
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).作業管理.作業枠
 */

@Getter
@AllArgsConstructor
public class TaskFrameUsageSetting extends AggregateRoot {
    // 枠設定
    private List<TaskFrameSetting> frameSettings;
}
