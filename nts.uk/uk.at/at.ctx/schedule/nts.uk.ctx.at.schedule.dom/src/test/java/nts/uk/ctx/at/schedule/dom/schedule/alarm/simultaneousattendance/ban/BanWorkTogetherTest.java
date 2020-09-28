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
public class BanWorkTogetherTest {
	
	@Test
	public void getters() {
		List<String> empsCanNotSameHolidays = BanWorkTogetherHelper.creatEmpBanWorkTogetherLst(5);
		
		BanWorkTogether simultaneousAttendanceBan = BanWorkTogether.createByNightShift(
				TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
				new BanWorkTogetherCode("001"),
				new BanWorkTogetherName("同時出勤禁1"),
				new MaxOfNumberEmployeeTogether(3),
				empsCanNotSameHolidays 
				);
		NtsAssert.invokeGetters(simultaneousAttendanceBan);
	}
	
	/**
	 * inv-1:  禁止する社員の組み合わせ.size() > 1	
	 * ケース: 禁止する社員の組み合わせがemptyです -> Msg_1875
	 */
	@Test
	public void check_inv1_emptyList() {
		NtsAssert.businessException("Msg_1875", ()-> {
			BanWorkTogether.createByNightShift(
					TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
					new BanWorkTogetherCode("001"),
					new BanWorkTogetherName("同時出勤禁1"),
					new MaxOfNumberEmployeeTogether(3),
					new ArrayList<>()
					);
		});
	}
	
	/**
	 * inv-1:  禁止する社員の組み合わせ.size() > 1	
	 * ケース: 禁止する社員の組み合わせのsize = 1 -> Msg_1875
	 */
	@Test
	public void check_inv1_sizeEquals1() {		
		NtsAssert.businessException("Msg_1875", ()-> {
			BanWorkTogether.createByNightShift(
					TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
					new BanWorkTogetherCode("001"),
					new BanWorkTogetherName("同時出勤禁1"),
					new MaxOfNumberEmployeeTogether(3),
					Arrays.asList( "EMPLOYEE_1")
					);
		});
	}
	
	/**
	 * inv-2: 許容する人数 < 禁止する社員の組み合わせ.size()		
	 * ケース: 禁止する社員の組み合わせ.size()= 5, 許容する人数  = 10 ->Msg_1787
	*/
	@Test
	public void check_inv2_empBanWorkTogetherLstBeforeAllowableNumberOfEmp() {
		val allowableNumberOfEmp = new MaxOfNumberEmployeeTogether(10);
		//ケース: 禁止する社員の組み合わせ.size()= 10, 許容する人数  = 10
		BanWorkTogether.createByNightShift(
				TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
				new BanWorkTogetherCode("001"),
				new BanWorkTogetherName("同時出勤禁1"),
				allowableNumberOfEmp,
				Arrays.asList(
						 "EMPLOYEE_1",
						 "EMPLOYEE_2",
						 "EMPLOYEE_3",
						 "EMPLOYEE_4",
						 "EMPLOYEE_5",
						 "EMPLOYEE_6",
						 "EMPLOYEE_7",
						 "EMPLOYEE_8",
						 "EMPLOYEE_9",
						 "EMPLOYEE_10")
				);
		//ケース: 禁止する社員の組み合わせ.size()= 5, 許容する人数  = 10
		NtsAssert.businessException("Msg_1787", ()-> {
			BanWorkTogether.createByNightShift(
					TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
					new BanWorkTogetherCode("001"),
					new BanWorkTogetherName("同時出勤禁1"),
					allowableNumberOfEmp,
					Arrays.asList(
							 "EMPLOYEE_1",
							 "EMPLOYEE_2",
							 "EMPLOYEE_3",
							 "EMPLOYEE_4",
							 "EMPLOYEE_5")
					);
		});
	}
	
	/**
	 * 終日を指定して作成する:　success
	 * 
	 */
	@Test
	public void create_specifyingAllDay_success() {
		val employeeIds = Arrays.asList(
				 "EMPLOYEE_1",
				 "EMPLOYEE_2",
				 "EMPLOYEE_3",
				 "EMPLOYEE_4",
				 "EMPLOYEE_5");
		val banWorkTogetherAllDay = BanWorkTogether.createBySpecifyingAllDay(
				TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
				new BanWorkTogetherCode("001"),
				new BanWorkTogetherName("同時出勤禁止1"),
				new MaxOfNumberEmployeeTogether(4),
				employeeIds
				);
		
		assertThat(banWorkTogetherAllDay.getTargetOrg().getUnit()).isEqualTo(TargetOrganizationUnit.WORKPLACE);
		assertThat(banWorkTogetherAllDay.getTargetOrg().getWorkplaceId().get()).isEqualTo("DUMMY");
		assertThat(banWorkTogetherAllDay.getBanWorkTogetherCode().v()).isEqualTo("001");
		assertThat(banWorkTogetherAllDay.getBanWorkTogetherName().v()).isEqualTo("同時出勤禁止1");
		assertThat(banWorkTogetherAllDay.getApplicableTimeZoneCls().value).isEqualTo(ApplicableTimeZoneCls.ALLDAY.value);
		assertThat(banWorkTogetherAllDay.getEmpBanWorkTogetherLst()).containsExactlyInAnyOrderElementsOf(employeeIds);
		
	}
	
	/**
	 * 夜勤時間帯を指定して作成する: success
	 * 
	 */
	@Test
	public void create_nightShift_success() {
		val employeeIds = Arrays.asList(
				 "EMPLOYEE_1",
				 "EMPLOYEE_2",
				 "EMPLOYEE_3",
				 "EMPLOYEE_4",
				 "EMPLOYEE_5");
		val banTogetherNightShift = BanWorkTogether.createByNightShift(
				TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
				new BanWorkTogetherCode("001"),
				new BanWorkTogetherName("同時出勤禁止1"),
				new MaxOfNumberEmployeeTogether(4),
				employeeIds
				);
		
		assertThat(banTogetherNightShift.getTargetOrg().getUnit()).isEqualTo(TargetOrganizationUnit.WORKPLACE);
		assertThat(banTogetherNightShift.getTargetOrg().getWorkplaceId().get()).isEqualTo("DUMMY");
		assertThat(banTogetherNightShift.getBanWorkTogetherCode().v()).isEqualTo("001");
		assertThat(banTogetherNightShift.getBanWorkTogetherName().v()).isEqualTo("同時出勤禁止1");
		assertThat(banTogetherNightShift.getApplicableTimeZoneCls().value).isEqualTo(ApplicableTimeZoneCls.NIGHTSHIFT.value);
		assertThat(banTogetherNightShift.getEmpBanWorkTogetherLst()).containsExactlyInAnyOrderElementsOf(employeeIds);
	}

}
