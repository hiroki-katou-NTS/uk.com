/**
 * 
 */
package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

		List<PersonalReportClassificationDto> listReportJhn011 = this.reportClsFinder.getAllReportCls(false);

		if (!listReportJhn011.isEmpty()) {
			listReportJhn011.forEach(rp -> {
				PersonalReportDto dto = new PersonalReportDto();
				dto.setClsDto(rp);
				if (!listReport.isEmpty()) {
					Optional<RegistrationPersonReport> report = listReport.stream().filter(rpt -> rpt.getReportLayoutID() == rp.getReportClsId()).findFirst();
					if (report.isPresent()) {
						dto.setReportID(report.get().getReportID());
						dto.setSendBackComment(report.get().getSendBackComment());
						dto.setRootSateId(report.get().getRootSateId());
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
	
	
	public List<RegistrationPersonReport> getListReportSaveDraft(String sid) {
		List<RegistrationPersonReport> result = repo.getListReportSaveDraft(sid);
		return result;
	};
	
	RegistrationPersonReport getDomain(String cid, Integer reportLayoutID){
		Optional<RegistrationPersonReport> result = repo.getDomain(cid, reportLayoutID);
		return result.isPresent() ? result.get() : null;
	};
	
	
}
