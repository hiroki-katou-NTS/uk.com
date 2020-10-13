package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PriceUnit;

/** インセンティブ単価を取得する
 *  ※単価の取得方法が未確定の為、仮実装。
 */
public class IncentiveUnitPriceService {
	
	public static interface RequireM1 extends RequireM5 {
		Optional<IncentiveUnitPriceUsageSet> getUsageSet(String companyId);
	}
	
	public static interface RequireM2 {
		Optional<IncentiveUnitPriceSetByCom> getCompanySet(String companyId);
	}
	
	public static interface RequireM3 {
		Optional<IncentiveUnitPriceSetByWkp> getWorkPlaceSet(String companyId, String workPlaceId);
		List<String> getWorkplaceIdAndUpper(CacheCarrier cacheCarrier, String companyId, String workPlaceId, GeneralDate baseDate);
	}
	
	public static interface RequireM4 {
		Optional<IncentiveUnitPriceSetByWlc> getWorkLocationSet(String companyId, WorkLocationCD workLocationCd);
	}
	
	public static interface RequireM5 extends RequireM2, RequireM3, RequireM4 {
		
	}

	/**
	 * インセンティブ単価を取得する
	 * @param require
	 * @param cacheCarrier
	 * @param baseDate
	 * @param companyId
	 * @param ouenWorkTimeSheets
	 * @return
	 */
	public static Map<OuenFrameNo, PriceUnit> getIncentiveUnitPrice(
			RequireM1 require,
			CacheCarrier cacheCarrier,
			GeneralDate baseDate,
			String companyId,
			List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheets) {
		
		Map<OuenFrameNo, PriceUnit> unitPrices = new HashMap<>();
		
		for(OuenWorkTimeSheetOfDailyAttendance timeSheet : ouenWorkTimeSheets) {
			//作業別インセンティブ単価の設定を取得する
			Optional<IncentiveUnitPriceSetCommon> setCommon = getIncentiveUnitPriceSet(
					require,
					cacheCarrier,
					baseDate,
					timeSheet.getWorkContent().getCompanyId(),
					timeSheet.getWorkContent().getWorkplace().getWorkplaceId(),
					timeSheet.getWorkContent().getWorkplace().getWorkLocationCD(),
					require.getUsageSet(companyId));
			
			if(setCommon.isPresent() && timeSheet.getWorkContent().getWork().isPresent()) {
				//単価を取得する
				unitPrices.put(
						timeSheet.getWorkNo(),
						setCommon.get().getIncentiveUnitPrice(timeSheet.getWorkContent().getWork().get(), baseDate));
			}
		}
		return unitPrices;
	}
	
	/**
	 * インセンティブ単価設定を取得する
	 * @param require
	 * @param cacheCarrier
	 * @param baseDate
	 * @param companyId
	 * @param workPlaceId
	 * @param workLocationCd
	 * @param usageSet
	 * @return
	 */
	public static Optional<IncentiveUnitPriceSetCommon> getIncentiveUnitPriceSet(
			RequireM5 require,
			CacheCarrier cacheCarrier,
			GeneralDate baseDate,
			String companyId,
			String workPlaceId,
			WorkLocationCD workLocationCd,
			Optional<IncentiveUnitPriceUsageSet> usageSet) {
		if(!usageSet.isPresent())
			return Optional.empty();
		
		if(usageSet.get().getUnit().isWorkPlace()) {
			//職場
			getFromWorkPlace(require, cacheCarrier, companyId, workPlaceId, baseDate);
		}
		if(usageSet.get().getUnit().isWorkLocation()) {
			//場所
			getFromWorkLocation(require, companyId, workLocationCd);
		}
		//会社
		return getFromCompany(require, companyId);
	}
	
	/**
	 * 会社の設定から取得する
	 * @param require
	 * @param companyId
	 * @return
	 */
	private static Optional<IncentiveUnitPriceSetCommon> getFromCompany(RequireM2 require, String companyId) {
		return require.getCompanySet(companyId).map(t -> t);
	}
	
	/**
	 * 職場の設定から取得する
	 * @param require
	 * @param cacheCarrier
	 * @param companyId
	 * @param workPlaceId
	 * @param baseDate
	 * @return
	 */
	private static Optional<IncentiveUnitPriceSetCommon> getFromWorkPlace(RequireM3 require, CacheCarrier cacheCarrier,
			String companyId, String workPlaceId, GeneralDate baseDate) {
		// 所属職場を含む上位階層の職場IDを取得
		List<String> workPlaceIdList = require.getWorkplaceIdAndUpper(cacheCarrier, companyId, workPlaceId, baseDate);
		
		for(String workPlace : workPlaceIdList) {
			return require.getWorkPlaceSet(companyId, workPlace).map(t -> t);
		}
		return Optional.empty();
	}
	
	/**
	 * 場所の設定から取得する
	 * @param require
	 * @param companyId
	 * @param workLocationCd
	 * @return
	 */
	private static Optional<IncentiveUnitPriceSetCommon> getFromWorkLocation(RequireM4 require, String companyId, WorkLocationCD workLocationCd) {
		return require.getWorkLocationSet(companyId, workLocationCd).map(t -> t);
	}
}
