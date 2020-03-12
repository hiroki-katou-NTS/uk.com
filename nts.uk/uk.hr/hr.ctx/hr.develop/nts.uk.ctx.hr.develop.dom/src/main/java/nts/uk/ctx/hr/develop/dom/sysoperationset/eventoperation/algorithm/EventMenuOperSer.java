package nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.algorithm;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.HRDevEvent;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.HRDevMenu;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.algorithm.AvailableEventAndMenuDto;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.algorithm.HREventMenuService;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.EventOperation;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuOperation;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuOperationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EventMenuOperSer {
	@Inject
	private HREventMenuService hrEventMenuSer;
	
	@Inject
	private EventOperationRepository eventRep;
	
	@Inject
	private MenuOperationRepository menuRep;
	
	/**
	 * ドメインモデル[イベント管理]を取得する
	 * @param companyId
	 * @param eventId
	 * @return
	 * @author yennth
	 */
	public Optional<EventOperation> findEventByKey(String companyId, int eventId){
		Optional<EventOperation> eventOper = eventRep.findByKey(companyId, eventId);
		return eventOper;
	}
	
	/**
	 * ドメインモデル[イベント管理]を追加する
	 * @param eventOperation
	 * @author yennth
	 */
	public void addEvent(EventOperation eventOperation){
		eventRep.add(eventOperation);
	}
	
	/**
	 * ドメインモデル[イベント管理]を更新する
	 * @param eventOperation
	 * @author yennth
	 */
	public void updateEvent(EventOperation eventOperation){
		eventRep.update(eventOperation);
	}
	
	/**
	 * ドメインモデル[メニュー管理]を取得する
	 * @param companyId
	 * @param programId
	 * @return
	 */
	public Optional<MenuOperation> findMenuByKey(String companyId, String programId){
		Optional<MenuOperation> menu = menuRep.findByKey(companyId, programId);
		return menu;
	}
	
	/**
	 * ドメインモデル[メニュー管理]を追加する
	 * @param menuOperation
	 * @author yennth
	 */
	public void addMenu(MenuOperation menuOperation){
		menuRep.add(menuOperation);
	}
	/**
	 * ドメインモデル[メニュー管理]を更新する
	 * @param eventOperation
	 * @author yennth
	 */
	public void updateMenu(MenuOperation menuOperation){
		menuRep.update(menuOperation);
	}

	
	// イベント管理設定を取得する
	public EventOprationSettingDto getEventOprationSettings(){
		List<EventOperation> listEventOper = new ArrayList<>();
		List<MenuOperation> listMenuOper = new ArrayList<>();
		AvailableEventAndMenuDto hrEventMenu = hrEventMenuSer.getAvailableEventAndMenu();
		if(!hrEventMenu.isAvailable()){
			return new EventOprationSettingDto(false, new ArrayList<>(), new ArrayList<>());
		}else{
			// アルゴリズム[イベント管理を取得する]を実行する
			for(HRDevEvent item: hrEventMenu.getListHrDevEvent()){
				Optional<EventOperation> eventOper = this.findEventByKey(AppContexts.user().companyId(), item.getEventId().value);
				if(!eventOper.isPresent()){
					// ドメインモデル[イベント管理]を追加する - insert event operation
					eventRep.add(EventOperation.createFromJavaType(item.getEventId().value, 0, 
											AppContexts.user().companyId(), 
											new BigInteger(AppContexts.user().companyCode())));
					listEventOper.add(EventOperation.createFromJavaType(item.getEventId().value, 0, AppContexts.user().companyId(), 
							new BigInteger(AppContexts.user().companyCode())));
				}else{
					listEventOper.add(eventOper.get());
				}
			}
			// アルゴリズム[メニュー管理を取得する]を実行する
			for(HRDevMenu obj: hrEventMenu.getListHrDevMenu()){
				Optional<MenuOperation> menuOper = this.findMenuByKey(AppContexts.user().companyId(), obj.getProgramId().toString());
				if(menuOper.isPresent()){
					listMenuOper.add(menuOper.get());
				}else{
					menuRep.add(MenuOperation.createFromJavaType(obj.getProgramId().toString(), 0, 
							AppContexts.user().companyId(), 0, 0, 0,
							new BigInteger(AppContexts.user().companyCode())));
					listMenuOper.add(MenuOperation.createFromJavaType(obj.getProgramId().toString(), 0, 
																		AppContexts.user().companyId(), 0, 0, 0,
																		new BigInteger(AppContexts.user().companyCode())));
				}
			}
		}
		return new EventOprationSettingDto(true, listEventOper, listMenuOper);
	}
}
