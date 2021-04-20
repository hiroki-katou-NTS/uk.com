package nts.uk.ctx.at.schedule.dom.schedule.task.taskpalette;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 組織の作業パレット
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.作業.作業パレット.組織の作業パレット
 * @author dan_pv
 *
 */
@AllArgsConstructor
@Getter
public class TaskPaletteOrganization implements DomainAggregate {
 
	/**
	 * 対象組織
	 */
	private final TargetOrgIdenInfor targetOrg;
	
	/**
	 * ページ
	 */
	private final int page;
	
	/**
	 * 表示情報
	 */
	private TaskPaletteDisplayInfo displayInfo;
	
	/**
	 * 作業パレットの作業
	 */
	private Map<Integer, TaskCode> tasks;
	
	/**
	 * 作業枠NO
	 * @return
	 */
	private TaskFrameNo getTaskFrameNo() {
		return new TaskFrameNo(1);
	}
	
	/**
	 * 作る
	 * @param targetOrg 対象組織
	 * @param page ページ
	 * @param displayInfo 表示情報
	 * @param tasks 作業パレットの作業
	 * @return
	 */
	public static TaskPaletteOrganization create(
			TargetOrgIdenInfor targetOrg,
			int page,
			TaskPaletteDisplayInfo displayInfo,
			Map<Integer, TaskCode> tasks
			) {
		if ( page < 1 || page > 5 ) {
			throw new BusinessException("Msg_2062");
		}
		
		if ( tasks.size() < 1 || tasks.size() > 10 ) {
			throw new BusinessException("Msg_2067");
		}
		
		tasks.entrySet().stream().sorted(Map.Entry.comparingByKey()); 
		
		return new TaskPaletteOrganization(targetOrg, page, displayInfo, tasks);
	}
	
	/**
	 * 表示情報を取得する
	 * @param require
	 * @param date 対象日
	 * @return
	 */
	public TaskPalette getDisplayInfo(Require require, GeneralDate date) {
		
		Map<Integer, TaskPaletteOneFrameDisplayInfo> displayTasks = new LinkedHashMap<>();
		
		this.tasks.forEach( (position, taskCode) -> {
			
			Optional<Task> task = require.getTask( this.getTaskFrameNo(), taskCode );
			
			if ( !task.isPresent() ) {
				displayTasks.put( position, TaskPaletteOneFrameDisplayInfo.createWithNotYetRegisteredType( taskCode )); return;
			}
			
			if ( ! task.get().checkExpirationDate(date) ) {
				displayTasks.put( position, TaskPaletteOneFrameDisplayInfo.createWithExpiredType( taskCode )); return;
			}
			
			displayTasks.put( 
					position , 
					TaskPaletteOneFrameDisplayInfo.createWithCanUseType(
						taskCode, 
						task.get().getDisplayInfo().getTaskName(), 
						task.get().getDisplayInfo().getTaskAbName() ));
		});
		
		return new TaskPalette(this.page, this.displayInfo.getName(), displayTasks, this.displayInfo.getRemark());
	}
	
	public static interface Require {
		
		/**
		 * 作業を取得する
		 * @return
		 */
		Optional<Task> getTask(TaskFrameNo taskFrameNo, TaskCode taskCode);
	} 
	
}
