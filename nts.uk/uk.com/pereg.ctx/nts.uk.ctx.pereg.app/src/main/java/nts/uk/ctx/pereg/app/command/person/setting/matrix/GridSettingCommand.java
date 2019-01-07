package nts.uk.ctx.pereg.app.command.person.setting.matrix;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pereg.app.command.person.setting.matrix.matrixdisplayset.CreateMatrixDisplaySetCommand;
import nts.uk.ctx.pereg.app.command.person.setting.matrix.personinfomatrixitem.CreatePersonInfoMatrixItemCommand;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GridSettingCommand {
	private CreateMatrixDisplaySetCommand maxtrixDisplays;
	private List<CreatePersonInfoMatrixItemCommand> personInfoItems;
}
