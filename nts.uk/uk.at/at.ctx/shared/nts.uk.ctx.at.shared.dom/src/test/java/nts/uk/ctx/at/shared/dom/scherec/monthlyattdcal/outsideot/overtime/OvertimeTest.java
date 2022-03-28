package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;

public class OvertimeTest {

	/**
	 * 	test [1] 超過時間に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();
		//超過時間NO = 1
		Overtime overtime = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.ONE, UseClassification.UseClass_NotUse);
		listAttdId  = overtime.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(536,537,538,539,540,541,542,543,544,545);
		//超過時間NO = 2
		overtime = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.TWO, UseClassification.UseClass_NotUse);
		listAttdId  = overtime.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(546,547,548,549,550,551,552,553,554,555);
		//超過時間NO = 3
		overtime = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.THREE, UseClassification.UseClass_NotUse);
		listAttdId  = overtime.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(556,557,558,559,560,561,562,563,564,565);
		//超過時間NO = 4
		overtime = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.FOUR, UseClassification.UseClass_NotUse);
		listAttdId  = overtime.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(566,567,568,569,570,571,572,573,574,575);
		//超過時間NO = 5
		overtime = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.FIVE, UseClassification.UseClass_NotUse);
		listAttdId  = overtime.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(576,577,578,579,580,581,582,583,584,585);
	}
	
	/**
	 * test [2] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//超過時間NO == 1 && 使用区分 == しない
		Overtime overtime = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.ONE, UseClassification.UseClass_NotUse);
		listAttdId  = overtime.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(536,537,538,539,540,541,542,543,544,545);
		//超過時間NO == 1 && 使用区分 != しない
		overtime = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.ONE, UseClassification.UseClass_Use);
		listAttdId  = overtime.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
	}

}
