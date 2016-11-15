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
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PersonalAllotSettingRepository;
import nts.uk.ctx.pr.screen.app.query.paymentdata.result.PaymentDataResult;

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

	/** LayoutMasterRepository */
	@Inject
	private LayoutMasterRepository layoutMasterRepository;

	@Inject
	private LayoutMasterCategoryRepository layoutMasterCategoryRepository;

	/**
	 * 邨ｦ荳弱ョ繝ｼ繧ｿ縺ｮ蜈･蜉幢ｼ亥�倶ｺｺ蛻･�ｼ�-蛻晄悄繝�繝ｼ繧ｿ蜿門ｾ怜�ｦ逅�.
	 * 
	 * @param companyCode
	 *            code
	 * @return PaymentDataResult
	 */
	public Optional<PaymentDataResult> find(String companyCode, String personId, int baseYM) {
		String stmtCode = "";
		// 譏守ｴｰ譖ｸ縺ｮ險ｭ螳夲ｼ亥�倶ｺｺ�ｼ�
		Optional<PersonalAllotSetting> optpersonalPS = this.personalPSRepository.find(companyCode, personId, baseYM);
		if (optpersonalPS.isPresent()) {
			stmtCode = optpersonalPS.get().getPaymentDetailCode().v();
		} else {
			// stmtCode =
		}

		// 譏守ｴｰ譖ｸ繝槭せ繧ｿ繝ｼ繧貞叙蠕励�√ョ繝ｼ繧ｿ縺後↑縺�蝣ｴ蜷遺�偵お繝ｩ繝ｼ繝｡繝�繧ｻ繝ｼ繧ｸ縺悟�ｺ縺励∪縺�
		LayoutMaster layout = this.layoutMasterRepository.find(companyCode, stmtCode, baseYM)
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("蟇ｾ雎｡繝�繝ｼ繧ｿ縺後≠繧翫∪縺帙ｓ縲�")));

		// 譏守ｴｰ譖ｸ繝槭せ繧ｿ繧ｫ繝�繧ｴ繝ｪ
		this.layoutMasterCategoryRepository.getCategories(companyCode, layout.getStmtCode().v(), baseYM);
		return null;
	}
}
