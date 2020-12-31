package nts.uk.ctx.at.shared.dom.alarmList.extractionResult;

import java.util.Optional;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDateTime;
/**
 * 抽出結果詳細
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtractionResultDetail extends DomainObject {
	/**社員ID	 */
	private String SID;
	/**アラーム値日付	 */
	private ExtractionAlarmPeriodDate periodDate;
	/**アラーム項目	 */
	private String alarmName;
	/**	アラーム内容 */
	private String alarmContent;
	/**	発生日時 */
	private GeneralDateTime runTime;
	/**職場ID	 */
	private Optional<String> wpID;
	/**	コメント */
    private Optional<String> comment;
    /**  チェック対象値   */
    private Optional<String> checkValue;
}
