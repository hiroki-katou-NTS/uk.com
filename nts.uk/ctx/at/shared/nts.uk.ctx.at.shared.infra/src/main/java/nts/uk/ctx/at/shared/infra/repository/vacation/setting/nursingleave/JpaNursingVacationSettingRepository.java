/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingVacationSettingRepository;

@Stateless
public class JpaNursingVacationSettingRepository extends JpaRepository implements NursingVacationSettingRepository {

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingRepository#add(nts.uk.ctx.at.shared.dom.vacation.
     * setting.nursingleave.NursingVacationSetting)
     */
    @Override
    public void add(List<NursingVacationSetting> settings) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingRepository#update(nts.uk.ctx.at.shared.dom.vacation
     * .setting.nursingleave.NursingVacationSetting)
     */
    @Override
    public void update(List<NursingVacationSetting> settings) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingRepository#findByCompanyId(java.lang.String)
     */
    @Override
    public List<NursingVacationSetting> findByCompanyId(String companyId) {
        // TODO Auto-generated method stub
        return null;
    }

}
