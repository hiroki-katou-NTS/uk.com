package nts.uk.ctx.at.record.infra.entity.dailyperformanceformat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_BUSINESS_TYPE")
public class KrcmtBusinessType extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtBusinessTypePK krcmtBusinessTypePK;

	@Column(name = "BUSINESS_TYPE_NAME")
	public String businessTypeName;

	@Override
	protected Object getKey() {
		return this.krcmtBusinessTypePK;
	}

}
