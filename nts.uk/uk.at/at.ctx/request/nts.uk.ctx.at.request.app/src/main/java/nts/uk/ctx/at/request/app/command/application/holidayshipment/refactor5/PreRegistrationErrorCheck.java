package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.WorkInformationForApplicationDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.vacation.service.UseDateDeadlineFromDatePeriod;
import nts.uk.ctx.at.shared.dom.vacation.service.UseDateDeadlineFromDatePeriod.RequireM1;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ManageDeadline;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
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
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private CompanyAdapter companyAdapter;
	
	@Inject
	private WorkTypeRepository wkTypeRepo;
	
	@Inject
	private ClosureRepository closureRepo; 
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	/**
	 * 登録前エラーチェック（新規）
	 * @param companyId 会社ID
	 * @param abs 振休申請
	 * @param rec 振出申請
	 * @param opActualContentDisplayLst 表示する実績内容
	 * @param employeeInfoLst 社員情報
	 * @param employmentCode 雇用コード
	 */
	public void errorCheck(String companyId, Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec, 
	        List<ActualContentDisplay> opActualContentDisplayLst, 
	        EmployeeInfoImport employeeInfo, String employmentCode, 
	        Optional<WorkInformationForApplicationDto> absWorkInformationForApp, Optional<WorkInformationForApplicationDto> recWorkInformationForApp, List<PayoutSubofHDManagement> payoutSubofHDManagements) {
		//アルゴリズム「事前条件チェック」を実行する
		this.preconditionCheck(abs, rec, absWorkInformationForApp, recWorkInformationForApp);
		
		//アルゴリズム「申請日関連チェック」を実行する
		this.applicationDateRelatedCheck(companyId, abs, rec, employmentCode);
		
		List<GeneralDate> dateLst = new ArrayList<>();
		List<String> workTypeLst = new ArrayList<>();
		if(rec.isPresent()) {
			dateLst.add(rec.get().getAppDate().getApplicationDate());
			workTypeLst.add(rec.get().getWorkInformation().getWorkTypeCode().v());
		}
		if(abs.isPresent()) {
			dateLst.add(abs.get().getAppDate().getApplicationDate());
			workTypeLst.add(abs.get().getWorkInformation().getWorkTypeCode().v());
		}
		//QA: http://192.168.50.4:3000/issues/113341 => done
		// hiện tại đang comment - không check chờ Trả lời QA: http://192.168.50.4:3000/issues/113784
		//申請の矛盾チェック
		this.commonAlgorithm.appConflictCheck(companyId, employeeInfo, dateLst, workTypeLst, opActualContentDisplayLst);

		//アルゴリズム「終日半日矛盾チェック」を実行する
		this.allDayAndHalfDayContradictionCheck(companyId, abs, rec);
		
		// INPUT．振出申請がNULL　AND　INPUT．振休申請がNULLじゃない　AND　INPUT.振休申請.振出振休紐付け管理＝設定なし
		if (!rec.isPresent() && abs.isPresent() && payoutSubofHDManagements.isEmpty()) {
		    throw new BusinessException("Msg_2223");
		}
	}
	
	
	/**
	 * 事前条件チェック
	 * @param abs 振休申請
	 * @param rec 振出申請
	 * @param absWorkInformationForApp 振休の変更前勤務情報
	 * @param recWorkInformationForApp 振出の変更前勤務情報
	 */
	public void preconditionCheck(Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec, 
	        Optional<WorkInformationForApplicationDto> absWorkInformationForApp, Optional<WorkInformationForApplicationDto> recWorkInformationForApp) {
		String cId = AppContexts.user().companyId();
		if (rec.isPresent()) {
		    String workTypeCD = null;
		    String workTimeCD = null;
		    if (!recWorkInformationForApp.isPresent() 
		            || (rec.get().getWorkInformation().getWorkTimeCode() != null && 
		            !recWorkInformationForApp.get().getWorkTimeCode().equals(rec.get().getWorkInformation().getWorkTimeCode().v()))) {
		        workTimeCD = rec.get().getWorkInformation().getWorkTimeCode() == null ? null : rec.get().getWorkInformation().getWorkTimeCode().v();
		    }
		    if (!recWorkInformationForApp.isPresent() 
                    || (rec.get().getWorkInformation().getWorkTypeCode() != null && 
                    !recWorkInformationForApp.get().getWorkTypeCode().equals(rec.get().getWorkInformation().getWorkTypeCode().v()))) {
		        workTypeCD = rec.get().getWorkInformation().getWorkTypeCode() == null ? null : rec.get().getWorkInformation().getWorkTypeCode().v();
            }
			//勤務種類、就業時間帯チェックのメッセージを表示 (Hiển thị message check Type of work, working hours)
			this.detailBeforeUpdate.displayWorkingHourCheck(cId, workTypeCD, workTimeCD);
			//ドメインモデル「振出申請」の事前条件をチェックする
			rec.get().validateApp();
		}
		if (abs.isPresent()) {
		    String workTypeCD = null;
            String workTimeCD = null;
            if (!absWorkInformationForApp.isPresent() 
                    || (abs.get().getWorkInformation().getWorkTimeCode() != null && 
                    !absWorkInformationForApp.get().getWorkTimeCode().equals(abs.get().getWorkInformation().getWorkTimeCode().v()))) {
                workTimeCD = abs.get().getWorkInformation().getWorkTimeCode() == null ? null : abs.get().getWorkInformation().getWorkTimeCode().v();
            }
            if (!absWorkInformationForApp.isPresent() 
                    || (abs.get().getWorkInformation().getWorkTypeCode() != null &&
                    !absWorkInformationForApp.get().getWorkTypeCode().equals(abs.get().getWorkInformation().getWorkTypeCode().v()))) {
                workTypeCD = abs.get().getWorkInformation().getWorkTypeCode() == null ? null : abs.get().getWorkInformation().getWorkTypeCode().v();
            }
			//勤務種類、就業時間帯チェックのメッセージを表示 (Hiển thị message check Type of work, working hours)
			this.detailBeforeUpdate.displayWorkingHourCheck(cId, workTypeCD, workTimeCD);
			//ドメインモデル「振休申請」の事前条件をチェックする
			abs.get().validateApp();
		}
	}
	
	/**
	 * 申請日関連チェック
	 */
	public void applicationDateRelatedCheck(String cId, Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec, String employmentCode) {
		if(abs.isPresent() && rec.isPresent()) {
			//日付の前後関係をチェックする - check quan hệ trước sau của date
			if(rec.get().getAppDate().getApplicationDate().equals(abs.get().getAppDate().getApplicationDate())){
				throw new BusinessException("Msg_696");
			}
			//会社別の振休管理設定を取得する - Get setting quản lý nghỉ bù theo công ty
			Optional<ComSubstVacation> comSubOpt = comSubrepo.findById(cId);
			//QA: http://192.168.50.4:3000/issues/113314 => done
			if (comSubOpt.isPresent()) {
				this.checkFirstShipment(comSubOpt.get().getSetting().getAllowPrepaidLeave(), rec.get().getAppDate().getApplicationDate(), abs.get().getAppDate().getApplicationDate());
				this.checkTimeApplication(cId, rec.get().getEmployeeID(), rec.get().getAppDate().getApplicationDate(), abs.get().getAppDate().getApplicationDate(), comSubOpt.get().getSetting().getManageDeadline(),comSubOpt.get().getSetting().getExpirationDate(), employmentCode);
			}
		}
	}
	
	/**
	 * 終日半日矛盾チェック
	 */
	public void allDayAndHalfDayContradictionCheck(String companyID, Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec) {
		if(rec.isPresent() && abs.isPresent()) {
			BigDecimal recDay = this.getByWorkType(rec.get().getWorkInformation().getWorkTypeCode().v(), companyID, WorkTypeClassification.Shooting);
			if(recDay.compareTo(new BigDecimal(0)) == 0) {
				return;
			}
			BigDecimal absDay = this.getByWorkType(abs.get().getWorkInformation().getWorkTypeCode().v(), companyID, WorkTypeClassification.Pause);
			if(absDay.compareTo(new BigDecimal(0)) == 0) {
				return;
			}
			if(recDay.compareTo(absDay) != 0) {
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
	public void checkTimeApplication(String cId, String sId,  GeneralDate recDate, GeneralDate absDate, ManageDeadline manageDeadline, ExpirationTime expirationDate, String employmentCode) {
		// Imported（就業.shared.組織管理.社員情報.所属雇用履歴)「所属雇用履歴」を取得する
		GeneralDate calExpDateFromRecDate = this.calExpDateFromRecDate(cId, recDate, manageDeadline, expirationDate, employmentCode);
		if(absDate.after(calExpDateFromRecDate)) {
			throw new BusinessException("Msg_1361", calExpDateFromRecDate.toString());
		}
	}
	
	/**
	 * 休暇使用期限から使用期限日を算出する
	 * @param cId 会社ID
	 * @param recDate 休暇発生日
	 * @param manageDeadline 期限日の管理方法
	 * @param expirationDate 休暇使用期限
	 * @param employmentCode 雇用コード
	 * @return
	 */
	public GeneralDate calExpDateFromRecDate(String cId, GeneralDate recDate, ManageDeadline manageDeadline, ExpirationTime expirationDate, String employmentCode) {
		GeneralDate resultDate = GeneralDate.max();
		switch (expirationDate) {
		case END_OF_YEAR:
			Integer startMonth = companyAdapter.getFirstMonth(cId).getStartMonth();
			resultDate = GeneralDate.ymd(recDate.month() < startMonth ? recDate.year() : recDate.year() + 1, startMonth, 1);
			resultDate.addDays(-1);
			break;
		case UNLIMITED:
			resultDate = GeneralDate.max();
			break;
		default:
			// QA: http://192.168.50.4:3000/issues/113331 => done
			// thiếu employmentCode Và thừa "期限日の管理方法"
			resultDate = UseDateDeadlineFromDatePeriod.useDateDeadline(createRequireM1(), employmentCode, expirationDate, recDate, manageDeadline);
			break;

		}
		return resultDate;
	}
	
	public RequireM1 createRequireM1() {
		
		return new RequireM1() {
			@Override
			public Optional<Closure> closure(String companyId, int closureId) {
				return closureRepo.findById(companyId, closureId);
			}
			
			@Override
			public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
				return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
			}
		};
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




