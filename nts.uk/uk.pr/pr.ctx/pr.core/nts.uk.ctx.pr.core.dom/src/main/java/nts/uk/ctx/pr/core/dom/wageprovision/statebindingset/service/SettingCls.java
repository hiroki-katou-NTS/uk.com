package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.service;

public enum SettingCls {
    // 個人
    PERSON(0),
    // 雇用
    EMPLOYEE(1),
    // 部門
    DEPARMENT(2),
    // 分類
    CLASSIFICATION(3),
    // 職位
    POSITION(4),
    // 給与分類
    SALARY(5),
    // 会社
    COMPANY(6);
    /**
     * The value.
     */
    public final int value;

    private SettingCls(int value) {
        this.value = value;
    }
}
