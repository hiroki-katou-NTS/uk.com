package nts.uk.ctx.pr.screen.app.query.paymentdata.processor;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSettingRepository;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSettingRepository;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
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

	@Inject
	private LayoutMasterLineRepository layoutMasterLineRepository;

	@Inject
	private LayoutMasterDetailRepository layoutMasterDetailRepository;

	/**
	 * get data detail
	 * 
	 * @param companyCode
	 *            code
	 * @return PaymentDataResult
	 */
	public Optional<PaymentDataResult> find(String personId, int baseYM) {
		String companyCode = AppContexts.user().companyCode();
		int startYM;
		String stmtCode = "";

		// get stmtCode
		Optional<PersonalAllotSetting> optpersonalPS = this.personalPSRepository.find(companyCode, personId, baseYM);
		if (optpersonalPS.isPresent()) {
			stmtCode = optpersonalPS.get().getPaymentDetailCode().v();
		} else {
			stmtCode = this.companyAllotSettingRepository.find(companyCode, baseYM).get().getPaymentDetailCode().v();
		}

		// get layout master info
		LayoutMaster layout = this.layoutMasterRepository.getLayout(companyCode, stmtCode, baseYM)
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("対象データがありません。")));
		startYM = layout.getStartYM().v();

		// 明細書マスタカテゴリ
		List<LayoutMasterCategory> categories = this.layoutMasterCategoryRepository.getCategories(companyCode,
				layout.getStmtCode().v(), startYM);

		// 明細書マスタ行
		// List<LayoutMasterLine>C lines =
		// this.layoutMasterLineRepository.getLines(companyCode, startYM,
		// stmtCode);

		List<LayoutMasterDetail> lineDetails = this.layoutMasterDetailRepository.getDetails(companyCode, stmtCode,
				startYM);

		return null;
	}
}
