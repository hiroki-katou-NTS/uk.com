package nts.uk.ctx.at.request.infra.repository.setting.company.request;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.DisabledSegment_New;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.AuthorizationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.RecordDate;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.applimitset.AppLimitSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.AfterhandRestriction;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.AppAcceptLimitDay;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.BeforeAddCheckMethod;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.BeforehandRestriction;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.PrePostInitialAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.ReceptionRestrictionSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.deadlinesetting.AppDeadlineSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.AppDisplaySetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.appreflect.AppReflectionSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.AppReflectAfterConfirm;
import nts.uk.ctx.at.request.dom.setting.company.request.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.ctx.at.request.infra.entity.setting.request.application.KrqstAppTypeDiscrete;
import nts.uk.ctx.at.request.infra.entity.setting.request.application.KrqstAppTypeDiscretePK;
import nts.uk.ctx.at.request.infra.entity.setting.request.application.KrqstApplicationSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.AttendanceClock;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaRequestSettingRepository extends JpaRepository implements RequestSettingRepository{
	private final String SELECT_NO_WHERE = "SELECT c FROM KrqstApplicationSetting c ";
	private final String SELECT_BY_COM = SELECT_NO_WHERE + "WHERE c.krqstApplicationSettingPK.companyID = :companyID";
	/**
	 * ドメインモデル「承認一覧表示設定」を取得する
	 */
	@Override
	public Optional<RequestSetting> findByCompany(String companyID) {
		return this.queryProxy().query(SELECT_BY_COM, KrqstApplicationSetting.class)
								.setParameter("companyID", companyID)
								.getSingle(c->toDomain(c));
	}
	
	private  RequestSetting toDomain(KrqstApplicationSetting entity){
		return RequestSetting.toDomain(
				entity.krqstApplicationSettingPK.companyID, 
				ApplicationSetting.toDomain(
						entity.baseDateFlg, 
						AppDisplaySetting.toDomain(
								entity.displayPrePostFlg, 
								entity.displaySearchTimeFlg, 
								entity.manualSendMailAtr), 
						entity.krqstAppTypeDiscretes.stream()
							.map(x -> ReceptionRestrictionSetting.toDomain(
									x.krqstAppTypeDiscretePK.appType, 
									BeforehandRestriction.toDomain(
											x.retrictPreMethodFlg, 
											x.retrictPreUseFlg, 
											x.retrictPreDay, 
											x.retrictPreTimeDay), 
									AfterhandRestriction.toDomain(x.retrictPostAllowFutureFlg)))
							.collect(Collectors.toList()), 
						entity.krqstAppTypeDiscretes.stream()
							.map(x -> AppTypeSetting.toDomain(
									x.prePostInitAtr, 
									x.prePostCanChangeFlg, 
									x.typicalReasonDisplayFlg, 
									x.sendMailWhenApprovalFlg, 
									x.sendMailWhenRegisterlFlg, 
									x.displayReasonFlg, 
									x.krqstAppTypeDiscretePK.appType, 
									null))
							.collect(Collectors.toList()), 
						AppLimitSetting.toDomain(
								entity.appActLockFlg, 
								entity.appEndWorkFlg, 
								entity.appActConfirmFlg, 
								entity.appOvertimeNightFlg, 
								entity.appActMonthConfirmFlg, 
								entity.requireAppReasonFlg), 
						entity.krqstAppDeadlines.stream()
							.map(x -> AppDeadlineSetting.createSimpleFromJavaType(
									x.krqstAppDeadlinePK.companyId, 
									x.krqstAppDeadlinePK.closureId, 
									x.useAtr, 
									x.deadline, 
									x.deadlineCriteria))
							.collect(Collectors.toList())
				),  
				AppReflectionSetting.toDomain(
						entity.scheReflectFlg, 
						entity.priorityTimeReflectFlg, 
						entity.attendentTimeReflectFlg), 
				ApprovalListDisplaySetting.toDomain(
						entity.advanceExcessMessDispAtr, 
						entity.hwAdvanceDispAtr, 
						entity.hwActualDispAtr, 
						entity.actualExcessMessDispAtr, 
						entity.otAdvanceDispAtr, 
						entity.otActualDispAtr, 
						entity.warningDateDispAtr, 
						entity.appReasonDispAtr), 
				AuthorizationSetting.toDomain(
						entity.appContentChangeFlg), 
				null);
	}
	/**
	 * update after and Before hand Restriction
	 * @author yennth
	 */
	@Override
	public void update(RequestSetting req) {
		String companyId = AppContexts.user().companyId();
		List<ReceptionRestrictionSetting> appType = req.getApplicationSetting().getListReceptionRestrictionSetting();
		for(ReceptionRestrictionSetting item: appType){
			KrqstAppTypeDiscrete oldEntity = this.queryProxy().find(new KrqstAppTypeDiscretePK(companyId, item.getAppType().value), KrqstAppTypeDiscrete.class).get();
			//チェック方法 - retrictPreMethodFlg - RETRICT_PRE_METHOD_CHECK_FLG
			oldEntity.retrictPreMethodFlg = item.getBeforehandRestriction().getMethodCheck().value;
			// 利用する - retrictPreUseFlg - RETRICT_PRE_USE_FLG
			oldEntity.retrictPreUseFlg = item.getBeforehandRestriction().getToUse() == true ? 1 : 0;
			// 日数 - retrictPreDay - RETRICT_PRE_DAY
			oldEntity.retrictPreDay = item.getBeforehandRestriction().getDateBeforehandRestriction().value;
			// 時刻 - retrictPreTimeDay - RETRICT_PRE_TIMEDAY
			oldEntity.retrictPreTimeDay = item.getBeforehandRestriction().getTimeBeforehandRestriction().v();
			// 未来日許可しない - retrictPostAllowFutureFlg - RETRICT_POST_ALLOW_FUTURE_FLG
			oldEntity.retrictPostAllowFutureFlg = item.getAfterhandRestriction().getAllowFutureDay() == true ? 1: 0;
			this.commandProxy().update(oldEntity);
		}
	}
}
