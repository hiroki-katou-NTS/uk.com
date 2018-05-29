package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.primitive.Disporder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.primitive.InitSelection;

@AllArgsConstructor
@Getter
/**
 * 
 * @author tuannv
 *
 */

//選択肢の並び順 ドメイン
public class SelectionItemOrder {
	private String selectionID;
	private String histId;
	private Disporder disporder;
	private InitSelection initSelection;

	public static SelectionItemOrder selectionItemOrder(String selectionID, String histId, int disporder,
			int initSelection) {

		// 選択肢の並び順 パラメーター帰還
		return new SelectionItemOrder(selectionID, histId, new Disporder(disporder),
				EnumAdaptor.valueOf(initSelection, InitSelection.class));
	}
}
