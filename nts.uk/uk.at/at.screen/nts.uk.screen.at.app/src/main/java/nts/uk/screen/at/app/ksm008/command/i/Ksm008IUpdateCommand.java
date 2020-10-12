package nts.uk.screen.at.app.ksm008.command.i;

import lombok.Data;

import java.util.List;

/**
 * @Author Md Rafiqul Islam
 */

@Data
public class Ksm008IUpdateCommand {

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
    private Ksm008IUpdateMaxDaysOfConsecutiveWorkTime maxDaysContiWorktime;
}

@Data
class Ksm008IUpdateMaxDaysOfConsecutiveWorkTime {

    /**
     * 就業時間帯コードリスト
     */
    private List<String> workTimeCodes;

    /**
     * 日数
     */
    private Integer numberOfDays;
}