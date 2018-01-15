/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime_old;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeAbName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeNote;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSymbol;
import nts.uk.ctx.at.shared.dom.worktime_old.SiftCode;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime_old.KwtmpWorkTimePK;
import nts.uk.ctx.at.shared.infra.entity.worktime_old.KwtmtWorkTime;
/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
public class JpaWorkTimeRepository extends JpaRepository implements WorkTimeRepository {

	private final String findWorkTimeByCompanyID = "SELECT a FROM KwtmtWorkTime a "
			+ "WHERE a.kwtmpWorkTimePK.companyID = :companyID";

	private final String findWorkTimeByList = "SELECT a FROM KwtmtWorkTime a "
			+ "WHERE a.kwtmpWorkTimePK.companyID = :companyID " + "AND a.kwtmpWorkTimePK.siftCD IN :siftCDs";

	private static final String FIND_ALL = "SELECT k FROM KwtmtWorkTime k " + "LEFT JOIN KshmtWorkTimeOrder o "
			+ "ON k.kwtmpWorkTimePK.siftCD = o.kshmpWorkTimeOrderPK.workTimeCode "
			+ "WHERE k.kwtmpWorkTimePK.companyID = :companyID " + "AND k.displayAtr = 1 " // Always
																							// display.
			+ "ORDER BY o.dispOrder ASC";

	private static final String FIND_BY_CODES = "SELECT k FROM KwtmtWorkTime k " + "LEFT JOIN KshmtWorkTimeOrder o "
			+ "ON k.kwtmpWorkTimePK.siftCD = o.kshmpWorkTimeOrderPK.workTimeCode "
			+ "WHERE k.kwtmpWorkTimePK.companyID = :companyID " + "AND k.displayAtr = 1 " // Always
																							// display.
			+ "AND k.kwtmpWorkTimePK.siftCD IN :siftCDs " + "ORDER BY o.dispOrder ASC";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository#findByCompanyID(java
	 * .lang.String)
	 */
	@Override
	public List<WorkTime> findByCompanyID(String companyID) {
		return this.queryProxy().query(findWorkTimeByCompanyID, KwtmtWorkTime.class)
				.setParameter("companyID", companyID).getList(x -> convertToDomainWorkTime(x));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository#findByCode(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public Optional<WorkTime> findByCode(String companyID, String siftCD) {
		return this.queryProxy().find(new KwtmpWorkTimePK(companyID, siftCD), KwtmtWorkTime.class)
				.map(x -> convertToDomainWorkTime(x));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository#findByCodeList(java.
	 * lang.String, java.util.List)
	 */
	@Override
	public List<WorkTime> findByCodeList(String companyID, List<String> siftCDs) {
		List<WorkTime> result = new ArrayList<WorkTime>();
		int i = 0;
		while (siftCDs.size() - (i + 500) > 0) {
			List<String> subCodelist = siftCDs.subList(i, i + 500);
			List<WorkTime> subResult = this.queryProxy().query(findWorkTimeByList, KwtmtWorkTime.class)
					.setParameter("companyID", companyID).setParameter("siftCDs", subCodelist)
					.getList(x -> convertToDomainWorkTime(x));
			result.addAll(subResult);
			i += 500;
		}
		List<WorkTime> lastResult = this.queryProxy().query(findWorkTimeByList, KwtmtWorkTime.class)
				.setParameter("companyID", companyID).setParameter("siftCDs", siftCDs.subList(i, siftCDs.size()))
				.getList(x -> convertToDomainWorkTime(x));
		result.addAll(lastResult);
		return result;
	}

	/**
	 * convert Work Time entity object to Work Time domain object
	 * 
	 * @param kwtmtWorkTime
	 *            Work Time entity object
	 * @return Work Time domain object
	 */
	private WorkTime convertToDomainWorkTime(KwtmtWorkTime kwtmtWorkTime) {
		return new WorkTime(new SiftCode(kwtmtWorkTime.kwtmpWorkTimePK.siftCD), kwtmtWorkTime.kwtmpWorkTimePK.companyID,
				new WorkTimeNote(kwtmtWorkTime.note),
				new WorkTimeDivision(EnumAdaptor.valueOf(kwtmtWorkTime.workTimeDailyAtr, WorkTimeDailyAtr.class),
						EnumAdaptor.valueOf(kwtmtWorkTime.workTimeMethodSet, WorkTimeMethodSet.class)),
				EnumAdaptor.valueOf(kwtmtWorkTime.displayAtr, UseSetting.class),
				WorkTimeDisplayName.builder().workTimeName(new WorkTimeName(kwtmtWorkTime.workTimeName))
						.workTimeAbName(new WorkTimeAbName(kwtmtWorkTime.workTimeAbName))
						.workTimeSymbol(new WorkTimeSymbol(kwtmtWorkTime.workTimeSymbol)).build(),
				null,null,null,null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository#findAll(java.lang.
	 * String)
	 */
	@Override
	public List<WorkTime> findAll(String companyID) {
		return this.queryProxy().query(FIND_ALL, KwtmtWorkTime.class)
				.setParameter("companyID", companyID).getList(x -> convertToDomainWorkTime(x));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository#findByCodes(java.
	 * lang.String, java.util.List)
	 */
	@Override
	public List<WorkTime> findByCodes(String companyID, List<String> codes) {
		List<WorkTime> result = new ArrayList<WorkTime>();
		int i = 0;
		while (codes.size() - (i + 500) > 0) {
			List<String> subCodelist = codes.subList(i, i + 500);
			List<WorkTime> subResult = this.queryProxy().query(FIND_BY_CODES, KwtmtWorkTime.class)
					.setParameter("companyID", companyID).setParameter("siftCDs", subCodelist)
					.getList(x -> convertToDomainWorkTime(x));
			result.addAll(subResult);
			i += 500;
		}
		List<WorkTime> lastResult = this.queryProxy().query(FIND_BY_CODES, KwtmtWorkTime.class)
				.setParameter("companyID", companyID).setParameter("siftCDs", codes.subList(i, codes.size()))
				.getList(x -> convertToDomainWorkTime(x));
		result.addAll(lastResult);
		return result;
	}
}
