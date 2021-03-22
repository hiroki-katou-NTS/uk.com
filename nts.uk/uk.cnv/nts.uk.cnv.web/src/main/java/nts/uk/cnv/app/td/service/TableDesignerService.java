package nts.uk.cnv.app.td.service;

import static java.util.stream.Collectors.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.TransactionService;
import nts.uk.cnv.app.cnv.command.ErpTableDesignImportCommand;
import nts.uk.cnv.app.cnv.command.ErpTableDesignImportCommandHandler;
import nts.uk.cnv.app.cnv.command.UkTableDesignImportCommand;
import nts.uk.cnv.app.cnv.command.UkTableDesignImportCommandHandler;
import nts.uk.cnv.app.cnv.dto.ExportToFileDto;
import nts.uk.cnv.app.cnv.dto.GetErpColumnsResultDto;
import nts.uk.cnv.app.cnv.dto.GetUkColumnsResultDto;
import nts.uk.cnv.app.cnv.dto.GetUkTablesResultDto;
import nts.uk.cnv.app.cnv.dto.ImportFromFileDto;
import nts.uk.cnv.app.cnv.dto.ImportFromFileResult;
import nts.uk.cnv.app.cnv.dto.TableDesignExportDto;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionTableRepository;
import nts.uk.cnv.dom.cnv.conversiontable.OneColumnConversion;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspect;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.tabledesign.ErpTableDesignRepository;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.UkTableDesignRepository;
import nts.uk.cnv.dom.td.service.ExportDdlService;
import nts.uk.cnv.dom.td.service.ExportDdlServiceResult;
import nts.uk.cnv.dom.td.tabledefinetype.UkDataType;
import nts.uk.cnv.dom.td.tabledefinetype.databasetype.SqlServerSpec;
import nts.uk.cnv.ws.table.TableInfoDto;

@Stateless
public class TableDesignerService {

	@Inject
	UkTableDesignRepository ukTableDesignRepository;

	@Inject
	ErpTableDesignRepository erpTableDesignRepository;

	@Inject
	ConversionTableRepository conversionTableRepository;

	@Inject
	ExportDdlService exportDdlService;

	@Inject
	private UkTableDesignImportCommandHandler handler;

	@Inject
	private ErpTableDesignImportCommandHandler handler_erp;

	@Inject
	private TransactionService transactionService;

	@Inject
	private SnapshotRepository ssRepo;

	@Inject
	private AlterationRepository alterationRepo;

	public ExportDdlServiceResult exportDdl(TableDesignExportDto params) {
		RequireImpl require = new RequireImpl(ukTableDesignRepository);

		return exportDdlService.exportDdl(
				require, params.getTableName(), params.getType(), params.isWithComment(), params.getFeature());
	}

	@RequiredArgsConstructor
	private static class RequireImpl implements ExportDdlService.Require {

		private final UkTableDesignRepository tableDesignRepository;

		@Override
		public Optional<TableDesign> find(String tableId, String feature, String eventId) {
			return tableDesignRepository.findByKey(tableId, feature);
		}

		@Override
		public List<TableDesign> findAll(String feature, String eventId) {
			return tableDesignRepository.getAll(feature, eventId);
		}
	}

