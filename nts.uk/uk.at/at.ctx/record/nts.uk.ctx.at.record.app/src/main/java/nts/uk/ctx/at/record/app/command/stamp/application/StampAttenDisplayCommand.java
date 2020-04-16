package nts.uk.ctx.at.record.app.command.stamp.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.application.StampAttenDisplay;
/**
 * 
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StampAttenDisplayCommand {
	
	/** 表示項目一覧 */
	private int displayItemId;
	
	public StampAttenDisplay toDomain(String companyId){
		return new StampAttenDisplay(companyId, displayItemId);
	}
	
	public static StampAttenDisplayCommand fromDomain(StampAttenDisplay x){
		return new StampAttenDisplayCommand(x.getDisplayItemId());
	}
}
