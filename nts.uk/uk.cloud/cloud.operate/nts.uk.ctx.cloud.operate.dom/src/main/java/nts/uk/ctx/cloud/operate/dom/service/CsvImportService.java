package nts.uk.ctx.cloud.operate.dom.service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;

import lombok.val;
import nts.uk.ctx.cloud.operate.dom.csvimport.ICSVImportTemporaryFileFactory;

@Stateless
public class CsvImportService {

	@Resource(lookup="java:app/AppName")
	private String applicationName;

	@Inject
	private ICSVImportTemporaryFileFactory factory;

	public void execImport(
			Require require,
			String contractCode,
			java.nio.file.Path zipFile,
			List<String> fileList)
	{
    	val container = factory.createContainer(contractCode);

    	container.unzipHere(zipFile);
    	Path folder = container.getPath();

    	for(String sqlFile : fileList) {
    		Path path = folder.resolve(sqlFile);
    		String tableName = FilenameUtils.getBaseName(sqlFile);

    		require.execCopy(contractCode, tableName, path.toString().replace(File.separator, "/"));
    	}
	}

	public static interface Require {
		void execCopy(String contractCode, String tableName, String fileName);
	}
}
