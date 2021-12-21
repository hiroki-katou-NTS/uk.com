/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import java.util.List;
//import java.util.List;
import java.util.Optional;
//import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KshmtHdnursingLeave;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * The Class JpaNursingVacationSettingGetMemento.
 */
public class JpaNursingLeaveSettingGetMemento implements NursingLeaveSettingGetMemento {

    /** The entity nursing. */
    private KshmtHdnursingLeave entityNursing;

    /**
     * Instantiates a new jpa nursing vacation setting get memento.
     *
     * @param entityNursing the entity nursing
     * @param entityWorkTypes the entity work types
     */
    public JpaNursingLeaveSettingGetMemento(KshmtHdnursingLeave entityNursing) {
        this.entityNursing = entityNursing;
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getCompanyId()
     */
    @Override
    public String getCompanyId() {
        return this.entityNursing.getKnlmtNursingLeaveSetPK().getCid();
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getManageType()
     */
    @Override
    public ManageDistinct getManageType() {
        return ManageDistinct.valueOf(this.entityNursing.getManageType());
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getNursingCategory()
     */
    @Override
    public NursingCategory getNursingCategory() {
        return NursingCategory.valueOf(this.entityNursing.getKnlmtNursingLeaveSetPK().getNursingCtr());
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getStartMonthDay()
     */
    @Override
    public MonthDay getStartMonthDay() {
    	int month = this.entityNursing.getStartMonthDay() / 100;
    	int day = this.entityNursing.getStartMonthDay() % 100;
    	return new MonthDay(month, day);
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getMaxPersonSetting()
     */
    @Override
    public List<MaxPersonSetting> getMaxPersonSetting() {
        return MaxPersonSetting.getList(new JpaMaxPersonSettingGetMemento(this.entityNursing));
    }

	@Override
	public Optional<Integer> getHdspFrameNo() {
		return Optional.ofNullable(this.entityNursing.getHdspFrameNo());
	}

	@Override
	public Optional<Integer> getAbsenceFrameNo() {
		return Optional.ofNullable(this.entityNursing.getAbsenceFrameNo());
	}

	@Override
	public TimeVacationDigestUnit getTimeVacationDigestUnit() {
		return new TimeVacationDigestUnit(
				EnumAdaptor.valueOf(this.entityNursing.getTimeManageAtr() != null ? this.entityNursing.getTimeManageAtr(): 0, ManageDistinct.class ),
				EnumAdaptor.valueOf(this.entityNursing.getDigestiveUnit() != null ? this.entityNursing.getDigestiveUnit() : 0, TimeDigestiveUnit.class));
	}


    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingGetMemento#getWorkTypeCodes()
     */
//    @Override
//    public List<String> getWorkTypeCodes() {
//        return this.entityNursing.getListWorkType().stream()
//                .filter(entity -> entity.getKnlmtNursingWorkTypePK().getNursingCtr() == this.entityNursing
//                        .getKnlmtNursingLeaveSetPK().getNursingCtr())
//                .map(entity -> entity.getWorkTypeCode())
//                .collect(Collectors.toList());
//    }

}
