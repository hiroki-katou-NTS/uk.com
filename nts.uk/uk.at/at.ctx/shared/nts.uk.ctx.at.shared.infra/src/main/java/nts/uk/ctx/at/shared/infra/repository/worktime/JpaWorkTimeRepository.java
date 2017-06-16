package nts.uk.ctx.at.shared.infra.repository.worktime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeAbName;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeName;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeNote;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeSymbol;
import nts.uk.ctx.at.shared.infra.entity.worktime.KwtmpWorkTimePK;
import nts.uk.ctx.at.shared.infra.entity.worktime.KwtmtWorkTime;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
public class JpaWorkTimeRepository extends JpaRepository implements WorkTimeRepository{

	private final String findWorkTimeByList = "SELECT a FROM KwtmtWorkTime a "
			+ "WHERE a.kwtmpWorkTimePK.companyID = :companyID "
			+ "AND a.kwtmpWorkTimePK.siftCD IN :siftCDs";
	
	@Override
	public Optional<WorkTime> findByCode(String companyID, String siftCD) {
		return this.queryProxy().find(new KwtmpWorkTimePK(companyID, siftCD), KwtmtWorkTime.class).map(x -> convertToDomainWorkTime(x));
	}

	@Override
	public List<WorkTime> findByCodeList(String companyID, List<String> siftCDs) {
		List<KwtmtWorkTime> result = this.queryProxy().query(findWorkTimeByList, KwtmtWorkTime.class)
			.setParameter("companyID", companyID).setParameter("siftCDs", siftCDs).getList();
		return 	result.stream().map(x -> convertToDomainWorkTime(x)).collect(Collectors.toList());
	}
	
	/**
	 * convert Work Time entity object to Work Time domain object
	 * @param kwtmtWorkTime Work Time entity object
	 * @return Work Time domain object
	 */
	private WorkTime convertToDomainWorkTime(KwtmtWorkTime kwtmtWorkTime){
		return new WorkTime(
			new WorkTimeCode(kwtmtWorkTime.kwtmpWorkTimePK.siftCD), 
			kwtmtWorkTime.kwtmpWorkTimePK.companyID, 
			new WorkTimeNote(kwtmtWorkTime.note), 
			new WorkTimeDivision(
				EnumAdaptor.valueOf(kwtmtWorkTime.workTimeDailyAtr, WorkTimeDailyAtr.class),
				EnumAdaptor.valueOf(kwtmtWorkTime.workTimeMethodSet, WorkTimeMethodSet.class)
			),
			EnumAdaptor.valueOf(kwtmtWorkTime.displayAtr, UseSetting.class),
			new WorkTimeDisplayName(
				new WorkTimeName(kwtmtWorkTime.workTimeName),
				new WorkTimeAbName(kwtmtWorkTime.workTimeAbName),
				new WorkTimeSymbol(kwtmtWorkTime.workTimeSymbol)
			)
		);
	}

}
