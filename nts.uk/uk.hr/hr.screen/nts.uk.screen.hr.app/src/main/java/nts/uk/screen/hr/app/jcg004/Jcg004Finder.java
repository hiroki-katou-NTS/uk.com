package nts.uk.screen.hr.app.jcg004;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.MenuApprovalSettingsService;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.service.DataBeforeReflectingPerInfoService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class Jcg004Finder {

	@Inject
	private ApprovalPersonReportRepository approvalPersonReportRepo;
	
	@Inject
	private MenuApprovalSettingsService menuApprovalSettingsService;
	
	@Inject
	private DataBeforeReflectingPerInfoService dataBeforeReflectingPerInfoService;
	
	public Jcg004Dto Start() {
		String cid = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		//アルゴリズム [社員IDから承認が必要な届出の有無を取得する] を実行する (Thực hiện thuật toán "Lấy co/khong co report can phai approve  từ employeeID" )
		boolean approvalOfApplication = approvalPersonReportRepo.checkExit(cid, employeeId);
		
		//アルゴリズム [業務承認するプログラムIDを取得する] を実行する (Thực hiện thuật toán "Lấy programe để phê duyệt nghiệp vụ")
		List<String> programIds = menuApprovalSettingsService.getProgramIdForBusinessApproval(cid);
		
		
		List<String> businessApprovalProgramIds = new ArrayList<>();
		if(!programIds.isEmpty()) {
			//アルゴリズム [業務承認の有無チェック] を実行する (Thực hiện thuật toán "Check có hoặc không có phê duyệt nghiệp vụ")
			businessApprovalProgramIds.addAll(dataBeforeReflectingPerInfoService.checkForBusinessApp(cid, employeeId));
		}
		
		List<JCG004BusinessApprovalDto> businessApproval = programIds.stream().map(c->{
			return new JCG004BusinessApprovalDto(c,businessApprovalProgramIds.contains(c));
		}).collect(Collectors.toList());
		
		return new Jcg004Dto(approvalOfApplication, businessApproval);
	}
}
