package nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.dto.EventOperationDto;
import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.dto.HRDevEventDto;
import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.dto.HRDevMenuDto;
import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.dto.JMM018Dto;
import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.dto.MenuOperationDto;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.algorithm.HREventMenuService;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.EventOperation;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuOperation;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.algorithm.EventMenuOperSer;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JMM018Finder {
	
	@Inject
	private HREventMenuService hrEventMenuSer;
	
	@Inject
	private EventMenuOperSer eventMenuOperSer;
	
	/**
	 * ドメインモデル[人材育成イベント]を取得する - lấy domain 人材育成イベント với điều kiện 利用できる= true và [order by] dspOrder　ASC
	 * @return
	 * @author yennth
	 */
	public List<HRDevEventDto> findHREvent(){
		List<HRDevEventDto> listResult = this.hrEventMenuSer.getAvailableEventAndMenu().getListHrDevEvent()
											.stream().map(x -> HRDevEventDto.fromDomain(x))
											.collect(Collectors.toList());
		return listResult;
	}
	
	/**
	 * ドメインモデル[人材育成メニュー]を取得する - lấy domain 人材育成メニュー với điều kiện 利用できる= true và [order by] dspOrder　ASC
	 * @return
	 * @author yennth
	 */
	public List<HRDevMenuDto> findHRMenu(){
		List<HRDevMenuDto> listResult = this.hrEventMenuSer.getAvailableEventAndMenu().getListHrDevMenu()
											.stream().map(x -> HRDevMenuDto.fromDomain(x))
											.collect(Collectors.toList());
		return listResult;
	}
	
	/**
	 * ドメインモデル[イベント管理]を取得する - get event operation
	 * @param eventId
	 * @return
	 * @author yennth
	 */
	public Optional<EventOperationDto> findEventOper(int eventId){
		Optional<EventOperation> findResult = eventMenuOperSer.findEventByKey(AppContexts.user().companyId(), eventId);
		if(findResult.isPresent()){
			EventOperation domain = findResult.get();
			EventOperationDto mapping = new EventOperationDto(domain.getEventId().value, domain.getUseEvent().value, domain.getCcd());
			return Optional.of(mapping);
		}else{
			return Optional.empty();
		}
	}
	
	/**
	 * ドメインモデル[イベント管理]を取得する - get event operation
	 * @param programId
	 * @return
	 * @author yennth
	 */
	public Optional<MenuOperationDto> findMenutOper(String programId){
		Optional<MenuOperation> findResult = eventMenuOperSer.findMenuByKey(AppContexts.user().companyId(), programId);
		if(findResult.isPresent()){
			MenuOperation domain = findResult.get();
			MenuOperationDto mapping = new MenuOperationDto(domain.getProgramId().v(), domain.getUseMenu().value, 
										domain.getCompanyId(), domain.getUseApproval().value, domain.getUseNotice().value,
										domain.getCcd());
			return Optional.of(mapping);
		}else{
			return Optional.empty();
		}
	}
	
	/**
	 * アルゴリズム[イベント管理設定を取得する]を実行する
	 * @return
	 * @author yennth
	 */
	public JMM018Dto finderJMM018(){
		// アルゴリズム[利用可能なイベントとメニューを取得する]を実行する
		List<HRDevEventDto> listHrEvent = findHREvent();
		List<HRDevMenuDto> listHrMenu = findHRMenu();
		boolean available = hrEventMenuSer.getAvailableEventAndMenu().isAvailable();
		
		List<EventOperationDto> eventOper = eventMenuOperSer.getEventOprationSettings().getListEventOper().stream()
											.map(x -> new EventOperationDto(x.getEventId().value, x.getUseEvent().value, x.getCcd()))
											.collect(Collectors.toList());
		List<MenuOperationDto> menuOper = eventMenuOperSer.getEventOprationSettings().getListMenuOper().stream()
											.map(q -> new MenuOperationDto(q.getProgramId().v(), q.getUseMenu().value, q.getCompanyId(), q.getUseApproval().value, q.getUseNotice().value, q.getCcd()))
											.collect(Collectors.toList());
		
		JMM018Dto result = new JMM018Dto(available, listHrEvent, listHrMenu, eventOper, menuOper);
		return result;
	}
}
