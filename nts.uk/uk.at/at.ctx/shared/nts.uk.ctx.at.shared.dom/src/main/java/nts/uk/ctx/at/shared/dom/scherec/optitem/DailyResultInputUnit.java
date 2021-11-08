package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Value;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 日別実績の入力単位
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).任意項目.日別実績の入力単位
 */
@Value
public class DailyResultInputUnit {
    // 時間項目の入力単位
    private Optional<TimeItemInputUnit> timeItemInputUnit;

    // 回数項目の入力単位
    private Optional<NumberItemInputUnit> numberItemInputUnit;

    // 金額項目の入力単位
    private Optional<AmountItemInputUnit> amountItemInputUnit;
    
    /**
     * 入力単位チェック
     * @param value
     * @param optionalItemAtr
     * @return
     */
    public ValueCheckResult checkInputUnit(BigDecimal value, OptionalItemAtr optionalItemAtr) {
		
    	double resultEnum = 0.0;
    	switch(optionalItemAtr) {
    	case TIME:
    		resultEnum = timeItemInputUnit.get().valueEnum();
    		break;
    	case NUMBER:
    		resultEnum = numberItemInputUnit.get().valueEnum();
    		break;
    	default: //金額
    		resultEnum = amountItemInputUnit.get().valueEnum();
    	}
    	
    	if(value.doubleValue()%resultEnum ==0) {
    		return new ValueCheckResult(true, Optional.empty());
    	}
    	String valueError = value+"";
    	if(optionalItemAtr == OptionalItemAtr.TIME) {
    		valueError = convertTime(value.intValue());
    	}
    	String errorContens = TextResource.localize("Msg_2290",valueError);
    	return new ValueCheckResult(false, Optional.of(errorContens));
	}
    
    private String convertTime(Integer time) {
		if (time == null) {
			return "";
		}
		String m = String.valueOf(time % 60).length() > 1 ? String.valueOf(time % 60) : 0 + String.valueOf(time % 60);
		String timeString = String.valueOf(time / 60) + ":" + m;
		return timeString;
	}
    
    
}
