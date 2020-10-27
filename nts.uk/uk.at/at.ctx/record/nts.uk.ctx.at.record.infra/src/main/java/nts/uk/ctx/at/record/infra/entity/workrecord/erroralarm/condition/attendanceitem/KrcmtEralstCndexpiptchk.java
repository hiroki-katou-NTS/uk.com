/**
 * 5:25:52 PM Dec 5, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlConditionsAttendanceItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcmtErAlCondition;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition.KrcmtMonCorrectCndAtd;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_ERALST_CNDEXPIPTCHK")
public class KrcmtEralstCndexpiptchk extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "CONDITION_GROUP_ID")
    public String conditionGroupId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONDITION_OPERATOR")
    public int conditionOperator;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = true) })
	public List<KrcmtEralstCndgrp> lstAtdItemCon;
	
    @OneToOne(mappedBy="krcmtEralstCndexpiptchk1")
	public KrcmtErAlCondition krcmtErAlCondition;
    
    @OneToOne(mappedBy="krcmtEralstCndexpiptchk1")
	public KrcmtMonCorrectCndAtd krcmtMonCorrectCndAtd;
    
	@Override
	protected Object getKey() {
		return this.conditionGroupId;
	}

	public KrcmtEralstCndexpiptchk(String conditionGroupId, int conditionOperator,
			List<KrcmtEralstCndgrp> lstAtdItemCon) {
		super();
		this.conditionGroupId = conditionGroupId;
		this.conditionOperator = conditionOperator;
		this.lstAtdItemCon = lstAtdItemCon;
	}
	
	public static KrcmtEralstCndexpiptchk toEntity() {
		return null;
	}
	
	public static KrcmtEralstCndexpiptchk toEntity(ErAlConditionsAttendanceItem domain, boolean isGroupOne) {
        return new KrcmtEralstCndexpiptchk(domain.getAtdItemConGroupId(), domain.getConditionOperator().value,
                domain.getLstErAlAtdItemCon().stream().map(item -> KrcmtEralstCndgrp.toEntity(domain.getAtdItemConGroupId(), item, isGroupOne)).collect(Collectors.toList()));
    }
    
    public ErAlConditionsAttendanceItem toDomain(String companyId,String errorAlarmCode) {
        return new ErAlConditionsAttendanceItem(
                this.conditionGroupId,
                EnumAdaptor.valueOf(this.conditionOperator, LogicalOperator.class),
                this.lstAtdItemCon.stream().map(c->c.toDomain(c, companyId, errorAlarmCode)).collect(Collectors.toList())
                );
        
    }
}
