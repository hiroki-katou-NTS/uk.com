package nts.uk.ctx.sys.auth.dom.wkpmanager;

import static mockit.Deencapsulation.invoke;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
@RunWith(JMockit.class)
public class RegisterWorkplaceManagerServiceTest {
	
	@Injectable
	private RegisterWorkplaceManagerService.Require require;

	/**
	 * 期間重複をチェックする
	 * case 1:
	 * 			<----------->  <--------------------->	職場管理者の履歴期間
	 *  	<------->									履歴期間
	 * input:
	 * 職場管理者の履歴期間 historyPeriods:[	2012/01/01 ~ 2018/12/31
	 * 										2019/01/01 ~ 2030/12/31	]
	 * 履歴期間 period：					「	2011/01/01 ~ 2015/12/31	」
	 * output： Msg_619
	 */
	@Test
	public void testCheckError_Msg_619_case_1() {
		String sid = "sid";
		val historyPeriods = Arrays.asList(
					new DatePeriod(GeneralDate.ymd(2015, 1, 1), GeneralDate.ymd(2018, 12, 31))
				,	new DatePeriod(GeneralDate.ymd(2019, 1, 1), GeneralDate.ymd(2030, 12, 31)));
		val registeredWorkplaceManagers = RegisterWorkplaceManagerServiceHelper.createWorkplaceManagers(sid, historyPeriods);
		val period = new DatePeriod(GeneralDate.ymd(2011, 1, 1), GeneralDate.ymd(2015, 12, 31));

		 NtsAssert.businessException("Msg_619", ()->{
				invoke(	RegisterWorkplaceManagerService.class 
					,	"checkError", require, sid, period, registeredWorkplaceManagers);
				});
	}	
	
	/**
	 * 期間重複をチェックする
	 * case 2:
	 * 			<-------------->			<--------------------->	職場管理者の履歴期間
	 *  					<------------------>					履歴期間
	 * input:
	 * 職場管理者の履歴期間 historyPeriods:[	2012/01/01 ~ 2018/12/31
	 * 										2019/01/01 ~ 2030/12/31	]
	 * 履歴期間 period：					「	2017/01/01 ~ 2021/12/31	」
	 * output： Msg_619
	 */
	@Test
	public void testCheckError_Msg_619_case_2() {
		String sid = "sid";
		val historyPeriods = Arrays.asList(
					new DatePeriod(GeneralDate.ymd(2015, 1, 1), GeneralDate.ymd(2018, 12, 31))
				,	new DatePeriod(GeneralDate.ymd(2019, 1, 1), GeneralDate.ymd(2030, 12, 31)));
		val registeredWorkplaceManagers = RegisterWorkplaceManagerServiceHelper.createWorkplaceManagers(sid, historyPeriods);
		val period = new DatePeriod(GeneralDate.ymd(2017, 1, 1), GeneralDate.ymd(2021, 12, 31));

		 NtsAssert.businessException("Msg_619", ()->{
				invoke(	RegisterWorkplaceManagerService.class 
					,	"checkError", require, sid, period, registeredWorkplaceManagers);
				});
	}

	/**
	 * 期間重複をチェックする
	 * case 3:
	 * 			<----------->		<--------------------->			職場管理者の履歴期間
	 *  												<------->	履歴期間
	 * input:
	 * 職場管理者の履歴期間 historyPeriods:[	2012/01/01 ~ 2018/12/31
	 * 										2019/01/01 ~ 2030/12/31	]
	 * 履歴期間 period：					「	2028/01/01 ~ 2031/12/31	」
	 * output： Msg_619
	 */
	@Test
	public void testCheckError_Msg_619_case_3() {
		String sid = "sid";
		val historyPeriods = Arrays.asList(
					new DatePeriod(GeneralDate.ymd(2015, 1, 1), GeneralDate.ymd(2018, 12, 31))
				,	new DatePeriod(GeneralDate.ymd(2019, 1, 1), GeneralDate.ymd(2030, 12, 31)));
		val registeredWorkplaceManagers = RegisterWorkplaceManagerServiceHelper.createWorkplaceManagers(sid, historyPeriods);
		val period = new DatePeriod(GeneralDate.ymd(2028, 1, 1), GeneralDate.ymd(2031, 12, 31));

		 NtsAssert.businessException("Msg_619", ()->{
				invoke(	RegisterWorkplaceManagerService.class 
					,	"checkError", require, sid, period, registeredWorkplaceManagers);
				});
	}
	
