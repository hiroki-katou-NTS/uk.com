package nts.uk.ctx.hr.develop.app.sysoperationset.eventmanage.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JMM018Dto {
	// 処理結果
	private int available;
	// <List>イベント管理 
	private List<EventOperationDto> eventOperList;
	//
	private List<MenuOperationDto> menuOperList;
}
