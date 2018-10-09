package nts.uk.ctx.exio.infra.entity.exo.category;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.category.ExOutLinkTable;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 外部出力リンクテーブル
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_OUT_LINK_TABLE")
public class OiomtExOutLinkTable extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtExOutLinkTablePk exCndOutputPk;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "MAIN_TABLE")
	public String mainTable;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "FORM1")
	public String form1;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "FORM2")
	public String form2;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "CONDITIONS")
	public String conditions;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ITEM_NAME_1")
	public String outCondItemName1;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ITEM_NAME_2")
	public String outCondItemName2;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ITEM_NAME_3")
	public String outCondItemName3;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ITEM_NAME_4")
	public String outCondItemName4;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ITEM_NAME_5")
	public String outCondItemName5;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ITEM_NAME_6")
	public String outCondItemName6;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ITEM_NAME_7")
	public String outCondItemName7;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ITEM_NAME_8")
	public String outCondItemName8;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ITEM_NAME_9")
	public String outCondItemName9;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ITEM_NAME_10")
	public String outCondItemName10;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ASSOCIATION_1")
	public Integer outCondAssociation1;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ASSOCIATION_2")
	public Integer outCondAssociation2;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ASSOCIATION_3")
	public Integer outCondAssociation3;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ASSOCIATION_4")
	public Integer outCondAssociation4;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ASSOCIATION_5")
	public Integer outCondAssociation5;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ASSOCIATION_6")
	public Integer outCondAssociation6;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ASSOCIATION_7")
	public Integer outCondAssociation7;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ASSOCIATION_8")
	public Integer outCondAssociation8;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ASSOCIATION_9")
	public Integer outCondAssociation9;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OUT_COND_ASSOCIATION_10")
	public Integer outCondAssociation10;

	@Override
	protected Object getKey() {
		return exCndOutputPk;
	}

	public ExOutLinkTable toDomain() {
		return new ExOutLinkTable(this.exCndOutputPk.categoryId, this.mainTable, this.form1, this.form2, this.conditions,
				this.outCondItemName1, this.outCondItemName2, this.outCondItemName3, this.outCondItemName4,
				this.outCondItemName5, this.outCondItemName6, this.outCondItemName7, this.outCondItemName8,
				this.outCondItemName9, this.outCondItemName10, this.outCondAssociation1, this.outCondAssociation2,
				this.outCondAssociation3, this.outCondAssociation4, this.outCondAssociation5, this.outCondAssociation6,
				this.outCondAssociation7, this.outCondAssociation8, this.outCondAssociation9,
				this.outCondAssociation10);
	}

	public static OiomtExOutLinkTable toEntity(ExOutLinkTable domain) {
		return new OiomtExOutLinkTable(domain);
	}

	public OiomtExOutLinkTable(ExOutLinkTable domain) {
		super();
		this.exCndOutputPk = new OiomtExOutLinkTablePk(domain.getCategoryId().v());
		this.mainTable = domain.getMainTable().v();
		this.form1 =  domain.getForm1().map(item -> item.v()).orElse(null);
		this.form2 =  domain.getForm2().map(item -> item.v()).orElse(null);
		this.conditions =  domain.getConditions().map(item -> item.v()).orElse(null);
		this.outCondItemName1 =  domain.getOutCondItemName1().map(item -> item.v()).orElse(null);
		this.outCondItemName2 =  domain.getOutCondItemName2().map(item -> item.v()).orElse(null);
		this.outCondItemName3 =  domain.getOutCondItemName3().map(item -> item.v()).orElse(null);
		this.outCondItemName4 =  domain.getOutCondItemName4().map(item -> item.v()).orElse(null);
		this.outCondItemName5 =  domain.getOutCondItemName5().map(item -> item.v()).orElse(null);
		this.outCondItemName6 =  domain.getOutCondItemName6().map(item -> item.v()).orElse(null);
		this.outCondItemName7 =  domain.getOutCondItemName7().map(item -> item.v()).orElse(null);
		this.outCondItemName8 =  domain.getOutCondItemName8().map(item -> item.v()).orElse(null);
		this.outCondItemName9 =  domain.getOutCondItemName9().map(item -> item.v()).orElse(null);
		this.outCondItemName10 =  domain.getOutCondItemName10().map(item -> item.v()).orElse(null);
		this.outCondAssociation1 =  domain.getOutCondAssociation1().map(item -> item.value).orElse(null);
		this.outCondAssociation2 =  domain.getOutCondAssociation2().map(item -> item.value).orElse(null);
		this.outCondAssociation3 =  domain.getOutCondAssociation3().map(item -> item.value).orElse(null);
		this.outCondAssociation4 =  domain.getOutCondAssociation4().map(item -> item.value).orElse(null);
		this.outCondAssociation5 =  domain.getOutCondAssociation5().map(item -> item.value).orElse(null);
		this.outCondAssociation6 =  domain.getOutCondAssociation6().map(item -> item.value).orElse(null);
		this.outCondAssociation7 =  domain.getOutCondAssociation7().map(item -> item.value).orElse(null);
		this.outCondAssociation8 =  domain.getOutCondAssociation8().map(item -> item.value).orElse(null);
		this.outCondAssociation9 =  domain.getOutCondAssociation9().map(item -> item.value).orElse(null);
		this.outCondAssociation10 =  domain.getOutCondAssociation10().map(item -> item.value).orElse(null);
	}

}
