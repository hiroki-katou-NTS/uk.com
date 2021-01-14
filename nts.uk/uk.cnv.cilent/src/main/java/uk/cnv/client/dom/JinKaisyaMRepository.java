package uk.cnv.client.dom;

import java.sql.SQLException;
import java.util.List;

import uk.cnv.client.infra.entity.JinKaisyaM;

public interface JinKaisyaMRepository {
	List<JinKaisyaM> getAll() throws SQLException;
}
