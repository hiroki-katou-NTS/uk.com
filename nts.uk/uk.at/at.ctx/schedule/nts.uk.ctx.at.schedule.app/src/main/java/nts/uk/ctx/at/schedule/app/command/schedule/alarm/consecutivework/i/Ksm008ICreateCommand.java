package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.i;

import lombok.Data;

import java.util.List;

/**
 * @Author Md Rafiqul Islam
 */

@Data
public class Ksm008ICreateCommand {

    /**
     * コード
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 日数
     */
    private Ksm008IICreateMaxDaysOfConsecutiveWorkTime maxDaysContiWorktime;

    @Data
    public static class Ksm008IICreateMaxDaysOfConsecutiveWorkTime {

        /**
         * 就業時間帯コードリスト
         */
        private List<String> workTimeCodes;

        /**
         * 日数
         */
        private Integer numberOfDays;
    }
}

