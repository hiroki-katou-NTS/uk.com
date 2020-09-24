package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@RunWith(JMockit.class)
public class SimultaneousAttendanceBanTest {
	
	@Test
	public void getters() {
		List<String> empsCanNotSameHolidays = SimultaneousAttendanceBanHelper.creatEmpBanWorkTogetherLst(5);
		
		SimultaneousAttendanceBan simultaneousAttendanceBan = SimultaneousAttendanceBan.createByNightShift(
				TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
				new SimultaneousAttendanceBanCode("001"),
				new SimultaneousAttendanceBanName("同時出勤禁1"),
				empsCanNotSameHolidays, 
				new MaxOfNumberEmployeeTogether(3));
		NtsAssert.invokeGetters(simultaneousAttendanceBan);
	}
	
	/**
	 * inv-1:  禁止する社員の組み合わせ.size() > 1	
	 * ケース: 禁止する社員の組み合わせがemptyです -> Msg_1875
	 */
	@Test
	public void check_inv1_emptyList() {
		NtsAssert.businessException("Msg_1875", ()-> {
			SimultaneousAttendanceBan.createByNightShift(
					TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
					new SimultaneousAttendanceBanCode("001"),
					new SimultaneousAttendanceBanName("同時出勤禁1"),
					new ArrayList<>(), 
					new MaxOfNumberEmployeeTogether(3));
		});
	}
	
	/**
	 * inv-1:  禁止する社員の組み合わせ.size() > 1	
	 * ケース: 禁止する社員の組み合わせのsize = 1 -> Msg_1875
	 */
	@Test
	public void check_inv1_sizeEquals1() {		
		NtsAssert.businessException("Msg_1875", ()-> {
			SimultaneousAttendanceBan.createByNightShift(
					TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
					new SimultaneousAttendanceBanCode("001"),
					new SimultaneousAttendanceBanName("同時出勤禁1"),
					Arrays.asList(
							 "517ef7f8-77d0-4eb0-b539-05e03a23f9e1"),
					new MaxOfNumberEmployeeTogether(3));
		});
	}
	
