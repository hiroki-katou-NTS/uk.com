package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.j;

import lombok.Data;

import java.util.List;

/**
 * KSM008J:組織の就業時間帯の連続勤務できる上限日数を更新する
 *
 * @author rafiqul.islam
 */

@Data
public class Ksm008JUpdateWorkTimeOrgCommand {
    /**
     * 職場グループ
     */
    private int workPlaceUnit;

    /**
     * ID
     */
    private String workPlaceId;

    /**
     * 入力対象（項目コード）
     */
    private String workPlaceGroup;

    /**
     * コード
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 就業時間帯コードリスト
     */
    private List<String> workTimeCodes;

    /**
     * 日数
     */
    private Integer numberOfDays;
}
