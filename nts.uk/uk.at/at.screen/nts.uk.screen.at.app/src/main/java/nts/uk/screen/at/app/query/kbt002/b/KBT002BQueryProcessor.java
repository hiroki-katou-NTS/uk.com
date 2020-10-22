package nts.uk.screen.at.app.query.kbt002.b;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.alarm.AlarmPatternSettingDto;
import nts.uk.ctx.at.function.app.find.alarm.AlarmPatternSettingFinder;
import nts.uk.ctx.at.function.app.find.indexreconstruction.IndexReorgCateDto;
import nts.uk.ctx.at.function.app.find.indexreconstruction.IndexReorgCateFinder;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.AnyAggrPeriodDto;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodFinder;
import nts.uk.query.app.exi.condset.StdAcceptCondSetQueryFinder;
import nts.uk.query.app.exi.condset.dto.StdAcceptCondSetDto;
import nts.uk.query.app.exo.condset.StdOutputCondSetQueryFinder;
import nts.uk.query.app.exo.condset.dto.StdOutputCondSetDto;
import nts.uk.screen.at.app.query.kbt002.b.dto.MasterInfoDto;

/**
 * The class KBT002B query processor.
 * 
 * @author nws-minhnb
 */
@Stateless
public class KBT002BQueryProcessor {

	/**
	 * The Optional aggregation period finder.
	 */
	@Inject
	private OptionalAggrPeriodFinder aggrPeriodFinder;

	/**
	 * The Alarm pattern setting finder.
	 */
	@Inject
	private AlarmPatternSettingFinder alarmPatternSettingFinder;

	/**
	 * The Standard output condition setting query finder.
	 */
	@Inject
	private StdOutputCondSetQueryFinder outputCondSetFinder;

	/**
	 * The Standard acceptance condition setting query finder.
	 */
	@Inject
	private StdAcceptCondSetQueryFinder acceptCondSetQueryFinder;

	/**
	 * The Index reorganization category finder.
	 */
	@Inject
	private IndexReorgCateFinder indexReorgCateFinder;

	/**
	 * Gets master info.<br>
	 * UKDesign.UniversalK.就業.KBT_更新処理自動実行.KBT002_更新処理自動実行.B:実行設定.アルゴリズム.利用するマスタ情報を取得する.利用するマスタ情報を取得する
	 * 
	 * @return the <code>MasterInfoDto</code>
	 */
	public MasterInfoDto getMasterInfo() {
		// ドメインモデル「任意集計期間」を取得する
		List<AnyAggrPeriodDto> aggrPeriodList = this.aggrPeriodFinder.findAll();

		// ドメインモデル「アラームリストパターン設定」を取得する
		List<AlarmPatternSettingDto> alarmPatternSettingList = this.alarmPatternSettingFinder.findAllAlarmPattern();

		// ドメインモデル「出力条件設定（定型）」を取得する
		List<StdOutputCondSetDto> stdOutputCondSetList = this.outputCondSetFinder.getStdOutputCondSetsByCompanyId();

		// ドメインモデル「受入条件設定（定型）」を取得する
		List<StdAcceptCondSetDto> stdAcceptCondSetList = this.acceptCondSetQueryFinder.getStdAcceptCondSetsByCompanyId();

		// TODO ドメインモデル「データ保存のパターン設定」を取得する
		List<?> dataStoragePatternSetList = Collections.emptyList();

		// TODO ドメインモデル「データ削除のパターン設定」を取得する
		List<?> dataDelPatternSetList = Collections.emptyList();

		// ドメインモデル「インデックス再構成カテゴリ」を取得する
		List<IndexReorgCateDto> indexReorgCateList = this.indexReorgCateFinder.getAllIndexReorgCates();

		return new MasterInfoDto(aggrPeriodList,
								 alarmPatternSettingList,
								 stdOutputCondSetList,
								 stdAcceptCondSetList,
								 dataStoragePatternSetList,
								 dataDelPatternSetList,
								 indexReorgCateList);
	}

}
