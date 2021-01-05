package uk.cnv.client.infra.repository;

import lombok.val;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import uk.cnv.client.dom.fileimport.MapptingFileIdRepository;
import uk.cnv.client.infra.entity.JmKihon;
import uk.cnv.client.infra.entity.ScvmtMappingFileId;
import uk.cnv.client.infra.repository.base.UkCnvRepositoryBase;

public class JpaMappingFileIdRepository extends UkCnvRepositoryBase implements MapptingFileIdRepository {

	@Override
	public void insert(StoredFileInfo mapptingFile, JmKihon employee) {
		val entity = new ScvmtMappingFileId(
					mapptingFile.getId(),
					"PROFILE_PHOTO",
					employee.getPid(),
					employee.getCompanyCode(),
					employee.getEmployeeCode()
				);

		super.insert(entity);
	}


}
