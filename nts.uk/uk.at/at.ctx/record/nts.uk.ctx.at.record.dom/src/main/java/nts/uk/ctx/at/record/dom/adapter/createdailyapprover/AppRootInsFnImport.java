package nts.uk.ctx.at.record.dom.adapter.createdailyapprover;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppRootInsFnImport {
	
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
	private List<AppPhaseInsFnImport> listAppPhase;
	
}
