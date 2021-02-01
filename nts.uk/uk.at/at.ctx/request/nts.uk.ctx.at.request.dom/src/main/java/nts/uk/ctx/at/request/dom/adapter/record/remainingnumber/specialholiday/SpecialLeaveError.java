package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import lombok.AllArgsConstructor;

/**
 * 特別休暇エラー
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum SpecialLeaveError {
    /**
     * 日単位特別休暇不足エラー(付与前)
     */
    DAY_SHORTAGE_ERROR_BEFORE_GRANT(0),
    /**
     * 日単位特別休暇不足エラー(付与後)
     */
    DAY_SHORTAGE_ERROR_AFTER_GRANT(1),
    /**
     * 時間特別休暇不足エラー(付与前)
     */
    TIME_SHORTAGE_ERROR_BEFORE_GRANT(2),
    /**
     * 時間特別休暇不足エラー(付与後)
     */
    TIME_SHORTAGE_ERROR_AFTER_GRANT(3);
    public final int value;
}
