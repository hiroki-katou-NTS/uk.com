package nts.uk.ctx.pereg.dom.copysetting.item;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateType;

@Getter
@Setter
public class EmpCopySettingItem extends AggregateRoot {

	private String perInfoCtgId;

	private String categoryCode;

	private String itemDefId;

	private String itemCode;

	private String itemName;

	private IsRequired isRequired;

	private boolean isAlreadyCopy;

	private int dataType;

	private int selectionItemRefType;

	private String itemParentCd;

	private DateType dateType;

	private String selectionItemRefCd;

	public EmpCopySettingItem(String perInfoCtgId, String categoryCode, String itemDefId, String itemCode,
			String itemName, IsRequired isRequired, int dataType, int selectionItemRefType, String itemParentCd,
			DateType dateType, String selectionItemRefCd) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.categoryCode = categoryCode;
		this.itemDefId = itemDefId;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.isRequired = isRequired;
		this.dataType = dataType;
		this.selectionItemRefType = selectionItemRefType;
		this.itemParentCd = itemParentCd;
		this.dateType = dateType;
		this.selectionItemRefCd = selectionItemRefCd;
	}

	public EmpCopySettingItem(String perInfoCtgId, String itemDefId, String itemName, String itemCode,
			Boolean isAlreadyCopy, String itemParentCd) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.itemDefId = itemDefId;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.isAlreadyCopy = isAlreadyCopy;
		this.itemParentCd = itemParentCd;
	}

	public static EmpCopySettingItem createFromJavaType(String perInfoCtgId, String categoryCode, String itemDefId,
			String itemCode, String itemName, int isRequired, int dataType, int selectionItemRefType,
			String itemParentCd, int dateType, String selectionItemRefCd) {

		return new EmpCopySettingItem(perInfoCtgId, categoryCode, itemDefId, itemCode, itemName,
				EnumAdaptor.valueOf(isRequired, IsRequired.class), dataType, selectionItemRefType, itemParentCd,
				EnumAdaptor.valueOf(dateType, DateType.class), selectionItemRefCd);

	}

	public static EmpCopySettingItem createFromJavaType(String perInfoCtgId, String itemDefId, String itemCode,
			String itemName, Boolean isAlreadyCopy, String itemParentCd) {
		return new EmpCopySettingItem(perInfoCtgId, itemDefId, itemCode, itemName, isAlreadyCopy, itemParentCd);
	}

}
