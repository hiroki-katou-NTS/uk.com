package nts.uk.cnv.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.uk.cnv.app.command.TableDesignImportCommand;
import nts.uk.cnv.app.command.TableDesignImportCommandHandler;
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

	public void importFromFile(String folderpath) {

		//フォルダからファイルリストを取得する
		File folder = new File("d:¥¥");

		if( folder.isFile() ) {
			try {
				Map<String, String> ddl = readFile(folder.toString());
				TableDesignImportCommand command = new TableDesignImportCommand(ddl.get("CREATE TABLE"), ddl.get("CREATE INDEX"), ddl.get("COMMENT"), "uk");
				handler.handle(command);
			}
			catch (Exception ex) {
				System.out.println("ファイル名[" + folder.toString() + "]の取り込みに失敗しました。:" + ex.getMessage());
			}
			finally{
				System.out.println("ファイル名[" + folder.toString() + "]を取り込みました。");
			}
			return;
		}

		File files[] = folder.listFiles();
		if (files == null || files.length == 0) {
			System.out.println("ファイルが存在しません。");
			return;
		}

		for (File file : folder.listFiles()) {
			try {
				Map<String, String> ddl = readFile(file.toString());
				TableDesignImportCommand command = new TableDesignImportCommand(ddl.get("CREATE TABLE"), ddl.get("CREATE INDEX"), ddl.get("COMMENT"), "uk");

				handler.handle(command);
			}
			catch (Exception ex) {
				System.out.println("ファイル名[" + file.toString() + "]の取り込みに失敗しました。:" + ex.getMessage());
			}
			finally{
				System.out.println("ファイル名[" + file.toString() + "]を取り込みました。");
			}
		}

		return;
	};

	public Map<String, String> readFile(String filename) throws IOException {
		Map<String, String> result = new HashMap<>();
		result.put("CREATE TABLE", "");
		result.put("CREATE INDEX", "");
		result.put("COMMENT", "");

		Path path = Paths.get(filename);
		List<String> text = Files.readAllLines(path); // UTF-8

		String[] block = String.join("\r\n", text).split(";");
		if (block.length > 2) {
			result.put("CREATE INDEX", block[1]);
			result.put("COMMENT", block[2]);
		}
		else if (block.length == 2) {
			if(block[1].contains("COMMENT")) {
				result.put("COMMENT", block[1]);
			}
			else {
				result.put("CREATE INDEX", block[1]);
			}
		}

		if(block.length > 0) {
			result.put("CREATE TABLE", block[0]);
		}

		return result;
	}

}
