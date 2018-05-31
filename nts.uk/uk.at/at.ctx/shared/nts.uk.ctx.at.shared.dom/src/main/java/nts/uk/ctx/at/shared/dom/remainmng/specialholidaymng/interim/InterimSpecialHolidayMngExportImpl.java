package nts.uk.ctx.at.shared.dom.remainmng.specialholidaymng.interim;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class InterimSpecialHolidayMngExportImpl implements InterimSpecialHolidayMngExport {

	@Inject
	private InterimSpecialHolidayMngRepository interimSpeHolidayRepo;
	@Override
	public List<InterimSpecialHolidayMng> getDataByMode(String cid, String sid, DatePeriod dateData, boolean mode) {
		//INPUT．モードをチェックする
		if(!mode) {
			//ドメインモデル「特別休暇暫定データ」を取得する
			return interimSpeHolidayRepo.findBySidPeriod(sid, dateData);
		}
		//暫定残数管理データを作成する
		//TODO 暫定残数管理データを作成するアルゴリズムが出来たらリンクする

		return null;
	}

}
