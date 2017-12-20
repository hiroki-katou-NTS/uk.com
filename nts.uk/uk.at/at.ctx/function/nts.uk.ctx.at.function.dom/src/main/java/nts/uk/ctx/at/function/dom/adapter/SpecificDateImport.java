package nts.uk.ctx.at.function.dom.adapter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpecificDateImport {
	
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

	public SpecificDateImport(String companyId, int useAtr, Integer specificDateItemNo, String specificName) {
		super();
		this.companyId = companyId;
		this.useAtr = useAtr;
		this.specificDateItemNo = specificDateItemNo;
		this.specificName = specificName;
	}
	
}
