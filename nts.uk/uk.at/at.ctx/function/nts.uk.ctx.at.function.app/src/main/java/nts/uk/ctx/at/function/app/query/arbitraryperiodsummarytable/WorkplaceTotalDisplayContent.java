package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 職場計表示内容
 */
@Data
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

    // 職場計一覧
    private List<DisplayContent> listOfWorkplaces;
}
