package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import java.util.Optional;

import nts.arc.task.tran.AtomTask;

/**
 * 会社の目安を登録する	
 * @author lan_lt
 *
 */
public class RegisterCompanyEstimatePriceService {

	/**
	 * 登録する
	 * @param require
	 * @param detail 目安金額詳細
	 * @param heAmount 目安金額の扱い
	 * @return
	 */
	public static AtomTask regiter(Require require, EstimateAmountDetail detail, HandingOfEstimateAmount heAmount) {
		Optional<CompanyEstimateAmount> ceAmountOpt = require.getCompanyEstimateAmount();
		Optional<HandingOfEstimateAmount> heAmountExist = require.getHandingOfEstimateAmount();
		
		return AtomTask.of(() -> {
			if(ceAmountOpt.isPresent()) {
				ceAmountOpt.get().update(detail);
				require.updateCompanyEstimateAmount(ceAmountOpt.get());
			}else {
				require.insertCompanyEstimateAmount(new CompanyEstimateAmount(detail));
			}
			
			if(heAmountExist.isPresent()) {
				require.updateHandingOfEstimateAmount(heAmount);
			}else {
				require.insertHandingOfEstimateAmount(heAmount);
			}
			
		});
	}
	
	public static interface Require{
		/** [R-1]  会社の目安金額を取得する  */
		Optional<CompanyEstimateAmount> getCompanyEstimateAmount();
		
		/** [R-2] 会社の目安金額を新規する */
		void insertCompanyEstimateAmount(CompanyEstimateAmount ceAmount);
		
		/**	[R-3] 会社の目安金額を変更する*/
		void updateCompanyEstimateAmount(CompanyEstimateAmount ceAmount);
		
		/**　[R-4]  目安金額の扱いを取得する*/
		Optional<HandingOfEstimateAmount> getHandingOfEstimateAmount();
	
		/** [R-5] 目安金額の扱いを新規する */
		void insertHandingOfEstimateAmount(HandingOfEstimateAmount heAmount);
		
		/** [R-6] 目安金額の扱いを変更する*/
		void updateHandingOfEstimateAmount(HandingOfEstimateAmount heAmount);
	}
}
