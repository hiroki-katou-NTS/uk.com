package nts.uk.ctx.bs.employee.dom.workplace.group.domainservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.service.EmployeeReferenceRangeImport;
import nts.uk.ctx.bs.employee.dom.workplace.EmployeeAffiliation;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupCode;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupGettingService;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupName;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupType;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.GetWorkplaceGroupsAndEmpService.Require;

@RunWith(JMockit.class)
public class GetWorkplaceGroupsAndEmpServiceTest {

	@Injectable
	private Require require;

	/**
	 * require.担当者かどうか( 社員ID ) == true 
	 * require.職場グループをすべて取得する() is empty
	 */
	@Test
	public void testGetWorkplaceGroup() {
		GeneralDate baseDate = GeneralDate.today();
		String empId = "empId";

		new Expectations() {
			{
				require.whetherThePersonInCharge(empId);
				result = true;

				require.getAll();

			}
		};
		Map<String, ScopeReferWorkplaceGroup> result = GetWorkplaceGroupsAndEmpService.getWorkplaceGroup(require,
				baseDate, empId);
		assertThat(result.isEmpty()).isTrue();
	}

	/**
	 * require.担当者かどうか( 社員ID ) == true 
	 * require.職場グループをすべて取得する() is not empty
	 */
	@Test
	public void testGetWorkplaceGroup_1() {
		GeneralDate baseDate = GeneralDate.today();
		String empId = "empId";
		List<WorkplaceGroup> lstWorkplaceGroup = Arrays
				.asList(WorkplaceGroup.getWpg("cID", new WorkplaceGroupCode("wKPGRPCode1"),
						new WorkplaceGroupName("wKPGRPName"), WorkplaceGroupType.NORMAL));
		new Expectations() {
			{
				require.whetherThePersonInCharge(empId);
				result = true;

				require.getAll();
				result = lstWorkplaceGroup;
			}
		};
		Map<String, ScopeReferWorkplaceGroup> result = GetWorkplaceGroupsAndEmpService.getWorkplaceGroup(require,
				baseDate, empId);
		assertThat(result.entrySet().stream().map(c -> c.getKey()).collect(Collectors.toList())).containsOnlyElementsOf(
				lstWorkplaceGroup.stream().map(c -> c.getWKPGRPID()).collect(Collectors.toList()));
		assertThat(result.entrySet().stream().map(c -> c.getValue()).collect(Collectors.toList())).containsOnly(
				ScopeReferWorkplaceGroup.ALL_EMPLOYEE);
		
	}
	
	/**
	 * require.担当者かどうか( 社員ID ) == false 
	 * require.指定社員の管理職場をすべて取得する( 社員ID, 基準日 ) is empty
	 * $所属組織.職場グループID.isEmpty()
	 */
	@Test
	public void testGetWorkplaceGroup_2() {
		GeneralDate baseDate = GeneralDate.today();
		String empId = "empId";
		new Expectations() {
			{
				require.whetherThePersonInCharge(empId);
				result = false;

			}
		};
		testGetFromManagedWorkplace_1();
		new MockUp<WorkplaceGroupGettingService>() {
			@Mock
			public List<EmployeeAffiliation> get(WorkplaceGroupGettingService.Require require, GeneralDate date, List<String> employeeIDs) {
				return Arrays.asList(new EmployeeAffiliation(empId, Optional.empty(),  Optional.empty(), "workplaceID",  Optional.empty()));
			}
		};
		Map<String, ScopeReferWorkplaceGroup> result = GetWorkplaceGroupsAndEmpService.getWorkplaceGroup(require,
				baseDate, empId);
		assertThat(result.isEmpty()).isTrue();
	}
	
