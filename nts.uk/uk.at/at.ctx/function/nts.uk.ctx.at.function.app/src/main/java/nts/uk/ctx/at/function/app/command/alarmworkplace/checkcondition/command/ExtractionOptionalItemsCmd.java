package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.command;

import lombok.Data;

import java.util.List;

@Data
public class ExtractionOptionalItemsCmd {

    private String id;

    private int no;

    private int checkItem;

    private int useAtr;

    private String name;

    private String minValue;

    private String maxValue;

    private String message;

    private int operator;

    private String checkCond;

    private Integer checkCondB;

    private List<Integer> additionAttendanceItems;

    private List<Integer> substractionAttendanceItems;

}
