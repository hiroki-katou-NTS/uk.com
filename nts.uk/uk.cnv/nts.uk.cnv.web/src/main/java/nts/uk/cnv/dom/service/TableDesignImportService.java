package nts.uk.cnv.dom.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.databasetype.DataType;
import nts.uk.cnv.dom.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.tabledesign.Indexes;
import nts.uk.cnv.dom.tabledesign.TableDesign;

public class TableDesignImportService {
	/**
	 * 
	 * @param require
	 * @param createTable create table文を指定する. 列のデータ型はDB設計規約の「Name for layout」を参照すること
	 * @param createIndex テーブルに所属するcreate index文を指定する.
	 * @throws JSQLParserException
	 */
	public static AtomTask regist(Require require, String createTable) throws JSQLParserException {
		TableDesign tableDesign = ddlToDomain(createTable);

		return AtomTask.of(() -> {
			require.regist(tableDesign);
		});
	}
	
	private static TableDesign ddlToDomain(String createTable) throws JSQLParserException {
		CCJSqlParserManager pm = new CCJSqlParserManager();
		Statement createTableSt = pm.parse(new StringReader(createTable.toUpperCase()));
		
		if (createTableSt instanceof CreateTable) {
			return toDomain( (CreateTable) createTableSt);
		}
		
		throw new JSQLParserException();
	}
	
	@SuppressWarnings("unchecked")
	private static TableDesign toDomain(CreateTable statement) {
		GeneralDateTime now = GeneralDateTime.now();
		
		List<Indexes> indexes = new ArrayList<>();
		Map<String, Integer> pk = new LinkedHashMap<>();
		Map<String, Integer> uk = new LinkedHashMap<>();
		if (statement.getIndexes() != null) {
			analyzeIndex(statement, indexes, pk, uk);
		}
		
		List<ColumnDesign> columns = new ArrayList<>();
		int id = 1;
		for (Iterator<ColumnDefinition> colmnDef =  statement.getColumnDefinitions().iterator(); colmnDef.hasNext();) {
			ColumnDefinition col = colmnDef.next();
			DataType type = DataType.valueOf(col.getColDataType().getDataType());
			List<String> specs = col.getColumnSpecStrings();

			List<String> args = col.getColDataType().getArgumentsStringList();
			
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
					defaultValue
			);
			columns.add(newItem);
			id++;
		}
		
		Table table = statement.getTable();
		TableDesign result = new TableDesign(table.getName(), table.getName(), now, now, columns, indexes);
		return result;
	}
 
	@SuppressWarnings("unchecked")
	private static void analyzeIndex(CreateTable statement, List<Indexes> indexes,
			Map<String, Integer> pk, Map<String, Integer> uk) {
		for (Iterator<Index> indexDef =  statement.getIndexes().iterator(); indexDef.hasNext();) {
			Index index = (Index) indexDef.next();
			if(index.getType().equals("PRIMARY KEY")) {
				int seq = 1;
				for(Object colName : index.getColumnsNames()){
					pk.put((String)colName, seq);
					seq++;
				}
				continue;
			}
			else if(index.getType().equals("UNIQUE")) {
				int seq = 1;
				for(Object colName : index.getColumnsNames()){
					uk.put((String)colName, seq);
					seq++;
				}
				continue;
			}
			Indexes idx = new Indexes(
					index.getName(),
					index.getColumnsNames()
			);
			indexes.add(idx);
		}
	}
	
	public interface Require {

		void regist(TableDesign tableDesign);
		
	}
}
