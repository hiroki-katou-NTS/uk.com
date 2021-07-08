package nts.uk.ctx.exio.infra.repository.input.workspace;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.JdbcProxy;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.group.ImportingGroup;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.workspace.TemporaryTable;
import nts.uk.ctx.exio.dom.input.workspace.WorkspaceTableName;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository.Require;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataTypeConfiguration;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;
import nts.uk.ctx.exio.dom.input.workspace.item.WorkspaceItem;
import nts.uk.shr.com.company.CompanyId;

@RequiredArgsConstructor
public class WorkspaceSql {

	private final ExecutionContext context;
	private final ImportingGroup group;
	private final GroupWorkspace workspace;
	private final JdbcProxy jdbcProxy;
	
	/**
	 * 編集済み用のCREATE TABLEを実行する
	 * @param require
	 * @return
	 */
	public void createTableRevised(WorkspaceItem.RequireConfigureDataType require) {
		TemporaryTable.dropTable(jdbcProxy, tableName().asRevised());
		String sql = createTable(require, tableName().asRevised());
		jdbcProxy.query(sql).execute();
	}

	/**
	 * 正準化済み用のCREATE TABLEを実行する
	 * @param require
	 * @return
	 */
	public void createTableCanonicalized(WorkspaceItem.RequireConfigureDataType require) {
		TemporaryTable.dropTable(jdbcProxy, tableName().asCanonicalized());
		String sql = createTable(require, tableName().asCanonicalized());
		jdbcProxy.query(sql).execute();
	}

	private String createTable(WorkspaceItem.RequireConfigureDataType require, String tableName) {
		val sql = new StringBuilder();
		val createTable = new CreateTable(sql);
		
		sql.append("create table ").append(tableName).append(" (");
		
		createTable.columnCommonColumns();
		workspace.getAllItemsSortedByItemNo().forEach(
				item -> createTable.column(require, item, workspace.isPrimaryKey(item)));
		
		createTable.primaryKey(tableName, workspace);
		
		sql.append(");");
		
		return sql.toString();
	}
	
	static class CommonColumns {
		static final Column ROW_NO = new Column("ROW_NO", "int not null", "rowno");
		static final Column CONTRACT_CD = new Column("CONTRACT_CD", "char(12) not null", "contract");
		static final Column CID = new Column("CID", "char(17) not null", "cid");

		static final List<Column> LIST = Arrays.asList(ROW_NO, CONTRACT_CD, CID);
		
		static String sqlParams() {
			return LIST.stream()
					.map(c -> "@" + c.paramName)
					.collect(Collectors.joining(","));
		}
		
		static void setParams(NtsStatement statement, int rowNo, ExecutionContext context) {
			
			statement.paramInt(ROW_NO.paramName, rowNo);
			
			String companyId = context.getCompanyId();
			String contractCode = CompanyId.getContractCodeOf(companyId);
			statement.paramString(CONTRACT_CD.paramName, contractCode);
			statement.paramString(CID.paramName, companyId);
		}
	}
	
	@Value
	static class Column {
		String name;
		String type;
		String paramName;
		
		public String sqlDefine() {
			return name + " " + type;
		}
	}
	
	@RequiredArgsConstructor
	class CreateTable {
		
		private final StringBuilder sql;
		
		void primaryKey(String tableName, GroupWorkspace workspace) {
			
			String pkName = "PK_" + tableName;
			
			String keys = workspace.getItemsPk().stream()
					.map(item -> item.getName())
					.collect(Collectors.joining(", "));
			
			sql.append("constraint ").append(pkName).append(" primary key nonclustered (").append(keys).append(")");
		}
		
		void columnCommonColumns() {
			// ROW_NO列はdecimal使わなくても良いんじゃないかな、さすがにintで足りると思う
			sql.append(CommonColumns.ROW_NO.sqlDefine() + ",");
			sql.append(CommonColumns.CONTRACT_CD.sqlDefine() + ",");
			sql.append(CommonColumns.CID.sqlDefine() + ",");
		}
		
		void column(WorkspaceItem.RequireConfigureDataType require, WorkspaceItem item, boolean isPK) {
			
			sql.append(item.getName()).append(" ");
			
			dataType(item.configureDataType(require));
			
			if (isPK) {
				sql.append(" not null,");
			} else {
				sql.append(" null,");
			}
		}
		
		void dataType(DataTypeConfiguration config) {
			
			switch (config.getType()) {
			case DATE:
				sql.append("datetime2");
				break;
			case STRING:
				sql.append("nvarchar(").append(config.getLength()).append(")");
				break;
			case INT:
				sql.append("decimal(").append(config.getLength()).append(")");
				break;
			case REAL:
				sql.append("decimal(").append(config.getLength()).append(",").append(config.getScale()).append(")");
				break;
			default:
				throw new RuntimeException("error: " + config);
			}
		}
	}
	
	/**
	 * 編集済み用のINSERT文を実行する
	 * @param require
	 * @param record
	 * @return
	 */
	public void insert(WorkspaceItem.RequireConfigureDataType require, RevisedDataRecord record) {
		insert(require, tableName().asRevised(), record.getRowNo(), itemNo -> record.getItemByNo(itemNo));
	}
	
	/**
	 * 正準化済み用のINSERT文を実行する
	 * @param require
	 * @param record
	 */
	public void insert(WorkspaceItem.RequireConfigureDataType require, CanonicalizedDataRecord record) {
		insert(require, tableName().asCanonicalized(), record.getRowNo(), itemNo -> record.getItemByNo(itemNo));
	}
	
