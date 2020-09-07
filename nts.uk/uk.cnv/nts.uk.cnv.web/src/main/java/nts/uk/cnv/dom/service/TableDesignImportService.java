package nts.uk.cnv.dom.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.databasetype.DataType;
import nts.uk.cnv.dom.databasetype.DataTypeDefine;
import nts.uk.cnv.dom.databasetype.DatabaseType;
import nts.uk.cnv.dom.databasetype.UkDataType;
import nts.uk.cnv.dom.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.tabledesign.Indexes;
import nts.uk.cnv.dom.tabledesign.TableDesign;

public class TableDesignImportService {
	/**
	 *
	 * @param require
	 * @param createTable create table文を指定する. 列のデータ型はDB設計規約の「Name for layout」を参照すること
	 * @param createIndex テーブルに所属するcreate index文を指定する.
	 * @param type SQL文がどのRDBMSかを指定する
	 * @throws JSQLParserException
	 */
	public static AtomTask regist(Require require, String createTable, String createIndexes, String comment, String type) throws JSQLParserException {
		DataTypeDefine typeDefine;
		if("uk".equals(type)) {
			typeDefine = new UkDataType();
		}
		else {
			typeDefine = DatabaseType.valueOf(type).spec();
		}
		TableDesign tableDesign = ddlToDomain(createTable, createIndexes, comment, typeDefine);

		return AtomTask.of(() -> {
			require.regist(tableDesign);
		});
	}

	private static TableDesign ddlToDomain(String createTable, String createIndexes, String comment, DataTypeDefine typeDefine) throws JSQLParserException {
		CCJSqlParserManager pm = new CCJSqlParserManager();

		boolean isClusteredPK = true;
		if(createTable.toUpperCase().contains("PRIMARY KEY NONCLUSTERED")) {
			isClusteredPK = false;
		}
		String formatedCreateTable = createTable.toUpperCase().replace("NONCLUSTERED", "").replace("CLUSTERED", "");
		Statement createTableSt = pm.parse(new StringReader(formatedCreateTable));

		List<CreateIndex> indexes = new ArrayList<>();
		Map<String, Boolean> indexClusteredMap = new HashMap<>();
		if(createIndexes != null && !createIndexes.isEmpty()) {
			for (String ci : createIndexes.split(";")) {

				boolean isClusteredIndex = false;
				if(ci.toUpperCase().contains("CREATE CLUSTERED INDEX")) {
					isClusteredIndex = true;
				}
				String formatedIndex = ci.toUpperCase().replace("NONCLUSTERED", "").replace("CLUSTERED", "");

				CreateIndex idx =(CreateIndex) pm.parse(new StringReader(formatedIndex));
				indexClusteredMap.put(idx.getIndex().getName(), isClusteredIndex);
				indexes.add(idx);
			}
		}

		if (createTableSt instanceof CreateTable) {
			return toDomain( (CreateTable) createTableSt, indexes, typeDefine, comment, indexClusteredMap, isClusteredPK);
		}

		throw new JSQLParserException();
	}

	@SuppressWarnings("unchecked")
	private static TableDesign toDomain(CreateTable statement, List<CreateIndex> createIndex, DataTypeDefine typeDefine,
			String comment, Map<String, Boolean> indexClusteredMap, boolean isClusteredPK) {
		GeneralDateTime now = GeneralDateTime.now();

		List<Indexes> indexes = new ArrayList<>();
		Map<String, Integer> pk = new LinkedHashMap<>();
		Map<String, Integer> uk = new LinkedHashMap<>();
		if (statement.getIndexes() != null) {
			analyzeIndex(statement, indexes, pk, uk, isClusteredPK);
		}
		if (!createIndex.isEmpty()) {
			indexes.addAll(
					createIndex.stream()
						.map(idx -> new Indexes(
								idx.getIndex().getName(),
								"INDEX",
								indexClusteredMap.get(idx.getIndex().getName()),
								idx.getIndex().getColumnsNames(),
								(idx.getTailParameters() == null ? new ArrayList<>() : idx.getTailParameters())
						)).collect(Collectors.toList())
			);
		}

		List<ColumnDesign> columns = new ArrayList<>();
		int id = 1;
		for (Iterator<ColumnDefinition> colmnDef =  statement.getColumnDefinitions().iterator(); colmnDef.hasNext();) {
			ColumnDefinition col = colmnDef.next();
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
			DataType type = typeDefine.parse(col.getColDataType().getDataType(), args.toArray(argsArray));

			boolean nullable = !specs.stream().anyMatch(arg -> arg.equals("NOT"));
			String defaultValue =
					specs.stream().anyMatch(arg-> arg.equals("DEFAULT"))
					? specs.get(specs.indexOf("DEFAULT") + 1).toString()
					: "";
			int maxLength = 0;
			int scale = 0;

			switch(type) {
			case REAL:
				scale = Integer.parseInt(args.get(1).toString());
			case INT:
			case CHAR:
			case VARCHAR:
			case NCHAR:
			case NVARCHAR:
				maxLength = Integer.parseInt(args.get(0).toString());
				break;
			case BOOL:
			case DATE:
			case DATETIME:
			case GUID:
				break;
			}

			ColumnDesign newItem = new ColumnDesign(
					id,
					col.getColumnName(),
					type,
					maxLength, scale, nullable,
					pk.containsKey(col.getColumnName()),
					pk.containsKey(col.getColumnName()) ? pk.get(col.getColumnName()) : 0,
					uk.containsKey(col.getColumnName()),
					uk.containsKey(col.getColumnName()) ? uk.get(col.getColumnName()) : 0,
					defaultValue,
					""
			);
			columns.add(newItem);
			id++;
		}

		Table table = statement.getTable();
		TableDesign result = new TableDesign(table.getName(), table.getName(), "",now, now, columns, indexes);
		return result;
	}

	@SuppressWarnings("unchecked")
	private static void analyzeIndex(CreateTable statement, List<Indexes> indexes, Map<String, Integer> pk, Map<String, Integer> uk, boolean isClusteredPK) {
		for (Iterator<Index> indexDef =  statement.getIndexes().iterator(); indexDef.hasNext();) {
			Index index = (Index) indexDef.next();
			boolean clustered = false;
			if(index.getType().equals("PRIMARY KEY")) {
				int seq = 1;
				for(Object colName : index.getColumnsNames()){
					pk.put((String)colName, seq);
					seq++;
				}
				clustered = isClusteredPK;
			}
			else if(index.getType().equals("UNIQUE")) {
				int seq = 1;
				for(Object colName : index.getColumnsNames()){
					uk.put((String)colName, seq);
					seq++;
				}
			}

			Indexes idx = new Indexes(
					index.getName(),
					index.getType(),
					clustered,
					index.getColumnsNames(),
					new ArrayList<>()
			);
			indexes.add(idx);
		}
	}

	public interface Require {

		void regist(TableDesign tableDesign);

	}
}
