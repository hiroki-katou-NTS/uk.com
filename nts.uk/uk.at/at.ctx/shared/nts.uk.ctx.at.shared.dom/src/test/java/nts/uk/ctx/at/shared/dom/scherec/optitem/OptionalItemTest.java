package nts.uk.ctx.at.shared.dom.scherec.optitem;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class OptionalItemTest {
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
