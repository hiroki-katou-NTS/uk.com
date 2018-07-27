package nts.uk.ctx.at.record.dom.actualworkinghours.repository;

import nts.arc.time.GeneralDate;

/**
 * 日別計算のストアド実行用
 * @author keisuke_hoshina
 *
 */
public interface AdTimeAnyItemStoredForDailyCalc {
	//ストアド実行
	public void storeAd(String employeeId, GeneralDate ymd);
}
