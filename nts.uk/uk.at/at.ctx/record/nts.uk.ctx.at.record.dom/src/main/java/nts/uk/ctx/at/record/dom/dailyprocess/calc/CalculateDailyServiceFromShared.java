package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.CommonCompanySettingForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.CalculateDailyRecordServiceCenterNew;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;

@Stateless
public class CalculateDailyServiceFromShared implements CalculateDailyRecordServiceCenterNew{

	@Inject
	private CommonCompanySettingForCalc commonCompanySettingForCalc;
	
	@Inject
	private CalculateDailyRecordServiceCenter center;
	
	@Override
	public List<IntegrationOfDaily> calculateForSchedule(CalculateOption calcOption,
			List<IntegrationOfDaily> integrationOfDaily) {
		val companyCommonSetting = commonCompanySettingForCalc.getCompanySetting();
		return center.calculateForSchedule(calcOption, integrationOfDaily, Optional.ofNullable(companyCommonSetting));
	}

}
