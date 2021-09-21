package nts.uk.ctx.cloud.operate.app.command;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.cloud.operate.dom.csvimport.CsvImportRepository;
import nts.uk.ctx.cloud.operate.dom.service.CsvImportService;

@Stateless
@Transactional
public class CSVImportCommandHandler extends CommandHandler<CSVImportCommand> {

	@Inject
	CsvImportService service;

	@Inject
	CsvImportRepository repo;

	@Override
	protected void handle(CommandHandlerContext<CSVImportCommand> context) {
		Map<String, List<InputPart>> formDataMap = context.getCommand().getFormDataMap();

		String contractCode = formDataMap.get("contractCode").stream()
				.map(inputPart -> getBody(String.class, inputPart))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);

		String[] fileList =
				formDataMap.get("filenamelist").stream()
				.map(inputPart -> getBody(String.class, inputPart))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new)
				.split(",");

		java.nio.file.Path zipFile = formDataMap.get("userfile").stream()
				.map(inputPart -> getBody(File.class, inputPart).toPath())
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);

		val require = new CsvImportServiceRequireImpl(repo);

		service.execImport(require, contractCode, zipFile, Arrays.asList(fileList));
	}

	private <T> T getBody(Class<T> t, InputPart inputPart) {
		T result = null;
		try {
			result = inputPart.getBody(t, null);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private class CsvImportServiceRequireImpl implements CsvImportService.Require {
		private CsvImportRepository repo;

		public CsvImportServiceRequireImpl(CsvImportRepository repo) {
			this.repo = repo;
		}

		@Override
		public void execCopy(String contractCode, String tableName, String fileName) {
			String tempTableName = tableName + "_TMP";
			repo.createTempTable(tempTableName, tableName);
			repo.exec(contractCode, tempTableName, fileName);
			repo.upsert(tempTableName, tableName);
		}

	}
}
