package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CorrectDeadline;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class ScheModifyStartDateServiceTest {

	@Injectable
	private ScheModifyStartDateService.Require require;
	
	/**
	 * case : スケジュール修正の修正期限が取得できない(データがない)
	 * Expected : GeneralDateの最小値(1900/1/1)
	 */
	@Test
	public void noneData_Test() {
		
		new Expectations() {{
			require.getScheAuthModifyDeadline("dummyId");
		}};
		
		val actual = ScheModifyStartDateService.getModifyStartDate(require, "dummyId");
		
		assertThat(actual).isEqualTo(GeneralDate.min());
	}

	/**
	 * case : スケジュール修正の修正期限.利用区分 = しない
	 * Expected : GeneralDateの最小値(1900/1/1)
	 */
	@Test
	public void notUse_Test() {
		
		new Expectations() {{
			require.getScheAuthModifyDeadline("dummyId");
			result = Optional.of(new ScheAuthModifyDeadline(
					"dummyId", 
					NotUseAtr.NOT_USE, 
					new CorrectDeadline(0)));
		}};
		
		val actual = ScheModifyStartDateService.getModifyStartDate(require, "dummyId");

		assertThat(actual).isEqualTo(GeneralDate.min());
	}
	
	/**
	 * case : スケジュール修正の修正期限.利用区分 = する, システム日付 = 2020/9/17, 	修正期限= 3
	 * Expected : 2020/9/21
	 */
	@Test
	public void standard() {
		
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 9, 17, 0, 0, 0);
		
		new Expectations() {{
			require.getScheAuthModifyDeadline("dummyId");
			result = Optional.of(new ScheAuthModifyDeadline(
					"dummyId", 
					NotUseAtr.USE, 
					new CorrectDeadline(3)));
		}};
		
		val actual = ScheModifyStartDateService.getModifyStartDate(require, "dummyId");

		assertThat(actual).isEqualTo(GeneralDate.ymd(2020, 9, 21));
	}
}
