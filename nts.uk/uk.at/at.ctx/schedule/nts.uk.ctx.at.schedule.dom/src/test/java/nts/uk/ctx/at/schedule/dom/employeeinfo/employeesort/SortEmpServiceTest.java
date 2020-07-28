package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortEmpService.Require;

@RunWith(JMockit.class)
public class SortEmpServiceTest {

	@Injectable
	private Require require;
	
	/**
	 * require.並び替え設定を取得する() is empty
	 */
	@Test
	public void testSortEmpTheirOrder() {
		GeneralDate ymd = GeneralDate.today();
		List<String> lstEmpId = Arrays.asList("emp1","emp2");
		
		new Expectations() {
			{
				require.get();
			}
		};
		
		List<String> listData = SortEmpService.sortEmpTheirOrder(require, ymd, lstEmpId);
		
		assertThat(listData)
		.extracting(d->d)
		.containsExactly("emp1","emp2");
	}

}
