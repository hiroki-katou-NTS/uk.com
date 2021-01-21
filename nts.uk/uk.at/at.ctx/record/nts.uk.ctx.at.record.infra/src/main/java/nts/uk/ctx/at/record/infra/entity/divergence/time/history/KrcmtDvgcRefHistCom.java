/*
 * 
 */
package nts.uk.ctx.at.record.infra.entity.divergence.time.history;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The persistent class for the KRCMT_DVGC_REF_HIST_COM database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_DVGC_REF_HIST_COM")
public class KrcmtDvgcRefHistCom extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The hist id. */
	@Id
	@Column(name = "HIST_ID")
	private String histId;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The end D. */
	@Column(name = "END_D")
	@Convert(converter= GeneralDateToDBConverter.class)
	private GeneralDate endD;

	/** The str D. */
	@Column(name = "STR_D")
	@Convert(converter= GeneralDateToDBConverter.class)
	private GeneralDate strD;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.histId;
	}

}