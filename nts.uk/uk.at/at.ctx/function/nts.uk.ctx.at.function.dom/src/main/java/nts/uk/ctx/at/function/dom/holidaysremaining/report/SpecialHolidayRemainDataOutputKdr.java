package nts.uk.ctx.at.function.dom.holidaysremaining.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpecialHolidayRemainDataOutputKdr {
    /**	社員ID */
    private String sid;
    /**	 年月*/
    private YearMonth ym;
    /**	特別休暇コード */
    private int specialHolidayCd;
    /**	特別休暇月別残数データ．特別休暇．使用数．使用日数 */
    private double useDays;
    /**	特別休暇月別残数データ．特別休暇．使用数．使用日数．特別休暇使用日数付与前 */
    private double beforeUseDays;
    /**	特別休暇月別残数データ．特別休暇．使用数．使用日数． 特別休暇使用日数付与後*/
    private double afterUseDays;
    /**	特別休暇月別残数データ．特別休暇．使用数．使用時間．使用時間*/
    private int useTimes;
    /**	特別休暇月別残数データ．特別休暇．使用数．使用時間． 使用時間付与前*/
    private int beforeUseTimes;
    /**	特別休暇月別残数データ．特別休暇．使用数．使用時間 ．使用時間付与後*/
    private int afterUseTimes;
    /**	特別休暇月別残数データ．特別休暇．使用数．使用時間: 使用回数 */
    private int useNumber;
    /**	特別休暇月別残数データ．実特別休暇．使用数．使用日数 .実特別休暇使用日数*/
    private double remainDays;
    /**	特別休暇月別残数データ．特別休暇．残数.時間 */
    private int remainTimes;

    private double beforeRemainDays;
    /**	特別休暇月別残数データ．特別休暇．残数付与前.時間 */
    private int beforeRemainTimes;

    /**特別休暇月別残数データ．特別休暇．残数付与後.日数	 */
    private double afterRemainDays;
    /**	特別休暇月別残数データ．特別休暇．残数付与後.時間 */
    private int afterRemainTimes;

}
