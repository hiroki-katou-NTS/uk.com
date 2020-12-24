package nts.uk.screen.at.app.kmk004.j;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.query.kmk004.common.EmployeeList;

/**
 * 
 * @author sonnlb
 *
 */
@Stateless
public class AfterSearchEmployees {

	@Inject
	private EmployeeList employeeList;

	public List<String> afterSearchEmployees() {

		// 社員リストを表示する
		return this.employeeList.get(LaborWorkTypeAttr.FLEX).stream().map(x -> x.employeeId)
				.collect(Collectors.toList());
	}

}
