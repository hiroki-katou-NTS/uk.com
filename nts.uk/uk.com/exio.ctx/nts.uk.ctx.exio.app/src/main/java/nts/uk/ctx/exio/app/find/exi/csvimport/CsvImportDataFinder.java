package nts.uk.ctx.exio.app.find.exi.csvimport;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.uk.ctx.exio.dom.exi.service.FileUtil;

@Stateless
public class CsvImportDataFinder {
	@Inject
	private StoredFileStreamService fileStreamService;

	public int getNumberOfLine(String fileId) {
		int totalRecord = 0;
		try {
			// get input stream by fileId
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileId);

			totalRecord = FileUtil.getNumberOfLine(inputStream);
			inputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return totalRecord;
	}

	public List<String> getRecordByIndex(String fileId, int numOfCol, int index) {
		List<String> result;
		try {
			// get input stream by fileId
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileId);

			result = FileUtil.getRecordByIndex(inputStream, numOfCol, index);
			inputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public List<String> getRecord(GettingCsvDataDto info) {
		List<String> result;
		try {
			// get input stream by fileId
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(info.getFileId());
			result = FileUtil.getRecord(inputStream, info.getColumns(), info.getIndex());
			inputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
