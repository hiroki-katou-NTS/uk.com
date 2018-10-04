package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.primitive.FormatSelection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.primitive.IntegrationCode;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.primitive.Memo;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.primitive.SelectionItemName;

/**
 * 
 * @author tuannv
 *
 */

@Getter
@Setter
// 個人情報の選択項目
public class PerInfoSelectionItem extends AggregateRoot {

	/**
	 * 契約コード
	 */
	private String contractCode;

	/**
	 * ID
	 */
	private String selectionItemId;

	/**
	 * 名称
	 */
	private SelectionItemName selectionItemName;

	/**
	 * 選択肢の形式
	 */
	private FormatSelection formatSelection;

	/**
	 * 統合コード
	 */
	private Optional<IntegrationCode> integrationCode;

	/**
	 * メモ
	 */
	private Optional<Memo> memo;

	private PerInfoSelectionItem() {
		super();
	}

	public static PerInfoSelectionItem createFromJavaType(String contractCode, String selectionItemId,
			String selectionItemName, int characterTypeAtr, int codeLength, int nameLength,
			int extraCodeLenth, String integrationCd, String memo) {
		PerInfoSelectionItem selectionItem = new PerInfoSelectionItem();
		selectionItem.contractCode = contractCode;
		selectionItem.selectionItemId = selectionItemId;
		selectionItem.selectionItemName = new SelectionItemName(selectionItemName);
		selectionItem.formatSelection = FormatSelection.createFromJavaType(characterTypeAtr, codeLength, nameLength,
				extraCodeLenth);
		selectionItem.integrationCode = integrationCd != null ? Optional.of(new IntegrationCode(integrationCd))
				: Optional.empty();
		selectionItem.memo = memo != null ? Optional.of(new Memo(memo)) : Optional.empty();
		return selectionItem;

	}

	public static PerInfoSelectionItem createFromJavaType(String contractCode, String selectionItemId,
			String selectionItemName, boolean characterType, int codeLength, int nameLength,
			int extraCodeLenth, String integrationCd, String memo) {
		int characterTypeAtr = characterType ? 1 : 0;
		return createFromJavaType(contractCode, selectionItemId, selectionItemName,
				characterTypeAtr, codeLength, nameLength, extraCodeLenth, integrationCd, memo);
	}

	public void updateDomain(String selectionItemName, String integrationCd, String memo) {
		this.selectionItemName = new SelectionItemName(selectionItemName);
		this.integrationCode = integrationCd != null ? Optional.of(new IntegrationCode(integrationCd))
				: Optional.empty();
		this.memo = memo != null ? Optional.of(new Memo(memo)) : Optional.empty();
	}

}
