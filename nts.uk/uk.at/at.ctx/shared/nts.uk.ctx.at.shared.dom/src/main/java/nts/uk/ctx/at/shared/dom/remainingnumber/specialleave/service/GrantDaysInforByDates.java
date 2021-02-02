package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholiday.export.NextSpecialLeaveGrant;


/** 次回特別休暇付与Work */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GrantDaysInforByDates {
	
	/**
	 * 期間外次回付与日
	 */
	private Optional<GeneralDate> grantDate;
	
	/**
	 * 特別休暇付与一覧
	 */
	private List<NextSpecialLeaveGrant> nextSpecialLeaveGrant;
	
}
