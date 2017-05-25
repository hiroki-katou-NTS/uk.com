/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.paymentdata;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.payment.PaymentReportPreviewGenerator;
import nts.uk.file.pr.app.export.payment.PaymentReportPreviewQuery;
import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.pr.file.infra.payment.PaymentGenerator;
import nts.uk.pr.file.infra.payment.PaymentReportPreviewData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

/**
 * The Class AsposePaymentReportPerviewGenerator.
 */
@Stateless
public class AsposePaymentReportPerviewGenerator extends AsposePaymentReportBaseGenerator
	implements PaymentReportPreviewGenerator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.file.pr.app.export.payment.PaymentReportPerviewGenerator#generate(
	 * nts.arc.layer.infra.file.export.FileGeneratorContext)
	 */
	@Override
	public void generate(FileGeneratorContext fileContext, PaymentReportPreviewQuery query) {
		List<PaymentReportDto> reportData = new ArrayList<>();
		
		String teamplate = TEAMPLATE_ZFOLDED;

		switch (query.getPageLayout()) {
		case 0:
			teamplate = TEMPLATE_VERTICAL_ONE_PERSON;
			reportData =  PaymentReportPreviewData.getVerticalOnePreviewData();
			break;
		case 1:
			teamplate = TEMPLATE_VERTICAL_TWO_PERSON;
			reportData =  PaymentReportPreviewData.getVerticalTwoPreviewData();
			break;
		case 2:
			teamplate = TEMPLATE_VERTICAL_THREE_PERSON;
			reportData =  PaymentReportPreviewData.getVerticalThreePreviewData();
			break;
		case 3:
			teamplate = TEMPLATE_HORIZONTAL_TWO_PERSON;
			reportData =  PaymentReportPreviewData.getHorizontalTwoPreviewData();
			break;
		case 4:
			teamplate = TEAMPLATE_ZFOLDED;
			reportData =  PaymentReportPreviewData.getZFoldedPreviewData();
			break;
		case 5:
			teamplate = TEMPLATE_POSTCARD;
			reportData =  PaymentReportPreviewData.getPostCardPreviewData();
			break;
		default:
			break;
		}
		try (AsposeCellsReportContext ctx = this.createContext(teamplate)) {
			PaymentReportData data = new PaymentReportData();
			data.setLayoutItem(query.getPageLayout());
			data.setReportData(reportData);
			data.setConfig(query.toDto());
			PaymentGenerator generator = this.factory.createGenerator(data);
			generator.generate(ctx, data);
			ctx.saveAsPdf(this.createNewFile(fileContext, this.getReportName(OUTPUT_PDF_NAME)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