	/**
	 * 入社前・退職後をチェックする
	 * 職場管理者の履歴期間 registeredWorkplaceManagers: empty
	 * 社員の所属履歴 comHistories:[2015/01/01 ~ 2020/12/31]
	 */
	@Test
	public void testCheckError_Msg_2199() {
		val sid = "sid";
		val periods = Arrays.asList(new DatePeriod(GeneralDate.ymd(2015, 1, 1), GeneralDate.ymd(2020, 12, 31)));
		val comHistories = RegisterWorkplaceManagerServiceHelper.createEmpEnrollPeriodImports(sid, periods);
		
		new Expectations() {{
			require.getEmployeeCompanyHistory((String) any, (DatePeriod) any);
			result = comHistories;
			
		}};
		
		/**
		 * case1: Msg_2199
		 * 			<----------->	社員の所属履歴
		 * <--> 				 	追加、変更期間
		 * 	<------>
		 * 					<------>
		 *						<---->
		 *								<------>
		 */
		{
			val addPeriodErrors = Arrays.asList(	new DatePeriod(GeneralDate.ymd(2010, 1, 1), GeneralDate.ymd(2014, 12, 31))
												,	new DatePeriod(GeneralDate.ymd(2014, 1, 1), GeneralDate.ymd(2015, 1, 1))
												,	new DatePeriod(GeneralDate.ymd(2016, 1, 1), GeneralDate.ymd(2021, 1, 1))
												,	new DatePeriod(GeneralDate.ymd(2020, 12, 31), GeneralDate.ymd(2021, 12, 31))
												,	new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 12, 31))
												);
			
