package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * 職場計表示内容
 */
@Getter
@AllArgsConstructor
public class WorkplaceTotalDisplayContent {
    // 職場ID
    private String workplaceId;

    // 職場コード
    private String workplaceCode;

    // 職場名称
    private String workplaceName;

    // 職場名称
    private String hierarchyCode;

    private int level;
    // 職場計一覧
    private List<DisplayContent> listOfWorkplaces;

    public WorkplaceTotalDisplayContent(String workplaceId, String workplaceCode,
                                        String workplaceName, String hierarchyCode,
                                        List<DisplayContent> listOfWorkplaces) {
        this.workplaceId = workplaceId;
        this.workplaceCode = workplaceCode;
        this.workplaceName = workplaceName;
        this.hierarchyCode = hierarchyCode;
        this.listOfWorkplaces = listOfWorkplaces;
        this.level = hierarchyCode.length() / 3;
    }
}
