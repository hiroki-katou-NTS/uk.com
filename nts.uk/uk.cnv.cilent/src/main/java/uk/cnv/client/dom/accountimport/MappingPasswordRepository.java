package uk.cnv.client.dom.accountimport;

import uk.cnv.client.infra.entity.JmKihon;

public interface MappingPasswordRepository {
	void insert(String hashedPassword, JmKihon employee, String userId);
}
