package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.medicalworkstyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkFormHisItem;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkStyleHistory;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.medicalworkstyle.KscmtMedicalWorkStyle;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.medicalworkstyle.KscmtMedicalWorkStylePk;

/**
 * 
 * @author HieuLt
 *
 */
@Stateless

public class JpaEmpMedicalWorkStyleHistoryRepository extends JpaRepository implements EmpMedicalWorkStyleHistoryRepository  {
	 private static final String GET_BY_KEY = " SELECT c FROM KscmtMedicalWorkStyle c WHERE c.pk.sid = empId AND c.pk.histId = historyId " ;			
	
	 private static final String GET_BY_LSTHISID = " SELECT c FROM KscmtMedicalWorkStyle c WHERE c.pk.sid = empId AND c.pk.histId IN : listHisId ";
	 
	 private static final String GET_BY_EMPID_AND_DATE = " SELECT c FROM KscmtMedicalWorkStyle c WHERE c.pk.sid = :empId "
			                                             + " AND c.endDate >= :referenceDate "
			                                             + " AND c.startDate <= :referenceDate ";
	 
	 private static final String GET_BY_EMPIDS_AND_DATE = " SELECT c FROM KscmtMedicalWorkStyle c WHERE c.pk.sid IN :listEmpId "
			 											 + " AND c.endDate >= :referenceDate "
                                                         + " AND c.startDate <= :referenceDate "; 
	@Override
	public Optional<EmpMedicalWorkFormHisItem> get(String empID, GeneralDate referenceDate) {
		Optional<EmpMedicalWorkFormHisItem> data = this.queryProxy().query(GET_BY_EMPID_AND_DATE, KscmtMedicalWorkStyle.class)
														.setParameter("empId", empID)
														.setParameter("referenceDate", referenceDate)
														.getSingle(c ->c.toDomainHisItem());			
		return data;
	}

	@Override
	public List<EmpMedicalWorkFormHisItem> get(List<String> listEmpId, GeneralDate referenceDate) {
		if(listEmpId.isEmpty())
			return new ArrayList<EmpMedicalWorkFormHisItem>();
		List<EmpMedicalWorkFormHisItem> data = this.queryProxy().query(GET_BY_EMPIDS_AND_DATE, KscmtMedicalWorkStyle.class)
																.setParameter("listEmpId", listEmpId)
																.setParameter("referenceDate", referenceDate)
																.getList( c -> c.toDomainHisItem()); 
		return data;
	}

	@Override
	public void insert(EmpMedicalWorkStyleHistory empMedicalWorkStyleHistory,
			EmpMedicalWorkFormHisItem empMedicalWorkFormHisItem) {
		this.commandProxy().insert(KscmtMedicalWorkStyle.toEntityMedicalWorkStyle(empMedicalWorkStyleHistory, empMedicalWorkFormHisItem));
		
	}

	@Override
	public void update(EmpMedicalWorkStyleHistory empMedicalWorkStyleHistory) {
		List <String> lstHistId = empMedicalWorkStyleHistory.getListDateHistoryItem()
				.stream().map( c -> c.identifier()).collect(Collectors.toList());

		List<KscmtMedicalWorkStyle> oldData = this.queryProxy().query(GET_BY_LSTHISID, KscmtMedicalWorkStyle.class)
																  .setParameter("empId", empMedicalWorkStyleHistory.getEmpID())
																  .setParameter("listHisId", lstHistId)
																  .getList();
		
		
		List<KscmtMedicalWorkStyle> newData = empMedicalWorkStyleHistory.getListDateHistoryItem()
				.stream().map(mapper-> new KscmtMedicalWorkStyle(
						new KscmtMedicalWorkStylePk(empMedicalWorkStyleHistory.getEmpID(), mapper.identifier()),
						mapper.span().start(),
						mapper.span().end())).collect(Collectors.toList());
		
		oldData.forEach(x->{
			newData.forEach(y->{
				x.startDate = y.startDate;
			    x.endDate = y.endDate ;
			    x.isOnlyNightShift = y.isOnlyNightShift;
			    x.medicalCareWorkStyle = y.medicalCareWorkStyle;
			    x.nurseLicenseCd = y.nurseLicenseCd;
			    x.medicalConcurrentPost = y.medicalConcurrentPost;
			    x.careConcurrentPost = y.careConcurrentPost;
			    x.careRptNote = y.careRptNote;
			    x.careNightRptNote = y.careNightRptNote;
			});
		});
		
		this.commandProxy().updateAll(oldData);
		
	}

	@Override
	public void update(EmpMedicalWorkFormHisItem hisItem) {

		 Optional<KscmtMedicalWorkStyle> entity = this.queryProxy().find(new KscmtMedicalWorkStylePk(hisItem.getEmpID(), hisItem.getHistoryID()), KscmtMedicalWorkStyle.class);
		if(entity.isPresent()){
			entity.get().setOnlyNightShift(hisItem.isNightShiftFullTime());
			entity.get().setMedicalCareWorkStyle(hisItem.getOptMedicalWorkFormInfor().isPresent() ? hisItem.getOptMedicalWorkFormInfor().get().getMedicalCareWorkStyle().value : hisItem.getOpyNursingWorkFormInfor().get().getMedicalCareWorkStyle().value );
			entity.get().setNurseLicenseCd(hisItem.getOptMedicalWorkFormInfor().isPresent() ? hisItem.getOptMedicalWorkFormInfor().get().getNurseClassifiCode().v() : null );
			entity.get().setMedicalConcurrentPost(hisItem.getOptMedicalWorkFormInfor().isPresent() ? hisItem.getOptMedicalWorkFormInfor().get().isOtherDepartmentConcurrently() : null );
			entity.get().setCareConcurrentPost(hisItem.getOpyNursingWorkFormInfor().isPresent() ? hisItem.getOpyNursingWorkFormInfor().get().isAsNursingCare() : null);
			entity.get().setCareRptNote(hisItem.getOpyNursingWorkFormInfor().isPresent() ? hisItem.getOpyNursingWorkFormInfor().get().getFulltimeRemarks().v() :  null);	
			entity.get().setCareNightRptNote(hisItem.getOpyNursingWorkFormInfor().isPresent() ? hisItem.getOpyNursingWorkFormInfor().get().getNightShiftRemarks().v() : null);
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void delete(String empId, String historyId) {
		this.commandProxy().remove(KscmtMedicalWorkStyle.class,new KscmtMedicalWorkStylePk(empId ,historyId )  );
	}

}
