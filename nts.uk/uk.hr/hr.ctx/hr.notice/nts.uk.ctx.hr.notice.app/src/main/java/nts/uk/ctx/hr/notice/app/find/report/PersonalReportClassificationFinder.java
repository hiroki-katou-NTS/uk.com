package nts.uk.ctx.hr.notice.app.find.report;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassification;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassificationRepository;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItem;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PersonalReportClassificationFinder {
	@Inject
	private PersonalReportClassificationRepository reportClsRepo;
	
	@Inject
	private RegisterPersonalReportItemRepository  itemReportClsRepo;
	
	public List<PersonalReportClassificationDto> getAllReportCls(boolean abolition){
		return this.reportClsRepo.getAllByCid(AppContexts.user().companyId(), abolition).stream().map(c -> {
			return PersonalReportClassificationDto.fromDomain(c);
		}).collect(Collectors.toList());
	}
	
	public PersonalReportClassificationDto getDetailReportCls(int reportClsId) {
		String cid = AppContexts.user().companyId();
		
		Optional<PersonalReportClassification> reportClsOpt = this.reportClsRepo
				.getDetailReportClsByReportClsID(reportClsId);
		
		List<RegisterPersonalReportItem> items = this.itemReportClsRepo.getAllItemBy(cid, reportClsId);
		
		
		return reportClsOpt.isPresent() == true ? PersonalReportClassificationDto.fromDomain(reportClsOpt.get())
				: new PersonalReportClassificationDto();
	}

}
