package nts.uk.cnv.app.td.service;

import java.io.File;
import java.io.FileWriter;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.error.BusinessException;
import nts.uk.cnv.app.cnv.dto.ExportToFileDto;
import nts.uk.cnv.app.cnv.dto.TableDesignExportDto;
import nts.uk.cnv.dom.td.schema.TableIdentity;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.snapshot.TableListSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.service.ExportDdlService;
import nts.uk.cnv.dom.td.service.ExportDdlServiceResult;

@Stateless
public class ExcportDdlService {

	@Inject
	ExportDdlService exportDdlService;

	@Inject
	private SnapshotRepository ssRepo;

	public ExportDdlServiceResult exportDdl(TableDesignExportDto params) {
		RequireImpl require = new RequireImpl();

		return exportDdlService.exportDdl(
				require, params.getTableName(), params.getType(), params.isWithComment());
	}

	@RequiredArgsConstructor
	private class RequireImpl implements ExportDdlService.Require {
		@Override
		public Optional<TableSnapshot> getLatestTableSnapshot(String tableName) {
			Optional<SchemaSnapshot> sss = ssRepo.getSchemaLatest();
			if(!sss.isPresent()) {
				return Optional.empty();
			}

			TableListSnapshot list = ssRepo.getTableList(sss.get().getSnapshotId());
			Optional<TableIdentity> ti = list.getList().stream()
				.filter(tableIdentity -> tableIdentity.getName().equals(tableName))
				.findFirst();
			if(!ti.isPresent()) {
				return Optional.empty();
			}

			return ssRepo.getTable(sss.get().getSnapshotId(), ti.get().getTableId());
		}

		@Override
		public TableListSnapshot getTableList() {
			Optional<SchemaSnapshot> sss = ssRepo.getSchemaLatest();
			return ssRepo.getTableList(sss.get().getSnapshotId());
		}

		@Override
		public Optional<TableSnapshot> getTable(String snapshotId, String tableId) {
			return ssRepo.getTable(snapshotId, tableId);
		}

	}

	public void exportToFile(ExportToFileDto params) {
		RequireImpl require = new RequireImpl();

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
		Optional<SchemaSnapshot> sss = ssRepo.getSchemaLatest();
		TableListSnapshot list = ssRepo.getTableList(sss.get().getSnapshotId());

		int total = 1;
		for (TableIdentity table : list.getList()) {
			String fileName = folder.getPath() + "\\" + table.getName() + ".sql";
			File file = new File(fileName);
			try {
				FileWriter fileWriter = new FileWriter(file);
				ExportDdlServiceResult result = exportDdlService.exportDdl(
						require, table.getTableId(),
						params.getType(),
						params.isWithComment());

				String ddl = result.getDdl();

				folder.createNewFile();
				file.createNewFile();
				fileWriter.write(ddl);

				fileWriter.close();

				System.out.println("テーブル名[" + table.getName() + "]を出力しました。[" + total + "/" + list.getList().size() + "]");
			}
			catch(Exception ex) {
				System.out.println("テーブル名[" + table.getName() + "]の出力に失敗しました。[" + total + "/" + list.getList().size() + "]:" + ex.getMessage());
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
}
