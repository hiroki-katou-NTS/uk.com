package nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author quytb
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTableOutputSettingCopyCommand {
	/** 複写元_コード*/
	private String copySourceCode;
	
	/** 複写先_コード*/
	private String newCode;
	
	/** 複写先_名称*/
	private String newName;
}
