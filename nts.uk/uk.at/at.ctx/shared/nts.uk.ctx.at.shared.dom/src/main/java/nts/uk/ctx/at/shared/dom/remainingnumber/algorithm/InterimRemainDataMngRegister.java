package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import nts.uk.ctx.at.shared.dom.remainingnumber.work.CompanyHolidayMngSetting;

public interface InterimRemainDataMngRegister {
	/**
	 * 暫定データの登録
	 * @param inputData
	 */
	public void registryInterimDataMng(InterimRemainCreateDataInputPara inputData, CompanyHolidayMngSetting comHolidaySetting);

}
