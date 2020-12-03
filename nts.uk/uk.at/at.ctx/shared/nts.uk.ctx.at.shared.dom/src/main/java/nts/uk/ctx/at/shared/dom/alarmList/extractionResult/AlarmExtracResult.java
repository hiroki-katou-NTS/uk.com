package nts.uk.ctx.at.shared.dom.alarmList.extractionResult;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * アラーム抽出結果
 * @author do_dt
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmExtracResult extends DomainObject {
	/**アラームチェック条件コード	 */
	private String coditionCode;
	/**カテゴリ	 */
	private int category; //AlarmCategory
	/**チェック条件結果	 */
	private List<ResultOfEachCondition> lstResult;
}
