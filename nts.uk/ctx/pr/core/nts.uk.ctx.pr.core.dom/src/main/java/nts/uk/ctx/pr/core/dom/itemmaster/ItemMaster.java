package nts.uk.ctx.pr.core.dom.itemmaster;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.core.dom.enums.DisplayAtr;

@Getter
public class ItemMaster extends AggregateRoot {
	private CompanyCode companyCode;
	private ItemCode itemCode;
	private ItemName itemName;
	private ItemName itemAbName;
	private ItemName itemAbNameE;
	private ItemName itemAbNameO;
	private CategoryAtr categoryAtr;
	private int fixAtr;
	private DisplayAtr displaySet;
	private UniteCode uniteCode;
	private DisplayAtr zeroDisplaySet;
	private ItemDisplayAtr itemDisplayAtr;

	@Override
	public void validate() {
		super.validate();
		if (StringUtil.isNullOrEmpty(this.itemCode.v(), true) || StringUtil.isNullOrEmpty(this.companyCode.v(), true)
				|| StringUtil.isNullOrEmpty(this.itemName.v(), true)
				|| StringUtil.isNullOrEmpty(this.itemAbName.v(), true) || this.displaySet == null) {
			throw new BusinessException("pika");
		}
	}

	public ItemMaster(CompanyCode companyCode, ItemCode itemCode, ItemName itemName, ItemName itemAbName,
			ItemName itemAbNameE, ItemName itemAbNameO, CategoryAtr categoryAtr, int fixAtr, DisplayAtr displaySet,
			UniteCode uniteCode, DisplayAtr zeroDisplaySet, ItemDisplayAtr itemDisplayAtr) {
		super();
		this.companyCode = companyCode;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemAbName = itemAbName;
		this.itemAbNameE = itemAbNameE;
		this.itemAbNameO = itemAbNameO;
		this.categoryAtr = categoryAtr;
		this.fixAtr = fixAtr;
		this.displaySet = displaySet;
		this.uniteCode = uniteCode;
		this.zeroDisplaySet = zeroDisplaySet;
		this.itemDisplayAtr = itemDisplayAtr;
	}

	public static ItemMaster createFromJavaType(String companyCode, String itemCode, String itemName, String itemAbName,
			String itemAbNameE, String itemAbNameO, int categoryAtr, int fixAtr, int displaySet, String uniteCode,
			int zeroDisplaySet, int itemDisplayAtr) {
		return new ItemMaster(new CompanyCode(companyCode), new ItemCode(itemCode), new ItemName(itemName),
				new ItemName(itemAbName), new ItemName(itemAbNameE), new ItemName(itemAbNameO),
				EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class), fixAtr,
				EnumAdaptor.valueOf(displaySet, DisplayAtr.class), new UniteCode(uniteCode),
				EnumAdaptor.valueOf(zeroDisplaySet, DisplayAtr.class),
				EnumAdaptor.valueOf(itemDisplayAtr, ItemDisplayAtr.class));
	}
}
