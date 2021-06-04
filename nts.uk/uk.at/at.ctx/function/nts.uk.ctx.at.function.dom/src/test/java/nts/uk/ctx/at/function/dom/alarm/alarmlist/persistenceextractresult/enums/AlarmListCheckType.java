package nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
 enum AlarmListCheckType {
    /**固定チェック	 */
    FixCheck(0),
    /**自由チェック	 */
    FreeCheck(1),
    /**36法定	 */
    Legal(2),
    /**36超過	 */
    Excess(3);
    public final int value;
}
