package nts.uk.ctx.at.schedule.app.find.schedule.setting.displaycontrol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.SchePerInfoAtr;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SchePerInfoAtrDto {
	/** 会社ID */
	private String companyId;
	
	/** コード */
	private int personInfoAtr;
	
	/**
	 * From domain
	 * 
	 * @param domain
	 * @return
	 */
	public static SchePerInfoAtrDto fromDomain(SchePerInfoAtr domain){
		
		return new SchePerInfoAtrDto(
				domain.getCompanyId(),
				domain.getPersonInfoAtr().value
		);
	}
}
