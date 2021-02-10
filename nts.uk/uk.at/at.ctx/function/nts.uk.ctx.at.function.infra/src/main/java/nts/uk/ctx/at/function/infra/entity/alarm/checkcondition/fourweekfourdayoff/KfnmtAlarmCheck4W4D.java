package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.fourweekfourdayoff;

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
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff.AlarmCheckCondition4W4D;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALST_CHK_4W4D")
public class KfnmtAlarmCheck4W4D extends ContractUkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtAlarmCheck4W4DPK pk;

	@Column(name = "CID")
	public String CID;

	@Column(name = "CATEGORY")
	public int category;

	@Column(name = "AL_CHECK_COND_CATE_CD")
	public String alCheckCondCateCD;

	@Column(name = "W4D4_CHECK_COND")
	public int fourW4DCheckCond;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false),
			@JoinColumn(name = "AL_CHECK_COND_CATE_CD", referencedColumnName = "CD", insertable = false, updatable = false) })
	public KfnmtAlarmCheckConditionCategory condition;

	@Override
	protected Object getKey() {

		return this.pk;
	}

	public KfnmtAlarmCheck4W4D(KfnmtAlarmCheck4W4DPK pk, String cID, int category, String alCheckCondCateCD,
			int fourW4DCheckCond) {
		super();
		this.pk = pk;
		CID = cID;
		this.category = category;
		this.alCheckCondCateCD = alCheckCondCateCD;
		this.fourW4DCheckCond = fourW4DCheckCond;
	}
	
	public AlarmCheckCondition4W4D toDomain() {
		return new AlarmCheckCondition4W4D(this.pk.alCheck4w4dID, this.fourW4DCheckCond);
	}

	public static KfnmtAlarmCheck4W4D toEntity(AlarmCheckCondition4W4D domain, String CID, AlarmCategory category,
			AlarmCheckConditionCode alCheckCondCateCD) {
		return new KfnmtAlarmCheck4W4D(new KfnmtAlarmCheck4W4DPK(domain.getAlCheck4w4dID()), CID, category.value,
				alCheckCondCateCD.v(), domain.getFourW4DCheckCond().value);

	}

}
