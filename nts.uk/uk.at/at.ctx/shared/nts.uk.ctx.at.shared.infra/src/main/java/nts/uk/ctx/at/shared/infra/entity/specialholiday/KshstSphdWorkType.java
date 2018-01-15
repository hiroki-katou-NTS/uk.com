package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_SPHD_WORKTYPE")
public class KshstSphdWorkType extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstSphdWorkTypePK kshstSphdWorkTypePK;

	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
    	@JoinColumn(name="SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
    })
	public KshstSpecialHoliday specialHoliday;
	
	public KshstSphdWorkType(KshstSphdWorkTypePK kshstSphdWorkTypePK) {
		super();
		this.kshstSphdWorkTypePK = kshstSphdWorkTypePK;
	}
	
	@Override
	protected Object getKey() {
		return kshstSphdWorkTypePK;
	}
}