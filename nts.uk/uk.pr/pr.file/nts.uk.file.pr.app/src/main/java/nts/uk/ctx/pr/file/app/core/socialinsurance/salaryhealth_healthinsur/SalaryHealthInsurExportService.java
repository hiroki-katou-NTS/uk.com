package nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth_healthinsur;


import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.HealthInsuStandardMonthlyFinder;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.SalaryHealthDto;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFee;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFeeRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthly;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthlyRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthExportData;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthExportQuery;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthFileGenerator;
import nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth.SalaryHealthRepository;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class SalaryHealthInsurExportService extends ExportService<SalaryHealthInsurExportQuery> {


	@Inject
	private HealthInsuStandardMonthlyFinder healthInsuStandardMonthlyFinder;
	@Inject
	private SalaryHealthInsurFileGenerator mSalaryHealthInsurFileGenerator;

	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<SalaryHealthInsurExportQuery> exportServiceContext) {
		SalaryHealthDto mSalaryHealthDto = healthInsuStandardMonthlyFinder.initScreen(exportServiceContext.getQuery().getTargetStartYm(),exportServiceContext.getQuery().getHisId(),false);
		mSalaryHealthInsurFileGenerator.generate(exportServiceContext.getGeneratorContext(),
				new SalaryHealthInsurExportData(mSalaryHealthDto.getCusDataDtos(),
						mSalaryHealthDto.getPremiumRate(),
						exportServiceContext.getQuery().getOfficeCode(),
						exportServiceContext.getQuery().getSocialInsuranceName(),
						exportServiceContext.getQuery().getDisplayStart(),
						exportServiceContext.getQuery().getDisplayEnd())
		);
	}
}
