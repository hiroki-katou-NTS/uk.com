package uk.cnv.client.infra.repository;

import java.sql.SQLException;

import lombok.val;
import uk.cnv.client.infra.entity.JmKihon;
import uk.cnv.client.infra.entity.ScvmtMappingPassword;
import uk.cnv.client.infra.repository.base.UkCnvRepositoryBase;

public class MappingPasswordRepositoryImpl extends UkCnvRepositoryBase {

	public void insert(String hashedPassword, JmKihon employee, String userId) throws SQLException {
		val entity = new ScvmtMappingPassword(
				employee.getPid(),
				employee.getCompanyCode(),
				employee.getEmployeeCode(),
				userId,
				hashedPassword);

		super.insert(entity);
	}

}