	@SuppressWarnings("deprecation")
	public ImportFromFileResult importFromFile(ImportFromFileDto params) {
		ImportFromFileResult result = new ImportFromFileResult(0, new ArrayList<>());

		//フォルダからファイルリストを取得する
		File folder = new File(params.getPath().replace("\\\\", "\\").replace("\"", ""));

		if( folder.isFile() ) {
			try {
				List<Map<String, String>> createTableSql = readFile(folder.toString(), params.getType(), false);

				for (Map<String, String> ddl : createTableSql) {
					UkTableDesignImportCommand command = new UkTableDesignImportCommand(
							ddl.get("CREATE TABLE"), ddl.get("CREATE INDEX"), ddl.get("COMMENT"), params.getType(),
							params.getFeature(), params.getDate().toString()
							);
					handler.handle(command);
					result.increment();
				}
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
			if(!file.isFile()) continue;

			String inProcessingSql = "";
			try {
				List<Map<String, String>> createTableSql = readFile(file.toString(), params.getType(), false);

				for (Map<String, String> ddl : createTableSql) {

					inProcessingSql = ddl.get("CREATE TABLE");
					UkTableDesignImportCommand command = new UkTableDesignImportCommand(
							ddl.get("CREATE TABLE"), ddl.get("CREATE INDEX"), ddl.get("COMMENT"), params.getType(),
							params.getFeature(), params.getDate().toString()
						);

					TransactionService.newTran(() -> {
						handler.handle(command);
					});

					result.increment();
				}
				System.out.println("ファイル名[" + file.toString() + "]を取り込みました。");
			}
			catch (Exception ex) {
				result.getErroMessages().add("ファイル名[" + file.toString() + "]の取り込みに失敗しました。:" + inProcessingSql + " " + ex.getMessage());
				System.out.println("ファイル名[" + file.toString() + "]の取り込みに失敗しました。:" + inProcessingSql + " " + ex.getMessage());


				Path dest = Paths.get(folder.toString() + "\\errorlist\\" + file.getName());
				try {
					Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
				}
			}
		}

		return result;
	};

	public void exportToFile(ExportToFileDto params) {
		RequireImpl require = new RequireImpl(ukTableDesignRepository);

		//フォルダを取得する
		File folder = new File(params.getPath().replace("\\\\", "\\").replace("\"", ""));

		if(folder.isDirectory() && !folder.exists()) {
			throw new BusinessException("フォルダが存在しません。");
		}

		if (params.isOneFile()) {
			exportOneFile(params, require, folder, params.getFeature());
		}
		else {
			exportMultipleFile(params, require, folder);
		}

		return;
	}

	private void exportMultipleFile(ExportToFileDto params, RequireImpl require, File folder) {
		// TODO:
		String eventId = "";

		List<GetUkTablesResultDto> tableList = ukTableDesignRepository.getAllTableList(params.getFeature(), eventId);

		int total = 1;
		for (GetUkTablesResultDto table : tableList) {
			String fileName = folder.getPath() + "\\" + table.getTableName() + ".sql";
			File file = new File(fileName);
			try {
				FileWriter fileWriter = new FileWriter(file);
				ExportDdlServiceResult result = exportDdlService.exportDdl(
						require, table.getTableId(),
						params.getType(),
						params.isWithComment(),
						params.getFeature());

				String ddl = result.getDdl();

				folder.createNewFile();
				file.createNewFile();
				fileWriter.write(ddl);

				fileWriter.close();

				System.out.println("テーブル名[" + table.getTableName() + "]を出力しました。[" + total + "/" + tableList.size() + "]");
			}
			catch(Exception ex) {
				System.out.println("テーブル名[" + table.getTableName() + "]の出力に失敗しました。[" + total + "/" + tableList.size() + "]:" + ex.getMessage());
			}
			total++;
		}

		return;
	};

	private void exportOneFile(ExportToFileDto params, RequireImpl require, File folder, String feature) {
		String fileName = folder.isFile()
			? folder.getAbsolutePath()
			: folder.getPath() + "\\" + "all.sql";
		File file = new File(fileName);

		try {
			FileWriter fileWriter = new FileWriter(file);
			String ddl = exportDdlService.exportDdlAll(require, params.getType(), params.isWithComment(), feature);

			folder.createNewFile();
			file.createNewFile();
			fileWriter.write(ddl);

			fileWriter.close();

			System.out.println("ファイル[" + fileName + "]を出力しました。");
		}
		catch(Exception ex) {
			System.out.println("ファイル[" + fileName + "]の出力に失敗しました。");
		}
	}


	public ImportFromFileResult importErpFromFile(ImportFromFileDto params) {

		ImportFromFileResult result = new ImportFromFileResult(0, new ArrayList<>());

		//フォルダからファイルリストを取得する
		File folder = new File(params.getPath().replace("\\\\", "\\").replace("\"", ""));

		if( folder.isFile() ) {
			try {
				List<Map<String, String>> createTableSql = readFile(folder.toString(), params.getType(), true);

				for (Map<String, String> ddl : createTableSql) {
					ErpTableDesignImportCommand command = new ErpTableDesignImportCommand(ddl.get("CREATE TABLE"), ddl.get("CREATE INDEX"), ddl.get("COMMENT"), params.getType());
					handler_erp.handle(command);
					result.increment();
				}
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
			String inProcessingSql = "";
			List<Map<String, String>> createTableSql = new ArrayList<>();
			try {
				createTableSql = readFile(file.toString(), params.getType(), true);
			}
			catch (Exception ex) {
				result.getErroMessages().add("ファイル名[" + file.toString() + "]の取り込みに失敗しました。:" + ex.getMessage());
				System.out.println("ファイル名[" + file.toString() + "]の取り込みに失敗しました。:" + ex.getMessage());

				Path dest = Paths.get(folder.toString() + "\\errorlist\\" + file.getName());
				try {
					Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {

				}
			}

			for (Map<String, String> ddl : createTableSql) {

				try {
					inProcessingSql = ddl.get("CREATE TABLE");
					ErpTableDesignImportCommand command = new ErpTableDesignImportCommand(ddl.get("CREATE TABLE"), ddl.get("CREATE INDEX"), ddl.get("COMMENT"), params.getType());

					transactionService.execute(() -> {
						handler_erp.handle(command);
					});

					result.increment();
				}
				catch(Exception ex) {
					result.getErroMessages().add("以下のSQLの取り込みに失敗しました。:\r\n" + inProcessingSql + " " + ex.getMessage());
					System.out.println("以下のSQLの取り込みに失敗しました。:\r\n" + inProcessingSql + " " + ex.getMessage());
				}
			}
			System.out.println("ファイル名[" + file.toString() + "]を取り込みました。");
		}
		return result;
	}

	private List<Map<String, String>> readFile(String filename, String type, boolean onlyCreateTable) throws IOException {

		Path path = Paths.get(filename);
		List<String> text = Files.readAllLines(path, StandardCharsets.UTF_16);

		List<String> createTablesqls =
			Arrays.stream(
				String.join("\r\n", text)
				.replace("[int]", "[int(10)]")
				.replace("[", "")
				.replace("]", "")
				//.replaceAll("CHECK \\(( |[A-Z]|[a-z]|[0-9]|=|>|<|_)*?\\)", "")
				.split("CREATE TABLE")
			)
			.filter(s -> !s.isEmpty())
			.map(s -> "CREATE TABLE" + s)
			.collect(Collectors.toList());

		List<Map<String, String>> resultList = new ArrayList<>();
		for (String sentence : createTablesqls) {
			Map<String, String> result = new HashMap<>();
			result.put("CREATE TABLE", "");
			result.put("CREATE INDEX", "");
			result.put("COMMENT", "");

			String[] block;
			if(type.equals("sqlserver")) {
				block = sentence
						.replaceAll("GO\r\n", ";")
						.replaceAll("GO$", ";")
						.replaceAll("WITH \\(( |[A-Z]|[a-z]|[0-9]|=|,|_)*?\\) ON ([A-Z]|[a-z])*?\r\n", "\r\n")
						.replaceAll("WITH \\(( |[A-Z]|[a-z]|[0-9]|=|,|_)*?\\) ON ([A-Z]|[a-z]|,)*?\r\n", ",\r\n")
						.replaceAll("\\) ON [A-Z]*", ")")
						.split(";");
			}
			else {
				block = String.join("\r\n", sentence).split(";");
			}

			if(block.length > 0) {
				for (int i=0; i<block.length; i++) {
					if(block[i].contains("CREATE TABLE")) {
						result.put("CREATE TABLE", block[i] + ";");
					}
					else if(!onlyCreateTable && block[i].contains("CREATE") && block[i].contains("INDEX")) {
						if (result.get("CREATE INDEX").isEmpty()) {
							result.put("CREATE INDEX", block[i] + ";");
						}
						else {
							result.put("CREATE INDEX", result.get("CREATE INDEX") + block[i] + ";");
						}
					}
					else if(!onlyCreateTable && block[i].contains("COMMENT")) {
						if (result.get("COMMENT").isEmpty()) {
							result.put("COMMENT", block[i] + ";");
						}
						else {
							result.put("COMMENT", result.get("COMMENT") + block[i] + ";");
						}
					}
				}
			}

			resultList.add(result);
		}

		return resultList;
	}

	public List<TableInfoDto> getProspect() {
//		Map<String, TableDesign> tableDesignList = ssRepo.getNewestAll().stream()
//				.collect(Collectors.toMap(td -> td.getId(), td -> td));
		Map<String, TableDesign> tableDesignList = null;
		// テーブル追加・削除・テーブル名変更のおるたのみ抽出
		Map<String, List<Alteration>> altarations = alterationRepo.getTableListChange().stream()
				.collect(Collectors.groupingBy(alt -> alt.getTableId()));

		altarations.keySet().stream()
			.forEach(tableId -> {
				TableProspectBuilder builder = tableDesignList.containsKey(tableId)
						? new TableProspectBuilder(tableDesignList.get(tableId))
						: TableProspectBuilder.empty();
				altarations.get(tableId).stream()
					.forEach(alt -> {
						alt.apply(builder);
				});
				Optional<TableProspect> alterd = builder.build();
				if(alterd.isPresent()) {
					tableDesignList.put(tableId, alterd.get());
				}
				else {
					tableDesignList.remove(tableId);
				}
			});

		return tableDesignList.entrySet().stream()
				.map(es -> new TableInfoDto(
						es.getValue().getId(),
						es.getValue().getName().v()))
				.collect(toList());
	}

	public List<String> getErpTables() {
		return erpTableDesignRepository.getAllTableList();
	}

	public List<GetUkColumnsResultDto> getUkColumns(String category, String tableId, int recordNo, String feature) {
		String id = tableId.replace("\"", "");
		TableDesign td = ukTableDesignRepository.findByKey(id, feature)
				.orElseThrow(RuntimeException::new);

		UkDataType dataType = new UkDataType();

		List<OneColumnConversion> conversionTable = conversionTableRepository.find(category, td.getName().v(), recordNo);

		return td.getColumns().stream()
				.map(col -> {
					boolean exists = conversionTable.stream()
						.anyMatch(ct -> ct.getTargetColumn().equals(col.getName()));
					return new GetUkColumnsResultDto(
						col.getId(),
						col.getName(),
						dataType.dataType(col.getType().getDataType(), col.getType().getLength(), col.getType().getScale()),
						exists
					);
				})
				.collect(Collectors.toList());
	}

	public List<GetErpColumnsResultDto> getErpColumns(String tableName) {
		String name = tableName.replace("\"", "");
		TableDesign td = erpTableDesignRepository.find(name)
				.orElseThrow(RuntimeException::new);

		SqlServerSpec spec = new SqlServerSpec();

		return td.getColumns().stream()
				.map(col -> new GetErpColumnsResultDto(
						col.getId(),
						col.getName(),
						spec.dataType(col.getType().getDataType(), col.getType().getLength(), col.getType().getScale())
					))
				.collect(Collectors.toList());
	}

}
