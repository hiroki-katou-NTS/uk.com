package nts.uk.ctx.at.aggregation.dom.form9;

public interface Form9DetailOutputSettingRepository {
	
	/**
	 * get
	 * @param companyId 会社ID
	 * @return
	 */
	Form9DetailOutputSetting get(String companyId);
	
	/**
	 * update
	 * @param companyId 会社ID
	 * @param detailOutputSetting 様式９の詳細出力設定
	 */
	void update(String companyId, Form9DetailOutputSetting detailOutputSetting);

}