			addPeriodErrors.stream().forEach(addperiod ->{
				 NtsAssert.businessException("Msg_2199", ()->{
						invoke(	RegisterWorkplaceManagerService.class 
							,	"checkError", require, sid, addperiod, Collections.emptyList());
				 });
			});
			
		}
		
		/**
		 * case2: エラーがない
		 * 			<----------->	社員の所属履歴
		 * 			<--->			追加、変更期間
		 * 				<------->	
		 */
		
		{
			val addperiods = Arrays.asList(		new DatePeriod(GeneralDate.ymd(2015, 1, 1), GeneralDate.ymd(2016, 12, 31))
											,	new DatePeriod(GeneralDate.ymd(2016, 1, 1), GeneralDate.ymd(2020, 12, 31))
											);
			
			addperiods.stream().forEach(addperiod -> {
				NtsAssert.Invoke.staticMethod(	RegisterWorkplaceManagerService.class
											,	"checkError", require, sid, addperiod, Collections.emptyList());
			});
		}
	}

	/**
	 * 追加: susscess
	 */
	@Test
	public void testAdd() {
		val sid = "sid";
		val workplaceId = "workplaceId";
		
		val historyPeriods = Arrays.asList(
					new DatePeriod(GeneralDate.ymd(2015, 1, 1), GeneralDate.ymd(2018, 12, 31))
				,	new DatePeriod(GeneralDate.ymd(2019, 1, 1), GeneralDate.ymd(2022, 12, 31)));
		val comHistperiods = Arrays.asList(
				new DatePeriod(GeneralDate.ymd(2015, 1, 1), GeneralDate.ymd(2050, 12, 31)));
		
		val registeredWorkplaceManagers = RegisterWorkplaceManagerServiceHelper.createWorkplaceManagers(sid, historyPeriods);
		val comHistories = RegisterWorkplaceManagerServiceHelper.createEmpEnrollPeriodImports(sid, comHistperiods);
		val period = new DatePeriod(GeneralDate.ymd(2023, 1, 1), GeneralDate.ymd(2026, 12, 31));
		val workPlaceManager = WorkplaceManager.createNew(workplaceId, sid, period);
		
		new Expectations() {{
			require.getWorkplaceMangager((String) any, (String) any);
			result = registeredWorkplaceManagers;
			
			require.getEmployeeCompanyHistory((String) any, (DatePeriod) any);
			result = comHistories;
			
		}};
		
		NtsAssert.atomTask(() -> RegisterWorkplaceManagerService.add(require, workplaceId, sid, period)
				,	any -> require.insert(workPlaceManager));
	}
	
	/**
	 * 期間を変更する: Msg_619
	 * case1:
	 * 			<----------->		<---------------->	既に登録される職場管理者リスト: [wkplaceManagerId_1, wkplaceManagerId_2]
	 * 					<--------->						職場管理者.履歴期間のwkplaceManagerId_2
	 */	
	@Test
	public void testChangePeriod_case_1() {
		val sid = "sid";
		val wkplaceManagerId_1 = "wkplaceManagerId_1";
		val wkplaceManagerId_2 = "wkplaceManagerId_2";
		
		val registeredWorkplaceManagers = Arrays.asList(
						RegisterWorkplaceManagerServiceHelper.createWorkplaceManagerById(sid, wkplaceManagerId_1
								,	new DatePeriod(GeneralDate.ymd(2015, 1, 1), GeneralDate.ymd(2018, 12, 31)))
					,	RegisterWorkplaceManagerServiceHelper.createWorkplaceManagerById(sid, wkplaceManagerId_2
								,	new DatePeriod(GeneralDate.ymd(2020, 1, 1), GeneralDate.ymd(2022, 12, 31)))
					);
		
		val workPlaceManager = RegisterWorkplaceManagerServiceHelper.createWorkplaceManagerById(sid, wkplaceManagerId_2
								,	new DatePeriod(GeneralDate.ymd(2016, 1, 1), GeneralDate.ymd(2019, 06, 30)));
		
		new Expectations() {{
			require.getWorkplaceMangager((String) any, (String) any);
			result = registeredWorkplaceManagers;
			
		}};

		 NtsAssert.businessException("Msg_619"
				 , ()->{RegisterWorkplaceManagerService.changePeriod(require, workPlaceManager);
		 });
	}

	/**
	 * 期間を変更する: susscess
	 * case2:
	 * 			<----------->		既に登録される職場管理者リスト
	 * 					<-------->	職場管理者.履歴期間
	 * 
	 * 社員の所属履歴 comHistories:	[2015/01/01 ~ 2050/12/31]
	 * 
	 */	
	@Test
	public void testChangePeriod_case_2() {
		val sid = "sid";
		val wkplaceManagerId_1 = "wkplaceManagerId_1";
		val comHistperiods = Arrays.asList(new DatePeriod(GeneralDate.ymd(2015, 1, 1), GeneralDate.ymd(2050, 12, 31)));
		
		val registeredWorkplaceManagers = Arrays.asList(
						RegisterWorkplaceManagerServiceHelper.createWorkplaceManagerById(sid, wkplaceManagerId_1
								,	new DatePeriod(GeneralDate.ymd(2015, 1, 1), GeneralDate.ymd(2018, 12, 31)))
						);
				
		val comHistories = RegisterWorkplaceManagerServiceHelper.createEmpEnrollPeriodImports(sid, comHistperiods);
		
		val workPlaceManager = RegisterWorkplaceManagerServiceHelper.createWorkplaceManagerById(sid, wkplaceManagerId_1
								,	new DatePeriod(GeneralDate.ymd(2016, 1, 1), GeneralDate.ymd(2026, 12, 31)));
		
		new Expectations() {{
			require.getWorkplaceMangager((String) any, (String) any);
			result = registeredWorkplaceManagers;
			
			require.getEmployeeCompanyHistory((String) any, (DatePeriod) any);
			result = comHistories;
			
		}};
		
		NtsAssert.atomTask(() -> RegisterWorkplaceManagerService.changePeriod(require, workPlaceManager)
				,	any -> require.update(workPlaceManager));
		
	}
	
}
