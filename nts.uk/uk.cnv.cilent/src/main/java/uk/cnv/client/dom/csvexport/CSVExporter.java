package uk.cnv.client.dom.csvexport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import lombok.val;
import uk.cnv.client.LogManager;
import uk.cnv.client.UkConvertProperty;
import uk.cnv.client.dom.execute.CommandExecutor;
import uk.cnv.client.dom.execute.CommandResult;
import uk.cnv.client.infra.query.GetAllUkWorkTablesQueryRepositoryImpl;
import uk.cnv.client.infra.repository.UkWorkTableDto;

public class CSVExporter {
	private static final String CSV_DIR = "csvDirectory";
	private static final int QUERY_MAX_LENGTH = 8191;

	private GetAllUkWorkTablesQueryRepositoryImpl repo;

	public CSVExporter() {
		repo = new GetAllUkWorkTablesQueryRepositoryImpl();
	}

	public CommandResult doWork() {
		val executor = new CommandExecutor();

		Path csvDir = Paths.get(UkConvertProperty.getProperty(CSV_DIR));
		File csvFolder = csvDir.toFile();
		csvFolder.mkdir();

		Map<String, String> queryList = null;
		try {
			queryList = createQuery();
		} catch (SQLException e) {
			LogManager.err(e);
			return new CommandResult(e);
		}

		List<String> errorList = new ArrayList<>();
		for(String table: queryList.keySet()) {
			LogManager.out("---- " + table + " ----");

			String csvFile = csvDir.resolve(table + ".csv").toString();
			String query = queryList.get(table);

			CommandResult result;
			if(query.length() > QUERY_MAX_LENGTH) {
				result = executor.bcpExecute("SELECT * FROM " + table, csvFile);
			}
			else {
				result = executor.bcpExecute(query, csvFile);
			}

			if(result.isError()) {
				errorList.add(table);
				continue;
			}

			if(query.length() > QUERY_MAX_LENGTH) {
				convertZeroLengthString(csvFile);
			}
		}

		if(errorList.size() > 0) {
			LogManager.err(String.format("%d件中%d件でエラーがありました。", queryList.keySet().size(), errorList.size()));
		}

		return new CommandResult();
	}

	/** bcpコマンド準備
	 * @throws SQLException **/
	private Map<String, String> createQuery() throws SQLException {

		Map<String, String> result = new TreeMap<String, String>();
		List<UkWorkTableDto> tables = null;
		// テーブル一覧取得
		tables = repo.find();

		Map<String, List<UkWorkTableDto>> tableMap = tables.stream()
			.collect(Collectors.groupingBy(UkWorkTableDto::getTableName));

		for(String tableName : tableMap.keySet()) {
			List<String> columns = tableMap.get(tableName).stream()
				.map(col -> col.columnExpression())
				.collect(Collectors.toList());
			String query = selectQuery(tableName, columns);
			result.put(tableName, query);
		}

		return result;
	}

	private String selectQuery(String table, List<String> columns) {
		return "SELECT " + String.join(",", columns) + " FROM " + table;
	}

	private void convertZeroLengthString(String csvFile) {
		// 長さ0文字列が0x00で出力されてしまうため、取り除く処理
		String renamedFileName = csvFile.replace(".csv", "_bak.csv");
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			File originalFile = new File(csvFile);
			File renamedFile = new File(renamedFileName);
			originalFile.renameTo(renamedFile);

			br = new BufferedReader(new FileReader(renamedFileName));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile, false),"UTF-8"));

			String rowData = br.readLine();
			rowData = rowData.replace(Integer.toString(0x00), "");

			bw.write(rowData);
			bw.newLine();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				if(br != null) {
					br.close();
				}
				if(bw != null) {
					bw.flush();
					bw.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
