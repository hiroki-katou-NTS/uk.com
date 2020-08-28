package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisInfoOrgDto {
	
	private String designation;	
	/** コード **/
	private String code;
	/** 名称 **/
	private String name;
	/** 表示名 **/
	private String displayName;
	/** 呼称 **/
	private String genericTerm;
}
