package nts.uk.screen.at.app.schedule.basicschedule;

import java.util.List;

public class ScheduleBasicSettingDto {
    /** 通常勤務の利用区分 */
    public int changeableFix;

    /** 流動勤務の利用区分 */
    public int changeableFluid;

    /** フレックス勤務の利用区分 */
    public int changeableFlex;

    /** 勤務種類制御の利用区分 */
    private int displayWorkTypeControl;

    /** 勤務種類名称リスト*/
    private List<String> displayableWorkTypeCodeList;

    /** 実績表示の利用区分 */
    private int displayActual;
}
