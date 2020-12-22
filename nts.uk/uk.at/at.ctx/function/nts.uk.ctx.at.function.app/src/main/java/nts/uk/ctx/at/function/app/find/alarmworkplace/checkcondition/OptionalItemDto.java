package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionalItemDto {

    private int checkItem;

    private int no;

    private String conditionName;

    private String message;

    private boolean useAtr;

}
