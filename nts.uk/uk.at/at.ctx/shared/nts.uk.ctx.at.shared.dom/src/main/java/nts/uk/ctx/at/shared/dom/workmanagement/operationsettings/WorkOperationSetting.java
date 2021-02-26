package nts.uk.ctx.at.shared.dom.workmanagement.operationsettings;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * AggregateRoot: 作業運用設定
 * Ea: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.運用設定.作業運用設定.
 * @author : chinh.hm
 */
@Getter
public class WorkOperationSetting extends AggregateRoot {
    // 作業運用方法
    private WorkOperationMethod workOperationMethod;

    public WorkOperationSetting(int operationMethod) {
        this.workOperationMethod = EnumAdaptor.valueOf(operationMethod, WorkOperationMethod.class);
    }
}
