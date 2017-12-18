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

	public EmpCopySettingItem(String perInfoCtgId, String categoryCode, String itemDefId, String itemCode,
			String itemName, IsRequired isRequired, int dataType, BigDecimal selectionItemRefType) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.categoryCode = categoryCode;
		this.itemDefId = itemDefId;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.isRequired = isRequired;
		this.dataType = dataType;
		this.selectionItemRefType = selectionItemRefType;
	}

	public static EmpCopySettingItem createFromJavaType(String perInfoCtgId, String categoryCode, String itemDefId,
			String itemCode, String itemName, int isRequired, int dataType, BigDecimal selectionItemRefType) {

		return new EmpCopySettingItem(perInfoCtgId, categoryCode, itemDefId, itemCode, itemName,
				EnumAdaptor.valueOf(isRequired, IsRequired.class), dataType, selectionItemRefType);

	}

}
