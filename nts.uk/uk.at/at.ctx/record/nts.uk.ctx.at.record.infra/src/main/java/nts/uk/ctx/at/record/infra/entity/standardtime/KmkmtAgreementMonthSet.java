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
@Table(name="KMKMT_AGREEMENT_MONTH_SET")
public class KmkmtAgreementMonthSet extends UkJpaEntity implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public KmkmtAgreementMonthSetPK kmkmtAgreementMonthSetPK;
	
	@Column(name ="ERROR_ONE_MONTH")
	public int errorOneMonth;
	
	@Column(name ="ALARM_ONE_MONTH")
	public int alarmOneMonth;
	
	@Override
	protected Object getKey() {
		return this.kmkmtAgreementMonthSetPK;
	}

}
