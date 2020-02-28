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
		
		List<ApprovalPersonReport> listDomain = repo.getListDomainByReportId(cid, reportId).stream()
				.filter(apr -> apr.getAprStatus().value == ApprovalStatus.Approved.value).collect(Collectors.toList());
		
		if (listDomain.isEmpty()) {
			return new ArrayList<>();
		}
		
		
		// check xem trong danh sach nguoi appro co nguoi đang login hay không(trả về list vì người login có thể được cài đặt apprơ cho nhiều phase)
		List<ApprovalPersonReport> domainLoginOpt = listDomain.stream().filter(dm -> dm.getAprSid().equals(sidLogin)).collect(Collectors.toList());
		
		if (domainLoginOpt.isEmpty()) {
			return new ArrayList<>();
		}
		
		Comparator<ApprovalPersonReport> comparator = Comparator.comparing( ApprovalPersonReport::getPhaseNum );
		int phaseMaxOfApprovalLogin = domainLoginOpt.stream().max(comparator).get().getPhaseNum();
		
		
		// lấy thông tin người làm đơn.
		Optional<RegistrationPersonReport> domainReport =  regisPersonReportRepo.getDomainByReportId( cid,  reportId);
		String bussinessNameApplication = ""; // tên người làm đơn
		if (domainReport.isPresent()) {
			bussinessNameApplication = domainReport.get().getAppBussinessName();
		}
		
		ApprovalPersonReportDto firstItemCombobox = ApprovalPersonReportDto.builder()
				.id(1)
				.reportID(listDomain.get(0).getReportID())
				.appSid(listDomain.get(0).getAppSid())
				.infoToDisplay("申請者：  " + bussinessNameApplication  ).build();
		result.add(firstItemCombobox);
		
		for (int i = 0; i < listDomain.size(); i++) {
			ApprovalPersonReport domain = listDomain.get(i);
			if ( (!sidLogin.equals(domain.getAprSid()) && domain.getPhaseNum() >= phaseMaxOfApprovalLogin)  ) {
				ApprovalPersonReportDto itemCombobox = ApprovalPersonReportDto.builder().id(i + 2).cid(cid) // 会社ID
						.reportID(domain.getReportID()) // 届出ID
						.phaseNum(domain.getPhaseNum())
						.aprNum(listDomain.get(i).getAprNum())
						.comment(domain.getComment() == null ? "" : listDomain.get(i).getComment().toString())
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
