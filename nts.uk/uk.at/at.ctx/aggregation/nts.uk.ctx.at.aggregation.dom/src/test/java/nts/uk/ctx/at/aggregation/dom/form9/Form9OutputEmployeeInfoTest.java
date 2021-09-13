package nts.uk.ctx.at.aggregation.dom.form9;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.MedicalCareWorkStyle;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiName;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.EmployeeCodeAndDisplayNameImport;

@RunWith(JMockit.class)
public class Form9OutputEmployeeInfoTest {
	
	@Injectable
	private Form9OutputEmployeeInfo.Require require;
	
	@Test
	public void testGetter() {
		
		val employeeInfo = new Form9OutputEmployeeInfo(
					"employeeId"
				,	LicenseClassification.NURSE_ASSOCIATE
				,	"fullName"
				,	true, false, true, true, true, false
					);
		
		NtsAssert.invokeGetters( employeeInfo );
		
	}
	
	/**
	 * target: create
	 * pattern: 社員の医療勤務形態履歴項目 = empty
	 * expect: empty
	 */
	@Test
	public void testCreate_empMedicalHistItem_empty() {
		val baseDate = GeneralDate.ymd( 2020, 12, 21 );
		val employeeId = "employeeId";
		
		new Expectations() {
			{
				require.getEmpMedicalWorkStyleHistoryItem( (String) any, (GeneralDate) any );
				
			}
		};
		
		//Act
		val result = Form9OutputEmployeeInfo.create( require, baseDate, employeeId );
		
		//Assert
		assertThat( result ).isEmpty();
		
	}
	
	/**
	 * target: create
	 * pattern: 看護区分 = empty
	 * expect: empty
	 */
	@Test
	public void testCreate_nurseClassification_empty(
			@Injectable EmpMedicalWorkStyleHistoryItem empMedicalItem ) {
		
		val baseDate = GeneralDate.ymd( 2020, 12, 21 );
		val employeeId = "employeeId";
		
		new Expectations() {
			{
				require.getEmpMedicalWorkStyleHistoryItem( (String) any, (GeneralDate) any );
				result = Optional.of( empMedicalItem );
				
				require.getNurseClassification( (NurseClassifiCode) any );
			}
		};
		
		//Act
		val result = Form9OutputEmployeeInfo.create( require, baseDate, employeeId );
		
		//Assert
		assertThat( result ).isEmpty();
		
	}
	
	/**
	 * target: create
	 * pattern: 看護管理者
	 * expect: empty
	 */
	@Test
	public void testCreate_nurseManager(
			@Injectable EmpMedicalWorkStyleHistoryItem empMedicalItem ) {
		
		val baseDate = GeneralDate.ymd( 2020, 12, 21 );
		val employeeId = "employeeId";
		val nurseClassification = Helper.createNurseClassification(
					new NurseClassifiCode( "01" )
				,	LicenseClassification.NURSE
				,	true
				,	true);//看護管理者か
		
		
		new Expectations() {
			{
				require.getEmpMedicalWorkStyleHistoryItem( (String) any, (GeneralDate) any );
				result = Optional.of( empMedicalItem );
				
				require.getNurseClassification( (NurseClassifiCode) any );
				result = Optional.of( nurseClassification );
			}
		};
		
		//Act
		val result = Form9OutputEmployeeInfo.create( require, baseDate, employeeId );
		
		//Assert
		assertThat( result ).isEmpty();
		
	}
	
