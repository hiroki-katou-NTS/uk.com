package nts.uk.ctx.exio.dom.exo.outputitem;

import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.ItemNo;

/**
 * カテゴリ項目
 */
@Getter
public class CategoryItem extends DomainObject {
	
	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 出力項目コード
	 */
	private OutputItemCode outputItemCode;

	/**
	 * 条件設定コード
	 */
	@Setter
	private ConditionSettingCode conditionSettingCode;

	/**
	 * カテゴリ項目NO
	 */
	private ItemNo itemNo;

	/**
	 * カテゴリID
	 */
	private CategoryId categoryId;

	/**
	 * 演算符号
	 */
	private Optional<OperationSymbol> operationSymbol;

	/**
	 * s 順序
	 */
	private int displayOrder;

	public CategoryItem(int itemNo, int categoryId, Integer operationSymbol, int displayOrder) {
		this.itemNo = new ItemNo(itemNo);
		this.categoryId = new CategoryId(categoryId);

		if (Objects.isNull(operationSymbol)) {
			this.operationSymbol = Optional.empty();
		} else {
			this.operationSymbol = Optional.of(EnumAdaptor.valueOf(operationSymbol, OperationSymbol.class));
		}

		this.displayOrder = displayOrder;
	}
}
