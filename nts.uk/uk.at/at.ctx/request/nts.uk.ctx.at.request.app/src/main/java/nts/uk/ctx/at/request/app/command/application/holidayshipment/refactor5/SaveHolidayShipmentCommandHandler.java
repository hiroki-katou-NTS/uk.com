package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.dto.HolidayShipmentRefactor5Command;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author thanhpv
 *	UKDesign.UniversalK.就業.KAF_申請.KAF011_振休振出申請.A：振休振出申請（新規）.ユースケース.登録する(Đăng kí).登録する
 */
@Stateless
public class SaveHolidayShipmentCommandHandler {

	@Inject
	private ErrorCheckProcessingBeforeRegistrationKAF011 errorCheckProcessingBeforeRegistrationKAF011;
	
	@Inject
	private RecruitmentAppRepository recruitmentAppRepository;
	
	@Inject
	private AbsenceLeaveAppRepository absenceLeaveAppRepository;
	
	@Inject
	private RegisterAtApproveReflectionInfoService registerAtApproveReflectionInfoService;
	
	/**
	 * @name 登録する
	 */
	public void register(HolidayShipmentRefactor5Command command){
		String companyId = AppContexts.user().companyId();
		Optional<AbsenceLeaveApp> abs = Optional.ofNullable(command.existAbs() ? command.abs.toDomainInsert() : null);
		Optional<RecruitmentApp> rec = Optional.ofNullable(command.existRec() ? command.rec.toDomainInsert(): null);
		
		//登録前のエラーチェック処理(Xử lý error check trước khi đăng ký)
		this.errorCheckProcessingBeforeRegistrationKAF011.processing(
				companyId, 
				abs,
				rec, 
				command.represent, 
				command.displayInforWhenStarting.appDispInfoStartup.getAppDispInfoWithDateOutput().getOpErrorFlag(), 
				command.displayInforWhenStarting.appDispInfoStartup.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				command.displayInforWhenStarting.appDispInfoStartup.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0), 
				command.displayInforWhenStarting.appDispInfoStartup.getAppDispInfoWithDateOutput().getEmpHistImport().employmentCode, 
				command.displayInforWhenStarting.appDispInfoStartup.toDomain());
	}
	
	/**
	 * @name 振休振出申請（新規）登録処理 (Xử lý đăng ký application nghỉ bù làm bù (New))
	 * @param companyId 会社ID
	 * @param abs 振休申請
	 * @param rec 振出申請
	 * @param baseDate 基準日
	 * @param managementUnit 管理単位
	 * @param mailServerSet メールサーバ設定済区分
	 * @param approvalLst 承認ルートインスタンス
	 * @param substituteManagement 振休紐付け管理区分
	 * @param mailServerSet 振出_休出代休紐付け管理
	 * @param approvalLst 振休_休出代休紐付け管理
	 * @param substituteManagement 振休_振出振休紐付け管理
	 */
	public void registrationApplicationProcess(String companyId, Optional<AbsenceLeaveApp> abs, Optional<RecruitmentApp> rec, GeneralDate baseDate, Integer managementUnit, boolean mailServerSet, List<ApprovalPhaseStateForAppDto> approvalLst, Integer substituteManagement) {
		
		if(rec.isPresent() && abs.isPresent()) {
			//ドメイン「振出申請」を1件登録する(đăng ký 1 domain[đơn xin nghì bù])
			recruitmentAppRepository.insert(rec.get());
			//アルゴリズム「登録前共通処理（新規）」を実行する(Thực hiện thuật toán [xử lý chung trước khi đăng ký(new)])
			registerAtApproveReflectionInfoService.newScreenRegisterAtApproveInfoReflect(rec.get().getEmployeeID(), rec.get());
			
			
			
		}else if(rec.isPresent()){
			//振出申請の登録(đăng ký đơn xin làm bù)
		}else if(abs.isPresent()){
			//振休申請の登録(đăng ký đơn xin nghỉ bù)			
		}
	}
	
	/**
	 * @name メールを送信する(新規)Send an email (new)
	 */
	public void sendEmail() {}
}
