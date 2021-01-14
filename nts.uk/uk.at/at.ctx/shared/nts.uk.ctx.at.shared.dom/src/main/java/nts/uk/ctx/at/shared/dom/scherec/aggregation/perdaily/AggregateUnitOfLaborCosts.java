package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import nts.arc.enums.EnumAdaptor;

/**
 * 人件費・時間の集計単位
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.人件費・時間.人件費・時間の種類
 * @author dan_pv
 *
 */
public enum AggregateUnitOfLaborCosts {

    /**
     * 合計
     */
    TOTAL(0),

    /**
     * 就業時間
     */
    WORKING_HOURS(1),

    /**
     * 時間外時間
     */
    OVERTIME(2);

    public int value;

    private AggregateUnitOfLaborCosts(int value) {
        this.value = value;
    }

    public static AggregateUnitOfLaborCosts of(int value) {
    	return EnumAdaptor.valueOf(value, AggregateUnitOfLaborCosts.class);
    }

}
