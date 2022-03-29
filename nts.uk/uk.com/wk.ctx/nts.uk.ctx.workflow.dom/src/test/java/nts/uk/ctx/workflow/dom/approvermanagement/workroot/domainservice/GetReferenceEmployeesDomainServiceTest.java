package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.StatusOfEmployment;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.adapter.EmployeeInDesignatedImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.GetReferenceEmployeesDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.GetReferenceEmployeesDomainService.Require;

@RunWith(JMockit.class)
public class GetReferenceEmployeesDomainServiceTest {

	@Injectable
	private Require require;

	/**
	 * [1]取得する
	 */
	@Test
	public void testGet() {

		// given
		List<String> workplaceIds = Arrays.asList("workplaceId");
		List<Integer> empStatus = Arrays.asList(StatusOfEmployment.INCUMBENT.value,
				StatusOfEmployment.LEAVE_OF_ABSENCE.value, StatusOfEmployment.HOLIDAY.value);
		List<String> empIds = Arrays.asList("emp1", "emp2", "emp3");
		new Expectations() {
			{
				require.findWorkplaceList(GeneralDate.today());
				result = workplaceIds;
			};
			{
				require.findEmployeeList(workplaceIds, GeneralDate.today(), empStatus);
				result = empIds.stream().map(id -> new EmployeeInDesignatedImport(id, 0)).collect(Collectors.toList());
			};
		};

		// when
		List<String> result = GetReferenceEmployeesDomainService.get(require, GeneralDate.today());

		// then
		assertThat(result).isNotEmpty();
		assertThat(result).isEqualTo(empIds);
	}

	/**
	 * [1]取得する
	 * 
	 * $職場IDList == empty
	 */
	@Test
	public void testGetWorkplacesEmpty() {

		// when
		List<String> result = GetReferenceEmployeesDomainService.get(require, GeneralDate.today());

		// then
		assertThat(result).isEmpty();
	}

	/**
	 * [1]取得する
	 * 
	 * $在籍状態の社員List == empty
	 */
	@Test
	public void testGetEmployeesEmpty() {
		
		// given
		List<String> workplaceIds = Arrays.asList("workplaceId");
		new Expectations() {
			{
				require.findWorkplaceList(GeneralDate.today());
				result = workplaceIds;
			};
		};

		// when
		List<String> result = GetReferenceEmployeesDomainService.get(require, GeneralDate.today());

		// then
		assertThat(result).isEmpty();
	}
}