	/**
	 * target: create
	 * pattern: 社員の短時間勤務履歴項目 = empty
	 * expect: empty
	 */
	@Test
	public void testCreate_shortTime_empty() {
		
		val baseDate = GeneralDate.ymd( 2020, 12, 21 );
		val employeeId = "employeeId";
		val businessName = "businessName";
		val nursClassCode = new NurseClassifiCode( "01" );
		val license = LicenseClassification.NURSE;
		val fullTime = MedicalCareWorkStyle.FULLTIME;
		
		val nurseClassification = Helper.createNurseClassification(
					nursClassCode
				,	license
				,	true//事務的業務従事者か
				,	false);//看護管理者か
		val empMedicalHistItem = new EmpMedicalWorkStyleHistoryItem(
					employeeId
				,	"historyID"//DUMMY
				,	nursClassCode
				,	true//夜勤専従か
				,	fullTime//常勤
				,	true);//他部署兼務か
		val personInfo = new EmployeeCodeAndDisplayNameImport(
					employeeId
				,	"employeeCode"
				,	businessName );
		
		new Expectations() {
			{
				require.getEmpMedicalWorkStyleHistoryItem( (String) any, (GeneralDate) any );
				result = Optional.of( empMedicalHistItem );
				
				require.getNurseClassification( (NurseClassifiCode) any );
				result = Optional.of( nurseClassification );
				
				require.getShortWorkTimeHistoryItem( (String) any, (GeneralDate) any );
				//shortTime empty
				
				require.getPersonEmployeeBasicInfo( (String) any );
				result = Optional.of(personInfo);
			}
		};
		
		//Act
		val result = Form9OutputEmployeeInfo.create( require, baseDate, employeeId );
		
		//Assert
		assertThat( result.get().getEmployeeId() ).isEqualTo( employeeId );
		assertThat( result.get().getFullName() ).isEqualTo( businessName );
		assertThat( result.get().getLicense() ).isEqualTo( license );
		assertThat( result.get().isFullTime() ).isTrue();
		assertThat( result.get().isPartTime() ).isFalse();
		assertThat( result.get().isShortTime() ).isFalse();
		assertThat( result.get().isConcurrentPost() ).isTrue();
		assertThat( result.get().isNightShiftOnly() ).isTrue();
		assertThat( result.get().isOfficeWorker() ).isTrue();
		
	}
	
	/**
	 * target: create
	 * pattern: 社員の短時間勤務履歴項目 not empty
	 * expect: empty
	 */
	@Test
	public void testCreate_shortTime_not_empty(@Injectable ShortWorkTimeHistoryItem shortTime) {
		
		val baseDate = GeneralDate.ymd( 2020, 12, 21 );
		val employeeId = "employeeId";
		val businessName = "businessName";
		val nursClassCode = new NurseClassifiCode( "01" );
		val license = LicenseClassification.NURSE;
		val fullTime = MedicalCareWorkStyle.FULLTIME;
		
		val nurseClassification = Helper.createNurseClassification(
					nursClassCode
				,	license
				,	true//事務的業務従事者か
				,	false);//看護管理者か
		val empMedicalHistItem = new EmpMedicalWorkStyleHistoryItem(
					employeeId
				,	"historyID"//DUMMY
				,	nursClassCode
				,	true//夜勤専従か
				,	fullTime//常勤
				,	true);//他部署兼務か
		val personInfo = new EmployeeCodeAndDisplayNameImport(
					employeeId
				,	"employeeCode"
				,	businessName );
		
		new Expectations() {
			{
				require.getEmpMedicalWorkStyleHistoryItem( (String) any, (GeneralDate) any );
				result = Optional.of( empMedicalHistItem );
				
				require.getNurseClassification( (NurseClassifiCode) any );
				result = Optional.of( nurseClassification );
				
				require.getShortWorkTimeHistoryItem( (String) any, (GeneralDate) any );
				result = Optional.of( shortTime );//shortTime = true
				
				require.getPersonEmployeeBasicInfo( (String) any );
				result = Optional.of(personInfo);
			}
		};
		
		//Act
		val result = Form9OutputEmployeeInfo.create( require, baseDate, employeeId );
		
		//Assert
		assertThat( result.get().getEmployeeId() ).isEqualTo( employeeId );
		assertThat( result.get().getFullName() ).isEqualTo( businessName );
		assertThat( result.get().getLicense() ).isEqualTo( license );
		assertThat( result.get().isFullTime() ).isTrue();
		assertThat( result.get().isPartTime() ).isFalse();
		assertThat( result.get().isShortTime() ).isTrue();
		assertThat( result.get().isConcurrentPost() ).isTrue();
		assertThat( result.get().isNightShiftOnly() ).isTrue();
		assertThat( result.get().isOfficeWorker() ).isTrue();
		
	}
	
	
	
	private static class Helper{
		
		/**
		 * 看護区分を作る
		 * @param nurseClassifiCode 看護区分コード
		 * @param license 免許区分
		 * @param officeWorker 事務的業務従事者か
		 * @param isNursingManager 看護管理者か
		 * @return
		 */
		public static NurseClassification createNurseClassification(NurseClassifiCode nurseClassifiCode
				,	LicenseClassification license
				,	boolean officeWorker
				,	boolean isNursingManager) {
			return NurseClassification.create(new CompanyId("cid"), nurseClassifiCode
					,	new NurseClassifiName("name")
					, license, officeWorker, isNursingManager);
		}
	}

}
