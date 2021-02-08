package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppRootInsImport {
	/**
	 * 承認ルート中間データID
	 */
	private String rootID;
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 対象者
	 */
	private String employeeID;
	
	/**
	 * 履歴期間
	 */
	@Setter
	private DatePeriod datePeriod;
	
	/**
	 * ルート種類
	 */
	private Integer rootType;
	
	/**
	 * 承認フェーズ
	 */
	private List<AppPhaseInsImport> listAppPhase;
}
