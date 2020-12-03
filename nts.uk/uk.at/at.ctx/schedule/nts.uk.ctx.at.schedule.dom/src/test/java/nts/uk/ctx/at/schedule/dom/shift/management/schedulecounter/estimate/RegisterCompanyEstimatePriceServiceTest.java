package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class RegisterCompanyEstimatePriceServiceTest {
	@Injectable
	private RegisterCompanyEstimateAmountService.Require require;
	
	@Mocked 
	private EstimateAmountDetail detail;
	
	@Mocked 
	private HandingOfEstimateAmount heAmount;
	
	/**
	 * ケース: 会社の目安金額: 存在しない, 目安金額の扱い: 存在しない
	 * 期待: 会社の目安金額: 新規, 目安金額の扱い: 新規
	 */
	@Test
	public void insert() {
		
		new Expectations() {
			{
				require.getCompanyEstimateAmount();
				
				require.getHandingOfEstimateAmount();
			}
		};
		
		val ceAmount = new CompanyEstimateAmount(detail);
		
		NtsAssert.atomTask(
				
				() -> RegisterCompanyEstimateAmountService.regiter(require, detail, heAmount),
				
				any -> require.insertCompanyEstimateAmount(ceAmount),
				
				any -> require.insertHandingOfEstimateAmount(heAmount)
				);
	}
	
	/**
	 * ケース: 会社の目安金額: 存在する, 目安金額の扱い: 存在する
	 * 期待: 会社の目安金額: 変更, 目安金額の扱い: 変更
	 */
	@Test
	public void update(@Mocked CompanyEstimateAmount ceAmountExist) {
		
		new Expectations() {
			{
				require.getCompanyEstimateAmount();
				result = Optional.of(ceAmountExist);	
						
				require.getHandingOfEstimateAmount();
				result = Optional.of(heAmount);
			}
		};
		
		ceAmountExist.update(detail);
		
		NtsAssert.atomTask(
				
				() -> RegisterCompanyEstimateAmountService.regiter(require, detail, heAmount),
				
				any -> require.updateCompanyEstimateAmount(ceAmountExist),
				
				any -> require.updateHandingOfEstimateAmount(heAmount)
				);
	}
	
	/**
	 * ケース: 会社の目安金額: 存在しない, 目安金額の扱い: 存在する
	 * 期待: 会社の目安金額: 新規, 目安金額の扱い: 変更
	 */
	@Test
	public void insert_companyEstimateAmount() {
		
		new Expectations() {
			{
				require.getCompanyEstimateAmount();
						
				require.getHandingOfEstimateAmount();
				result = Optional.of(heAmount);
			}
		};
		
		val ceAmount = new CompanyEstimateAmount(detail);
		
		NtsAssert.atomTask(
				
				() -> RegisterCompanyEstimateAmountService.regiter(require, detail, heAmount),
				
				any -> require.insertCompanyEstimateAmount(ceAmount),
				
				any -> require.updateHandingOfEstimateAmount(heAmount)
				);
	}
	
	
}
