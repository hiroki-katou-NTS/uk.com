package nts.uk.ctx.hr.develop.dom.humanresourcedevevent.algorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.HRDevEvent;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.HRDevMenu;

@Stateless
public class HREventMenuService {
	@Inject
	private HRDevEventRepository hrdevEventRep;
	
	@Inject
	private HRDevMenuRepository hrDevMenuRep;
	
	/**
	 * ドメインモデル[人材育成イベント]を取得する
	 * @return
	 */
	public List<HRDevEvent> getHrEvent(){
		List<HRDevEvent> hrEvent = hrdevEventRep.findByAvailable();
		return hrEvent;
	}
	
	/**
	 * ドメインモデル[人材育成メニュー]を取得する
	 * @return
	 */
	public List<HRDevMenu> getHrMenu(){
		List<HRDevMenu> hrMenu = hrDevMenuRep.findByAvailable();
		return hrMenu;
	}
	
	// 利用可能なイベントとメニューを取得する
	public AvailableEventAndMenuDto getAvailableEventAndMenu(){
		boolean available = false;
		List<HRDevEvent> listEvent = this.getHrEvent();
		if(!listEvent.isEmpty()){
			available = true;
			List<HRDevMenu> listMenu = this.getHrMenu();
			if(listMenu.isEmpty()){
				available = false;
				return new AvailableEventAndMenuDto(listEvent, new ArrayList<>(), available);
			}else{
				return new AvailableEventAndMenuDto(listEvent, listMenu, available);
			}
		}
		return new AvailableEventAndMenuDto(new ArrayList<>(), new ArrayList<>(), available);
	}
}
