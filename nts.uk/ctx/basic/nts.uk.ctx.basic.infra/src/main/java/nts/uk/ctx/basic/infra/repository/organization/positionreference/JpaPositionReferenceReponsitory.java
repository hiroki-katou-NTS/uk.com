package nts.uk.ctx.basic.infra.repository.organization.positionreference;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.organization.department.Department;
import nts.uk.ctx.basic.dom.organization.department.DepartmentCode;
import nts.uk.ctx.basic.dom.organization.position.Position;
import nts.uk.ctx.basic.dom.organization.positionhistory.PositionHistory;
import nts.uk.ctx.basic.dom.organization.positionhistory.PositionHistoryRepository;
import nts.uk.ctx.basic.dom.organization.positionreference.PositionReferenceRepository;
import nts.uk.ctx.basic.infra.entity.organization.department.CmnmtDep;
import nts.uk.ctx.basic.infra.entity.organization.department.CmnmtDepPK;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitle;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitlePK;
import nts.uk.ctx.basic.infra.entity.organization.positionhistory.CmnmtJobTitleHistory;
import nts.uk.ctx.basic.infra.entity.organization.positionhistory.CmnmtJobTitleHistoryPK;


@Stateless
public class JpaPositionReferenceReponsitory extends JpaRepository implements PositionReferenceRepository {

	
	private static final String FIND_ALL_BY_HISTORY;

	private static final String FIND_SINGLE_HISTORY;
	
	private static final String CHECK_EXIST;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTitleHistory e");
		builderString.append(" WHERE e.cmnmtJobTitleHistoryPK.companyCode = :companyCode");
		FIND_ALL_BY_HISTORY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTitleHistory e");
		builderString.append(" WHERE e.cmnmtJobTitleHistoryPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtJobTitleHistoryPK.historyID = :historyID");
		FIND_SINGLE_HISTORY = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTitleHistory e");
		builderString.append(" WHERE e.cmnmtJobTitleHistoryPK.companyCode = :companyCode");
		CHECK_EXIST = builderString.toString();
		
	
		
	}
	


		



}