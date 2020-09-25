package nts.uk.ctx.bs.employee.dom.workplace.group.domainservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.EmployeeAffiliation;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.GetEmpCanReferBySpecifyWorkgroupService.Require;

@RunWith(JMockit.class)
public class GetEmpCanReferBySpecifyWorkgroupServiceTest {

	@Injectable
	private Require require;

	/**
	 * 社員が参照可能な職場グループと社員参照範囲を取得する#取得する( require, 基準日, 社員ID ) is empty
	 */
	@Test
	public void testGetEmpCanRefer() {
		GeneralDate date = GeneralDate.today();
		String empId = "empId";
		String workplaceGroupId = "workplaceGroupId";
		new MockUp<GetWorkplaceGroupsAndEmpService>() {
			@Mock
			public Map<String, ScopeReferWorkplaceGroup> getWorkplaceGroup(GetWorkplaceGroupsAndEmpService.Require require, GeneralDate baseDate,
					String empId) {
				return new HashMap<>();
				
			}
		};
		List<String> data = GetEmpCanReferBySpecifyWorkgroupService.getEmpCanRefer(require, date, empId, workplaceGroupId);
		assertThat(data.isEmpty()).isTrue();
	}
	
	/**
	 * 社員が参照可能な職場グループと社員参照範囲を取得する#取得する( require, 基準日, 社員ID ) not empty
	 * if ONLY_ME
	 */
	@Test
	public void testGetEmpCanRefer_1() {
		GeneralDate date = GeneralDate.today();
		String empId = "empId";
		String workplaceGroupId = "workplaceGroupId";
		new MockUp<GetWorkplaceGroupsAndEmpService>() {
			@Mock
			public Map<String, ScopeReferWorkplaceGroup> getWorkplaceGroup(GetWorkplaceGroupsAndEmpService.Require require, GeneralDate baseDate,
					String empId) {
				Map<String, ScopeReferWorkplaceGroup> ressult = new HashMap<>();
				ressult.put(workplaceGroupId, ScopeReferWorkplaceGroup.ONLY_ME);
				return ressult;
				
			}
		};
		List<String> data = GetEmpCanReferBySpecifyWorkgroupService.getEmpCanRefer(require, date, empId, workplaceGroupId);
		assertThat(data)
		.extracting(d->d)
		.containsExactly(empId);
	}
	
	/**
	 * 社員が参照可能な職場グループと社員参照範囲を取得する#取得する( require, 基準日, 社員ID ) not empty && have workplace Group Id
	 * if ALL_EMPLOYEE
	 *  職場グループに所属する社員をすべて取得する#取得する( require, 基準日, 職場グループID ) have not data
	 */
	@Test
	public void testGetEmpCanRefer_2() {
		GeneralDate date = GeneralDate.today();
		String empId = "empId";
		String workplaceGroupId = "workplaceGroupId";
		new MockUp<GetWorkplaceGroupsAndEmpService>() {
			@Mock
			public Map<String, ScopeReferWorkplaceGroup> getWorkplaceGroup(GetWorkplaceGroupsAndEmpService.Require require, GeneralDate baseDate,
					String empId) {
				Map<String, ScopeReferWorkplaceGroup> ressult = new HashMap<>();
				ressult.put(workplaceGroupId, ScopeReferWorkplaceGroup.ALL_EMPLOYEE);
				return ressult;
				
			}
		};
		new MockUp<GetAllEmpWhoBelongWorkplaceGroupService>() {
			@Mock
			public  List<EmployeeAffiliation> getAllEmp(GetAllEmpWhoBelongWorkplaceGroupService.Require require ,GeneralDate baseDate , String workplaceGroupId ){
				return new ArrayList<>();
			}
		};
		List<String> data = GetEmpCanReferBySpecifyWorkgroupService.getEmpCanRefer(require, date, empId, workplaceGroupId);
		assertThat(data.isEmpty()).isTrue();
	}
	
	/**
	 * 社員が参照可能な職場グループと社員参照範囲を取得する#取得する( require, 基準日, 社員ID ) not empty && have workplace Group Id
	 * if ALL_EMPLOYEE
	 *  職場グループに所属する社員をすべて取得する#取得する( require, 基準日, 職場グループID ) have data
	 */
	@Test
	public void testGetEmpCanRefer_3() {
		GeneralDate date = GeneralDate.today();
		String empId = "empId";
		String workplaceGroupId = "workplaceGroupId";
		new MockUp<GetWorkplaceGroupsAndEmpService>() {
			@Mock
			public Map<String, ScopeReferWorkplaceGroup> getWorkplaceGroup(GetWorkplaceGroupsAndEmpService.Require require, GeneralDate baseDate,
					String empId) {
				Map<String, ScopeReferWorkplaceGroup> ressult = new HashMap<>();
				ressult.put(workplaceGroupId, ScopeReferWorkplaceGroup.ALL_EMPLOYEE);
				return ressult;
				
			}
		};
		String empId2 = "empId2";
		String empId3 = "empId3";
		String empId4 = "empId4";
		List<EmployeeAffiliation> listEmployeeAffiliation = Arrays.asList(
				new EmployeeAffiliation(empId2, Optional.empty(), Optional.empty(), "workplaceID2", Optional.of(workplaceGroupId)),
				new EmployeeAffiliation(empId3, Optional.empty(), Optional.empty(), "workplaceID3",  Optional.of(workplaceGroupId)),
				new EmployeeAffiliation(empId4, Optional.empty(), Optional.empty(), "workplaceID4",  Optional.of(workplaceGroupId))
				);
		new MockUp<GetAllEmpWhoBelongWorkplaceGroupService>() {
			@Mock
			public  List<EmployeeAffiliation> getAllEmp(GetAllEmpWhoBelongWorkplaceGroupService.Require require ,GeneralDate baseDate , String workplaceGroupId ){
				return listEmployeeAffiliation;
			}
		};
		List<String> data = GetEmpCanReferBySpecifyWorkgroupService.getEmpCanRefer(require, date, empId, workplaceGroupId);
		assertThat(data)
		.extracting(d->d)
		.containsExactly(empId2,empId3,empId4);
	}

}
