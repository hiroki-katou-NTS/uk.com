package nts.uk.ctx.sys.auth.dom.grant;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetCode;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT - ロールセット個人別付与
 *
 */

@Getter
public class RoleSetGrantedPerson extends AggregateRoot {

	// ロールセットコード.
	private RoleSetCode roleSetCd;

	// 会社ID
	private String companyId;

	// 有効期間
	private DatePeriod validPeriod;

	// 社員ID
	private String employeeID;

}
