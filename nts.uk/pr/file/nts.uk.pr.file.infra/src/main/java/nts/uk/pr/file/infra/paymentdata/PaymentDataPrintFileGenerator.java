package nts.uk.pr.file.infra.paymentdata;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;

import nts.arc.layer.infra.file.FileGenerator;
import nts.arc.layer.infra.file.FileGeneratorContext;
import nts.uk.pr.file.infra.paymentdata.result.DetailItemDto;
import nts.uk.pr.file.infra.paymentdata.result.LayoutMasterCategoryDto;
import nts.uk.pr.file.infra.paymentdata.result.LineDto;
import nts.uk.pr.file.infra.paymentdata.result.PaymentDataResult;

@RequestScoped
public class PaymentDataPrintFileGenerator extends FileGenerator {

	private final String fileName = "report/paymentdata.xlsx";

	@Override
	protected void generate(FileGeneratorContext context) {
		try {
			PaymentDataResult result = context.getParameterAt(0);
			OutputStream output = context.createOutputFileStream("paymentdata.xlsx");
			WorkbookDesigner designer = new WorkbookDesigner();
			Workbook workbook = null;
			try {
				workbook = new Workbook(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
			} catch (Exception e) {
				throw new RuntimeException("Report template not found");
			}
			designer.setWorkbook(workbook);
			designer.setDataSource("PaymentDataHeader", result.getPaymentHeader());
			List<LayoutMasterCategoryDto> category1 = result.getCategories().stream()
					.filter(x -> x.getCategoryAttribute() == 0).collect(Collectors.toList());
			List<LayoutMasterCategoryDto> category2 = result.getCategories().stream()
					.filter(x -> x.getCategoryAttribute() == 1).collect(Collectors.toList());
			List<LayoutMasterCategoryDto> category3 = result.getCategories().stream()
					.filter(x -> x.getCategoryAttribute() == 2).collect(Collectors.toList());
			List<LayoutMasterCategoryDto> category4 = result.getCategories().stream()
					.filter(x -> x.getCategoryAttribute() == 3).collect(Collectors.toList());
			//bind data to datasource
			createValue(designer, category1.get(0), 1);
			createValue(designer, category2.get(0), 2);
			createValue(designer, category3.get(0), 3);
			createValue(designer, category4.get(0), 4);
			//bind processing Yearmonth
			String year = result.getPaymentHeader().getProcessingYM().toString().substring(0, 4);
			String month = result.getPaymentHeader().getProcessingYM().toString().substring(4);
			String processingYm = year + "年" + month + "月";
			designer.setDataSource("ProcessingYm", processingYm);
			designer.process(true);
			designer.getWorkbook().save(output, SaveFormat.XLSX);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
						int hours = t/60;
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
