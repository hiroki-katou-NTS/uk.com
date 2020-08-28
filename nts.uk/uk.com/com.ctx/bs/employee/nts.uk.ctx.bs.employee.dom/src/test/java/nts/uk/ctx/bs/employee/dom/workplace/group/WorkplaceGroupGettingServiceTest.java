package nts.uk.ctx.bs.employee.dom.workplace.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.EmployeeAffiliation;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupGettingService.Require;

@RunWith(JMockit.class)
public class WorkplaceGroupGettingServiceTest {

	@Injectable
	private Require require;
	/**
	 *  require.職場グループ所属情報を取得する( $職場IDリスト ) is empty
	 */
	@Test
	public void testGet() {
		GeneralDate date = GeneralDate.today();
		List<String> employeeIDs = Arrays.asList("emp1","emp2","emp3");
		String wkp = "wkp1";
		new Expectations() {
			{
				require.getAffWkpHistItemByEmpDate(anyString, date);
				result = wkp;
				
				require.getWGInfo(Arrays.asList(wkp));
			}
		};
		
		List<EmployeeAffiliation> datas = WorkplaceGroupGettingService.get(require, date, employeeIDs);
		assertThat(datas)
		.extracting(d->d.getEmployeeID(),d->d.getEmployeeCode(),d->d.getBusinessName(),d->d.getWorkplaceID(),d->d.getWorkplaceGroupID())
		.containsExactly(
				tuple(employeeIDs.get(0),Optional.empty(),Optional.empty(),wkp,Optional.empty()),
				tuple(employeeIDs.get(1),Optional.empty(),Optional.empty(),wkp,Optional.empty()),
				tuple(employeeIDs.get(2),Optional.empty(),Optional.empty(),wkp,Optional.empty())
				);
	}
	
	/**
	 *  require.職場グループ所属情報を取得する( $職場IDリスト ) is not empty
	 */
	@Test
	public void testGet_1() {
		GeneralDate date = GeneralDate.today();
		List<String> employeeIDs = Arrays.asList("emp1","emp2","emp3");
		String wkp = "wkp1";
		String wkpGroup = "wKPGRPID";
		List<AffWorkplaceGroup> listAffWorkplaceGroup = Arrays.asList(
				new AffWorkplaceGroup(wkpGroup, wkp) 
				
				);
		
		new Expectations() {
			{
				require.getAffWkpHistItemByEmpDate(anyString, date);
				result = wkp;
				
				require.getWGInfo(Arrays.asList(wkp));
				result = listAffWorkplaceGroup;
			}
		};
		
		List<EmployeeAffiliation> datas = WorkplaceGroupGettingService.get(require, date, employeeIDs);
		assertThat(datas)
		.extracting(d->d.getEmployeeID(),d->d.getEmployeeCode(),d->d.getBusinessName(),d->d.getWorkplaceID(),d->d.getWorkplaceGroupID())
		.containsExactly(
				tuple(employeeIDs.get(0),Optional.empty(),Optional.empty(),wkp,Optional.of(wkpGroup)),
				tuple(employeeIDs.get(1),Optional.empty(),Optional.empty(),wkp,Optional.of(wkpGroup)),
				tuple(employeeIDs.get(2),Optional.empty(),Optional.empty(),wkp,Optional.of(wkpGroup))
				);
	}

}
