package nts.uk.ctx.at.request.ac.standardtime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.pub.standardtime.AgreementMonthSettingPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.standardtime.AgreementMonthSettingAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.standardtime.AgreementMonthSettingOutput;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSettingOutputExport;
/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).月の勤怠計算.36協定実績.３６協定管理.特例設定.Export."[NO.708]社員と年月を指定して３６協定年月設定を取得する"
 * @author hoangnd
 * rql708
 */
@Stateless
public class AgreementMonthSettingAdapterImpl implements AgreementMonthSettingAdapter {

	@Inject
	private AgreementMonthSettingPub pub;
	
	@Override
	public AgreementMonthSettingOutput getData(String employeeId, YearMonth yearMonth) {
		AgreementMonthSettingOutput output = new AgreementMonthSettingOutput();
		AgreementMonthSettingOutputExport export = pub.getExport(employeeId, yearMonth);
		
		output.setIsExist(export.getIsExist());
		output.setOpAgreementMonthSetting(export.getOpAgreementMonthSetting());
		
		return output;
	}

}
