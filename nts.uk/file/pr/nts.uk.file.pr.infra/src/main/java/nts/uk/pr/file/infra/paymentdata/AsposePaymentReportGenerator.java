/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.paymentdata;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.payment.PaymentReportGenerator;
import nts.uk.file.pr.app.export.payment.PaymentReportQuery;
import nts.uk.file.pr.app.export.payment.PaymentReportRepository;
import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.pr.file.infra.payment.PaymentGenerator;
import nts.uk.pr.file.infra.payment.PaymentReportGeneratorFactory;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposePaymentReportGenerator.
 */
@Stateless
public class AsposePaymentReportGenerator extends AsposeCellsReportGenerator
	implements PaymentReportGenerator {

	/** The Constant TEMPLATE_PATH_A. */
	private static final String TEMPLATE_PATH_A = "report/QPP021_A.xlsx";

	/** The Constant TEMPLATE_PATH_B. */
	private static final String TEMPLATE_PATH_B = "report/QPP021_B.xlsx";

	/** The Constant OUTPUT_PDF_NAME. */
	private static final String OUTPUT_PDF_NAME = "賃金テープル.pdf";

	/** The factory. */
	@Inject
	private PaymentReportGeneratorFactory factory;

	/** The repository. */
	@Inject
	private PaymentReportRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.file.pr.app.export.payment.PaymentReportGenerator#generate(nts.arc
	 * .layer.app.file.export.ExportServiceContext)
	 */
	@Override
	public void generate(ExportServiceContext<PaymentReportQuery> context) {
		FileGeneratorContext rptContext = context.getGeneratorContext();
		PaymentReportQuery query = context.getQuery();
		PaymentReportData data = this.repository.findData(query);
		String teamplate = TEMPLATE_PATH_A;
		if (query.getPageOrientation().equals("PORTRAIT")) {
			teamplate = TEMPLATE_PATH_B;
		}
		try (AsposeCellsReportContext ctx = this.createContext(teamplate)) {
			PaymentGenerator generator = this.factory.createGenerator(query.getPageOrientation(),
				data);
			generator.generate(ctx, data);
			ctx.saveAsPdf(this.createNewFile(rptContext, this.getReportName(OUTPUT_PDF_NAME)));
			//ctx.saveAsExcel(this.createNewFile(rptContext, this.getReportName(OUTPUT_PDF_NAME)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}