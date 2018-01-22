package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkStampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtent;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeCommonSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFixedWorkRestSetGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaWorkTimezoneCommonSetGetMemento;

/**
 * The Class JpaDiffTimeWorkSettingGetMemento.
 */
public class JpaDiffTimeWorkSettingGetMemento implements DiffTimeWorkSettingGetMemento {

	private KshmtDiffTimeWorkSet entity;

	public JpaDiffTimeWorkSettingGetMemento(KshmtDiffTimeWorkSet kshmtDiffTimeWorkSet) {
		this.entity = kshmtDiffTimeWorkSet;

	}

	@Override
	public String getCompanyId() {
		return this.entity.getKshmtDiffTimeWorkSetPK().getCid();
	}

	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.entity.getKshmtDiffTimeWorkSetPK().getWorktimeCd());
	}

	@Override
	public FixedWorkRestSet getRestSet() {
		return new FixedWorkRestSet(new JpaFixedWorkRestSetGetMemento<KshmtDiffTimeWorkSet>(this.entity));
	}

	@Override
	public DiffTimeDayOffWorkTimezone getDayoffWorkTimezone() {
		return new DiffTimeDayOffWorkTimezone(new DiffTimeDayOffWorkTimezoneGetMemento(this.entity));
	}

	@Override
	public WorkTimezoneCommonSet getCommonSet() {
		KshmtWorktimeCommonSet diffTimeCommonSet = this.entity.getKshmtWorktimeCommonSet();
		if (diffTimeCommonSet == null) {
			return null;
		}
		return new WorkTimezoneCommonSet(new JpaWorkTimezoneCommonSetGetMemento(diffTimeCommonSet));
	}

	@Override
	public boolean isIsUseHalfDayShift() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseHalfDay());
	}

	@Override
	public EmTimezoneChangeExtent getChangeExtent() {
		return new EmTimezoneChangeExtent(new JpaEmTimezoneChangeExtentGetMemento(this.entity));
	}

	@Override
	public List<DiffTimeHalfDayWorkTimezone> getHalfDayWorkTimezones() {
		List<DiffTimeHalfDayWorkTimezone> lstReturn = new ArrayList<>();

		for (AmPmAtr type : AmPmAtr.values()) {
			lstReturn.add(new DiffTimeHalfDayWorkTimezone(new JpaDiffTimeHalfDayGetMemento(this.entity, type)));
		}
		return lstReturn;
	}

	@Override
	public DiffTimeWorkStampReflectTimezone getStampReflectTimezone() {
		return new DiffTimeWorkStampReflectTimezone(new JpaDiffTimeStampReflectGetMemento(this.entity));
	}

	@Override
	public LegalOTSetting getOvertimeSetting() {
		return LegalOTSetting.valueOf(this.entity.getOtSet());
	}

}
