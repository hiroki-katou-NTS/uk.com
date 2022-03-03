package nts.uk.ctx.exio.infra.repository.input.workspace;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.layer.infra.data.database.DatabaseProduct;
import nts.arc.layer.infra.data.jdbc.JdbcProxy;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalItem;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository.Require;
import nts.uk.ctx.exio.dom.input.workspace.TemporaryTable;
import nts.uk.ctx.exio.dom.input.workspace.WorkspaceTableName;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataTypeConfiguration;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;
import nts.uk.ctx.exio.dom.input.workspace.item.WorkspaceItem;
import nts.uk.shr.com.company.CompanyId;

/**
 * ワークスペースの一時テーブルを扱うクラス
 * 現状はSQLServerにしか対応できていないので、後でPostgreSQL対応が必要。
 * その際、できるだけTemporaryTableクラスに処理を移譲すること。
 */
@RequiredArgsConstructor
public class WorkspaceSql {

	private final ExecutionContext context;
	private final DomainWorkspace workspace;
	private final JdbcProxy jdbcProxy;
	private final DatabaseProduct database;	//this.database().product();

	static final Column ROW_NO = new Column("ROW_NO", new DataTypeConfiguration(DataType.INT, 10,0), "rowno", true);
	static final Column CONTRACT_CD = new Column("CONTRACT_CD", new DataTypeConfiguration(DataType.STRING, 12,0), "9999", false);
	static final Column CID = new Column("CID", new DataTypeConfiguration(DataType.STRING, 17,0), "9998", false);

	public static WorkspaceSql create(Require require, ExecutionContext context, JdbcProxy jdbcProxy, DatabaseProduct database) {

		val workspace = require.getDomainWorkspace(context.getDomainId());
		
		return new WorkspaceSql(context, workspace, jdbcProxy, database);
	}
	
	/**
	 * 古い一時テーブルをすべて削除する
	 * @param require
	 * @param context
	 * @param jdbcProxy
	 */
	public static void cleanOldTables(Require require, ExecutionContext context, JdbcProxy jdbcProxy) {
		val tableName = tableName(context);
		TemporaryTable.dropTable(jdbcProxy, tableName.asRevised());
		TemporaryTable.dropTable(jdbcProxy, tableName.asCanonicalized());
	}
	
	/**
	 * 編集済み用のCREATE TABLEを実行する
	 * @return
	 */
	public void createTableRevised() {
		createTable(tableName().asRevised());
	}

	/**
	 * 正準化済み用のCREATE TABLEを実行する
	 * @return
	 */
	public void createTableCanonicalized() {
		createTable(tableName().asCanonicalized());
	}

	private void createTable(String tableName) {
		TemporaryTable.createTable(jdbcProxy, database, tableName, b -> {
			for (Column column : allWorkspaceTableColumns()){
				if (column.pkey) {
					b = b.columnPK(column.name, column.type);
				}
				else {
					b = b.column(column.name, column.type);
				}
			}
		});
	}

	protected List<Column> allWorkspaceTableColumns() {
		List<Column> result = new ArrayList<>();
		result.add(ROW_NO);
		for (WorkspaceItem item : workspace.getAllItemsSortedByItemNo()){
			result.add(new Column(item.getName(), item.getDataTypeConfig(), "@" + Insert.paramItem(item.getItemNo()), false));
		}
		return result;
	}
	
	static class CommonColumns {
		static final List<String> NothingItemNoList = Arrays.asList(ROW_NO.paramName);
		static final List<String> HasItemNoList = Arrays.asList(CONTRACT_CD.paramName, CID.paramName);
		
		static String sqlParams() {
			return NothingItemNoList.stream()
					.map(paramName -> "@" + paramName)
					.collect(Collectors.joining(","));
		}
		
		static void setParams(NtsStatement statement, int rowNo, ExecutionContext context) {
			
			//項目Noを持たず、列が自動生成される者たち
			statement.paramInt(ROW_NO.paramName, rowNo);
			
			//項目Noを持つので列は生成される者たち
			String companyId = context.getCompanyId();
			String contractCode = CompanyId.getContractCodeOf(companyId);
			statement.paramString("p"+CONTRACT_CD.paramName, contractCode);
			statement.paramString("p"+CID.paramName, companyId);
		}

		/**
		 * 項目Noが割り振られている共通列を取り除く 
		 */
		static List<WorkspaceItem> removeCommonColumns(List<WorkspaceItem> allItemsSortedByItemNo) {
			val commonColumnsMinItemNo = HasItemNoList.stream()
							.map(item -> Integer.valueOf(item))
							.min((first, second) -> Integer.compare(first, second))
							.get();
			return allItemsSortedByItemNo.stream()
							.filter(item -> item.getItemNo() < commonColumnsMinItemNo)
							.sorted((first, second) -> Integer.compare(first.getItemNo() ,second.getItemNo()))
							.collect(Collectors.toList());
		}
	}
	
	@Value
	static class Column {
		String name;
		DataTypeConfiguration type;
		String paramName;
		boolean pkey;
	}

	/**
	 * 編集済み用のINSERT文を実行する
	 * @param record
	 * @return
	 */
	public void insert(RevisedDataRecord record) {
		insert(
				tableName().asRevised(),
				record.getRowNo(),
				itemNo -> record.getItemByNo(itemNo).map(DataItem::getValue).orElse(null));
	}
	
