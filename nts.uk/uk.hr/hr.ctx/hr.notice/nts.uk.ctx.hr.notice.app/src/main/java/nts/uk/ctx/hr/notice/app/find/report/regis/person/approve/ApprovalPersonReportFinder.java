/**
 * 
 */
package nts.uk.ctx.hr.notice.app.find.report.regis.person.approve;

import java.util.ArrayList;
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
		List<ApprovalPersonReportDto> result = new ArrayList<>();
		
		List<ApprovalPersonReport> listDomain = repo.getListDomainByReportId(cid, String.valueOf(reportId)).stream()
				.filter(apr -> apr.getAprStatus().value == ApprovalStatus.Approved.value).collect(Collectors.toList());
		
		if (listDomain.isEmpty()) {
			return new ArrayList<>();
		}

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
		
		for (int i = 0; i < 10; i++) {
			ApprovalPersonReportDto itemCombobox = ApprovalPersonReportDto.builder()
					.id(i+2)
					.cid(cid) // 会社ID
					.reportID(listDomain.get(i).getReportID()) // 届出ID
					.phaseNum(listDomain.get(i).getPhaseNum())
					.aprNum(listDomain.get(i).getAprNum())
					.comment(listDomain.get(i).getComment() == null ? "" : listDomain.get(i).getComment().toString())
					.inputSid(listDomain.get(i).getInputSid())
					.appSid(listDomain.get(i).getAppSid())
					.aprSid(listDomain.get(i).getAprSid())
					.sendBackClass(listDomain.get(i).getSendBackClass().isPresent() ? listDomain.get(i).getSendBackClass().get().value : 2 )
					.infoToDisplay("フェーズ" + i + "の承認者" + i + ":  " + "aprBussinessName")
					.build();
			result.add(itemCombobox);
		}

		return result;

	}
}
