package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalculationFormulaOfItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemsOutputToBookTable;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 帳表に出力する項目
 * 
 * @author LienPTK
 */
@Entity
@Table(name = "KFNMT_RPT_WK_YEAR_ITEM")
@Getter
@Setter
@NoArgsConstructor
public class KfnmtRptWkYearItem extends ContractUkJpaEntity implements Serializable
															 , ItemsOutputToBookTable.MementoSetter
															 , ItemsOutputToBookTable.MementoGetter {
	private static final long serialVersionUID = 1L;

	/** ID */
	@EmbeddedId
	public KfnmtRptWkYearItemPK kfnmtRptWkYearItemPK;
	
	/** 会社ID */
	@Column(name = "CID")
	private String cid;
	
	/** 排他バージョン */
	@Version
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** 並び順 */
	@Basic(optional = false)
	@Column(name = "SORT_BY")
	public int sortBy;

	/** 使用区分 */
	@Basic(optional = false)
	@Column(name = "USE_CLASS")
	public boolean useClass;

	/** 見出し名称  */
	@Basic(optional = false)
	@Column(name = "HEADING_NAME")
	public String headingName;

	/** 値の出力形式 */
	@Basic(optional = false)
	@Column(name = "VAL_OUT_FORMAT")
	public int valOutFormat;

	@Override
	protected Object getKey() {
		return this.kfnmtRptWkYearItemPK;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(@JoinColumn(name = "LAYOUT_ID", referencedColumnName = "LAYOUT_ID", insertable = false, updatable = false))
	public KfnmtRptWkYearSet kfnmtRptWkYearSet;

	@OneToMany(cascade=CascadeType.ALL, mappedBy = "kfnmtRptWkYearItem", orphanRemoval = true, fetch = FetchType.LAZY)
	public List<KfnmtRptWkYearCalc> lstKfnmtRptWkYearCalcs;

	@Override
	public String getItemOutCd() {
		return this.kfnmtRptWkYearItemPK.itemOutCd;
	}

	@Override
	public List<CalculationFormulaOfItem> getListOperationSetting() {
		return this.lstKfnmtRptWkYearCalcs.stream().map(t -> {
			return new CalculationFormulaOfItem(t.getOperator(), t.getId().attandanceItemId);
		}).collect(Collectors.toList());
	}

	@Override
	public void setItemOutCd(String itemOutCd) {
		if (this.kfnmtRptWkYearItemPK == null) {
			this.kfnmtRptWkYearItemPK = new KfnmtRptWkYearItemPK();
		}
		this.kfnmtRptWkYearItemPK.itemOutCd = itemOutCd;
	}

	@Override
	public void setListOperationSetting(List<CalculationFormulaOfItem> listOperationSetting) {
		this.lstKfnmtRptWkYearCalcs = listOperationSetting.stream().map(t -> {
			KfnmtRptWkYearCalcPK id = new KfnmtRptWkYearCalcPK();
			id.attandanceItemId = t.getAttendanceItemId();
			id.layoutId = this.kfnmtRptWkYearItemPK.layoutId;
			id.itemOutCd = this.kfnmtRptWkYearItemPK.itemOutCd;
			KfnmtRptWkYearCalc calItem = new KfnmtRptWkYearCalc();
			calItem.setId(id);
			calItem.setOperator(t.getOperation());
			calItem.setCid(this.cid);
			return calItem;
		}).collect(Collectors.toList());
	}
}
