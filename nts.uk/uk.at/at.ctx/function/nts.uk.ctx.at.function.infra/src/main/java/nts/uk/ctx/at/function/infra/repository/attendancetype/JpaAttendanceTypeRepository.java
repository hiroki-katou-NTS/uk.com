package nts.uk.ctx.at.function.infra.repository.attendancetype;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceType;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.infra.entity.attendancetype.KmnmtAttendanceType;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAttendanceTypeRepository extends JpaRepository implements AttendanceTypeRepository{
	
	private static final String SEL_ITEM_BY_TYPE = "SELECT a "
			+ "FROM KmnmtAttendanceType a "
			+ "WHERE a.kmnmtAttendanceTypePK.companyId = :companyId "
			+ "AND a.kmnmtAttendanceTypePK.screenUseAtr = :screenUseAtr";
	
	private static final String SEL_ITEM_BY_TYPE_AND_ATR = SEL_ITEM_BY_TYPE
			+ " AND a.kmnmtAttendanceTypePK.attendanctType = :attendanctType";
	
	@Override
	public List<AttendanceType> getItemByScreenUseAtr(String companyID, int screenUseAtr) {
		return this.queryProxy()
				.query(SEL_ITEM_BY_TYPE, KmnmtAttendanceType.class)
				.setParameter("companyId", companyID)
				.setParameter("screenUseAtr", screenUseAtr)
			.getList().stream().map(x -> 
				AttendanceType.createSimpleFromJavaType(
						x.kmnmtAttendanceTypePK.companyId, 
						x.kmnmtAttendanceTypePK.attendanceItemId,
						x.kmnmtAttendanceTypePK.screenUseAtr,
						x.kmnmtAttendanceTypePK.attendanctType))
			.collect(Collectors.toList());
	}

	@Override
	public List<AttendanceType> getItemByAtrandType(String companyId, int screenUseAtr, int attendanceItemType) {
		return this.queryProxy()
				.query(SEL_ITEM_BY_TYPE_AND_ATR, KmnmtAttendanceType.class)
				.setParameter("companyId", companyId)
				.setParameter("screenUseAtr", screenUseAtr)
				.setParameter("attendanctType", attendanceItemType)
			.getList().stream().map(x -> 
				AttendanceType.createSimpleFromJavaType(
						x.kmnmtAttendanceTypePK.companyId, 
						x.kmnmtAttendanceTypePK.attendanceItemId,
						x.kmnmtAttendanceTypePK.screenUseAtr,
						x.kmnmtAttendanceTypePK.attendanctType))
			.collect(Collectors.toList());
	}

}
