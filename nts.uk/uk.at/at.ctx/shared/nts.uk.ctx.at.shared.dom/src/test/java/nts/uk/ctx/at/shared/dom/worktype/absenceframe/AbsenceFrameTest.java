package nts.uk.ctx.at.shared.dom.worktype.absenceframe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;

public class AbsenceFrameTest {
	@Test
	public void getters() {
		AbsenceFrame frame = new AbsenceFrame("000000000008-0006", 1, new WorkTypeName("Name"), ManageDistinct.NO);
		NtsAssert.invokeGetters(frame);
	}

	/**
	 * Test [1] 欠勤枠に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthAttdItemsCorrespondAbsenteeism() {
		// 特別休暇枠No = 1
		AbsenceFrame frame = AbsenceFrameHelper.createAbsenceFrame(1);
		List<Integer> lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(239, 1288);

		// 特別休暇枠No = 2 :
		frame = AbsenceFrameHelper.createAbsenceFrame(2);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(240,1289);
		// 特別休暇枠No = 3 :
		frame = AbsenceFrameHelper.createAbsenceFrame(3);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(241, 1290);
		// 特別休暇枠No = 4 :
		frame = AbsenceFrameHelper.createAbsenceFrame(4);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(242, 1291);
		// 特別休暇枠No = 5 :
		frame = AbsenceFrameHelper.createAbsenceFrame(5);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(243, 1292);
		// 特別休暇枠No = 6 :
		frame = AbsenceFrameHelper.createAbsenceFrame(6);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(244, 1293);
		// 特別休暇枠No = 7 :
		frame = AbsenceFrameHelper.createAbsenceFrame(7);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(245, 1294);
		// 特別休暇枠No = 8 :
		frame = AbsenceFrameHelper.createAbsenceFrame(8);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(246, 1295);
		// 特別休暇枠No = 9 :
		frame = AbsenceFrameHelper.createAbsenceFrame(9);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(247, 1296);
		// 特別休暇枠No = 10 :
		frame = AbsenceFrameHelper.createAbsenceFrame(10);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(248, 1297);
		// 特別休暇枠No = 11 :
		frame = AbsenceFrameHelper.createAbsenceFrame(11);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(249, 1298);
		// 特別休暇枠No = 12 :
		frame = AbsenceFrameHelper.createAbsenceFrame(12);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(250, 1299);
		// 特別休暇枠No = 13 :
		frame = AbsenceFrameHelper.createAbsenceFrame(13);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(251, 1300);
		// 特別休暇枠No = 14 :
		frame = AbsenceFrameHelper.createAbsenceFrame(14);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(252, 1301);
		// 特別休暇枠No = 15 :
		frame = AbsenceFrameHelper.createAbsenceFrame(15);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(253, 1302);
		// 特別休暇枠No = 16 :
		frame = AbsenceFrameHelper.createAbsenceFrame(16);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(254, 1303);
		// 特別休暇枠No = 17 :
		frame = AbsenceFrameHelper.createAbsenceFrame(17);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(255, 1304);
		// 特別休暇枠No = 18 :
		frame = AbsenceFrameHelper.createAbsenceFrame(18);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(256, 1305);
		// 特別休暇枠No = 19 :
		frame = AbsenceFrameHelper.createAbsenceFrame(19);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(257, 1306);
		// 特別休暇枠No = 20 :
		frame = AbsenceFrameHelper.createAbsenceFrame(20);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(258, 1307);
		// 特別休暇枠No = 21 :
		frame = AbsenceFrameHelper.createAbsenceFrame(21);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(259, 1308);
		// 特別休暇枠No = 22 :
		frame = AbsenceFrameHelper.createAbsenceFrame(22);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(260, 1309);
		// 特別休暇枠No = 23 :
		frame = AbsenceFrameHelper.createAbsenceFrame(23);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(261, 1310);
		// 特別休暇枠No = 24 :
		frame = AbsenceFrameHelper.createAbsenceFrame(24);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(262, 1311);
		// 特別休暇枠No = 25 :
		frame = AbsenceFrameHelper.createAbsenceFrame(25);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(263, 1312);
		// 特別休暇枠No = 26 :
		frame = AbsenceFrameHelper.createAbsenceFrame(26);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(264, 1313);
		// 特別休暇枠No = 27 :
		frame = AbsenceFrameHelper.createAbsenceFrame(27);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(265, 1314);
		// 特別休暇枠No = 28 :
		frame = AbsenceFrameHelper.createAbsenceFrame(28);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(266, 1315);
		// 特別休暇枠No = 29 :
		frame = AbsenceFrameHelper.createAbsenceFrame(29);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(267, 1316);
		// 特別休暇枠No = 30 :
		frame = AbsenceFrameHelper.createAbsenceFrame(30);
		lstId = frame.getMonthAttdItemsCorrespondAbsenteeism();
		assertThat(lstId).extracting(d -> d).containsExactly(268, 1317);
	}

	/**
	 * Test [2] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void getMonthAttdItemsCorrespondAbsenteeism() {
	// =========== Case 1
		// 特別休暇枠No = 1
		AbsenceFrame frame = AbsenceFrameHelper
				.createAbsenceFrame(ManageDistinct.NO, 1);
		List<Integer> lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(239, 1288);

		// 特別休暇枠No = 2 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,2);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(240, 1289);
		// 特別休暇枠No = 3 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,3);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(241, 1290);
		// 特別休暇枠No = 4 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,4);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(242, 1291);
		// 特別休暇枠No = 5 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,5);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(243, 1292);
		// 特別休暇枠No = 6 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,6);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(244, 1293);
		// 特別休暇枠No = 7 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,7);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(245, 1294);
		// 特別休暇枠No = 8 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,8);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(246, 1295);
		// 特別休暇枠No = 9 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,9);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(247, 1296);
		// 特別休暇枠No = 10 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,10);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(248, 1297);
		// 特別休暇枠No = 11 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,11);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(249, 1298);
		// 特別休暇枠No = 12 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,12);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(250, 1299);
		// 特別休暇枠No = 13 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,13);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(251, 1300);
		// 特別休暇枠No = 14 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,14);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(252, 1301);
		// 特別休暇枠No = 15 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,15);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(253, 1302);
		// 特別休暇枠No = 16 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,16);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(254, 1303);
		// 特別休暇枠No = 17 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,17);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(255, 1304);
		// 特別休暇枠No = 18 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,18);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(256, 1305);
		// 特別休暇枠No = 19 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,19);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(257, 1306);
		// 特別休暇枠No = 20 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,20);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(258, 1307);
		// 特別休暇枠No = 21 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,21);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(259, 1308);
		// 特別休暇枠No = 22 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,22);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(260, 1309);
		// 特別休暇枠No = 23 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,23);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(261, 1310);
		// 特別休暇枠No = 24 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,24);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(262, 1311);
		// 特別休暇枠No = 25 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,25);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(263, 1312);
		// 特別休暇枠No = 26 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,26);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(264, 1313);
		// 特別休暇枠No = 27 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,27);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(265, 1314);
		// 特別休暇枠No = 28 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,28);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(266, 1315);
		// 特別休暇枠No = 29 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,29);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(267, 1316);
		// 特別休暇枠No = 30 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.NO,30);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId).extracting(d -> d).containsExactly(268, 1317);
		
		
	// =========== Case 2
		// 特別休暇枠No = 1
		frame = AbsenceFrameHelper
				.createAbsenceFrame(ManageDistinct.YES, 1);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId.isEmpty()).isTrue();

		// 特別休暇枠No = 2 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.YES,2);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId.isEmpty()).isTrue();
		// 特別休暇枠No = 3 :
		frame = AbsenceFrameHelper.createAbsenceFrame(ManageDistinct.YES,3);
		lstId = frame.getMonthAttdItems();
		assertThat(lstId.isEmpty()).isTrue();
	}
}
