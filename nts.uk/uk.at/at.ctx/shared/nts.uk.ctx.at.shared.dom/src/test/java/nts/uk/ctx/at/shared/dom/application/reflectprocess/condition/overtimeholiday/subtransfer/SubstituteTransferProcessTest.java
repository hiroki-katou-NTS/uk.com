package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.subtransfer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer.MaximumTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer.MaximumTimeZone;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer.SubstituteTransferProcess;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class SubstituteTransferProcessTest {

	/*
	 * テストしたい内容
	 * 
	 * →振替をした後の時間（List）をチェック
	 * 
	 * 準備するデータ
	 * 
	 * →未割り当ての時間を算出<=0
	 * 
	 */
	@Test
	public void test1() {

		MaximumTimeZone maxTimeZone = new MaximumTimeZone();
		List<MaximumTime> maxTime = Arrays.asList(new MaximumTime(1, new AttendanceTime(0), new AttendanceTime(0)));// 振替時間=0
		List<MaximumTime> timeAfterReflectApp = Arrays
				.asList(new MaximumTime(1, new AttendanceTime(666), new AttendanceTime(999)));

		new Expectations() {
			{

			}
		};

		val result = SubstituteTransferProcess.process(maxTimeZone, maxTime, timeAfterReflectApp);

		assertThat(result).extracting(x -> x.getNo(), x -> x.getTransferTime().v(), //
				x -> x.getTime().v())//
				.contains(Tuple.tuple(1, 999, 666));

	}

	/*
	 * テストしたい内容
	 * 
	 * →振替をした後の時間（List）をチェック
	 * 
	 * 準備するデータ
	 * 
	 * →未割り当ての時間を算出>=0
	 * 
	 */
	@Test
	public void test2() {

		MaximumTimeZone maxTimeZone = new MaximumTimeZone();
		maxTimeZone.getTimeSpan().put(1, new TimeSpanForDailyCalc(new TimeWithDayAttr(100), new TimeWithDayAttr(200)));

		List<MaximumTime> maxTime = Arrays.asList(new MaximumTime(1, new AttendanceTime(10), new AttendanceTime(10)));
		List<MaximumTime> timeAfterReflectApp = Arrays
				.asList(new MaximumTime(1, new AttendanceTime(9), new AttendanceTime(16)));

		val result = SubstituteTransferProcess.process(maxTimeZone, maxTime, timeAfterReflectApp);
		assertThat(result).extracting(x -> x.getNo(), x -> x.getTransferTime().v(), //
				x -> x.getTime().v())//
				.contains(Tuple.tuple(1, 25, 0));

	}

}
