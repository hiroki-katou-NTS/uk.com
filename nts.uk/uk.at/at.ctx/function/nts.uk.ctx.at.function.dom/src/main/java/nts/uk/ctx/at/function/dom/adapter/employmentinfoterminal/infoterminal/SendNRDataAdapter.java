package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;

public interface SendNRDataAdapter {

	public Optional<SendOvertimeNameImport> sendOvertime(String empInfoTerCode, String contractCode);

	public List<SendPerInfoNameImport> sendPerInfo(String empInfoTerCode, String contractCode);

	public List<SendReasonApplicationImport> sendReasonApp(String empInfoTerCode, String contractCode);

	public List<SendReservationMenuImport> sendReservMenu(String empInfoTerCode, String contractCode);

	public Optional<SendTimeRecordSettingImport> sendTimeRecordSetting(String empInfoTerCode, String contractCode);

	public List<SendWorkTimeNameImport> sendWorkTime(String empInfoTerCode, String contractCode);

	public List<SendWorkTypeNameImport> sendWorkType(String empInfoTerCode, String contractCode);
	
	public SendTimeInfomationImport sendSystemTime();

}
