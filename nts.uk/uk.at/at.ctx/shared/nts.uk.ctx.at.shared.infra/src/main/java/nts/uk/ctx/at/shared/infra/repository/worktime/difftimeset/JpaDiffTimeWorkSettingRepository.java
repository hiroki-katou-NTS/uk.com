/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSetPK;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDiffTimeWorkSettingRepository.
 */

@Stateless
public class JpaDiffTimeWorkSettingRepository extends JpaRepository implements DiffTimeWorkSettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<DiffTimeWorkSetting> find(String companyId, String workTimeCode) {
		// Query
		Optional<KshmtDiffTimeWorkSet> optionalEntityTimeSet = this.queryProxy()
				.find(new KshmtDiffTimeWorkSetPK(companyId, workTimeCode), KshmtDiffTimeWorkSet.class);

		// Check exist
		if (!optionalEntityTimeSet.isPresent()) {
			return Optional.empty();
		}
		return Optional
				.ofNullable(new DiffTimeWorkSetting(new JpaDiffTimeWorkSettingGetMemento(optionalEntityTimeSet.get())));

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository#add(nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting)
	 */
	@Override
	public void add(DiffTimeWorkSetting diffTimeWorkSetting) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository#save(nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting)
	 */
	@Override
	public void save(DiffTimeWorkSetting diffTimeWorkSetting) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository#update(nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting)
	 */
	@Override
	public void update(DiffTimeWorkSetting diffTimeWorkSetting) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean remove(String companyId, String workTimeCode) {
		// TODO Auto-generated method stub
		return false;
	}

}
