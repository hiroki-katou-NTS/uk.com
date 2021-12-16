package nts.uk.ctx.at.shared.dom.workdayoff.frame;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class WorkdayoffFrameTest {

	/**
	 * test [1] 休出枠NOに対応する日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();
		//残業枠NO == 1
		WorkdayoffFrame workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(1, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(266,267,268,269,270,777,848);
		//残業枠NO == 2
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(2, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(271,272,273,274,275,778,849);
		//残業枠NO == 3
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(3, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(276,277,278,279,280,779,850);
		//残業枠NO == 4
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(4, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(281,282,283,284,285,780,851);
		//残業枠NO == 5
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(5, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(286,287,288,289,290,781,852);
		//残業枠NO == 6
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(6, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(291,292,293,294,295,782,853);
		//残業枠NO == 7
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(7, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(296,297,298,299,300,783,854);
		//残業枠NO == 8
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(8, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(301,302,303,304,305,784,855);
		//残業枠NO == 9
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(9, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(306,307,308,309,310,785,856);
		//残業枠NO == 10
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(10, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(311,312,313,314,315,786,857);
	}
	
	/**
	 * test [2] 休出枠NOに対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();
		//残業枠NO == 1
		WorkdayoffFrame workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(1, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(110,121,132,143,154,165,175,1826,1836,2223,2233);
		//残業枠NO == 2
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(2, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(111,122,133,144,155,166,176,1827,1837,2224,2234);
		//残業枠NO == 3
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(3, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(112,123,134,145,156,167,177,1828,1838,2225,2235);
		//残業枠NO == 4
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(4, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(113,124,135,146,157,168,178,1829,1839,2226,2236);
		//残業枠NO == 5
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(5, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(114,125,136,147,158,169,179,1830,1840,2227,2237);
		//残業枠NO == 6
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(6, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(115,126,137,148,159,170,180,1831,1841,2228,2238);
		//残業枠NO == 7
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(7, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(116,127,138,149,160,171,181,1832,1842,2229,2239);
		//残業枠NO == 8
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(8, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(117,128,139,150,161,172,182,1833,1843,2230,2240);
		//残業枠NO == 9
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(9, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(118,129,140,151,162,173,183,1834,1844,2231,2241);
		//残業枠NO == 10
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(10, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getMonthlyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(119,130,141,152,163,174,184,1835,1845,2232,2242);
	}
	
	/**
	 * test [3] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//残業枠NO == 1 && 使用区分 == しない
		WorkdayoffFrame workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(1, NotUseAtr.NOT_USE);
		listAttdId  = workdayoffFrame.getDailyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(266,267,268,269,270,777,848);
		//残業枠NO == 1 && 使用区分 != しない
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(1, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getDailyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
	}
	
	/**
	 * test	[4] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//残業枠NO == 1 && 使用区分 == しない
		WorkdayoffFrame workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(1, NotUseAtr.NOT_USE);
		listAttdId  = workdayoffFrame.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(110,121,132,143,154,165,175,1826,1836,2223,2233);
		//残業枠NO == 1 && 使用区分 != しない
		workdayoffFrame = WorkdayoffFrameHelper.createWorkdayoffFrameTestByNoAndUseAtr(1, NotUseAtr.USE);
		listAttdId  = workdayoffFrame.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
	}

}
