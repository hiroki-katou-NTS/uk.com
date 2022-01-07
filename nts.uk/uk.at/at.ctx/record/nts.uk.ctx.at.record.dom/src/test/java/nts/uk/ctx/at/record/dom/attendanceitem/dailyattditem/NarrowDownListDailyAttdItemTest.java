package nts.uk.ctx.at.record.dom.attendanceitem.dailyattditem;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationMethod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.MaximumNumberOfSupport;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;

@RunWith(JMockit.class)
public class NarrowDownListDailyAttdItemTest {
	
	@Injectable
	private NarrowDownListDailyAttdItem.Require require;

	/**
	 * test [prv-1] 利用できない応援作業項目を取得する
	 * require.応援の運用設定を取得する(会社ID) is not empty
	 */
	@Test
	public void testGetSupportWorkItemNotAvailable() {
		String companyId = "companyId";
		SupportOperationSetting domain = new SupportOperationSetting(true, true, new MaximumNumberOfSupport(20));
		new Expectations() {{
			require.getSupportOperationSetting( anyString );
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getSupportWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(921, 2070, 931, 2290, 941, 2330, 961, 2350, 971, 2370, 981,
				2390, 991, 2410, 1001, 2430, 1011, 2450, 1021, 2470, 1031, 2490, 1041, 2510, 1051, 2530, 1061, 2550,
				1071, 2570, 1081, 2590, 1091, 2610, 1101, 2630, 1111, 2650))).isTrue();
	}
	
	/**
	 * test [prv-1] 利用できない応援作業項目を取得する
	 * require.応援の運用設定を取得する(会社ID) is empty
	 * require.作業運用設定を取得する(会社ID) is not empty
	 */
	@Test
	public void testGetSupportWorkItemNotAvailable_2() {
		String companyId = "companyId";
		TaskOperationSetting domain = new TaskOperationSetting(TaskOperationMethod.DO_NOT_USE); 
		new Expectations() {{
			require.getSupportOperationSetting( anyString );
			
			require.getTasksOperationSetting(companyId);
			result = Optional.of(domain);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getSupportWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result.containsAll(Arrays.asList(921, 2070, 931, 2290, 941, 2330, 961, 2350, 971, 2370, 981,
				2390, 991, 2410, 1001, 2430, 1011, 2450, 1021, 2470, 1031, 2490, 1041, 2510, 1051, 2530, 1061, 2550,
				1071, 2570, 1081, 2590, 1091, 2610, 1101, 2630, 1111, 2650))).isTrue();
	}
	
	/**
	 * test [prv-1] 利用できない応援作業項目を取得する
	 * require.応援の運用設定を取得する(会社ID) is empty
	 * require.作業運用設定を取得する(会社ID) is empty
	 */
	@Test
	public void testGetSupportWorkItemNotAvailable_3() {
		String companyId = "companyId";
		new Expectations() {{
			require.getSupportOperationSetting( anyString );
			
			require.getTasksOperationSetting(companyId);
		}};
		List<Integer>  result = NtsAssert.Invoke.privateMethod(new NarrowDownListDailyAttdItem(),
							"getSupportWorkItemNotAvailable"
							, require,companyId
						);
		assertThat(result).isEmpty();
	}

}
