package nts.uk.ctx.at.request.ac.record.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting.TimeRecordReqSettingPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting.dto.TimeRecordReqSettingExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.infoterminal.TimeRecordReqSettingAdapter;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;

/**
 * @author ThanhNX
 *
 *         就業情報端末のリクエスト一覧Pub
 */
@Stateless
public class TimeRecordReqSettingAdapterImpl implements TimeRecordReqSettingAdapter {

	@Inject
	private TimeRecordReqSettingPub pub;

	@Override
	public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(String empInfoTerCode, String contractCode) {
		return pub.getTimeRecordReqSetting(empInfoTerCode, contractCode).map(x -> convertTo(x));
	}

	private TimeRecordReqSetting convertTo(TimeRecordReqSettingExport setting) {
		return new ReqSettingBuilder(setting.getTerminalCode(), setting.getCompanyCode(), setting.getCompanyId(),
				setting.getCompanyCode(), setting.getEmployeeIds(), setting.getBentoMenuFrameNumbers(),
				setting.getWorkTypeCodes()).workTime(setting.getWorkTimeCodes())
						.overTimeHoliday(setting.isOverTimeHoliday()).applicationReason(setting.isApplicationReason())
						.stampReceive(setting.isStampReceive()).reservationReceive(setting.isReservationReceive())
						.applicationReceive(setting.isApplicationReceive()).timeSetting(setting.isTimeSetting())
						.sendEmployeeId(setting.isSendEmployeeId()).sendBentoMenu(setting.isSendBentoMenu())
						.sendWorkType(setting.isSendWorkType()).sendWorkTime(setting.isSendWorkTime()).build();
	}
}
