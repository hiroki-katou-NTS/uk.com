package nts.uk.query.app.exo.condset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.ClosureAdapter;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.PresentClosingPeriodImport;
import nts.uk.ctx.exio.dom.exo.condset.DateAdjustment;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSetting;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSettingRepository;
import nts.uk.query.app.exo.condset.dto.ServerExternalOutputDto;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.外部入出力.外部出力.アルゴリズム.サーバ外部出力自動実行.サーバ外部出力実行時引数処理.サーバ外部出力実行時引数処理
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OutputPeriodSettingQuery {

	@Inject
	private OutputPeriodSettingRepository outputPeriodSettingRepository;

	@Inject
	private ClosureAdapter closureAdapter;

	public ServerExternalOutputDto processServerExternalOutput(String cid, String conditionCd) {
		// ドメインモデル「出力条件設定」を取得する
		Optional<OutputPeriodSetting> optPeriodSet = this.outputPeriodSettingRepository.findById(cid, conditionCd);
		// 締めIDから締め情報を取得する。アルゴリズム「処理年月と締め期間を取得する」を実行する
		if (optPeriodSet.isPresent()) {
			OutputPeriodSetting periodSetting = optPeriodSet.get();
			Optional<PresentClosingPeriodImport> optClosure = this.closureAdapter.findByClosureId(cid,
					periodSetting.getClosureDayAtr().orElse(null));
			// 開始日を求める。アルゴリズム「処理日取得処理」を実行する
			GeneralDate startDate = this.processGetDate(optClosure.get(),
					periodSetting.getStartDateClassification().map(data -> data.value).orElse(null),
					periodSetting.getStartDateAdjustment().map(DateAdjustment::v),
					periodSetting.getStartDateSpecify().orElse(null));
			// 終了日を求める。アルゴリズム「処理日取得処理」を実行する
			GeneralDate endDate = this.processGetDate(optClosure.get(),
					periodSetting.getEndDateClassification().map(data -> data.value).orElse(null),
					periodSetting.getEndDateAdjustment().map(DateAdjustment::v),
					periodSetting.getEndDateSpecify().orElse(null));

			// 開始日と終了日が逆転になっているかをチェックする
			if (startDate != null && startDate.after(endDate)) {
				// 開始日＞終了日
				return new ServerExternalOutputDto(false, "Msg_917");
			} else {
				// 開始日<=終了日
				// 基準日を求める。アルゴリズム「基準日取得処理」を実行する
				GeneralDate baseDate = this.processGetBaseDate(optClosure.get(), startDate, endDate,
						periodSetting.getBaseDateClassification().map(data -> data.value).orElse(null),
						periodSetting.getBaseDateSpecify().orElse(null));
				return new ServerExternalOutputDto(true, new DatePeriod(startDate, endDate), baseDate);
			}
		}
		return null;
	}

	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.外部入出力.外部出力.アルゴリズム.サーバ外部出力自動実行.サーバ外部出力実行時引数処理.処理日取得処理.処理日取得処理
	 * Calculate startDate or endDate with adjustment
	 * 
	 * @param closure     処理年月, 締め開始日, 締め終了日
	 * @param dateCls     処理日区分
	 * @param dateAdjust  処理日調整
	 * @param dateSpecify 処理日指定
	 * @return
	 */
	private GeneralDate processGetDate(PresentClosingPeriodImport closure, Integer dateCls,
			Optional<Integer> dateAdjust, GeneralDate dateSpecify) {
		GeneralDate resultDate = null;
		// 処理日区分により処理を分岐する
		switch (dateCls) {
		// 締め開始日
		case 1:
			// 処理日 = 締め開始日とする
			resultDate = closure.getClosureStartDate();
			return this.adjustDate(resultDate, dateAdjust);
		// 締め終了日
		case 2:
			// 処理日 = 締め終了日とする
			resultDate = closure.getClosureEndDate();
			return this.adjustDate(resultDate, dateAdjust);
		// 処理年月
		case 3:
			// 処理日 = 処理年月の１日とする
			resultDate = this.getStartOfMonth(closure.getProcessingYm());
			return this.adjustDate(resultDate, dateAdjust);
		// システム日付
		case 4:
			// 処理日 = システム日付とする
			resultDate = GeneralDate.today();
			return this.adjustDate(resultDate, dateAdjust);
		case 5:
			// 処理日 = 処理日調整とする
			return dateSpecify;
		default:
			return null;
		}
	}

	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.外部入出力.外部出力.アルゴリズム.サーバ外部出力自動実行.サーバ外部出力実行時引数処理.基準日取得処理.基準日取得処理
	 * 
	 * @param closure         処理年月, 締め開始日, 締め終了日
	 * @param startDate       開始日
	 * @param endDate         終了日
	 * @param baseDateCls     基準日区分
	 * @param baseDateSpecify 基準日指定
	 * @return
	 */
	private GeneralDate processGetBaseDate(PresentClosingPeriodImport closure, GeneralDate startDate,
			GeneralDate endDate, Integer baseDateCls, GeneralDate baseDateSpecify) {
		// 基準日区分により処理を分岐する
		switch (baseDateCls) {
		// 締め開始日
		case 1:
			// 基準日 = 締め開始日とする
			return closure.getClosureStartDate();
		// 締め終了日
		case 2:
			// 処理日 = 締め終了日とする
			return closure.getClosureEndDate();
		// システム日付
		case 3:
			// 基準日 = システム日付とする
			return GeneralDate.today();
		// 開始日
		case 4:
			// 基準日 = 開始日とする
			return startDate;
		// 終了日
		case 5:
			// 基準日 = 終了日とする
			return endDate;
		case 6:
			// 処理日 = 処理日調整とする
			return baseDateSpecify;
		default:
			return null;
		}
	}

	private GeneralDate getStartOfMonth(YearMonth yearMonth) {
		return GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1);
	}

	private GeneralDate adjustDate(GeneralDate date, Optional<Integer> daysToAdjust) {
		return daysToAdjust.map(days -> date.addDays(days)).orElse(date);
	}
}
