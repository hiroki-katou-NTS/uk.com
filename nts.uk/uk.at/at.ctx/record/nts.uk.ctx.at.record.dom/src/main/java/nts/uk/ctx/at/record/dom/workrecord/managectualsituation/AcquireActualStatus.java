package nts.uk.ctx.at.record.dom.workrecord.managectualsituation;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実績状況を取得する
 */
@Data
@AllArgsConstructor
public class AcquireActualStatus {
	/**
	 * 会社ID：会社ID 
	 */
	String companyId;
	/**
	 * 基準社員: 社員ID
	 */
	String employeeId;
	/**
	 * 処理年月: 年月
	 */
	Integer processDateYM;
	/**
	 * 締めID: 締めID
	 */
	Integer closureId;
	/**
	 * 締め日: 年月日
	 */
	GeneralDate closureDate;
	/**
	 * 期間: 期間
	 */
	DatePeriod duration;
	/**
	 * 所属職場ID: 職場ID
	 */
	String workplaceId;
	
}
