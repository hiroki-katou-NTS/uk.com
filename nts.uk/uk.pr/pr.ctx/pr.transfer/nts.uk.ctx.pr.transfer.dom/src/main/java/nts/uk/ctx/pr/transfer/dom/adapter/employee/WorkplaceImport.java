package nts.uk.ctx.pr.transfer.dom.adapter.employee;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 所属職場
 */
@Value
@AllArgsConstructor
public class WorkplaceImport {

    /**
     * The workplace id.
     */
    private String workplaceId; // 職場ID

    /**
     * The workplace code.
     */
    private String workplaceCode; //職場コード

    /**
     * The workplace generic name.
     */
    private String workplaceGenericName; //職場総称

    /**
     * The workplace name.
     */
    private String workplaceName; //職場表示名
}
