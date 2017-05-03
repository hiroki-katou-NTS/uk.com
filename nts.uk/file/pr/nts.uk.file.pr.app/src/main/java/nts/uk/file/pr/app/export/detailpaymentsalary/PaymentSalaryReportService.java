/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryPrintSettingDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;
import nts.uk.file.pr.app.export.detailpaymentsalary.query.PaymentSalaryQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author duongnd
 *
 */
@Stateless
public class PaymentSalaryReportService extends ExportService<PaymentSalaryQuery> {

	/** The generator. */
	@Inject
	private PaymentSalaryInsuranceGenerator generator;

	/** The repository. */
	@Inject
	private PaymentSalaryReportRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
	 * .export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<PaymentSalaryQuery> context) {
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companycode by user login
		String companyCode = loginUserContext.companyCode();

		// get query
		PaymentSalaryQuery query = context.getQuery();

		if (this.repository.checkExport(companyCode, query)) {
			throw new BusinessException("ER010");
		}

		List<Integer> selectedLevels = Arrays.asList(1, 3, 6);
		PaymentSalaryReportData rawData = this.repository.exportPDFPaymentSalary(companyCode,
			query);

		PaymentSalaryPrintSettingDto configure = new PaymentSalaryPrintSettingDto();
		configure.setSelectedLevels(selectedLevels);
		rawData.setConfigure(configure);

		this.generator.generate(context.getGeneratorContext(), rawData);
	}

}
