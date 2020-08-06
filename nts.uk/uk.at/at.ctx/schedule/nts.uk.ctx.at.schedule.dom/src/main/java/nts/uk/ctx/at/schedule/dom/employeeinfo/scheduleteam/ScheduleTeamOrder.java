package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;



/**
 * スケジュールチームの並び順
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author Hieult
 *
 */
@AllArgsConstructor
@Getter
public class ScheduleTeamOrder implements DomainAggregate{
	/** 職場グループID **/ 
	private final String WKPGRPID;
	
	/**並び順リストOrderedList<スケジュールチームコード> --- OrderedList<スケジュールチームコード> **/
	private List<ScheduleTeamCd> listOrderScheduleTeamCd;
	
	/**
	 * [C-1] 作る		
	 * @param WKPGRPID
	 * @param listOrderScheduleTeamCd
	 * @return
	 */
	public static  ScheduleTeamOrder create(String WKPGRPID ,List<ScheduleTeamCd> listOrderScheduleTeamCd ){
		if(!(listOrderScheduleTeamCd.size()> 0)){
			//inv-1	@並び順リスト.size > 0	
			throw new RuntimeException("Debug message inv-1");
		}
		List<String> listDistinct = listOrderScheduleTeamCd.stream().map(c ->c.v()).distinct().collect(Collectors.toList());
		if(listDistinct.size() < listOrderScheduleTeamCd.size() ){
			//inv-2	@並び順リストのスケジュールチームコードが重複しない
			throw new RuntimeException("Debug message inv-2");
		}
		return new ScheduleTeamOrder(WKPGRPID, listOrderScheduleTeamCd);
	}
	
	/**
	 * [1] 末尾に追加する
	 * 並び順リストにスケジュールチームを追加する			
	 * @param scheduleTeamCd
	 */
	public void addSheduleTeamCd(ScheduleTeamCd scheduleTeamCd){
		List<String> listDistinct = listOrderScheduleTeamCd.stream().map(c ->c.v()).distinct().collect(Collectors.toList());
		if(listDistinct.size() < listOrderScheduleTeamCd.size() ){
			//inv-2	@並び順リストのスケジュールチームコードが重複しない
			throw new RuntimeException("Debug message inv-2");
		}
		listOrderScheduleTeamCd.add(scheduleTeamCd);
	}
	/**
	 * [2] 更新する
	 * 並び順リストを更新する			
	 * @param listCode
	 */
	public void update(List<ScheduleTeamCd> listCode ){
		if(!(listOrderScheduleTeamCd.size()> 0)){
			//inv-1	@並び順リスト.size > 0	
			throw new RuntimeException("Debug message inv-1");
		}
		List<String> listDistinct = listOrderScheduleTeamCd.stream().map(c ->c.v()).distinct().collect(Collectors.toList());
		if(listDistinct.size() < listOrderScheduleTeamCd.size() ){
			//inv-2	@並び順リストのスケジュールチームコードが重複しない
			throw new RuntimeException("Debug message inv-2");
		}
		listOrderScheduleTeamCd = listCode;
	}
	/**
	 * [3] 削除する	
	 * 並び順リストからスケジュールチームを除外する
	 * @param scheduleTeamCd
	 */
	public void delete(ScheduleTeamCd scheduleTeamCd){
		if(listOrderScheduleTeamCd.size()> 0){
			//inv-1	@並び順リスト.size > 0	
			throw new RuntimeException("Debug message inv-1");
		}
		listOrderScheduleTeamCd.remove(scheduleTeamCd);	
	}
}
