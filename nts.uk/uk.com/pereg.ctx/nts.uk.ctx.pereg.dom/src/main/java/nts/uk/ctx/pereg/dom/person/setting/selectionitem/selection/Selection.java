package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@Getter
@Setter
public class Selection extends AggregateRoot{
	private String selectionID;
	private String histId;
	private SelectionCD selectionCD;
	private SelectionName selectionName;
	private ExternalCD externalCD;
	private MemoSelection memoSelection;
	
	//add selectionItemName
	private String selectionItemName;

	// 選択肢ドメイン
	public static Selection createFromSelection(String selectionID, String histId, String selectionCD,
			String selectionName, String externalCD, String memoSelection) {

		// 選択肢パラメーター帰還
		return new Selection(selectionID, histId, new SelectionCD(selectionCD),
				new SelectionName(selectionName), new ExternalCD(externalCD), new MemoSelection(memoSelection));

	}
	
	
	// Lanlt
	public static Selection createFromSelection(String selectionID, String histId, String selectionCD,
			String selectionName, String externalCD, String memoSelection, String selectionItemName) {

		// 選択肢パラメーター帰還
		return new Selection(selectionID, histId, new SelectionCD(selectionCD),
				new SelectionName(selectionName), new ExternalCD(externalCD), new MemoSelection(memoSelection),
				selectionItemName);

	}
	public Selection(String selectionID, String histId, SelectionCD selectionCD, SelectionName selectionName,
			ExternalCD externalCD, MemoSelection memoSelection) {
		super();
		this.selectionID = selectionID;
		this.histId = histId;
		this.selectionCD = selectionCD;
		this.selectionName = selectionName;
		this.externalCD = externalCD;
		this.memoSelection = memoSelection;
	}
	

	
}
