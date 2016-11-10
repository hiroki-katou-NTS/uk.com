package nts.uk.ctx.pr.proto.infra.entity.layoutmaster;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.AggregateTableEntity;
import nts.uk.ctx.pr.proto.infra.entity.QcamtItem;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="QSTMT_STMT_LAYOUT_DETAIL")
public class QstmtStmtLayoutDetail extends AggregateTableEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private QstmtStmtLayoutDetailPk qstmtStmtLayoutDetailPk;

	@Basic(optional = false)
	@Column(name ="END_YM")
	private int endYm;

	@Basic(optional = false)
	@Column(name ="AUTO_LINE_ID")
	private String autoLineId;

	@Basic(optional = false)
	@Column(name ="ITEM_POS_COLUMN")
	private String itemPosColumn;

	@Basic(optional = false)
	@Column(name ="DISP_ATR")
	private int dispAtr;

	@Basic(optional = false)
	@Column(name ="SUM_SCOPE_ATR")
	private int sumScopeAtr;

	@Basic(optional = false)
	@Column(name ="CALC_METHOD")
	private int calcMethod;

	@Basic(optional = false)
	@Column(name ="P_WAGE_CD")
	private String pWageCd;

	@Basic(optional = false)
	@Column(name ="FORMULA_CD")
	private String formulaCd;

	@Basic(optional = false)
	@Column(name ="WAGE_TABLE_CD")
	private String wageTableCd;

	@Basic(optional = false)
	@Column(name ="COMMON_MNY")
	private int commonMny;

	@Basic(optional = false)
	@Column(name ="SETOFF_ITEM_CD")
	private String setoffItemCd;

	@Basic(optional = false)
	@Column(name ="COMMUTE_ATR")
	private int commuteAtr;

	@Basic(optional = false)
	@Column(name ="DISTRIBUTE_SET")
	private int distributeSet;

	@Basic(optional = false)
	@Column(name ="DISTRIBUTE_WAY")
	private int distributeWay;

	@Basic(optional = false)
	@Column(name ="ERR_RANGE_LOW_ATR")
	private int errRangeLowAtr;

	@Basic(optional = false)
	@Column(name ="ERR_RANGE_LOW")
	private int errRangeLow;

	@Basic(optional = false)
	@Column(name ="ERR_RANGE_HIGH_ATR")
	private int errRangeHighAtr;

	@Basic(optional = false)
	@Column(name ="ERR_RANGE_HIGH")
	private int errRangeHigh;

	@Basic(optional = false)
	@Column(name ="AL_RANGE_LOW_ATR")
	private int alRangeLowAtr;

	@Basic(optional = false)
	@Column(name ="AL_RANGE_LOW")
	private int alRangeLow;

	@Basic(optional = false)
	@Column(name ="AL_RANGE_HIGH_ATR")
	private int alRangeHighAtr;

	@Basic(optional = false)
	@Column(name ="AL_RANGE_HIGH")
	private int alRangeHigh;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="layoutDetail")
	private List<QstmtStmtLayoutLines> layoutLines;
	
	@OneToOne(optional=false)
	@JoinColumns({
        @JoinColumn(name="CCD", referencedColumnName="CCD", insertable = false, updatable = false),
        @JoinColumn(name="STMT_CD", referencedColumnName="STMT_CD", insertable = false, updatable = false),
        @JoinColumn(name="STR_YM", referencedColumnName="STR_YM", insertable = false, updatable = false),
        @JoinColumn(name="CTG_ATR", referencedColumnName="CTG_ATR", insertable = false, updatable = false)
    })
	private QstmtStmtLayoutCtg getQstmtStmtLayoutCtg;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="layoutDetail")
	private List<QcamtItem> lstItem;
}
