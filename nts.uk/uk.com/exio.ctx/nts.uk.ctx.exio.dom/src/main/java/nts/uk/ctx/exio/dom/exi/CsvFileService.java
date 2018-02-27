package nts.uk.ctx.exio.dom.exi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.file.storage.StoredFileStreamService;

@Stateless
public class CsvFileService {
	@Inject
	private StoredFileStreamService fileStreamService;
	
	public int getTotalRecordCsv(String fileId) throws IOException {
		InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileId);
		Scanner sc = new Scanner(inputStream, "UTF-8");
		int count = 0;

		while (sc.hasNextLine()) {
			sc.nextLine();
			count++;
		}

		if (inputStream != null) {
			inputStream.close();
		}
		if (sc != null) {
			sc.close();
		}
		return count;
	}
}
