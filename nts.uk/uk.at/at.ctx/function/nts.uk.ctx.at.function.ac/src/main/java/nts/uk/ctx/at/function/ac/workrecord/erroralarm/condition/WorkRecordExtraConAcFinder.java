package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapter;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapterDto;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.WorkRecordExtraConPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.WorkRecordExtraConPubExport;
/**
 * 
 * @author tutk
 *
 */
@Stateless
public class WorkRecordExtraConAcFinder implements WorkRecordExtraConAdapter {

	@Inject
	private WorkRecordExtraConPub repo;
	
	@Override
	public List<WorkRecordExtraConAdapterDto> getAllWorkRecordExtraConByListID(List<String> listErrorAlarmID) {
		List<WorkRecordExtraConAdapterDto> data = repo.getAllWorkRecordExtraConByListID(listErrorAlarmID)
				.stream().map(c->convertToImport(c)).collect(Collectors.toList());
		return data;
	}

	@Override
	public WorkRecordExtraConAdapterDto getWorkRecordExtraConById(String errorAlarmCheckID) {
		WorkRecordExtraConAdapterDto data = convertToImport(repo.getWorkRecordExtraConById(errorAlarmCheckID));
		return data;
	}
	
	private WorkRecordExtraConAdapterDto convertToImport( WorkRecordExtraConPubExport export ) {
		return new WorkRecordExtraConAdapterDto(
				export.getErrorAlarmCheckID(),
				export.getCheckItem(),
				export.isMessageBold(),
				export.getMessageColor(),
				export.getSortOrderBy(),
				export.isUseAtr(),
				export.getNameWKRecord()
				);
	}

}
