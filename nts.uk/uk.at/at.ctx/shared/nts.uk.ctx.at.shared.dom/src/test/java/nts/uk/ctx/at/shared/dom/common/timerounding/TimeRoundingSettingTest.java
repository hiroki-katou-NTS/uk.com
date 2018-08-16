package nts.uk.ctx.at.shared.dom.common.timerounding;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TimeRoundingSettingTest {

	@Test
	public void round() {
		
		//10380 ← 173:00
		//10440　← 174:00
		
//		//1分切り捨て（丸めない場合）
//		TimeRoundingSetting timeRoundingSetting1 = new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN);
//		//任意項目での計算
//		int testTime1 = 10425 - 20850;
//		int resut1 = timeRoundingSetting1.round(testTime1);
//		//is() ← の中が期待値
//		assertThat(resut1, is(-10425));
//		
//		
//		//30分切り捨て
//		TimeRoundingSetting timeRoundingSetting2 = new TimeRoundingSetting(Unit.ROUNDING_TIME_30MIN, Rounding.ROUNDING_DOWN);
//		int testTime2 = 10425 - 20850;
//		int resut2 = timeRoundingSetting2.round(testTime2);
//		assertThat(resut2, is(-10440));
//		
//		
//		//30分切り上げ
//		TimeRoundingSetting timeRoundingSetting3 = new TimeRoundingSetting(Unit.ROUNDING_TIME_30MIN, Rounding.ROUNDING_UP);
//		int testTime3 = 10425 - 20850;
//		int resut3 = timeRoundingSetting3.round(testTime3);
//		assertThat(resut3, is(-10410));
//		
//		
//		//30分未満切捨、以上切上
//		TimeRoundingSetting timeRoundingSetting4 = new TimeRoundingSetting(Unit.ROUNDING_TIME_30MIN, Rounding.ROUNDING_DOWN_OVER);
//		//29分を丸め
//		int testTime4 = 10425 - 20834;
//		int resut4 = timeRoundingSetting4.round(testTime4);
//		assertThat(resut4, is(-10380));	
//		//30分を丸め
//		int testTime5 = 10425 - 20835;
//		int resut5 = timeRoundingSetting4.round(testTime5);
//		assertThat(resut5, is(-10380));
//		//31分を丸め
//		int testTime6 = 10425 - 20836;
//		int resut6 = timeRoundingSetting4.round(testTime6);
//		assertThat(resut6, is(-10440));
//		
//		
//		//15分未満切捨、以上切上
//		TimeRoundingSetting timeRoundingSetting5 = new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_DOWN_OVER);
//		//14分を丸め
//		int testTime7 = 10425 - 20819;
//		int resut7 = timeRoundingSetting5.round(testTime7);
//		assertThat(resut7, is(-10380));
//		//15分を丸め
//		int testTime8 = 10425 - 20820;
//		int resut8 = timeRoundingSetting5.round(testTime8);
//		assertThat(resut8, is(-10380));
//		//16分を丸め
//		int testTime9 = 10425 - 20821;
//		int resut9 = timeRoundingSetting5.round(testTime9);
//		assertThat(resut9, is(-10410));
//		//30分を丸め
//		int testTime10 = 10425 - 20835;
//		int resut10 = timeRoundingSetting5.round(testTime10);
//		assertThat(resut10, is(-10410));
//		//31分を丸め
//		int testTime11 = 10425 - 20836;
//		int resut11 = timeRoundingSetting5.round(testTime11);
//		assertThat(resut11, is(-10410));
//		//45分を丸め
//		int testTime12 = 10425 - 20850;
//		int resut12 = timeRoundingSetting5.round(testTime12);
//		assertThat(resut12, is(-10410));
//		//46分を丸め
//		int testTime13 = 10425 - 20851;
//		int resut13 = timeRoundingSetting5.round(testTime13);
//		assertThat(resut13, is(-10440));
		
		
		
		//30分切り捨て
		TimeRoundingSetting timeRoundingSetting = new TimeRoundingSetting(Unit.ROUNDING_TIME_6MIN, Rounding.ROUNDING_DOWN);
		int testTime = 543;
		int resut = timeRoundingSetting.round(testTime);
		assertThat(resut, is(540));
		
		
		
		

	}
	
}
