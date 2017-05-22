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
	private static final String TEAMPLATE_ZFOLDED = "report/QPP021_A.xlsx";
	
	/** The Constant TEMPLATE_POSTCARD. */
	private static final String TEMPLATE_POSTCARD = "report/QPP021_A.xlsx";
	
	/** The Constant TEMPLATE_VERTICALONEPERSON. */
	private static final String TEMPLATE_VERTICAL_ONE_PERSON = "report/QPP021_B.xlsx";
	
	/** The Constant TEMPLATE_VERTICAL_TWO_PERSON. */
	private static final String TEMPLATE_VERTICAL_TWO_PERSON = "report/QPP021_A.xlsx";
	
	/** The Constant TEMPLATE_VERTICAL_THREE_PERSON. */
	private static final String TEMPLATE_VERTICAL_THREE_PERSON = "report/QPP021_A.xlsx";
	
	/** The Constant TEMPLATE_HORIZONTAL_TWO_PERSON. */
	private static final String TEMPLATE_HORIZONTAL_TWO_PERSON = "report/QPP021_A.xlsx";

	/** The Constant OUTPUT_PDF_NAME. */
	private static final String OUTPUT_PDF_NAME = "賃金テープル.pdf";

	// TODO UPDATE FILE NAME ?
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
		String teamplate = TEAMPLATE_ZFOLDED;
		
		switch (query.getLayoutItems()) {
		case 0:
			teamplate = TEMPLATE_VERTICAL_ONE_PERSON;
			break;
		case 1:
			teamplate = TEMPLATE_VERTICAL_TWO_PERSON;
			break;
		case 2:
			teamplate = TEMPLATE_VERTICAL_THREE_PERSON;
			break;
		case 3:
			teamplate = TEMPLATE_HORIZONTAL_TWO_PERSON;
			break;
		case 4:
			teamplate = TEAMPLATE_ZFOLDED;
			break;
		case 5:
			teamplate = TEMPLATE_POSTCARD;
			break;

		default:
			break;
		}		
		
		try (AsposeCellsReportContext ctx = this.createContext(teamplate)) {
			PaymentGenerator generator = this.factory.createGenerator(data);
			generator.generate(ctx, data);
			ctx.saveAsPdf(this.createNewFile(rptContext, this.getReportName(OUTPUT_PDF_NAME)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}