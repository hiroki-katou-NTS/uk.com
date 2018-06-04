/**
 * 5:25:15 PM Dec 5, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_ER_AL_ATD_ITEM_CON")
public class KrcmtErAlAtdItemCon extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtErAlAtdItemConPK krcmtErAlAtdItemConPK;

	@Basic(optional = false)
	@NotNull
	@Column(name = "CONDITION_ATR")
	public int conditionAtr;

	@Basic(optional = false)
	@NotNull
	@Column(name = "USE_ATR")
	public int useAtr;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	@JoinColumns({
			@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = false),
			@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", nullable = false) })
	public List<KrcstErAlAtdTarget> lstAtdItemTarget;

	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtErAlAtdItemCon", orphanRemoval=true)
	public KrcstErAlCompareSingle erAlCompareSingle;

	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtErAlAtdItemCon", orphanRemoval=true)
	public KrcstErAlCompareRange erAlCompareRange;

	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcmtErAlAtdItemCon", orphanRemoval=true)
	public KrcstErAlSingleFixed erAlSingleFixed;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	@JoinColumns({
			@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = false),
			@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", nullable = false) })
	public List<KrcstErAlSingleAtd> erAlSingleAtd;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false) })
	public KrcstErAlConGroup krcstErAlConGroup;

	@Override
	protected Object getKey() {
		return this.krcmtErAlAtdItemConPK;
	}

	public KrcmtErAlAtdItemCon(KrcmtErAlAtdItemConPK krcmtErAlAtdItemConPK, int conditionAtr, int useAtr,
			List<KrcstErAlAtdTarget> lstAtdItemTarget, KrcstErAlCompareSingle erAlCompareSingle,
			KrcstErAlCompareRange erAlCompareRange, KrcstErAlSingleFixed erAlSingleFixed,
			List<KrcstErAlSingleAtd> erAlSingleAtd) {
		super();
		this.krcmtErAlAtdItemConPK = krcmtErAlAtdItemConPK;
		this.conditionAtr = conditionAtr;
		this.useAtr = useAtr;
		this.lstAtdItemTarget = lstAtdItemTarget;
		this.erAlCompareSingle = erAlCompareSingle;
		this.erAlCompareRange = erAlCompareRange;
		this.erAlSingleFixed = erAlSingleFixed;
		this.erAlSingleAtd = erAlSingleAtd;
	}

}
