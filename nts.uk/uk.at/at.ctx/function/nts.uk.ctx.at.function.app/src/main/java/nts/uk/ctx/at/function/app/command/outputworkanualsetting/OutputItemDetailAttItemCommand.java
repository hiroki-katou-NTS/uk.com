package nts.uk.ctx.at.function.app.command.outputworkanualsetting;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OutputItemDetailAttItemCommand {

    //	演算子
    private int operator ;

    // 	勤怠項目ID
    private int attendanceItemId;

}
