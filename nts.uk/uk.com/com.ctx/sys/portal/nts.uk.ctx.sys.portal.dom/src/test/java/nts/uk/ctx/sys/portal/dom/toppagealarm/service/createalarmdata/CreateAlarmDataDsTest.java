package nts.uk.ctx.sys.portal.dom.toppagealarm.service.createalarmdata;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.AtomTaskAssert;
import nts.uk.ctx.sys.portal.dom.toppagealarm.service.registeralarmdata.ToppageAlarmParam;
import nts.uk.ctx.sys.portal.dom.toppagealarm.service.registeralarmdata.RegisterAlarmDataDs.RegisterAlarmDataRequire;
import nts.uk.ctx.sys.portal.dom.toppagealarm.service.updatealarmdata.UpdateAlarmDataDs.UpdateAlarmDataRequire;
import nts.uk.ctx.sys.portal.dom.toppagealarm.service.updateautorunalarm.UpdateAutoRunAlarmDs.UpdateAutoRunAlarmRequire;
import nts.uk.shr.com.i18n.TextResource;

@RunWith(JMockit.class)
public class CreateAlarmDataDsTest {

	@Injectable
	private UpdateAutoRunAlarmRequire rq1;

	@Injectable
	private UpdateAlarmDataRequire rq2;

	@Injectable
	private RegisterAlarmDataRequire rq3;
	
	@Mocked 
	private TextResource tr;

	/*
	 * Optional<削除の情報> NOT EMPTY
	 * アラーム分類 = 更新処理自動実行業務エラー
	 */
	@Test
	public void CreateAlarmDataDsTest1() {
		// give
		List<ToppageAlarmParam> alarmInfos = CreateAlarmDataDsHelper.mockToppageAlarmParams();
		Optional<DeleleteInfo> delInfo = CreateAlarmDataDsHelper.mockDeleleteInfoBusinessErr();

		// when
		Supplier<AtomTask> result = () -> AtomTask
				.of(() -> CreateAlarmDataDs.create(rq1, rq2, rq3, "cid", alarmInfos, delInfo));

		// then
		AtomTaskAssert.atomTask(result);
	}
	
	/*
	 * Optional<削除の情報> NOT EMPTY
	 * アラーム分類 = 更新処理自動実行動作異常
	 */
	@Test
	public void CreateAlarmDataDsTest2() {
		// give
		List<ToppageAlarmParam> alarmInfos = CreateAlarmDataDsHelper.mockToppageAlarmParams();
		Optional<DeleleteInfo> delInfo = CreateAlarmDataDsHelper.mockDeleleteInfoOperationErr();

		// when
		Supplier<AtomTask> result = () -> AtomTask
				.of(() -> CreateAlarmDataDs.create(rq1, rq2, rq3, "cid", alarmInfos, delInfo));

		// then
		AtomTaskAssert.atomTask(result);
	}
	
	/*
	 * Optional<削除の情報> NOT EMPTY
	 * アラーム分類 = アラームリスト
	 */
	@Test
	public void CreateAlarmDataDsTest3() {
		// give
		List<ToppageAlarmParam> alarmInfos = CreateAlarmDataDsHelper.mockToppageAlarmParams();
		Optional<DeleleteInfo> delInfo = CreateAlarmDataDsHelper.mockDeleleteInfoAlarmList();

		// when
		Supplier<AtomTask> result = () -> AtomTask
				.of(() -> CreateAlarmDataDs.create(rq1, rq2, rq3, "cid", alarmInfos, delInfo));

		// then
		AtomTaskAssert.atomTask(result);
	}
	
	/*
	 * Optional<削除の情報>  EMPTY
	 */
	@Test
	public void CreateAlarmDataDsTest4() {
		// give
		List<ToppageAlarmParam> alarmInfos = CreateAlarmDataDsHelper.mockToppageAlarmParams();
		Optional<DeleleteInfo> delInfo = Optional.empty();

		// when
		Supplier<AtomTask> result = () -> AtomTask
				.of(() -> CreateAlarmDataDs.create(rq1, rq2, rq3, "cid", alarmInfos, delInfo));

		// then
		AtomTaskAssert.atomTask(result);
	}
}
