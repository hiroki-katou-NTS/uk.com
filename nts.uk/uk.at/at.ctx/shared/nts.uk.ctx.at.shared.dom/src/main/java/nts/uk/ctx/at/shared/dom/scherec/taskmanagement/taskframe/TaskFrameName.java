package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 作業枠名
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).作業管理.作業枠
 */

@StringMaxLength(12)
public class TaskFrameName extends StringPrimitiveValue<TaskFrameName> {
    public TaskFrameName(String rawValue) {
        super(rawValue);
    }
}
