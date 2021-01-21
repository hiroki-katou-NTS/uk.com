package nts.uk.ctx.at.function.ac.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendNRDataAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendOvertimeNameImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendOvertimeNameImport.SendOvertimeDetailImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendPerInfoNameImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendReasonApplicationImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendReservationMenuImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendTimeInfomationImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendTimeRecordSettingImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendTimeRecordSettingImport.SettingImportBuilder;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendWorkTimeNameImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendWorkTypeNameImport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendNRDataPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendOvertimeNamePub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendPerInfoNameExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendReasonApplicationExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendReservationMenuExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendSystemTimePub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendTimeInfomationExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendTimeRecordSettingPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendWorkTimeNameExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendWorkTypeNameExport;

/**
 * @author ThanhNX
 *
 */
@Stateless
public class SendNRDataAdapterImpl implements SendNRDataAdapter {

	@Inject
	private SendNRDataPub<List<SendPerInfoNameExport>> sendPerInfoNamePub;

	@Inject
	private SendNRDataPub<List<SendReasonApplicationExport>> sendReasonApplicationPub;

	@Inject
	private SendNRDataPub<List<SendReservationMenuExport>> sendReservationMenuPub;

	@Inject
	private SendNRDataPub<List<SendWorkTimeNameExport>> sendWorkTimeNamePub;

	@Inject
	private SendNRDataPub<List<SendWorkTypeNameExport>> sendWorkTypeNamePub;

	@Inject
	private SendOvertimeNamePub sendOvertimeNamePub;

	@Inject
	private SendTimeRecordSettingPub sendTimeRecordSettingPub;

	@Inject
	private SendSystemTimePub sendSystemTimePub;

	@Override
	public Optional<SendOvertimeNameImport> sendOvertime(Integer empInfoTerCode, String contractCode) {

		return sendOvertimeNamePub.send(empInfoTerCode, contractCode).map(x -> {
			List<SendOvertimeDetailImport> overtimes = x.getOvertimes().stream()
					.map(y -> new SendOvertimeDetailImport(y.getSendOvertimeNo(), y.getSendOvertimeName()))
					.collect(Collectors.toList());

			List<SendOvertimeDetailImport> vacations = x.getVacations().stream()
					.map(y -> new SendOvertimeDetailImport(y.getSendOvertimeNo(), y.getSendOvertimeName()))
					.collect(Collectors.toList());

			return new SendOvertimeNameImport(overtimes, vacations);
		});
	}

	@Override
	public List<SendPerInfoNameImport> sendPerInfo(Integer empInfoTerCode, String contractCode) {
		return sendPerInfoNamePub
				.send(empInfoTerCode, contractCode).stream().map(x -> new SendPerInfoNameImport(x.getIdNumber(),
						x.getPerName(), x.getDepartmentCode(), x.getCompanyCode(), x.getReservation(), x.getPerCode()))
				.collect(Collectors.toList());
	}

	@Override
	public List<SendReasonApplicationImport> sendReasonApp(Integer empInfoTerCode, String contractCode) {
		return sendReasonApplicationPub.send(empInfoTerCode, contractCode).stream().map(x -> {
			return new SendReasonApplicationImport(x.getAppReasonNo(), x.getAppReasonName());
		}).collect(Collectors.toList());
	}

	@Override
	public List<SendReservationMenuImport> sendReservMenu(Integer empInfoTerCode, String contractCode) {
		return sendReservationMenuPub.send(empInfoTerCode, contractCode).stream()
				.map(x -> new SendReservationMenuImport(x.getBentoMenu(), x.getUnit(), x.getFrameNumber()))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<SendTimeRecordSettingImport> sendTimeRecordSetting(Integer empInfoTerCode, String contractCode) {
		return sendTimeRecordSettingPub.send(empInfoTerCode, contractCode).map(setting -> {
			return new SendTimeRecordSettingImport(new SettingImportBuilder(setting.isRequest1(), setting.isRequest2(),
					setting.isRequest3(), setting.isRequest4(), setting.isRequest6()).createReq7(setting.isRequest7())
							.createReq8(setting.isRequest8()).createReq9(setting.isRequest9())
							.createReq10(setting.isRequest10()).createReq11(setting.isRequest11()));
		});
	}

	@Override
	public List<SendWorkTimeNameImport> sendWorkTime(Integer empInfoTerCode, String contractCode) {
		return sendWorkTimeNamePub.send(empInfoTerCode, contractCode).stream().map(x -> {
			return new SendWorkTimeNameImport(x.getWorkTimeNumber(), x.getWorkTimeName(), x.getTime());
		}).collect(Collectors.toList());
	}

	@Override
	public List<SendWorkTypeNameImport> sendWorkType(Integer empInfoTerCode, String contractCode) {
		return sendWorkTypeNamePub.send(empInfoTerCode, contractCode).stream()
				.map(x -> new SendWorkTypeNameImport(x.getWorkTypeNumber(), x.getDaiClassifiNum(), x.getWorkName()))
				.collect(Collectors.toList());
	}

	@Override
	public SendTimeInfomationImport sendSystemTime() {
		SendTimeInfomationExport info = sendSystemTimePub.send();
		return new SendTimeInfomationImport(info.getYear(), info.getMonth(), info.getDay(), info.getHour(),
				info.getMinute(), info.getSecond(), info.getWeek());
	}

}
