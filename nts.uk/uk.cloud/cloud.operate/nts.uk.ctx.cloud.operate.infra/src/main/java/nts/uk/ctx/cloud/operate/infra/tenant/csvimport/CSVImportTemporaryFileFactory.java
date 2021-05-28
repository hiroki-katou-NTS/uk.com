package nts.uk.ctx.cloud.operate.infra.tenant.csvimport;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Resource;
import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFilesContainer;
import nts.uk.ctx.cloud.operate.dom.csvimport.ICSVImportTemporaryFileFactory;
import nts.uk.ctx.cloud.operate.dom.system.property.UkCloudSystemProperties;

@Stateless
public class CSVImportTemporaryFileFactory extends JpaRepository implements ICSVImportTemporaryFileFactory {

	/**
	 * Application name: nts.uk.com.web, nts.uk.pr.web, ...
	 */
	@Resource(lookup="java:app/AppName")
	private String applicationName;

	@Override
	public ApplicationTemporaryFilesContainer createContainer(String contractCode) {

		Path tempContainer;
		tempContainer = this.prepareTempDir(contractCode);

		// delete when JVM terminates
		tempContainer.toFile().deleteOnExit();

		return new CSVImportTemporaryFilesContainer(tempContainer);
	}

	private Path prepareTempDir(String contractCode) {
		Path sharedFolderPath = UkCloudSystemProperties.sharedFolderPath();

		val applicationTempDir = sharedFolderPath.resolve(this.applicationName + "\\" + contractCode + "\\csv");
		if (Files.exists(applicationTempDir)) {
			deleteFolder(applicationTempDir.toFile());
		}

		try {
			Files.createDirectories(applicationTempDir);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return applicationTempDir;
	}

	private void deleteFolder(File targetFolder) {
		String[] list = targetFolder.list();
        for(String file : list) {
        	Path filePath = targetFolder.toPath().resolve(file);
            File f = filePath.toFile();
            if(f.isDirectory()) {
            	deleteFolder(f);
            }else {
                f.delete();
            }
        }
        targetFolder.delete();
	}

}
