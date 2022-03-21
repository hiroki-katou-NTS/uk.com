package nts.uk.file.at.infra.statement.stamp;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.aspose.cells.Cell;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.PageSetup;
import com.aspose.cells.PaperSizeType;
import com.aspose.cells.Picture;
import com.aspose.cells.PlacementType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.system.ServerSystemProperties;
import nts.uk.file.at.app.export.statement.stamp.GetExtractedEmployeeCardNo;
import nts.uk.file.at.app.export.statement.stamp.GetExtractedEmployeeCardNoInput;
import nts.uk.file.at.app.export.statement.stamp.IExportQRCodeToExcel;
import nts.uk.file.at.app.export.statement.stamp.QRStampCardDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class ExportQRCodeToExcel extends AsposeCellsReportGenerator implements IExportQRCodeToExcel {

	private static final String FILE_NAME = "report/KMP001J.xlsx";
	private static final String REPORT_FILE_EXTENSION = ".xlsx";

	@Inject
	private GetExtractedEmployeeCardNo getCardNo;

	// Function to create the QR code
	@SuppressWarnings("deprecation")
	public static void createQR(String data, String path, String charset,
			Map<EncodeHintType, ErrorCorrectionLevel> hashMap, int height, int width)
			throws WriterException, IOException {

		BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, width, height);

		MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
	}

	@Override
	public void export(FileGeneratorContext generatorContext, GetExtractedEmployeeCardNoInput input) {
		int imageSize = 0;
		String space = "";
		int qrSize = input.getQrSize();

		switch (qrSize) {

		// 大 40mm×40mm
		case 0: {
			imageSize = 165;
			space = "             ";
			break;
		}

		// 中 30mm×30mm
		case 1: {
			imageSize = 130;
			space = "         ";
			break;
		}

		// 小 20mm×20mm
		case 2: {
			imageSize = 90;
			space = "      ";
			break;
		}

		}

		// Encoding charset
		String charset = "UTF-8";

		Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();

		hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		// Create the QR code and save
		// in the specified folder
		// as a png file

		List<QRStampCardDto> stampCardDtos = getCardNo.get(input);

		for (QRStampCardDto stamp : stampCardDtos) {

			// The path where the image will get saved
			String path = ServerSystemProperties.fileStoragePath() + "\\" + stamp.getCardNumber() + ".png";
			try {
				createQR(stamp.getCardNumber(), path, charset, hashMap, imageSize, imageSize);
			} catch (WriterException | IOException e) {
				e.printStackTrace();
			}
		}

		try (AsposeCellsReportContext reportContext = this.createContext(FILE_NAME)) {
			Workbook workbook = reportContext.getWorkbook();
			Worksheet worksheet = workbook.getWorksheets().get(0);
			HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();
			
			// Setting the paper size to A4
			PageSetup pageSetup = worksheet.getPageSetup();
			pageSetup.setPaperSize(PaperSizeType.PAPER_A_4);
			
			pageSetup.setHeaderMargin(0.8);
			pageSetup.setFooterMargin(0.8);
			
			pageSetup.setBottomMargin(1.9);
			pageSetup.setTopMargin(1.9);
			
			pageSetup.setLeftMargin(1.8);
			pageSetup.setRightMargin(1.8);
			

			for (int i = 0; i < stampCardDtos.size(); i++) {
				int index;
				index = worksheet.getPictures().add(0, 1,
						ServerSystemProperties.fileStoragePath() + "\\" + stampCardDtos.get(i).getCardNumber() + ".png");

				// Get the picture and set its different attributes.
				Picture objPicture = worksheet.getPictures().get(index);

				int crCol = i % input.getSetCol();

				int crRow = (i / input.getSetCol()) * 3;

				Cell cell = worksheet.getCells().get(crRow, crCol);

				// Adjust column width and row height
				worksheet.getCells().setColumnWidthPixel(cell.getColumn(), objPicture.getWidth());
				worksheet.getCells().setRowHeightPixel(cell.getRow(), objPicture.getHeight());

				// Set text
				// Adjust style of text
				Style style = cell.getStyle();
				Font font = style.getFont();
				font.setSize(8);
				cell.setStyle(style);
				style.setHorizontalAlignment(TextAlignmentType.LEFT);

				worksheet.getCells().setStyle(style);

				worksheet.getCells().get(crRow + 1, crCol).setValue(space + stampCardDtos.get(i).getEmployeeCode());
				worksheet.getCells().get(crRow + 2, crCol).setValue(space + stampCardDtos.get(i).getEmployeeName());

				// Adjust column width and row height
				objPicture.setPlacement(PlacementType.MOVE_AND_SIZE);
				objPicture.setLockAspectRatio(false);

				objPicture.setUpperLeftColumn(crCol);

				objPicture.setUpperLeftRow(crRow);
				objPicture.setTop(0);
				objPicture.setLeft(0);

				int firstRow = i * (input.getSetRow() * 3);

				if (i >= 1) {
					pageBreaks.add(firstRow);
				}

			}
			
			reportContext.getDesigner().setWorkbook(workbook);
			reportContext.processDesigner();
			reportContext.saveAsExcel(this.createNewFile(generatorContext,
					"KMP001_" + "QRコード出力" + "_"
							+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.JAPAN))
							+ REPORT_FILE_EXTENSION));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
