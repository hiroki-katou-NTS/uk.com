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

	/** The Constant PAGE_E1. */
	private static final String PAGE_E1 = "E1";

	/** The Constant PAGE_E2. */
	private static final String PAGE_E2 = "E2";

	/** The Constant PAGE_E3. */
	private static final String PAGE_E3 = "E3";

	/** The Constant PAGE_F. */
	private static final String PAGE_F1 = "F1";

	/** The Constant PAGE_F2. */
	private static final String PAGE_F2 = "F2";

	/** The Constant PAGE_G. */
	private static final String PAGE_G = "G";

	/** The Constant TEMPLATE_PATH_PREVIEW_E1. */
	private static final String TEMPLATE_PATH_PREVIEW_E1 = "report/QPP021_PE1.xlsx";
	
	/** The Constant TEMPLATE_PATH_PREVIEW_E2. */
	private static final String TEMPLATE_PATH_PREVIEW_E2 = "report/QPP021_PE2.xlsx";
	
	/** The Constant TEMPLATE_PATH_PREVIEW_E3. */
	private static final String TEMPLATE_PATH_PREVIEW_E3 = "report/QPP021_PE3.xlsx";
	
	/** The Constant TEMPLATE_PATH_PREVIEW_F1. */
	private static final String TEMPLATE_PATH_PREVIEW_F1 = "report/QPP021_PF1.xlsx";
	
	/** The Constant TEMPLATE_PATH_PREVIEW_E3. */
	private static final String TEMPLATE_PATH_PREVIEW_F2 = "report/QPP021_PF2.xlsx";
	
	/** The Constant TEMPLATE_PATH_PREVIEW_G. */
	private static final String TEMPLATE_PATH_PREVIEW_G = "report/QPP021_PG.xlsx";

	/** The Constant OUTPUT_PDF_NAME. */
	private static final String OUTPUT_PDF_NAME = "給与明細書.pdf";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.file.pr.app.export.payment.PaymentReportPerviewGenerator#generate(
	 * nts.arc.layer.infra.file.export.FileGeneratorContext)
	 */
	@Override
	public void generate(FileGeneratorContext fileContext, PaymentReportPreviewQuery query) {
		try {
			AsposeCellsReportContext ctx = null;
			switch (query.getPageLayout()) {
			case PAGE_E1:
				ctx = this.createContext(TEMPLATE_PATH_PREVIEW_E1);
				ctx.saveAsPdf(this.createNewFile(fileContext, this.getReportName(OUTPUT_PDF_NAME)));
				break;
			case PAGE_E2:
				ctx = this.createContext(TEMPLATE_PATH_PREVIEW_E2);
				ctx.saveAsPdf(this.createNewFile(fileContext, this.getReportName(OUTPUT_PDF_NAME)));
				break;
			case PAGE_E3:
				ctx = this.createContext(TEMPLATE_PATH_PREVIEW_E3);
				ctx.saveAsPdf(this.createNewFile(fileContext, this.getReportName(OUTPUT_PDF_NAME)));
				break;
			case PAGE_F1:
				ctx = this.createContext(TEMPLATE_PATH_PREVIEW_F1);
				ctx.saveAsPdf(this.createNewFile(fileContext, this.getReportName(OUTPUT_PDF_NAME)));
				break;
			case PAGE_F2:
				ctx = this.createContext(TEMPLATE_PATH_PREVIEW_F2);
				ctx.saveAsPdf(this.createNewFile(fileContext, this.getReportName(OUTPUT_PDF_NAME)));
				break;
			case PAGE_G:
				ctx = this.createContext(TEMPLATE_PATH_PREVIEW_G);
				ctx.saveAsPdf(this.createNewFile(fileContext, this.getReportName(OUTPUT_PDF_NAME)));
				break;
				
			default:
				break;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
