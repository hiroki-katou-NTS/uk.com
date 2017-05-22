package nts.uk.pr.file.infra.payment;

import java.util.List;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

public class PaymentReportPostCardGenerator extends PaymentReportBaseGenerator implements PaymentGenerator {

	@Override
	public void generate(AsposeCellsReportContext context, PaymentReportData data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	int getItemWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	int getCategoryStartRow() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	void printPageContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void setPageHeaderRange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	List<CellValue> getHeaderTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void setTemplateStyle() {
		// TODO Auto-generated method stub
		
	}

}
