/**
 * 9:15:47 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.timeitem;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.TimeItemTypeAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.TimeItemId;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.TimeItemName;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author hungnm
 *
 */
@Getter
public class BonusPayTimeItem extends AggregateRoot {

	private String companyId;

	private String timeItemId;

	private UseAtr useAtr;

	private TimeItemName timeItemName;

	private int id;

	private TimeItemTypeAtr timeItemTypeAtr;

	private BonusPayTimeItem() {
		super();
		this.timeItemId = IdentifierUtil.randomUniqueId();
	}

	private BonusPayTimeItem(String companyId, String timeItemId, UseAtr useAtr, TimeItemName timeItemName,
			int id, TimeItemTypeAtr timeItemTypeAtr) {
		super();
		this.companyId = companyId;
		this.timeItemId = timeItemId;
		this.useAtr = useAtr;
		this.timeItemName = timeItemName;
		this.id = id;
		this.timeItemTypeAtr = timeItemTypeAtr;
	}

	public static BonusPayTimeItem createFromJavaType(String companyId, String timeItemId, int useAtr,
			String timeItemName, int id, int timeItemTypeAtr) {
		return new BonusPayTimeItem(companyId, timeItemId,
				EnumAdaptor.valueOf(useAtr, UseAtr.class), new TimeItemName(timeItemName), id,
				EnumAdaptor.valueOf(timeItemTypeAtr, TimeItemTypeAtr.class));
	}

	public static BonusPayTimeItem initNewData(String companyId, int useAtr, String timeItemName, int id,
			int timeItemTypeAtr) {
		BonusPayTimeItem newData = new BonusPayTimeItem();
		newData.companyId = companyId;
		newData.useAtr = EnumAdaptor.valueOf(useAtr, UseAtr.class);
		newData.timeItemName = new TimeItemName(timeItemName);
		newData.id = id;
		newData.timeItemTypeAtr = EnumAdaptor.valueOf(timeItemTypeAtr, TimeItemTypeAtr.class);
		return newData;
	}

}