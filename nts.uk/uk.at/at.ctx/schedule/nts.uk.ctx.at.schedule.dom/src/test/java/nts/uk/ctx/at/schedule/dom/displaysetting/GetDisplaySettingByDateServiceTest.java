package nts.uk.ctx.at.schedule.dom.displaysetting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class GetDisplaySettingByDateServiceTest {

	@Injectable
	private GetDisplaySettingByDateService.Require require;
	
	/**
	 * case : 組織別スケジュール修正日付別の表示設定が存在する
	 */
	@Test
	public void getSuccess_Org() {
		new Expectations() {{
			require.getOrg(Helper.GetDispByDateService.TargetOrg.DUMMY);
			result = Optional.of (Helper.GetDispByDateService.DispsetOrg.create(
					new DisplaySettingByDate(
							DisplayRangeType.DISP48H, 
							new DisplayStartTime(1), 
							new DisplayStartTime(10))));
		}};
		
		val actual = GetDisplaySettingByDateService.get(require, Helper.GetDispByDateService.TargetOrg.DUMMY);
		
		assertThat (actual.getDispRange()).isEqualTo(DisplayRangeType.DISP48H);
		assertThat (actual.getDispStart()).isEqualTo(new DisplayStartTime(1));
		assertThat (actual.getInitDispStart()).isEqualTo(new DisplayStartTime(10));
	}

	/**
	 * case : 組織別スケジュール修正日付別の表示設定が存在しない、会社別スケジュール修正日付別の表示設定が存在する
	 */
	@Test
	public void getSuccess_Com() {
		new Expectations() {{
			require.getOrg(Helper.GetDispByDateService.TargetOrg.DUMMY);
			
			require.getCmp();
			result = Optional.of (Helper.GetDispByDateService.DispsetCom.create(
					new DisplaySettingByDate(
							DisplayRangeType.DISP24H, 
							new DisplayStartTime(0), 
							new DisplayStartTime(12))));
		}};
		
		val actual = GetDisplaySettingByDateService.get(require, Helper.GetDispByDateService.TargetOrg.DUMMY);
		
		assertThat (actual.getDispRange()).isEqualTo(DisplayRangeType.DISP24H);
		assertThat (actual.getDispStart()).isEqualTo(new DisplayStartTime(0));
		assertThat (actual.getInitDispStart()).isEqualTo(new DisplayStartTime(12));
	}
	
	/**
	 * case : 組織別、会社スケジュール修正日付別の表示設定がどちらも存在しない
	 */
	@Test
	public void getEmpty() {
		new Expectations() {{
			require.getOrg(Helper.GetDispByDateService.TargetOrg.DUMMY);
			
			require.getCmp();
		}};
		
		val actual = GetDisplaySettingByDateService.get(require, Helper.GetDispByDateService.TargetOrg.DUMMY);
		
		assertThat (actual.getDispRange()).isEqualTo(DisplayRangeType.DISP24H);
		assertThat (actual.getDispStart()).isEqualTo(new DisplayStartTime(0));
		assertThat (actual.getInitDispStart()).isEqualTo(new DisplayStartTime(0));
	}
}
