package nts.uk.ctx.bs.employee.dom.employee.history;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/** 所属会社履歴 */
public class AffCompanyHist extends AggregateRoot {
	/** 個人ID */
	private String pId;

	/** 社員別履歴 */
	private List<AffCompanyHistByEmployee> lstAffCompanyHistByEmployee;

	public AffCompanyHistByEmployee getAffCompanyHistByEmployee(String sid) {
		if (lstAffCompanyHistByEmployee == null) {
			lstAffCompanyHistByEmployee = new ArrayList<AffCompanyHistByEmployee>();
		}

		List<AffCompanyHistByEmployee> filter = lstAffCompanyHistByEmployee.stream().filter(m -> m.getSId().equals(sid))
				.collect(Collectors.toList());
		if (filter != null && !filter.isEmpty()) {
			return filter.get(0);
		}

		return null;
	}

	public void addAffCompanyHistByEmployee(AffCompanyHistByEmployee domain) {
		if (lstAffCompanyHistByEmployee == null) {
			lstAffCompanyHistByEmployee = new ArrayList<AffCompanyHistByEmployee>();
		}

		lstAffCompanyHistByEmployee.add(domain);
		domain.toBePublished();
	}
}