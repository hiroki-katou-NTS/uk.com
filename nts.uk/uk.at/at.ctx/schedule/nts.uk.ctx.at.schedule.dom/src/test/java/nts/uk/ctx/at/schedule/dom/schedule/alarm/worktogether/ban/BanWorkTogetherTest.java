package nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
/**
 * UnitTest: 同時出勤禁止
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class BanWorkTogetherTest {
	
	@Test
	public void getters() {
		val banWorkTogether = BanWorkTogetherHelper.banWorkTogether;
		NtsAssert.invokeGetters(banWorkTogether);
	}
	
	/**
	 * 適用する時間帯 = 夜勤時間帯	
	 * 禁止する社員の組み合わせのsize = 0 -> Msg_1875
	 */
	@Test
	public void night_shift_check_inv1_emptyList() {
		NtsAssert.businessException("Msg_1875", ()-> {
			BanWorkTogether.createByNightShift(
					TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
					new BanWorkTogetherCode("001"),
					new BanWorkTogetherName("同時出勤禁1"),
					new ArrayList<>(),
					3
					);
		});
	}
	
	/**
	 * 適用する時間帯 = 全日帯
	 * 禁止する社員の組み合わせのsize = 0 -> Msg_1875
	 */
	@Test
	public void all_day_check_inv1_emptyList() {
		NtsAssert.businessException("Msg_1875", ()-> {
			BanWorkTogether.createBySpecifyingAllDay(
					TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
					new BanWorkTogetherCode("001"),
					new BanWorkTogetherName("同時出勤禁1"),
					new ArrayList<>(),
					3
					);
		});
	}
	
	/**
	 * 適用する時間帯 = 夜勤時間帯	
	 * 禁止する社員の組み合わせのsize = 1 -> Msg_1875
	 */
	@Test
	public void night_shift_check_inv1_sizeEquals1() {		
		NtsAssert.businessException("Msg_1875", ()-> {
			BanWorkTogether.createByNightShift(
					TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
					new BanWorkTogetherCode("001"),
					new BanWorkTogetherName("同時出勤禁1"),
					Arrays.asList( "EMPLOYEE_1"),
					3);
		});
	}
	
	/**
	 * 適用する時間帯 = 全日帯	
	 * 禁止する社員の組み合わせのsize = 1 -> Msg_1875
	 */
	@Test
	public void all_day_check_inv1_sizeEquals1() {		
		NtsAssert.businessException("Msg_1875", ()-> {
			BanWorkTogether.createBySpecifyingAllDay(
					TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
					new BanWorkTogetherCode("001"),
					new BanWorkTogetherName("同時出勤禁1"),
					Arrays.asList( "EMPLOYEE_1"),
					3);
		});
	}
	
	
	/**
	 * 適用する時間帯 = 夜勤時間帯
	 * ケース: 禁止する社員の組み合わせ.size()= 10, 許容する人数  = 10 ->Msg_1787
	*/
	@Test
	public void night_shift_check_inv2_empBanWorkOfSizeEqualNumberOfEmp() {
		val allowableNumberOfEmp = 10;
		val empBanWorkTogetherLst = BanWorkTogetherHelper.creatEmpBanWorkTogetherLst(10);
		NtsAssert.businessException("Msg_1787", ()-> {
			BanWorkTogether.createByNightShift(
					TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
					new BanWorkTogetherCode("001"),
					new BanWorkTogetherName("同時出勤禁1"),
					empBanWorkTogetherLst,
					allowableNumberOfEmp

					);
		});
	}
	
	/**
	 * 適用する時間帯 = 全日帯	
	 * ケース: 禁止する社員の組み合わせ.size()= 10, 許容する人数  = 10 ->Msg_1787
	 */
	@Test
	public void all_day_check_inv2_empBanWorkOfSizeEqualNumberOfEmp() {
		val allowableNumberOfEmp = 10;
		val empBanWorkTogetherLst = BanWorkTogetherHelper.creatEmpBanWorkTogetherLst(10);
		NtsAssert.businessException("Msg_1787", ()-> {
			BanWorkTogether.createBySpecifyingAllDay(
					TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
					new BanWorkTogetherCode("001"),
					new BanWorkTogetherName("同時出勤禁1"),
					empBanWorkTogetherLst,
					allowableNumberOfEmp

					);
		});
	}
	
	/**
	 * 適用する時間帯 = 夜勤時間帯
	 * ケース: 禁止する社員の組み合わせ.size()= 10, 許容する人数  = 11 ->Msg_1787
	*/
	@Test
	public void night_shift_check_inv2_empBanWorkOfSizeBeforeNumberOfEmp() {
		val allowableNumberOfEmp = 11;
		val empBanWorkTogetherLst = BanWorkTogetherHelper.creatEmpBanWorkTogetherLst(10);
		
		NtsAssert.businessException("Msg_1787", ()-> {
			BanWorkTogether.createByNightShift(
					TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
					new BanWorkTogetherCode("001"),
					new BanWorkTogetherName("同時出勤禁1"),
					empBanWorkTogetherLst,
					allowableNumberOfEmp

					);
		});
	}
	
	/**
	 * 適用する時間帯 = 全日帯
	 * ケース: 禁止する社員の組み合わせ.size()= 10, 許容する人数  = 11 ->Msg_1787
	*/
	@Test
	public void all_day_check_inv2_empBanWorkOfSizeBeforeNumberOfEmp() {
		val allowableNumberOfEmp = 11;
		val empBanWorkTogetherLst = BanWorkTogetherHelper.creatEmpBanWorkTogetherLst(10);
		
		NtsAssert.businessException("Msg_1787", ()-> {
			BanWorkTogether.createBySpecifyingAllDay(
					TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
					new BanWorkTogetherCode("001"),
					new BanWorkTogetherName("同時出勤禁1"),
					empBanWorkTogetherLst,
					allowableNumberOfEmp

					);
		});
	}
	
	/**
	 * 適用する時間帯 = 夜勤時間帯
	 * 禁止する社員の組み合わせ.size()= 10, 許容する人数  = 9
	 * 夜勤時間帯を指定して作成する:　success
	 * 
	*/
	@Test
	public void create_night_shift_sucess() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY");
		val allowableNumberOfEmp = 9;
		val empBanWorkTogetherLst = BanWorkTogetherHelper.creatEmpBanWorkTogetherLst(10);
		val banWorkTogether = BanWorkTogether.createByNightShift(
				targetOrg,
				new BanWorkTogetherCode("001"),
				new BanWorkTogetherName("night_shift"),
				empBanWorkTogetherLst,
				allowableNumberOfEmp
				);
		
		assertThat(banWorkTogether.getTargetOrg()).isEqualTo(targetOrg);
		assertThat(banWorkTogether.getCode().v()).isEqualTo("001");
		assertThat(banWorkTogether.getName().v()).isEqualTo("night_shift");
		assertThat(banWorkTogether.getApplicableTimeZoneCls()).isEqualTo(ApplicableTimeZoneCls.NIGHTSHIFT);
		assertThat(banWorkTogether.getEmpBanWorkTogetherLst()).containsExactlyInAnyOrderElementsOf(empBanWorkTogetherLst);
	}
	
	/**
	 * 適用する時間帯 = 全日帯
	 * 禁止する社員の組み合わせ.size()= 10, 許容する人数  = 9
	 * 終日を指定して作成する:　success
	 * 
	 */
	@Test
	public void create_all_day_success() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY");
		val allowableNumberOfEmp = 9;
		val empBanWorkTogetherLst = BanWorkTogetherHelper.creatEmpBanWorkTogetherLst(10);
		val banWorkTogether = BanWorkTogether.createBySpecifyingAllDay(
				targetOrg,
				new BanWorkTogetherCode("001"),
				new BanWorkTogetherName("all_day"),
				empBanWorkTogetherLst,
				allowableNumberOfEmp
				);
		
		assertThat(banWorkTogether.getTargetOrg()).isEqualTo(targetOrg);
		assertThat(banWorkTogether.getCode().v()).isEqualTo("001");
		assertThat(banWorkTogether.getName().v()).isEqualTo("all_day");
		assertThat(banWorkTogether.getApplicableTimeZoneCls()).isEqualTo(ApplicableTimeZoneCls.ALLDAY);
		assertThat(banWorkTogether.getEmpBanWorkTogetherLst()).containsExactlyInAnyOrderElementsOf(empBanWorkTogetherLst);
		
	}
}
