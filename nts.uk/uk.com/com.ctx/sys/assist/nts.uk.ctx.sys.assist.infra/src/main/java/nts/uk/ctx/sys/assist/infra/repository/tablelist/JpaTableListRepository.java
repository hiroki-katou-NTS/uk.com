package nts.uk.ctx.sys.assist.infra.repository.tablelist;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.ctx.sys.assist.infra.entity.tablelist.SspmtTableList;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaTableListRepository extends JpaRepository implements TableListRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT t FROM SspmtTableList t";
	
	@Override
	public void add(TableList domain) {
		this.commandProxy().insert(SspmtTableList.toEntity(domain));
	}

	@Override
	public List<?> getAutoObject(String query, Class<?> type, boolean hasCid, boolean hasStartDate, boolean hasEndDate, GeneralDate startDate, GeneralDate endDate) {
		
		TypedQueryWrapper<?> queryWrapper = this.queryProxy().query(query, type);
		if (hasCid)
		{
			String companyId = AppContexts.user().companyId();
			queryWrapper = queryWrapper.setParameter("companyId", companyId);
		}
		if (hasStartDate)
		{
			queryWrapper = queryWrapper.setParameter("startDate", startDate);
		}
		if (hasEndDate)
		{
			queryWrapper = queryWrapper.setParameter("endDate", endDate);
		}
		return queryWrapper.getList();
	}

	@Override
	public Class<?> getTypeForTableName(String tableName) {
		for (EntityType<?> entityType : this.getEntityManager().getMetamodel().getEntities()) {
			Table table = entityType.getJavaType().getAnnotation(Table.class);
			if (table != null && table.name().equals(tableName)) {
				return entityType.getJavaType();
			}
		}
		return null;
	}

	@Override
	public String getFieldForColumnName(Class<?> tableType, String columnName) {
		for (Field field : tableType.getDeclaredFields()) {
			if (field.isAnnotationPresent(EmbeddedId.class)) {
				Class<?> pk = field.getType();
				for (Field fieldPk : pk.getDeclaredFields()) {
					Column columnPk = fieldPk.getDeclaredAnnotation(Column.class);
					if (columnPk != null && columnPk.name().equals(columnName)) {
						return field.getName() + "." + fieldPk.getName();
					}
				}
			}
			Column column = field.getDeclaredAnnotation(Column.class);
			if (column != null && column.name().equals(columnName)) {
				return field.getName();
			}
		}
		return null;
	}

	@Override
	public List<TableList> getAllTableList() {
		 return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspmtTableList.class)
        .getList(item -> item.toDomain());
	}

}
