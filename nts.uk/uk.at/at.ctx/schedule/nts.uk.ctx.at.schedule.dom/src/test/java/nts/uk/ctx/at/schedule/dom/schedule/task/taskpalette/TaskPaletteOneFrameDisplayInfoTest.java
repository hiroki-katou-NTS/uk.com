package nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskAbName;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskName;

public class TaskPaletteOneFrameDisplayInfoTest {
	
	@Test
	public void testGetters() {
		TaskPaletteOneFrameDisplayInfo target = new TaskPaletteOneFrameDisplayInfo(
				new TaskCode("001"), 
				TaskStatus.CanUse,
				Optional.of(new TaskName("task-name")), 
				Optional.of(new TaskAbName("task-ab-name")));
		
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void testCreateWithCanUseType() {
		
		TaskPaletteOneFrameDisplayInfo result = 
				TaskPaletteOneFrameDisplayInfo.createWithCanUseType(
						new TaskCode("001"), 
						new TaskName("task-name"), 
						new TaskAbName("task-ab-name"));
		
		assertThat( result.getTaskCode().v()).isEqualTo("001");
		assertThat( result.getTaskStatus()).isEqualTo( TaskStatus.CanUse );
		assertThat( result.getTaskName().get().v()).isEqualTo("task-name");
		assertThat( result.getTaskAbName().get().v()).isEqualTo("task-ab-name");
	}
	
	@Test
	public void testCreateWithNotYetRegisteredType() {
		
		TaskPaletteOneFrameDisplayInfo result = 
				TaskPaletteOneFrameDisplayInfo.createWithNotYetRegisteredType(
						new TaskCode("001"));
		
		assertThat( result.getTaskCode().v()).isEqualTo("001");
		assertThat( result.getTaskStatus()).isEqualTo( TaskStatus.NotYetRegistered );
		assertThat( result.getTaskName()).isEmpty();
		assertThat( result.getTaskAbName()).isEmpty();
	}
	
	@Test
	public void testCreateWithExpiredType() {
		
		TaskPaletteOneFrameDisplayInfo result = 
				TaskPaletteOneFrameDisplayInfo.createWithExpiredType( new TaskCode("001") );
		
		assertThat( result.getTaskCode().v()).isEqualTo("001");
		assertThat( result.getTaskStatus()).isEqualTo( TaskStatus.Expired );
		assertThat( result.getTaskName()).isEmpty();
		assertThat( result.getTaskAbName()).isEmpty();
	}

}
