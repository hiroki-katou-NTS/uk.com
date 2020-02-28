package nts.uk.ctx.hr.shared.dom.personalinfo.stresscheck;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author anhdt
 * ストレスチェック
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StressCheck extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 履歴ID
	 */
	private String historyID;

	/**
	 * 社員ID
	 */
	private String employeeID;

	/**
	 * 総合結果
	 */
	private String overallResult;

}
