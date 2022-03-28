package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings;


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

@RunWith(JMockit.class)
public class TaskOperationSettingTest {
	
	@Injectable
	private TaskOperationSetting.Require require;

    @Test
    public void testGetter() {
        val instance = new TaskOperationSetting(
                TaskOperationMethod.DO_NOT_USE
        );
        NtsAssert.invokeGetters(instance);

    }
    /**
     * test [1] 残業枠NOに対応する日次の勤怠項目を取得する
     */
    @Test
	public void testGetDaiLyAttendanceIdByWork() {
		TaskOperationSetting instance = new TaskOperationSetting(TaskOperationMethod.DO_NOT_USE); //dummy
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
    	//@作業運用方法 <> 実績で利用
		TaskOperationSetting instance = new TaskOperationSetting(TaskOperationMethod.DO_NOT_USE); 
		
		new Expectations() {
			{
				require.getOptionLicense();
				result = new OptionLicense() {};
			}
		};
		List<Integer> listAttdId = new ArrayList<>();
		listAttdId = instance.getDaiLyAttendanceIdNotAvailable(require);
		assertThat(listAttdId.containsAll(Arrays.asList(921, 2070, 931, 2290, 941, 2330, 961, 2350, 971, 2370, 981,
				2390, 991, 2410, 1001, 2430, 1011, 2450, 1021, 2470, 1031, 2490, 1041, 2510, 1051, 2530, 1061, 2550,
				1071, 2570, 1081, 2590, 1091, 2610, 1101, 2630, 1111, 2650))).isTrue();
		//@作業運用方法  ==  実績で利用
		instance = new TaskOperationSetting(TaskOperationMethod.USED_IN_ACHIEVENTS);
		listAttdId = instance.getDaiLyAttendanceIdNotAvailable(require);
		assertThat(listAttdId).isEmpty();
	}
    
    /**
     * test [3] 実績で作業を利用できるか
     */
    @Test
	public void testCanWorkUsedWithAchievements() {
    	//@作業運用方法 <> 実績で利用
		TaskOperationSetting instance = new TaskOperationSetting(TaskOperationMethod.DO_NOT_USE);
		
		new Expectations() {
			{
				require.getOptionLicense();
				result = new OptionLicense() {};
			}
		};
		
		
		boolean result  = instance.canWorkUsedWithAchievements(require);
		assertThat(result).isFalse();
		
		//@作業運用方法  ==  実績で利用
		instance = new TaskOperationSetting(TaskOperationMethod.USED_IN_ACHIEVENTS);
		result  = instance.canWorkUsedWithAchievements(require);
		assertThat(result).isTrue();
	}

}
