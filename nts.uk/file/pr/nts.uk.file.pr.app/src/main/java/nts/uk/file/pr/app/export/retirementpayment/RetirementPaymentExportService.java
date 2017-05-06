package nts.uk.file.pr.app.export.retirementpayment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.pr.app.export.retirementpayment.data.CompanyMasterDto;
import nts.uk.file.pr.app.export.retirementpayment.data.PersonalBasicDto;
import nts.uk.file.pr.app.export.retirementpayment.data.RetirePayItemDto;
import nts.uk.file.pr.app.export.retirementpayment.data.RetirementPaymentDto;
import nts.uk.file.pr.app.export.retirementpayment.data.RetirementPaymentReportData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RetirementPaymentExportService extends ExportService<RetirementPaymentQuery> {

	@Inject
	private RetirementPaymentRepository retirementPaymentRepository;

	@Inject
	private RetirementPaymentReportGenerator retirementPaymentReportGenerator;

	@Override
	protected void handle(ExportServiceContext<RetirementPaymentQuery> context) {
		List<String> lstPersonId = context.getQuery().getLstPersonId();
		String companyCode = AppContexts.user().companyCode();
		List<RetirementPaymentReportData> dataSource = new ArrayList<>();
		List<RetirePayItemDto> lstRetirePayItemDto = this.retirementPaymentRepository.getListRetirePayItem(companyCode);
		//build report datasource
		for (String personId : lstPersonId) {
			RetirementPaymentReportData retirementPaymentReportData = new RetirementPaymentReportData();
			//get dtos
			Optional<RetirementPaymentDto> retirementPaymentDto = this.retirementPaymentRepository.getRetirementPayment(
					companyCode, personId, context.getQuery().getStartDate(), context.getQuery().getEndDate());
			PersonalBasicDto personalBasicDto = new PersonalBasicDto("従業員名 " + personId.charAt(35), "2005年 3月 1日", "2017年 12月 31日", "自己都合（転職）", "0年0ヶ 月", "0年0ヶ 月");
			CompanyMasterDto companyMasterDto = new CompanyMasterDto("日通システム株式会社", "愛知県名古屋市中区栄", "ナディアパークビジネスセンタービル９F");
			//set dtos to report data
			if(retirementPaymentDto.isPresent()){
				retirementPaymentReportData.setRetirementPaymentDto(retirementPaymentDto.get());
			}
			retirementPaymentReportData.setCompanyMasterDto(companyMasterDto);
			retirementPaymentReportData.setPersonalBasicDto(personalBasicDto);
			//add to list
			dataSource.add(retirementPaymentReportData);
		}
		this.retirementPaymentReportGenerator.generate(context.getGeneratorContext(), dataSource, lstRetirePayItemDto);
	}

}
