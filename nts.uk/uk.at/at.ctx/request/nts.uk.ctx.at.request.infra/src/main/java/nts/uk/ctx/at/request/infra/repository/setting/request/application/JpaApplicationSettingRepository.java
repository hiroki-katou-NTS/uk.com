package nts.uk.ctx.at.request.infra.repository.setting.request.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.UseAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.AllowAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.AppCanAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.CheckMethod;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.PossibleAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.RequiredFlg;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.RetrictDay;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.RetrictPreTimeDay;
import nts.uk.ctx.at.request.dom.setting.request.application.primitive.VacationAppType;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.infra.entity.setting.request.application.KrqstApplicationSetting;
import nts.uk.ctx.at.request.infra.entity.setting.request.application.KrqstApplicationSettingPK;

@Stateless
public class JpaApplicationSettingRepository extends JpaRepository implements ApplicationSettingRepository {

	public final String SELECT_NO_WHERE = "SELECT c FROM KrqstApplicationSetting c";

	public final String SELECT_WITH_CID = SELECT_NO_WHERE 
			+ " WHERE c.KrqstApplicationSettingPK.companyID := companyID";

	public final String SELECT_WITH_APP_TYPE = SELECT_NO_WHERE
			+ " WHERE c.KrqstApplicationSettingPK.companyID := companyID"
			+ " AND c.KrqstApplicationSettingPK.appType := appType ";

	/**
	 * @param entity
	 * @return
	 */
	private ApplicationSetting toDomain(KrqstApplicationSetting entity) {
		return new ApplicationSetting(entity.krqstApplicationSettingPK.companyID,
				EnumAdaptor.valueOf(entity.krqstApplicationSettingPK.appType, ApplicationType.class),
				EnumAdaptor.valueOf(entity.prePostCanChangeFlg, AppDisplayAtr.class),
				EnumAdaptor.valueOf(entity.prePostInitAtr, AppCanAtr.class),
				EnumAdaptor.valueOf(entity.typicalReasonDisplayFlg, AppDisplayAtr.class),
				EnumAdaptor.valueOf(entity.sendMailWhenApprovalFlg, AppCanAtr.class),
				EnumAdaptor.valueOf(entity.sendMailWhenRegisterlFlg, AppCanAtr.class),
				EnumAdaptor.valueOf(entity.displayReasonFlg, AppCanAtr.class),
				EnumAdaptor.valueOf(entity.vacationAppType, VacationAppType.class),
				EnumAdaptor.valueOf(entity.appActLockFlg, AppCanAtr.class),
				EnumAdaptor.valueOf(entity.appEndWorkFlg, AppCanAtr.class),
				EnumAdaptor.valueOf(entity.appActConfirmFlg, AppCanAtr.class),
				EnumAdaptor.valueOf(entity.appOvertimeNightFlg, AppCanAtr.class),
				EnumAdaptor.valueOf(entity.appActMonthConfirmFlg, AppCanAtr.class),
				EnumAdaptor.valueOf(entity.requireAppReasonFlg, RequiredFlg.class),
				EnumAdaptor.valueOf(entity.retrictPreMethodFlg, CheckMethod.class),
				EnumAdaptor.valueOf(entity.retrictPreUseFlg, UseAtr.class),
				EnumAdaptor.valueOf(entity.retrictPreDay, RetrictDay.class),
				EnumAdaptor.valueOf(entity.retrictPreTimeDay, RetrictPreTimeDay.class),
				EnumAdaptor.valueOf(entity.retrictPreCanAcceptFlg, PossibleAtr.class),
				EnumAdaptor.valueOf(entity.retrictPostAllowFutureFlg, AllowAtr.class),
				EnumAdaptor.valueOf(entity.displayPrePostFlg, AppDisplayAtr.class),
				EnumAdaptor.valueOf(entity.displaySearchTimeFlg, AppDisplayAtr.class),
				EnumAdaptor.valueOf(entity.displayInitDayFlg, RetrictDay.class));
	}

