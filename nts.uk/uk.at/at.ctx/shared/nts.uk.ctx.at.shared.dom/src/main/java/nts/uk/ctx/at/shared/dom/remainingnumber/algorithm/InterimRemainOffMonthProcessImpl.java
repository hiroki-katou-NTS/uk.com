package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByRecordData;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class InterimRemainOffMonthProcessImpl implements InterimRemainOffMonthProcess{
	@Inject
	private RemainCreateInforByRecordData recordDataService;
	@Inject
	private InterimRemainOffPeriodCreateData periodCreateData;
	@Override
	public Map<GeneralDate, DailyInterimRemainMngData> monthInterimRemainData(String cid, String sid, DatePeriod dateData) {
		//(Imported)「残数作成元情報(実績)」を取得する
		List<RecordRemainCreateInfor> lstRecordData = recordDataService.lstRecordRemainData(cid, sid, dateData);
		//アルゴリズム「指定期間の暫定残数管理データを作成する」を実行する
		InterimRemainCreateDataInputPara inputPara = new InterimRemainCreateDataInputPara(cid, sid, dateData, lstRecordData, Collections.emptyList(), Collections.emptyList(), false);
		Map<GeneralDate, DailyInterimRemainMngData> mapDataOutput = periodCreateData.createInterimRemainDataMng(inputPara);
		
		return mapDataOutput;
	}

}
