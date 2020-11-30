package nts.uk.ctx.sys.assist.app.find.autosetting;

import java.io.File;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.system.ServerSystemProperties;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StorageFreeSpaceFinder {
	
	private static final long GB_SIZE = 1024 * 1024 * 1024;

	public double getFreeSpace() {
		File file = new File(ServerSystemProperties.fileStoragePath());
		return file.getFreeSpace()/GB_SIZE;
	}
}
