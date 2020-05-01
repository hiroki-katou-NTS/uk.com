package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;

public interface SendNRDataAdapter {

	public Optional<SendOvertimeNameImport> sendOvertime(Integer empInfoTerCode);

	public List<SendPerInfoNameImport> sendPerInfo(Integer empInfoTerCode);

	public List<SendReasonApplicationImport> sendReasonApp(Integer empInfoTerCode);

	public List<SendReservationMenuImport> sendReservMenu(Integer empInfoTerCode);

	public Optional<SendTimeRecordSettingImport> sendTimeRecordSetting(Integer empInfoTerCode);

	public List<SendWorkTimeNameImport> sendWorkTime(Integer empInfoTerCode);

	public List<SendWorkTypeNameImport> sendWorkType(Integer empInfoTerCode);

}
