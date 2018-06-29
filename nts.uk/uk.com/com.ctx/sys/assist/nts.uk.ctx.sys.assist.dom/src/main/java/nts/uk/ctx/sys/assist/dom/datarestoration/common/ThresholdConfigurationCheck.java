package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

@Stateless
public class ThresholdConfigurationCheck {

	private static final String EXTENSION = ".csv";
	// アルゴリズム「テーブル一覧の復元」を実行する
	public ServerPrepareMng checkFileConfiguration(ServerPrepareMng serverPrepareMng, List<TableList> tableList){
		boolean fileConfigError = false;
		if (!tableList.isEmpty()){
			String csvStoragePath = CsvFileUtil.getCsvStoragePath(serverPrepareMng.getFileId().get());
			File f = new File(csvStoragePath);
			if (f.exists()){
				List<String> listFileName = Arrays.asList(f.list());
				if (tableList.size() == listFileName.size() -2){
					for(TableList tableListItem: tableList){
						String tableJapanName = tableListItem.getInternalFileName();
						if(!listFileName.contains(tableJapanName + EXTENSION)){
							fileConfigError = true;
							break;
						}
					}
				} else fileConfigError = true;
			} else fileConfigError = true;
		}
		if (fileConfigError){
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.FILE_CONFIG_ERROR);
		}
		return serverPrepareMng;
	}
}
