package nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EventMenuOperSer {
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
	
}
