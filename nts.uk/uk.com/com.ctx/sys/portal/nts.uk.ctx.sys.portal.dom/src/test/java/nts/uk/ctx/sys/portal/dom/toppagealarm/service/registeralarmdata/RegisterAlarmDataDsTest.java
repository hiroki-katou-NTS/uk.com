package nts.uk.ctx.sys.portal.dom.toppagealarm.service.registeralarmdata;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.AtomTaskAssert;
import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmClassification;
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayAtr;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmData;
import nts.uk.ctx.sys.portal.dom.toppagealarm.service.registeralarmdata.RegisterAlarmDataDs.RegisterAlarmDataRequire;
import nts.uk.shr.com.i18n.TextResource;

@RunWith(JMockit.class)
public class RegisterAlarmDataDsTest {

	@Injectable
	private RegisterAlarmDataRequire require;
	
	@Mocked 
	private TextResource tr;
	
	/**
	 * $トップアラーム.isPresent()
	 * $トップアラーム.既読日時　EMPTY
	 */
	@Test
	public void RegisterAlarmDataDsTest1() {
		
		//given
		ToppageAlarmParam param = RegisterAlarmDataDsHelper.mockParamHealthLife();
		String cid = "cid";
		String sid = "sid";
		String patternCode = "patternCode";
		DisplayAtr displayAtr = param.getDisplayEmpClassfication();
		AlarmClassification alarmCls = param.getAlarmClassification();
		
		//[R-1]
		Optional<ToppageAlarmData> checkDomain = RegisterAlarmDataDsHelper.mockToppageAlarmDataReadDateEmpty();
		
		new Expectations() {
			{
				require.get(cid, sid, displayAtr.value, alarmCls.value, Optional.of(patternCode), Optional.empty());
				result = checkDomain;
			}
		};
		
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> RegisterAlarmDataDs.register(require, cid, param));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * $トップアラーム.isPresent()
	 * $トップアラーム.既読日時　>　トップアラームパラメータ.発生日時
	 */
	@Test
	public void RegisterAlarmDataDsTest2() {
		
		//given
		ToppageAlarmParam param = RegisterAlarmDataDsHelper.mockParamHealthLife();
		String cid = "cid";
		String sid = "sid";
		String patternCode = "patternCode";
		DisplayAtr displayAtr = param.getDisplayEmpClassfication();
		AlarmClassification alarmCls = param.getAlarmClassification();
		
		//[R-1]
		Optional<ToppageAlarmData> checkDomain = RegisterAlarmDataDsHelper.mockToppageAlarmDataReadDateBefore();
		
		new Expectations() {
			{
				require.get(cid, sid, displayAtr.value, alarmCls.value, Optional.of(patternCode), Optional.empty());
				result = checkDomain;
			}
		};
		
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> RegisterAlarmDataDs.register(require, cid, param));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * $トップアラーム.isPresent()
	 * $トップアラーム.既読日時　<　トップアラームパラメータ.発生日時
	 */
	@Test
	public void RegisterAlarmDataDsTest3() {
		
		//given
		ToppageAlarmParam param = RegisterAlarmDataDsHelper.mockParamHealthLife();
		String cid = "cid";
		String sid = "sid";
		String patternCode = "patternCode";
		DisplayAtr displayAtr = param.getDisplayEmpClassfication();
		AlarmClassification alarmCls = param.getAlarmClassification();
		
		//[R-1]
		Optional<ToppageAlarmData> checkDomain = RegisterAlarmDataDsHelper.mockToppageAlarmDataReadDateAfter();
		
		new Expectations() {
			{
				require.get(cid, sid, displayAtr.value, alarmCls.value, Optional.of(patternCode), Optional.empty());
				result = checkDomain;
			}
		};
		
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> RegisterAlarmDataDs.register(require, cid, param));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * $トップアラーム.isNotPresent()
	 * アラーム分類 = ヘルス×ライフメッセージ
	 */
	@Test
	public void RegisterAlarmDataDsTest4() {
		
		//given
		ToppageAlarmParam param = RegisterAlarmDataDsHelper.mockParamHealthLife();
		String cid = "cid";
		
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> RegisterAlarmDataDs.register(require, cid, param));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * $トップアラーム.isNotPresent()
	 * アラーム分類 = アラームリスト
	 * 表示社員区分 = 本人
	 */
	@Test
	public void RegisterAlarmDataDsTest5() {
		
		//given
		ToppageAlarmParam param = RegisterAlarmDataDsHelper.mockParamAlarmListPrincipal();
		String cid = "cid";
		String url = "url";
		String patternName = param.getPatternName().orElse("");
		String kal001 = "KAL001";
		String b = "B";
		Integer kinjirou = 1; // 勤次郎
		Integer standard = 0; // 標準
		
		new Expectations() {
			{
				require.getUrl(cid, kinjirou, standard, kal001, b);
				result = Optional.of(url);
			}
			{
				TextResource.localize("KTG031_37", Arrays.asList(patternName));
				result = "KTG031_37";
			}
		};
		
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> RegisterAlarmDataDs.register(require, cid, param));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * $トップアラーム.isNotPresent()
	 * アラーム分類 = アラームリスト
	 * 表示社員区分 = 上長
	 */
	@Test
	public void RegisterAlarmDataDsTest6() {
		
		//given
		ToppageAlarmParam param = RegisterAlarmDataDsHelper.mockParamAlarmListBoss();
		String cid = "cid";
		String url = "url";
		String patternName = param.getPatternName().orElse("");
		String kal001 = "KAL001";
		String b = "B";
		Integer kinjirou = 1; // 勤次郎
		Integer standard = 0; // 標準
		
		new Expectations() {
			{
				require.getUrl(cid, kinjirou, standard, kal001, b);
				result = Optional.of(url);
			}
			{
				TextResource.localize("KTG031_38", Arrays.asList(patternName));
				result = "KTG031_38";
			}
		};
		
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> RegisterAlarmDataDs.register(require, cid, param));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * $トップアラーム.isNotPresent()
	 * アラーム分類 = 更新処理自動実行業務エラー
	 */
	@Test
	public void RegisterAlarmDataDsTest7() {
		
		//given
		ToppageAlarmParam param = RegisterAlarmDataDsHelper.mockParamAlarmListBusinessErr();
		String cid = "cid";
		String url = "url";
		String kbt002 = "KBT002";
		String f = "F";
		Integer kinjirou = 1; // 勤次郎
		Integer standard = 0; // 標準
		
		new Expectations() {
			{
				require.getUrl(cid, kinjirou, standard, kbt002, f);
				result = Optional.of(url);
			}
			{
				TextResource.localize("KTG031_39");
				result = "KTG031_39";
			}
		};
		
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> RegisterAlarmDataDs.register(require, cid, param));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
	
	/**
	 * $トップアラーム.isNotPresent()
	 * アラーム分類 = アラームリスト
	 * 表示社員区分 = 担当者
	 */
	@Test
	public void RegisterAlarmDataDsTest8() {
		
		//given
		ToppageAlarmParam param = RegisterAlarmDataDsHelper.mockParamAlarmListPic();
		String cid = "cid";
		String url = "url";
		String kal001 = "KAL001";
		String b = "B";
		Integer kinjirou = 1; // 勤次郎
		Integer standard = 0; // 標準
		
		new Expectations() {
			{
				require.getUrl(cid, kinjirou, standard, kal001, b);
				result = Optional.of(url);
			}
			{
				TextResource.localize("KTG031_40");
				result = "KTG031_40";
			}
		};
		
		//when
		Supplier<AtomTask> result = () -> AtomTask.of(() -> RegisterAlarmDataDs.register(require, cid, param));
		
		//then
		AtomTaskAssert.atomTask(result);
	}
}
