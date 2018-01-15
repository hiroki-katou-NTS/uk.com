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
import nts.uk.ctx.at.shared.dom.specialholiday.Classfication;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@NoArgsConstructor
@Entity
@Table(name = "KSHST_SPHD_CLASSIFICATION")
public class KshstSphdClassfication extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstSphdClassficationPK kshstSphdClassficationPK;

	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
    	@JoinColumn(name="SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
    })
	public KshstSphdSubCondition subCondition;
	
	public KshstSphdClassfication(KshstSphdClassficationPK kshstSphdClassficationPK) {
		super();
		this.kshstSphdClassficationPK = kshstSphdClassficationPK;
	}
	
	@Override
	protected Object getKey() {
		return kshstSphdClassficationPK;
	}

	public static KshstSphdClassfication toEntity(Classfication domain){
		return new KshstSphdClassfication(new KshstSphdClassficationPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v(), domain.getClassficationCode()));
	}
}