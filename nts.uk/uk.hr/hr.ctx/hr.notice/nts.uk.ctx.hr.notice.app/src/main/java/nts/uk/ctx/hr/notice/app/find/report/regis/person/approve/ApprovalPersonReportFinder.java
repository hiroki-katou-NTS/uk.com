/**
 * 
 */
package nts.uk.ctx.hr.notice.app.find.report.regis.person.approve;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalStatus;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
public class ApprovalPersonReportFinder {

	@Inject
	private ApprovalPersonReportRepository repo;
	
	@Inject
	private RegistrationPersonReportRepository regisPersonReportRepo;

	/**
	 * @param reportId,
	 * @param cid
	 * @return
	 */
	public List<ApprovalPersonReportDto> getListDomain(int reportId) {
		String cid = AppContexts.user().companyId();
		String sidLogin = AppContexts.user().employeeId();
		
		List<ApprovalPersonReportDto> result = new ArrayList<>();
		
		// lay danh sach nguoi duoc seting appro cho don xin nay
		List<ApprovalPersonReport> listDomain = repo.getListDomainByReportId(cid, reportId);
		
		// lay danh sach nguoi đã appro cho don xin nay
		List<ApprovalPersonReport> listDomainApproved = repo.getListDomainByReportId(cid, reportId).stream()
				.filter(apr -> apr.getAprStatus().value == ApprovalStatus.Approved.value).collect(Collectors.toList());
		
		if (listDomainApproved.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<ApprovalPersonReport> domainAppro = listDomain.stream().filter(i -> i.getAprSid().equals(sidLogin)).collect(Collectors.toList());
		
		if (domainAppro.isEmpty()) {
			return new ArrayList<>();
		}
		
		int phaseMinOfApprovalLogin = domainAppro.stream().min(Comparator.comparing(ApprovalPersonReport::getPhaseNum)).get().getPhaseNum();
		
		
		// lấy thông tin người làm đơn.
		Optional<RegistrationPersonReport> domainReport =  regisPersonReportRepo.getDomainByReportId( cid,  reportId);
		String bussinessNameApplication = ""; // tên người làm đơn
		if (domainReport.isPresent()) {
			bussinessNameApplication = domainReport.get().getAppBussinessName();
		}
		
		ApprovalPersonReportDto firstItemCombobox = ApprovalPersonReportDto.builder()
				.id(1)
				.reportID(listDomainApproved.get(0).getReportID())
				.appSid(listDomainApproved.get(0).getAppSid())
				.infoToDisplay("申請者：  " + bussinessNameApplication  ).build();
		result.add(firstItemCombobox);
		
		for (int i = 0; i < listDomainApproved.size(); i++) {
			ApprovalPersonReport domain = listDomainApproved.get(i);
			if ( (!sidLogin.equals(domain.getAprSid()) && domain.getPhaseNum() >= phaseMinOfApprovalLogin)  ) {
				ApprovalPersonReportDto itemCombobox = ApprovalPersonReportDto.builder().id(i + 2).cid(cid) // 会社ID
						.reportID(domain.getReportID()) // 届出ID
						.phaseNum(domain.getPhaseNum())
						.aprNum(listDomainApproved.get(i).getAprNum())
						.comment(domain.getComment() == null ? "" : listDomainApproved.get(i).getComment().toString())
						.inputSid(domain.getInputSid())
						.appSid(domain.getAppSid())
						.aprSid(domain.getAprSid())
						.sendBackClass(domain.getSendBackClass().isPresent() ? domain.getSendBackClass().get().value : 2)
						.infoToDisplay("フェーズ" + domain.getPhaseNum() + "の承認者" + domain.getAprNum() + ":  " + domain.getAprBussinessName())
						.build();
				result.add(itemCombobox);
			}
		}

		return result;

	}
}
