package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class TotalTimesTest {

	/**
	 * test [1] 回数集計に対応する月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceId() {
		TotalTimes totalTimes = null;
		//回数集計No 1~30
		for(int i =1;i<=30;i++) {
			totalTimes = TotalTimesHelper.createTotalTimesByNoAndAtr(i,UseAtr.NotUse);
			List<Integer> listAttdId = totalTimes.getMonthlyAttendanceId();
			assertThat( listAttdId )
			.extracting( d -> d)
			.containsExactly(i+475,i+505);
		}
	}
	
	/**
	 * test [2] 利用できない月次の勤怠項目を取得する
	 */
	@Test
	public void testGetMonthlyAttendanceIdNotAvailable() {
		//回数集計No 1  && 	利用区分 !=  しない
		TotalTimes totalTimes = TotalTimesHelper.createTotalTimesByNoAndAtr(1,UseAtr.Use);
		List<Integer> listAttdId = totalTimes.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId ).isEmpty();
		//回数集計No 1  && 	利用区分 ==  しない
		totalTimes = TotalTimesHelper.createTotalTimesByNoAndAtr(1,UseAtr.NotUse);
		listAttdId = totalTimes.getMonthlyAttendanceIdNotAvailable();
		assertThat( listAttdId )
		.extracting( d -> d)
		.containsExactly(476,506);
	}

}
