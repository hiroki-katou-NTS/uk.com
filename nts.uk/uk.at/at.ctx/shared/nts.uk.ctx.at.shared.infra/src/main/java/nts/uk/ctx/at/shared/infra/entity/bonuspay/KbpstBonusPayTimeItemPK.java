package nts.uk.ctx.at.shared.infra.entity.bonuspay;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.query.DBCharPaddingAs;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.TimeItemId;
@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KbpstBonusPayTimeItemPK  implements Serializable{
	private static final long serialVersionUID = 1L;
	@Column(name = "CID")
	public String companyId;
	@DBCharPaddingAs(TimeItemId.class)
	@Column(name = "TIME_ITEM_ID")
	public String timeItemId;

}
