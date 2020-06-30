package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.test.repository;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;

/**
 * 年休設定
 * @author masaaki_jinno
 *
 */
public class TestAnnualPaidLeaveSetting {

	static public AnnualPaidLeaveSetting create(){
		
		AnnualPaidLeaveSetting a = new AnnualPaidLeaveSetting(
				"1", // 会社ID
				null, // 取得設定 cquisitionSetting.
				ManageDistinct.YES, // 年休管理区分
				null, // 年休管理設定  ManageAnnualSetting
				null ); // 時間年休管理設定 TimeAnnualSetting
		
		return a;
	}
	
}


