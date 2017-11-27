package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TanLV
 *
 */
@AllArgsConstructor
@Getter
public class PresetItems {
	/* 会社ID */
    private String companyId;
    
    /* ID */
    private String Id;
    
    /* 名称 */
    private String name;
    
    /* 属性 */
    private Attributes attributes;
}
