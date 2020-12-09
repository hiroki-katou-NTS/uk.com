package nts.uk.ctx.at.shared.infra.entity.scherec.monthlyattendanceitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinColumns;
//import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.DisplayAndInputMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.InputControlMonthly;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "KSHMT_MON_ITEM_DISP_CTR")
public class KrcstDisplayAndInputMonthly extends ContractUkJpaEntity implements Serializable {
 
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KrcstDisplayAndInputMonthlyPK krcstDisplayAndInputMonthlyPK;

	@Column(name = "USE_ATR")
	public int toUse;
	
	@Column(name = "CHANGED_BY_YOU")
	public int youCanChangeIt;
	
	@Column(name = "CHANGED_BY_OTHERS")
	public int canBeChangedByOthers;
	
	@Override
	protected Object getKey() {
		return krcstDisplayAndInputMonthlyPK;
	}

	public KrcstDisplayAndInputMonthly(KrcstDisplayAndInputMonthlyPK krcstDisplayAndInputMonthlyPK, int toUse, int youCanChangeIt, int canBeChangedByOthers) {
		super();
		this.krcstDisplayAndInputMonthlyPK = krcstDisplayAndInputMonthlyPK;
		this.toUse = toUse;
		this.youCanChangeIt = youCanChangeIt;
		this.canBeChangedByOthers = canBeChangedByOthers;
	}
	
	public static KrcstDisplayAndInputMonthly toEntity(String companyID,String authorityMonthlyID,DisplayAndInputMonthly domain) {
		return new KrcstDisplayAndInputMonthly(
				new KrcstDisplayAndInputMonthlyPK(companyID,authorityMonthlyID,domain.getItemMonthlyId()),
				domain.isToUse()?1:0,
				domain.getInputControlMonthly().isYouCanChangeIt()?1:0,
				domain.getInputControlMonthly().isCanBeChangedByOthers()?1:0
				);
	}
	
	public DisplayAndInputMonthly toDomain() {
		return new DisplayAndInputMonthly(
				this.krcstDisplayAndInputMonthlyPK.itemMonthlyID,
				this.toUse == 1?true:false,
				new InputControlMonthly(
						this.youCanChangeIt ==1?true:false,
						this.canBeChangedByOthers ==1?true:false
						)
				);
	}
}
