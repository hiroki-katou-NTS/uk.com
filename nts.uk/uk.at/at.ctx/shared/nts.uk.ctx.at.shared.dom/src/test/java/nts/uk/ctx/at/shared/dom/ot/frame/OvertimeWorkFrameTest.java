package nts.uk.ctx.at.shared.dom.ot.frame;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class OvertimeWorkFrameTest {

	/**
	 * test 残業枠NOに対応する日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();
		//残業枠NO == 1
		OvertimeWorkFrame overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(1, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(216,217,218,219,220,767,838);
		//残業枠NO == 2
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(2, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(221,222,223,224,225,768,839);
		//残業枠NO == 3
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(3, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(226,227,228,229,230,769,840);
		//残業枠NO == 4
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(4, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(231,232,233,234,235,770,841);
		//残業枠NO == 5
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(5, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(236,237,238,239,240,771,842);
		//残業枠NO == 6
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(6, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(241,242,243,244,245,772,843);
		//残業枠NO == 7
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(7, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(246,247,248,249,250,773,844);
		//残業枠NO == 8
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(8, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(251,252,253,254,255,774,845);
		//残業枠NO == 9
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(9, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(256,257,258,259,260,775,846);
		//残業枠NO == 10
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(10, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(261,262,263,264,265,776,847);
	}
	
	/**
	 * test 残業枠NOに対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();
		//残業枠NO == 1
		OvertimeWorkFrame overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(1, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(35,46,57,90,1364,1375,1386,1804,1814,2203,2213);
		//残業枠NO == 2
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(2, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(36,47,58,91,1365,1376,1387,1805,1815,2204,2214);
		//残業枠NO == 3
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(3, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(37,48,59,92,1366,1377,1388,1806,1816,2205,2215);
		//残業枠NO == 4
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(4, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(38,49,60,93,1367,1378,1389,1807,1817,2206,2216);
		//残業枠NO == 5
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(5, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(39,50,61,94,1368,1379,1390,1808,1818,2207,2217);
		//残業枠NO == 6
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(6, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(40,51,62,95,1369,1380,1391,1809,1819,2208,2218);
		//残業枠NO == 7
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(7, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(41,52,63,96,1370,1381,1392,1810,1820,2209,2219);
		//残業枠NO == 8
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(8, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(42,53,64,97,1371,1382,1393,1811,1821,2210,2220);
		//残業枠NO == 9
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(9, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(43,54,65,98,1372,1383,1394,1812,1822,2211,2221);
		//残業枠NO == 10
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(10, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(44,55,66,99,1373,1384,1395,1813,1823,2212,2222);
	}
	
	/**
	 * test 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//残業枠NO == 1 && 使用区分 == しない
		OvertimeWorkFrame overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(1, NotUseAtr.NOT_USE);
		listAttdId  = overtimeWorkFrame.getDailyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(216,217,218,219,220,767,838);
		//残業枠NO == 1 && 使用区分 != しない
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(1, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getDailyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
	}
	
	/**
	 * test 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//残業枠NO == 1 && 使用区分 == しない
		OvertimeWorkFrame overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(1, NotUseAtr.NOT_USE);
		listAttdId  = overtimeWorkFrame.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(35,46,57,90,1364,1375,1386,1804,1814,2203,2213);
		//残業枠NO == 1 && 使用区分 != しない
		overtimeWorkFrame = OvertimeWorkFrameHelper.createOvertimeWorkFrameByNoAndUseAtr(1, NotUseAtr.USE);
		listAttdId  = overtimeWorkFrame.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
	}
	

}
