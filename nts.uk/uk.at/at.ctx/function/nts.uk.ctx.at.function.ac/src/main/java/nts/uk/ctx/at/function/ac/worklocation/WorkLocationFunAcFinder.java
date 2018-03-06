package nts.uk.ctx.at.function.ac.worklocation;

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
	public RecordWorkInfoFunAdapterDto getInfoCheckNotRegister(String employeeId, GeneralDate ymd) {
		return convertToExport(recordWorkInfoPub.getInfoCheckNotRegister(employeeId, ymd));
	}
	
	private RecordWorkInfoFunAdapterDto convertToExport(InfoCheckNotRegisterPubExport export) {
		return new  RecordWorkInfoFunAdapterDto(
					export.getEmployeeId(),
					export.getWorkTimeCode(),
					export.getWorkTypeCode()
				);
	}


}
