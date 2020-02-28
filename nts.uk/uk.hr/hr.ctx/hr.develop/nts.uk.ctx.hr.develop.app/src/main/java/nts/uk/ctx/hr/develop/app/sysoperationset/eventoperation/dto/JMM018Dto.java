package nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JMM018Dto {
	// 処理結果
	private boolean available;
	// 人材育成イベント list
	List<HRDevEventDto> listHrEvent;
	private List<HRDevMenuDto> hRDevMenuList;
	// <List>イベント管理 
	private List<EventOperationDto> eventOperList;
	//
	private List<MenuOperationDto> menuOperList;
}
