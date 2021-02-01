package nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;

@NoArgsConstructor
@Setter
@AllArgsConstructor
public class LinkingManagementInforDto {

	/*振出用_休出代休紐付け管理<List>*/
	public List<LeaveComDayOffManaDto> recLeaveComDayOffMana;
	
	/*振休用_休出代休紐付け管理<List>*/
	public List<LeaveComDayOffManaDto> absLeaveComDayOffMana;
	
	/*振休用_振出振休紐付け管理<List>*/
	public List<PayoutSubofHDManagementDto> absPayoutSubofHDManagements;
}
