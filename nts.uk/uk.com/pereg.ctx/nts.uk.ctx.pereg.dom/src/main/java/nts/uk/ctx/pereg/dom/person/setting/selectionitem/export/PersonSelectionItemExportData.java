package nts.uk.ctx.pereg.dom.person.setting.selectionitem.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonSelectionItemExportData {
	// 選択項目名称
	private String selectionItemName;

	// 選択肢履歴.履歴（連続する）.期間
	private GeneralDate startDate;
	// 選択肢履歴.履歴（連続する）.期間
	private GeneralDate endDate;
	// 初期選択とする
	private Integer initSelection;
	// 選択肢コード
	private String selectionCD;
	// 選択肢名称
	private String selectionName;
	// 選択肢外部コード
	private String externalCD;
	// メモ
	private String memoSelection;

}
