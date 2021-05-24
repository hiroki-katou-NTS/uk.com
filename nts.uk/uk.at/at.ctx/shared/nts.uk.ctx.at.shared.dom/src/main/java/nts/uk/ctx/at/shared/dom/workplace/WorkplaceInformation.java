package nts.uk.ctx.at.shared.dom.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 職場情報
 */

@Getter
@AllArgsConstructor
public class WorkplaceInformation {

    /**
     * 会社ID
     */
    public String companyId;

    /**
     * 削除フラグ
     */
    public boolean deleteFlag;

    /**
     * 職場履歴ID
     */
    public String workplaceHistoryId;

    /**
     * 職場ID
     */
    public String workplaceId;

    /**
     * 職場コード
     */
    public WorkplaceCode workplaceCode;

    /**
     * 職場名称
     */
    public WorkplaceName workplaceName;

    /**
     * 職場総称
     */
    public WorkplaceGeneric workplaceGeneric;

    /**
     * 職場表示名
     */
    public WorkplaceDisplayName workplaceDisplayName;

    /**
     * 階層コード
     */
    public WorkplaceHierarchyCode hierarchyCode;

    /**
     * 職場外部コード
     */
    private Optional<WorkplaceExternalCode> workplaceExternalCode;

    public WorkplaceInformation(String companyId, boolean deleteFlag, String historyId, String workplaceId,
                                String workplaceCode, String workplaceName, String workplaceGeneric, String workplaceDisplayName,
                                String hierarchyCode, String externalCode) {
        this.companyId = companyId;
        this.deleteFlag = deleteFlag;
        this.workplaceHistoryId = historyId;
        this.workplaceId = workplaceId;
        this.workplaceCode = new WorkplaceCode(workplaceCode);
        this.workplaceName = new WorkplaceName(workplaceName);
        this.workplaceGeneric = new WorkplaceGeneric(workplaceGeneric);
        this.workplaceDisplayName = new WorkplaceDisplayName(workplaceDisplayName);
        this.hierarchyCode = new WorkplaceHierarchyCode(hierarchyCode);
        this.workplaceExternalCode = externalCode == null || externalCode.isEmpty() ? Optional.empty()
                : Optional.of(new WorkplaceExternalCode(externalCode));
    }
}