	/**
	 * require.指定社員の管理職場をすべて取得する( 社員ID, 基準日 ) is empty
	 */
	@Test
	public void testGetFromManagedWorkplace_1() {
		GeneralDate baseDate = GeneralDate.today();
		String empId = "empId";
		
		new Expectations() {
			{
				require.getAllManagedWorkplaces(empId, baseDate);
			}
		};
			
		val instance = new GetWorkplaceGroupsAndEmpService();
		@SuppressWarnings("unchecked")
		Map<String, ScopeReferWorkplaceGroup> result = (Map<String, ScopeReferWorkplaceGroup>) NtsAssert.Invoke.privateMethod(
				instance, 
				"getFromManagedWorkplace",
				require,
				baseDate,
				empId
				);
		assertThat(result.isEmpty()).isTrue();
	}
	
	
	/**
	 * require.担当者かどうか( 社員ID ) == false 
	 * require.指定社員の管理職場をすべて取得する( 社員ID, 基準日 ) not empty
	 * $所属組織.職場グループID is empty
	 * require.指定職場の職場グループ所属情報を取得する( $管理職場IDリスト ) is empty
	 */
	@Test
	public void testGetWorkplaceGroup_3() {
		GeneralDate baseDate = GeneralDate.today();
		String empId = "empId";
		new Expectations() {
			{
				require.whetherThePersonInCharge(empId);
				result = false;
			}	
		};
		this.testGetFromManagedWorkplace_2();
		new MockUp<WorkplaceGroupGettingService>() {
			@Mock
			public List<EmployeeAffiliation> get(WorkplaceGroupGettingService.Require require, GeneralDate date, List<String> employeeIDs) {
				return Arrays.asList(
						new EmployeeAffiliation(empId, Optional.empty(),  Optional.empty(), "workplaceID2",  Optional.empty()),
						new EmployeeAffiliation(empId, Optional.empty(),  Optional.empty(), "workplaceID1",  Optional.empty())
						
						);
			}
		};
		Map<String, ScopeReferWorkplaceGroup> result = GetWorkplaceGroupsAndEmpService.getWorkplaceGroup(require,
				baseDate, empId);
		assertThat(result.isEmpty()).isTrue();
	}
	
	/**
	 * require.指定社員の管理職場をすべて取得する( 社員ID, 基準日 ) not empty
	 * require.指定職場の職場グループ所属情報を取得する( $管理職場IDリスト ) is empty
	 */
	@Test
	public void testGetFromManagedWorkplace_2() {
		GeneralDate baseDate = GeneralDate.today();
		String empId = "empId";
		List<String> listWkpId = Arrays.asList("workplaceID1","workplaceID2","workplaceID3");
		
		new Expectations() {
			{
				require.getAllManagedWorkplaces(empId, baseDate);
				result = listWkpId;

				require.getByListWKPID(listWkpId);
			}
		};
			
		val instance = new GetWorkplaceGroupsAndEmpService();
		@SuppressWarnings("unchecked")
		Map<String, ScopeReferWorkplaceGroup> result = (Map<String, ScopeReferWorkplaceGroup>) NtsAssert.Invoke.privateMethod(
				instance, 
				"getFromManagedWorkplace",
				require,
				baseDate,
				empId
				);
		assertThat(result.isEmpty()).isTrue();
	}
	
	
	/**
	 * require.担当者かどうか( 社員ID ) == false 
	 * require.指定社員の管理職場をすべて取得する( 社員ID, 基準日 ) not empty
	 * $所属組織.職場グループID is empty
	 * require.指定職場の職場グループ所属情報を取得する( $管理職場IDリスト ) not empty
	 */
	@Test
	public void testGetWorkplaceGroup_4() {
		GeneralDate baseDate = GeneralDate.today();
		String empId = "empId";
		new Expectations() {
			{
				require.whetherThePersonInCharge(empId);
				result = false;
			}
		};
		this.testGetFromManagedWorkplace_3();
		new MockUp<WorkplaceGroupGettingService>() {
			@Mock
			public List<EmployeeAffiliation> get(WorkplaceGroupGettingService.Require require, GeneralDate date, List<String> employeeIDs) {
				return Arrays.asList(
						new EmployeeAffiliation(empId, Optional.empty(),  Optional.empty(), "workplaceID1",  Optional.empty()),
						new EmployeeAffiliation(empId, Optional.empty(),  Optional.empty(), "workplaceID3",  Optional.empty())
						
						);
			}
		};
		Map<String, ScopeReferWorkplaceGroup> result = GetWorkplaceGroupsAndEmpService.getWorkplaceGroup(require,
				baseDate, empId);
		assertThat(result.entrySet())
		.extracting(d->d.getKey(),d->d.getValue())
		.containsExactly(
				tuple( "workplaceGroupID1",ScopeReferWorkplaceGroup.ALL_EMPLOYEE),
				tuple( "workplaceGroupID3",ScopeReferWorkplaceGroup.ALL_EMPLOYEE));
	}
	
