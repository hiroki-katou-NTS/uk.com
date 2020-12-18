/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

//import java.util.ArrayList;
//import java.util.List;
import java.util.Optional;

//import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.TimeCareNursingSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingLeaveSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingLeaveSetPK;
//import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingWorkType;
//import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingWorkTypePK;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * The Class JpaNursingVacationSettingSetMemento.
 */
public class JpaNursingLeaveSettingSetMemento implements NursingLeaveSettingSetMemento {

    /** The entity nursing. */
    private KnlmtNursingLeaveSet entityNursing;

    /**
     * Instantiates a new jpa nursing leave setting set memento.
     *
     * @param entityNursing the entity nursing
     */
    public JpaNursingLeaveSettingSetMemento(KnlmtNursingLeaveSet entityNursing) {
        // check exist primary key
        if (entityNursing.getKnlmtNursingLeaveSetPK() == null) {
            entityNursing.setKnlmtNursingLeaveSetPK(new KnlmtNursingLeaveSetPK());
        }
        this.entityNursing = entityNursing;
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.entityNursing.getKnlmtNursingLeaveSetPK().setCid(companyId);
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setManageType(nts.uk.ctx.at.shared.dom.
     * vacation.setting.ManageDistinct)
     */
    @Override
    public void setManageType(ManageDistinct manageType) {
        this.entityNursing.setManageType(manageType.value);
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setNursingCategory(nts.uk.ctx.at.shared.
     * dom.vacation.setting.nursingleave.NursingCategory)
     */
    @Override
    public void setNursingCategory(NursingCategory nursingCategory) {
        this.entityNursing.getKnlmtNursingLeaveSetPK().setNursingCtr(nursingCategory.value);
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setStartMonthDay(java.lang.Integer)
     */
    @Override
    public void setStartMonthDay(Integer startMonthDay) {
//    	int monthday = startMonthDay.getMonth() * 100 + startMonthDay.getDay();
//    	this.entityNursing.setStartMonthDay(monthday);
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setMaxPersonSetting(nts.uk.ctx.at.shared
     * .dom.vacation.setting.nursingleave.MaxPersonSetting)
     */
    @Override
    public void setMaxPersonSetting(MaxPersonSetting maxPersonSetting) {
        JpaMaxPersonSettingSetMemento memento = new JpaMaxPersonSettingSetMemento(this.entityNursing);
        maxPersonSetting.saveToMemento(memento);
    }

	@Override
	public void setHdspFrameNo(Optional<Integer> hdspFrameNo) {
		if (hdspFrameNo.isPresent())
			this.entityNursing.setHdspFrameNo(hdspFrameNo.get());
		else
			this.entityNursing.setHdspFrameNo(null);
	}

	@Override
	public void setAbsenceFrameNo(Optional<Integer> absenceFrameNo) {
		if (absenceFrameNo.isPresent())
			this.entityNursing.setAbsenceFrameNo(absenceFrameNo.get());
		else
			this.entityNursing.setAbsenceFrameNo(null);
	}

	@Override
	public void setTimeCareNursingSet(TimeCareNursingSet timeCareNursingSet) {
		this.entityNursing.setDigestiveUnit(timeCareNursingSet.getTimeDigestiveUnit().value);
		this.entityNursing.setTimeManageAtr(timeCareNursingSet.getManageDistinct().value);
		
	}


    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setWorkTypeCodes(java.util.List)
     */
//    @Override
//    public void setWorkTypeCodes(List<String> workTypeCodes) {
//        List<KnlmtNursingWorkType> listWorkType = this.entityNursing.getListWorkType();
//        if (CollectionUtil.isEmpty(listWorkType)) {
//            listWorkType = new ArrayList<>();
//        }
//        List<KnlmtNursingWorkType> newListWorkType = new ArrayList<>();
//        for (int i = 0; i < workTypeCodes.size(); i++) {
//            String workTypeCode = workTypeCodes.get(i);
//            int nursingCtr = this.entityNursing.getKnlmtNursingLeaveSetPK().getNursingCtr();
//            int orderNumber = i;
//
//            KnlmtNursingWorkTypePK pk = new KnlmtNursingWorkTypePK();
//            pk.setCid(this.entityNursing.getKnlmtNursingLeaveSetPK().getCid());
//            pk.setNursingCtr(nursingCtr);
//            pk.setOrderNumber(orderNumber);
//
//            KnlmtNursingWorkType entityWorkType = listWorkType.stream()
//                    .filter(entity -> entity.getKnlmtNursingWorkTypePK().getNursingCtr() == nursingCtr
//                            && entity.getKnlmtNursingWorkTypePK().getOrderNumber() == orderNumber)
//                    .findFirst()
//                    .orElse(new KnlmtNursingWorkType(pk));
//
//            entityWorkType.setWorkTypeCode(workTypeCode);
//            newListWorkType.add(entityWorkType);
//        }
//        this.entityNursing.setListWorkType(newListWorkType);
//    }
}
