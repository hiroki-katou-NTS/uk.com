package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import lombok.val;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CorrectDeadline;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * AR : スケジュール修正の修正期限 のテスト
 * @author hiroko_miura
 *
 */
public class ScheAuthModifyDeadlineTest {

	@Before
	public void dummySystemdate () {
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 9, 1, 0, 0, 0);
	}
	
	/**
	 * case : 修正期限利用しない、修正期限 =3
	 * Expected : 日付の最小値
	 */
	@Test
	public void modifiableDate_notUse() {
		val actual = new ScheAuthModifyDeadline("test", NotUseAtr.NOT_USE, new CorrectDeadline(3));
		
		assertThat(actual.modifiableDate()).isEqualTo(GeneralDate.min());
	}
	
	/**
	 * case : システム日付 = 2020/9/1、修正期限利用する、修正期限 =最小値(0)
	 * Expected : 2020/9/2
	 */
	@Test
	public void modifiableDate_min() {
		val actual = new ScheAuthModifyDeadline("test", NotUseAtr.USE, new CorrectDeadline(0));
		
		assertThat(actual.modifiableDate()).isEqualTo(GeneralDate.ymd(2020, 9, 2));
	}
	
	/**
	 * case : システム日付 = 2020/9/1、修正期限利用する、修正期限 =最大値(31)
	 * Expected : 2020/10/3
	 */
	@Test
	public void modifiableDate_max() {
		val actual = new ScheAuthModifyDeadline("test", NotUseAtr.USE, new CorrectDeadline(31));
		
		assertThat(actual.modifiableDate()).isEqualTo(GeneralDate.ymd(2020, 10, 3));
	}
	
	
	/**
	 * case : システム日付 = 2020/9/1、基準日 = 2020/9/1、修正期限利用しない、修正期限 =3
	 * Expected : true
	 */
	@Test
	public void isModifiable_notUse_test() {
		GeneralDate targetDate = GeneralDate.today();
		
		val actual = new ScheAuthModifyDeadline("test", NotUseAtr.NOT_USE, new CorrectDeadline(3));
		
		assertThat(actual.isModifiable(targetDate)).isTrue();
	}
	
	/**
	 * case : システム日付 = 2020/9/1、基準日 = 2020/9/4、修正期限利用する、修正期限 =3
	 * Expected : false(修正可能日 = 2020/9/5)
	 */
	@Test
	public void isModifiable_lessthanModifyable() {		
		GeneralDate targetDate = GeneralDate.ymd(2020, 9, 4);
		
		val actual = new ScheAuthModifyDeadline("test", NotUseAtr.USE, new CorrectDeadline(3));
		
		assertThat(actual.isModifiable(targetDate)).isFalse();
	}

	/**
	 * case : システム日付 = 2020/9/1、基準日 = 2020/9/5、修正期限利用する、修正期限 =3
	 * Expected : true(修正可能日 = 2020/9/5)
	 */
	@Test
	public void isModifiable_equalModifyable() {
		GeneralDate targetDate = GeneralDate.ymd(2020, 9, 5);
		
		val actual = new ScheAuthModifyDeadline("test", NotUseAtr.USE, new CorrectDeadline(3));
		
		assertThat(actual.isModifiable(targetDate)).isTrue();
	}
	
	
	/**
	 * case : システム日付 = 2020/9/1、基準日 = 2020/9/6、修正期限利用する、修正期限 =3
	 * Expected : true(修正可能日 = 2020/9/5)
	 */
	@Test
	public void isModifiable_todayLessthanModifyable() {
		GeneralDate targetDate = GeneralDate.ymd(2020, 9, 6);
		
		val actual = new ScheAuthModifyDeadline("test", NotUseAtr.USE, new CorrectDeadline(3));
		
		assertThat(actual.isModifiable(targetDate)).isTrue();
	}
}