	/**
	 * require.指定社員の管理職場をすべて取得する( 社員ID, 基準日 ) not empty
	 * require.指定職場の職場グループ所属情報を取得する( $管理職場IDリスト ) not empty
	 */
	@Test
	public void testGetFromManagedWorkplace_3() {
		GeneralDate baseDate = GeneralDate.today();
		String empId = "empId";
		List<String> listWkpId = Arrays.asList("workplaceID1","workplaceID2","workplaceID3");
		List<AffWorkplaceGroup> listAffWorkplaceGroup = Arrays.asList(
				new AffWorkplaceGroup("workplaceGroupID3", "workplaceID3"),
				new AffWorkplaceGroup("workplaceGroupID1", "workplaceID1")
				);
		
		new Expectations() {
			{
				require.getAllManagedWorkplaces(empId, baseDate);
				result = listWkpId;

				require.getByListWKPID(listWkpId);
				result = listAffWorkplaceGroup;
			}
		};
			
		val instance = new GetWorkplaceGroupsAndEmpService();
		@SuppressWarnings("unchecked")
		Map<String, ScopeReferWorkplaceGroup> result = (Map<String, ScopeReferWorkplaceGroup>) NtsAssert.Invoke.privateMethod(
				instance, 
				"getFromManagedWorkplace",
				require,
				baseDate,
				empId
				);
		assertThat(result.entrySet())
		.extracting(d->d.getKey(),d->d.getValue())
		.containsExactly(
				tuple( "workplaceGroupID1",ScopeReferWorkplaceGroup.ALL_EMPLOYEE),
				tuple( "workplaceGroupID3",ScopeReferWorkplaceGroup.ALL_EMPLOYEE));
	}
	
	/**
	 * require.担当者かどうか( 社員ID ) == false 
	 * require.指定社員の管理職場をすべて取得する( 社員ID, 基準日 ) not empty
	 * $所属組織.職場グループID not empty && $参照可能職場グループ.containsKey( $所属組織.職場グループID ) is true
	 * require.指定職場の職場グループ所属情報を取得する( $管理職場IDリスト ) is empty
	 */
	@Test
	public void testGetWorkplaceGroup_5() {
		GeneralDate baseDate = GeneralDate.today();
		String empId = "empId";
		new Expectations() {
			{
				require.whetherThePersonInCharge(empId);
				result = false;
			}
		};
		this.testGetFromManagedWorkplace_3();
		new MockUp<WorkplaceGroupGettingService>() {
			@Mock
			public List<EmployeeAffiliation> get(WorkplaceGroupGettingService.Require require, GeneralDate date, List<String> employeeIDs) {
				return Arrays.asList(
						new EmployeeAffiliation(empId, Optional.empty(),  Optional.empty(), "workplaceID3",  Optional.of("workplaceGroupID3")),
						new EmployeeAffiliation(empId, Optional.empty(),  Optional.empty(), "workplaceID1",  Optional.empty())
						);
			}
		};
		Map<String, ScopeReferWorkplaceGroup> result = GetWorkplaceGroupsAndEmpService.getWorkplaceGroup(require,
				baseDate, empId);
		assertThat(result.entrySet())
		.extracting(d->d.getKey(),d->d.getValue())
		.containsExactly(
				tuple( "workplaceGroupID1",ScopeReferWorkplaceGroup.ALL_EMPLOYEE),tuple( "workplaceGroupID3",ScopeReferWorkplaceGroup.ALL_EMPLOYEE));
	}
	
	/**
	 * require.担当者かどうか( 社員ID ) == false 
	 * require.指定社員の管理職場をすべて取得する( 社員ID, 基準日 ) not empty
	 * $所属組織.職場グループID not empty && $参照可能職場グループ.containsKey( $所属組織.職場グループID ) is false
	 * require.指定職場の職場グループ所属情報を取得する( $管理職場IDリスト ) is empty
	 * 
	 *  require.社員参照範囲を取得する( 社員ID ) == EmployeeReferenceRangeImport.ONLY_MYSELF
	 */
	@Test
	public void testGetWorkplaceGroup_6() {
		GeneralDate baseDate = GeneralDate.today();
		String empId = "empId";
		List<String> listWkpId = Arrays.asList("workplaceID1","workplaceID2","workplaceID3");
		List<AffWorkplaceGroup> listAffWorkplaceGroup = Arrays.asList(new AffWorkplaceGroup("workplaceGroupID2", "workplaceID2"));
		new Expectations() {
			{
				require.whetherThePersonInCharge(empId);
				result = false;
				
				require.getAllManagedWorkplaces(empId, baseDate);
				result = listWkpId;

				require.getByListWKPID(listWkpId);
				result = listAffWorkplaceGroup;
				
				require.getEmployeeReferRangeOfLoginEmployees(empId);
				result = EmployeeReferenceRangeImport.ONLY_MYSELF;
				
			}
		};
		new MockUp<WorkplaceGroupGettingService>() {
			@Mock
			public List<EmployeeAffiliation> get(WorkplaceGroupGettingService.Require require, GeneralDate date, List<String> employeeIDs) {
				return Arrays.asList(new EmployeeAffiliation(empId, Optional.empty(),  Optional.empty(), "workplaceID2",  Optional.of("workplaceGroupID44")));
			}
		};
		Map<String, ScopeReferWorkplaceGroup> result = GetWorkplaceGroupsAndEmpService.getWorkplaceGroup(require,
				baseDate, empId);
		assertThat(result.entrySet())
		.extracting(d->d.getKey(),d->d.getValue())
		.containsExactly(
				tuple( "workplaceGroupID44",ScopeReferWorkplaceGroup.ONLY_ME),
				tuple( "workplaceGroupID2",ScopeReferWorkplaceGroup.ALL_EMPLOYEE));
	}
	
