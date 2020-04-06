package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPersonHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CheckAttdErrorAfterStampService.Require;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
/**
 * 
 * @author tutk
 *
 */
@RunWith(JMockit.class)
public class CheckAttdErrorAfterStampServiceTest {
	
	@Injectable
	private Require require;

	/**
	 * [prv-5] エラー確認する必要があるか
	 * check : needCheckError == true
	 * 
	 * condition : 
	 * 		require.getStampSetPer().isPresent() == true
	 * 		getButtonSet(pageNo,buttonDisNo).isPresent() == true
	 * 		getChangeClockArt().checkWorkingOut() == true
	 * 
	 */
	@Test
	public void testCheckAttdErrorAfterStampService_1() {
		String employeeId = "employeeId";//dummy
		int pageNo = 1;
		int buttonDisNo = 1;
		new Expectations() {
			{
				require.getStampSetPer();
				result = Optional.of(StampSettingPersonHelper.DUMMY);
			}
		};
		
		assertThat(CheckAttdErrorAfterStampService.get(require, employeeId, pageNo, buttonDisNo).isEmpty()).isTrue();
	}
	
	/**
	 * [prv-5] エラー確認する必要があるか
	 * check : needCheckError == false
	 * [prv-1] 申請促す設定を取得する
	 * require.getStampSet().isPresent() == false
	 */
	@Test
	public void testCheckAttdErrorAfterStampService_2() {
		String employeeId = "employeeId";
		int pageNo = 1;
		int buttonDisNo = 1;
		new Expectations() {
			{
				require.getStampSetPer();
				
				require.getStampSet();
				
			}
		};
		
		assertThat(CheckAttdErrorAfterStampService.get(require, employeeId, pageNo, buttonDisNo).isEmpty()).isTrue();
	}
	
	/**
	 * [prv-5] エラー確認する必要があるか
	 * check : needCheckError == false
	 * [prv-1] 申請促す設定を取得する
	 * require.getStampSet().isPresent() == true
	 * [prv-2] エラーを確認対象期間を取得する
	 * getErrorCheckPeriod return option.empty()
	 */
	@Test
	public void testCheckAttdErrorAfterStampService_3() {
		String employeeId = "employeeId";
		int pageNo = 1;
		int buttonDisNo = 1;
		GeneralDate baseDate = GeneralDate.today();
		new Expectations() {
			{
				require.getStampSetPer();
				
				require.getStampSet();
				result = Optional.of(DomainServiceHeplper.getStamPromptApplication());
				
				require.findClosurePeriod(employeeId, baseDate);
				result = new DatePeriod(GeneralDate.today().addMonths(-3), GeneralDate.today().addMonths(-3));
				
				require.getClosurePeriod( employeeId, baseDate);
			}
		};
		
		assertThat(CheckAttdErrorAfterStampService.get(require, employeeId, pageNo, buttonDisNo).isEmpty()).isTrue();
	}
	
	/**
	 * [prv-5] エラー確認する必要があるか
	 * check : needCheckError == false
	 * [prv-1] 申請促す設定を取得する
	 * require.getStampSet().isPresent() == true
	 * [prv-2] エラーを確認対象期間を取得する
	 * getErrorCheckPeriod return not option.empty()
	 * [prv-3] 指定期間の日別実績エラーを取得する
	 * [prv-4] 日別勤怠エラー情報を作成する
	 * createDailyErrorInfo return not empty
	 */
	@Test
	public void testCheckAttdErrorAfterStampService_4() {
		String employeeId = "employeeId";
		int pageNo = 1;
		int buttonDisNo = 1;
		GeneralDate baseDate = GeneralDate.today();
		DatePeriod period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		new Expectations() {
			{
				require.getStampSetPer();
				
				require.getStampSet();
				result = Optional.of(DomainServiceHeplper.getStamPromptApplication());
				
				require.findClosurePeriod(employeeId, baseDate);
				result = new DatePeriod(GeneralDate.today().addMonths(-3), GeneralDate.today().addMonths(-3));
				
				require.getClosurePeriod( employeeId, baseDate);
				result = Optional.of(new ClosurePeriod());
				
				require.findByPeriodOrderByYmd(employeeId, period);
				result = Arrays.asList(DomainServiceHeplper.getEmployeeDailyPerErrorDefault());
			}
		};
		
		assertThat(CheckAttdErrorAfterStampService.get(require, employeeId, pageNo, buttonDisNo).isEmpty()).isTrue();
	}
	
