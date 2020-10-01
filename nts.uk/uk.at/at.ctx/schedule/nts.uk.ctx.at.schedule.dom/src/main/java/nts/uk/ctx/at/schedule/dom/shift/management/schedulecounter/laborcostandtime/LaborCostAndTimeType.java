package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime;

import nts.arc.enums.EnumAdaptor;

public enum LaborCostAndTimeType {
	
    TOTAL(0), // 合計

    WORKING_HOURS(1), // 就業時間
    
    OVERTIME(2); // 時間外時間
    
    public int value;

    private LaborCostAndTimeType(int value) {
        this.value = value;
    }
    
    public static LaborCostAndTimeType of(int value) {
    	return EnumAdaptor.valueOf(value, LaborCostAndTimeType.class);
    }

}
