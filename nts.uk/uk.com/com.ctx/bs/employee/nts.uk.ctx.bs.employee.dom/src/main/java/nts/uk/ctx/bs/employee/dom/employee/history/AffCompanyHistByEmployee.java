package nts.uk.ctx.bs.employee.dom.employee.history;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffCompanyHistByEmployee extends DomainObject{
	/** 社員ID */
	private String sId;

	/** 履歴 */
	private List<AffCompanyHistItem> lstAffCompanyHistoryItem;
}
