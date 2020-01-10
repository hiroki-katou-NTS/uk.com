package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.shr.com.context.AppContexts;


	
import nts.uk.ctx.hr.notice.app.find.report.PersonalReportClassificationDto;
import nts.uk.ctx.hr.notice.app.find.report.PersonalReportClassificationFinder;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class RegistrationPersonReportFinder {
	
	@Inject
	private RegistrationPersonReportRepository repo;
	
	@Inject
	private PersonalReportClassificationFinder reportClsFinder;
	
	// lay ra danh sach report hien thi trong gird  owr manf JHN001.A
	public List<PersonalReportDto> getListReport(String sid) {

		List<PersonalReportDto> result = new ArrayList<PersonalReportDto>();

		List<RegistrationPersonReport> listReport = repo.getListBySIds(sid);

		List<PersonalReportClassificationDto> listReportJhn011 = this.reportClsFinder.getAllReportCls(true);

		if (!listReportJhn011.isEmpty()) {
			listReportJhn011.forEach(rp -> {
				PersonalReportDto dto = new PersonalReportDto();
				dto.setClsDto(rp);
				if (!listReport.isEmpty()) {
					Optional<RegistrationPersonReport> report = listReport.stream().filter(rpt -> rpt.getReportLayoutID() == rp.getReportClsId()).findFirst();
					if (report.isPresent()) {
						dto.setReportID(report.get().getReportID());
						dto.setSendBackComment(report.get().getSendBackComment());
					}
				}else{
					dto.setReportID(null);
					dto.setSendBackComment("");
				}
				result.add(dto);
			});
			return result;
		}
		return new ArrayList<>();
	};

	public List<RegistrationPersonReportDto> findPersonReport(PersonReportQuery query) {

		String cId = AppContexts.user().companyId();

		String sId = AppContexts.user().employeeId();
		
		List<RegistrationPersonReport> regisList = this.repo.findByJHN003(cId, sId, query.getAppDate().getStartDate(),
				query.getAppDate().getEndDate(), query.getReportId(), query.getApprovalStatus(), query.getInputName(),
				query.isApprovalReport());

		if ((regisList.size() > 99 && query.isApprovalReport())
				|| (regisList.size() > 999 && !query.isApprovalReport())) {
			throw new BusinessException("Msgj_46");
		}

		return regisList
				.stream().map(x -> RegistrationPersonReportDto.fromDomain(x, query.isApprovalReport()))
				.sorted(Comparator.comparing(RegistrationPersonReportDto::getInputDate)).collect(Collectors.toList());
	}
	
	
	public List<RegistrationPersonReport> getListReportSaveDraft(String sid) {
		List<RegistrationPersonReport> result = repo.getListReportSaveDraft(sid);
		return result;
	};
	
	RegistrationPersonReport getDomain(String cid, Integer reportLayoutID){
		Optional<RegistrationPersonReport> result = repo.getDomain(cid, reportLayoutID);
		return result.isPresent() ? result.get() : null;
	};
	
	
}
