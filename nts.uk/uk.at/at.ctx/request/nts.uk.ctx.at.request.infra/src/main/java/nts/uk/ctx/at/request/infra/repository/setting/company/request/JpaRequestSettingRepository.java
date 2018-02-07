package nts.uk.ctx.at.request.infra.repository.setting.company.request;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.infra.entity.setting.request.application.KrqstApplicationSetting;
import nts.uk.ctx.at.request.infra.entity.setting.request.application.KrqstApplicationSettingPK;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaRequestSettingRepository extends JpaRepository implements RequestSettingRepository{
	
	/**
	 * ドメインモデル「承認一覧表示設定」を取得する
	 */
	@Override
	public Optional<RequestSetting> findByCompany(String companyID) {
		return this.queryProxy().find(new KrqstApplicationSettingPK(companyID), KrqstApplicationSetting.class).map(c->toDomain(c));
	}
	
	private  RequestSetting toDomain(KrqstApplicationSetting entity){
		val domain = RequestSetting.createSimpleFromJavaType(entity.krqstApplicationSettingPK.companyID,
				entity.scheReflectFlg,
				entity.priorityTimeReflectFlg,
				entity.attendentTimeReflectFlg,
				entity.advanceExcessMessDispAtr,
				entity.hwAdvanceDispAtr,
				entity.hwActualDispAtr,
				entity.actualExcessMessDispAtr,
				entity.otAdvanceDispAtr,
				entity.otActualDispAtr,
				entity.warningDateDispAtr,
				entity.appReasonDispAtr,
				entity.appContentChangeFlg,
				entity.scheduleConfirmedAtr,
				entity.achievementConfirmedAtr);
		return domain;
	}
}
