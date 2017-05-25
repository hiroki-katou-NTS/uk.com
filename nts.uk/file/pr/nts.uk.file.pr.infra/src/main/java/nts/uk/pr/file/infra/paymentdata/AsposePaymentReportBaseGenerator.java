package nts.uk.pr.file.infra.paymentdata;

import javax.inject.Inject;

import nts.uk.file.pr.app.export.payment.PaymentReportRepository;
import nts.uk.pr.file.infra.payment.PaymentReportGeneratorFactory;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

public abstract class AsposePaymentReportBaseGenerator extends AsposeCellsReportGenerator{
	

	/** The Constant TEAMPLATE_ZFOLDED. */
	protected static final String TEAMPLATE_ZFOLDED = "report/QPP021_E2.xlsx";
	
	/** The Constant TEMPLATE_POSTCARD. */
	protected static final String TEMPLATE_POSTCARD = "report/QPP021_E3.xlsx";
	
	/** The Constant TEMPLATE_VERTICAL_ONE_PERSON. */
	protected static final String TEMPLATE_VERTICAL_ONE_PERSON = "report/QPP021_E1.xlsx";
	
	/** The Constant TEMPLATE_VERTICAL_TWO_PERSON. */
	protected static final String TEMPLATE_VERTICAL_TWO_PERSON = "report/QPP021_F1.xlsx";
	
	/** The Constant TEMPLATE_VERTICAL_THREE_PERSON. */
	protected static final String TEMPLATE_VERTICAL_THREE_PERSON = "report/QPP021_G.xlsx";
	
	/** The Constant TEMPLATE_HORIZONTAL_TWO_PERSON. */
	protected static final String TEMPLATE_HORIZONTAL_TWO_PERSON = "report/QPP021_F2.xlsx";

	/** The Constant OUTPUT_PDF_NAME. */
	protected static final String OUTPUT_PDF_NAME = "給与明細書.pdf";
	
	

	/** The factory. */
	@Inject
	protected PaymentReportGeneratorFactory factory;

	/** The repository. */
	@Inject
	protected PaymentReportRepository repository;


}
