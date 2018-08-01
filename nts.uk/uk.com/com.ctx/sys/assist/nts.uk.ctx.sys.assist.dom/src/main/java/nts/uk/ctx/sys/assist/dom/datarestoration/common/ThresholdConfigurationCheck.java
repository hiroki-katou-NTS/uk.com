package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

@Stateless
public class ThresholdConfigurationCheck {
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;
	private static final String EXTENSION = ".csv";

	// アルゴリズム「テーブル一覧の復元」を実行する
	public ServerPrepareMng checkFileConfiguration(ServerPrepareMng serverPrepareMng, List<TableList> tableList) {
		if(checkFileConfigError(serverPrepareMng, tableList)){
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.FILE_CONFIG_ERROR);
			serverPrepareMngRepository.update(serverPrepareMng);
		}
		return serverPrepareMng;
	}

	private boolean checkFileConfigError(ServerPrepareMng serverPrepareMng, List<TableList> tableList) {
		if (tableList.isEmpty())
			return true;
		String csvStoragePath = CsvFileUtil.getExtractDataStoragePath(serverPrepareMng.getFileId().get());
		File f = new File(csvStoragePath);
		if (!f.exists())
			return true;
		// Number of line without header in tablelist must be equals number of csv not include tablelist and target csv
		List<String> listFileName = Arrays.asList(f.list());
		if (tableList.size() != listFileName.size() - 2)
			return true;
		String tableJapanName = "";
		for (TableList tableListItem : tableList) {
			tableJapanName = tableListItem.getInternalFileName();
			if (!listFileName.contains(tableJapanName + EXTENSION)) {
				return true;
			}
		}
		return false;
	}
}
