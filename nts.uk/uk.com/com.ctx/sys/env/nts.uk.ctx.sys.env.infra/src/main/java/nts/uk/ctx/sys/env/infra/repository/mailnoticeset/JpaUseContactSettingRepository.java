/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailnoticeset;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSettingRepository;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.SevstUseContactSet;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.SevstUseContactSetPK;

/**
 * The Class JpaUseContactSettingRepository.
 */
@Stateless
public class JpaUseContactSettingRepository extends JpaRepository implements UseContactSettingRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSettingRepository#findByEmployeeId(java.lang.String)
	 */
	@Override
	public UseContactSetting findByEmployeeId(String employeeId,String companyId) {
		return this.queryProxy().find(new SevstUseContactSetPK(companyId, employeeId), SevstUseContactSet.class).map(e -> this.toDomain(e)).get();
	}

	/**
	 * To domain.
	 *
	 * @param e the e
	 * @return the use contact setting
	 */
	private UseContactSetting toDomain(SevstUseContactSet e) {
		return new UseContactSetting(new JpaUseContactSettingGetMemento(e));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSettingRepository#add(nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UseContactSetting, java.lang.String)
	 */
	@Override
	public void add(UseContactSetting useContactSetting, String companyId) {
		this.commandProxy().insert(this.toEntity(useContactSetting,companyId));
	}

	/**
	 * To entity.
	 *
	 * @param useContactSetting the use contact setting
	 * @param companyId the company id
	 * @return the sevst use contact set
	 */
	private SevstUseContactSet toEntity(UseContactSetting useContactSetting, String companyId) {
		SevstUseContactSet entity = new SevstUseContactSet();
		useContactSetting.saveToMemento(new JpaUseContactSettingSetMemento(entity,companyId));
		return entity;
	}

}
