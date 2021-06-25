package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim;


import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimMngCommon;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定振休管理データ
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class InterimAbsMng extends InterimRemain implements InterimMngCommon {
	/**	必要日数 */
	private RequiredDay requeiredDays;
	/**	未相殺日数 */
	private UnOffsetDay unOffsetDays;
	@Override
	public String getId() {
		return this.getRemainManaID();
	}
	public InterimAbsMng(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr, RemainType remainType,
			RequiredDay requeiredDays, UnOffsetDay unOffsetDays) {
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.requeiredDays = requeiredDays;
		this.unOffsetDays = unOffsetDays;
	}
	
	// [1] 逐次発生の休暇明細に変換する
	public AccumulationAbsenceDetail convertSeqVacationState() {
		return new AccuVacationBuilder(this.getSID(), new CompensatoryDayoffDate(false, Optional.of(this.getYmd())),
				OccurrenceDigClass.DIGESTION, this.getCreatorAtr().convertToMngData(false), this.getRemainManaID())
						.numberOccurren(new NumberConsecuVacation(
								new ManagementDataRemainUnit(this.getRequeiredDays().v()), Optional.empty()))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(this.unOffsetDays.v()),
								Optional.empty()))
						.build();
	}
	
	//[3] 未相殺数を更新する
	
}
