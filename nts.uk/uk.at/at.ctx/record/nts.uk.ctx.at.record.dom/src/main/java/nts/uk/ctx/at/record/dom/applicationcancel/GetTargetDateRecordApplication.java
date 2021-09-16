package nts.uk.ctx.at.record.dom.applicationcancel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.AuthcMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.ReflectDataStampDailyService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppRecordImageShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.EngraveShareAtr;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * @author thanh_nx
 *
 *         レコーダイメージ申請の対象日を取得する
 */
public class GetTargetDateRecordApplication {

	public static Pair<Optional<GeneralDate>, Optional<Stamp>> getTargetDate(Require require, String cid, 
			AppRecordImageShare applicaton) {
		// 打刻カード番号を取得する
		List<StampCard> lstCard = require.getLstStampCardBySidAndContractCd(applicaton.getEmployeeID());

		if (lstCard.isEmpty())
			return Pair.of(Optional.empty(), Optional.empty());
		GeneralDate appDate = applicaton.getAppDate().getApplicationDate();
		int time = applicaton.getAttendanceTime().v();
		if (time >= 1440) {
			appDate.addDays(1);
			time -= 1440;
		}
		Stamp stamp = new Stamp(lstCard.get(0).getContractCd(), lstCard.get(0).getStampNumber(),
				GeneralDateTime.localDateTime(
						LocalDateTime.of(appDate.year(), appDate.month(), appDate.day(), time / 60, time % 60)),
				new Relieve(AuthcMethod.ID_AUTHC, StampMeans.NAME_SELECTION),
				new StampType(false,
						applicaton.getAppStampGoOutAtr().map(x -> EnumAdaptor.valueOf(x.value, GoingOutReason.class)),
						SetPreClockArt.NONE, convertToChangeClockArt(applicaton.getAppStampCombinationAtr()), ChangeCalArt.NONE),
				new RefectActualResult(null, null, null), Optional.empty());

		Optional<GeneralDate> date = ReflectDataStampDailyService.getJudgment(require, cid, applicaton.getEmployeeID(),
				stamp);
		return Pair.of(date, Optional.of(stamp));

	}
	
	private static ChangeClockArt convertToChangeClockArt(EngraveShareAtr atr) {
		switch (atr) {
		case ATTENDANCE:
		case EARLY:
		case HOLIDAY:
			return ChangeClockArt.GOING_TO_WORK;
		case OFFICE_WORK:
		case OVERTIME:
			return ChangeClockArt.WORKING_OUT;
		case GO_OUT:
			return ChangeClockArt.GO_OUT;
		case RETURN:
			return ChangeClockArt.RETURN;
		default:
			return null;
		}
	}

	public static interface Require extends ReflectDataStampDailyService.Require {

		// 打刻カード番号を取得する
		// StampCardRepository
		List<StampCard> getLstStampCardBySidAndContractCd(String sid);
	}
}
