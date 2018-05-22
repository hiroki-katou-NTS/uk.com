/**
 * 5:25:52 PM Dec 5, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcmtErAlCondition;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycondition.KrcmtTimeChkMonthly;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCST_ER_AL_CON_GROUP")
public class KrcstErAlConGroup extends UkJpaEntity implements Serializable {

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
    public BigDecimal conditionOperator;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumns({ @JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = true) })
	public List<KrcmtErAlAtdItemCon> lstAtdItemCon;
	
    @OneToOne(mappedBy="krcstErAlConGroup1")
	public KrcmtErAlCondition krcmtErAlCondition;
    
    @OneToOne(mappedBy="krcstErAlConGroup1")
	public KrcmtTimeChkMonthly krcmtTimeChkMonthly;
    
	@Override
	protected Object getKey() {
		return this.conditionGroupId;
	}

	public KrcstErAlConGroup(String conditionGroupId, BigDecimal conditionOperator,
			List<KrcmtErAlAtdItemCon> lstAtdItemCon) {
		super();
		this.conditionGroupId = conditionGroupId;
		this.conditionOperator = conditionOperator;
		this.lstAtdItemCon = lstAtdItemCon;
	}
}
