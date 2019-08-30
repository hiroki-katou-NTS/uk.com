package nts.uk.ctx.hr.develop.dom.humanresourcedevevent;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
}
