package nts.uk.ctx.at.schedule.dom.budget.premium;

import nts.uk.ctx.at.schedule.dom.budget.AimItemType;
import org.eclipse.persistence.internal.xr.ValueObject;


/**
 * 人件費の目指項目
 */
public class AimItemsForLaborCosts extends ValueObject {
    //1: 目指時間: int
    private int aimTime;

    //2: 目指金額: int
    private int targetAmount;

    //3: 目指項目種類 : 目指項目種類
    private AimItemType aimItemType;
}
