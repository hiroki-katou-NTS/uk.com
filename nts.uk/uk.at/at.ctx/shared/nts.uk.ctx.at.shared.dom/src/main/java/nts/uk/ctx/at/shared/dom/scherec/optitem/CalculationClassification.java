package nts.uk.ctx.at.shared.dom.scherec.optitem;

import lombok.AllArgsConstructor;

/**
 * 
 * @author anhnm
 * Refactor 5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).任意項目
 * 計算区分
 * 
 */
@AllArgsConstructor
public enum CalculationClassification {

    // 計算する
    CALC(1, "計算する"),
    
    // 計算しない
    NOT_CALC(0, "計算しない");
    
    public final int value;
    
    public final String name;
}