	/**
	 * @param domain
	 * @return
	 */
	private KrqstApplicationSetting toEntity(ApplicationSetting domain) {
		val entity = new KrqstApplicationSetting();
		entity.krqstApplicationSettingPK = new KrqstApplicationSettingPK();
		entity.krqstApplicationSettingPK.companyID = domain.getCompanyID();
		entity.krqstApplicationSettingPK.appType = domain.getAppType().value;
		entity.prePostCanChangeFlg = domain.getPrePostCanChangeFlg().value;
		entity.prePostInitAtr = domain.getPrePostInitFlg().value;
		entity.typicalReasonDisplayFlg = domain.getTypicalReasonDisplayFlg().value;
		entity.sendMailWhenApprovalFlg = domain.getSendMailWhenApprovalFlg().value;
		entity.sendMailWhenRegisterlFlg = domain.getSendMailWhenRegisterFlg().value;
		entity.displayReasonFlg = domain.getDisplayReasonFlg().value;
		entity.vacationAppType = domain.getVacationAppType().value;
		entity.appActLockFlg = domain.getAppActLockFlg().value;
		entity.appEndWorkFlg = domain.getAppEndWorkFlg().value;
		entity.appActConfirmFlg = domain.getAppActConfirmFlg().value;
		entity.appOvertimeNightFlg = domain.getAppOvertimeNightFlg().value;
		entity.appActMonthConfirmFlg = domain.getAppActMonthConfirmFlg().value;
		entity.requireAppReasonFlg = domain.getRequireAppReasonFlg().value;
		entity.retrictPreMethodFlg = domain.getRetrictPreMethodFlg().value;
		entity.retrictPreUseFlg = domain.getRetrictPreUseFlg().value;
		entity.retrictPreDay = domain.getRetrictPreDay().value;
		entity.retrictPreTimeDay = domain.getRetrictPreCanAceeptFlg().value;
		entity.retrictPreCanAcceptFlg = domain.getRetrictPreCanAceeptFlg().value;
		entity.retrictPostAllowFutureFlg = domain.getRetrictPostAllowFutureFlg().value;
		entity.displayPrePostFlg = domain.getDisplayPrePostFlg().value;
		entity.displaySearchTimeFlg = domain.getDisplaySearchTimeFlg().value;
		entity.displayInitDayFlg = domain.getDisplayInitDayFlg().value;
		return entity;
	}

	@Override
	public Optional<ApplicationSetting> getApplicationSettingByAppType(String companyID, int appType) {
		return this.queryProxy().query(SELECT_WITH_APP_TYPE, KrqstApplicationSetting.class)
				.setParameter("companyID", companyID).setParameter("appID", appType).getSingle(c -> toDomain(c));
	}

	@Override
	public List<ApplicationSetting> getApplicationSettingByCompany(String companyID) {
		return this.queryProxy().query(SELECT_WITH_CID, KrqstApplicationSetting.class)
				.setParameter("companyID", companyID).getList(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.request.dom.setting.request.application.
	 * ApplicationSettingRepository#update(nts.uk.ctx.at.request.dom.setting.request
	 * .application.ApplicationSetting)
	 */
	@Override
	public void updateSingle(ApplicationSetting applicationSetting) {
		this.commandProxy().update(toEntity(applicationSetting));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.request.dom.setting.request.application.
	 * ApplicationSettingRepository#updateList(java.util.List)
	 */
	@Override
	public void updateList(List<ApplicationSetting> lstApplicationSetting) {
		List<KrqstApplicationSetting> lstEntity = new ArrayList<>();
		for (ApplicationSetting applicationSetting : lstApplicationSetting) {
			lstEntity.add(toEntity(applicationSetting));
		}
		;
		this.commandProxy().updateAll(lstEntity);
	}
}
