package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author thanhpv
 * @URL UKDesign.UniversalK.就業.KAF_申請.KAF011_振休振出申請.A：振休振出申請（新規）.アルゴリズム.振休振出申請の新規登録.登録前エラーチェック（新規）
 * 
 */
@Stateless
public class PreRegistrationErrorCheck {
	
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
	
	@Inject
	private ComSubstVacationRepository comSubrepo;
	
	@Inject
	private PersonalLaborConditionRepository personalLaborConditionRepository;
	
	@Inject
	private CompanyAdapter companyAdapter;
	
	/**
	 * 登録前エラーチェック（新規）
	 * @param abs 振休申請
	 * @param rec 振出申請
	 */
	public void errorCheck(Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec, boolean requiredReasons) {
		String cId = AppContexts.user().companyId();
		//アルゴリズム「事前条件チェック」を実行する
		this.preconditionCheck(abs, rec, requiredReasons);
		
		if(rec.isPresent()) {
			//アルゴリズム「申請前勤務種類の取得」を実行する
			this.getPreApplicationWorkType(rec.get().getEmployeeID(), rec.get().getAppDate().getApplicationDate());
		}
		if(abs.isPresent()) {
			//アルゴリズム「申請前勤務種類の取得」を実行する
			this.getPreApplicationWorkType(abs.get().getEmployeeID(), abs.get().getAppDate().getApplicationDate());
		}
		
	}
	
	/**
	 * 事前条件チェック
	 */
	public void preconditionCheck(Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec, boolean requiredReasons) {
		String cId = AppContexts.user().companyId();
		if (abs.isPresent()) {
			//勤務種類、就業時間帯チェックのメッセージを表示 (Hiển thị message check Type of work, working hours)
			this.detailBeforeUpdate.displayWorkingHourCheck(cId, abs.get().getWorkInformation().getWorkTypeCode().v(), abs.get().getWorkInformation().getWorkTimeCode().v());
			//ドメインモデル「振休申請」の事前条件をチェックする
			abs.get().validateApp(requiredReasons);
		}
		if (rec.isPresent()) {
			//勤務種類、就業時間帯チェックのメッセージを表示 (Hiển thị message check Type of work, working hours)
			this.detailBeforeUpdate.displayWorkingHourCheck(cId, rec.get().getWorkInformation().getWorkTypeCode().v(), rec.get().getWorkInformation().getWorkTimeCode().v());
			//ドメインモデル「振出申請」の事前条件をチェックする
			rec.get().validateApp(requiredReasons);
		}
		// アルゴリズム「申請理由の生成と検査」を実行する QA: http://192.168.50.4:3000/issues/113296
		
	}
	
	/**
	 * 申請前勤務種類の取得
	 * đang chờ QA: http://192.168.50.4:3000/issues/113311
	 */
	public void getPreApplicationWorkType(String sID, GeneralDate appDate) {
		
	}
	
	/**
	 * 申請日関連チェック
	 */
	public void applicationDateRelatedCheck(String cId, Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec) {
		if(abs.isPresent() && rec.isPresent()) {
			//日付の前後関係をチェックする - check quan hệ trước sau của date
			if(rec.get().getAppDate().getApplicationDate().equals(abs.get().getAppDate().getApplicationDate())){
				throw new BusinessException("Msg_696");
			}
			//会社別の振休管理設定を取得する - Get setting quản lý nghỉ bù theo công ty
			Optional<ComSubstVacation> comSubOpt = comSubrepo.findById(cId);
			//QA: http://192.168.50.4:3000/issues/113314
			if (comSubOpt.isPresent()) {
				this.checkFirstShipment(comSubOpt.get().getSetting().getAllowPrepaidLeave(), rec.get().getAppDate().getApplicationDate(), abs.get().getAppDate().getApplicationDate());
			}
		}
	}
	
	/**
	 * 申請の矛盾チェック
	 */
	public void applicationContradictionCheck() {}
	
	/**
	 * 終日半日矛盾チェック
	 */
	public void allDayAndHalfDayContradictionCheck() {}
	
	
	/**
	 * 振休先取可否チェック
	 */
	private void checkFirstShipment(ApplyPermission applyPermission, GeneralDate recDate, GeneralDate absDate) {
		if (recDate.equals(absDate)) {
			throw new BusinessException("Msg_696");
		}
		if (recDate.after(absDate) && applyPermission.equals(ApplyPermission.NOT_ALLOW)) {
			throw new BusinessException("Msg_697");
		}
	}
	
	/**
	 * 同時申請時有効期限チェック
	 */
	public void checkTimeApplication(String cId, String sId,  GeneralDate recDate, GeneralDate absDate, ExpirationTime expTime, ManageDistinct isManage) {
		// Imported（就業.shared.組織管理.社員情報.所属雇用履歴)「所属雇用履歴」を取得する
		GeneralDate calExpDateFromRecDate = this.calExpDateFromRecDate(cId, recDate, expTime, isManage);
		if(absDate.after(calExpDateFromRecDate)) {
			throw new BusinessException("Msg_1361");
		}
	}
	
	/**
	 * 休暇使用期限から使用期限日を算出する
	 */
	public GeneralDate calExpDateFromRecDate(String cId, GeneralDate recDate, ExpirationTime expTime, ManageDistinct isManage) {
		GeneralDate resultDate;
		switch (expTime) {
		case END_OF_YEAR:
			Integer startMonth = companyAdapter.getFirstMonth(cId).getStartMonth();
			resultDate = GeneralDate.ymd(recDate.month() < startMonth ? recDate.month() : recDate.month() + 1, startMonth, 1);
			resultDate.addDays(-1);
			break;
		case UNLIMITED:
			resultDate = GeneralDate.max();
			break;
		default:
			// 期限指定のある使用期限日を作成する
			// resultDate = this.dateDeadline.useDateDeadline(employmentCd, expTime, recDate);
			resultDate = null;
			break;

		}
		return resultDate;
	}
}
