package nts.uk.ctx.pr.report.dom.payment.comparing.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "QLSPT_COMPARE_PRINT_SET")
public class QlsptComparePrintSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public QlsptComparePrintSetPK qlsptComparePrintSetPK;

	@Basic(optional = false)
	@Column(name = "PLUSH_BACK_COLOR")
	public String plushBackColor;

	@Basic(optional = false)
	@Column(name = "MINUS_BACK_COLOR")
	public String minusBackColor;

	@Basic(optional = false)
	@Column(name = "SHOW_ITEM_IF_CF_WITH_NULL")
	public int showItemIfCfWithNull;

	@Basic(optional = false)
	@Column(name = "SHOW_ITEM_IF_SAME_VALUE")
	public int showItemIfSameValue;

	@Basic(optional = false)
	@Column(name = "SHOW_PAYMENT")
	public int showPayment;

	@Basic(optional = false)
	@Column(name = "TOTAL_SET")
	public int totalSet;

	@Basic(optional = false)
	@Column(name = "SUM_EACH_DEPRT_SET")
	public int sumEachDeprtSet;

	@Basic(optional = false)
	@Column(name = "SUM_DEP_HRCHY_INDEX_SET")
	public int sumDepHrchyIndexSet;

	@Basic(optional = false)
	@Column(name = "HRCHY_INDEX1")
	public int hrchyIndex1;

	@Basic(optional = false)
	@Column(name = "HRCHY_INDEX2")
	public int hrchyIndex2;

	@Basic(optional = false)
	@Column(name = "HRCHY_INDEX3")
	public int hrchyIndex3;

	@Basic(optional = false)
	@Column(name = "HRCHY_INDEX4")
	public int hrchyIndex4;

	@Basic(optional = false)
	@Column(name = "HRCHY_INDEX5")
	public int hrchyIndex5;

	@Override
	protected Object getKey() {
		return qlsptComparePrintSetPK;
	}
}
