package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialLeaveGrantRemainingDataTotal {
	
	List<SpecialLeaveGrantRemainingData> lstGrantDataTotal;
	/**
	 * //付与予定
	 */
	List<SpecialLeaveGrantRemainingData> lstGrantDataMemory;
	/**
	 * 付与済
	 */
	List<SpecialLeaveGrantRemainingData> lstGrantDatabase;
}
