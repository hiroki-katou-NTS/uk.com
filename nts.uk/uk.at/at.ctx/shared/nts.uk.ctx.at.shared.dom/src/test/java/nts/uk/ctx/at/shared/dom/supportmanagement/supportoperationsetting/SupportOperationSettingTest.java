package nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.license.option.OptionLicense;

/**
 * 応援の運用設定のUTコード
 * 
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class SupportOperationSettingTest {

	@Injectable
	private SupportOperationSetting.Require require;


	@Test
	public void getters() {

		val supportOperationSetting = new SupportOperationSetting(true, true, new MaximumNumberOfSupport(20));

		NtsAssert.invokeGetters(supportOperationSetting);

	}

	/**
	 * test [1] 応援に対応する日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIdByWork() {
		SupportOperationSetting instance = new SupportOperationSetting(true, true, new MaximumNumberOfSupport(20)); // dummy
		List<Integer> listAttdId = new ArrayList<>();
		listAttdId = instance.getDaiLyAttendanceIdByWork();
		assertThat(listAttdId.containsAll(Arrays.asList(921, 2070, 931, 2290, 941, 2330, 961, 2350, 971, 2370, 981,
				2390, 991, 2410, 1001, 2430, 1011, 2450, 1021, 2470, 1031, 2490, 1041, 2510, 1051, 2530, 1061, 2550,
				1071, 2570, 1081, 2590, 1091, 2610, 1101, 2630, 1111, 2650))).isTrue();
	}

	/**
	 * test [2] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIdNotAvailable() {
		// [3] 応援を利用できるか(require) == false
		SupportOperationSetting instance = new SupportOperationSetting(false, true, new MaximumNumberOfSupport(20));

		new Expectations() {
			{
				require.getOptionLicense();
				result = new OptionLicense() {
				};
			}
		};
		List<Integer> listAttdId = new ArrayList<>();
		listAttdId = instance.getDaiLyAttendanceIdNotAvailable(require);
		assertThat(listAttdId.containsAll(Arrays.asList(921, 2070, 931, 2290, 941, 2330, 961, 2350, 971, 2370, 981,
				2390, 991, 2410, 1001, 2430, 1011, 2450, 1021, 2470, 1031, 2490, 1041, 2510, 1051, 2530, 1061, 2550,
				1071, 2570, 1081, 2590, 1091, 2610, 1101, 2630, 1111, 2650))).isTrue();

		/**
		 * [3] 応援を利用できるか(require) == true
		 * @一日の最大応援回数 == 20
		 */
		instance = new SupportOperationSetting(true, true, new MaximumNumberOfSupport(20));
		listAttdId = instance.getDaiLyAttendanceIdNotAvailable(require);
		assertThat(listAttdId).isEmpty();

		/**
		 * [3] 応援を利用できるか(require) == true
		 * @一日の最大応援回数 < 20
		 */
		instance = new SupportOperationSetting(true, true, new MaximumNumberOfSupport(15));
		listAttdId = instance.getDaiLyAttendanceIdNotAvailable(require);
		assertThat(listAttdId.containsAll(Arrays.asList(1071, 1072, 1073, 1074, 1075, 1076, 1077, 1078, 1079, 1080, 
				1081, 2590, 1091, 2610, 1101, 2630, 1111, 2650))).isTrue();
		
		assertThat(listAttdId.containsAll(Arrays.asList(2191, 2211, 2231, 2192, 2212, 2232))).isFalse();
	}

	/**
	 * test [3] 実績で作業を利用できるか
	 */
	@Test
	public void testCanWorkUsedWithAchievements() {
		// @利用するか == true
		SupportOperationSetting instance = new SupportOperationSetting(true, true, new MaximumNumberOfSupport(20));

		new Expectations() {
			{
				require.getOptionLicense();
				result = new OptionLicense() {
				};
			}
		};
		boolean result = instance.canUsedSupport(require);
		assertThat(result).isTrue();
		// @利用するか == false
		instance = new SupportOperationSetting(false, true, new MaximumNumberOfSupport(20));
		result = instance.canUsedSupport(require);
		assertThat(result).isFalse();

	}
}
