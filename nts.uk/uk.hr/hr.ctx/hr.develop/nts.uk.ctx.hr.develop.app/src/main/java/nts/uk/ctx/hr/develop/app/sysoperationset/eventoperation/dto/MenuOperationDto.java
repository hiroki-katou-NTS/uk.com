package nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuOperation;

/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class MenuOperationDto {
	// プログラムID
	private String programId;
	// メニューを使用する
	private int useMenu;
	// 会社ID
	private String companyId;
	//承認機能を使用する
	private int useApproval;
	// 通知機能を使用する
	private int useNotice;
	// 会社コード
	private BigInteger ccd;

	
	public static MenuOperationDto fromDomain(MenuOperation domain){
		return new MenuOperationDto(domain.getProgramId().v(), domain.getUseMenu().value, 
									domain.getCompanyId(), domain.getUseApproval().value, domain.getUseNotice().value,
									domain.getCcd());
	}
}
