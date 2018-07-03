package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpecialHolidayInfor {
	/**
	 * ・特別休暇情報一覧
	 */
	private GrantDaysInfor grantDaysInfor;
	/**
	 * 期限日
	 */
	private Optional<GeneralDate> deadlineDate;
}
