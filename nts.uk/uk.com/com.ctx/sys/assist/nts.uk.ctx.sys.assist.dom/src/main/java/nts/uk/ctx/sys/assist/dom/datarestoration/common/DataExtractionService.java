package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.system.ServerSystemProperties;
import nts.gul.file.archive.ArchiveFormat;
import nts.gul.file.archive.ExtractStatus;
import nts.gul.file.archive.FileArchiver;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;

@Stateless
public class DataExtractionService {
	@Inject
	private StoredFileStreamService fileStreamService;
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;
	private static final String DATA_STORE_PATH = ServerSystemProperties.fileStoragePath();

	// アルゴリズム「ファイル解凍処理」を実行する
	public ServerPrepareMng extractData(ServerPrepareMng serverPrepareMng) {
		String fileId = serverPrepareMng.getFileId().get();
		InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileId);
		Path destinationDirectory = Paths.get(DATA_STORE_PATH + "//packs" + "//" + fileId);
		String password = serverPrepareMng.getPassword().isPresent() ? serverPrepareMng.getPassword().get().v() : "";
		ExtractStatus extractStatus = FileArchiver.create(ArchiveFormat.ZIP).extract(inputStream, password,
				destinationDirectory);
		switch (extractStatus) {
		case SUCCESS:
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.CHECKING_FILE_STRUCTURE);
			break;
		case NOT_VALID_FILE:
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.EXTRACTION_FAILED);
			break;
		case NONE_CORRECT_PASSWORD:
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.PASSWORD_DIFFERENCE);
			break;
		case NEED_PASSWORD:
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.PASSWORD_DIFFERENCE);
			break;
		}
		serverPrepareMngRepository.update(serverPrepareMng);
		return serverPrepareMng;
	}
}
