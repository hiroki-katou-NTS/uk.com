package nts.uk.ctx.exio.app.find.exo.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.category.ExOutLinkTable;

/**
 * 外部出力リンクテーブル
 */
@AllArgsConstructor
@Value
@Getter
public class ExCndOutputDto {
	

	/**
	* 
	*/
	private int categoryId;

	/**
	* 
	*/
	private String mainTable;

	/**
	* 
	*/
	private String form1;

	/**
	* 
	*/
	private String form2;

	/**
	* 
	*/
	private String conditions;

	/**
	* 
	*/
	private String outCondItemName1;

	/**
	* 
	*/
	private String outCondItemName2;

	/**
	* 
	*/
	private String outCondItemName3;

	/**
	* 
	*/
	private String outCondItemName4;

	/**
	* 
	*/
	private String outCondItemName5;

	/**
	* 
	*/
	private String outCondItemName6;

	/**
	* 
	*/
	private String outCondItemName7;

	/**
	* 
	*/
	private String outCondItemName8;

	/**
	* 
	*/
	private String outCondItemName9;

	/**
	* 
	*/
	private String outCondItemName10;

	/**
	* 
	*/
	private Integer outCondAssociation1;

	/**
	* 
	*/
	private Integer outCondAssociation2;

	/**
	* 
	*/
	private Integer outCondAssociation3;

	/**
	* 
	*/
	private Integer outCondAssociation4;

	/**
	* 
	*/
	private Integer outCondAssociation5;

	/**
	* 
	*/
	private Integer outCondAssociation6;

	/**
	* 
	*/
	private Integer outCondAssociation7;

	/**
	* 
	*/
	private Integer outCondAssociation8;

	/**
	* 
	*/
	private Integer outCondAssociation9;

	/**
	* 
	*/
	private Integer outCondAssociation10;

	public static ExCndOutputDto fromDomain(ExOutLinkTable domain) {
		return new ExCndOutputDto(domain);
	}

	public ExCndOutputDto(ExOutLinkTable domain) {
		super();
		this.categoryId = domain.getCategoryId().v();
		this.mainTable =  domain.getMainTable().v();
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
