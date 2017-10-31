/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.layout.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.init.item.DateDataDto;
import nts.uk.ctx.bs.employee.app.find.init.item.NumberDataDto;
import nts.uk.ctx.bs.employee.app.find.init.item.SaveDataDto;
import nts.uk.ctx.bs.employee.app.find.init.item.StringDataDto;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceTypes;

/**
 * @author danpv
 *
 */
@Data
public class EmpPersonInfoItemDto {

	private String id;

	private String perInfoCtgId;

	private int dispOrder;

	private String itemCode;

	private String itemName;

	private int isAbolition;

	private int isFixed;

	private int isRequired;

	private int systemRequired;

	private int requireChangable;

	private BigDecimal selectionItemRefType;

	// private ItemTypeStateDto itemTypeState;

	private List<EnumConstant> selectionItemRefTypes;

	private SaveDataDto data;

	public static EmpPersonInfoItemDto createFromDomain(PersonInfoItemDefinition itemDef, int dispOrder) {
		EmpPersonInfoItemDto dto = new EmpPersonInfoItemDto();
		dto.setId(itemDef.getPerInfoItemDefId());
		dto.setPerInfoCtgId(itemDef.getPerInfoCategoryId());
		dto.setDispOrder(dispOrder);
		dto.setItemCode(itemDef.getItemCode().v());
		dto.setItemName(itemDef.getItemName().v());
		dto.setIsAbolition(itemDef.getIsAbolition().value);
		dto.setIsFixed(itemDef.getIsFixed().value);
		dto.setRequireChangable(itemDef.getIsRequired().value);
		dto.setSystemRequired(itemDef.getSystemRequired().value);
		dto.setRequireChangable(itemDef.getRequireChangable().value);
		dto.setSelectionItemRefType(itemDef.getSelectionItemRefType());
		// createItemTypeStateDto(itemDef.getItemTypeState())
		dto.setSelectionItemRefTypes(EnumAdaptor.convertToValueNameList(ReferenceTypes.class));
		return dto;
	}

	public void setData(String value) {
		this.data = StringDataDto.createFromJavaType(value);
	}

	public void setData(int value) {
		this.data = NumberDataDto.createFromJavaType(value);
	}

	public void setData(GeneralDate value) {
		this.data = DateDataDto.createFromJavaType(value);
	}

}
