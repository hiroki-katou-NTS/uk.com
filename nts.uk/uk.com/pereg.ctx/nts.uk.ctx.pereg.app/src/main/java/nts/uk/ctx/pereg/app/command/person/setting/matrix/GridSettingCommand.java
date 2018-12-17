package nts.uk.ctx.pereg.app.command.person.setting.matrix;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pereg.app.command.person.setting.matrix.matrixdisplayset.CreateMatrixDisplaySetCommand;
import nts.uk.ctx.pereg.app.command.person.setting.matrix.personinfomatrixitem.CreatePersonInfoMatrixItemCommand;

@Getter
@Setter
@AllArgsConstructor
public class GridSettingCommand {
	private CreateMatrixDisplaySetCommand maxtrixDisplays;
	private CreatePersonInfoMatrixItemCommand personInfoItems;
}
