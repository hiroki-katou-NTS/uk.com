package nts.uk.ctx.pereg.dom.person.setting.selectionitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.ExternalCD;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.MemoSelection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionCD;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionName;

/**
 * 
 * @author tuannv
 *
 */

@AllArgsConstructor
@Getter
@Setter
// 個人情報の選択項目ドメイン
public class PerInfoSelectionItem extends AggregateRoot{
	private String selectionItemId;
	private SelectionItemName selectionItemName;
	private Memo Memo;
	private SelectionItemClassification selectionItemClassification;
	private String contractCode;
	private IntegrationCode integrationCode;
	private FormatSelection formatSelection;

	public static PerInfoSelectionItem createFromJavaType(
			String selectionItemId, 
			String selectionItemName, 
			String memo,
			int selectionItemClsAtr, 
			String contractCd, 
			String integrationCd, 
			int selectionCd, 
			int characterTypeAtr,
			int selectionName, 
			int selectionExtCd) {

		// 個人情報の選択項目パラメーター帰還
		return new PerInfoSelectionItem(
				selectionItemId, 
				new SelectionItemName(selectionItemName), 
				new Memo(memo),
				EnumAdaptor.valueOf(selectionItemClsAtr, SelectionItemClassification.class), 
				contractCd,
				new IntegrationCode(integrationCd),
				FormatSelection.createFromJavaType(selectionCd, characterTypeAtr, selectionName, selectionExtCd));

	}

//	public PerInfoSelectionItem(String selectionItemId, SelectionItemName selectionItemName,
//			Memo Memo, SelectionItemClassification selectionItemClassification, String contractCode,
//			IntegrationCode integrationCode, FormatSelection formatSelection){
//		super();
//		this.selectionItemId = selectionItemId;
//		this.selectionItemName = selectionItemName;
//		this.Memo = Memo;
//		this.selectionItemClassification = selectionItemClassification;
//		this.contractCode = contractCode;
//		this.integrationCode = integrationCode;
//		this.formatSelection.getSelectionName();
//		
//	}
}
