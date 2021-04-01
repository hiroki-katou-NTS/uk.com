package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import lombok.Data;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseRemainingNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseStartdateDaysInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseStartdateInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;

import java.util.Optional;


/**
 * 起算日からの休暇情報
 * @author yuri_tamakoshi
 */
@Data
public class ChildCareNurseStartdateDaysInfoExport {
	/** 子の看護休暇情報（本年）*/
	private ChildCareNurseStartdateInfoExport thisYear;
	/** 子の看護休暇情報（翌年）*/
	private Optional<ChildCareNurseStartdateInfoExport> nextYear;

	/**
	 * コンストラクタ　ChildCareNurseStartdateDaysInfoExport
	 */
	public ChildCareNurseStartdateDaysInfoExport(){
		this.thisYear = new ChildCareNurseStartdateInfoExport();
		this.nextYear = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param thisYear 子の看護休暇情報（本年）
	 * @param nextYear 子の看護休暇情報（翌年）
	 * @return 起算日からの休暇情報
	 */
	public static ChildCareNurseStartdateDaysInfoExport of(
			ChildCareNurseStartdateInfoExport thisYear,
			Optional <ChildCareNurseStartdateInfoExport> nextYear){

		ChildCareNurseStartdateDaysInfoExport export = new ChildCareNurseStartdateDaysInfoExport();
		export.thisYear = thisYear;
		export.nextYear = nextYear;
		return export;
	}

	public ChildCareNurseStartdateDaysInfo toDomain() {
		return ChildCareNurseStartdateDaysInfo.of(
				ChildCareNurseStartdateInfo.of(
						ChildCareNurseUsedNumber.of(
								new DayNumberOfUse(thisYear.getUsedDays().getUsedDays()),
								thisYear.getUsedDays().getUsedTime().map(c->new TimeOfUse(c))),
						ChildCareNurseRemainingNumber.of(
								new DayNumberOfUse(thisYear.getRemainingNumber().getDays()),
								thisYear.getRemainingNumber().getTime().map(c->new TimeOfUse(c))),
						new ChildCareNurseUpperLimit(thisYear.getLimitDays())
				),

				nextYear.map(mapper -> ChildCareNurseStartdateInfo.of(
						ChildCareNurseUsedNumber.of(
								new DayNumberOfUse(mapper.getUsedDays().getUsedDays()),
								mapper.getUsedDays().getUsedTime().map(c->new TimeOfUse(c))),
						ChildCareNurseRemainingNumber.of(
								new DayNumberOfUse(mapper.getRemainingNumber().getDays()),
								mapper.getRemainingNumber().getTime().map(c->new TimeOfUse(c))),
						new ChildCareNurseUpperLimit(mapper.getLimitDays())
				))
		);
	}
}
