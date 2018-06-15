package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.TableColumnNameNativeQueryRepository;
@Stateless
public class JpaTableColumnNameNativeQueryRepositoryImpl extends JpaRepository implements TableColumnNameNativeQueryRepository {
	private static final String GET_COLUMN_QUERY = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS where TABLE_NAME= :tableName";
	@Override
	public List<String> getTableColumnName(String tableName) {
		List<Object> listResult = this.getEntityManager().createNativeQuery(
				GET_COLUMN_QUERY, Objects[].class).setParameter("tableName", tableName).getResultList();
		List<String> tableColumn = new ArrayList<>();
		if(!listResult.isEmpty()){
			tableColumn = (List<String>) listResult.get(0);
		}
		return tableColumn;
	}
}
