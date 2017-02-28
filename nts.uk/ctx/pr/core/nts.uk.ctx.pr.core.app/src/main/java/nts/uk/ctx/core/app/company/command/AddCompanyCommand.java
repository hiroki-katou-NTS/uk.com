package nts.uk.ctx.core.app.company.command;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * AddCompanyCommand
 */
@Getter
@Setter
public class AddCompanyCommand extends CompanyCommandBase {

	private GeneralDateTime date;
}
