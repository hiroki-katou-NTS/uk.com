package nts.uk.ctx.at.record.app.command.stamp.application;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.application.StamPromptApplication;
import nts.uk.ctx.at.record.dom.stamp.application.StampRecordDis;
import nts.uk.shr.com.context.AppContexts;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddStamPromptApplicationCommad {

	
	private List<StampRecordDisCommand> lstStampRecord;
	
	public StamPromptApplication toDomain(){
		
		List<StampRecordDis> lstStampRecordDis = lstStampRecord.stream().map(mapper->StampRecordDisCommand.toDomain(mapper)).collect(Collectors.toList());
		
		String companyId = AppContexts.user().companyId();
		return new StamPromptApplication(companyId, lstStampRecordDis);
		
	}
}
