package nts.uk.ctx.hr.notice.app.find.report;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumConstant;
@Getter
public class RegisterItemDto {
	private String id;

	private String perInfoCtgId;
	
	private String itemCode;
	
	private String itemParentCode;

	private String itemName;

	private int isAbolition;

	private int isFixed;

	private int isRequired;

	private int systemRequired;

	private int requireChangable;
	
	private ItemReportTypeStateDto itemTypeState;
	
	private String resourceId;

	// is not ItemDefinition of attribute
	private int dispOrder;

	/**
	 * will remove: lanlt
	 */
	private BigDecimal selectionItemRefType;

	// is not ItemDefinition of attribute
	private List<EnumConstant> selectionItemRefTypes;
	
	private boolean canAbolition;
}
