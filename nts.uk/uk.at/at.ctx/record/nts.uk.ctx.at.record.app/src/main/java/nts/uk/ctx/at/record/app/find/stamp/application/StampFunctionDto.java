package nts.uk.ctx.at.record.app.find.stamp.application;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.app.command.stamp.application.StampAttenDisplayCommand;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;

@Data
@AllArgsConstructor
public class StampFunctionDto {
	/** 使用区分 */
	private int usrAtr;
	
	/** 表示項目一覧 */
	private List<StampAttenDisplayCommand> lstDisplayItemId;
	
	public static StampFunctionDto fromDomain (StampResultDisplay x){
		
		return new StampFunctionDto(x.getUsrAtr().value, x.getLstDisplayItemId().stream().map(mapper->StampAttenDisplayCommand.fromDomain(mapper)).collect(Collectors.toList()));
	}
}
