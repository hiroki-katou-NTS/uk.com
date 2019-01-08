package nts.uk.ctx.at.record.dom.operation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
/**
 * 
 * @author Hoidd
 *
 */
@Getter
@AllArgsConstructor
public class ApplicationCallExport {
	/** 会社ID */
	private String companyId;
	/** 呼び出す申請一覧 */
	private ApplicationTypeExport appType;
	
	public static ApplicationCallExport createFromJavaType(String companyId, int appType){
		return new ApplicationCallExport(companyId, EnumAdaptor.valueOf(appType, ApplicationTypeExport.class));
	}
}

