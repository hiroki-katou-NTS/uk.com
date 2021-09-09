package nts.uk.screen.at.app.ktgwidget.ktg001;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.CheckTarget;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureIdPresentClosingPeriod;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.TopPageDisplayYearMonthEnum;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.アルゴリズム.2.日別実績の承認すべきデータの取得.トップページの設定により対象年月と締めIDを取得する
 * @author tutt
 *
 */
@Stateless
public class CheckTargetFinder {
	/**
	 * トップページの設定により対象年月と締めIDを取得する
	 */
	public CheckTarget getCheckTarget(ClosureIdPresentClosingPeriod closingPeriod, Integer yearMonth) {

		// 処理年月
		// YearMonth processingYm = closingPeriods.stream().filter(c -> c.getClosureId() == closureId)
		//		.map(m -> m.getCurrentClosingPeriod().getProcessingYm()).collect(Collectors.toList()).get(0);

		// トップページの設定により対象年月と締めIDを取得する
		// ユーザー固有情報「トップページ表示年月」を取得する
		// EA3955
		YearMonth processingYm = closingPeriod.getCurrentClosingPeriod().getProcessingYm();
		Integer closureId = closingPeriod.getClosureId();
		
		if (EnumAdaptor.valueOf(yearMonth,
				TopPageDisplayYearMonthEnum.class) == TopPageDisplayYearMonthEnum.THIS_MONTH_DISPLAY || yearMonth == null) {
			return new CheckTarget(closureId, processingYm);

		} else if (EnumAdaptor.valueOf(yearMonth,
				TopPageDisplayYearMonthEnum.class) == TopPageDisplayYearMonthEnum.NEXT_MONTH_DISPLAY) {
			return new CheckTarget(closureId, processingYm.addMonths(1));

		} else {
			return new CheckTarget(closureId, processingYm);
		}

	}
}
