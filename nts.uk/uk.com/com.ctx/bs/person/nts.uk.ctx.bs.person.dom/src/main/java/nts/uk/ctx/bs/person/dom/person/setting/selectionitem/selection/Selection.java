package nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Selection {
	private String selectionID;
	private String histId;
	private SelectionCD selectionCD;
	private SelectionName selectionName;
	private ExternalCD externalCD;
	private MemoSelection memoSelection;

	// 選択肢ドメイン
	public static Selection createFromSelection(String selectionID, String histId, String selectionCD,
			String selectionName, String externalCD, String memoSelection) {

		// 選択肢パラメーター帰還
		return new Selection(memoSelection, memoSelection, new SelectionCD(selectionCD),
				new SelectionName(selectionName), new ExternalCD(externalCD), new MemoSelection(memoSelection));

	}
}
