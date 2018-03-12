package nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * スケジュール資格設定
 * 
 * @author TanLV
 *
 */
@Getter
@AllArgsConstructor
public class ScheQualifySet extends DomainObject {
	/** 会社ID */
	private String companyId;
	
	/** 資格コード  */
	private String qualifyCode;
	
	/**
	 * Create From Java Type
	 * 
	 * @param companyId
	 * @param qualificationCd
	 * @return
	 */
	public static ScheQualifySet createFromJavaType(String companyId, String qualifyCode) {
		
		return new ScheQualifySet(companyId, qualifyCode);
	}
}
