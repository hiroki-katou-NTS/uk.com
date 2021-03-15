package nts.uk.cnv.dom.td.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;
import nts.arc.task.tran.AtomTask;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.Indexes;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;
import nts.uk.cnv.dom.td.tabledefinetype.UkDataType;
import nts.uk.cnv.dom.td.tabledefinetype.databasetype.DatabaseType;

@Stateless
public class DDLImportService {
	/**
	 * @param require
	 * @param snapshotId スナップショットID
	 * @param createTable create table文を指定する. 列のデータ型はDB設計規約の「Name for layout」を参照すること
	 * @param createIndex テーブルに所属するcreate index文を指定する.
	 * @param comment コメントDLL
	 * @param type SQL文がどのRDBMSかを指定する
	 * @throws JSQLParserException
	 */
	public static AtomTask regist(Require require, String snapshotId, String createTable, String createIndexes, String comment, String type) throws JSQLParserException {
		TableDefineType typeDefine;

		if("uk".equals(type)) {
			typeDefine = new UkDataType();
		}
		else {
			typeDefine = DatabaseType.valueOf(type).spec();
		}
		TableSnapshot ss = new TableSnapshot(
				snapshotId,
				ddlToDomain(createTable, createIndexes, comment, typeDefine));

		return AtomTask.of(() -> {
			require.regist(ss);
		});
	}

	private static TableDesign ddlToDomain(
		String createTable, String createIndexes, String comment, TableDefineType typeDefine) throws JSQLParserException {
		CCJSqlParserManager pm = new CCJSqlParserManager();

		boolean isClusteredPK = true;
		if(createTable.toUpperCase().contains("PRIMARY KEY NONCLUSTERED")) {
			isClusteredPK = false;
		}
		String formatedCreateTable = createTable.replace("NONCLUSTERED", "").replace("CLUSTERED", "");
		Statement createTableSt = pm.parse(new StringReader(formatedCreateTable));

		List<CreateIndex> indexes = new ArrayList<>();
		Map<String, Boolean> indexClusteredMap = new HashMap<>();
		if(createIndexes != null && !createIndexes.isEmpty()) {
			for (String ci : createIndexes.split(";")) {

				boolean isClusteredIndex = false;
				if(ci.toUpperCase().contains(" CLUSTERED ")) {
					isClusteredIndex = true;
				}
				String formatedIndex = ci.replace("NONCLUSTERED", "").replace("CLUSTERED", "");

				CreateIndex idx =(CreateIndex) pm.parse(new StringReader(formatedIndex));
				indexClusteredMap.put(idx.getIndex().getName(), isClusteredIndex);
				indexes.add(idx);
			}
		}

		if (createTableSt instanceof CreateTable) {
			return toDomain((CreateTable) createTableSt, indexes, typeDefine, comment, indexClusteredMap, isClusteredPK);
		}

		throw new JSQLParserException();
	}

	private static TableDesign toDomain(
			CreateTable statement, List<CreateIndex> createIndex, TableDefineType typeDefine,
			String comment, Map<String, Boolean> indexClusteredMap, boolean isClusteredPK) {

		List<Indexes> indexes = new ArrayList<>();
		if (statement.getIndexes() != null) {
			analyzeIndex(new TableName(statement.getTable().getName()),statement, indexes, isClusteredPK);
		}
		if (!createIndex.isEmpty()) {
			indexes.addAll(
					createIndex.stream()
						.map(idx -> Indexes.createIndex(
								idx.getIndex().getName(),
								idx.getIndex().getColumnsNames(),
								indexClusteredMap.get(idx.getIndex().getName()))
						).collect(Collectors.toList())
			);
		}

		Map<String, String> commentMap = createComment(comment);

		List<ColumnDesign> columns = new ArrayList<>();
		int id = 1;
		for (Iterator<ColumnDefinition> colmnDef =  statement.getColumnDefinitions().iterator(); colmnDef.hasNext();) {
			ColumnDefinition col = colmnDef.next();

			if(isFixedColumn(col.getColumnName())) {
				continue;
			}

			ColumnDesign newItem = createColumnDesign(commentMap, typeDefine, col, id);

			columns.add(newItem);
			id++;
		}

		Table table = statement.getTable();

		String tableComment =
				commentMap.containsKey(table.getName())
				? commentMap.get(table.getName())
				: "";

		TableDesign result = new TableDesign(table.getName(), table.getName(), tableComment, columns, null);
		return result;
	}

