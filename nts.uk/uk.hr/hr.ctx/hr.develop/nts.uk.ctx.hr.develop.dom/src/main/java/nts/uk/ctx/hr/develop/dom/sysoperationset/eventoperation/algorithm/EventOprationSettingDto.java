package nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.EventOperation;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuOperation;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class EventOprationSettingDto {
	private boolean result;
	private List<EventOperation> listEventOper;
	private List<MenuOperation> listMenuOper;
}
