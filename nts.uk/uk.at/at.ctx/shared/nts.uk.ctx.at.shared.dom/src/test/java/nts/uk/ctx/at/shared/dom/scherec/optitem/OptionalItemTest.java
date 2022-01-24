package nts.uk.ctx.at.shared.dom.scherec.optitem;

import static org.assertj.core.api.Assertions.assertThat;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author tutk
 *
 */
@RunWith(JMockit.class)
public class OptionalItemTest {

	@Test
	public void getters() {
		OptionalItem optionalItem = OptionalItemTestHelper.createDefault();
		NtsAssert.invokeGetters(optionalItem);
	}

	/**
	 * no error
	 */
	@Test
	public void testCheckInputValueCorrect_1() {
		OptionalItem optionalItem = OptionalItemTestHelper.createDefault();
		BigDecimal inputValue = new BigDecimal(69);

		List<String> listErrors = new ArrayList<>();
		CheckValueInputCorrectOuput checkValueInputCorrectOuput = new CheckValueInputCorrectOuput(true, listErrors);
		new MockUp<InputControlSetting>() {
			@Mock
			public CheckValueInputCorrectOuput checkValueInputCorrect(BigDecimal inputValue,
					PerformanceAtr performanceAtr, OptionalItemAtr optionalItemAtr) {
				return checkValueInputCorrectOuput;
			}
		};

		CheckValueInputCorrectOuput dataResult = optionalItem.checkInputValueCorrect(inputValue);
		assertThat(dataResult.isCheckResult()).isTrue();
		assertThat(dataResult.getErrorContent()).isEmpty();
	}

	/**
	 * one error
	 */
	@Test
	public void testCheckInputValueCorrect_2() {
		OptionalItem optionalItem = OptionalItemTestHelper.createDefault();
		BigDecimal inputValue = new BigDecimal(69);

		List<String> listErrors = new ArrayList<>();
		listErrors.add("10分の単位で入力してください。");
		CheckValueInputCorrectOuput checkValueInputCorrectOuput = new CheckValueInputCorrectOuput(false, listErrors);
		new MockUp<InputControlSetting>() {
			@Mock
			public CheckValueInputCorrectOuput checkValueInputCorrect(BigDecimal inputValue,
					PerformanceAtr performanceAtr, OptionalItemAtr optionalItemAtr) {
				return checkValueInputCorrectOuput;
			}
		};

		CheckValueInputCorrectOuput dataResult = optionalItem.checkInputValueCorrect(inputValue);
		assertThat(dataResult.isCheckResult()).isFalse();
		assertThat(dataResult.getErrorContent().get(0)).isEqualTo("10分の単位で入力してください。");
	}

	/**
	 * two error
	 */
	@Test
	public void testCheckInputValueCorrect_3() {
		OptionalItem optionalItem = OptionalItemTestHelper.createDefault();
		BigDecimal inputValue = new BigDecimal(69);

		List<String> listErrors = new ArrayList<>();
		listErrors.add("10分の単位で入力してください。");
		listErrors.add("70.0以上の値で入力してください。");
		CheckValueInputCorrectOuput checkValueInputCorrectOuput = new CheckValueInputCorrectOuput(false, listErrors);
		new MockUp<InputControlSetting>() {
			@Mock
			public CheckValueInputCorrectOuput checkValueInputCorrect(BigDecimal inputValue,
					PerformanceAtr performanceAtr, OptionalItemAtr optionalItemAtr) {
				return checkValueInputCorrectOuput;
			}
		};

		CheckValueInputCorrectOuput dataResult = optionalItem.checkInputValueCorrect(inputValue);
		assertThat(dataResult.isCheckResult()).isFalse();
		assertThat(dataResult.getErrorContent().get(0)).isEqualTo("10分の単位で入力してください。");
		assertThat(dataResult.getErrorContent().get(1)).isEqualTo("70.0以上の値で入力してください。");
	}
	
	/**
	 * test [1] 任意項目に対応する日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIdByNo() {
		OptionalItem optionalItem = null;
		//任意項目NO 1~100
		for(int i = 1;i<=100;i++) {
			List<Integer> listAttdId = new ArrayList<>();
			optionalItem = OptionalItemHelper.createOptionalItemByNoAndUseAtr(i, OptionalItemUsageAtr.USE);
			listAttdId  = optionalItem.getDaiLyAttendanceIdByNo();
			assertThat( listAttdId )
			.extracting( d -> d)
			.containsExactly(i+640);
		}
	}

	/**
	 * test [2] 任意項目に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdByNo() {
		OptionalItem optionalItem = null;
		//任意項目NO 1~100
		for(int i = 1;i<=100;i++) {
			List<Integer> listAttdId = new ArrayList<>();
			optionalItem = OptionalItemHelper.createOptionalItemByNoAndUseAtr(i, OptionalItemUsageAtr.USE);
			listAttdId  = optionalItem.getMonthlyAttendanceIdByNo();
			assertThat( listAttdId )
			.extracting( d -> d)
			.containsExactly(i+588);
		}
	}
	
	/**
	 * test [3] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//任意項目NO 1 && 任意項目利用区分 == 利用しない
		OptionalItem optionalItem = OptionalItemHelper.createOptionalItemByNoAndUseAtr(1, OptionalItemUsageAtr.NOT_USE);
		listAttdId  = optionalItem.getDailyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(641);
		//任意項目NO 1 && 任意項目利用区分 != 利用しない
		optionalItem = OptionalItemHelper.createOptionalItemByNoAndUseAtr(1, OptionalItemUsageAtr.USE);
		listAttdId  = optionalItem.getDailyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
	}
	
	/**
	 * test [4] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//任意項目NO 1 && 任意項目利用区分 == 利用しない
		OptionalItem optionalItem = OptionalItemHelper.createOptionalItemByNoAndUseAtr(1, OptionalItemUsageAtr.NOT_USE);
		listAttdId  = optionalItem.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(589);
		//任意項目NO 1 && 任意項目利用区分 != 利用しない
		optionalItem = OptionalItemHelper.createOptionalItemByNoAndUseAtr(1, OptionalItemUsageAtr.USE);
		listAttdId  = optionalItem.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
	}

}
