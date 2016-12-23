package nts.uk.pr.file.infra.paymentdata;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.aspose.cells.PageOrientationType;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;

import nts.arc.layer.infra.file.FileGenerator;
import nts.arc.layer.infra.file.FileGeneratorContext;
import nts.uk.pr.file.infra.paymentdata.result.DetailItemDto;
import nts.uk.pr.file.infra.paymentdata.result.LayoutMasterCategoryDto;
import nts.uk.pr.file.infra.paymentdata.result.LineDto;
import nts.uk.pr.file.infra.paymentdata.result.PaymentDataResult;

@Stateless
public class PaymentDataPrintFileGenerator extends FileGenerator {

	private final String fileName = "report/paymentdata.xlsx";

	@Override
	protected void generate(FileGeneratorContext context) {
		try {
			//get list PaymentDataResult
			List<PaymentDataResult> results = context.getParameterAt(0);
			// create workbook
			WorkbookDesigner designer = new WorkbookDesigner();
			OutputStream output = context.createOutputFileStream("paymentdata.pdf");
			Workbook workbook = null;
			try {
				workbook = new Workbook(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
			} catch (Exception e) {
				throw new RuntimeException("Report template not found");
			}
			designer.setWorkbook(workbook);
			//bind data
			bindingData(designer, results);
			// save to file
			designer.getWorkbook().save(output, SaveFormat.PDF);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bindingData(WorkbookDesigner designer, List<PaymentDataResult> results) throws Exception {
		for (int i = 0; i < results.size(); i++) {
			// bind header
			designer.setDataSource("PaymentDataHeader", results.get(i).getPaymentHeader());

			// bind categories data to datasource
			List<LayoutMasterCategoryDto> categories = results.get(i).getCategories();
			for (int j = 0; j < categories.size(); j++) {
				if (categories.get(j).getCategoryAttribute() == 0) {
					createValue(designer, categories.get(j), 1);
				} else if (categories.get(j).getCategoryAttribute() == 1) {
					createValue(designer, categories.get(j), 2);
				} else if (categories.get(j).getCategoryAttribute() == 2) {
					createValue(designer, categories.get(j), 3);
				} else if (categories.get(j).getCategoryAttribute() == 3) {
					createValue(designer, categories.get(j), 4);
				}
			}
			// bind processing Yearmonth
			String year = results.get(i).getPaymentHeader().getProcessingYM().toString().substring(0, 4);
			String month = results.get(i).getPaymentHeader().getProcessingYM().toString().substring(4);
			String processingYm = year + "年" + month + "月";
			designer.setDataSource("ProcessingYm", processingYm);
			// fit wide
			designer.getWorkbook().getWorksheets().get(i).getPageSetup().setFitToPagesWide(1);
			// set landscape
			designer.getWorkbook().getWorksheets().get(i).getPageSetup().setOrientation(PageOrientationType.LANDSCAPE);
			// add another page
			if (i < results.size() - 1) {
				designer.getWorkbook().getWorksheets().addCopy(i);
			}
			// approve process
			designer.process(i, true);
		}
	}

	// calculate lines and items position
	private void createValue(WorkbookDesigner designer, LayoutMasterCategoryDto category, int cateIndex) {
		List<LineDto> lines = category.getLines();
		for (LineDto line : lines) {
			int i = 0;
			for (DetailItemDto detailItem : line.getDetails()) {
				String dataSourceName = String.format("ItemNameCat%sLine%s_%s", cateIndex, line.getLinePosition(), i);
				String dataSourceValue = String.format("ItemValueCat%sLine%s_%s", cateIndex, line.getLinePosition(), i);
				designer.setDataSource(dataSourceName, detailItem.getItemName());
				if (detailItem.getValue() != null) {
					if (detailItem.getCategoryAtr() == 0 || detailItem.getCategoryAtr() == 3) {
						designer.setDataSource(dataSourceValue, detailItem.getValue().toString() + "¥");
					} else if (detailItem.getCategoryAtr() == 2) {
						int t = detailItem.getValue().intValue();
						int hours = t / 60;
						int minutes = t % 60;
						String timeFormat = String.format("%d:%02d", hours, minutes);
						designer.setDataSource(dataSourceValue, timeFormat);
					} else {
						designer.setDataSource(dataSourceValue, detailItem.getValue());
					}
				} else {
					designer.setDataSource(dataSourceValue, "");
				}
				i++;
			}

		}
	}

}
