package nts.uk.ctx.sys.log.infra.entity.reference;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.log.dom.reference.LogOutputItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/*
 * author: hiep.th
 */
@Entity
@Table(name = "SRCMT_LOG_OUTPUT_ITEM")
@NoArgsConstructor
@AllArgsConstructor
public class SrcmtLogOutputItem extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SrcmtLogOutputItemPK srcdtLogOutputItemPK;
	
	/** 項目名 */
	@Basic(optional=false)
	@Column(name = "ITEM_NAME")
    public String itemName;
	
	@Override
	protected Object getKey() {
		return srcdtLogOutputItemPK;
	}

	public LogOutputItem toDomain() {
		return LogOutputItem.createFromJavatype(this.srcdtLogOutputItemPK.itemNo,
				this.itemName, this.srcdtLogOutputItemPK.recordType);
	}

	public static SrcmtLogOutputItem toEntity(LogOutputItem domain) {
		return new SrcmtLogOutputItem(new SrcmtLogOutputItemPK(domain.getItemNo(), domain.getRecordType().code),
				 domain.getItemName().v());
	}
}
