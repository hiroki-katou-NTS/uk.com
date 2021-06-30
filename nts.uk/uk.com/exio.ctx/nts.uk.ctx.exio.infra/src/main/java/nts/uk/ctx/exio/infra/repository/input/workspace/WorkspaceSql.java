package nts.uk.ctx.exio.infra.repository.input.workspace;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.JdbcProxy;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroup;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.workspace.GroupWorkspace;
import nts.uk.ctx.exio.dom.input.workspace.WorkspaceItem;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataTypeConfiguration;

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
		String sql = createTable(require, tableName().asRevised());
		jdbcProxy.query(sql).execute();
	}

	/**
	 * 正準化済み用のCREATE TABLEを実行する
	 * @param require
	 * @return
	 */
	public void createTableCanonicalized(WorkspaceItem.RequireConfigureDataType require) {
		String sql = createTable(require, tableName().asCanonicalized());
		jdbcProxy.query(sql).execute();
	}

	private String createTable(WorkspaceItem.RequireConfigureDataType require, String tableName) {
		val sql = new StringBuilder();
		val createTable = new CreateTable(sql);
		
		sql.append("create table ").append(tableName()).append(" (");
		
		createTable.columnRowNo();
		workspace.getAllItemsSortedByItemNo().forEach(item -> createTable.column(require, item));
		
		createTable.primaryKey(tableName, workspace);
		
		sql.append(");");
		
		return sql.toString();
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
		 * ただし先頭はROW_NO列で固定。
		 */
		String paramRowNo = "rowno";
		String sql = Insert.createInsertSql(tableName, workspace, paramRowNo);
		
		val statement = jdbcProxy.query(sql);
		statement.paramInt(paramRowNo, rowNo);
		
		for (val workspaceItem : workspace.getAllItemsSortedByItemNo()) {
			val dataType = workspaceItem.configureDataType(require);
			Insert.setParam(dataType, itemGetter, statement, workspaceItem);
		}
		
		statement.execute();
	}
	
	private WorkspaceTableName tableName() {
		return new WorkspaceTableName(context, group.getName());
	}

	@RequiredArgsConstructor
	class CreateTable {
		
		private final StringBuilder sql;
		
		void primaryKey(String tableName, GroupWorkspace workspace) {
			
			String pkName = "PK_" + tableName;
			
			String keys = workspace.getItemsPk().stream()
					.map(item -> item.getName())
					.collect(Collectors.joining(", "));
			
			sql.append("constraint ").append(pkName).append(" key (").append(keys).append(")");
		}
		
		void columnRowNo() {
			// ROW_NO列はdecimal使わなくても良いんじゃないかな、さすがにintで足りると思う
			sql.append("ROW_NO int not null,");
		}
		
		void column(WorkspaceItem.RequireConfigureDataType require, WorkspaceItem item) {
			
			sql.append(item.getName()).append(" ");
			
			dataType(item.configureDataType(require));
			
			sql.append(" null,");
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
	
	static class Insert {

		static String createInsertSql(String tableName, GroupWorkspace workspace, String paramRowNo) {
			
			return new StringBuilder()
				.append("insert into ")
				.append(tableName)
				.append(" values (")
				.append("@" + paramRowNo + ",")
				.append(workspace.getAllItemsSortedByItemNo().stream()
						.map(item -> "@" + Insert.paramItem(item.getItemNo()))
						.collect(Collectors.joining(",")))
				.append(");")
				.toString();
		}

		static void setParam(
				DataTypeConfiguration dataType,
				Function<Integer, Optional<DataItem>> itemGetter,
				NtsStatement statement,
				WorkspaceItem workspaceItem) {
			
			String param = paramItem(workspaceItem.getItemNo());
			DataItem item = itemGetter.apply(workspaceItem.getItemNo()).get();
			
			switch (dataType.getType()) {
			case INT:
				statement.paramLong(param, item.getInt());
				break;
			case REAL:
				statement.paramDecimal(param, item.getReal());
				break;
			case STRING:
				statement.paramString(param, item.getString());
				break;
			case DATE:
				statement.paramDate(param, item.getDate());
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
}
