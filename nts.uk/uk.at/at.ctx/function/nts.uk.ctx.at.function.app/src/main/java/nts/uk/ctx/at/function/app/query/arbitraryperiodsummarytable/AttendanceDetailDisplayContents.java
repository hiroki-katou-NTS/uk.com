package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.List;

/**
 * 明細表示内容
 */
@Value
@AllArgsConstructor
public class AttendanceDetailDisplayContents {
    //職場ID
    private String workplaceId;
    // 職場コード
    private String workplaceCd;
    // 職場名称
    private String workplaceName;
    //
    private String hierarchyCode;
    // 表示社員一覧
    private List<DisplayedEmployee> listDisplayedEmployees;
}
