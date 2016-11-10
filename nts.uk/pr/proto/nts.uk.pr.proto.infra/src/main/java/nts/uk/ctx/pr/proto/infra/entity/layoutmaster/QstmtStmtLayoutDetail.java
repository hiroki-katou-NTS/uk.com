package nts.uk.ctx.pr.proto.infra.entity.layoutmaster;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="QSTMT_STMT_LAYOUT_DETAIL")
public class QstmtStmtLayoutDetail {
	
	@EmbeddedId
	public QstmtStmtLayoutDetailPk qstmtStmtLayoutDetailPk;
	
	@Column(name ="END_YM")
	public int endYm;
		
	@Column(name ="AUTO_LINE_ID")
	public String autoLineId;
	
	@Column(name ="ITEM_POS_COLUMN")
	public String itemPosColumn;
	
	@Column(name ="DISP_ATR")
	public int dispAtr;
	
	@Column(name ="SUM_SCOPE_ATR")
	public int sumScopeAtr;

	@Column(name ="CALC_METHOD")
	public int calcMethod;
	
	@Column(name ="P_WAGE_CD")
	public String pWageCd;
	
	@Column(name ="FORMULA_CD")
	public String formulaCd;
	
	@Column(name ="WAGE_TABLE_CD")
	public String wageTableCd;
	
	@Column(name ="COMMON_MNY")
	public int commonMny;
	
	@Column(name ="SETOFF_ITEM_CD")
	public String setoffItemCd;
	
	@Column(name ="COMMUTE_ATR")
	public int commuteAtr;
	
	@Column(name ="DISTRIBUTE_SET")
	public int distributeSet;
	
	@Column(name ="DISTRIBUTE_WAY")
	public int distributeWay;
	
	@Column(name ="ERR_RANGE_LOW_ATR")
	public int errRangeLowAtr;
	
	@Column(name ="ERR_RANGE_LOW")
	public int errRangeLow;
	
	@Column(name ="ERR_RANGE_HIGH_ATR")
	public int errRangeHighAtr;
	
	@Column(name ="ERR_RANGE_HIGH")
	public int errRangeHigh;
	
	@Column(name ="AL_RANGE_LOW_ATR")
	public int alRangeLowAtr;
	
	@Column(name ="AL_RANGE_LOW")
	public int alRangeLow;
	
	@Column(name ="AL_RANGE_HIGH_ATR")
	public int alRangeHighAtr;
	
	@Column(name ="AL_RANGE_HIGH")
	public int alRangeHigh;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="layoutDetail")
	public List<QstmtStmtLayoutLines> layoutLines;
	
	@OneToOne(optional=false)
	@JoinColumns({
        @JoinColumn(name="CCD", referencedColumnName="CCD", insertable = false, updatable = false),
        @JoinColumn(name="STMT_CD", referencedColumnName="STMT_CD", insertable = false, updatable = false),
        @JoinColumn(name="STR_YM", referencedColumnName="STR_YM", insertable = false, updatable = false),
        @JoinColumn(name="CTG_ATR", referencedColumnName="CTG_ATR", insertable = false, updatable = false)
    })
	public QstmtStmtLayoutCtg getQstmtStmtLayoutCtg;
}
