package nts.uk.ctx.sys.assist.infra.repository.tablelist;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;

import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.queries.DatabaseQuery;

import com.google.common.base.Strings;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.uk.ctx.sys.assist.dom.categoryfieldmt.HistoryDiviSion;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.ctx.sys.assist.infra.entity.tablelist.SspmtTableList;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class JpaTableListRepository extends JpaRepository implements TableListRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT t FROM SspmtTableList t WHERE t.tableListPk.dataStorageProcessingId =:storeProcessingId";
	private static final String COMPANY_CD = "0";
	private static final String EMPLOYEE_CD = "5";
	private static final String YEAR = "6";
	private static final String YEAR_MONTH = "7";
	private static final String YEAR_MONTH_DAY = "8";

	@Override
	public void add(TableList domain) {
		this.commandProxy().insert(SspmtTableList.toEntity(domain));
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
		for (Field field : tableType.getSuperclass().getDeclaredFields()) {
			Column column = field.getDeclaredAnnotation(Column.class);
			if (column != null && column.name().equals(columnName)) {			
				return field.getName();
			}
		}
		
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
		return "";
	}

	@Override
	public List<TableList> getByOffsetAndNumber(String storeProcessingId, int offset, int number) {
		List<SspmtTableList> listTable = this.getEntityManager()
				.createQuery(SELECT_ALL_QUERY_STRING, SspmtTableList.class)
				.setParameter("storeProcessingId", storeProcessingId).setFirstResult(offset).setMaxResults(number)
				.getResultList();

		return listTable.stream().map(item -> item.toDomain()).collect(Collectors.toList());
	}

	@Override
	public List<TableList> getByProcessingId(String storeProcessingId) {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspmtTableList.class)
				.setParameter("storeProcessingId", storeProcessingId).getList(c -> c.toDomain());
	}

	@Override
	public List<?> getDataDynamic(TableList tableList, Class<?> tableExport ) {
		StringBuffer query = new StringBuffer("");

		// Select
		query.append("SELECT t");

		// From
		query.append(" FROM ").append(tableExport.getSimpleName()).append(" t");
		Class<?> tableParent = null;
		if (tableList.getHasParentTblFlg() == NotUseAtr.USE && tableList.getParentTblName().isPresent()) {
			tableParent = this.getTypeForTableName(tableList.getParentTblName().get());
			// アルゴリズム「親テーブルをJOINする」を実行する
			query.append(" INNER JOIN ").append(tableParent.getSimpleName()).append(" p ON ");

			String[] parentFields = { tableList.getFieldParent1().orElse(""), tableList.getFieldParent2().orElse(""),
					tableList.getFieldParent3().orElse(""), tableList.getFieldParent4().orElse(""), tableList.getFieldParent5().orElse(""),
					tableList.getFieldParent6().orElse(""), tableList.getFieldParent7().orElse(""), tableList.getFieldParent8().orElse(""),
					tableList.getFieldParent9().orElse(""), tableList.getFieldParent10().orElse("") };

			String[] childFields = { tableList.getFieldChild1().orElse(""), tableList.getFieldChild2().orElse(""), tableList.getFieldChild3().orElse(""),
					tableList.getFieldChild4().orElse(""), tableList.getFieldChild5().orElse(""), tableList.getFieldChild6().orElse(""),
					tableList.getFieldChild7().orElse(""), tableList.getFieldChild8().orElse(""), tableList.getFieldChild9().orElse(""),
					tableList.getFieldChild10().orElse("") };

			boolean isFirstOnStatement = true;
			for (int i = 0; i < parentFields.length; i++) {
				String onStatement = getOnStatement(tableParent, parentFields[i], tableExport, childFields[i]);
				if (!Strings.isNullOrEmpty(onStatement)) {
					if (!isFirstOnStatement) {
						query.append(" AND ");
					}
					isFirstOnStatement = false;
					query.append(onStatement);
				}
			}
		}

		String[] fieldKeyQuerys = { tableList.getFieldKeyQuery1().orElse(""), tableList.getFieldKeyQuery2().orElse(""),
				tableList.getFieldKeyQuery3().orElse(""), tableList.getFieldKeyQuery4().orElse(""), tableList.getFieldKeyQuery5().orElse(""),
				tableList.getFieldKeyQuery6().orElse(""), tableList.getFieldKeyQuery7().orElse(""), tableList.getFieldKeyQuery8().orElse(""),
				tableList.getFieldKeyQuery9().orElse(""), tableList.getFieldKeyQuery10().orElse("") };
		String[] clsKeyQuerys = { tableList.getClsKeyQuery1().orElse(""), tableList.getClsKeyQuery2().orElse(""), tableList.getClsKeyQuery3().orElse(""),
				tableList.getClsKeyQuery4().orElse(""), tableList.getClsKeyQuery5().orElse(""), tableList.getClsKeyQuery6().orElse(""),
				tableList.getClsKeyQuery7().orElse(""), tableList.getClsKeyQuery8().orElse(""), tableList.getClsKeyQuery9().orElse(""),
				tableList.getClsKeyQuery10().orElse("") };

		for (int i = 0; i < clsKeyQuerys.length; i++) {
			if (clsKeyQuerys[i] == EMPLOYEE_CD) {
				if (tableList.getHasParentTblFlg() == NotUseAtr.USE) {
					query.append(" INNER JOIN SspmtTargetEmployees e ON e.targetEmployeesPk.Sid = p.");
					query.append(this.getFieldForColumnName(tableParent, fieldKeyQuerys[i]));
				} else {
					query.append(" INNER JOIN SspmtTargetEmployees e ON e.targetEmployeesPk.Sid = t.");
					query.append(this.getFieldForColumnName(tableExport, fieldKeyQuerys[i]));
				}
			}
		}

		// Where
		query.append(" WHERE 1 = 1 ");

		List<Integer> companyCdIndexs = new ArrayList<Integer>();
		List<Integer> yearIndexs = new ArrayList<Integer>();
		List<Integer> yearMonthIndexs = new ArrayList<Integer>();
		List<Integer> yearMonthDayIndexs = new ArrayList<Integer>();
		for (int i = 0; i < clsKeyQuerys.length; i++) {
			if (clsKeyQuerys[i] == null)
				continue;
			switch (clsKeyQuerys[i]) {
			case COMPANY_CD:
				companyCdIndexs.add(i);
				break;

			case YEAR:
				yearIndexs.add(i);
				break;

			case YEAR_MONTH:
				yearMonthIndexs.add(i);
				break;

			case YEAR_MONTH_DAY:
				yearMonthDayIndexs.add(i);
				break;

			default:
				break;
			}
		}
		List<Object> params = new ArrayList<>();
		if (companyCdIndexs.size() > 0) {
			query.append(" AND ( ");
			boolean isFirstOrStatement = true;
			for (Integer index : companyCdIndexs) {
				if (!isFirstOrStatement) {
					query.append(" OR ");
				}
				isFirstOrStatement = false;
				String companyCdField = this.getFieldForColumnName(tableExport, fieldKeyQuerys[index]);
				if (!Strings.isNullOrEmpty(companyCdField)) {
					query.append(" t.");
					query.append(companyCdField);
				} else {
					query.append(" p.");
					query.append(this.getFieldForColumnName(tableParent, fieldKeyQuerys[index]));
				}
				query.append(" = :companyId ");
				params.add(AppContexts.user().companyId());
			}
			query.append(" ) ");
		}

		// 履歴区分を判別する。履歴なしの場合
		if (tableList.getHistoryCls() == HistoryDiviSion.NO_HISTORY) {
			List<Integer> indexs = new ArrayList<Integer>();
			switch (tableList.getRetentionPeriodCls()) {
			case ANNUAL:
				indexs = yearIndexs;
				break;
			case MONTHLY:
				indexs = yearMonthIndexs;
				break;
			case DAILY:
				indexs = yearMonthDayIndexs;
				break;

			default:
				break;
			}

			if (indexs.size() > 0) {
				query.append(" AND ( ");
				boolean isFirstOrStatement = true;
				for (Integer index : indexs) {
					if (!isFirstOrStatement) {
						query.append(" OR ");
					}
					isFirstOrStatement = false;
					String startDateField = this.getFieldForColumnName(tableExport, fieldKeyQuerys[index]);
					if (!Strings.isNullOrEmpty(startDateField)) {
						query.append(" (t.");
						query.append(startDateField);
					} else {
						query.append(" (p.");
						query.append(this.getFieldForColumnName(tableParent, fieldKeyQuerys[index]));
					}

					query.append(" >= :startDate ");
					query.append(" AND ");

					String endDateField = this.getFieldForColumnName(tableExport, fieldKeyQuerys[index]);
					if (!Strings.isNullOrEmpty(endDateField)) {
						query.append(" t.");
						query.append(endDateField);
					} else {
						query.append(" p.");
						query.append(this.getFieldForColumnName(tableParent, fieldKeyQuerys[index]));
					}

					query.append(" <= :endDate) ");

					switch (tableList.getRetentionPeriodCls()) {
					case DAILY:
						params.add(tableList.getSaveDateFrom().get());
						params.add(tableList.getSaveDateTo().get());
						break;
					case MONTHLY:
						params.add(Integer.valueOf(tableList.getSaveDateFrom().get().replaceAll("\\/", "")));
						params.add(Integer.valueOf(tableList.getSaveDateTo().get().replaceAll("\\/", "")));
						break;
					case ANNUAL:
						params.add(Integer.valueOf(tableList.getSaveDateFrom().get()));
						params.add(Integer.valueOf(tableList.getSaveDateTo().get()));
						break;

					default:
						break;
					}
				}
				query.append(" ) ");
			}
		}

		// 抽出条件キー固定
		String extractCondKeyFix = tableList.getDefaultCondKeyQuery() == null ? "" : tableList.getDefaultCondKeyQuery().orElse("");

		TypedQueryWrapper<?> queryWrapper = this.queryProxy().query(query.toString(), tableExport);
		DatabaseQuery databaseQuery = queryWrapper.getQuery().unwrap(EJBQueryImpl.class).getDatabaseQuery();
		StringBuffer sql = new StringBuffer(databaseQuery.getSQLString() + " " + extractCondKeyFix);
		int paramIndex = 1;
		char[] chs = sql.toString().toCharArray();
		for (int i = 0; i < chs.length; i++) {
			if (chs[i] == '?') {
				sql.insert(i + paramIndex, paramIndex);
				paramIndex++;
			}
		}

		Query queryString = getEntityManager().createNativeQuery(sql.toString(), tableExport);
		queryString.setParameter(1, 1);
		queryString.setParameter(2, 1);
		for (int i = 0; i < params.size(); i++) {
			queryString.setParameter(3 + i, params.get(i));
		}

		return queryString.getResultList();
	}

	private String getOnStatement(Class<?> parentTable, String parentColumn, Class<?> childTable, String childColumn) {
		if (!Strings.isNullOrEmpty(parentColumn) && !Strings.isNullOrEmpty(childColumn)) {
			String parentColumnJpa = this.getFieldForColumnName(parentTable, parentColumn);
			String childColumnJpa = this.getFieldForColumnName(childTable, childColumn);
			return "p." + parentColumnJpa + "=" + "t." + childColumnJpa;
		}
		return "";
	}

}
