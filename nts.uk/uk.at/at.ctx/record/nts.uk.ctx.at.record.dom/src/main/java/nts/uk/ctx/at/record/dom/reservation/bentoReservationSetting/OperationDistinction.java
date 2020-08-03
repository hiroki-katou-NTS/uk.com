package nts.uk.ctx.at.record.dom.reservation.bentoReservationSetting;

import lombok.AllArgsConstructor;

/**
 * 予約の運用区別
 */
@AllArgsConstructor
public enum OperationDistinction {

    /** The by company. */
    // 会社別
    BY_COMPANY(0, "会社別"),

    /** The by location. */
    // 場所別
    BY_LOCATION(1, "場所別");

    public final int value;
    public final String name;


    private final static OperationDistinction[] values = OperationDistinction.values();

    public static OperationDistinction valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (OperationDistinction val : OperationDistinction.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }

}
