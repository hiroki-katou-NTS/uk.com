package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisplayContenteEmployeeInfor {
    // 社員コード
    private int employeeCode;

    // 社員名
    private String employeeName;

    // 職場コード
    private int workPlaceCode;

    // 職場名
    private String workPlaceName;
}
