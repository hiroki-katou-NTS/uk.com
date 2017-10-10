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

	public static Selection createFromSelection(String selectionID, String histId, String selectionCD,
			String selectionName, String externalCD, String memoSelection) {
		return new Selection(memoSelection, memoSelection, new SelectionCD(selectionCD),
				new SelectionName(selectionName), new ExternalCD(externalCD), new MemoSelection(memoSelection));

	}
}
