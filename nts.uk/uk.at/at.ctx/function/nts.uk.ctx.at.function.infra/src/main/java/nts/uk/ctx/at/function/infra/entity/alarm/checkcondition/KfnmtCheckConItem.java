package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_CHECK_CON_ITEM")
public class KfnmtCheckConItem extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtCheckConItemPK pk;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="ALARM_PATTERN_CD", referencedColumnName="ALARM_PATTERN_CD", insertable = false, updatable = false),
		@JoinColumn(name="ALARM_CATEGORY", referencedColumnName="ALARM_CATEGORY", insertable = false, updatable = false) })
	public KfnmtCheckCondition checkCondition;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KfnmtCheckConItem(KfnmtCheckConItemPK pk) {
		super();
		this.pk = pk;
	}
	
	
}
