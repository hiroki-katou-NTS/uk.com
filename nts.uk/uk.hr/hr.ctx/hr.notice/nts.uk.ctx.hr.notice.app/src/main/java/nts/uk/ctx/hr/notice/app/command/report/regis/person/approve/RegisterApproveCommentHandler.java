package nts.uk.ctx.hr.notice.app.command.report.regis.person.approve;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalActivity;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalStatus;
import nts.uk.ctx.hr.shared.dom.primitiveValue.String_Any_400;
import nts.uk.shr.com.context.AppContexts;
/**
 * 「登録処理」アルゴリズム
 * @author lanlt
 *
 */
@Stateless
public class RegisterApproveCommentHandler extends CommandHandler<ApproveReportCommand>{

	@Inject
	private ApprovalPersonReportRepository approvalPersonReportRepo;
	
	@Override
	protected void handle(CommandHandlerContext<ApproveReportCommand> context) {
		
		ApproveReportCommand cmd = context.getCommand();
		
		switch(cmd.getActionApprove().value) {
		
		 case 0:
			 
			 approveReport(cmd);
			 
			 break;
			 
		 case 1:
			 
			 denyReport(cmd);
			 
			 break;
			 
		 case 3:
			 
			 cancelReport(cmd);
			 
			 break;
		
		 case 4:
			 
			 registerReport(cmd);
			 
			 break;
			 
		default: break;
		
		}

	}
	
	/**
	 *登録処理
	 * @param cmd
	 */
	private void registerReport(ApproveReportCommand cmd) {
		
		//届出IDをキーに人事届出の承認
		List<ApprovalPersonReport> approvalPersonReports = this.approvalPersonReportRepo.getListDomainByReportId(cmd.getReportId());
		
		approvalPersonReports.stream().forEach(c ->{
			
			//「人事届出の承認.コメント」に更新する
			c.setComment(new String_Any_400(cmd.getApproveComment()));
			
		});
		
		this.approvalPersonReportRepo.addAll(approvalPersonReports);
	}
	
	/**
	 * 解除処理(Cancel)
	 * @param cmd
	 */
	private void cancelReport(ApproveReportCommand cmd) {
		
		String sid = AppContexts.user().employeeId();
		
		//承認者社員ID、届出IDをキーにドメイン「人事届出の承認」情報を取得する
		List<ApprovalPersonReport> approvalPersonReports = this.approvalPersonReportRepo.getListDomainByReportIdAndSid( cmd.getReportId(), sid);
	
		GeneralDateTime approveDay = GeneralDateTime.now();
		
		//ドメイン「人事届出の承認.コメント」に画面項目「承認コメント」の内容を登録する
		approvalPersonReports.stream().forEach(c ->{
			
			//人事届出の承認.承認日=now
			c.setAppDate(approveDay);
			
			//人事届出の承認.承認状況=未承認
			c.setAprStatusName(ApprovalStatus.Not_Acknowledged);
			
			//人事届出の承認.承認活性=活性
			c.setAprActivity(ApprovalActivity.Activity);
			
			//人事届出の承認.コメント=承認コメント
			c.setComment(new String_Any_400(cmd.getApproveComment()));
			
		});
	
		this.approvalPersonReportRepo.updateAll(approvalPersonReports);
		
		//【パラメータ】
		//ルートインスタンスID
		//社員ID=ログイン社員ID
		//承認ルートインスタンスを更新する([[RQ635]申請書を解除する]アルゴリズムを実行する)(Update approval route instance
		//(Thực hiện thuật toán [[RQ635] Cancel application form] ))
	
	}
	
	/**
	 * 否認する(Không phê duyệt)
	 * @param cmd
	 */
	private void denyReport(ApproveReportCommand cmd) {
		
		String sid = AppContexts.user().employeeId();
		
		//承認者社員ID、届出IDをキーにドメイン「人事届出の承認」情報を取得する
		List<ApprovalPersonReport> approvalPersonReports = this.approvalPersonReportRepo.getListDomainByReportIdAndSid( cmd.getReportId(), sid);
	
		GeneralDateTime approveDay = GeneralDateTime.now();
		
		//ドメイン「人事届出の承認.コメント」に画面項目「承認コメント」の内容を登録する
		approvalPersonReports.stream().forEach(c ->{
			
			//人事届出の承認.承認日=now
			c.setAppDate(approveDay);
			
			//人事届出の承認.承認状況=否認
			c.setAprStatusName(ApprovalStatus.Deny);
			
			//人事届出の承認.承認活性=否認済
			c.setAprActivity(ApprovalActivity.Deny);
			
			//人事届出の承認.コメント=承認コメント
			c.setComment(new String_Any_400(cmd.getApproveComment()));
			
		});
		
		//ドメイン「人事届出の承認」の各種属性を登録する
		this.approvalPersonReportRepo.updateAll(approvalPersonReports);
		
		//【パラメータ】
		//ルートインスタンスID
		//社員ID=ログイン社員ID
		//承認ルートインスタンスを更新する([[RQ633]申請書を否認する]アルゴリズムを実行する)
	}
	
	/**
	 * 承認する(Phê duyệt)
	 * @param cmd
	 */
	public void approveReport(ApproveReportCommand cmd) {

		String sid = AppContexts.user().employeeId();

		// 承認者社員ID、届出IDをキーにドメイン「人事届出の承認」情報を取得する
		List<ApprovalPersonReport> approvalPersonReports = this.approvalPersonReportRepo
				.getListDomainByReportIdAndSid(cmd.getReportId(), sid);
		
		GeneralDateTime approveDay = GeneralDateTime.now();
		
		//ドメイン「人事届出の承認.コメント」に画面項目「承認コメント」の内容を登録する
		approvalPersonReports.stream().forEach(c ->{
			
			//人事届出の承認.承認日=now
			c.setAppDate(approveDay);
			
			//人事届出の承認.承認状況=承認済
			c.setAprStatusName(ApprovalStatus.Approved);
			
			//人事届出の承認.承認活性=承認済
			c.setAprActivity(ApprovalActivity.Approved);
			
			//人事届出の承認.コメント=承認コメント
			c.setComment(new String_Any_400(cmd.getApproveComment()));
			
		});
		
		//ドメイン「人事届出の承認」の各種属性を登録する
		this.approvalPersonReportRepo.updateAll(approvalPersonReports);
		
		//【パラメータ】
		//ルートインスタンスID
		//承認者社員ID=ログイン社員ID
		//承認ルートインスタンスを更新する([[RQ632]申請書を承認する]アルゴリズムを実行する((Update approval route instance ( Thực hiện thuật toán ([[RQ632] Approve application)]))
		
		
		//アルゴリズム[[RQ631]申請書の承認者と状況を取得する。]を実行する(( Thực hiện thuật toán[[RQ631] Get status và approver of application.] )
	
	}
	
	/**
	 * アルゴリズム[届出分析データのカウント処理]を実行する(Thực hiện thuật toán[Xử lý count data phân tích report] )
	 *会社ID
	 *届出年月
	 *届出種類ID
	 *カウント大区分=1:届出
	 *カウント小区分=1:承認済
	 *
	 */
	private void countData(String cid, int reportDate, int reportLayoutId, int countClsBig, int countClsSmall) {
		
		
		
	}

}
