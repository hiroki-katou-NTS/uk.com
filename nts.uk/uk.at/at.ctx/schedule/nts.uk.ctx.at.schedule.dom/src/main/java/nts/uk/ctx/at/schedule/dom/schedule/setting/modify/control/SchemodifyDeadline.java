package nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.UseAtr;
/**
 * 
 * @author phongtq
 *
 */
@Getter
@AllArgsConstructor
public class SchemodifyDeadline {

	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 利用区分*/
	private UseAtr useCls;
	
	/** 修正期限*/
	private CorrectDeadline correctDeadline;
	
	public static SchemodifyDeadline createFromJavaType(String companyId, String roleId, int useCls, Integer correctDeadline){
		return new SchemodifyDeadline(companyId, roleId, EnumAdaptor.valueOf(useCls, UseAtr.class), new CorrectDeadline(correctDeadline));
	}
	
}
