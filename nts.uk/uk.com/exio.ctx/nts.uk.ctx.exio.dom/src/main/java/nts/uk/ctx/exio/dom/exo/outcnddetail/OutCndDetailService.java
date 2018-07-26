package nts.uk.ctx.exio.dom.exo.outcnddetail;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.RegisterMode;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.RegistrationCondDetails;
import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;

@Stateless
public class OutCndDetailService {
	@Inject
	private RegistrationCondDetails registrationCondDetails;

	/**
	 * 外部出力条件登録
	 */
	public void registerExOutCondition(Optional<OutCndDetail> outCndDetail, int standardAtr, int registerMode) {
		// アルゴリズム「外部出力登録条件詳細」を実行する
		registrationCondDetails.algorithm(outCndDetail, EnumAdaptor.valueOf(standardAtr, StandardAtr.class),
				EnumAdaptor.valueOf(registerMode, RegisterMode.class));
	}
}
