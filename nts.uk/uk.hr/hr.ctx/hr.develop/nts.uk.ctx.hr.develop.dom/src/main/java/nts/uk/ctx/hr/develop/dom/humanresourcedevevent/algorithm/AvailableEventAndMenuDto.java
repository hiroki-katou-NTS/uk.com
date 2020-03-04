package nts.uk.ctx.hr.develop.dom.humanresourcedevevent.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.HRDevEvent;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.HRDevMenu;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class AvailableEventAndMenuDto {
	private List<HRDevEvent> listHrDevEvent;
	private List<HRDevMenu> listHrDevMenu;
	private boolean available;
}
