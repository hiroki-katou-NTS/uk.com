package nts.uk.ctx.at.request.dom.applicationreflect.algorithm.checkprocess;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.DetermineActualResultLockAdapter.LockStatus;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.DetermineActualResultLockAdapter.PerformanceType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class CheckProcessDuringLockTest {

	private String companyId;

	private Integer closureId;

	private GeneralDate dateRefer;

	@Injectable
	private CheckProcessDuringLock.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		companyId = "cid";
		closureId = 1;
		dateRefer = GeneralDate.ymd(2020, 05, 10);
	}

	/*
	 * テストしたい内容
	 * 
	 * →勤務予定反映処理できる
	 * 
	 * 準備するデータ
	 * 
	 * → ロック中の計算/集計できる
	 * 
	 * 実績ロックされて
	 * 
	 */
	@Test
	public void testReflectWhenlock() {

		NotUseAtr actualResult = CheckProcessDuringLock.checkProcess(require, companyId, closureId, true, dateRefer);

		assertThat(actualResult).isEqualTo(NotUseAtr.USE);
	}

	/*
	 * テストしたい内容
	 * 
	 * →勤務予定反映処理できる
	 * 
	 * 準備するデータ
	 * 
	 * →実績ロックされてわない
	 * 
	 */
	@Test
	public void testNoLockActual() {

		new Expectations() {
			{
				require.lockStatus(anyString, (GeneralDate)any, closureId, PerformanceType.DAILY);
				result = LockStatus.UNLOCK;
			}
		};
		NotUseAtr actualResult = CheckProcessDuringLock.checkProcess(require, companyId, closureId, false, dateRefer);

		assertThat(actualResult).isEqualTo(NotUseAtr.USE);
	}

	/*
	 * テストしたい内容
	 * 
	 * →勤務予定反映処理できない
	 * 
	 * 準備するデータ
	 * 
	 * →実績ロックされている
	 * 
	 */
	@Test
	public void testNoReflect() {

		new Expectations() {
			{
				require.lockStatus(anyString, (GeneralDate)any, closureId, PerformanceType.DAILY);
				result = LockStatus.LOCK;
			}
		};
		
		NotUseAtr actualResult = CheckProcessDuringLock.checkProcess(require, companyId, closureId, false, dateRefer);

		assertThat(actualResult).isEqualTo(NotUseAtr.NOT_USE);
	}

}
