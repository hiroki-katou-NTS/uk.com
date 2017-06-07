/**
 * 9:15:47 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.timeitem;

import lombok.AllArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.TimeItemTypeAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.TimeItemId;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.TimeItemName;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
public class BonusPayTimeItem extends AggregateRoot {

	private CompanyId companyId;

	private TimeItemId timeItemId;

	private UseAtr useAtr;

	private TimeItemName timeItemName;

	private int id;

	private TimeItemTypeAtr timeItemTypeAtr;

}