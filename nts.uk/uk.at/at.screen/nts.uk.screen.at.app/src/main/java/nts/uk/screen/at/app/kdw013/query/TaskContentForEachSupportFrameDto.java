package nts.uk.screen.at.app.kdw013.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.TaskContentForEachSupportFrame;

/**
 * 
 * @author tutt
 *
 */
@Getter
@NoArgsConstructor
public class TaskContentForEachSupportFrameDto {

	/** 応援勤務枠No */
	public int frameNo;

	/** 作業内容 */
	public TaskContentDto taskContent;

	public TaskContentForEachSupportFrameDto(TaskContentForEachSupportFrame domain) {
		this.frameNo = domain.getFrameNo().v();
		this.taskContent = new TaskContentDto(domain.getTaskContent());
	}
}
