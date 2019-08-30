package nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.find;

import java.math.BigInteger;
import java.util.ArrayList;
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
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.HREventMenuService;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.EventMenuOperSer;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.EventOperation;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuOperation;
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
		List<HRDevEventDto> listResult = this.hrEventMenuSer.getHrEvent()
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
		List<HRDevMenuDto> listResult = this.hrEventMenuSer.getHrMenu()
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
		List<HRDevEventDto> listHrEvent = findHREvent();
		List<HRDevMenuDto> listHrMenu = new ArrayList<>();
		int available = 1;
		JMM018Dto result = new JMM018Dto(available, listHrEvent, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		// アルゴリズム[利用可能なイベントとメニューを取得する]を実行する
		if(listHrEvent.isEmpty()){
			available = 0;
		}else{
			listHrMenu = findHRMenu(); 
			result.setHRDevMenuList(listHrMenu);
			if(listHrMenu.isEmpty()){
				available = 0; 
			}
		}
		if(available == 0){
			result.setAvailable(available);
			return result;
		}else{
			for(HRDevEventDto item: listHrEvent){
				// アルゴリズム[イベント管理を取得する]を実行する
				Optional<EventOperationDto> getEventOper = findEventOper(item.getEventId());
				if(!getEventOper.isPresent()){
					// ドメインモデル[イベント管理]を追加する - insert event operation
					eventMenuOperSer.addEvent(EventOperation.createFromJavaType(item.getEventId(), 0, 
											AppContexts.user().companyId(), 
											new BigInteger(AppContexts.user().companyCode())));
				}else{
					// [output.イベント管理]を<List>イベント管理に追加する - add item to event operation list
					result.getEventOperList().add(getEventOper.get());
				}
			}
			for(HRDevMenuDto param: listHrMenu){
				// アルゴリズム[メニュー管理を取得する]を実行する
				Optional<MenuOperationDto> getEventOper = findMenutOper(param.getProgramId());
				if(!getEventOper.isPresent()){
					// アルゴリズム[メニュー管理を追加する]を実行する - insert menu operation
					eventMenuOperSer.addMenu(MenuOperation.createFromJavaType(param.getProgramId(), 0, 
																		AppContexts.user().companyId(), 
																		0, 0, 
																		new BigInteger(AppContexts.user().companyCode())));
				}else{
					// [output.メニュー管理]を<List>メニュー管理に追加する - add item to menu operation list
					result.getMenuOperList().add(getEventOper.get());
				}
			}
		}
		return result;
	}
}
