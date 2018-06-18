package nts.uk.ctx.exio.app.find.exi.csvimport;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.gul.text.StringUtil;
import nts.uk.ctx.exio.dom.exi.service.FileUtil;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class CsvImportDataFinder {
	@Inject
	private StoredFileStreamService fileStreamService;

	private static final int MAX_LENGTH_COLNAME = 40;

	public int getNumberOfLine(String fileId, int endcoding) {
		int totalRecord = 0;
		try {
			// get input stream by fileId
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileId);

			totalRecord = FileUtil.getNumberOfLine(inputStream, endcoding);
			inputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return totalRecord;
	}

	public List<CsvMappingDataDto> getRecordByIndex(String fileId, int dataLineNum, int startLine, int endcoding) {
		List<CsvMappingDataDto> result = new ArrayList<>();
		try {
			// get input stream by fileId
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileId);

			List<List<String>> data = FileUtil.getRecordByIndex(inputStream, dataLineNum, startLine, endcoding);
			inputStream.close();
			List<String> errorList = new ArrayList<>();
			for (int i = 0; i < data.size(); i++) {
				if (!StringUtil.isNullOrEmpty(data.get(i).get(0), true) &&
					StringUtil.lengthHalf(data.get(i).get(0)) > MAX_LENGTH_COLNAME) {
					errorList.add(TextResource.localize("Msg_1153", String.valueOf(i + 1)));
				}
				result.add(new CsvMappingDataDto(i + 1, data.get(i).get(0), data.get(i).get(1)));
			}
			if (!errorList.isEmpty()) {
				String headerError = TextResource.localize("Msg_1158");
				for (String e: errorList) {
					headerError = headerError + "\n\t" + e;
				}
				RawErrorMessage errorMsg = new RawErrorMessage(headerError);
				throw new BusinessException(errorMsg);
			}
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
			result = FileUtil.getRecord(inputStream, info.getColumns(), info.getIndex(), info.getEndCoding());
			inputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
