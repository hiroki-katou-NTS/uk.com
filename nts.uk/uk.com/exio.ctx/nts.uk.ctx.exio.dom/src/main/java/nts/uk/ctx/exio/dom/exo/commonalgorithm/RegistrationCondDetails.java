package nts.uk.ctx.exio.dom.exo.commonalgorithm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RegistrationCondDetails {
	@Inject
	private OutCndDetailRepository stdOutCndDetailRepo;

	/**
	 * 外部出力登録条件詳細
	 * 
	 * @param outCndDetail
	 *            条件詳細保存内容
	 * @param standardAtr
	 *            定型区分(定型/ユーザ)
	 * @param registerMode
	 *            登録モード(新規/更新)
	 */
	@Transactional
	public void algorithm(Optional<OutCndDetail> outCndDetailOtp, StandardAtr standardAtr, RegisterMode registerMode) {
		String cid = AppContexts.user().companyId();
		if (!outCndDetailOtp.isPresent()) {
			return;
		}
		OutCndDetail outCndDetail = outCndDetailOtp.get();
		if (StandardAtr.STANDARD.equals(standardAtr)) {
			if (RegisterMode.NEW.equals(registerMode)) {
				stdOutCndDetailRepo.add(outCndDetail);
			} else {
				stdOutCndDetailRepo.remove(cid, outCndDetail.getConditionSettingCd().v());
				stdOutCndDetailRepo.add(outCndDetail);
			}
		}
	}
}
