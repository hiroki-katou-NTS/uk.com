package nts.uk.file.at.app.export.bento;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.period.DatePeriod;

/**
 * 月間予約台帳
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ReservationMonthDataSource {
	
	/**
	 * 会社名
	 */
	private String companyName;
	
	/**
	 * 対象期間
	 */
	private DatePeriod period;
	
	private List<ReservationWkpLedger> wkpLedgerLst;
}
