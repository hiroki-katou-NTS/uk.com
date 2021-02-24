package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication;

import java.util.Optional;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveAppReflectCondition;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class CheckConditionReflectAppTimeLeaveTest {

	/*
	 * テストしたい内容
	 * 
	 * →①時間休暇の申請反映条件チェック、時間消化申請の時間年休を作成する
	 * 
	 * →②時間休暇の申請反映条件チェック、時間消化申請の時間年休を作成しない
	 * 
	 * 準備するデータ
	 * 
	 * →①時間休暇の申請反映条件.時間年休= する
	 * 
	 * →②時間休暇の申請反映条件.時間年休= しない
	 */
	@Test
	public void testCase1() {
		AttendanceTime timeCommon = new AttendanceTime(0);
		TimeDigestApplicationShare digest = new TimeDigestApplicationShare(timeCommon, timeCommon, timeCommon,
				timeCommon, timeCommon, new AttendanceTime(60), // 時間年休時間 = 60
				Optional.empty());
		// ①時間休暇の申請反映条件チェック、時間消化申請の時間年休を作成する
		TimeLeaveAppReflectCondition condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.USE, NotUseAtr.NOT_USE);
		TimeDigestApplicationShare result = CheckConditionReflectAppTimeLeave.check(digest, condition);
		assertThat(result.getTimeAnnualLeave().v()).isEqualTo(60);// 時間年休を作成する
		assertThat(result.getChildTime().v()).isEqualTo(0);
		assertThat(result.getNursingTime().v()).isEqualTo(0);
		assertThat(result.getOvertime60H().v()).isEqualTo(0);
		assertThat(result.getTimeOff().v()).isEqualTo(0);
		assertThat(result.getTimeSpecialVacation().v()).isEqualTo(0);

		// ②時間休暇の申請反映条件チェック、時間消化申請の時間年休を作成しない
		condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		result = CheckConditionReflectAppTimeLeave.check(digest, condition);
		assertThat(result.getTimeAnnualLeave().v()).isEqualTo(0);// 時間年休を作成しない
	}

	/*
	 * テストしたい内容
	 * 
	 * →①時間休暇の申請反映条件チェック、時間消化申請の60H超休を作成する
	 * 
	 * →②時間休暇の申請反映条件チェック、時間消化申請の60H超休を作成しない
	 * 
	 * 準備するデータ
	 * 
	 * →①時間休暇の申請反映条件.60H超休= する
	 * 
	 * →②時間休暇の申請反映条件.60H超休= しない
	 */
	@Test
	public void testCase２() {
		AttendanceTime timeCommon = new AttendanceTime(0);
		TimeDigestApplicationShare digest = new TimeDigestApplicationShare(new AttendanceTime(60), timeCommon,
				timeCommon, timeCommon, timeCommon, timeCommon, // 60H超休時間 = 60
				Optional.empty());
		// ①時間休暇の申請反映条件チェック、時間消化申請の60H超休を作成する
		TimeLeaveAppReflectCondition condition = new TimeLeaveAppReflectCondition(NotUseAtr.USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		TimeDigestApplicationShare result = CheckConditionReflectAppTimeLeave.check(digest, condition);
		assertThat(result.getOvertime60H().v()).isEqualTo(60);// 60H超休を作成する
		assertThat(result.getTimeAnnualLeave().v()).isEqualTo(0);
		assertThat(result.getChildTime().v()).isEqualTo(0);
		assertThat(result.getNursingTime().v()).isEqualTo(0);
		assertThat(result.getTimeOff().v()).isEqualTo(0);
		assertThat(result.getTimeSpecialVacation().v()).isEqualTo(0);

		// ②時間休暇の申請反映条件チェック、時間消化申請の時間年休を作成しない
		condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		result = CheckConditionReflectAppTimeLeave.check(digest, condition);
		assertThat(result.getOvertime60H().v()).isEqualTo(0);// 60H超休を作成しない
	}

	/*
	 * テストしたい内容
	 * 
	 * →①時間休暇の申請反映条件チェック、時間消化申請の時間代休を作成する
	 * 
	 * →②時間休暇の申請反映条件チェック、時間消化申請の時間代休を作成しない
	 * 
	 * 準備するデータ
	 * 
	 * →①時間休暇の申請反映条件.時間代休= する
	 * 
	 * →②時間休暇の申請反映条件.時間代休= しない
	 */
	@Test
	public void testCase３() {
		AttendanceTime timeCommon = new AttendanceTime(0);
		TimeDigestApplicationShare digest = new TimeDigestApplicationShare(timeCommon, timeCommon, timeCommon,
				new AttendanceTime(60), timeCommon, timeCommon, // 時間代休時間 = 60
				Optional.empty());
		// ①時間休暇の申請反映条件チェック、時間消化申請の時間代休を作成する
		TimeLeaveAppReflectCondition condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		TimeDigestApplicationShare result = CheckConditionReflectAppTimeLeave.check(digest, condition);
		assertThat(result.getTimeOff().v()).isEqualTo(60);// 時間代休を作成する
		assertThat(result.getTimeAnnualLeave().v()).isEqualTo(0);
		assertThat(result.getChildTime().v()).isEqualTo(0);
		assertThat(result.getNursingTime().v()).isEqualTo(0);
		assertThat(result.getOvertime60H().v()).isEqualTo(0);
		assertThat(result.getTimeSpecialVacation().v()).isEqualTo(0);

		// ②時間休暇の申請反映条件チェック、時間消化申請の時間年休を作成しない
		condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		result = CheckConditionReflectAppTimeLeave.check(digest, condition);
		assertThat(result.getTimeOff().v()).isEqualTo(0);// 時間代休を作成しない
	}

	/*
	 * テストしたい内容
	 * 
	 * →①時間休暇の申請反映条件チェック、時間消化申請の時間特別休暇を作成する
	 * 
	 * →②時間休暇の申請反映条件チェック、時間消化申請の時間特別休暇を作成しない
	 * 
	 * 準備するデータ
	 * 
	 * →①時間休暇の申請反映条件.時間特別休暇= する
	 * 
	 * →②時間休暇の申請反映条件.時間特別休暇= しない
	 */
	@Test
	public void testCase4() {
		AttendanceTime timeCommon = new AttendanceTime(0);
		TimeDigestApplicationShare digest = new TimeDigestApplicationShare(timeCommon, timeCommon, timeCommon,
				timeCommon, new AttendanceTime(60), timeCommon, // 時間特別休暇時間 = 60
				Optional.of(1));
		// ①時間休暇の申請反映条件チェック、時間消化申請の時間特別休暇を作成する
		TimeLeaveAppReflectCondition condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.USE);
		TimeDigestApplicationShare result = CheckConditionReflectAppTimeLeave.check(digest, condition);
		assertThat(result.getTimeSpecialVacation().v()).isEqualTo(60);// 時間特別休暇を作成する
		assertThat(result.getSpecialVacationFrameNO().get()).isEqualTo(1);
		assertThat(result.getTimeAnnualLeave().v()).isEqualTo(0);
		assertThat(result.getChildTime().v()).isEqualTo(0);
		assertThat(result.getNursingTime().v()).isEqualTo(0);
		assertThat(result.getOvertime60H().v()).isEqualTo(0);
		assertThat(result.getTimeOff().v()).isEqualTo(0);

		// ②時間休暇の申請反映条件チェック、時間消化申請の時間特別休暇を作成しない
		condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		result = CheckConditionReflectAppTimeLeave.check(digest, condition);
		assertThat(result.getTimeSpecialVacation().v()).isEqualTo(0);// 時間特別休暇を作成しない
	}

	/*
	 * テストしたい内容
	 * 
	 * →①時間休暇の申請反映条件チェック、時間消化申請の子看護を作成する
	 * 
	 * →②時間休暇の申請反映条件チェック、時間消化申請の子看護を作成しない
	 * 
	 * 準備するデータ
	 * 
	 * →①時間休暇の申請反映条件.子看護= する
	 * 
	 * →②時間休暇の申請反映条件.子看護= しない
	 */
	@Test
	public void testCase5() {
		AttendanceTime timeCommon = new AttendanceTime(0);
		TimeDigestApplicationShare digest = new TimeDigestApplicationShare(timeCommon, timeCommon, new AttendanceTime(60), timeCommon,
				timeCommon, timeCommon, // 子看護時間 = 60
				Optional.of(1));
		// ①時間休暇の申請反映条件チェック、時間消化申請の子看護を作成する
		TimeLeaveAppReflectCondition condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		TimeDigestApplicationShare result = CheckConditionReflectAppTimeLeave.check(digest, condition);
		assertThat(result.getChildTime().v()).isEqualTo(60);//子看護を作成する
		assertThat(result.getTimeAnnualLeave().v()).isEqualTo(0);
		assertThat(result.getNursingTime().v()).isEqualTo(0);
		assertThat(result.getOvertime60H().v()).isEqualTo(0);
		assertThat(result.getTimeOff().v()).isEqualTo(0);
		assertThat(result.getTimeSpecialVacation().v()).isEqualTo(0);

		// ②時間休暇の申請反映条件チェック、時間消化申請の子看護を作成しない
		condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		result = CheckConditionReflectAppTimeLeave.check(digest, condition);
		assertThat(result.getChildTime().v()).isEqualTo(0);// 子看護を作成しない
	}
	
	/*
	 * テストしたい内容
	 * 
	 * →①時間休暇の申請反映条件チェック、時間消化申請の介護を作成する
	 * 
	 * →②時間休暇の申請反映条件チェック、時間消化申請の介護を作成しない
	 * 
	 * 準備するデータ
	 * 
	 * →①時間休暇の申請反映条件.介護= する
	 * 
	 * →②時間休暇の申請反映条件.介護= しない
	 */
	@Test
	public void testCase6() {
		AttendanceTime timeCommon = new AttendanceTime(0);
		TimeDigestApplicationShare digest = new TimeDigestApplicationShare(timeCommon, new AttendanceTime(60), timeCommon, timeCommon,
				timeCommon, timeCommon, // 介護時間 = 60
				Optional.of(1));
		// ①時間休暇の申請反映条件チェック、時間消化申請の介護を作成する
		TimeLeaveAppReflectCondition condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		TimeDigestApplicationShare result = CheckConditionReflectAppTimeLeave.check(digest, condition);
		assertThat(result.getNursingTime().v()).isEqualTo(60);//介護を作成する
		assertThat(result.getTimeAnnualLeave().v()).isEqualTo(0);
		assertThat(result.getChildTime().v()).isEqualTo(0);
		assertThat(result.getOvertime60H().v()).isEqualTo(0);
		assertThat(result.getTimeOff().v()).isEqualTo(0);
		assertThat(result.getTimeSpecialVacation().v()).isEqualTo(0);

		// ②時間休暇の申請反映条件チェック、時間消化申請の介護を作成しない
		condition = new TimeLeaveAppReflectCondition(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE,
				NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE);
		result = CheckConditionReflectAppTimeLeave.check(digest, condition);
		assertThat(result.getNursingTime().v()).isEqualTo(0);// 介護を作成しない
	}
	
}
