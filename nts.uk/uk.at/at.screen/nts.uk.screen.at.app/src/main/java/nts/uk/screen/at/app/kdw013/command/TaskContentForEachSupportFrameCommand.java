package nts.uk.screen.at.app.kdw013.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.ouen.SupportFrameNo;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.TaskContentForEachSupportFrame;

/**
 * 
 * @author tutt
 *
 */
@Data
@NoArgsConstructor
public class TaskContentForEachSupportFrameCommand {

	/** 応援勤務枠No */
	private int frameNo;

	/** 作業内容 */
	private TaskContentCommand taskContent;

	public TaskContentForEachSupportFrame toDomain() {
		return new TaskContentForEachSupportFrame(new SupportFrameNo(this.frameNo), this.taskContent.toDomain());
	}
}
