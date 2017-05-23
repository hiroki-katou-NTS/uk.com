/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.List;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

public class PaymentReportVerticalThreeGenerator extends PaymentReportBaseGenerator implements PaymentGenerator {

	@Override
	public void generate(AsposeCellsReportContext context, PaymentReportData data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	int getNumberOfColumnPerItem() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	void printPageContent() {
		// TODO Auto-generated method stub
		
	}


	@Override
	List<CellValue> getHeaderTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	int getPersonPerPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	String getPageHeaderStartCell() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String getPageHeaderEndCell() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String getCategoryHeaderCell() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String getItemNameCell() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String getItemValueCell() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String getRemarkCell() {
		// TODO Auto-generated method stub
		return null;
	}

}
