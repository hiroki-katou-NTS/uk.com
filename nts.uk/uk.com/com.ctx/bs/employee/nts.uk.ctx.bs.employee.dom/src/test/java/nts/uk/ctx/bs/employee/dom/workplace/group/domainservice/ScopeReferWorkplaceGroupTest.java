package nts.uk.ctx.bs.employee.dom.workplace.group.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.bs.employee.dom.employee.service.EmployeeReferenceRangeImport;

public class ScopeReferWorkplaceGroupTest {

	@Test
	public void getters() {
		ScopeReferWorkplaceGroup data = ScopeReferWorkplaceGroup.of(0);
		NtsAssert.invokeGetters(data);
	}
	
	@Test
	public void test() {
		ScopeReferWorkplaceGroup data = ScopeReferWorkplaceGroup.of(0);
		assertThat(data).isEqualTo(ScopeReferWorkplaceGroup.ALL_EMPLOYEE);
		data = ScopeReferWorkplaceGroup.of(1);
		assertThat(data).isEqualTo(ScopeReferWorkplaceGroup.ONLY_ME);
	}
	/**
	 * 社員参照範囲  == EmployeeReferenceRangeImport.ONLY_MYSELF
	 */
	@Test
	public void testDetermineTheReferenceRange() {
		EmployeeReferenceRangeImport employeeReferenceRange = EmployeeReferenceRangeImport.ONLY_MYSELF;
		ScopeReferWorkplaceGroup data = ScopeReferWorkplaceGroup.of(0);
		assertThat(data.determineTheReferenceRange(employeeReferenceRange)).isEqualTo(ScopeReferWorkplaceGroup.ONLY_ME);
	}
	
	/**
	 * 社員参照範囲  != EmployeeReferenceRangeImport.ONLY_MYSELF
	 */
	@Test
	public void testDetermineTheReferenceRange_1() {
		EmployeeReferenceRangeImport employeeReferenceRange = EmployeeReferenceRangeImport.DEPARTMENT_ONLY;
		ScopeReferWorkplaceGroup data = ScopeReferWorkplaceGroup.of(0);
		assertThat(data.determineTheReferenceRange(employeeReferenceRange)).isEqualTo(ScopeReferWorkplaceGroup.ALL_EMPLOYEE);
	}

}
