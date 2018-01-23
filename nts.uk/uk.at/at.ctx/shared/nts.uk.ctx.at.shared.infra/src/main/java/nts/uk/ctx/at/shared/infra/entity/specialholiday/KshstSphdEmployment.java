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
import nts.uk.ctx.at.shared.dom.specialholiday.Employment;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_SPHD_EMPLOYMENT")
public class KshstSphdEmployment extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstSphdEmploymentPK kshstSphdEmploymentPK;

	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
    	@JoinColumn(name="SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
    })
	public KshstSphdSubCondition subCondition;
	
	public KshstSphdEmployment(KshstSphdEmploymentPK kshstSphdEmploymentPK) {
		super();
		this.kshstSphdEmploymentPK = kshstSphdEmploymentPK;
	}
	
	@Override
	protected Object getKey() {
		return kshstSphdEmploymentPK;
	}
	
	public static KshstSphdEmployment toEntity(Employment domain){
		return new KshstSphdEmployment(new KshstSphdEmploymentPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v(), domain.getEmploymentCode()));
	}
}