package nts.uk.ctx.at.function.dom.adapter;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@Value
public class PublicHolidaySettingImport {
	
	/** The company id. */
	private String companyId;
	
	/** The is manage com public hd. */
	private Integer isManageComPublicHd;

	public PublicHolidaySettingImport(String companyId, Integer isManageComPublicHd) {
		super();
		this.companyId = companyId;
		this.isManageComPublicHd = isManageComPublicHd;
	}
	
	

}
