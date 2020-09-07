package nts.uk.ctx.at.record.dom.workrecord.workrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 *	AR:就業確定
 *  UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.実績の状況管理.管理職場の就業確定.就業確定
 * @author chungnt
 *
 */

@AllArgsConstructor
@Getter
public class EmploymentConfirmed implements DomainAggregate  {

	/**
	 * 会社ID
	 */
	private final CompanyId companyId;
	
	/**
	 * 職場ID
	 */
	private final WorkplaceId workplaceId;
	
	/**
	 * 締めID
	 */
	private final ClosureId closureId;
	
	/**
	 * 年月
	 */
	private final YearMonth processYM;
	
	/**
	 * 社員ID
	 */
	private final String employeeId;
		
	/**
	 * 日時
	 */
	private final GeneralDateTime date;
	
}
