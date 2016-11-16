package nts.uk.ctx.pr.proto.infra.entity.paymentdata.personalwage;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.AggregateTableEntity;

//@Entity
@Table(name="PPRMT_PERSON_WAGE")
public class PprmtPersonWage extends AggregateTableEntity {
	@EmbeddedId
    public PprmtPersonWagePK pprmtPersonWagePK;
	
	@Column(name = "END_YM")
	public int endYm;
	
	@Column(name = "VAL")
	public BigDecimal val;
}
