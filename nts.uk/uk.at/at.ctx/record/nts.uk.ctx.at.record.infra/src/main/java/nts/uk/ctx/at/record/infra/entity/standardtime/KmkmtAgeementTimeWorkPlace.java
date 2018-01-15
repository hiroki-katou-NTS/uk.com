package nts.uk.ctx.at.record.infra.entity.standardtime;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KMKMT_AGREEMENTTIME_WPL")
public class KmkmtAgeementTimeWorkPlace extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KmkmtAgeementTimeWorkPlacePK kmkmtAgeementTimeWorkPlacePK;

	@Column(name = "LABOR_SYSTEM_ATR")
	public BigDecimal laborSystemAtr;

	@Override
	protected Object getKey() {
		return this.kmkmtAgeementTimeWorkPlacePK;
	}
}
