package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@RunWith(JMockit.class)
public class BanHolidayTogetherTest {
	
	@Test
	public void getters() {
		
		BanHolidayTogether banHdTogether = BanHolidayTogether.create(
				TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
				new BanHolidayTogetherCode("0001"), 
				new BanHolidayTogetherName("禁止グループA"),
				Optional.empty(),
				new MinNumberEmployeeTogether(1),
				creatEmpsCanNotSameHolidays(2)
				);
		
		NtsAssert.invokeGetters(banHdTogether);
	}
	

	/**
	 * inv1: 同日の休日取得を禁止する社員.size() > 1	
	 * ケース: 同日の休日取得を禁止する社員がemptyです -> Msg_1885
	 */
	@Test
	public void check_inv1_emptyList() {		
		NtsAssert.businessException("Msg_1885", ()-> {
			BanHolidayTogether.create(
					TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
					new BanHolidayTogetherCode("0001"), 
					new BanHolidayTogetherName("会社の禁止グループ"),
					creatCalendarReferenceCompany(),
					new MinNumberEmployeeTogether(3),
					Collections.emptyList()
					);
		});
	}
	
	/**
	 * inv1: 同日の休日取得を禁止する社員.size() > 1	
	 * ケース: 同日の休日取得を禁止する社員のsize() = 1 -> Msg_1885
	 */
	@Test
	public void check_inv1_sizeEquals1() {
		
		NtsAssert.businessException("Msg_1885", ()-> {
			BanHolidayTogether.create(
					TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
					new BanHolidayTogetherCode("0001"), 
					new BanHolidayTogetherName("会社の禁止グループ"),
					creatCalendarReferenceCompany(),
					new MinNumberEmployeeTogether(3),
					creatEmpsCanNotSameHolidays(1)
					);
		});
	}
	
	/**
	 * inv2: 最低限出勤すべき人数 <= 同日の休日取得を禁止する社員.size()
	 * ケース: 同日の休日取得を禁止する社員.size()= 5, 最低限出勤すべき人数 = 10 ->Msg_1886
	*/
	@Test
	public void check_inv2_empsCanNotSameHolidaysBeforeMinNumberOfEmployeeToWork() {
		

		BanHolidayTogether.create(
				TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
				new BanHolidayTogetherCode("0001"), 
				new BanHolidayTogetherName("会社の禁止グループ"),
				creatCalendarReferenceCompany(),
				new MinNumberEmployeeTogether(5),
				creatEmpsCanNotSameHolidays(5)
				);
		
		NtsAssert.businessException("Msg_1886", ()-> {
			
			BanHolidayTogether.create(
					TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
					new BanHolidayTogetherCode("0001"), 
					new BanHolidayTogetherName("会社の禁止グループ"),
					creatCalendarReferenceCompany(),
					new MinNumberEmployeeTogether(6),
					creatEmpsCanNotSameHolidays(5)
					);
		});
		
	}
	

	/**
	 * 作成する 組織の単位 = 職場, 稼働日の参照先 = empty
	 * 
	 */
	@Test
	public void create_banSameDayHolidayNotReference_success() {
		val empsCanNotSameHolidays = creatEmpsCanNotSameHolidays(2);

		val banHolidayCompanyEmpty = BanHolidayTogether.create(
				TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
				new BanHolidayTogetherCode("001"),
				new BanHolidayTogetherName("禁止グループ会社A"), 
				Optional.empty(),
				new MinNumberEmployeeTogether(1),
				empsCanNotSameHolidays);
		
		assertThat(banHolidayCompanyEmpty.getTargetOrg().getUnit()).isEqualTo(TargetOrganizationUnit.WORKPLACE);
		assertThat(banHolidayCompanyEmpty.getBanHolidayTogetherCode().v()).isEqualTo("001");
		
		//名称

		//稼働日の参照先
		assertThat(banHolidayCompanyEmpty.getWorkDayReference()).isEmpty();

		//同日の休日取得を禁止する社員
		assertThat(banHolidayCompanyEmpty.getEmpsCanNotSameHolidays()).containsExactlyInAnyOrderElementsOf(empsCanNotSameHolidays);
	}
	
	/**
	 * 作成する 稼働日の参照先 = 会社を参照する
	 * 
	 */
	@Test
	public void create_banSameDayHolidayCompany_success() {
		
		val banHdCom = BanHolidayTogether.create(
				TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
				new BanHolidayTogetherCode("0001"),
				new BanHolidayTogetherName("禁止グループ会社A"),
				creatCalendarReferenceCompany(), 
				new MinNumberEmployeeTogether(1),
				creatEmpsCanNotSameHolidays(2));
		
		assertThat(banHdCom.getWorkDayReference()).isNotEmpty();
		assertThat(banHdCom.getWorkDayReference().get().getBusinessDaysCalendarType()).isEqualTo(BusinessDaysCalendarType.COMPANY);
	}
	
	/**
	 * 作成する 稼働日の参照先 = 職場カレンダーを参照する
	 * 
	 */
	@Test
	public void create_banSameDayHolidayWorkplace_success() {
		
		val banHdWorkplace = BanHolidayTogether.create(
				TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
				new BanHolidayTogetherCode("0001"),
				new BanHolidayTogetherName("禁止グループ職場カレンダー"),
				Optional.ofNullable(new ReferenceCalendarWorkplace(IdentifierUtil.randomUniqueId())),
				new MinNumberEmployeeTogether(1),
				creatEmpsCanNotSameHolidays(2));

		assertThat(banHdWorkplace.getWorkDayReference()).isNotEmpty();
		assertThat(banHdWorkplace.getWorkDayReference().get().getBusinessDaysCalendarType()).isEqualTo(BusinessDaysCalendarType.WORKPLACE);

	}
	
	/**
	 * 作成する 稼働日の参照先 = 分類カレンダーを参照する
	 * 
	 */
	@Test
	public void create_banHolidayTogetherClassification_success() {
		
		val banHolidayClass = BanHolidayTogether.create(
				TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
				new BanHolidayTogetherCode("0001"), 
				new BanHolidayTogetherName("禁止グループ分類カレンダー"),
				Optional.of(new ReferenceCalendarClass(new ClassificationCode("0009"))),
				new MinNumberEmployeeTogether(1), creatEmpsCanNotSameHolidays(2));
		
		assertThat(banHolidayClass.getWorkDayReference()).isNotEmpty();
		assertThat(banHolidayClass.getWorkDayReference().get()).isEqualTo(BusinessDaysCalendarType.CLASSSICATION);

	}
	
	private Optional<ReferenceCalendar> creatCalendarReferenceCompany(){
		
		return Optional.ofNullable(new ReferenceCalendarCompany());
	}
	/**
	 * Helperを作る
	 * creatEmpsCanNotSameHolidays
	 * @param size
	 * @return
	 */
	private List<String> creatEmpsCanNotSameHolidays(int size){
		List<String> result = new ArrayList<>();
		
		for(int i = 0; i < size; i++){
			result.add(IdentifierUtil.randomUniqueId());
		}
		return result;
	}
}
