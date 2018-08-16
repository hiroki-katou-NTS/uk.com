package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByApplicationData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByRecordData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByScheData;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class InterimRemainDataMngRegisterDateChangeImpl implements InterimRemainDataMngRegisterDateChange{
	@Inject
	private RemainCreateInforByScheData remainScheData;
	@Inject
	private RemainCreateInforByRecordData remainRecordData;
	@Inject
	private RemainCreateInforByApplicationData remainAppData;
	@Inject
	private InterimRemainDataMngRegister mngRegister;
	@Override
	public void registerDateChange(String cid, String sid, List<GeneralDate> lstDate) {
		for (GeneralDate loopDate : lstDate) {
			DatePeriod datePeriod = new DatePeriod(loopDate, loopDate);
			//「残数作成元の勤務予定を取得する」
			List<ScheRemainCreateInfor> lstScheData = remainScheData.createRemainInfor(cid, sid, datePeriod);
			//「残数作成元情報(実績)」を取得する
			List<RecordRemainCreateInfor> lstRecordData = remainRecordData.lstRecordRemainData(cid, sid, datePeriod);
			//「残数作成元の申請を取得する」
			List<AppRemainCreateInfor> lstAppData = remainAppData.lstRemainDataFromApp(cid, sid, datePeriod);
			//指定期間の暫定残数管理データを作成する
			InterimRemainCreateDataInputPara inputData = new InterimRemainCreateDataInputPara(cid, 
					sid, 
					datePeriod, 
					lstRecordData, 
					lstScheData,
					lstAppData,
					false);
			mngRegister.registryInterimDataMng(inputData);
		}
	}

}
