package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.condition;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.DisplayMessages;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtraConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtractingCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.NameWKRecord;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.WorkRecordExtraConPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.WorkRecordExtraConPubExport;

@Stateless
public class WorkRecordExtraConPubImpl implements WorkRecordExtraConPub {

	@Inject
	private WorkRecordExtraConRepository repo;
	
	@Override
	public List<WorkRecordExtraConPubExport> getAllWorkRecordExtraConByListID(List<String> listErrorAlarmID) {
		List<WorkRecordExtraConPubExport> data = repo.getAllWorkRecordExtraConByListID(listErrorAlarmID)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}

	@Override
	public WorkRecordExtraConPubExport getWorkRecordExtraConById(String errorAlarmCheckID) {
		Optional<WorkRecordExtraConPubExport> data = repo.getWorkRecordExtraConById(errorAlarmCheckID).map(c->convertToExport(c));
		if(data.isPresent())
			return data.get();
		return null;
	}
	
	public WorkRecordExtraConPubExport convertToExport(WorkRecordExtractingCondition domain) {
		return new WorkRecordExtraConPubExport(
					domain.getErrorAlarmCheckID(),
					domain.getCheckItem().value,
					domain.getDisplayMessages().isMessageBold(),
					domain.getDisplayMessages().getMessageColor().v(),
					domain.getSortOrderBy(),
					domain.isUseAtr(),
					domain.getNameWKRecord().v()
				);
	}
	
	public WorkRecordExtractingCondition convertToDomain(WorkRecordExtraConPubExport dto) {
		return new WorkRecordExtractingCondition(
				dto.getErrorAlarmCheckID(),
					EnumAdaptor.valueOf(dto.getCheckItem(), TypeCheckWorkRecord.class),
					new DisplayMessages(
							dto.isMessageBold(),
							new ColorCode(dto.getMessageColor())
							),
					dto.getSortOrderBy(),
					dto.isUseAtr(),
					new NameWKRecord(dto.getNameWKRecord())
				);
	}

	@Override
	public void addWorkRecordExtraConPub(WorkRecordExtraConPubExport workRecordExtraConPubExport) {
		this.repo.addWorkRecordExtraCon(convertToDomain(workRecordExtraConPubExport));
	}

	@Override
	public void updateWorkRecordExtraConPub(WorkRecordExtraConPubExport workRecordExtraConPubExport) {
		this.repo.updateWorkRecordExtraCon(convertToDomain(workRecordExtraConPubExport));
	}

	@Override
	public void deleteWorkRecordExtraConPub(List<String> errorAlarmCheckID) {
		for(String errorID : errorAlarmCheckID) {
			this.repo.deleteWorkRecordExtraCon(errorID);
		}
		
	}

}
