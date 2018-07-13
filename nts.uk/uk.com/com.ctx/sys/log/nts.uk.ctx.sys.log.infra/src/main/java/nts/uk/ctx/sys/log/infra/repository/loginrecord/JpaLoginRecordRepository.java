/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.log.infra.repository.loginrecord;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.log.dom.loginrecord.LoginRecord;
import nts.uk.ctx.sys.log.dom.loginrecord.LoginRecordRepository;
import nts.uk.ctx.sys.log.infra.entity.loginrecord.SrcdtLoginRecord;


/**
 * The Class JpaLoginRecordRepository.
 */
@Stateless
public class JpaLoginRecordRepository extends JpaRepository implements LoginRecordRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.log.dom.loginrecord.LoginRecordRepository#add(nts.uk.ctx.sys.log.dom.loginrecord.LoginRecord)
	 */
	@Override
	public void add(LoginRecord loginRecord) {
		SrcdtLoginRecord entity = new SrcdtLoginRecord();
		loginRecord.saveToMemento(new JpaLoginRecordSetMemento(entity));
		this.commandProxy().insert(entity);
	}

}
