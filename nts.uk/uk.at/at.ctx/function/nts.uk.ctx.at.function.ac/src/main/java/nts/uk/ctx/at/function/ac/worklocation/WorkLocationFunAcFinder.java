package nts.uk.ctx.at.function.ac.worklocation;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.worklocation.RecordWorkInfoFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.worklocation.RecordWorkInfoFunAdapterDto;
import nts.uk.ctx.at.record.pub.workinformation.InfoCheckNotRegisterPubExport;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPub;

@Stateless
public class WorkLocationFunAcFinder implements RecordWorkInfoFunAdapter {

	@Inject
	private RecordWorkInfoPub recordWorkInfoPub;

	@Override
	public Optional<RecordWorkInfoFunAdapterDto> getInfoCheckNotRegister(String employeeId, GeneralDate ymd) {
		Optional<InfoCheckNotRegisterPubExport> data = recordWorkInfoPub.getInfoCheckNotRegister(employeeId, ymd);
		if(data.isPresent())
			return Optional.of(convertToExport(data.get()));
		return Optional.empty();
	}
	
	private RecordWorkInfoFunAdapterDto convertToExport(InfoCheckNotRegisterPubExport export) {
		return new  RecordWorkInfoFunAdapterDto(
					export.getEmployeeId(),
					export.getWorkTimeCode()==null?null:export.getWorkTimeCode(),
					export.getWorkTypeCode()
				);
	}

	@Override
	public Optional<String> getWorkTypeCode(String employeeId, GeneralDate ymd) {		
		return recordWorkInfoPub.getWorkTypeCode(employeeId, ymd);
	}


}
