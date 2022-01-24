package nts.uk.ctx.at.shared.dom.worktype.specialholidayframe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class SpecialHolidayFrameTest {
	@Test
	public void getters() {
		SpecialHolidayFrame maxDay = new SpecialHolidayFrame("000000000008-0006", 1, new WorkTypeName("Name"),
				ManageDistinct.NO, NotUseAtr.NOT_USE);
		NtsAssert.invokeGetters(maxDay);
	}

	/**
	 * Test [1] 特別休暇枠に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceItemsSpecialLeave() {
		// 特別休暇枠No = 1
		SpecialHolidayFrame maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(1);
		List<Integer> lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(270, 1319);

		// 特別休暇枠No = 2 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(2);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(271, 1320);
		// 特別休暇枠No = 3 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(3);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(272, 1321);
		// 特別休暇枠No = 4 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(4);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(273, 1322);
		// 特別休暇枠No = 5 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(5);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(274, 1323);
		// 特別休暇枠No = 6 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(6);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(275, 1324);
		// 特別休暇枠No = 7 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(7);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(276, 1325);
		// 特別休暇枠No = 8 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(8);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(277, 1326);
		// 特別休暇枠No = 9 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(9);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(278, 1327);
		// 特別休暇枠No = 10 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(10);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(279, 1328);
		// 特別休暇枠No = 11 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(11);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(280, 1329);
		// 特別休暇枠No = 12 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(12);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(281, 1330);
		// 特別休暇枠No = 13 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(13);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(282, 1331);
		// 特別休暇枠No = 14 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(14);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(283, 1332);
		// 特別休暇枠No = 15 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(15);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(284, 1333);
		// 特別休暇枠No = 16 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(16);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(285, 1334);
		// 特別休暇枠No = 17 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(17);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(286, 1335);
		// 特別休暇枠No = 18 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(18);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(287, 1336);
		// 特別休暇枠No = 19 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(19);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(288, 1337);
		// 特別休暇枠No = 20 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(20);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(289, 1338);
		// 特別休暇枠No = 21 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(21);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(290, 1339);
		// 特別休暇枠No = 22 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(22);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(291, 1340);
		// 特別休暇枠No = 23 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(23);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(292, 1341);
		// 特別休暇枠No = 24 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(24);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(293, 1342);
		// 特別休暇枠No = 25 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(25);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(294, 1343);
		// 特別休暇枠No = 26 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(26);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(295, 1344);
		// 特別休暇枠No = 27 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(27);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(296, 1345);
		// 特別休暇枠No = 28 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(28);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(297, 1346);
		// 特別休暇枠No = 29 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(29);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(298, 1347);
		// 特別休暇枠No = 30 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(30);
		lstId = maxDay.getMonthlyAttendanceItemsSpecialLeave();
		assertThat(lstId).extracting(d -> d).containsExactly(299, 1348);
	}

	/**
	 * Test [2] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void getMonthlyAttendanceItems() {
	// =========== Case 1
		// 特別休暇枠No = 1
		SpecialHolidayFrame maxDay = SpecialHolidayFrameHelper
				.createSpecialHolidayFrame(ManageDistinct.NO, 1);
		List<Integer> lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(270, 1319);

		// 特別休暇枠No = 2 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,2);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(271, 1320);
		// 特別休暇枠No = 3 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,3);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(272, 1321);
		// 特別休暇枠No = 4 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,4);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(273, 1322);
		// 特別休暇枠No = 5 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,5);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(274, 1323);
		// 特別休暇枠No = 6 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,6);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(275, 1324);
		// 特別休暇枠No = 7 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,7);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(276, 1325);
		// 特別休暇枠No = 8 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,8);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(277, 1326);
		// 特別休暇枠No = 9 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,9);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(278, 1327);
		// 特別休暇枠No = 10 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,10);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(279, 1328);
		// 特別休暇枠No = 11 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,11);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(280, 1329);
		// 特別休暇枠No = 12 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,12);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(281, 1330);
		// 特別休暇枠No = 13 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,13);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(282, 1331);
		// 特別休暇枠No = 14 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,14);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(283, 1332);
		// 特別休暇枠No = 15 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,15);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(284, 1333);
		// 特別休暇枠No = 16 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,16);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(285, 1334);
		// 特別休暇枠No = 17 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,17);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(286, 1335);
		// 特別休暇枠No = 18 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,18);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(287, 1336);
		// 特別休暇枠No = 19 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,19);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(288, 1337);
		// 特別休暇枠No = 20 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,20);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(289, 1338);
		// 特別休暇枠No = 21 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,21);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(290, 1339);
		// 特別休暇枠No = 22 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,22);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(291, 1340);
		// 特別休暇枠No = 23 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,23);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(292, 1341);
		// 特別休暇枠No = 24 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,24);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(293, 1342);
		// 特別休暇枠No = 25 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,25);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(294, 1343);
		// 特別休暇枠No = 26 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,26);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(295, 1344);
		// 特別休暇枠No = 27 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,27);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(296, 1345);
		// 特別休暇枠No = 28 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,28);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(297, 1346);
		// 特別休暇枠No = 29 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,29);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(298, 1347);
		// 特別休暇枠No = 30 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.NO,30);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(299, 1348);
		
		
	// =========== Case 2
		// 特別休暇枠No = 1
		maxDay = SpecialHolidayFrameHelper
				.createSpecialHolidayFrame(ManageDistinct.YES, 1);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();

		// 特別休暇枠No = 2 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.YES,2);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
		// 特別休暇枠No = 3 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.YES,3);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
		// 特別休暇枠No = 4 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.YES,4);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
		// 特別休暇枠No = 5 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.YES,5);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
		// 特別休暇枠No = 6 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.YES,6);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
		// 特別休暇枠No = 7 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.YES,7);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
		// 特別休暇枠No = 8 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.YES,8);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
		// 特別休暇枠No = 9 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.YES,9);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
		// 特別休暇枠No = 10 :
		maxDay = SpecialHolidayFrameHelper.createSpecialHolidayFrame(ManageDistinct.YES,10);
		lstId = maxDay.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
		
	}
}
