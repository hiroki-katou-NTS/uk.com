package nts.uk.ctx.at.schedule.app.command.schedule.setting.modifydeadline;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPermissonCommand {
	/** ロールID*/	
	private String roleId;
	
	private List<CommonAuthorCommand> commonAuthor;
	
	private List<PerWorkplaceCommand> perWorkplace;
	
	private List<PersAuthorityCommand> persAuthority;
	
	private List<DateAuthorityCommand> dateAuthority;
	
	private List<ShiftPermissonCommand> shiftPermisson;
	
	private SchemodifyDeadlineCommand schemodifyDeadline;
	
}
