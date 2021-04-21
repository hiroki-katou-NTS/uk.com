package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import nts.arc.enums.EnumAdaptor;

public enum PeriodType {
    DATE(1),
    YM(2),
    MONTH(3);

    public final int value;

    private PeriodType(int value) {
        this.value = value;
    }

    public static PeriodType of(int value) {
        return EnumAdaptor.valueOf(value, PeriodType.class);
    }
}
