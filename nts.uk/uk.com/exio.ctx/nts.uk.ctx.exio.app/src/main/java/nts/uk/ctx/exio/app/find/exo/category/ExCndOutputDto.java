package nts.uk.ctx.exio.app.find.exo.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.category.ExCndOutput;

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
	private String categoryId;

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
	private int outCondAssociation1;

	/**
	* 
	*/
	private int outCondAssociation2;

	/**
	* 
	*/
	private int outCondAssociation3;

	/**
	* 
	*/
	private int outCondAssociation4;

	/**
	* 
	*/
	private int outCondAssociation5;

	/**
	* 
	*/
	private int outCondAssociation6;

	/**
	* 
	*/
	private int outCondAssociation7;

	/**
	* 
	*/
	private int outCondAssociation8;

	/**
	* 
	*/
	private int outCondAssociation9;

	/**
	* 
	*/
	private int outCondAssociation10;

	public static ExCndOutputDto fromDomain(ExCndOutput domain) {
		return new ExCndOutputDto(domain.getCategoryId().v(), domain.getMainTable().v(), domain.getForm1().v(),
				domain.getForm2().v(), domain.getConditions().v(), domain.getOutCondItemName1().v(),
				domain.getOutCondItemName2().v(), domain.getOutCondItemName3().v(), domain.getOutCondItemName4().v(),
				domain.getOutCondItemName5().v(), domain.getOutCondItemName6().v(), domain.getOutCondItemName7().v(),
				domain.getOutCondItemName8().v(), domain.getOutCondItemName9().v(), domain.getOutCondItemName10().v(),
				domain.getOutCondAssociation1().value, domain.getOutCondAssociation2().value,
				domain.getOutCondAssociation3().value, domain.getOutCondAssociation4().value,
				domain.getOutCondAssociation5().value, domain.getOutCondAssociation6().value,
				domain.getOutCondAssociation7().value, domain.getOutCondAssociation8().value,
				domain.getOutCondAssociation9().value, domain.getOutCondAssociation10().value);
	}

}
