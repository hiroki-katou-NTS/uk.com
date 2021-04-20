package nts.uk.ctx.at.record.dom.adapter.specificdatesetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Import 特定日項目
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecSpecificDateItemImport {
	/**
	 * companyId
	 */
	private String companyId;
	
	/**
	 * useAtr
	 */
	private int useAtr;
	
	/**
	 * specificDateItemNo
	 */
	private Integer specificDateItemNo;
	
	/**
	 * specificName
	 */
	private String specificName;
}
