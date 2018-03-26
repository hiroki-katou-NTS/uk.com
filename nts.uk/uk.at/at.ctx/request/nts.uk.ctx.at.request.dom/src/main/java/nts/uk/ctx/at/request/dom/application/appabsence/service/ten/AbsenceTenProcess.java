package nts.uk.ctx.at.request.dom.application.appabsence.service.ten;

public interface AbsenceTenProcess {
	/**
	 * 10-1.年休の設定を取得する
	 * @param companyID
	 * @return
	 */
	public AnnualHolidaySetOutput getSettingForAnnualHoliday(String companyID);
	
	
}
