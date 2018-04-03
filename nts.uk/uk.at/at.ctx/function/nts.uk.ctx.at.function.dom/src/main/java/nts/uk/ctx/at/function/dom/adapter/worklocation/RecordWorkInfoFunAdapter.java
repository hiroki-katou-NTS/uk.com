package nts.uk.ctx.at.function.dom.adapter.worklocation;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface RecordWorkInfoFunAdapter {
	public Optional<RecordWorkInfoFunAdapterDto>  getInfoCheckNotRegister(String employeeId, GeneralDate ymd);
	public Optional<String> getWorkTypeCode(String employeeId, GeneralDate ymd);
}
