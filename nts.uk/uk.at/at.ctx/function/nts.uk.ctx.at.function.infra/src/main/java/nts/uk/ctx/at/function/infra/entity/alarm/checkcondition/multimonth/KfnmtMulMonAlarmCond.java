package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.multimonth;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.MulMonAlarmCond;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALST_CHKMLT")
public class KfnmtMulMonAlarmCond extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	//ID
	@Id
	@Basic(optional = false)
	@Column(name = "MUL_MON_ALARM_CON_ID")	
	public String mulMonAlarmConID;

	@Column(name = "CID")
	public String companyId;

	@Column(name = "CD")
	public String code;

	@Column(name = "CATEGORY")
	public int category;

	
	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false),
			@JoinColumn(name = "CD", referencedColumnName = "CD", insertable = false, updatable = false) })
	public KfnmtAlarmCheckConditionCategory condition;
	
	@OneToMany(mappedBy = "mulMonAlarmCheck", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<KfnmtMulMonAlarmCode> listMulMonAlarmCode;
	
	@Override
	protected Object getKey() {
		return this.mulMonAlarmConID;
	}

	public KfnmtMulMonAlarmCond(String mulMonAlarmConID, String companyId, String code, int category, List<KfnmtMulMonAlarmCode> listMulMonAlarmCode) {
		super();
		this.mulMonAlarmConID = mulMonAlarmConID;
		this.companyId = companyId;
		this.code = code;
		this.category = category;
		this.listMulMonAlarmCode = listMulMonAlarmCode;
	}
	
	
	public static KfnmtMulMonAlarmCond toEntity(String companyId, String code, int category,MulMonAlarmCond domain ) {
		return new KfnmtMulMonAlarmCond(
				domain.getMulMonAlarmCondID(),
				companyId,
				code,
				category,
				KfnmtMulMonAlarmCode.toEntity(domain.getMulMonAlarmCondID(), domain.getErrorAlarmCondIds())
				);
	}
	
	public MulMonAlarmCond toDomain() {
		return new MulMonAlarmCond(
				this.mulMonAlarmConID,
				this.listMulMonAlarmCode.stream().map(c->c.kfnmtMulMonAlarmCodePK.errorAlarmCheckID).collect(Collectors.toList())
				);
	}
}
