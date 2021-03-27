package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.subtransfer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.groups.Tuple;
import org.junit.Test;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer.MaximumTime;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer.MaximumTimeZone;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.algorithm.subtransfer.TransferTimeFromTimezone;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TransferTimeFromTimezoneTest {

	/*
	 * テストしたい内容
	 * 
	 * →振替可能時間<=0時に処理を停止し、「残りの振替可能時間振替をした後の時間（List）」戻る値
	 * 
	 * 準備するデータ
	 * 
	 * →振替可能時間<=0
	 */

	@Test
	public void test1() {

		MaximumTimeZone maxTimeZone = new MaximumTimeZone();
		maxTimeZone.getTimeSpan().put(1, new TimeSpanForDailyCalc(new TimeWithDayAttr(600), new TimeWithDayAttr(800)));// 最大時間帯(List）：最大時間帯

		List<MaximumTime> timeAfterReflectApp = Arrays
				.asList(new MaximumTime(1, new AttendanceTime(666), new AttendanceTime(444)));// 最大の時間(List)：時間外労働時間（振替用）

		List<MaximumTime> maxTime = Arrays.asList(new MaximumTime(1, new AttendanceTime(666), new AttendanceTime(444)));// 振替をした後の時間(List）：時間外労働時間（振替用）

		Pair<Integer, List<MaximumTime>> result = TransferTimeFromTimezone.process(0, maxTimeZone, maxTime,
				timeAfterReflectApp);

		assertThat(result.getLeft()).isEqualTo(0);
		assertThat(result.getRight()).extracting(x -> x.getNo(), x -> x.getTime().v(), x -> x.getTransferTime().v())
				.contains(Tuple.tuple(1, 666, 444));

	}

	/*
	 * テストしたい内容
	 * 
	 * →処理中の最大時間帯に該当する枠NOの振替時間をチェクする場合は＜＝0、処理を停止し、「残りの振替可能時間振替をした後の時間（List）」戻る値
	 * 
	 * 準備するデータ
	 * 
	 * →最大の時間.振替時間<=0
	 */

	@Test
	public void test2() {
		MaximumTimeZone maxTimeZone = new MaximumTimeZone();
		maxTimeZone.getTimeSpan().put(1, new TimeSpanForDailyCalc(new TimeWithDayAttr(600), new TimeWithDayAttr(800)));// 最大時間帯(List）：最大時間帯

		List<MaximumTime> timeAfterReflectApp = Arrays
				.asList(new MaximumTime(1, new AttendanceTime(666), new AttendanceTime(444)));// 最大の時間(List)：時間外労働時間（振替用）

		List<MaximumTime> maxTime = Arrays.asList(new MaximumTime(1, new AttendanceTime(0), new AttendanceTime(444)));// 振替をした後の時間(List）：時間外労働時間（振替用）

		Pair<Integer, List<MaximumTime>> result = TransferTimeFromTimezone.process(100, maxTimeZone, maxTime,
				timeAfterReflectApp);

		assertThat(result.getLeft()).isEqualTo(100);
		assertThat(result.getRight()).extracting(x -> x.getNo(), x -> x.getTime().v(), x -> x.getTransferTime().v())
				.contains(Tuple.tuple(1, 666, 444));
	}

	/*
	 * テストしたい内容
	 * 
	 * →[input.振替をした後の時間（List）]を取得をチェクする場合は＜＝0、処理を停止し、「残りの振替可能時間振替をした後の時間（List）」戻る値
	 * 
	 * 準備するデータ
	 * 
	 * →最大の時間.振替時間<=0
	 */

	@Test
	public void test3() {
		MaximumTimeZone maxTimeZone = new MaximumTimeZone();
		maxTimeZone.getTimeSpan().put(1, new TimeSpanForDailyCalc(new TimeWithDayAttr(600), new TimeWithDayAttr(800)));// 最大時間帯(List）：最大時間帯

		List<MaximumTime> timeAfterReflectApp = Arrays
				.asList(new MaximumTime(1, new AttendanceTime(0), new AttendanceTime(444)));// 最大の時間(List)：時間外労働時間（振替用）

		List<MaximumTime> maxTime = Arrays.asList(new MaximumTime(1, new AttendanceTime(666), new AttendanceTime(444)));// 振替をした後の時間(List）：時間外労働時間（振替用）

		Pair<Integer, List<MaximumTime>> result = TransferTimeFromTimezone.process(100, maxTimeZone, maxTime,
				timeAfterReflectApp);

		assertThat(result.getLeft()).isEqualTo(100);
		assertThat(result.getRight()).extracting(x -> x.getNo(), x -> x.getTime().v(), x -> x.getTransferTime().v())
				.contains(Tuple.tuple(1, 0, 444));
	}

	/*
	 * テストしたい内容
	 * 
	 * →[input.振替をした後の時間（List）]を取得をチェクする場合は>0、時間の振替ができます
	 * 
	 * 準備するデータ
	 * 
	 * →最大の時間.振替時間＞0
	 */

	@Test
	public void test4() {
		MaximumTimeZone maxTimeZone = new MaximumTimeZone();
		maxTimeZone.getTimeSpan().put(1, new TimeSpanForDailyCalc(new TimeWithDayAttr(600), new TimeWithDayAttr(800)));// 最大時間帯(List）：最大時間帯

		List<MaximumTime> timeAfterReflectApp = Arrays
				.asList(new MaximumTime(1, new AttendanceTime(666), new AttendanceTime(444)));// 最大の時間(List)：時間外労働時間（振替用）

		List<MaximumTime> maxTime = Arrays.asList(new MaximumTime(1, new AttendanceTime(666), new AttendanceTime(444)));// 振替をした後の時間(List）：時間外労働時間（振替用）

		Pair<Integer, List<MaximumTime>> result = TransferTimeFromTimezone.process(100, maxTimeZone, maxTime,
				timeAfterReflectApp);

		assertThat(result.getLeft()).isEqualTo(-444);
		assertThat(result.getRight()).extracting(x -> x.getNo(), x -> x.getTime().v(), x -> x.getTransferTime().v())
				.contains(Tuple.tuple(1, 122, 544));// transfer += tranferableTime, time = 666-544
	}
}
