package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpecialHolidayInterimMngData {
	private List<InterimSpecialHolidayMng> lstSpecialInterimMng;
	private List<InterimRemain> lstInterimMng;
}