	/**
	 * [prv-5] エラー確認する必要があるか
	 * check : needCheckError == false
	 * [prv-1] 申請促す設定を取得する
	 * require.getStampSet().isPresent() == true
	 * [prv-2] エラーを確認対象期間を取得する
	 * getErrorCheckPeriod return not option.empty()
	 * [prv-3] 指定期間の日別実績エラーを取得する
	 * [prv-4] 日別勤怠エラー情報を作成する
	 * createDailyErrorInfo return not empty
	 */
	@Test
	public void testCheckAttdErrorAfterStampService_5() {
		String employeeId = "employeeId";
		int pageNo = 1;
		int buttonDisNo = 1;
		GeneralDate baseDate = GeneralDate.today();
		DatePeriod period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		new Expectations() {
			{
				require.getStampSetPer();
				
				require.getStampSet();
				result = Optional.of(DomainServiceHeplper.getStamPromptApplication());
				
				require.findClosurePeriod(employeeId, baseDate);
				result = new DatePeriod(GeneralDate.today(), GeneralDate.today());
				
				require.findByPeriodOrderByYmd(employeeId, period);
				result = DomainServiceHeplper.getListEmployeeDailyPerErrorDefault();
			}
		};
		
		assertThat(CheckAttdErrorAfterStampService.get(require, employeeId, pageNo, buttonDisNo).isEmpty()).isFalse();
	}
	
	/**
	 * [prv-5] エラー確認する必要があるか
	 * check : needCheckError == false
	 * [prv-1] 申請促す設定を取得する
	 * require.getStampSet().isPresent() == true
	 * [prv-2] エラーを確認対象期間を取得する
	 * getErrorCheckPeriod return not option.empty()
	 * [prv-3] 指定期間の日別実績エラーを取得する
	 * [prv-4] 日別勤怠エラー情報を作成する
	 * createDailyErrorInfo return empty
	 */
	@Test
	public void testCheckAttdErrorAfterStampService_6() {
		String employeeId = "employeeId";
		int pageNo = 1;
		int buttonDisNo = 1;
		GeneralDate baseDate = GeneralDate.today();
		DatePeriod period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		new Expectations() {
			{
				require.getStampSetPer();
				
				require.getStampSet();
				result = Optional.of(DomainServiceHeplper.getStamPromptApplication());
				
				require.findClosurePeriod(employeeId, baseDate);
				result = new DatePeriod(GeneralDate.today(), GeneralDate.today());
				
				require.findByPeriodOrderByYmd(employeeId, period);
				result = Arrays.asList(DomainServiceHeplper.getEmployeeDailyPerErrorDefault());
			}
		};
		
		assertThat(CheckAttdErrorAfterStampService.get(require, employeeId, pageNo, buttonDisNo).isEmpty()).isTrue();
	}
	
	/**
	 * [prv-5] エラー確認する必要があるか
	 * check : needCheckError == false
	 * [prv-1] 申請促す設定を取得する
	 * require.getStampSet().isPresent() == true
	 * [prv-2] エラーを確認対象期間を取得する
	 * getErrorCheckPeriod return not option.empty()
	 * [prv-3] 指定期間の日別実績エラーを取得する
	 * [prv-4] 日別勤怠エラー情報を作成する
	 * createDailyErrorInfo return empty
	 * require.getAllErAlAppByEralCode isPresent() == true
	 */
	@Test
	public void testCheckAttdErrorAfterStampService_7() {
		String employeeId = "employeeId";
		int pageNo = 1;
		int buttonDisNo = 1;
		GeneralDate baseDate = GeneralDate.today();
		DatePeriod period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		new Expectations() {
			{
				require.getStampSetPer();
				
				require.getStampSet();
				result = Optional.of(DomainServiceHeplper.getStamPromptApplication());
				
				require.findClosurePeriod(employeeId, baseDate);
				result = new DatePeriod(GeneralDate.today(), GeneralDate.today());
				
				require.findByPeriodOrderByYmd(employeeId, period);
				result = DomainServiceHeplper.getListEmployeeDailyPerErrorDefault();
				
				require.getAllErAlAppByEralCode(anyString);
				result = Optional.of(DomainServiceHeplper.getErAlApplicationDefault()) ;
				
			}
		};
		
		assertThat(CheckAttdErrorAfterStampService.get(require, employeeId, pageNo, buttonDisNo).isEmpty()).isFalse();
	}

}
