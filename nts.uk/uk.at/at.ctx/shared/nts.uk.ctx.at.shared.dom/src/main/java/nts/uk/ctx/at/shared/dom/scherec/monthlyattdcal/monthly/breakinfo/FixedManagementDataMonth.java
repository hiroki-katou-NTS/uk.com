package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;

/**
 * @author ThanhNX
 *
 *         月別確定管理データ
 */
@AllArgsConstructor
@Getter
public class FixedManagementDataMonth {

	// 確定代休管理データ
	private List<CompensatoryDayOffManaData> compenDayOffMagData;

	// 確定振休管理データ
	private List<SubstitutionOfHDManagementData> substitutionHDMagData;
	
	public FixedManagementDataMonth() {
		this.compenDayOffMagData = new ArrayList<>();
		this.substitutionHDMagData = new ArrayList<>();
	}

}
