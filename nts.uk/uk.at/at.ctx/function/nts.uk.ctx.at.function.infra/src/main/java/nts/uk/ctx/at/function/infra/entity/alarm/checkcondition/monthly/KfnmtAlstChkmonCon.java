package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.monthly;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.MonAlarmCheckCon;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
//import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.daily.KrcmtDailyErrorCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALST_CHKMON")
public class KfnmtAlstChkmonCon extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "MON_ALARM_CHECK_CON_ID")
	public String monAlarmCheckConID;

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
	
	@OneToMany(mappedBy = "monthlyAlarmCheck", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<KfnmtMonAlarmCode> listMonAlarmCode;
	
	
	@Override
	protected Object getKey() {
		return monAlarmCheckConID;
	}

	public KfnmtAlstChkmonCon(String monAlarmCheckConID, String companyId, String code, int category, List<KfnmtMonAlarmCode> listMonAlarmCode) {
		super();
		this.monAlarmCheckConID = monAlarmCheckConID;
		this.companyId = companyId;
		this.code = code;
		this.category = category;
		this.listMonAlarmCode = listMonAlarmCode;
	}

	
	public static KfnmtAlstChkmonCon toEntity(String companyId, String code, int category,MonAlarmCheckCon domain ) {
		return new KfnmtAlstChkmonCon(
				domain.getMonAlarmCheckConID(),
				companyId,
				code,
				category,
				KfnmtMonAlarmCode.toEntity(domain.getMonAlarmCheckConID(), domain.getArbExtraCon())
				);
	}
	
	public MonAlarmCheckCon toDomain() {
		return new MonAlarmCheckCon(
				this.monAlarmCheckConID,
				this.listMonAlarmCode.stream().map(c->c.kfnmtMonAlarmCodePK.errorAlarmCheckID).collect(Collectors.toList())
				);
	}



	
	

}
