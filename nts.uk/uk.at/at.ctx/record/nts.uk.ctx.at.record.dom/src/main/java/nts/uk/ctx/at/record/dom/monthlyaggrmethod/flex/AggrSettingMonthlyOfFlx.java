package nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.ShortageFlexSetting;

/**
 * フレックス時間勤務の月の集計設定
 * @author shuichu_ishida
 */
@Getter
public class AggrSettingMonthlyOfFlx {
	
	/** 集計方法 */
	private FlexAggregateMethod aggregateMethod;
	/** 残業時間を含める　（未）するしない区分の統一後、それに型変更する */
	private boolean includeOverTime;
	/** 不足設定 */
	private ShortageFlexSetting shortageSet;
	/** 法定内集計設定 */
	private LegalAggrSetOfFlx legalAggregateSet;
	/** 36協定集計方法 */
	private SettingOfFlex36AgreementTime arrgMethod36Agreement;

	/**
	 * コンストラクタ
	 */
	public AggrSettingMonthlyOfFlx(){
		
		this.aggregateMethod = FlexAggregateMethod.PRINCIPLE;
		this.includeOverTime = false;
		this.shortageSet = new ShortageFlexSetting();
		this.legalAggregateSet = new LegalAggrSetOfFlx();
		this.arrgMethod36Agreement = new SettingOfFlex36AgreementTime();
	}

	/**
	 * ファクトリー
	 * @param aggregateMethod 集計方法
	 * @param includeOverTime 残業時間を含める
	 * @param shortageSet 不足設定
	 * @param legalAggregateSet 法定内集計設定
	 * @param arrgMethod36Agreement 36協定集計方法
	 * @return フレックス時間勤務の月の集計設定
	 */
	public static AggrSettingMonthlyOfFlx of(
			FlexAggregateMethod aggregateMethod,
			boolean includeOverTime,
			ShortageFlexSetting shortageSet,
			LegalAggrSetOfFlx legalAggregateSet,
			SettingOfFlex36AgreementTime arrgMethod36Agreement){
		
		val domain = new AggrSettingMonthlyOfFlx();
		domain.aggregateMethod = aggregateMethod;
		domain.includeOverTime = includeOverTime;
		domain.shortageSet = shortageSet;
		domain.legalAggregateSet = legalAggregateSet;
		domain.arrgMethod36Agreement = arrgMethod36Agreement;
		return domain;
	}
}
