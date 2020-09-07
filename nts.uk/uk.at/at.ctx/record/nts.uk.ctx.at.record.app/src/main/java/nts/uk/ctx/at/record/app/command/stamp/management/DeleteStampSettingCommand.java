package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteStampSettingCommand {
	
	private int pageNo;
	
	private Integer mode;
}
