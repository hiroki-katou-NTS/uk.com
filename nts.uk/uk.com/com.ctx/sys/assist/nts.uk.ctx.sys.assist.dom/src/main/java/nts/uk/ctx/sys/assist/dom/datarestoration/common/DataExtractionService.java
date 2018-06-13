package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.gul.file.archive.ArchiveFormat;
import nts.gul.file.archive.FileArchiver;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;

@Stateless
public class DataExtractionService {
	@Inject
	private StoredFileStreamService fileStreamService;
	private static final String TEMP_PATH = "D://UK//temp";
	private static final String PACK_PATH = "D://UK//temp//packs";
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;
	// アルゴリズム「ファイル解凍処理」を実行する
	public ServerPrepareMng extractData(ServerPrepareMng serverPrepareMng){
		
		String fileId = serverPrepareMng.getFileId().get();
		serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.EXTRACTING);
		InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileId);
		Path destinationDirectory = Paths.get(PACK_PATH + "//" + fileId);
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
