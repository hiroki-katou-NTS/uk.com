package nts.uk.ctx.hr.notice.app.command.report.regis.person.approve;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EnumType;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportAnalysis;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportAnalysisRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportItem;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ReportItemRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalActivity;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalStatus;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.PreReflectAnyData;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.PreReflectAnyItem;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.PreReflectAnyService;
import nts.uk.ctx.hr.shared.dom.primitiveValue.String_Any_400;
import nts.uk.shr.com.context.AppContexts;
/**
 * 「登録処理」アルゴリズム
 * @author lanlt
 *
 */
@Stateless
public class RegisterApproveHandler extends CommandHandler<ApproveReportCommand>{

	@Inject
	private ApprovalPersonReportRepository approvalPersonReportRepo;
	
	@Inject
	private RegistrationPersonReportRepository regisPersonReportRepo;
	
	@Inject
	private ReportAnalysisRepository reportAnalysisRepo;
	
	@Inject
	private ReportItemRepository reportItemRepo;
	
	@Inject
	private PreReflectAnyService preReflectAnyService;
	
	@Override
	protected void handle(CommandHandlerContext<ApproveReportCommand> context) {
		
		ApproveReportCommand cmd = context.getCommand();
		
		switch(cmd.getActionApprove()) {
		
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
		List<ApprovalPersonReport> approvalPersonReports = this.approvalPersonReportRepo.getListDomainByReportId(Integer.valueOf(cmd.getReportId()));
		
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
		List<ApprovalPersonReport> approvalPersonReports = this.approvalPersonReportRepo.getListDomainByReportIdAndSid( Integer.valueOf(cmd.getReportId()), sid);
	
		GeneralDateTime approveDay = GeneralDateTime.now();
		
		//ドメイン「人事届出の承認.コメント」に画面項目「承認コメント」の内容を登録する
		approvalPersonReports.stream().forEach(c ->{
			
			//人事届出の承認.承認日=now
			c.setAppDate(approveDay);
			
			//人事届出の承認.承認状況=未承認
			c.setAprStatus(ApprovalStatus.Not_Acknowledged);
			
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
		List<ApprovalPersonReport> approvalPersonReports = this.approvalPersonReportRepo.getListDomainByReportIdAndSid( Integer.valueOf(cmd.getReportId()), sid);
	
		GeneralDateTime approveDay = GeneralDateTime.now();
		
		//ドメイン「人事届出の承認.コメント」に画面項目「承認コメント」の内容を登録する
		approvalPersonReports.stream().forEach(c ->{
			
			//人事届出の承認.承認日=now
			c.setAppDate(approveDay);
			
			//人事届出の承認.承認状況=否認
			c.setAprStatus(ApprovalStatus.Deny);
			
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
		
		String cid = AppContexts.user().companyId();

		// 承認者社員ID、届出IDをキーにドメイン「人事届出の承認」情報を取得する
		List<ApprovalPersonReport> approvalPersonReports = this.approvalPersonReportRepo
				.getListDomainByReportIdAndSid(Integer.valueOf(cmd.getReportId()).intValue(), sid);
		
		GeneralDateTime approveDay = GeneralDateTime.now();
		
		//ドメイン「人事届出の承認.コメント」に画面項目「承認コメント」の内容を登録する
		approvalPersonReports.stream().forEach(c ->{
			
			//人事届出の承認.承認日=now
			c.setAppDate(approveDay);
			
			//人事届出の承認.承認状況=承認済
			c.setAprStatus(ApprovalStatus.Approved);
			
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
		
		
		//アルゴリズム[届出分析データのカウント処理]を実行する(Thực hiện thuật toán[Xử lý count data phân tích report] )
		
		Optional<RegistrationPersonReport> regisPersonReportOpt =  this.regisPersonReportRepo.getDomainByReportId(cid, Integer.valueOf(cmd.getReportId()));
		
		if(regisPersonReportOpt.isPresent()) {
			
			String[] monthSplit =  java.time.YearMonth.now().toString().split("-");
			
			int yearMonth  = Integer.valueOf(monthSplit[0] + monthSplit[1]).intValue();
			
			countData( cid, 1, regisPersonReportOpt.get().getReportLayoutID(), 1, yearMonth);
			
		}

		//ドメイン[人事届出の登録]、[届出の項目]のリストを取得して、反映前データに届出情報を取り込む(アルゴリズム[届出データの追加]を実行する)(Lấy list của domain [人事届出の登録]、[届出の項目], import thông tin report vào data trước khi phản ánh (Thực hiện thuật toán [届出データの追加/thêm data report] ))
		preprareReflectData(cid, Integer.valueOf(cmd.getReportId()));
		
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
		
		Optional<ReportAnalysis>  reportAnalysisOpt = this.reportAnalysisRepo.getListReportAnalysis(cid, reportLayoutId,  countClsBig,  countClsSmall, reportDate);
		
		if(reportAnalysisOpt.isPresent()) {
			
			int reportCount = reportAnalysisOpt.get().getReportCount()  + 1;
			
			reportAnalysisOpt.get().setReportCount(reportCount);
			
			this.reportAnalysisRepo.update(reportAnalysisOpt.get());
			
		}
		
	}
	
	/**
	 * ドメイン[人事届出の登録]、[届出の項目]のリストを取得して
	 * 、反映前データに届出情報を取り込む(アルゴリズム[届出データの追加]を実行する)
	 * @param cid
	 * @param reportId
	 */
	private void preprareReflectData(String cid, Integer reportId) {
		
		Optional<RegistrationPersonReport> regisPersonReportOpt =  this.regisPersonReportRepo.getDomainByReportId(cid, reportId);
		
		if(regisPersonReportOpt.isPresent()) {
			
			List<PreReflectAnyItem> anyItems = new ArrayList<>();
			
			RegistrationPersonReport regisReport = regisPersonReportOpt.get();
			
			String newHistId = IdentifierUtil.randomUniqueId();
			
			PreReflectAnyData anyData = new PreReflectAnyData(newHistId,
					cid,
					AppContexts.user().companyCode(),
					regisReport.getWorkId(),
					regisReport.getReportType().value,
					regisReport.getInputDate(),
					1,
					regisReport.getAppPid(),
					regisReport.getAppSid(),
					regisReport.getAppScd(),
					regisReport.getAppBussinessName(),
					regisReport.getReportID(),
					regisReport.getReportCode(),
					regisReport.getReportName(),
					regisReport.getInputDate()
					);
			
			List<ReportItem> reportItems = this.reportItemRepo.getDetailReport(cid, reportId.intValue());
			
			
			if(!CollectionUtil.isEmpty(reportItems)) {
				
				String itemHistId = IdentifierUtil.randomUniqueId();
				
				anyItems.addAll(reportItems.stream().map(c ->{
					
					return new PreReflectAnyItem(itemHistId,
							newHistId,
							cid,
							c.getReportID(),
							c.getDspOrder(),
							c.getCategoryId(),
							c.getCtgCode(),
							c.getItemId(),
							c.getItemCd(),
							c.getSaveDataAtr(),
							c.getStringVal(),
							c.getIntVal(),
							c.getDateVal());
					
				}).collect(Collectors.toList()));
				
			}
			
			preReflectAnyService.preprareReflectData(anyData, anyItems);
			
		}
		
	}

}