	private static ColumnDesign createColumnDesign(Map<String, String> commentMap, TableDefineType typeDefine,
			ColumnDefinition col, int id) {
		String columnComment =
				commentMap.containsKey(col.getColumnName())
				? commentMap.get(col.getColumnName())
				: "";


		List<String> specs = col.getColumnSpecs();
		List<Integer> args;
		if (col.getColDataType().getArgumentsStringList() != null && col.getColDataType().getArgumentsStringList().size() > 0) {
			args = col.getColDataType().getArgumentsStringList().stream()
					.map(arg ->  Integer.parseInt(arg))
					.collect(Collectors.toList());
		}
		else {
			args = new ArrayList<>();
		}

		Integer[] argsArray = new Integer[args.size()];
		DataType type = typeDefine.parse(col.getColDataType().getDataType().toUpperCase(), args.toArray(argsArray));

		boolean nullable = (specs == null)
				? false
				: !String.join(" ", specs).contains("NOT NULL");

		String defaultValue = (specs == null)
			? ""
			: specs.stream().anyMatch(arg-> arg.equals("DEFAULT"))
				? specs.get(specs.indexOf("DEFAULT") + 1).toString()
				: "";

		String check = (specs == null)
			? ""
			: specs.stream().anyMatch(arg-> arg.equals("CHECK"))
				? "CHECK " + specs.get(specs.indexOf("CHECK") + 1).toString()
				: "";

		int maxLength = 0;
		int scale = 0;

		switch(type) {
		case REAL:
			scale = Integer.parseInt(args.get(1).toString());
		case INT:
			defaultValue = defaultValue.replaceAll("'", "");
		case CHAR:
		case VARCHAR:
		case NCHAR:
		case NVARCHAR:
			maxLength = (args.size() > 0)
				? Integer.parseInt(args.get(0).toString())
				: 0;
			break;
		case BOOL:
			defaultValue = defaultValue.replaceAll("'", "")
										.replaceAll("true", "1")
										.replaceAll("True", "1")
										.replaceAll("false", "0")
										.replaceAll("False", "0");
			break;
		case DATE:
		case DATETIME:
		case DATETIMEMS:
			defaultValue = defaultValue.replaceAll("'NULL'", "NULL");
		case GUID:
			break;
		}

		return new ColumnDesign(
				IdentifierUtil.randomUniqueId(),
				col.getColumnName(),
				col.getColumnName(),
				new DefineColumnType(
						type,
						maxLength,
						scale,
						nullable,
						defaultValue,
						check),
				columnComment,
				id
		);
	}

	private static Map<String, String> createComment(String commentBlock) {

		Map<String, String> result = new HashMap<>();

		if(commentBlock == null || commentBlock.isEmpty()) return result;

		String[] blocks = commentBlock.split(";");
		for(String block : blocks ) {
			if(block.contains("COMMENT ON TABLE")) {
				block = block.replace("COMMENT ON TABLE ", "").replaceAll("\r\n", "");
			}
			else if(block.contains("COMMENT ON COLUMN")) {
				block = block.substring(block.indexOf(".") + 1);
			}
			String[] comment = block.split(" IS ");
			result.put(comment[0], comment[1].replace(";", "").replaceAll("'", ""));
		}
		return result;
	}

	private static boolean isFixedColumn(String columnName) {
		if (columnName == null) return false;

		String column = columnName.toUpperCase();
		return ( column.equals("INS_DATE")
				|| column.equals("INS_CCD")
				|| column.equals("INS_SCD")
				|| column.equals("INS_PG")
				|| column.equals("UPD_DATE")
				|| column.equals("UPD_CCD")
				|| column.equals("UPD_SCD")
				|| column.equals("UPD_PG")
				|| column.equals("EXCLUS_VER")
				);
	}

	private static void analyzeIndex(TableName tableName, CreateTable statement, List<Indexes> indexes, boolean isClusteredPK) {
		for (Iterator<Index> indexDef =  statement.getIndexes().iterator(); indexDef.hasNext();) {
			Index index = (Index) indexDef.next();
			boolean clustered = false;
			Indexes idx = null;
			if(index.getType().equals("PRIMARY KEY")) {
				clustered = isClusteredPK;

				idx = Indexes.createPk(
						tableName,
						index.getColumnsNames(),
						clustered
				);
			}
			else if(index.getType().equals("UNIQUE KEY")) {
				idx = Indexes.createUk(
						index.getName(),
						index.getColumnsNames(),
						clustered
				);
			}
			indexes.add(idx);
		}
	}

	public interface Require {

		void regist(TableSnapshot tableDesign);

	}
}