	/**
	 * 正準化済み用のINSERT文を実行する
	 * @param record
	 */
	public void insert(CanonicalizedDataRecord record) {
		insert(
				tableName().asCanonicalized(),
				record.getRowNo(),
				itemNo -> record.getItemByNo(itemNo).map(CanonicalItem::getValue).orElse(null));
	}
	
	private void insert(
			String tableName,
			int rowNo,
			Function<Integer, Object> itemValueGetter) {

		/*
		 * VALUES句の列順は、項目No順にテーブルが作られるという仕様を前提とする。
		 * ただし先頭はROW_NO列で固定。
		 */
		String sql = Insert.createInsertSql(tableName, workspace, allWorkspaceTableColumns());
		
		val statement = jdbcProxy.query(sql);
		
		CommonColumns.setParams(statement, rowNo, context);
		val domainsItemsSortedByItemNo = CommonColumns.removeCommonColumns(workspace.getAllItemsSortedByItemNo());
		
		for (val workspaceItem : domainsItemsSortedByItemNo) {
			val dataType = workspaceItem.getDataTypeConfig();
			Object itemValue = itemValueGetter.apply(workspaceItem.getItemNo());
			Insert.setParam(dataType, itemValue, statement, workspaceItem);
		}
		
		statement.execute();
	}

	static class Insert {

		static String createInsertSql(String tableName, DomainWorkspace workspace, List<Column> columns) {

			return new StringBuilder()
				.append("insert into ")
				.append(tableName)
				.append(" (")
				.append(columns.stream().map(col -> col.getName()).collect(Collectors.joining(",")))
				.append(")")
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
				Object value,
				NtsStatement statement,
				WorkspaceItem workspaceItem
				) {
			
			String param = paramItem(workspaceItem.getItemNo());
			
			try {
				if(dataType.getType() == DataType.BOOLEAN && value != null && value.getClass() == Long.class){
					// 編集済データをINSERTする際はBoolean型の項目であっても数値でくるので、変換が必要
					value = Objects.equals(value,Long.valueOf(1));
				}
				dataType.getType().setParam(statement, param, value);
			} catch (Exception ex) {
				throw new RuntimeException("パラメータ設定に失敗：" + value + ", " + dataType + ", " + workspaceItem, ex);
			}
		}
		
		static String paramItem(int itemNo) {
			return "p" + itemNo;
		}
	}
	
	public int getMaxRowNumberOfRevisedData() {
		String sql = "select max(" + ROW_NO.name + ") from " + tableName().asRevised();
		return jdbcProxy.query(sql).getSingle(rec -> rec.getInt(1)).get();
	}
	
	public List<String> getStringsOfRevisedData(String columnName) {
		String sql = "select " + columnName + " from " + tableName().asRevised();
		return jdbcProxy.query(sql).getList(rec -> rec.getString(1)).stream().distinct().collect(Collectors.toList());
	}
	
	public Optional<RevisedDataRecord> findRevisedByRowNo(int rowNo) {
		String sql = "select * from " + tableName().asRevised()
				+ " where " + ROW_NO.name + " = " + rowNo;
		return jdbcProxy.query(sql).getSingle(rec -> toRevised(rec));
	}
	
	public List<RevisedDataRecord> findAllRevised() {
		String sql = "select * from " + tableName().asRevised();
		return jdbcProxy.query(sql).getList(rec -> toRevised(rec));
	}
	
	public List<RevisedDataRecord> findRevisedWhere(int itemNoCondition, String conditionString) {
		
		String columnName = workspace.getItem(itemNoCondition)
				.orElseThrow(() -> new RuntimeException("not found: " + itemNoCondition))
				.getName();
		
		String sql = "select * from " + tableName().asRevised()
				+ " where " + columnName + " = @p";
		
		return jdbcProxy.query(sql)
				.paramString("p", conditionString)
				.getList(rec -> toRevised(rec));
	}
	
	private RevisedDataRecord toRevised(NtsResultRecord record) {
		
		int rowNo = record.getInt(ROW_NO.name);
		
		val items = workspace.getAllItemsSortedByItemNo().stream()
				.map(wi -> toDataItem(record, wi))
				.collect(toList());
		
		return new RevisedDataRecord(rowNo, new DataItemList(items));
	}
	
	private static DataItem toDataItem(NtsResultRecord record, WorkspaceItem workspaceItem) {
		
		val dataType = workspaceItem.getDataTypeConfig();
		int itemNo = workspaceItem.getItemNo();
		String name = workspaceItem.getName();
		
		// nullの場合
		if(Objects.isNull(record.getObject(name))) {
			return DataItem.of(itemNo);
		}
		
		switch (dataType.getType()) {
		case INT:
			return DataItem.of(itemNo, record.getLong(name));
		case REAL:
			return DataItem.of(itemNo, record.getBigDecimal(name));
		case STRING:
			return DataItem.of(itemNo, record.getString(name));
		case DATE:
			return DataItem.of(itemNo, record.getGeneralDate(name));
		case BOOLEAN:
			return DataItem.of(itemNo, record.getBoolean(name));
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
		return tableName(context);
	}
	
	private static WorkspaceTableName tableName(ExecutionContext context) {
		return new WorkspaceTableName(context);
	}
}
