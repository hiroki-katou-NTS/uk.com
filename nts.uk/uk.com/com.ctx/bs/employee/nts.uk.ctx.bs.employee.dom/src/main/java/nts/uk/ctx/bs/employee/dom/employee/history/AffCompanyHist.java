package nts.uk.ctx.bs.employee.dom.employee.history;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffCompanyHist extends AggregateRoot {
	/** 個人ID */
	private String pId;

	/** 社員別履歴 */
	private List<AffCompanyHistByEmployee> lstAffCompanyHistByEmployee;
}