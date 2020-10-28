package nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.worktogether;

import lombok.Data;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together.WorkTogether;

import java.util.List;

@Data
public class AddWorkTogetherCommand {

    private String sid;

    private List<String> empMustWorkTogetherLst;

    public WorkTogether toDomain() {
        return WorkTogether.create(this.sid, this.empMustWorkTogetherLst);
    }

}