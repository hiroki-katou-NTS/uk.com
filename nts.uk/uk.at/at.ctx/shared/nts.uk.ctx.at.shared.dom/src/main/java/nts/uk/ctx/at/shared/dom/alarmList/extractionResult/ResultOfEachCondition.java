package nts.uk.ctx.at.shared.dom.alarmList.extractionResult;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * 各チェック条件の結果
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultOfEachCondition extends DomainObject {
	/**チェック種類	 */
	private AlarmListCheckType checkType;
	/**コード	 */
	private String no;
	/**抽出結果	 */
	private List<ExtractionResultDetail> lstResultDetail;

}
