package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import nts.uk.ctx.at.shared.dom.remainingnumber.work.InforFormerRemainData;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

public interface TempRemainCreateEachData {
	/**
	 * 残数作成元情報から暫定年休管理データを作成する
	 * @param inforData
	 * @param workTypeClass
	 * @param mngData
	 * @return
	 */
	DailyInterimRemainMngData createInterimAnnualHoliday(InforFormerRemainData inforData, WorkTypeClassification workTypeClass,
			DailyInterimRemainMngData mngData);
	/**
	 * 残数作成元情報から暫定積立年休管理データを作成する
	 * @param inforData
	 * @param workTypeClass
	 * @param mngData
	 * @return
	 */
	DailyInterimRemainMngData createInterimReserveHoliday(InforFormerRemainData inforData, WorkTypeClassification workTypeClass,
			DailyInterimRemainMngData mngData);
	
	/**
	 * 残数作成元情報から暫定振休管理データを作成する
	 * @param inforData
	 * @return
	 */
	DailyInterimRemainMngData createInterimAbsData(InforFormerRemainData inforData, WorkTypeClassification workTypeClass,
			DailyInterimRemainMngData mngData);
	/**
	 * 残数作成元情報から暫定代休管理データを作成する
	 * @param inforData
	 * @param workTypeClass
	 * @param mngData
	 * @return
	 */
	DailyInterimRemainMngData createInterimDayOffData(InforFormerRemainData inforData, WorkTypeClassification workTypeClass,
			DailyInterimRemainMngData mngData);
	/**
	 * 残数作成元情報から暫定振出管理データを作成する
	 * @param inforData
	 * @param workTypeClass
	 * @param mngData
	 * @return
	 */
	DailyInterimRemainMngData createInterimRecData(InforFormerRemainData inforData, WorkTypeClassification workTypeClass,
			DailyInterimRemainMngData mngData);
	/**
	 * 残数作成元情報から暫定休出管理データを作成する
	 * @param inforData
	 * @param workTypeClass
	 * @param mngData
	 * @return
	 */
	DailyInterimRemainMngData createInterimBreak(InforFormerRemainData inforData, WorkTypeClassification workTypeClass,
			DailyInterimRemainMngData mngData);
	/**
	 * 残数作成元情報から暫定特別休暇管理データを作成する
	 * @param inforData
	 * @param workTypeClass
	 * @param mngData
	 * @return
	 */
	DailyInterimRemainMngData createInterimSpecialHoliday(InforFormerRemainData inforData, WorkTypeClassification workTypeClass,
			DailyInterimRemainMngData mngData);
}
