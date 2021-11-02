package uk.cnv.client.infra.repository;

import java.sql.SQLException;

import lombok.val;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import uk.cnv.client.dom.fileimport.MappingFileIdRepository;
import uk.cnv.client.infra.entity.JmKihon;
import uk.cnv.client.infra.entity.ScvmtMappingFileId;
import uk.cnv.client.infra.repository.base.UkCnvRepositoryBase;

public class MappingFileIdRepositoryImpl extends UkCnvRepositoryBase implements MappingFileIdRepository {

	@Override
	public void insert(StoredFileInfo mapptingFile, JmKihon employee, String FileType) throws SQLException {
		val entity = new ScvmtMappingFileId(
					mapptingFile.getId(),
					FileType,
					employee.getPid()
				);

		super.insert(entity);
	}

	@Override
	public void truncateTable() throws SQLException {
		super.truncateTable(new ScvmtMappingFileId());
	}


}
