package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DivergenceTimeRootTest {

	/**
	 * test [1] 乖離時間に対応する日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();
		//乖離時間NO == 1
		DivergenceTimeRoot divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(1, DivergenceTimeUseSet.USE);
		listAttdId  = divergenceTimeRoot.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(436,437,440);
		//乖離時間NO == 2
		divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(2, DivergenceTimeUseSet.USE);
		listAttdId  = divergenceTimeRoot.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(441,442,445);
		//乖離時間NO == 3
		divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(3, DivergenceTimeUseSet.USE);
		listAttdId  = divergenceTimeRoot.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(446,447,450);
		//乖離時間NO == 4
		divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(4, DivergenceTimeUseSet.USE);
		listAttdId  = divergenceTimeRoot.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(451,452,455);
		//乖離時間NO == 5
		divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(5, DivergenceTimeUseSet.USE);
		listAttdId  = divergenceTimeRoot.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(456,457,460);
		//乖離時間NO == 6
		divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(6, DivergenceTimeUseSet.USE);
		listAttdId  = divergenceTimeRoot.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(799,800,803);
		//乖離時間NO == 7
		divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(7, DivergenceTimeUseSet.USE);
		listAttdId  = divergenceTimeRoot.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(804,805,808);
		//乖離時間NO == 8
		divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(8, DivergenceTimeUseSet.USE);
		listAttdId  = divergenceTimeRoot.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(809,810,813);
		//乖離時間NO == 9
		divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(9, DivergenceTimeUseSet.USE);
		listAttdId  = divergenceTimeRoot.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(814,815,818);
		//乖離時間NO == 10
		divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(10, DivergenceTimeUseSet.USE);
		listAttdId  = divergenceTimeRoot.getDaiLyAttendanceIdByNo();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(819,820,823);
	}
	
	/**
	 * test [2] 乖離時間に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();
		//乖離時間NO == 1 ~ 10
		DivergenceTimeRoot divergenceTimeRoot = null;
		for(int i =1;i<=10;i++) {
			divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(i, DivergenceTimeUseSet.USE);
			listAttdId  = divergenceTimeRoot.getMonthlyAttendanceIdByNo();
			assertThat( listAttdId )
			.extracting( d -> d)
			.containsExactly(388+i);
		}
	}
	
	/**
	 * test [3] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDailyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//乖離時間NO == 1
		//@使用区分 == 使用しない
		DivergenceTimeRoot divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(1, DivergenceTimeUseSet.NOT_USE);
			listAttdId  = divergenceTimeRoot.getDailyAttendanceIdNotAvailable();
			assertThat( listAttdId )
			.extracting( d -> d)
			.containsExactly(436,437,440);
			
		//乖離時間NO == 1
		//@使用区分 != 使用しない
		divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(1, DivergenceTimeUseSet.USE);
			listAttdId  = divergenceTimeRoot.getDailyAttendanceIdNotAvailable();
			assertThat( listAttdId ).isEmpty();
	
	}
	
	/**
	 * test [4] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		//乖離時間NO == 1
		//@使用区分 == 使用しない
		DivergenceTimeRoot divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(1, DivergenceTimeUseSet.NOT_USE);
			listAttdId  = divergenceTimeRoot.getMonthlyAttendanceIdNotAvailable();
			assertThat( listAttdId )
			.extracting( d -> d)
			.containsExactly(389);
			
		//乖離時間NO == 1
		//@使用区分 != 使用しない
		divergenceTimeRoot = DivergenceTimeRootHelper.createDivergenceTimeRootByNoAndUseAtr(1, DivergenceTimeUseSet.USE);
			listAttdId  = divergenceTimeRoot.getMonthlyAttendanceIdNotAvailable();
			assertThat( listAttdId ).isEmpty();
	
	}

}
