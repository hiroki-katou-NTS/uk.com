package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.google.common.base.Strings;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.TableColumnNameNativeQueryRepository;
@Stateless
public class JpaTableColumnNameNativeQueryRepositoryImpl extends JpaRepository implements TableColumnNameNativeQueryRepository {
	private static final String GET_COLUMN_QUERY = "select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = ?tableName";
	private static final String QUERY1 = "SELECT * FROM SSPMT_TABLE_LIST";
	@Override
	public List<String> getTableColumnName(String tableName) {
		return this.getEntityManager().createNativeQuery(GET_COLUMN_QUERY).setParameter("tableName", tableName).getResultList();
	}
}
