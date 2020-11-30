package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal.setting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting.TimeRecordReqSettingPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting.dto.TimeRecordReqSettingExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting.dto.TimeRecordReqSettingExport.ReqSettingBuilder;

/**
 * @author ThanhNX
 *
 *         就業情報端末のリクエスト一覧Pub
 */
@Stateless
public class TimeRecordReqSettingPubImpl implements TimeRecordReqSettingPub {

	@Inject
	private TimeRecordReqSettingRepository repository;

	@Override
	public Optional<TimeRecordReqSettingExport> getTimeRecordReqSetting(String empInfoTerCode, String contractCode) {
		return repository
				.getTimeRecordReqSetting(new EmpInfoTerminalCode(empInfoTerCode), new ContractCode(contractCode))
				.map(x -> convertTo(x));
	}

	private TimeRecordReqSettingExport convertTo(TimeRecordReqSetting setting) {

		return new ReqSettingBuilder(setting.getTerminalCode().v(), setting.getCompanyCode(), setting.getCompanyId(),
				setting.getCompanyCode(), setting.getEmployeeIds(), setting.getBentoMenuFrameNumbers(),
				setting.getWorkTypeCodes()).workTime(setting.getWorkTimeCodes())
						.overTimeHoliday(setting.isOverTimeHoliday()).applicationReason(setting.isApplicationReason())
						.stampReceive(setting.isStampReceive()).reservationReceive(setting.isReservationReceive())
						.applicationReceive(setting.isApplicationReceive()).timeSetting(setting.isTimeSetting())
						.sendEmployeeId(setting.isSendEmployeeId()).sendBentoMenu(setting.isSendBentoMenu())
						.sendWorkType(setting.isSendWorkType()).sendWorkTime(setting.isSendWorkTime()).build();
	}

}
