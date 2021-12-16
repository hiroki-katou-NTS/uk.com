package nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardEdit;

/**
 * 
 * @author NWS_namnv
 *
 */
@Data
@NoArgsConstructor
public class SupportCardSettingCommand {
	
	private int editMethod;
	
	public SupportCardEdit toDomain() {
		return new SupportCardEdit(editMethod);
	}

}
