package uk.cnv.client.dom.accountimport;

import java.sql.SQLException;

import uk.cnv.client.infra.entity.JmKihon;

public interface MappingPasswordRepository {
	void insert(String hashedPassword, JmKihon employee, String userId) throws SQLException;
	void truncateTable() throws SQLException;
}
