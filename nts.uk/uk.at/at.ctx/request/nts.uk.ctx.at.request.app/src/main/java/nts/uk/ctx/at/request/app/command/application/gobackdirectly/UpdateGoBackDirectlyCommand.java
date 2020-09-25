package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectlyDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.InforGoBackCommonDirectDto;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateGoBackDirectlyCommand {
	private ApplicationDto applicationDto;
	private GoBackDirectlyDto goBackDirectlyDto;
	private InforGoBackCommonDirectDto inforGoBackCommonDirectDto;
}
