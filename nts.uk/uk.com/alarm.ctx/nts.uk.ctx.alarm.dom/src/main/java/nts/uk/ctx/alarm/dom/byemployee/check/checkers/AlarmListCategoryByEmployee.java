package nts.uk.ctx.alarm.dom.byemployee.check.checkers;

import lombok.RequiredArgsConstructor;

/**
 * アラームリストのカテゴリ
 */
@RequiredArgsConstructor
public enum AlarmListCategoryByEmployee {

    SCHEDULE_MONTHLY(1),

    DAILY(2),
    
    MASTER(3),
    	
    APPLICATION_APPROVAL(4),

    ;

    public final int value;

    public String categoryName() {
        return "";
    }
}
