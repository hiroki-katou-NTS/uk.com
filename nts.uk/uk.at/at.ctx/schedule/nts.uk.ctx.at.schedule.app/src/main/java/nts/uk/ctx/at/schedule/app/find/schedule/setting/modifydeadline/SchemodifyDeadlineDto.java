package nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.SchemodifyDeadline;
/**
 * 
 * @author sonnh1
 *
 */
@Data
@AllArgsConstructor
public class SchemodifyDeadlineDto {
	/** 会社ID*/
	private String companyId;
	
	/** ロールID*/	
	private String roleId;
	
	/** 利用区分*/
	private int useCls;
	
	/** 修正期限*/
	private Integer correctDeadline;
	
	public static SchemodifyDeadlineDto fromDomain(SchemodifyDeadline domain){
		return new SchemodifyDeadlineDto(domain.getCompanyId(), domain.getRoleId(), domain.getUseCls().value, domain.getCorrectDeadline().v());
	}
}
