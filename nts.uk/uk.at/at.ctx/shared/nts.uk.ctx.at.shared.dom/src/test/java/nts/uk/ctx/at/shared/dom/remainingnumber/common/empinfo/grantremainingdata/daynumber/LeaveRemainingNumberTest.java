package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;

public class LeaveRemainingNumberTest {

	@Injectable
	private LeaveRemainingNumber.RequireM3 require;
	
	/**
	 * [1] 積み崩すかテスト
	 * 積み崩しができることを確認
	 */
	@Test
	public void needStackingTest1() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,60);
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		val expect = remainingNumber.needStacking(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect).isEqualTo(true);
	}
	/**
	 * [1] 積み崩すかテスト
	 * 残日数が0日で積み崩しができないことを確認
	 */
	@Test
	public void needStackingTest2() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(0.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,60);
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		val expect = remainingNumber.needStacking(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect).isEqualTo(false);
	}
	/**
	 * [1] 積み崩すかテスト
	 * 残時間だけで消化可能のため積み崩しが不要であることを確認
	 */
	@Test
	public void needStackingTest3() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(0.0, 60);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,60);
		

		
		val expect = remainingNumber.needStacking(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect).isEqualTo(false);
	}
	/**
	 * [1] 積み崩すかテスト
	 * 日数消化のため積み崩しが不要なことを確認
	 */
	@Test
	public void needStackingTest4() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(1.0,0);

		
		val expect = remainingNumber.needStacking(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect).isEqualTo(false);
	}
	
	/**
	 * [2] 消化しきれるか
	 * 残日数が2日、消化日数が1日で消化可能であることを確認
	 */
	@Test
	public void canDigestTest1() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(1.0,0);
		
		
		val expect = remainingNumber.canDigest(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect).isEqualTo(true);
	}
	/**
	 * [2] 消化しきれるか
	 * 残日数が2日、消化時間が1時間で消化可能であることを確認
	 */
	@Test
	public void canDigestTest2() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,60);
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		val expect = remainingNumber.canDigest(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect).isEqualTo(true);
	}
	
	/**
	 * [2] 消化しきれるか
	 * 残時間が1時間、消化時間が1時間で消化可能であることを確認
	 */
	@Test
	public void canDigestTest3() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(0.0, 60);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,60);
		

		val expect = remainingNumber.canDigest(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect).isEqualTo(true);
	}
	
	/**
	 * [2] 消化しきれるか
	 * 残時間が1時間、消化日数が1日で消化できないことを確認
	 */
	@Test
	public void canDigestTest4() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(0.0, 60);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(1.0,0);
		
		val expect = remainingNumber.canDigest(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect).isEqualTo(false);
	}
	/**
	 * [2] 消化しきれるか
	 * 残時間が1時間、消化時間が2時間で消化できないことを確認
	 */
	@Test
	public void canDigestTest5() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(0.0, 60);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,120);
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		val expect = remainingNumber.canDigest(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect).isEqualTo(false);
	}
	
	/**
	 * [3] 消化する
	 * 残日数が2日、消化日数が1日で残数が1日になることを確認
	 */
	@Test
	public void digestTest1() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(1.0,0);
		
		LeaveRemainingNumber expected = new LeaveRemainingNumber(1.0, 0);
		
		val expect = remainingNumber.digest(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect.days).isEqualTo(expected.days); 
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [3] 消化する
	 * 残日数が2日と2時間、消化時間が5時間で残数が1日と5時間になることを確認
	 */
	@Test
	public void digestTest2() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 120);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,300);
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		LeaveRemainingNumber expected = new LeaveRemainingNumber(1.0, 300);
		
		val expect = remainingNumber.digest(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect.days).isEqualTo(expected.days); 
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [3] 消化する
	 * 残日数が0.5日と2時間、消化時間が5時間で,
	 * 消化できず残数が0日と0時間になることを確認
	 */
	@Test
	public void digestTest3() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(0.5, 120);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,300);
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		LeaveRemainingNumber expected = new LeaveRemainingNumber(0.0, 0);
		
		val expect = remainingNumber.digest(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect.days).isEqualTo(expected.days); 
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [3] 消化する
	 * 残日数が2日、消化時間が12時間で,
	 * 消化できず残数が0日と0時間になることを確認
	 */
	@Test
	public void digestTest4() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,720);
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		LeaveRemainingNumber expected = new LeaveRemainingNumber(0.0, 0);
		
		val expect = remainingNumber.digest(require, companyId, employeeId, baseDate, usedNumber);
		assertThat(expect.days).isEqualTo(expected.days); 
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [4] 消化できた使用数を取得する
	 * 残日数が1日、消化日数が1日で、
	 * 消化できた使用数が1日であることを確認
	 */
	@Test
	public void digestUsedNumberTest1() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);
		LeaveRemainingNumber digest = new LeaveRemainingNumber(1.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(1.0,0);
		

		LeaveUsedNumber expected = new LeaveUsedNumber(1.0, null);
		
		LeaveUsedNumber expect =  remainingNumber.digestUsedNumber(require, usedNumber, digest, companyId, employeeId, baseDate);
		assertThat(expect.days).isEqualTo(expected.days); 
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [4] 消化できた使用数を取得する
	 * 残日数が2日と2時間、消化時間が5時間で、
	 * 消化できた使用数が5時間であることを確認
	 */
	@Test
	public void digestUsedNumberTest2() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 120);
		LeaveRemainingNumber digest = new LeaveRemainingNumber(1.0, 300);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,300);
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		LeaveUsedNumber expected= new LeaveUsedNumber(0.0, 300);
		
		LeaveUsedNumber expect =  remainingNumber.digestUsedNumber(require, usedNumber, digest, companyId, employeeId, baseDate);
		assertThat(expect.days).isEqualTo(expected.days); 
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	/**
	 * [4] 消化できた使用数を取得する
	 * 残日数が0.5日と2時間、消化時間が5時間で、
	 * 消化できた使用数が2時間であることを確認
	 */
	@Test
	public void digestUsedNumberTest3() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(0.5, 120);
		LeaveRemainingNumber digest = new LeaveRemainingNumber(0.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,300);
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		LeaveUsedNumber expected= new LeaveUsedNumber(0.0, 120);
		
		LeaveUsedNumber expect =  remainingNumber.digestUsedNumber(require, usedNumber, digest, companyId, employeeId, baseDate);
		assertThat(expect.days).isEqualTo(expected.days); 
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [4] 消化できた使用数を取得する
	 * 残日数が2日、消化時間が12時間で、1日のみ積み崩しを行い
	 * 消化できた使用数が8時間であることを確認
	 */
	@Test
	public void digestUsedNumberTest4() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);
		LeaveRemainingNumber digest = new LeaveRemainingNumber(0.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,720);
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		LeaveUsedNumber expected= new LeaveUsedNumber(0.0, 480);
		
		LeaveUsedNumber expect =  remainingNumber.digestUsedNumber(require, usedNumber, digest, companyId, employeeId, baseDate);
		assertThat(expect.days).isEqualTo(expected.days); 
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [5] 消化できなかった使用数を求める
	 * 残日数が2日、消化日数が1日で、消化できなかった使用数が0であることを確認
	 */
	@Test
	public void findUsedNumberThatCouldNotDigestedTest1() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);
		LeaveRemainingNumber digest = new LeaveRemainingNumber(1.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(1.0,0);
		

		LeaveUsedNumber expected = new LeaveUsedNumber(0.0, 0);
		
		LeaveUsedNumber expect =  remainingNumber.calculateForUnDigestedNumber(require, usedNumber, digest, companyId, employeeId, baseDate);
		assertThat(expect.days).isEqualTo(expected.days); 
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [5] 消化できなかった使用数を求める
	 * 残日数が2日と2時間、消化時間が5時間で、
	 * 消化できなかった使用数が0であることを確認
	 */
	@Test
	public void findUsedNumberThatCouldNotDigestedTest2() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 120);
		LeaveRemainingNumber digest = new LeaveRemainingNumber(1.0, 300);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,300);
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		LeaveUsedNumber expected= new LeaveUsedNumber(0.0, 0);
		
		LeaveUsedNumber expect =  remainingNumber.calculateForUnDigestedNumber(require, usedNumber, digest, companyId, employeeId, baseDate);
		assertThat(expect.days).isEqualTo(expected.days); 
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [5] 消化できなかった使用数を求める
	 * 残日数が0.5日と2時間、消化時間が5時間で、
	 * 消化できなかった使用数が3時間であることを確認
	 */
	@Test
	public void findUsedNumberThatCouldNotDigestedTest3() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(0.5, 120);
		LeaveRemainingNumber digest = new LeaveRemainingNumber(0.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,300);
		
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		LeaveUsedNumber expected= new LeaveUsedNumber(0.0, 180);
		
		LeaveUsedNumber expect =  remainingNumber.calculateForUnDigestedNumber(require, usedNumber, digest, companyId, employeeId, baseDate);
		assertThat(expect.days).isEqualTo(expected.days); 
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [5] 消化できなかった使用数を求める
	 * 残日数が2日、消化時間が12時間で、1日のみ積み崩しが行われて
	 * 消化できなかった使用数が4時間であることを確認
	 */
	@Test
	public void findUsedNumberThatCouldNotDigestedTest4() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);
		LeaveRemainingNumber digest = new LeaveRemainingNumber(0.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,720);
		
		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		LeaveUsedNumber expected= new LeaveUsedNumber(0.0, 240);
		
		LeaveUsedNumber expect =  remainingNumber.calculateForUnDigestedNumber(require, usedNumber, digest, companyId, employeeId, baseDate);
		assertThat(expect.days).isEqualTo(expected.days); 
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	
	/**
	 * [prv-1] 消化する
	 * 残日数が2日、消化日数が1日で、消化後残数が1日であることを確認
	 */
	@Test
	public void digestPrvTest1() {
		
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(1.0,0);
		
		LeaveRemainingNumber expected= new LeaveRemainingNumber(1.0, 0);
		
		LeaveRemainingNumber expect = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(remainingNumber,"digest",usedNumber);
		assertThat(expect.days).isEqualTo(expected.days);
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [prv-1] 消化する
	 * 残日数が2日と8時間、消化時間が2時間で、消化後残数が2日と6時間であることを確認
	 */
	@Test
	public void digestPrvTest2() {
		
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 480);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0,120);
		
		LeaveRemainingNumber expected= new LeaveRemainingNumber(2.0, 360);
		
		LeaveRemainingNumber expect = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(remainingNumber,"digest",usedNumber);
		assertThat(expect.days).isEqualTo(expected.days);
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [prv-1] 消化する
	 * 残日数が0.5日と残時間8時間、消化日数が1日で、日数が足りず、残数0日0時間になること確認
	 */
	@Test
	public void digestPrvTest3() {
		
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(0.5, 480);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(1.0, 0);
		
		LeaveRemainingNumber expected= new LeaveRemainingNumber(0.0, 0);
		
		LeaveRemainingNumber expect = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(remainingNumber,"digestNotMinus",usedNumber);
		assertThat(expect.days).isEqualTo(expected.days);
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [prv-1] 消化する
	 * 残日数が2日と残時間2時間、消化時間が6時間で、時間が足りず、残数0日0時間になること確認
	 */
	@Test
	public void digestPrvTest4() {
		
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 120);
		LeaveUsedNumber usedNumber = new LeaveUsedNumber(0.0, 360);
		
		LeaveRemainingNumber expected= new LeaveRemainingNumber(0.0, 0);
		
		LeaveRemainingNumber expect = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(remainingNumber,"digestNotMinus",usedNumber);
		assertThat(expect.days).isEqualTo(expected.days);
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	
	/**
	 * [prv-2] 積み崩しを行うテスト
	 * 積み崩しされ日数が-1日時間が+8時間されることを確認
	 */
	@Test
	public void calcStackPrvTest1() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);

		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = annualPaidLeaveSetting(companyId);
			}
		};
		
		LeaveRemainingNumber expected= new LeaveRemainingNumber(1.0, 480);
		
		LeaveRemainingNumber expect = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(remainingNumber,"calcStack",
				require, companyId, employeeId, baseDate);
		assertThat(expect.days).isEqualTo(expected.days);
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	/**
	 * [prv-2] 積み崩しを行うテスト
	 * 契約時間が取得できず、積み崩しされないことを確認
	 */
	@Test
	public void calcStackTest2() {
		
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate baseDate = GeneralDate.ymd(2022, 4, 1);
		LeaveRemainingNumber remainingNumber = new LeaveRemainingNumber(2.0, 0);

		new Expectations() {
			{
				require.annualPaidLeaveSetting(companyId);
				result = null;
			}
		};
		
		LeaveRemainingNumber expected= new LeaveRemainingNumber(2.0, 0);
		
		LeaveRemainingNumber expect = nts.arc.testing.assertion.NtsAssert.Invoke.privateMethod(remainingNumber,"calcStack",
				require, companyId, employeeId, baseDate);
		assertThat(expect.days).isEqualTo(expected.days);
		assertThat(expect.minutes).isEqualTo(expected.minutes);
	}
	
	private AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId){
		return AnnualPaidLeaveSettingHelper.annualPaidLeaveSetting(companyId);
	}

}
