package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseItemsDto {
	private List<BaseItem> dailyAttItems;
	
	private List<BaseItem> scheduleItems;
	
	private List<BaseItem> externalItems;
}

@Data
class BaseItem {
	/* 会社ID */
    private String companyId;
    
    /* Id */
    private int id;
    
    /* 項目Id */
    private String itemId;
    
    /* 項目名 */
    private String itemName;
    
    /* アイテムタイプ */
    private int itemType;
    
    /* 順番 */
    private int dispOrder;
}