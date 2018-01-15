package nts.uk.ctx.at.record.infra.entity.stamp.stampcard;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KWKDT_STAMP_CARD")
@AllArgsConstructor
@NoArgsConstructor
public class KwkdtStampCard extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KwkdtStampCardPK kwkdtStampCardPK;
	
	/* 個人ID */
	@Basic(optional = false)
	@Column(name = "SID")
	public String employeeID;
	
	@Override
	protected Object getKey() {
		return kwkdtStampCardPK;
	}

}