	private void insert(
			WorkspaceItem.RequireConfigureDataType require,
			String tableName,
			int rowNo,
			Function<Integer, Optional<DataItem>> itemGetter) {

		/*
		 * VALUES句の列順は、項目No順にテーブルが作られるという仕様を前提とする。
		 * ただし先頭はROW_NO, CONTRACT_CD, CID列で固定。
		 */
		String sql = Insert.createInsertSql(tableName, workspace);
		
		val statement = jdbcProxy.query(sql);
		
		CommonColumns.setParams(statement, rowNo, context);
		
		for (val workspaceItem : workspace.getAllItemsSortedByItemNo()) {
			val dataType = workspaceItem.configureDataType(require);
			val itemOpt = itemGetter.apply(workspaceItem.getItemNo());
			Insert.setParam(dataType, itemOpt, statement, workspaceItem);
		}
		
		statement.execute();
	}

	static class Insert {

		static String createInsertSql(String tableName, GroupWorkspace workspace) {
			
			return new StringBuilder()
				.append("insert into ")
				.append(tableName)
				.append(" values (")
				.append(CommonColumns.sqlParams() + ",")
				.append(workspace.getAllItemsSortedByItemNo().stream()
						.map(item -> "@" + Insert.paramItem(item.getItemNo()))
						.collect(Collectors.joining(",")))
				.append(");")
				.toString();
		}

		static void setParam(
				DataTypeConfiguration dataType,
				Optional<DataItem> item,
				NtsStatement statement,
				WorkspaceItem workspaceItem) {
			
			String param = paramItem(workspaceItem.getItemNo());
			
			switch (dataType.getType()) {
			case INT:
				statement.paramLong(param, item.map(d -> d.getInt()).orElse(null));
				break;
			case REAL:
				statement.paramDecimal(param, item.map(d -> d.getReal()).orElse(null));
				break;
			case STRING:
				statement.paramString(param, item.map(d -> d.getString()).orElse(null));
				break;
			case DATE:
				statement.paramDate(param, item.map(d -> d.getDate()).orElse(null));
				break;
			default:
				throw new RuntimeException("unknown: " + dataType.getType());
			}
		}
		
		static String paramItem(int itemNo) {
			return "p" + itemNo;
		}
		
		static String value(DataItem dataItem, DataTypeConfiguration dataType) {
			
			if (dataItem.isNull()) {
				return "null";
			}
			
			switch (dataType.getType()) {
			case STRING:
				return "'" + dataItem.getString() + "'";
			case INT:
			case REAL:
				return dataItem.getValue().toString();
			case DATE:
				return dataItem.getDate().toString("yyyy-MM-dd");
			}
			
			throw new RuntimeException(dataItem + ", " + dataType);
		}
	}
	
	public int getMaxRowNumberOfRevisedData() {
		String sql = "select max(" + CommonColumns.ROW_NO.name + ") from " + tableName().asRevised();
		return jdbcProxy.query(sql).getSingle(rec -> rec.getInt(1)).get();
	}
	
	public List<String> getStringsOfRevisedData(String columnName) {
		String sql = "select " + columnName + " from " + tableName().asRevised();
		return jdbcProxy.query(sql).getList(rec -> rec.getString(1));
	}
	
	public Optional<RevisedDataRecord> findRevisedByRowNo(WorkspaceItem.RequireConfigureDataType require, int rowNo) {
		String sql = "select * from " + tableName().asRevised()
				+ " where " + CommonColumns.ROW_NO.name + " = " + rowNo;
		return jdbcProxy.query(sql).getSingle(rec -> toRevised(require, rec));
	}
	
	public List<RevisedDataRecord> findRevisedWhere(Require require, int itemNoCondition, String conditionString) {
		
		String columnName = workspace.getItem(itemNoCondition)
				.orElseThrow(() -> new RuntimeException("not found: " + itemNoCondition))
				.getName();
		
		String sql = "select * from " + tableName().asRevised()
				+ " where " + columnName + " = @p";
		
		return jdbcProxy.query(sql)
				.paramString("p", conditionString)
				.getList(rec -> toRevised(require, rec));
	}
	
	private RevisedDataRecord toRevised(WorkspaceItem.RequireConfigureDataType require, NtsResultRecord record) {
		
		int rowNo = record.getInt(CommonColumns.ROW_NO.name);
		
		val items = workspace.getAllItemsSortedByItemNo().stream()
				.map(wi -> toDataItem(require, record, wi))
				.collect(toList());
		
		return new RevisedDataRecord(rowNo, new DataItemList(items));
	}
	
	private static DataItem toDataItem(WorkspaceItem.RequireConfigureDataType require, NtsResultRecord record, WorkspaceItem workspaceItem) {
		
		val dataType = workspaceItem.configureDataType(require);
		int itemNo = workspaceItem.getItemNo();
		String name = workspaceItem.getName();
		
		switch (dataType.getType()) {
		case INT:
			return DataItem.of(itemNo, record.getLong(name));
		case REAL:
			return DataItem.of(itemNo, record.getBigDecimal(name));
		case STRING:
			return DataItem.of(itemNo, record.getString(name));
		case DATE:
			return DataItem.of(itemNo, record.getGeneralDate(name));
		default:
			throw new RuntimeException("unknown: " + dataType.getType());
		}
	}
	
	public List<String> getAllEmployeeIdsOfCanonicalizedData() {
		
		// 社員IDのカラム名は固定で SID
		String sql = "select SID from " + tableName().asCanonicalized();
		
		return jdbcProxy.query(sql)
				.getList(rec -> rec.getString(1))
				.stream()
				.distinct()
				.collect(toList());
	}
	
	private WorkspaceTableName tableName() {
		return new WorkspaceTableName(context, group.getName());
	}
}
