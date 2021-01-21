package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.InforGoBackCommonDirectDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertApplicationGoBackDirectlyCommand {
	/**
	 * 直行直帰 Item
	 */
	InsertGoBackDirectlyCommand_Old goBackCommand;
	/**
	 * 申請 ITEM
	 */
	CreateApplicationCommand appCommand;
	
	private InforGoBackCommonDirectDto inforGoBackCommonDirectDto;
	
	private boolean checkOver1Year;
	

}
