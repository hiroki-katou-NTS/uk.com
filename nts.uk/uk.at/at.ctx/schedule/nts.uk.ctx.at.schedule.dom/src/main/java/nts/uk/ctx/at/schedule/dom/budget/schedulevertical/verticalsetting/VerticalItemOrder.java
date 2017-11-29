package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** *
 * TanLV
 * 汎用縦計項目の並び順
 */
@AllArgsConstructor
@Getter
public class VerticalItemOrder {
	/**  会社ID */
    private String companyId;
    
    /** コード*/
    private String verticalCalCd;
    
    /**  汎用縦計項目ID */
    private String itemId;
    
    /**  並び順 */
    private int dispOrder;
}
