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

    private Integer level;

    public AttendanceDetailDisplayContents (String workplaceId,String workplaceCd,String workplaceName,
                                            String hierarchyCode,List<DisplayedEmployee> listDisplayedEmployees){
        this.workplaceCd = workplaceCd;
        this.workplaceId = workplaceId;
        this.workplaceName = workplaceName;
        this.hierarchyCode = hierarchyCode;
        this.listDisplayedEmployees = listDisplayedEmployees;
        this.level =   hierarchyCode != null ? hierarchyCode.length() / 3 : 0;
    }

}
