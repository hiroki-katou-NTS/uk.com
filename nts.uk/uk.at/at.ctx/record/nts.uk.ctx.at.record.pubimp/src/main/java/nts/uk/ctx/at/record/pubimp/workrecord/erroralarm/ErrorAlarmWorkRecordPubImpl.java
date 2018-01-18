package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.ErrorAlarmWorkRecordPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.ErrorAlarmWorkRecordPubExport;
@Stateless
public class ErrorAlarmWorkRecordPubImpl implements ErrorAlarmWorkRecordPub {
	
	@Inject
	private ErrorAlarmWorkRecordRepository repo;

	@Override
	public ErrorAlarmWorkRecordPubExport findByErrorAlamCheckId(String eralCheckId) {
		Optional<ErrorAlarmWorkRecordPubExport> data = repo.findByErrorAlamCheckId(eralCheckId).map(c->convertToExport(c));
		if(data.isPresent())
			return data.get();
		return null;
	}

	@Override
	public List<ErrorAlarmWorkRecordPubExport> findByListErrorAlamCheckId(List<String> listEralCheckId) {
		List<ErrorAlarmWorkRecordPubExport> data = repo.findByListErrorAlamCheckId(listEralCheckId)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	
	private ErrorAlarmWorkRecordPubExport convertToExport(ErrorAlarmWorkRecord domain) {
		return new ErrorAlarmWorkRecordPubExport(
				domain.getCompanyId(),
				domain.getCode().v(),
				domain.getName().v(),
				domain.getFixedAtr()?1:0,
				domain.getUseAtr()?1:0,
				domain.getTypeAtr().value,
				domain.getMessage().getBoldAtr()?1:0,
				domain.getMessage().getMessageColor().v(),
				domain.getCancelableAtr()?1:0,
				Integer.valueOf(domain.getErrorDisplayItem().intValueExact()),
				domain.getCancelRoleId(),
				domain.getErrorAlarmCheckID()
				);
	}
}
