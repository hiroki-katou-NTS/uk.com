package nts.uk.ctx.pereg.app.find.layoutdef.classification;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pereg.app.find.person.info.item.ItemTypeStateDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GridEmpHead {
	private String itemId;
	private int itemOrder;
	
	private String itemCode;
	private String itemParentCode;
	private String itemName;
	private ItemTypeStateDto itemTypeState;
	
	private boolean required;
	
	private String resourceId;
	
	private List<GridEmpHead> childs;
} 
