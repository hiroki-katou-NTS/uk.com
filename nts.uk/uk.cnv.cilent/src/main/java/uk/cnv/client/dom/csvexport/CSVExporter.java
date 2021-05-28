package uk.cnv.client.dom.csvexport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
	private static final int QUERY_MAX_LENGTH = 8191;

	private GetAllUkWorkTablesQueryRepositoryImpl repo;

	public CSVExporter() {
		repo = new GetAllUkWorkTablesQueryRepositoryImpl();
	}

	public CommandResult doWork() {
		val executor = new CommandExecutor();

		Path csvDir = Paths.get(UkConvertProperty.getProperty(UkConvertProperty.CSV_DIR));
		File csvFolder = csvDir.toFile();
		if(csvFolder.exists()) {
			// 既に存在していたら中身を一度消す
			deleteFolder(csvFolder);
		}
		else if(!csvFolder.mkdir()) {
			return new CommandResult(new RuntimeException("フォルダの生成に失敗しました。"));
		}

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

			convertString(csvFile);
		}

		if(errorList.size() > 0) {
			LogManager.err(String.format("%d件中%d件でエラーがありました。", queryList.keySet().size(), errorList.size()));
		}

		return new CommandResult();
	}

	private void deleteFolder(File targetFolder) {
		String[] list = targetFolder.list();
        for(String file : list) {
        	Path filePath = targetFolder.toPath().resolve(file);
            File f = filePath.toFile();
            if(f.isDirectory()) {
            	deleteFolder(f);
            }else {
                f.delete();
            }
        }
        targetFolder.delete();
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

	private void convertString(String csvFileName) {
		String originalFileName = csvFileName.replace(".csv", "_original.csv");
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			Path csvFile = Paths.get(csvFileName);
			Path originalFile = Paths.get(originalFileName);
			Files.copy(csvFile, originalFile, StandardCopyOption.REPLACE_EXISTING);

			// 文字コードの変更
			br = new BufferedReader(new InputStreamReader(new FileInputStream(originalFileName), "UTF-16"));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFileName, false),"UTF-8"));

			String rowData;
			while((rowData = br.readLine()) != null) {
				// 長さ0文字列が0x00で出力されてしまうため、取り除く処理
				byte[] bytes = rowData.getBytes();
				for (byte b : bytes) {
					if (b == 0x00) {
						b = (byte)"".charAt(0);
					}
				}

				bw.write(new String(bytes));
				bw.newLine();
			}

			br.close();
			bw.close();
			Files.delete(originalFile);
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
