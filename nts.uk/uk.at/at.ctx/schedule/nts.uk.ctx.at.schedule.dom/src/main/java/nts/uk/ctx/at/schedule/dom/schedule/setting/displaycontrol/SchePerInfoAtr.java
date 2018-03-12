package nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * スケジュール個人情報区分
 * 
 * @author TanLV
 *
 */
@Getter
@AllArgsConstructor
public class SchePerInfoAtr extends DomainObject {
	/** 会社ID **/
	private String companyId;
	
	/** 個人情報区分 **/
	private PerInfoAtr personInfoAtr;
	
	/**
	 * Create From Java Type
	 * 
	 * @param companyId
	 * @param perInfoAtrCd
	 * @return
	 */
	public static SchePerInfoAtr createFromJavaType(String companyId, int personInfoAtr) {
		
		return new SchePerInfoAtr(companyId, EnumAdaptor.valueOf(personInfoAtr, PerInfoAtr.class));
	}
}
