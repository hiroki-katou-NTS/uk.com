/**
 * 5:25:15 PM Dec 5, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	public BigDecimal conditionAtr;

	@Basic(optional = false)
	@NotNull
	@Column(name = "USE_ATR")
	public BigDecimal useAtr;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "KRCST_ER_AL_ATD_TARGET", joinColumns = {
			@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = true),
			@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", nullable = true) })
	public List<KrcstErAlAtdTarget> lstAtdItemTarget;

	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinTable(name = "KRCST_ERAL_COMPARE_SINGLE", joinColumns = {
			@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = true),
			@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", nullable = true) })
	public KrcstErAlCompareSingle erAlCompareSingle;

	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinTable(name = "KRCST_ERAL_COMPARE_RANGE", joinColumns = {
			@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = true),
			@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", nullable = true) })
	public KrcstErAlCompareRange erAlCompareRange;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinTable(name = "KRCST_ERAL_SINGLE_FIXED", joinColumns = {
			@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = true),
			@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", nullable = true) })
	public KrcstErAlSingleFixed erAlSingleFixed;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "KRCST_ERAL_SINGLE_ATD", joinColumns = {
			@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", nullable = true),
			@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", nullable = true) })
	public List<KrcstErAlSingleAtd> erAlSingleAtd;

	@Override
	protected Object getKey() {
		return this.krcmtErAlAtdItemConPK;
	}

}
