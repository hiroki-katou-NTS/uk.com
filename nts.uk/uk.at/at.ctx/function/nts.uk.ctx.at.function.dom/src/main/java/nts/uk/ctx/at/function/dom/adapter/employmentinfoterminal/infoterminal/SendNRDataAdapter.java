package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;

public interface SendNRDataAdapter {

	public Optional<SendOvertimeNameImport> sendOvertime(Integer empInfoTerCode, String contractCode);

	public List<SendPerInfoNameImport> sendPerInfo(Integer empInfoTerCode, String contractCode);

	public List<SendReasonApplicationImport> sendReasonApp(Integer empInfoTerCode, String contractCode);

	public List<SendReservationMenuImport> sendReservMenu(Integer empInfoTerCode, String contractCode);

	public Optional<SendTimeRecordSettingImport> sendTimeRecordSetting(Integer empInfoTerCode, String contractCode);

	public List<SendWorkTimeNameImport> sendWorkTime(Integer empInfoTerCode, String contractCode);

	public List<SendWorkTypeNameImport> sendWorkType(Integer empInfoTerCode, String contractCode);
	
	public SendTimeInfomationImport sendSystemTime();

}
