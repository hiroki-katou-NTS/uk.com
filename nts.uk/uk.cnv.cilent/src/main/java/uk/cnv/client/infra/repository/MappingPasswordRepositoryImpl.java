package uk.cnv.client.infra.repository;

import lombok.val;
import uk.cnv.client.dom.accountimport.MappingPasswordRepository;
import uk.cnv.client.infra.entity.JmKihon;
import uk.cnv.client.infra.entity.ScvmtMappingPassword;
import uk.cnv.client.infra.repository.base.UkCnvRepositoryBase;

public class MappingPasswordRepositoryImpl extends UkCnvRepositoryBase implements MappingPasswordRepository {

	@Override
	public void insert(String hashedPassword, JmKihon employee, String userId) {
		val entity = new ScvmtMappingPassword(
				employee.getPid(),
				employee.getCompanyCode(),
				employee.getEmployeeCode(),
				userId,
				hashedPassword);

		super.insert(entity);
	}

}
