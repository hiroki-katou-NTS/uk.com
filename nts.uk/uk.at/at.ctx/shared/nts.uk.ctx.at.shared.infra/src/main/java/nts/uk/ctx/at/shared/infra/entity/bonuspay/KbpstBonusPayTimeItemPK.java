package nts.uk.ctx.at.shared.infra.entity.bonuspay;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KbpstBonusPayTimeItemPK  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "TIME_ITEM_NO")
	public BigDecimal timeItemNo;
	
	@Column(name = "TYPE_ATR")
	public BigDecimal timeItemTypeAtr;

}
