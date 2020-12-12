package nts.uk.ctx.at.record.dom.applicationcancel;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.record.dom.applicationcancel.GetReflectOutGoFrameNumber.ReflectOutGoFrameNumberResult;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class GetReflectOutGoFrameNumberTest {

	/*
	 * テストしたい内容
	 * 
	 * → 申請反映がある
	 * 
	 * 準備するデータ
	 * 
	 * → 反映前の外出時間帯.外出枠NO ！＝処理中の外出時間帯.外出枠NO
	 */
	@Test
	public void test() {

		List<OutingTimeSheet> lstBefore = Arrays.asList(createOutTime(1, // NO 1
				480, 600));
		List<OutingTimeSheet> lstAfter = Arrays.asList(createOutTime(1, // NO 1
				480, 600),
				createOutTime(2, // NO 2
						720, 1080));

		ReflectOutGoFrameNumberResult actualResult = GetReflectOutGoFrameNumber.process(lstAfter, lstBefore);

		assertThat(actualResult.isReflect()).isTrue();
		assertThat(actualResult.getNo()).isEqualTo(2);

	}

	/*
	 * テストしたい内容
	 * 
	 * → 申請反映がある
	 * 
	 * 準備するデータ
	 * 
	 * → 反映前の外出時間帯.外出枠NO = 処理中の外出時間帯.外出枠NO
	 * 
	 * → 異なる値
	 */
	@Test
	public void testValueNotEqual() {

		List<OutingTimeSheet> lstBefore = Arrays.asList(createOutTime(1, // NO 1
				480, 600));
		List<OutingTimeSheet> lstAfter = Arrays.asList(createOutTime(1, // NO 1
				400, 600));

		ReflectOutGoFrameNumberResult actualResult = GetReflectOutGoFrameNumber.process(lstAfter, lstBefore);

		assertThat(actualResult.isReflect()).isTrue();
		assertThat(actualResult.getNo()).isEqualTo(1);

	}
	
	/*
	 * テストしたい内容
	 * 
	 * → 申請反映がない
	 * 
	 * 準備するデータ
	 * 
	 * → 反映前の外出時間帯.外出枠NO = 処理中の外出時間帯.外出枠NO
	 * 
	 * → 異なる値
	 */
	@Test
	public void testValueEqual() {

		List<OutingTimeSheet> lstBefore = Arrays.asList(createOutTime(1, // NO 1
				480, 600));
		List<OutingTimeSheet> lstAfter = Arrays.asList(createOutTime(1, // NO 1
				480, 600));

		ReflectOutGoFrameNumberResult actualResult = GetReflectOutGoFrameNumber.process(lstAfter, lstBefore);

		assertThat(actualResult.isReflect()).isFalse();

	}
	
	private OutingTimeSheet createOutTime(int no, int startTime, int endTime) {
		// 日別勤怠の外出時間帯
		OutingTimeSheet outSheet = new OutingTimeSheet(new OutingFrameNo(no),
				Optional.of(new TimeActualStamp(null,
						new WorkStamp(
								new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET, null),
										new TimeWithDayAttr(startTime)),
								Optional.empty()),
						0)),
				new AttendanceTime(600), new AttendanceTime(600), GoingOutReason.PUBLIC,
				Optional.of(new TimeActualStamp(null,
						new WorkStamp(
								new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET, null),
										new TimeWithDayAttr(endTime)),
								Optional.empty()),
						0)));
		return outSheet;
	}
}