	/**
	 * require.担当者かどうか( 社員ID ) == false 
	 * require.指定社員の管理職場をすべて取得する( 社員ID, 基準日 ) not empty
	 * $所属組織.職場グループID not empty && $参照可能職場グループ.containsKey( $所属組織.職場グループID ) is false
	 * require.指定職場の職場グループ所属情報を取得する( $管理職場IDリスト ) is empty
	 * 
	 *  require.社員参照範囲を取得する( 社員ID ) != EmployeeReferenceRangeImport.ONLY_MYSELF
	 */
	@Test
	public void testGetWorkplaceGroup_7() {
		GeneralDate baseDate = GeneralDate.today();
		String empId = "empId";
		List<String> listWkpId = Arrays.asList("workplaceID1","workplaceID2","workplaceID3");
		List<AffWorkplaceGroup> listAffWorkplaceGroup = Arrays.asList(
				new AffWorkplaceGroup("workplaceGroupID3", "workplaceID3"),
				new AffWorkplaceGroup("workplaceGroupID2", "workplaceID2")
				);
		new Expectations() {
			{
				require.whetherThePersonInCharge(empId);
				result = false;
				
				require.getAllManagedWorkplaces(empId, baseDate);
				result = listWkpId;

				require.getByListWKPID(listWkpId);
				result = listAffWorkplaceGroup;
				
				require.getEmployeeReferRangeOfLoginEmployees(empId);
				result = EmployeeReferenceRangeImport.DEPARTMENT_ONLY;
			}
		};
		new MockUp<WorkplaceGroupGettingService>() {
			@Mock
			public List<EmployeeAffiliation> get(WorkplaceGroupGettingService.Require require, GeneralDate date, List<String> employeeIDs) {
				return Arrays.asList(
						new EmployeeAffiliation(empId, Optional.empty(),  Optional.empty(), "workplaceID3",  Optional.of("workplaceGroupID43")),
						new EmployeeAffiliation(empId, Optional.empty(),  Optional.empty(), "workplaceID2",  Optional.of("workplaceGroupID42"))
						
						);
			}
		};
		Map<String, ScopeReferWorkplaceGroup> result = GetWorkplaceGroupsAndEmpService.getWorkplaceGroup(require,
				baseDate, empId);
		assertThat(result.entrySet())
		.extracting(d->d.getKey(),d->d.getValue())
		.containsExactly(
				tuple( "workplaceGroupID43",ScopeReferWorkplaceGroup.ALL_EMPLOYEE),
				tuple( "workplaceGroupID3",ScopeReferWorkplaceGroup.ALL_EMPLOYEE),
				tuple( "workplaceGroupID2",ScopeReferWorkplaceGroup.ALL_EMPLOYEE)
				);
	}
	
	/**
	 * require.担当者かどうか( 社員ID ) == false 
	 * require.指定社員の管理職場をすべて取得する( 社員ID, 基準日 ) is empty
	 * $所属組織.職場グループID not empty && $参照可能職場グループ.containsKey( $所属組織.職場グループID ) is false
	 */
	@Test
	public void testGetWorkplaceGroup_8() {
		GeneralDate baseDate = GeneralDate.today();
		String empId = "empId";
		new Expectations() {
			{
				require.whetherThePersonInCharge(empId);
				result = false;
				
				require.getAllManagedWorkplaces(empId, baseDate);
				
				require.getEmployeeReferRangeOfLoginEmployees(empId);
				result = EmployeeReferenceRangeImport.DEPARTMENT_ONLY;
			}
		};
		new MockUp<WorkplaceGroupGettingService>() {
			@Mock
			public List<EmployeeAffiliation> get(WorkplaceGroupGettingService.Require require, GeneralDate date, List<String> employeeIDs) {
				return Arrays.asList(
						new EmployeeAffiliation(empId, Optional.empty(),  Optional.empty(), "workplaceID3",  Optional.of("workplaceGroupID43")),
						new EmployeeAffiliation(empId, Optional.empty(),  Optional.empty(), "workplaceID2",  Optional.of("workplaceGroupID42"))
						
						);
			}
		};
		Map<String, ScopeReferWorkplaceGroup> result = GetWorkplaceGroupsAndEmpService.getWorkplaceGroup(require,
				baseDate, empId);
		assertThat(result.entrySet())
		.extracting(d->d.getKey(),d->d.getValue())
		.containsExactly(
				tuple( "workplaceGroupID43",ScopeReferWorkplaceGroup.ALL_EMPLOYEE)
				);
	}
	
	
	

}
