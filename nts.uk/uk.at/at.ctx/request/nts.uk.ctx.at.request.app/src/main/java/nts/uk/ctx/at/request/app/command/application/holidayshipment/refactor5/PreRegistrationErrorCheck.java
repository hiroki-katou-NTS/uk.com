package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.ActualContentDisplayDto;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ManageDeadline;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
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
	
//	@Inject
//	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private CompanyAdapter companyAdapter;
	
	@Inject
	private WorkTypeRepository wkTypeRepo;
	
	/**
	 * 登録前エラーチェック（新規）
	 * @param companyId 会社ID
	 * @param abs 振休申請
	 * @param rec 振出申請
	 * @param opActualContentDisplayLst 表示する実績内容
	 */
	public void errorCheck(String companyId, Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec, List<ActualContentDisplayDto> opActualContentDisplayLst) {
		//アルゴリズム「事前条件チェック」を実行する
		this.preconditionCheck(abs, rec);
		
		//アルゴリズム「申請日関連チェック」を実行する
		this.applicationDateRelatedCheck(companyId, abs, rec);
		
		//QA: http://192.168.50.4:3000/issues/113341
		//申請の矛盾チェック
		// this.commonAlgorithm.appConflictCheck(companyId, employeeInfo, dateLst, workTypeLst, opActualContentDisplayLst);

		//アルゴリズム「終日半日矛盾チェック」を実行する
		this.allDayAndHalfDayContradictionCheck(companyId, abs, rec);
		
	}
	
	
	/**
	 * 事前条件チェック
	 */
	public void preconditionCheck(Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec) {
		String cId = AppContexts.user().companyId();
		if (abs.isPresent()) {
			//勤務種類、就業時間帯チェックのメッセージを表示 (Hiển thị message check Type of work, working hours)
			this.detailBeforeUpdate.displayWorkingHourCheck(cId, abs.get().getWorkInformation().getWorkTypeCode().v(), abs.get().getWorkInformation().getWorkTimeCode().v());
			//ドメインモデル「振休申請」の事前条件をチェックする
			abs.get().validateApp();
		}
		if (rec.isPresent()) {
			//勤務種類、就業時間帯チェックのメッセージを表示 (Hiển thị message check Type of work, working hours)
			this.detailBeforeUpdate.displayWorkingHourCheck(cId, rec.get().getWorkInformation().getWorkTypeCode().v(), rec.get().getWorkInformation().getWorkTimeCode().v());
			//ドメインモデル「振出申請」の事前条件をチェックする
			rec.get().validateApp();
		}
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
				this.checkTimeApplication(cId, rec.get().getEmployeeID(), rec.get().getAppDate().getApplicationDate(), abs.get().getAppDate().getApplicationDate(), comSubOpt.get().getSetting().getManageDeadline(),comSubOpt.get().getSetting().getExpirationDate());
			}
		}
	}
	
	/**
	 * 終日半日矛盾チェック
	 */
	public void allDayAndHalfDayContradictionCheck(String companyID, Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec) {
		if(rec.isPresent() && abs.isPresent()) {
			BigDecimal recDay = this.getByWorkType(rec.get().getWorkInformation().getWorkTypeCode().v(), companyID, WorkTypeClassification.Shooting);
			if(recDay == new BigDecimal(0)) {
				return;
			}
			BigDecimal absDay = this.getByWorkType(abs.get().getWorkInformation().getWorkTypeCode().v(), companyID, WorkTypeClassification.Pause);
			if(absDay == new BigDecimal(0)) {
				return;
			}
			if(recDay != absDay) {
				throw new BusinessException("Msg_698");
			}
		}
	}
	
	
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
	public void checkTimeApplication(String cId, String sId,  GeneralDate recDate, GeneralDate absDate, ManageDeadline manageDeadline, ExpirationTime expirationDate) {
		// Imported（就業.shared.組織管理.社員情報.所属雇用履歴)「所属雇用履歴」を取得する
		GeneralDate calExpDateFromRecDate = this.calExpDateFromRecDate(cId, recDate, manageDeadline, expirationDate);
		if(absDate.after(calExpDateFromRecDate)) {
			throw new BusinessException("Msg_1361");
		}
	}
	
	/**
	 * 休暇使用期限から使用期限日を算出する
	 */
	public GeneralDate calExpDateFromRecDate(String cId, GeneralDate recDate, ManageDeadline manageDeadline, ExpirationTime expirationDate) {
		GeneralDate resultDate = GeneralDate.max();
		switch (expirationDate) {
		case END_OF_YEAR:
			Integer startMonth = companyAdapter.getFirstMonth(cId).getStartMonth();
			resultDate = GeneralDate.ymd(recDate.month() < startMonth ? recDate.month() : recDate.month() + 1, startMonth, 1);
			resultDate.addDays(-1);
			break;
		case UNLIMITED:
			resultDate = GeneralDate.max();
			break;
		default:
			// QA: http://192.168.50.4:3000/issues/113331
			// thiếu employmentCode Và thừa "期限日の管理方法"
			break;

		}
		return resultDate;
	}
	
	
	/**
	 * @name 勤務種類別振休発生数の取得 -> workTypeClass = 振出
	 * @name 勤務種類別振休消化数の取得 -> workTypeClass = 振休
	 */
	public BigDecimal getByWorkType(String wkTypeCD, String companyID, WorkTypeClassification workTypeClass) {
		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyID, wkTypeCD);
		BigDecimal result = new BigDecimal(0);
		if (wkTypeOpt.isPresent()) {
			if (wkTypeOpt.get().getDailyWork().getWorkTypeUnit() == WorkTypeUnit.OneDay && wkTypeOpt.get().getDailyWork().getOneDay() == workTypeClass) {
				result = new BigDecimal(1);
			}else {
				if (wkTypeOpt.get().getDailyWork().getMorning() == workTypeClass) {
					result = result.add(BigDecimal.valueOf(0.5));
				} else {
					//2018/03/19 対象外(Pending)
					//勤務種類に「振出扱い」の設定が反映されていないため
				}
				if (wkTypeOpt.get().getDailyWork().getAfternoon() == workTypeClass) {
					result = result.add(BigDecimal.valueOf(0.5));
				} else {
					//2018/03/19 対象外(Pending)
					//勤務種類に「振出扱い」の設定が反映されていないため
				}
			}
		}
		return result;
	}

}
