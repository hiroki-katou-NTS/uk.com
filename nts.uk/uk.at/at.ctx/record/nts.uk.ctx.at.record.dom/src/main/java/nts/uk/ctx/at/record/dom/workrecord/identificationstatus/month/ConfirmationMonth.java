package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Day;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * @author thanhnx
 * 月の本人確認
 */
@Getter
@Setter
@AllArgsConstructor
public class ConfirmationMonth extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private CompanyId companyID;

	/**
	 * 社員ID
	 */
	private String employeeId;

	/**
	 * 締めID
	 */
	private ClosureId closureId;

	// fix bug 101936
	/**
	 * 締め日
	 */
	private ClosureDate closureDate;

	/**
	 * 年月
	 */
	private YearMonth processYM;

	/**
	 * 本人確認日
	 */
	private GeneralDate indentifyYmd;
}
