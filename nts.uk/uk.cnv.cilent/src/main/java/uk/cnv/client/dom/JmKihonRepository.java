package uk.cnv.client.dom;

import java.sql.SQLException;
import java.util.List;

import uk.cnv.client.infra.entity.JmKihon;

public interface JmKihonRepository {
	List<JmKihon> findAll() throws SQLException;
}
