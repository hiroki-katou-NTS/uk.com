/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.payment;

import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.uk.file.pr.app.export.payment.data.PaymentReportData;
import nts.uk.file.pr.app.export.payment.data.dto.PaymentReportDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;

/**
 * The Class OneDemensionGenerator.
 */
@Stateless(name = "PaymentReportHorizontalGenerator")
public class PaymentReportHorizontalGenerator implements PaymentGenerator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.pr.file.infra.wagetable.Generator#generate(nts.uk.shr.infra.file.
	 * report.aspose.cells.AsposeCellsReportContext,
	 * nts.uk.ctx.pr.core.app.wagetable.command.WtUpdateCommand)
	 */
	@Override
	public void generate(AsposeCellsReportContext context, PaymentReportData data) {
		// Set worksheet name.
		Worksheet ws = context.getWorkbook().getWorksheets().get(0);
		ws.setName("PaymentService");

		// Set data.
		Cells cells = ws.getCells();

		int startRow = 0;
		int startCol = 5;
		cells.get(startRow, startCol).setValue("給与支給明細書");
		startRow++;
		startCol = 0;
		cells.get(startRow, startCol).setValue("部門コード");
		startCol++;
		cells.get(startRow, startCol).setValue("個人コード");
		startCol++;
		cells.get(startRow, startCol).setValue("氏名");

		List<PaymentReportDto> reportData = data.getReportData();
		PaymentReportDto dataFirst = reportData.get(0);
		startRow++;
		startCol = 0;
		cells.get(startRow, startCol).setValue(dataFirst.getDepartmentInfo().getDepartmentCode());
		startCol++;
		cells.get(startRow, startCol).setValue(dataFirst.getEmployeeInfo().getEmployeeCode());
		startCol++;
		cells.get(startRow, startCol).setValue(dataFirst.getEmployeeInfo().getEmployeeName());
		/*
		 * // Fill data. Map<String, Long> itemToAmountMap =
		 * history.getValueItems() .stream()
		 * .collect(Collectors.toMap(WtItemDto::getElement1Id,
		 * WtItemDto::getAmount)); int index = 0; for (ElementItemDto item :
		 * elementSettingDto.getItemList()) { cells.get(index + 1,
		 * 0).setValue(item.getDisplayName()); cells.get(index + 1,
		 * 1).setValue(itemToAmountMap.get(item.getUuid())); index ++; }
		 * 
		 * // Border set. Style style = cells.get(1, 0).getStyle();
		 * cells.createRange(1, 0, index, 2).setStyle(style);
		 */
	}
}
