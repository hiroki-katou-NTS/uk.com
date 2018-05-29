package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.delete;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class DeleteStampCardCommand {


	@PeregRecordId
	private String stampCardId;
	
}
