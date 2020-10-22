package nts.uk.ctx.at.auth.dom.employmentrole;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.auth.dom.employmentrole.GetListOfWorkplacesService.Require;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.RollInformation;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.WorkPlaceAuthorityDto;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.WorkplaceManagerDto;


/**
 * @author laitv
 */

@RunWith(JMockit.class)
public class GetListOfWorkplacesServiceTest { 

	@Injectable
	private Require require;
	
	/**
	 * [prv-1] ロールから就業確定できる職場を取得する
	 * check function getWorkplaceWhereConfirmWorkFromRole
	 * check require.getUserID is empty
	 */
	@Test
	public void testGetListOfWorkplacesServiceTest_1() {
		String companyId = "companyId";
		String employeeId = "employeeId";
		GeneralDate baseDate = GeneralDate.today();
		Integer closureId = 1; // dummy
		
		new Expectations() {
			{
				require.getUserID(employeeId);
			}
		};
		assertThat(GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, baseDate).isEmpty()).isTrue();
	}
	
	/**
	 * [prv-1] ロールから就業確定できる職場を取得する
	 * check function getWorkplaceWhereConfirmWorkFromRole
	 * check require.getUserID is not empty
	 * check require.getRole is Empty
	 */
	@Test
	public void testGetListOfWorkplacesServiceTest_2() {
		String companyId = "companyId";  // dummy
		String employeeId = "employeeId";  // dummy
		GeneralDate baseDate = GeneralDate.today();  // dummy
		Integer closureId = 1; // dummy
		int rollType = RoleType.EMPLOYMENT.value;  // dummy 
		
		new Expectations() {
			{
				require.getUserID(employeeId);
				result = Optional.of("userId");
				
				require.getRole("userId", rollType, baseDate, companyId);
				
			}
		};
		assertThat(GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, baseDate).isEmpty()).isTrue();
	}
	
	/**
	 * [prv-1] ロールから就業確定できる職場を取得する
	 * check function getWorkplaceWhereConfirmWorkFromRole
	 * check require.getUserID is not empty
	 * check require.getRole is not Empty
	 */
	@Test
	public void testGetListOfWorkplacesServiceTest_3() {
		String companyId = "companyId"; // dummy
		String employeeId = "employeeId"; // dummy
		GeneralDate baseDate = GeneralDate.today(); // dummy
		Integer closureId = 1; // dummy
		int rollType = RoleType.EMPLOYMENT.value; // dummy
		
		new Expectations() {
			{
				require.getUserID(employeeId);
				result = Optional.of("userId");
				
				require.getRole("userId", rollType, baseDate, companyId);
				result = Optional.of(new RollInformation());
			}
		};
		assertThat(GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, baseDate).isEmpty()).isTrue();
	}
	
	
	/**
	 * [prv-1] ロールから就業確定できる職場を取得する
	 * check function getWorkplaceWhereConfirmWorkFromRole
	 * check require.getUserID is not empty
	 * check require.getRole is not Empty
	 * check require.getWorkAuthority is Empty
	 */
	@Test
	public void testGetListOfWorkplacesServiceTest_4() {
		String companyId = "companyId"; // dummy
		String employeeId = "employeeId"; // dummy
		GeneralDate baseDate = GeneralDate.today(); // dummy
		Integer closureId = 1; // dummy
		int rollType = RoleType.EMPLOYMENT.value; // dummy
		int functionNo = 2; 
		
		new Expectations() {
			{
				require.getUserID(employeeId);
				result = Optional.of("userId");
				
				require.getRole("userId", rollType, baseDate, companyId);
				result = Optional.of(new RollInformation());
				
				require.getWorkAuthority((String) any, (String) any, (Integer) any);
			}
		};
		assertThat(GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, baseDate).isEmpty()).isTrue();
	}
	
	/**
	 * [prv-1] ロールから就業確定できる職場を取得する
	 * check function getWorkplaceWhereConfirmWorkFromRole
	 * check require.getUserID is not empty
	 * check require.getRole is not Empty
	 * check require.getWorkAuthority is not Empty  AND result.workPlaceAuthority.get().isAvailability() = true
	 */
	@Test
	public void testGetListOfWorkplacesServiceTest_5() {
		String companyId = "companyId"; // dummy
		String employeeId = "employeeId"; // dummy
		GeneralDate baseDate = GeneralDate.today(); // dummy
		Integer closureId = 1; // dummy
		int rollType = RoleType.EMPLOYMENT.value; // dummy
		int functionNo = 2;
		
		new Expectations() {
			{
				require.getUserID(employeeId);
				result = Optional.of("userId");
				
				require.getRole("userId", rollType, baseDate, companyId);
				result = Optional.of(new RollInformation(false, "roleId"));
				
				require.getWorkAuthority("roleId", companyId, functionNo);
				result = Optional.of(new WorkPlaceAuthorityDto("roleId", "companyId", functionNo, true));
			}
		};
		assertThat(GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, baseDate).isEmpty()).isTrue();
	}
	
	/**
	 * [prv-1] ロールから就業確定できる職場を取得する
	 * check function getWorkplaceWhereConfirmWorkFromRole
	 * check require.getUserID is not empty
	 * check require.getRole is not Empty
	 * check require.getWorkAuthority is not Empty AND AND result.workPlaceAuthority.get().isAvailability() = false
	 */
	@Test
	public void testGetListOfWorkplacesServiceTest_6() {
		String companyId = "companyId"; // dummy
		String employeeId = "employeeId"; // dummy
		GeneralDate baseDate = GeneralDate.today(); // dummy
		Integer closureId = 1; // dummy
		int rollType = RoleType.EMPLOYMENT.value; // dummy
		int functionNo = 2;
		
		new Expectations() {
			{
				require.getUserID(employeeId);
				result = Optional.of("userId");
				
				require.getRole("userId", rollType, baseDate, companyId);
				result = Optional.of(new RollInformation(false, "roleId"));
				
				require.getWorkAuthority("roleId", companyId, functionNo);
				result = Optional.of(new WorkPlaceAuthorityDto("roleId", "companyId", functionNo, false));
			}
		};
		assertThat(GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, baseDate).isEmpty()).isTrue();
	}
	
	
	/**
	 * [prv-1] ロールから就業確定できる職場を取得する
	 * check function getWorkplaceWhereConfirmWorkFromRole
	 * check require.getUserID is not empty
	 * check require.getRole is not Empty
	 * check require.getWorkAuthority is not Empty AND result.workPlaceAuthority.get().isAvailability() = true
	 * rollInformation.get().getRoleCharge() = true
	 */
	@Test
	public void testGetListOfWorkplacesServiceTest_7() {
		String companyId = "companyId"; // dummy
		String employeeId = "employeeId"; // dummy
		GeneralDate baseDate = GeneralDate.today(); // dummy
		Integer closureId = 1; // dummy 
		int rollType = RoleType.EMPLOYMENT.value; // dummy
		int functionNo = 2;
		
		new Expectations() {
			{
				require.getUserID(employeeId);
				result = Optional.of("userId");
				
				require.getRole("userId", rollType, baseDate, companyId);
				result = Optional.of(new RollInformation(true, "roleId"));
				
				require.getWorkAuthority("roleId", companyId, functionNo);
				result = Optional.of(new WorkPlaceAuthorityDto("roleId", "companyId", functionNo, true));
			}
		};
		assertThat(GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, baseDate).isEmpty()).isTrue();
	}
	
	
	/**
	 * [prv-1] ロールから就業確定できる職場を取得する
	 * check function getWorkplaceWhereConfirmWorkFromRole
	 * check require.getUserID is not empty
	 * check require.getRole is not Empty
	 * check require.getWorkAuthority is not Empty AND result.workPlaceAuthority.get().isAvailability() = true
	 * rollInformation.get().getRoleCharge() = true
	 * check require.getEmployeeReferenceRange is Empty
	 */
	@Test
	public void testGetListOfWorkplacesServiceTest_8() {
		String companyId = "companyId"; // dummy
		String employeeId = "employeeId"; // dummy
		GeneralDate baseDate = GeneralDate.today(); // dummy
		Integer closureId = 1; // dummy 
		int rollType = RoleType.EMPLOYMENT.value; // dummy
		int functionNo = 2;
		
		new Expectations() {
			{
				require.getUserID(employeeId);
				result = Optional.of("userId"); 
				
				require.getRole("userId", rollType, baseDate, companyId);
				result = Optional.of(new RollInformation(true, "roleId"));
				
				require.getWorkAuthority("roleId", companyId, functionNo);
				result = Optional.of(new WorkPlaceAuthorityDto("roleId", "companyId", functionNo, true));
				
				//require.getEmployeeReferenceRange("roleId");
			}
		};
		assertThat(GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, baseDate).isEmpty()).isTrue();
	}
	
	
	/**
	 * [prv-1] ロールから就業確定できる職場を取得する
	 * check function getWorkplaceWhereConfirmWorkFromRole
	 * check require.getUserID is not empty
	 * check require.getRole is not Empty
	 * check require.getWorkAuthority is not Empty AND result.workPlaceAuthority.get().isAvailability() = true
	 * rollInformation.get().getRoleCharge() = true
	 * check require.getEmployeeReferenceRange is not Empty
	 */
	@Test
	public void testGetListOfWorkplacesServiceTest_9() {
		String companyId = "companyId"; // dummy
		String employeeId = "employeeId"; // dummy
		GeneralDate baseDate = GeneralDate.today(); // dummy
		Integer closureId = 1; // dummy 
		int rollType = RoleType.EMPLOYMENT.value; // dummy
		int functionNo = 2;
		
		new Expectations() {
			{ 
				require.getUserID(employeeId);
				result = Optional.of("userId");
				
				require.getRole("userId", rollType, baseDate, companyId);
				result = Optional.of(new RollInformation(false, "roleId"));
				
				require.getWorkAuthority("roleId", companyId, functionNo);
				result = Optional.of(new WorkPlaceAuthorityDto("roleId", "companyId", functionNo, true));
				
				require.getEmployeeReferenceRange("roleId");
				result = OptionalInt.of(1);
			}
		};
		assertThat(GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, baseDate).isEmpty()).isTrue();
	}
	
	
	/**
	 * [prv-2] 管理職場を取得する
	 * check function getManagedWorkplace
	 * check require.getWorkplaceManager is empty
	 */
	@Test
	public void testGetListOfWorkplacesServiceTest_10() {
		String companyId = "companyId"; // dummy
		String employeeId = "employeeId"; // dummy
		GeneralDate baseDate = GeneralDate.today(); // dummy
		Integer closureId = 1; // dummy 
		
		new Expectations() {
			{ 
				require.getWorkplaceManager(employeeId, baseDate);
			}
		};
		assertThat(GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, baseDate).isEmpty()).isTrue();
	}
	
	/**
	 * [prv-2] 管理職場を取得する
	 * check function getManagedWorkplace
	 * check require.getWorkplaceManager is not empty
	 */
	@Test
	public void testGetListOfWorkplacesServiceTest_11() {
		String companyId = "companyId"; // dummy
		String employeeId = "employeeId"; // dummy
		GeneralDate baseDate = GeneralDate.today(); // dummy
		Integer closureId = 1; // dummy 
		
		new Expectations() {
			{ 
				require.getWorkplaceManager(employeeId, baseDate);
				result = Arrays.asList(new WorkplaceManagerDto(
						"workplaceManagerId", 
						"employeeId", 
						"workplaceId", 
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
			}
		};
		assertThat(GetListOfWorkplacesService.get(require, companyId, closureId, employeeId, baseDate).isEmpty()).isTrue();
	}
	
	

}