	/**
	 * inv-2: 許容する人数 < 禁止する社員の組み合わせ.size()		
	 * ケース: 禁止する社員の組み合わせ.size()= 5, 許容する人数  = 10 ->Msg_1787
	*/
	@Test
	public void check_inv2_empBanWorkTogetherLstBeforeAllowableNumberOfEmp() {
		val allowableNumberOfEmp = new MaxOfNumberEmployeeTogether(10);
		
		NtsAssert.businessException("Msg_1787", ()-> {
			SimultaneousAttendanceBan.createByNightShift(
					TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
					new SimultaneousAttendanceBanCode("001"),
					new SimultaneousAttendanceBanName("同時出勤禁1"),
					 Arrays.asList(
							 "517ef7f8-77d0-4eb0-b539-05e03a23f9e1",
							 "517ef7f8-77d0-4eb0-b539-05e03a23f9e2",
							 "517ef7f8-77d0-4eb0-b539-05e03a23f9e3",
							 "517ef7f8-77d0-4eb0-b539-05e03a23f9e4",
							 "517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
					allowableNumberOfEmp);
		});
	}
	
	/**
	 * 終日を指定して作成する
	 * 
	 */
	@Test
	public void create_specifyingAllDay_success() {
		val simultaneousAttendanceBanAllDay = SimultaneousAttendanceBan.createBySpecifyingAllDay(
				TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
				new SimultaneousAttendanceBanCode("001"),
				new SimultaneousAttendanceBanName("同時出勤禁止1"),
				 Arrays.asList(
						 "517ef7f8-77d0-4eb0-b539-05e03a23f9e1",
						 "517ef7f8-77d0-4eb0-b539-05e03a23f9e2",
						 "517ef7f8-77d0-4eb0-b539-05e03a23f9e3",
						 "517ef7f8-77d0-4eb0-b539-05e03a23f9e4",
						 "517ef7f8-77d0-4eb0-b539-05e03a23f9e5"), 
				new MaxOfNumberEmployeeTogether(4));

		assertThat(simultaneousAttendanceBanAllDay).extracting(
				ban -> ban.getTargetOrg().getUnit(),
				ban -> ban.getTargetOrg().getWorkplaceId().get(),
				ban -> ban.getTargetOrg().getWorkplaceGroupId().isPresent(),
				ban -> ban.getSimultaneousAttBanCode().v(),
				ban -> ban.getSimultaneousAttendanceBanName().v(),
				ban -> ban.getApplicableTimeZoneCls(),
				ban  -> ban.getAllowableNumberOfEmp().v()				
				).containsExactly(
						TargetOrganizationUnit.WORKPLACE,
						"517ef7f8-77d0-4eb0-b539-05e03a23f9e5",
						false,
						"001",
						"同時出勤禁止1",
						ApplicableTimeZoneCls.ALLDAY,
						4);
		
		assertThat(simultaneousAttendanceBanAllDay.getEmpBanWorkTogetherLst().equals(
				Arrays.asList(
				 "517ef7f8-77d0-4eb0-b539-05e03a23f9e1",
				 "517ef7f8-77d0-4eb0-b539-05e03a23f9e2",
				 "517ef7f8-77d0-4eb0-b539-05e03a23f9e3",
				 "517ef7f8-77d0-4eb0-b539-05e03a23f9e4",
				 "517ef7f8-77d0-4eb0-b539-05e03a23f9e5")));
	}
	
	/**
	 * 夜勤時間帯を指定して作成する
	 * 
	 */
	@Test
	public void create_nightShift_success() {
		val simultaneousAttendanceBanNightShift = SimultaneousAttendanceBan.createByNightShift(
				TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
				new SimultaneousAttendanceBanCode("001"),
				new SimultaneousAttendanceBanName("同時出勤禁止1"),
				 Arrays.asList(
						 "517ef7f8-77d0-4eb0-b539-05e03a23f9e1",
						 "517ef7f8-77d0-4eb0-b539-05e03a23f9e2",
						 "517ef7f8-77d0-4eb0-b539-05e03a23f9e3",
						 "517ef7f8-77d0-4eb0-b539-05e03a23f9e4",
						 "517ef7f8-77d0-4eb0-b539-05e03a23f9e5"), 
				new MaxOfNumberEmployeeTogether(4));

		assertThat(simultaneousAttendanceBanNightShift).extracting(
				ban -> ban.getTargetOrg().getUnit(),
				ban -> ban.getTargetOrg().getWorkplaceId().get(),
				ban -> ban.getTargetOrg().getWorkplaceGroupId().isPresent(),
				ban -> ban.getSimultaneousAttBanCode().v(),
				ban -> ban.getSimultaneousAttendanceBanName().v(),
				ban -> ban.getApplicableTimeZoneCls(),
				ban  -> ban.getAllowableNumberOfEmp().v()				
				).containsExactly(
						TargetOrganizationUnit.WORKPLACE,
						"517ef7f8-77d0-4eb0-b539-05e03a23f9e5",
						false,
						"001",
						"同時出勤禁止1",
						ApplicableTimeZoneCls.NIGHTSHIFT,
						4);
		
		assertThat(simultaneousAttendanceBanNightShift.getEmpBanWorkTogetherLst().equals(
				Arrays.asList(
				 "517ef7f8-77d0-4eb0-b539-05e03a23f9e1",
				 "517ef7f8-77d0-4eb0-b539-05e03a23f9e2",
				 "517ef7f8-77d0-4eb0-b539-05e03a23f9e3",
				 "517ef7f8-77d0-4eb0-b539-05e03a23f9e4",
				 "517ef7f8-77d0-4eb0-b539-05e03a23f9e5")));
	}

}
