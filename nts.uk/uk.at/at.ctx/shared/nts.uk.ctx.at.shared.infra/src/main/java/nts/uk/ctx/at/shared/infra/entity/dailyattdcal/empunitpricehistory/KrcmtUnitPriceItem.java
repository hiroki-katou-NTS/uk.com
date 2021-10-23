package nts.uk.ctx.at.shared.infra.entity.dailyattdcal.empunitpricehistory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.dailyattdcal.empunitpricehistory.UnitPricePerNumber;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * @author laitv
 * 社員単価履歴項目
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSRMT_UNIT_PRICE_ITEM_SYA")
public class KrcmtUnitPriceItem  extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KrcmtUnitPriceItemPK pk;
	
	/** 会社ID */
	@Column(name = "CID")
	public String cid;
	
	/** 社員ID */
	@Column(name = "SID")
	public String sid;
	
	/** 開始日 */
	@Column(name = "HOURLY_UNIT_PRICE")
	public int hourlyUnitPrice;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public KrcmtUnitPriceItem(String hisId, int unitPriceNo, String cid, String sid, int hourlyUnitPrice) {
		super();
		this.pk = new KrcmtUnitPriceItemPK(hisId, unitPriceNo);
		this.sid = sid;
		this.cid = cid;
		this.hourlyUnitPrice = hourlyUnitPrice;
	}
	
	public static UnitPricePerNumber toUnitPricePerNumber(int unitPriceNo, int unitPrice) {
		return UnitPricePerNumber.createSimpleFromJavaType(unitPriceNo, unitPrice);
	}
}
