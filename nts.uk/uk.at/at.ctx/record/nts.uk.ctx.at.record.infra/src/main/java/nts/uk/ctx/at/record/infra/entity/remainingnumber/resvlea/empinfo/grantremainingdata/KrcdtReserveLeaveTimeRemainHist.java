package nts.uk.ctx.at.record.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantTimeRemainHistoryData;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.resvlea.KrcmtReverseLeaRemain;

/**
 * 
 * @author HungTT - 積立年休付付与時点残数履歴データ
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_RVSLEA_TIME_RM_HIST")
public class KrcdtReserveLeaveTimeRemainHist extends KrcmtReverseLeaRemain {

	// 付与処理日
	@Column(name = "GRANT_PROC_DATE")
	public GeneralDate grantDate;

	public KrcdtReserveLeaveTimeRemainHist(GeneralDate grantDate) {
		super();
		this.grantDate = grantDate;
	}

	public static KrcdtReserveLeaveTimeRemainHist fromDomain(ReserveLeaveGrantTimeRemainHistoryData domain) {
		return new KrcdtReserveLeaveTimeRemainHist(domain.getGrantDate());
	}

	public ReserveLeaveGrantTimeRemainHistoryData toDomain() {
		return new ReserveLeaveGrantTimeRemainHistoryData(grantDate);
	}
}
