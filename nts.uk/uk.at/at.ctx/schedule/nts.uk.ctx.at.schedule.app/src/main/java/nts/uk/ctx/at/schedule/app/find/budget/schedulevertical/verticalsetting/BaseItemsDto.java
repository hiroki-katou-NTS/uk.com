package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseItemsDto {
	/** 会社ID */
    private String companyId;
    
    /** Id */
    private String id;
    
    /** 項目Id */
    private String itemId;
    
    /** 項目名 */
    private String itemName;
    
    /** アイテムタイプ */
    private int itemType;
    
    /** 順番 */
    private int dispOrder;
}