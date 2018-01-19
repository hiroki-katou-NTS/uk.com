package nts.uk.ctx.at.function.ac.workrecord.erroralarm;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapterDto;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.FixedConWorkRecordPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.FixedConWorkRecordPubExport;

@Stateless
public class FixedConWorkRecordAcFinder implements FixedConWorkRecordAdapter {

	@Inject
	private FixedConWorkRecordPub fixedConWorkRecordPub;

	@Override
	public List<FixedConWorkRecordAdapterDto> getAllFixedConWorkRecordByListID(List<String> listErrorAlarmID) {
		List<FixedConWorkRecordAdapterDto> data = fixedConWorkRecordPub.getAllFixedConWorkRecordByListID(listErrorAlarmID)
				.stream().map(c->convertToImport(c)).collect(Collectors.toList());
		return data;
	}

	@Override
	public FixedConWorkRecordAdapterDto getFixedConWRByCode(String errorAlarmID) {
		FixedConWorkRecordAdapterDto data = convertToImport(fixedConWorkRecordPub.getFixedConWRByCode(errorAlarmID));
		return data;
	}
	
	private FixedConWorkRecordAdapterDto convertToImport(FixedConWorkRecordPubExport export) {
		return new FixedConWorkRecordAdapterDto(
				export.getErrorAlarmID(),
				export.getFixConWorkRecordNo(),
				export.getMessage(),
				export.isUseAtr()
				);
	}
	
	private FixedConWorkRecordPubExport convertToExport(FixedConWorkRecordAdapterDto dto) {
		return new FixedConWorkRecordPubExport(
				dto.getErrorAlarmID(),
				dto.getFixConWorkRecordNo(),
				dto.getMessage(),
				dto.isUseAtr()
				);
	}

	@Override
	public void addFixedConWorkRecordPub(FixedConWorkRecordAdapterDto fixedConWorkRecordAdapterDto) {
		this.fixedConWorkRecordPub.addFixedConWorkRecordPub(convertToExport(fixedConWorkRecordAdapterDto));
	}

	@Override
	public void updateFixedConWorkRecordPub(FixedConWorkRecordAdapterDto fixedConWorkRecordAdapterDto) {
		this.fixedConWorkRecordPub.updateFixedConWorkRecordPub(convertToExport(fixedConWorkRecordAdapterDto));
	}

	@Override
	public void deleteFixedConWorkRecordPub(List<String> errorAlarmID) {
		this.fixedConWorkRecordPub.deleteFixedConWorkRecordPub(errorAlarmID);
	}
	
	
}
