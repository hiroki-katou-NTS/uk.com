package nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.ExternalCooperationInfo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskAbName;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskDisplayInfo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskName;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public class TaskPaletteOrganizationTest {
	
	@Injectable
	TaskPaletteOrganization.Require require;
	
	@Test
	public void testCreate_pageNotTrue() {
		
		TargetOrgIdenInfor targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("workplace-id");
		
		Map<Integer, TaskCode> tasks = new HashMap<>();
		tasks.put(1, new TaskCode("code3"));
		tasks.put(2, new TaskCode("code1"));
		tasks.put(3, new TaskCode("code5"));
		tasks.put(4, new TaskCode("code6"));
		tasks.put(5, new TaskCode("code2"));
		tasks.put(6, new TaskCode("code4"));
		
		TaskPaletteDisplayInfo displayInfo = new TaskPaletteDisplayInfo(
				new TaskPaletteName("task-palette-name"), 
				Optional.of(new TaskPaletteRemark("task-palette-remark")));
		
		NtsAssert.businessException("Msg_2062", () -> {
			TaskPaletteOrganization.create( targetOrg, 0, displayInfo, tasks);
		}); 
		
		NtsAssert.businessException("Msg_2062", () -> {			
			TaskPaletteOrganization.create( targetOrg, 6, displayInfo, tasks);
		}); 
		
	}
	
	@Test
	public void testCreate_taskSize0() {
		
		Map<Integer, TaskCode> tasks = new HashMap<>();
		
		NtsAssert.businessException("Msg_2067", () -> {
			
			TaskPaletteOrganization.create(
					TargetOrgIdenInfor.creatIdentifiWorkplace("workplace-id"), 
					1, 
					new TaskPaletteDisplayInfo(
							new TaskPaletteName("task-palette-name"), 
							Optional.of(new TaskPaletteRemark("task-palette-remark"))), 
					tasks);
		}); 
		
	} 
	
	@Test
	public void testCreate_taskSize11() {
		
		Map<Integer, TaskCode> tasks = new HashMap<>();
		tasks.put(1, new TaskCode("code3"));
		tasks.put(2, new TaskCode("code1"));
		tasks.put(3, new TaskCode("code5"));
		tasks.put(4, new TaskCode("code6"));
		tasks.put(5, new TaskCode("code2"));
		tasks.put(6, new TaskCode("code4"));
		tasks.put(7, new TaskCode("code4"));
		tasks.put(8, new TaskCode("code4"));
		tasks.put(9, new TaskCode("code4"));
		tasks.put(10, new TaskCode("code4"));
		tasks.put(11, new TaskCode("code4"));
		
		NtsAssert.businessException("Msg_2067", () -> {
			
			TaskPaletteOrganization.create(
					TargetOrgIdenInfor.creatIdentifiWorkplace("workplace-id"), 
					1, 
					new TaskPaletteDisplayInfo(
							new TaskPaletteName("task-palette-name"), 
							Optional.of(new TaskPaletteRemark("task-palette-remark"))), 
					tasks);
		}); 
		
	} 
	
	@Test
	public void testCreate_success() {
		
		Map<Integer, TaskCode> tasks = new HashMap<>();
		tasks.put(5, new TaskCode("code5"));
		tasks.put(3, new TaskCode("code3"));
		tasks.put(4, new TaskCode("code4"));
		tasks.put(2, new TaskCode("code2"));
		tasks.put(6, new TaskCode("code6"));
		tasks.put(1, new TaskCode("code1"));
		
			
		TaskPaletteOrganization result = TaskPaletteOrganization.create(
					TargetOrgIdenInfor.creatIdentifiWorkplace("workplace-id"), 
					1, 
					new TaskPaletteDisplayInfo(
							new TaskPaletteName("task-palette-name"), 
							Optional.of(new TaskPaletteRemark("task-palette-remark"))), 
					tasks);
		
		assertThat( result.getTargetOrg().getTargetId() ).isEqualTo( "workplace-id");
		assertThat( result.getPage() ).isEqualTo( 1 );
		assertThat( result.getDisplayInfo().getName().v() ).isEqualTo("task-palette-name");
		assertThat( result.getDisplayInfo().getRemark().get().v() ).isEqualTo("task-palette-remark");
		assertThat( result.getTasks().entrySet() )
			.extracting( 
					x -> x.getKey(), 
					x -> x.getValue().v())
			.containsExactly( 
					// sorted
					tuple(1, "code1"),
					tuple(2, "code2"),
					tuple(3, "code3"),
					tuple(4, "code4"),
					tuple(5, "code5"),
					tuple(6, "code6"));
		
	} 
	
	@Test
	public void testGetDisplayInfo() {
		
		Map<Integer, TaskCode> tasks = new HashMap<>();
		tasks.put(1, new TaskCode("code1"));
		tasks.put(2, new TaskCode("code2"));
		tasks.put(3, new TaskCode("code3"));
		
			
		TaskPaletteOrganization target = TaskPaletteOrganization.create(
					TargetOrgIdenInfor.creatIdentifiWorkplace("workplace-id"), 
					1, 
					new TaskPaletteDisplayInfo(
							new TaskPaletteName("task-palette-name"), 
							Optional.of(new TaskPaletteRemark("task-palette-remark"))), 
					tasks);
		
		Task task2 = Helper.createTask("code2", "task-name2", "abName2");
		Task task3 = Helper.createTask("code3", "task-name3", "task-abName3");
		
		new Expectations(task2, task3) {{
			
			require.getTask( (TaskFrameNo) any , new TaskCode("code1"));
			//result = empty;
			
			require.getTask( (TaskFrameNo) any , new TaskCode("code2"));
			result = Optional.of(task2);
			
			require.getTask( (TaskFrameNo) any , new TaskCode("code3"));
			result = Optional.of(task3);
			
			task2.isWithinExpirationDate( (GeneralDate) any );
			result = false;
			
			task3.isWithinExpirationDate( (GeneralDate) any );
			result = true;
		}};
		
		TaskPalette result = target.getDisplayInfo(require, GeneralDate.today());
		
		assertThat( result.getPage() ).isEqualTo( 1 );
		assertThat( result.getName().v() ).isEqualTo( "task-palette-name");
		assertThat( result.getRemark().get().v() ).isEqualTo( "task-palette-remark" );
		assertThat( result.getTasks().entrySet() ).extracting( 
				d -> d.getKey(),
				d -> d.getValue() )
			.containsExactly(
				tuple( 1, TaskPaletteOneFrameDisplayInfo.createWithNotYetRegisteredType( new TaskCode("code1" ))),
				tuple( 2, TaskPaletteOneFrameDisplayInfo.createWithExpiredType( new TaskCode("code2" ))),
				tuple( 3, TaskPaletteOneFrameDisplayInfo.createWithCanUseType( 
						new TaskCode("code3" ), 
						new TaskName("task-name3"), 
						new TaskAbName("task-abName3")))
				);
		
		
	}
	
	static class Helper {
		
		public static Task createTask(String code, String name, String abName) {
			return new Task(
					new TaskFrameNo(1), 
					new TaskCode(code), 
					new DatePeriod(
							GeneralDate.ymd(2020, 1, 1), 
							GeneralDate.ymd(2020, 12, 31)), 
					new TaskDisplayInfo(
							new TaskName(name), 
							new TaskAbName(abName), 
							Optional.empty(), 
							Optional.empty()), 
					new ExternalCooperationInfo(
							Optional.empty(), 
							Optional.empty(), 
							Optional.empty(), 
							Optional.empty(),
							Optional.empty()), 
					new ArrayList<>());
		}
		
	}

}
