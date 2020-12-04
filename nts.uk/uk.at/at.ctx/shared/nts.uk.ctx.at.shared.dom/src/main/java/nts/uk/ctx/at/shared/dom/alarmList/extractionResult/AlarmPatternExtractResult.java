package nts.uk.ctx.at.shared.dom.alarmList.extractionResult;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * アラームリストパターンの抽出結果
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AlarmPatternExtractResult extends AggregateRoot {
	/**会社ID	 */
	private String CID;
	/**自動実行コード	 */
	private String runCode;
	/**アラームリストパターンコード	 */
	private String patternCode;
	/**アラームリストパターン名称	 */
	private String patternName;
	/**	アラーム抽出結果 */
	private List<AlarmExtracResult> lstExtracResult;

}
