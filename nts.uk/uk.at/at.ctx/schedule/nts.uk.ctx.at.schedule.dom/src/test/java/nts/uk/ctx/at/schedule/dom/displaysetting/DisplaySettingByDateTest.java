package nts.uk.ctx.at.schedule.dom.displaysetting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;

/**
 * VO : スケジュール修正日付別の表示設定 のテスト
 * @author hiroko_miura
 *
 */
public class DisplaySettingByDateTest {

	/**
	 * case : 開始時刻 > 初期表示の開始時刻
	 * 期待値 : BusinessException　Msg_1804
	 */
	@Test
	public void test_create_startMorethanInit() {
		NtsAssert.businessException("Msg_1804", () -> {
			DisplaySettingByDate.create(DisplayRangeType.DISP24H, new DisplayStartTime(2), new DisplayStartTime(1));
		});
	}
	
	/**
	 * case : 開始時刻 == 初期表示の開始時刻
	 * 期待値 : BusinessException　ではない
	 */
	@Test
	public void test_create_initEqualStart() {
		val range = DisplayRangeType.DISP24H;
		val start = new DisplayStartTime(1);
		val init = new DisplayStartTime(1);
		
		val dispSet = DisplaySettingByDate.create(range, start, init);
		
		assertThat(dispSet.getDispRange()).isEqualTo(range);
		assertThat(dispSet.getDispStart()).isEqualTo(start);
		assertThat(dispSet.getInitDispStart()).isEqualTo(init);
	}
	
	/**
	 * case : 開始時刻 < 初期表示の開始時刻
	 * 期待値 : BusinessException　ではない
	 */
	@Test
	public void test_create_startLessthanInit() {
		val range = DisplayRangeType.DISP48H;
		val start = new DisplayStartTime(11);
		val init = new DisplayStartTime(12);
		
		val dispSet = DisplaySettingByDate.create(range, start, init);
		
		assertThat(dispSet.getDispRange()).isEqualTo(range);
		assertThat(dispSet.getDispStart()).isEqualTo(start);
		assertThat(dispSet.getInitDispStart()).isEqualTo(init);
	}
}
