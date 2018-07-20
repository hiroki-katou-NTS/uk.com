package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister_New;

@Stateless
public class DetailBeforeUpdateImpl implements DetailBeforeUpdate {

	@Inject
	private NewBeforeRegister_New newBeforeRegister;

	@Inject
	private ApplicationRepository_New applicationRepository;

	public void processBeforeDetailScreenRegistration(String companyID, String employeeID, GeneralDate appDate,
			int employeeRouteAtr, String appID, PrePostAtr postAtr, Long version) {

		// 選択した勤務種類の矛盾チェック(check sự mâu thuẫn của worktype đã chọn)
		// selectedWorkTypeConflictCheck();

		// アルゴリズム「確定チェック」を実施する(thực hiện xử lý 「確定チェック」)
		newBeforeRegister.confirmationCheck(companyID, employeeID, appDate);

		exclusiveCheck(companyID, appID, version);
	}
	
	/**
	 * 11-1.詳細画面差し戻し前の処理
	 */
	public void exclusiveCheck(String companyID, String appID, Long version) {
		if (applicationRepository.findByID(companyID, appID).isPresent()) {
			Application_New application = applicationRepository.findByID(companyID, appID).get();
			if (!application.getVersion().equals(version)) {
				throw new BusinessException("Msg_197");
			}
		} else {
			throw new BusinessException("Msg_198");
		}
	}

	/**
	 * 4-1.詳細画面登録前の処理 (CMM045)
	 * 
	 * @author hoatt
	 */
	@Override
	public boolean processBefDetailScreenReg(String companyID, String employeeID, GeneralDate appDate,
			int employeeRouteAtr, String appID, PrePostAtr postAtr, Long version) {
		// 選択した勤務種類の矛盾チェック(check sự mâu thuẫn của worktype đã chọn)
		// selectedWorkTypeConflictCheck();

		// アルゴリズム「確定チェック」を実施する(thực hiện xử lý 「確定チェック」)
		newBeforeRegister.confirmationCheck(companyID, employeeID, appDate);
		// アルゴリズム「排他チェック」を実行する(thực hiện xử lý 「排他チェック」)
		return exclusiveCheckErr(companyID, appID, version);
	}

	private boolean exclusiveCheckErr(String companyID, String appID, Long version) {
		if (applicationRepository.findByID(companyID, appID).isPresent()) {
			Application_New application = applicationRepository.findByID(companyID, appID).get();
			if (!application.getVersion().equals(version)) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
}
