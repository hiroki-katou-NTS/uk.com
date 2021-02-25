package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.lateearlycancellation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * refactor4 refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).申請反映処理.申請反映条件.遅刻早退取消申請
 * 遅刻早退取消申請の反映
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LateEarlyCancelReflect extends AggregateRoot {
    private String companyId;

    /**
     * 遅刻早退報告を行った場合はアラームとしない
     */
    private boolean clearLateReportWarning;
}
