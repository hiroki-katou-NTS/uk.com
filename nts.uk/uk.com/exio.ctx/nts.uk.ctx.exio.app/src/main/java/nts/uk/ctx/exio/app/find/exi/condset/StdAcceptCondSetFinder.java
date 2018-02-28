package nts.uk.ctx.exio.app.find.exi.condset;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.ctx.exio.dom.exi.service.FileUtil;

@Stateless
/**
 * 受入条件設定（定型）
 */
public class StdAcceptCondSetFinder {

	@Inject
	private StdAcceptCondSetRepository finder;

	@Inject
	private StoredFileStreamService fileStreamService;

	public List<StdAcceptCondSetDto> getAllStdAcceptCondSet() {
		return finder.getAllStdAcceptCondSet().stream().map(item -> StdAcceptCondSetDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	public List<StdAcceptCondSetDto> getStdAcceptCondSetBySystemType(int systemType) {
		return finder.getStdAcceptCondSetBySysType(systemType).stream()
				.map(item -> StdAcceptCondSetDto.fromDomain(item)).collect(Collectors.toList());
	}

	public int getTotalRecordCsv(String fileId) {
		int totalRecord = 0;
		try {
			// get input stream by fileId
			InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileId);

			totalRecord = FileUtil.getTotalRecord(inputStream);
			inputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return totalRecord;
	}
}
