package nts.uk.ctx.at.aggregation.dom.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalPageBreakCollection;
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

@Stateless
public class ExportQRCodeToExcel {

	public void printData(int setRow, int setCol, List<QRStampCardDto> stampCardDtos, int qrSize) throws Exception {
		int imageSize = 0;

		switch (qrSize) {

		// 大 40mm×40mm
		case 0: {
			imageSize = 151;
			break;
		}

		// 中 30mm×30mm
		case 1: {
			imageSize = 113;
			break;
		}

		// 小 20mm×20mm
		case 2: {
			imageSize = 75;
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
		for (QRStampCardDto stamp : stampCardDtos) {

			// The path where the image will get saved
			String path = "C:\\\\output\\picture/" + stamp.getCardNumber() + ".png";
			createQR(stamp.getCardNumber(), path, charset, hashMap, imageSize, imageSize);
		}

		Workbook workbook = new Workbook();
		Worksheet worksheet = workbook.getWorksheets().get(0);
		HorizontalPageBreakCollection pageBreaks = worksheet.getHorizontalPageBreaks();

		for (int i = 0; i < stampCardDtos.size(); i++) {
			int index = worksheet.getPictures().add(0, 1,
					"C:\\output\\picture\\" + stampCardDtos.get(i).getCardNumber() + ".png");

			// Get the picture and set its different attributes.
			Picture objPicture = worksheet.getPictures().get(index);

			int crCol = i % setCol;

			int crRow = (i / setCol) * 3;

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

			worksheet.getCells().get(crRow + 1, crCol).setValue(stampCardDtos.get(i).getEmployeeCode());
			worksheet.getCells().get(crRow + 2, crCol).setValue(stampCardDtos.get(i).getEmployeeName());

			// Adjust column width and row height
			objPicture.setPlacement(PlacementType.MOVE_AND_SIZE);
			objPicture.setLockAspectRatio(false);

			objPicture.setUpperLeftColumn(crCol);

			objPicture.setUpperLeftRow(crRow);
			objPicture.setTop(0);
			objPicture.setLeft(0);

			int firstRow = i * (setRow * 3);

			if (i >= 1) {
				pageBreaks.add(firstRow);
			}

		}

		// Save the output Excel file
		workbook.save("C:\\output\\A4_Output.xlsx");

		System.out.println("QR Code Generated!!! ");

	}

	// Function to create the QR code
	@SuppressWarnings("deprecation")
	public static void createQR(String data, String path, String charset,
			Map<EncodeHintType, ErrorCorrectionLevel> hashMap, int height, int width)
			throws WriterException, IOException {

		BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, width, height);

		MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
	}
}
