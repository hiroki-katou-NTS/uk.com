package nts.uk.ctx.cloud.operate.dom.service;

import java.nio.file.Path;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;

import lombok.val;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;

@Stateless
public class CsvImportService {

	@Resource(lookup="java:app/AppName")
	private String applicationName;

	@Inject
	private ApplicationTemporaryFileFactory factory;

	public void execImport(
			Require require,
			String contractCode,
			java.nio.file.Path zipFile,
			List<String> fileList)
	{
    	val container = factory.createContainer();

    	container.unzipHere(zipFile);

    	for(String sqlFile : fileList) {
    		Path path = container.getPath().resolve(sqlFile);
    		String tableName = FilenameUtils.getBaseName(sqlFile);

    		require.execCopy(contractCode, tableName, path.toAbsolutePath().toString());
    	}
	}

	public static interface Require {
		void execCopy(String contractCode, String tableName, String fileName);
	}
}
