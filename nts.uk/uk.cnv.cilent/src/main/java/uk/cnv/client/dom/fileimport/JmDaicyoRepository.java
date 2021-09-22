package uk.cnv.client.dom.fileimport;

import java.sql.SQLException;
import java.util.List;

import uk.cnv.client.infra.entity.JmDaicyo;

public interface JmDaicyoRepository {

	List<JmDaicyo> getAll(int companyCode) throws SQLException;
}
