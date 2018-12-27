package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RsvLeaveInfoImport {
	//付与前残数
	private Double befRemainDay;
	//付与後残数
	private Double aftRemainDay;
	//付与日
	private Double grantDay;
	//残数
	private Double remainingDays;
}
