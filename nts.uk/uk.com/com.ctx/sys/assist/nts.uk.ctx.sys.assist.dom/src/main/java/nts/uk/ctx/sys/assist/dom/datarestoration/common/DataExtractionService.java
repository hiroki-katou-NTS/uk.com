package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.system.ServerSystemProperties;
import nts.gul.file.archive.ArchiveFormat;
import nts.gul.file.archive.FileArchiver;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.shr.infra.file.storage.stream.DefaultStoredFileStreamService;

@Stateless
public class DataExtractionService {
	@Inject
	private StoredFileStreamService fileStreamService;
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;
	private static final String DATA_STORE_PATH = ServerSystemProperties.fileStoragePath();
	// アルゴリズム「ファイル解凍処理」を実行する
	public ServerPrepareMng extractData(ServerPrepareMng serverPrepareMng){
		String fileId = serverPrepareMng.getFileId().get();
		serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.EXTRACTING);
		InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileId);
		Path destinationDirectory = Paths.get(DATA_STORE_PATH + "//packs" + "//" + fileId);
		String password = serverPrepareMng.getPassword().isPresent() ? serverPrepareMng.getPassword().get().v(): null;
		FileArchiver.create(ArchiveFormat.ZIP).extract(inputStream, password, destinationDirectory);
		//処理結果ログを追加する
		//(Bổ sung log kết quả xử ly)
		//TODO
		//Extract return void
		//Waiting fot Kiban
		serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.CHECKING_FILE_STRUCTURE);
		serverPrepareMngRepository.update(serverPrepareMng);
		return serverPrepareMng;
	}
}
