package nts.uk.screen.at.app.kwr004.b;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;

import java.util.List;

@Data
@AllArgsConstructor
public class OutputItemDetailAttItemDto {

    //	演算子
    private int operator ;

    // 	勤怠項目ID
    private int attendanceItemId;

}
