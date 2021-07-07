package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author ThanhNX
 *
 *         打刻記録
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_STAMP_RECORD")
public class KrcdtStampRecord extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtStampRecordPk pk;
	
 	/**
	 * CID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String Cid;
	
	/**
	 * 表示する打刻区分
	 */
	@Basic(optional = false)
	@Column(name = "DISPLAY_STAMP_ART")
	public String stampTypeDisplay;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KrcdtStampRecord toUpdateEntity(StampRecord domain) {
		this.stampTypeDisplay = domain.getStampTypeDisplay().v();
		return this;
	}

}
