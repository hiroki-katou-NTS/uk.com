package nts.uk.ctx.pr.screen.app.query.paymentdata.processor;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSetting;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.CompanyAllotSettingRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PersonalAllotSettingRepository;
import nts.uk.ctx.pr.screen.app.query.paymentdata.result.PaymentDataResult;
import nts.uk.shr.com.context.AppContexts;

/**
 * GetPaymentDataQueryProcessor
 * 
 * @author vunv
 *
 */
@RequestScoped
public class GetPaymentDataQueryProcessor {

	/** PersonalPaymentSettingRepository */
	@Inject
	private PersonalAllotSettingRepository personalPSRepository;

	@Inject
	private CompanyAllotSettingRepository companyAllotSettingRepository;

	/** LayoutMasterRepository */
	@Inject
	private LayoutMasterRepository layoutMasterRepository;

	@Inject
	private LayoutMasterCategoryRepository layoutMasterCategoryRepository;

	/**
	 * 給与データの入力（個人別）-初期データ取得処理.
	 * 
	 * @param companyCode
	 *            code
	 * @return PaymentDataResult
	 */
	public Optional<PaymentDataResult> find(String personId, int baseYM) {
		String companyCode = AppContexts.user().companyCode();
		String stmtCode = "";
		// 明細書の設定（個人）
		Optional<PersonalAllotSetting> optpersonalPS = this.personalPSRepository.find(companyCode, personId, baseYM);
		if (optpersonalPS.isPresent()) {
			stmtCode = optpersonalPS.get().getPaymentDetailCode().v();
		} else {
			stmtCode = this.companyAllotSettingRepository.find(companyCode, baseYM).get().getPaymentDetailCode().v();
		}

		// 明細書マスターを取得、データがない場合→エラーメッセージが出します
		LayoutMaster layout = this.layoutMasterRepository.find(companyCode, stmtCode, baseYM)
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("対象データがありません。")));

		// 明細書マスタカテゴリ
		this.layoutMasterCategoryRepository.getCategories(companyCode, layout.getStmtCode().v(), baseYM);
		return null;
	}
}
