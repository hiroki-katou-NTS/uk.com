package nts.uk.ctx.at.record.dom.reservation.bentoReservationSetting;

import lombok.AllArgsConstructor;

/**
 * 注文済みデータ区分
 */

@AllArgsConstructor
public enum OrderedData {

    /** Cannot be corrected. */
    // 修正不可
    CANNOT_CORRECTED(0, "修正不可"),

    /** Possible correction. */
    // 修正可能
    POSSIBLE_CORRECTION(1, "修正可能");

    public final int value;
    public final String name;
}
