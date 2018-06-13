package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;

@Stateless
public class TableListRestorationService {
	private static final String FILE_NAME_CSV1 = "保存対象テーブル一覧";
	private static final String FILE_NAME_CSV2 = "対象社員";
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;
	@Inject
	private FileUtil fileUtil;
	public PerformDataRecovery restoreTableList(ServerPrepareMng serverPrepareMng){
		PerformDataRecovery performDataRecovery = null;
		String dataStoragePath = "packs" + serverPrepareMng.getFileId().get();
		String tableListPath = dataStoragePath + FILE_NAME_CSV1 + ".csv";
		try {
			InputStream inputStream = new FileInputStream(new File(tableListPath));
			List<List<String>> tableListContent = fileUtil.getAllRecord(inputStream, 3);
		} catch (FileNotFoundException e) {
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.TABLE_LIST_FAULT);
			serverPrepareMngRepository.update(serverPrepareMng);
		}
		return performDataRecovery;
	}
}
