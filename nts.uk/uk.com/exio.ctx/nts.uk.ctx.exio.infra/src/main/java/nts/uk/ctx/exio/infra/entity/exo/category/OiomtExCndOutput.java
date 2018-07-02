package nts.uk.ctx.exio.infra.entity.exo.category;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exo.category.Association;
import nts.uk.ctx.exio.dom.exo.category.CategoryId;
import nts.uk.ctx.exio.dom.exo.category.Conditions;
import nts.uk.ctx.exio.dom.exo.category.ExCndOutput;
import nts.uk.ctx.exio.dom.exo.category.Form1;
import nts.uk.ctx.exio.dom.exo.category.Form2;
import nts.uk.ctx.exio.dom.exo.category.MainTable;
import nts.uk.ctx.exio.dom.exo.category.PhysicalProjectName;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 外部出力リンクテーブル
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_CND_OUTPUT")
public class OiomtExCndOutput extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtExCndOutputPk exCndOutputPk;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "MAIN_TABLE")
	public String mainTable;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "FORM1")
	public String form1;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "FORM2")
	public String form2;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "CONDITIONS")
	public String conditions;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ITEM_NAME_1")
	public String outCondItemName1;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ITEM_NAME_2")
	public String outCondItemName2;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ITEM_NAME_3")
	public String outCondItemName3;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ITEM_NAME_4")
	public String outCondItemName4;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ITEM_NAME_5")
	public String outCondItemName5;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ITEM_NAME_6")
	public String outCondItemName6;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ITEM_NAME_7")
	public String outCondItemName7;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ITEM_NAME_8")
	public String outCondItemName8;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ITEM_NAME_9")
	public String outCondItemName9;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ITEM_NAME_10")
	public String outCondItemName10;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ASSOCIATION_1")
	public int outCondAssociation1;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ASSOCIATION_2")
	public int outCondAssociation2;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ASSOCIATION_3")
	public int outCondAssociation3;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ASSOCIATION_4")
	public int outCondAssociation4;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ASSOCIATION_5")
	public int outCondAssociation5;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ASSOCIATION_6")
	public int outCondAssociation6;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ASSOCIATION_7")
	public int outCondAssociation7;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ASSOCIATION_8")
	public int outCondAssociation8;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ASSOCIATION_9")
	public int outCondAssociation9;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "OUT_COND_ASSOCIATION_10")
	public int outCondAssociation10;

	@Override
	protected Object getKey() {
		return exCndOutputPk;
	}

	public ExCndOutput toDomain() {
		return new ExCndOutput(new CategoryId(this.exCndOutputPk.categoryId),
				new MainTable(this.mainTable),
				new Form1( this.form1),
				new Form2( this.form2),
				new Conditions(this.conditions),
				new PhysicalProjectName(this.outCondItemName1),	
				new PhysicalProjectName(this.outCondItemName2),
				new PhysicalProjectName(this.outCondItemName3),
				new PhysicalProjectName(this.outCondItemName4),
				new PhysicalProjectName(this.outCondItemName5),
				new PhysicalProjectName(this.outCondItemName6),
				new PhysicalProjectName(this.outCondItemName7),
				new PhysicalProjectName(this.outCondItemName8),
				new PhysicalProjectName(this.outCondItemName9),
				new PhysicalProjectName(this.outCondItemName10),				
				EnumAdaptor.valueOf(this.outCondAssociation1,Association.class) ,
				EnumAdaptor.valueOf(this.outCondAssociation2,Association.class) ,
				EnumAdaptor.valueOf(this.outCondAssociation3,Association.class) ,
				EnumAdaptor.valueOf(this.outCondAssociation4,Association.class) ,
				EnumAdaptor.valueOf(this.outCondAssociation5,Association.class) ,
				EnumAdaptor.valueOf(this.outCondAssociation6,Association.class) ,
				EnumAdaptor.valueOf(this.outCondAssociation7,Association.class) ,
				EnumAdaptor.valueOf(this.outCondAssociation8,Association.class) ,
				EnumAdaptor.valueOf(this.outCondAssociation9,Association.class) ,
				EnumAdaptor.valueOf(this.outCondAssociation10,Association.class));
				
				
				
				
	}

	public static OiomtExCndOutput toEntity(ExCndOutput domain) {
		return new OiomtExCndOutput(new OiomtExCndOutputPk(domain.getCategoryId().v()), domain.getMainTable().v(),
				domain.getForm1().v(), domain.getForm2().v(), domain.getConditions().v(), domain.getOutCondItemName1().v(),
				domain.getOutCondItemName2().v(), domain.getOutCondItemName3().v(), domain.getOutCondItemName4().v(),
				domain.getOutCondItemName5().v(), domain.getOutCondItemName6().v(), domain.getOutCondItemName7().v(),
				domain.getOutCondItemName8().v(), domain.getOutCondItemName9().v(), domain.getOutCondItemName10().v(),
				domain.getOutCondAssociation1().value, domain.getOutCondAssociation2().value, domain.getOutCondAssociation3().value,
				domain.getOutCondAssociation4().value, domain.getOutCondAssociation5().value, domain.getOutCondAssociation6().value,
				domain.getOutCondAssociation7().value, domain.getOutCondAssociation8().value, domain.getOutCondAssociation9().value,
				domain.getOutCondAssociation10().value);
	}

}
