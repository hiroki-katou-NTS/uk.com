package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeHelper;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;

public class OutsideOTBRDItemTest {

	/**
	 * test [1] 時間外超過の内訳に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();
		//内訳項目NO = 1
		OutsideOTBRDItem outsideOTBRDItem = OutsideOTBRDItemHelper.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.ONE,
				UseClassification.UseClass_NotUse);
		listAttdId  = outsideOTBRDItem.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(536,546,556,566,576,2069);
		//内訳項目NO = 2
		outsideOTBRDItem = OutsideOTBRDItemHelper.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.TWO,
				UseClassification.UseClass_NotUse);
		listAttdId  = outsideOTBRDItem.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(537,547,557,567,577,2070);
		//内訳項目NO = 3
		outsideOTBRDItem = OutsideOTBRDItemHelper.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.THREE,
				UseClassification.UseClass_NotUse);
		listAttdId  = outsideOTBRDItem.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(538,548,558,568,578,2071);
		//内訳項目NO = 4
		outsideOTBRDItem = OutsideOTBRDItemHelper.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.FOUR,
				UseClassification.UseClass_NotUse);
		listAttdId  = outsideOTBRDItem.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(539,549,559,569,579,2072);
		//内訳項目NO = 5
		outsideOTBRDItem = OutsideOTBRDItemHelper.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.FIVE,
				UseClassification.UseClass_NotUse);
		listAttdId  = outsideOTBRDItem.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(540,550,560,570,580,2073);
		//内訳項目NO = 6
		outsideOTBRDItem = OutsideOTBRDItemHelper.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.SIX,
				UseClassification.UseClass_NotUse);
		listAttdId  = outsideOTBRDItem.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(541,551,561,571,581,2074);
		//内訳項目NO = 7
		outsideOTBRDItem = OutsideOTBRDItemHelper.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.SEVEN,
				UseClassification.UseClass_NotUse);
		listAttdId  = outsideOTBRDItem.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(542,552,562,572,582,2075);
		//内訳項目NO = 8
		outsideOTBRDItem = OutsideOTBRDItemHelper.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.EIGHT,
				UseClassification.UseClass_NotUse);
		listAttdId  = outsideOTBRDItem.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(543,553,563,573,583,2076);
		//内訳項目NO = 9
		outsideOTBRDItem = OutsideOTBRDItemHelper.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.NINE,
				UseClassification.UseClass_NotUse);
		listAttdId  = outsideOTBRDItem.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(544,554,564,574,584,2077);
		//内訳項目NO = 10
		outsideOTBRDItem = OutsideOTBRDItemHelper.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.TEN,
				UseClassification.UseClass_NotUse);
		listAttdId  = outsideOTBRDItem.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(545,555,565,575,585,2078);
	}
	
	/**
	 * test [2] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//内訳項目NO == 1 && 使用区分 == しない
		OutsideOTBRDItem outsideOTBRDItem = OutsideOTBRDItemHelper.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.ONE,
				UseClassification.UseClass_NotUse);
		listAttdId  = outsideOTBRDItem.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(536,546,556,566,576,2069);
		//内訳項目NO == 1 && 使用区分 != しない
		outsideOTBRDItem = OutsideOTBRDItemHelper.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.ONE,
				UseClassification.UseClass_Use);
		listAttdId  = outsideOTBRDItem.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
	}

}
