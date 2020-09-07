package nts.uk.cnv.app.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.TransactionService;
import nts.uk.cnv.app.command.TableDesignImportCommand;
import nts.uk.cnv.app.command.TableDesignImportCommandHandler;
import nts.uk.cnv.app.dto.ExportToFileDto;
import nts.uk.cnv.app.dto.ImportFromFileDto;
import nts.uk.cnv.app.dto.ImportFromFileResult;
import nts.uk.cnv.app.dto.TableDesignExportDto;
import nts.uk.cnv.dom.service.ExportDdlService;
import nts.uk.cnv.dom.tabledesign.TableDesign;
import nts.uk.cnv.dom.tabledesign.TableDesignRepository;

@Stateless
public class TableDesignerService {

	@Inject
	TableDesignRepository tableDesignRepository;

	@Inject
	ExportDdlService exportDdlService;

	@Inject
	private TableDesignImportCommandHandler handler;

	public String exportDdl(TableDesignExportDto params) {
		RequireImpl require = new RequireImpl(tableDesignRepository);
		return exportDdlService.exportDdl(require, params.getTableName(), params.getType());
	}

	@RequiredArgsConstructor
	private static class RequireImpl implements ExportDdlService.Require {

		private final TableDesignRepository tableDesignRepository;

		@Override
		public Optional<TableDesign> find(String tablename) {
			return tableDesignRepository.find(tablename);
		}
	}

	public ImportFromFileResult importFromFile(ImportFromFileDto params) {
		ImportFromFileResult result = new ImportFromFileResult(0, new ArrayList<>());

		//フォルダからファイルリストを取得する
		File folder = new File(params.getPath().replace("\\\\", "\\").replace("\"", ""));

		if( folder.isFile() ) {
			try {
				Map<String, String> ddl = readFile(folder.toString(), params.getType());
				TableDesignImportCommand command = new TableDesignImportCommand(ddl.get("CREATE TABLE"), ddl.get("CREATE INDEX"), ddl.get("COMMENT"), params.getType());
				handler.handle(command);
				result.increment();

				System.out.println("ファイル名[" + folder.toString() + "]を取り込みました。");
			}
			catch (Exception ex) {
				result.getErroMessages().add("ファイル名[" + folder.toString() + "]の取り込みに失敗しました。:" + ex.getMessage());
				System.out.println("ファイル名[" + folder.toString() + "]の取り込みに失敗しました。:" + ex.getMessage());
			}

			return result;
		}

		File files[] = folder.listFiles();
		if (files == null || files.length == 0) {
			throw new BusinessException("ファイルが存在しません。");
		}

		for (File file : folder.listFiles()) {
			try {
				Map<String, String> ddl = readFile(file.toString(), params.getType());
				TableDesignImportCommand command = new TableDesignImportCommand(ddl.get("CREATE TABLE"), ddl.get("CREATE INDEX"), ddl.get("COMMENT"), params.getType());

				TransactionService.newTran(() -> {
					handler.handle(command);
				});

				result.increment();
				System.out.println("ファイル名[" + file.toString() + "]を取り込みました。");
			}
			catch (Exception ex) {
				result.getErroMessages().add("ファイル名[" + folder.toString() + "]の取り込みに失敗しました。:" + file.getName() + " " + ex.getMessage());
				System.out.println("ファイル名[" + file.toString() + "]の取り込みに失敗しました。:" + file.getName() + " " + ex.getMessage());


				Path dest = Paths.get(folder.toString() + "\\errorlist\\" + file.getName());
				try {
					Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
				}
			}
		}

		return result;
	};

	public int exportToFile(ExportToFileDto params) {
		int total = 0;
		RequireImpl require = new RequireImpl(tableDesignRepository);

		//フォルダを取得する
		File folder = new File(params.getPath().replace("\\\\", "\\").replace("\"", ""));

		if(!folder.exists()) {
			throw new BusinessException("フォルダが存在しません。");
		}

		List<String> tableList = tableDesignRepository.getAllTableList();

		for (String tableName : tableList) {
			String fileName = folder.getPath() + "\\" + tableName + ".sql";
			File file = new File(fileName);
			try {
				FileWriter fileWriter = new FileWriter(file);

				String ddl = exportDdlService.exportDdl(require, tableName, params.getType());

				folder.createNewFile();
				file.createNewFile();
				fileWriter.write(ddl);

				fileWriter.close();

				total += 1;
				System.out.println("テーブル名[" + tableName + "]を出力しました。[" + total + "/" + tableList.size() + "]");

			}
			catch(Exception ex) {
				total += 1;
				System.out.println("テーブル名[" + tableName + "]の出力に失敗しました。[" + total + "/" + tableList.size() + "]:" + ex.getMessage());
			}
		}

		return total;
	};

	private Map<String, String> readFile(String filename, String type) throws IOException {
		Map<String, String> result = new HashMap<>();
		result.put("CREATE TABLE", "");
		result.put("CREATE INDEX", "");
		result.put("COMMENT", "");

		Path path = Paths.get(filename);
		List<String> text = Files.readAllLines(path, StandardCharsets.UTF_16);

		String[] block;
		if(type.equals("sqlserver")) {
			block = String.join("\r\n", text)
					.replace("[int]", "[int(10)]")
					.toUpperCase()
					.replace("[", "")
					.replace("]", "")
					.replaceAll("GO\r\n", ";")
					.replaceAll("GO$", ";")
					.replaceAll("WITH \\(( |[A-Z]|[0-9]|=|,|_)*?\\) ON [A-Z]*?\r\n", "\r\n")
					.replaceAll("WITH \\(( |[A-Z]|[0-9]|=|,|_)*?\\) ON ([A-Z]|,)*?\r\n", ",\r\n")
					.replaceAll("\\) ON [A-Z]*?$", ")")
					.split(";");
		}
		else {
			block = String.join("\r\n", text).split(";");
		}

		if(block.length > 0) {
			for (int i=0; i<block.length; i++) {
				if(block[i].contains("CREATE TABLE")) {
					result.put("CREATE TABLE", block[i]);
				}
				else if(block[i].contains("CREATE") && block[i].contains("INDEX")) {
					if (result.get("CREATE INDEX").isEmpty()) {
						result.put("CREATE INDEX", block[i]);
					}
					else {
						result.put("CREATE INDEX", result.get("CREATE INDEX") + ";\r\n" + block[i]);
					}
				}
				else if(block[i].contains("COMMENT")) {
					if (result.get("COMMENT").isEmpty()) {
						result.put("COMMENT", block[i]);
					}
					else {
						result.put("COMMENT", result.get("COMMENT") + ";\r\n" + block[i]);
					}
				}
			}
		}

		return result;
	}

}
