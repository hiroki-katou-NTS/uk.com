package nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.k;

import lombok.Data;

import java.util.List;

/**
 * @Author Md Rafiqul Islam
 */

@Data
public class Ksm008KUpdateCommand {

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
    private Integer maxDay;
}

