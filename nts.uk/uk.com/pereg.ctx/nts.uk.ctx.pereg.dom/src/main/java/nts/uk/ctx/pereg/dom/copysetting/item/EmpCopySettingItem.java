package nts.uk.ctx.pereg.dom.copysetting.item;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

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

	private BigDecimal selectionItemRefType;

	private String itemParentCd;

	public EmpCopySettingItem(String perInfoCtgId, String categoryCode, String itemDefId, String itemCode,
			String itemName, IsRequired isRequired, int dataType, BigDecimal selectionItemRefType, String itemParentCd) {
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
	}

	public EmpCopySettingItem(String perInfoCtgId, String itemDefId, String itemName, String itemCode, Boolean isAlreadyCopy,
			String itemParentCd) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.itemDefId = itemDefId;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.isAlreadyCopy = isAlreadyCopy;
		this.itemParentCd = itemParentCd;
	}

	public static EmpCopySettingItem createFromJavaType(String perInfoCtgId, String categoryCode, String itemDefId,
			String itemCode, String itemName, int isRequired, int dataType, BigDecimal selectionItemRefType,
			String itemParentCd) {

		return new EmpCopySettingItem(perInfoCtgId, categoryCode, itemDefId, itemCode, itemName,
				EnumAdaptor.valueOf(isRequired, IsRequired.class), dataType, selectionItemRefType, itemParentCd);

	}

	public static EmpCopySettingItem createFromJavaType(String perInfoCtgId, String itemDefId, String itemCode, String itemName,
			Boolean isAlreadyCopy, String itemParentCd) {
		return new EmpCopySettingItem(perInfoCtgId, itemDefId,itemCode, itemName, isAlreadyCopy, itemParentCd);
	}

}
