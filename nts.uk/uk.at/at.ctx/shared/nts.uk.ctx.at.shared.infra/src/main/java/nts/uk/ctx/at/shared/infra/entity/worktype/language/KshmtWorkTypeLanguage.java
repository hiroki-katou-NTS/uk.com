package nts.uk.ctx.at.shared.infra.entity.worktype.language;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkType;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name="KSHMT_WORKTYPE_LANGUAGE")
@AllArgsConstructor
@NoArgsConstructor
public class KshmtWorkTypeLanguage extends ContractUkJpaEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshmtWorkTypeLanguagePK  kshmtWorkTypeLanguagePK;
	/*名称*/
	@Column(name = "NAME")
	public String name;
	/*略名*/
	@Column(name = "ABNAME")
	public String abname;
	
	@OneToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name = "WORKTYPE_CD", referencedColumnName="CD", insertable = false, updatable = false)
	})
	public KshmtWorkType workType;
	
	@Override
	protected Object getKey() {
		return kshmtWorkTypeLanguagePK;
	}
	
}
