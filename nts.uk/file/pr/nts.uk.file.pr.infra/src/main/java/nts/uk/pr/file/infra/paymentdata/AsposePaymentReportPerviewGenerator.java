/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.paymentdata;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.payment.PaymentReportPreviewGenerator;
import nts.uk.file.pr.app.export.payment.PaymentReportPreviewQuery;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposePaymentReportPerviewGenerator.
 */
@Stateless
public class AsposePaymentReportPerviewGenerator extends AsposeCellsReportGenerator
	implements PaymentReportPreviewGenerator {

	/** The Constant PAGE_E. */
	private static final String PAGE_E = "E";

	/** The Constant PAGE_F. */
	private static final String PAGE_F = "F";

	/** The Constant PAGE_G. */
	private static final String PAGE_G = "G";

	/** The Constant TEMPLATE_PATH_E1. */
	private static final String TEMPLATE_PATH_E1 = "report/QPP021_E1.xlsx";

	/** The Constant TEMPLATE_PATH_F1. */
	private static final String TEMPLATE_PATH_F1 = "report/QPP021_F1.xlsx";

	/** The Constant TEMPLATE_PATH_G. */
	private static final String TEMPLATE_PATH_G = "report/QPP021_G.xlsx";

	/** The Constant OUTPUT_PDF_NAME. */
	private static final String OUTPUT_PDF_NAME = "賃金テープル.pdf";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.file.pr.app.export.payment.PaymentReportPerviewGenerator#generate(
	 * nts.arc.layer.infra.file.export.FileGeneratorContext)
	 */
	@Override
	public void generate(FileGeneratorContext fileContext, PaymentReportPreviewQuery query) {
		switch (query.getPageLayout()) {
		case PAGE_E:
			try (AsposeCellsReportContext ctx = this.createContext(TEMPLATE_PATH_E1)) {
				ctx.saveAsPdf(this.createNewFile(fileContext, this.getReportName(OUTPUT_PDF_NAME)));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			break;
		case PAGE_F:
			try (AsposeCellsReportContext ctx = this.createContext(TEMPLATE_PATH_F1)) {
				ctx.saveAsPdf(this.createNewFile(fileContext, this.getReportName(OUTPUT_PDF_NAME)));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			break;
		case PAGE_G:
			try (AsposeCellsReportContext ctx = this.createContext(TEMPLATE_PATH_G)) {
				ctx.saveAsPdf(this.createNewFile(fileContext, this.getReportName(OUTPUT_PDF_NAME)));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			break;

		default:
			break;
		}
	}

}
