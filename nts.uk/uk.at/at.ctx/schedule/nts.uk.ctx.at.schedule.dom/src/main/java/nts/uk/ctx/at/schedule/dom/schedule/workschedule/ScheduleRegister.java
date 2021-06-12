package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ScheduleRegister {

    List<ScheduleRegisterTarget> targets;
    
    boolean overWrite;
}
