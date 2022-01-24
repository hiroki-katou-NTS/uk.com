package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItemHelper;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeHelper;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;

public class OutsideOTSettingTest {

	/**
	 * test [1] 時間外超過に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();

		// 超過時間1 : 超過時間NO = 1 
		Overtime overtimeNo1 = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.ONE,
				UseClassification.UseClass_NotUse);
		// 超過時間2 : 超過時間NO = 2 
		Overtime overtimeNo2 = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.TWO,
				UseClassification.UseClass_NotUse);
		// 超過時間一覧
		List<Overtime> overtimes = new ArrayList<>();
		overtimes.add(overtimeNo1);
		overtimes.add(overtimeNo2);

		// 内訳項目1 : 内訳項目NO = 1 
		OutsideOTBRDItem outsideOTBRDItem1 = OutsideOTBRDItemHelper
				.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.ONE, UseClassification.UseClass_NotUse);
		// 内訳項目2 : 内訳項目NO = 2 
		OutsideOTBRDItem outsideOTBRDItem2 = OutsideOTBRDItemHelper
				.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.TWO, UseClassification.UseClass_NotUse);
		// 内訳項目一覧
		List<OutsideOTBRDItem> breakdownItems = new ArrayList<>();
		breakdownItems.add(outsideOTBRDItem1);
		breakdownItems.add(outsideOTBRDItem2);

		OutsideOTSetting outsideOTSetting = OutsideOTSettingHelper.createOutsideOTSettingDefault(overtimes,
				breakdownItems);
		listAttdId = outsideOTSetting.getMonthlyAttendanceIdByNo().stream().sorted((x, y) -> x.compareTo(y))
				.collect(Collectors.toList());

		List<Integer> listResult = Arrays.asList(536, 537, 538, 539, 540, 541, 542, 543, 544, 545, 546, 547, 548, 549,
				550, 551, 552, 553, 554, 555, 536, 546, 556, 566, 576, 2069, 537, 547, 557, 567, 577, 2070);
		listResult = listResult.stream().distinct().sorted((x, y) -> x.compareTo(y)).collect(Collectors.toList());

		assertThat(listAttdId).isEqualTo(listResult);
	}
	
	/**
	 * test [2] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();

		// 超過時間1 : 超過時間NO = 1 && 使用区分 != しない
		Overtime overtimeNo1 = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.ONE,
				UseClassification.UseClass_Use);
		// 超過時間2 : 超過時間NO = 2 && 使用区分 != しない
		Overtime overtimeNo2 = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.TWO,
				UseClassification.UseClass_Use);
		// 超過時間一覧
		List<Overtime> overtimes = new ArrayList<>();
		overtimes.add(overtimeNo1);
		overtimes.add(overtimeNo2);

		// 内訳項目1 : 内訳項目NO = 1 && 使用区分 != しない
		OutsideOTBRDItem outsideOTBRDItem1 = OutsideOTBRDItemHelper
				.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.ONE, UseClassification.UseClass_Use);
		// 内訳項目2 : 内訳項目NO = 2 && 使用区分 != しない
		OutsideOTBRDItem outsideOTBRDItem2 = OutsideOTBRDItemHelper
				.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.TWO, UseClassification.UseClass_Use);
		// 内訳項目一覧
		List<OutsideOTBRDItem> breakdownItems = new ArrayList<>();
		breakdownItems.add(outsideOTBRDItem1);
		breakdownItems.add(outsideOTBRDItem2);

		OutsideOTSetting outsideOTSetting = OutsideOTSettingHelper.createOutsideOTSettingDefault(overtimes,
				breakdownItems);
		listAttdId = outsideOTSetting.getMonthlyAttendanceIdNotAvailable();
		assertThat(listAttdId).isEmpty();
		
		
		// 超過時間1 : 超過時間NO = 1 && 使用区分 == しない
		overtimeNo1 = OvertimeHelper.createOvertimeByNoAndAtr(OvertimeNo.ONE,
				UseClassification.UseClass_NotUse);
		// 超過時間一覧2
		List<Overtime> overtimes2 = new ArrayList<>();
		overtimes2.add(overtimeNo1);
		overtimes2.add(overtimeNo2);

		// 内訳項目1 : 内訳項目NO = 1 && 使用区分 == しない
		outsideOTBRDItem1 = OutsideOTBRDItemHelper
				.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.ONE, UseClassification.UseClass_NotUse);
		// 内訳項目一覧2
		List<OutsideOTBRDItem> breakdownItems2 = new ArrayList<>();
		breakdownItems2.add(outsideOTBRDItem1);
		breakdownItems2.add(outsideOTBRDItem2);
		
		OutsideOTSetting outsideOTSetting2 = OutsideOTSettingHelper.createOutsideOTSettingDefault(overtimes2,
				breakdownItems2);
		listAttdId = outsideOTSetting2.getMonthlyAttendanceIdNotAvailable().stream().sorted((x, y) -> x.compareTo(y))
				.collect(Collectors.toList());

		List<Integer> listResult2 = Arrays.asList(536,537,538,539,540,541,542,543,544,545,536,546,556,566,576,2069);
		listResult2 = listResult2.stream().distinct().sorted((x, y) -> x.compareTo(y)).collect(Collectors.toList());

		assertThat(listAttdId).isEqualTo(listResult2);
		
		// CASE 内訳項目一覧 = empty
		// 超過時間一覧3
		List<Overtime> overtimes3 = new ArrayList<>();
		overtimes3.add(overtimeNo1);
		overtimes3.add(overtimeNo2);
		
		// 内訳項目一覧 = empty
		List<OutsideOTBRDItem> breakdownItems3 = new ArrayList<>();
		
		OutsideOTSetting outsideOTSetting3 = OutsideOTSettingHelper.createOutsideOTSettingDefault(overtimes3,
				breakdownItems3);
		listAttdId = outsideOTSetting3.getMonthlyAttendanceIdNotAvailable().stream().sorted((x, y) -> x.compareTo(y))
				.collect(Collectors.toList());
		List<Integer> listResult3 = Arrays.asList(536, 537, 538, 539, 540, 541, 542, 543, 544, 545);
		assertThat(listAttdId).isEqualTo(listResult3);
		
		
		// CASE 超過時間一覧 = empty
		// 超過時間一覧 = empty
		List<Overtime> overtimes4 = new ArrayList<>();
		
		// 内訳項目一覧4
		List<OutsideOTBRDItem> breakdownItems4 = new ArrayList<>();
		
		// 内訳項目1 : 内訳項目NO = 1 && 使用区分 == しない
		outsideOTBRDItem1 = OutsideOTBRDItemHelper
				.createOutsideOTBRDItemByNoAndAtr(BreakdownItemNo.ONE, UseClassification.UseClass_NotUse);
		breakdownItems4.add(outsideOTBRDItem1);
		breakdownItems4.add(outsideOTBRDItem2);
		
		OutsideOTSetting outsideOTSetting4 = OutsideOTSettingHelper.createOutsideOTSettingDefault(overtimes4,
				breakdownItems4);
		listAttdId = outsideOTSetting4.getMonthlyAttendanceIdNotAvailable().stream().sorted((x, y) -> x.compareTo(y))
				.collect(Collectors.toList());
		List<Integer> listResult4 = Arrays.asList(536, 546, 556, 566, 576, 2069);
		assertThat(listAttdId).isEqualTo(listResult4);
	}

}
