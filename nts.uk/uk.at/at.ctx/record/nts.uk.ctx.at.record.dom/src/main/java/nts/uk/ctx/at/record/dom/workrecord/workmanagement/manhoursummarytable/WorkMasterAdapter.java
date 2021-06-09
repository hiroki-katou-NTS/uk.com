package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;

import java.util.List;

/**
 * 作業マスタを取得するAdapter
 */
public interface WorkMasterAdapter {

    List<Task> getTaskList(String cid, Integer taskFrameNo, List<String> codes);
}
