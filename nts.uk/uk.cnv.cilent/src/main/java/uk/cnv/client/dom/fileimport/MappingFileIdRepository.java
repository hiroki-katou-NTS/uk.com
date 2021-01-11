package uk.cnv.client.dom.fileimport;

import nts.arc.layer.app.file.storage.StoredFileInfo;
import uk.cnv.client.infra.entity.JmKihon;

public interface MappingFileIdRepository {
	void insert(StoredFileInfo mapptingFile, JmKihon employee, String type);
}
