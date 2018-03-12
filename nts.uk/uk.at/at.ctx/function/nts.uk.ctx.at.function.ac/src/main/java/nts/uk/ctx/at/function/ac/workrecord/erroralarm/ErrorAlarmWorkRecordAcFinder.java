package nts.uk.ctx.at.function.ac.workrecord.erroralarm;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapterDto;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.ErrorAlarmWorkRecordPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.ErrorAlarmWorkRecordPubExport;
@Stateless
public class ErrorAlarmWorkRecordAcFinder implements ErrorAlarmWorkRecordAdapter {
	
	@Inject
	private ErrorAlarmWorkRecordPub repo;

	@Override
	public ErrorAlarmWorkRecordAdapterDto findByErrorAlamCheckId(String eralCheckId) {
		ErrorAlarmWorkRecordAdapterDto data = convertToImport(repo.findByErrorAlamCheckId(eralCheckId));
		return data;
	}

	@Override
	public List<ErrorAlarmWorkRecordAdapterDto> findByListErrorAlamCheckId(List<String> listEralCheckId) {
		List<ErrorAlarmWorkRecordAdapterDto> data = repo.findByListErrorAlamCheckId(listEralCheckId)
				.stream().map(c->convertToImport(c)).collect(Collectors.toList());
		return data;
	}
	
	private ErrorAlarmWorkRecordAdapterDto convertToImport(ErrorAlarmWorkRecordPubExport export) {
		return new ErrorAlarmWorkRecordAdapterDto(
				export.getCompanyId(),
				export.getCode(),
				export.getName(),
				export.getFixedAtr(),
				export.getUseAtr(),
				export.getTypeAtr(),
				export.getBoldAtr(),
				export.getMessageColor(),
				export.getCancelableAtr(),
				export.getErrorDisplayItem(),
				export.getCancelRoleId(),
				export.getErrorAlarmCheckID(),
				export.getDisplayMessage()
				);
	}

	@Override
	public List<ErrorAlarmWorkRecordAdapterDto> getAllErrorAlarmWorkRecord(String companyID) {
		List<ErrorAlarmWorkRecordAdapterDto> data = repo.getAllErrorAlarmWorkRecord(companyID)
				.stream().map(c->convertToImport(c)).collect(Collectors.toList());
		return data;
	}

	@Override
	public List<ErrorAlarmWorkRecordAdapterDto> getListErAlByListCode(String companyId, List<String> listCode) {
		List<ErrorAlarmWorkRecordAdapterDto> data = repo.getListErAlByListCode(companyId, listCode)
				.stream().map(c->convertToImport(c)).collect(Collectors.toList());
		return data;
	}
	

}
