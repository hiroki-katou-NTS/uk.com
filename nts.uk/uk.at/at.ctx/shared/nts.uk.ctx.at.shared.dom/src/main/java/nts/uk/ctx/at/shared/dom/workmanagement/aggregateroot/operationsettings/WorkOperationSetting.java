package nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.operationsettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * AggregateRoot: 作業運用設定
 * Ea: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.運用設定.作業運用設定.
 * @author : chinh.hm
 */
@AllArgsConstructor
@Getter
public class WorkOperationSetting extends AggregateRoot {
    // 作業運用方法
    private WorkOperationMethod workOperationMethod;
}
