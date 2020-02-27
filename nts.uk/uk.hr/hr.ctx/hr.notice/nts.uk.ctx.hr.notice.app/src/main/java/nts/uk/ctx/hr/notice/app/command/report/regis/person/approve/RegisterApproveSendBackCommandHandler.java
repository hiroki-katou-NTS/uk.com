/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person.approve;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalActivity;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalStatus;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.SendBackClass;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.ApproveRepository;
import nts.uk.ctx.hr.shared.dom.primitiveValue.String_Any_400;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
public class RegisterApproveSendBackCommandHandler extends CommandHandler<ApproveReportSendBackCommand> {

	@Inject
	private ApprovalPersonReportRepository repoApproval;
	
	@Inject 
	private RegistrationPersonReportRepository registrationPersonReportRepo;
	
	@Inject
	private RegisterApproveHandler registerApproveHandler;
	
	@Inject
	private ApproveRepository approveRepository;
	

	// アルゴリズム「差し戻し処理」を実行する(Thực hiện thuật toán"Xử lý return" )
	@Override
	protected void handle(CommandHandlerContext<ApproveReportSendBackCommand> context) {
		ApproveReportSendBackCommand command = context.getCommand();
		String approverEmpID = AppContexts.user().employeeId();
		String cid = AppContexts.user().companyId();
		Integer reprtId = command.getReportID();
		
		// 承認者社員ID、届出IDをキーにドメイン「人事届出の承認」情報を取得する(Lấy thông tin của domain 「人事届出の承認/phê duyệt HR report」với key là Approver employee ID, report ID)
		List<ApprovalPersonReport> listDomain =  repoApproval.getListDomainByReportIdAndSid(cid, reprtId, approverEmpID);

		if (listDomain.isEmpty()) {
			return;
		}
		
		listDomain.forEach(dm -> {
			dm.setAprDate(GeneralDateTime.now());
			dm.setAprStatus(ApprovalStatus.Send_Back);
			dm.setAprActivity(ApprovalActivity.Activity);
			dm.setComment(new String_Any_400(command.comment == null || command.comment == "" ? null : command.comment));
			dm.setSendBackClass(command.sendBackClass == 2 ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(command.sendBackClass, SendBackClass.class)));
			dm.setSendBackSID(Optional.of(command.sendBackSID));
		}) ;
		
		// ドメイン「人事届出の承認」の各種属性を登録する(Đăng ký các thuộc tính khác nhau của domain "Approval of HR report")
		repoApproval.updateSendBack(listDomain, reprtId,  approverEmpID  );
		
		// ドメイン「人事届出の登録」 を更新する
		registrationPersonReportRepo.updateAfterSendBack(cid, reprtId, command.sendBackSID, command.comment);
		
		
		if (command.selectedReturn == 1) {
			// アルゴリズム [申請書を差し戻しする（申請本人まで）] を実行する
			this.approveRepository.remandForApplicantHr(listDomain.get(0).getRootSatteId());
		} else {
			// アルゴリズム [申請書を差し戻しする（承認者まで）] を実行する
			this.approveRepository.remandForApproverHr(listDomain.get(0).getRootSatteId(), command.phaseNum);
		}
		
		
		Optional<RegistrationPersonReport> regisPersonReportOpt =  this.registrationPersonReportRepo.getDomainByReportId( cid, reprtId );
		
		if (regisPersonReportOpt.isPresent()) {
			
			// アルゴリズム[届出分析データのカウント処理]を実行する (Thực hiện thuật toán [Xử lý count dữ liệu phân tích report])
			String[] monthSplit = java.time.YearMonth.now().toString().split("-");

			int yearMonth = Integer.valueOf(monthSplit[0] + monthSplit[1]).intValue();
			registerApproveHandler.countData(cid, yearMonth, regisPersonReportOpt.get().getReportLayoutID(), 2, 2);
		}
		
	}

}
