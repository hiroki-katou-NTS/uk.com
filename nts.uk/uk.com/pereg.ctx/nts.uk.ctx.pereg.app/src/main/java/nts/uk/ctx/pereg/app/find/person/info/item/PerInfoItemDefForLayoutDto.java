package nts.uk.ctx.pereg.app.find.person.info.item;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateItem;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionItem;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetItem;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.pereg.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.pereg.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.pereg.dom.person.info.timepointitem.TimePointItem;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * item def dto which declare fields for displaying layout
 * @author xuan vinh
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerInfoItemDefForLayoutDto {
	
	private String itemDefId;
	
	private String perInfoCtgId;
	
	private String perInfoCtgCd;

	private String itemCode;

	private String itemName;
	
	private int itemDefType;
	

	private int isRequired;

	private int dispOrder;
	
	private int row;
	
	private ActionRole actionRole;

	private BigDecimal selectionItemRefType;

	private ItemTypeStateDto itemTypeState;

	private List<EnumConstant> selectionItemRefTypes;
	
}
