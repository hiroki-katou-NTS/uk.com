package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings;


/**
 * Enumeration: 作業運用方法
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.運用設定.作業運用方法
 *
 * @author chinh.hm
 */
public enum TaskOperationMethod {
    // 予定で利用
    USE_ON_SCHEDULE(0),
    // 利用しない
    DO_NOT_USE(1),
    // 実績で利用
    USED_IN_ACHIEVENTS(2);

    public final int value;

    TaskOperationMethod(int value) {
        this.value = value;
    }

}
