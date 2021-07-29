package nts.uk.ctx.at.shared.infra.repository.employeeworkway.medicalworkstyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.infra.entity.employeeworkway.medicalworkstyle.KscmtMedicalWorkStyle;
import nts.uk.ctx.at.shared.infra.entity.employeeworkway.medicalworkstyle.KscmtMedicalWorkStylePk;

/**
 * 
 * @author HieuLt
 *
 */
@Stateless

public class JpaEmpMedicalWorkStyleHistoryRepository extends JpaRepository implements EmpMedicalWorkStyleHistoryRepository  {
	 private static final String GET_BY_KEY = " SELECT c FROM KshmtMedicalWorkStyle c WHERE c.pk.sid = :empId AND c.pk.histId = :historyId " ;
	 
	 private static final String GET_BY_HISTID = " SELECT c FROM KshmtMedicalWorkStyle c WHERE c.pk.histId = :historyId ";
	 
	 private static final String GET_BY_HISTIDLIST = " SELECT c FROM KshmtMedicalWorkStyle c WHERE c.pk.histId IN :historyIdList ";	
	 
	 private static final String GET_BY_SID_DESC = " SELECT c FROM KshmtMedicalWorkStyle c WHERE c.companyId = :cid AND c.pk.sid = :empId ORDER BY c.startDate DESC";

	 private static final String GET_BY_SID = " SELECT c FROM KshmtMedicalWorkStyle c WHERE c.companyId = :cid AND c.pk.sid = :empId ORDER BY c.startDate";
	
	 private static final String GET_BY_LSTHISID = " SELECT c FROM KshmtMedicalWorkStyle c WHERE c.pk.sid = :empId AND c.pk.histId IN :listHisId ";
	 
	 private static final String GET_BY_EMPID_AND_DATE = " SELECT c FROM KshmtMedicalWorkStyle c WHERE c.pk.sid = :empId "
			                                             + " AND c.endDate >= :referenceDate "
			                                             + " AND c.startDate <= :referenceDate ";
	 
	 private static final String GET_BY_EMPIDS_AND_DATE = " SELECT c FROM KshmtMedicalWorkStyle c WHERE c.pk.sid IN :listEmpId "
			 											 + " AND c.endDate >= :referenceDate "
                                                         + " AND c.startDate <= :referenceDate "; 
	private static final String GET_BY_SIDS_AND_CID = " SELECT c FROM KshmtMedicalWorkStyle c WHERE c.pk.sid IN :listEmpId "
														 + " AND c.companyId = :cid "
														 + " AND c.endDate >= :referenceDate "
														 + " AND c.startDate <= :referenceDate "; 
	@Override
	public Optional<EmpMedicalWorkStyleHistoryItem> get(String empID, GeneralDate referenceDate) {
		Optional<EmpMedicalWorkStyleHistoryItem> data = this.queryProxy().query(GET_BY_EMPID_AND_DATE, KscmtMedicalWorkStyle.class)
														.setParameter("empId", empID)
														.setParameter("referenceDate", referenceDate)
														.getSingle(c ->c.toDomainHisItem());			
		return data;
	}

	@Override
	public List<EmpMedicalWorkStyleHistoryItem> get(List<String> listEmpId, GeneralDate referenceDate) {
		if(listEmpId.isEmpty())
			return new ArrayList<EmpMedicalWorkStyleHistoryItem>();
		List<EmpMedicalWorkStyleHistoryItem> data = this.queryProxy().query(GET_BY_EMPIDS_AND_DATE, KscmtMedicalWorkStyle.class)
																.setParameter("listEmpId", listEmpId)
																.setParameter("referenceDate", referenceDate)
																.getList( c -> c.toDomainHisItem()); 
		return data;
	}

	@Override
	public void insert(EmpMedicalWorkStyleHistory empMedicalWorkStyleHistory,
			EmpMedicalWorkStyleHistoryItem empMedicalWorkStyleHistoryItem) {
		this.commandProxy().insert(KscmtMedicalWorkStyle.toEntityMedicalWorkStyle(empMedicalWorkStyleHistory, empMedicalWorkStyleHistoryItem));
		
	}

	@Override
	public void update(EmpMedicalWorkStyleHistory empMedicalWorkStyleHistory) {
		List <String> lstHistId = empMedicalWorkStyleHistory.getListDateHistoryItem()
				.stream().map( c -> c.identifier()).collect(Collectors.toList());

		List<KshmtMedicalWorkStyle> oldData = this.queryProxy().query(GET_BY_LSTHISID, KshmtMedicalWorkStyle.class)
																  .setParameter("empId", empMedicalWorkStyleHistory.getEmpID())
																  .setParameter("listHisId", lstHistId)
																  .getList();
		
		
		List<KshmtMedicalWorkStyle> newData = empMedicalWorkStyleHistory.getListDateHistoryItem()
				.stream().map(mapper-> new KshmtMedicalWorkStyle(
						new KshmtMedicalWorkStylePk(empMedicalWorkStyleHistory.getEmpID(), mapper.identifier()),
						mapper.span().start(),
						mapper.span().end())).collect(Collectors.toList());
		
		oldData.forEach(x->{
			Optional<KshmtMedicalWorkStyle> newDataOldId = newData.stream().filter(el -> el.getPk().getHistId().equals(x.getPk().getHistId())).findFirst();
			if (newDataOldId.isPresent()) {
				KshmtMedicalWorkStyle y = newDataOldId.get();
				x.startDate = y.startDate;
			    x.endDate = y.endDate ;
			}
		});
		
		this.commandProxy().updateAll(oldData);
		
	}

	@Override
	public void update(EmpMedicalWorkStyleHistoryItem hisItem) {

		 Optional<KshmtMedicalWorkStyle> entity = this.queryProxy().find(new KshmtMedicalWorkStylePk(hisItem.getEmpID(), hisItem.getHistoryID()), KshmtMedicalWorkStyle.class);
		if(entity.isPresent()){
			//TODO
			/*entity.get().setOnlyNightShift(hisItem.isNightShiftFullTime());
			entity.get().setMedicalCareWorkStyle(hisItem.getOptMedicalWorkFormInfor().isPresent() ? hisItem.getOptMedicalWorkFormInfor().get().getMedicalCareWorkStyle().value : hisItem.getOpyNursingWorkFormInfor().get().getMedicalCareWorkStyle().value );
			entity.get().setNurseLicenseCd(hisItem.getOptMedicalWorkFormInfor().isPresent() ? hisItem.getOptMedicalWorkFormInfor().get().getNurseClassifiCode().v() : null );
			entity.get().setMedicalConcurrentPost(hisItem.getOptMedicalWorkFormInfor().isPresent() ? hisItem.getOptMedicalWorkFormInfor().get().isOtherDepartmentConcurrently() : null );
			entity.get().setCareConcurrentPost(hisItem.getOpyNursingWorkFormInfor().isPresent() ? hisItem.getOpyNursingWorkFormInfor().get().isAsNursingCare() : null);
			entity.get().setCareRptNote(hisItem.getOpyNursingWorkFormInfor().isPresent() ? hisItem.getOpyNursingWorkFormInfor().get().getFulltimeRemarks().v() :  null);	
			entity.get().setCareNightRptNote(hisItem.getOpyNursingWorkFormInfor().isPresent() ? hisItem.getOpyNursingWorkFormInfor().get().getNightShiftRemarks().v() : null);*/
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void delete(String empId, String historyId) {
		this.commandProxy().remove(KshmtMedicalWorkStyle.class,new KshmtMedicalWorkStylePk(empId ,historyId )  );
	}

	@Override
	public void insertAll(List<EmpMedicalWorkStyleHistory> empMedicalWorkStyleHistoryList,
			List<EmpMedicalWorkFormHisItem> empMedicalWorkFormHisItemList) {
		empMedicalWorkStyleHistoryList.forEach(emwHist -> {
			Optional<EmpMedicalWorkFormHisItem> emwHistItemOp = empMedicalWorkFormHisItemList.stream().filter(emwHistItem -> emwHistItem.getEmpID().equals(emwHist.getEmpID())).findFirst();
			if (emwHistItemOp.isPresent()) {
				this.insert(emwHist, emwHistItemOp.get());
			}		
		});
	}

	@Override
	public void updateAllHist(List<EmpMedicalWorkStyleHistory> empMedicalWorkStyleHistoryList) {
		empMedicalWorkStyleHistoryList.stream().forEach(emwHist -> {
			this.update(emwHist);
		});
	}

	@Override
	public void updateAllItem(List<EmpMedicalWorkFormHisItem> empMedicalWorkFormHisItemList) {
		empMedicalWorkFormHisItemList.stream().forEach(emwHistItem -> {
			this.update(emwHistItem);
		});
	}

	@Override
	public Optional<EmpMedicalWorkStyleHistory> getHistByHistId(String histId) {
		Optional<EmpMedicalWorkStyleHistory> domainHis = this.queryProxy().query(GET_BY_HISTID, KshmtMedicalWorkStyle.class)
				.setParameter("historyId", histId)
				.getSingle(c -> c.toDomainHis()); 
		return domainHis;
	}

	@Override
	public Optional<EmpMedicalWorkStyleHistory> getHist(String empID, GeneralDate referenceDate) {
		Optional<EmpMedicalWorkStyleHistory> domainHis = this.queryProxy().query(GET_BY_EMPID_AND_DATE, KshmtMedicalWorkStyle.class)
				.setParameter("empId", empID)
				.setParameter("referenceDate", referenceDate)
				.getSingle(c ->c.toDomainHis());			
		return domainHis;
	}

	@Override
	public Optional<EmpMedicalWorkFormHisItem> getItemByHistId(String histId) {
		Optional<EmpMedicalWorkFormHisItem> domainHisItem = this.queryProxy().query(GET_BY_HISTID, KshmtMedicalWorkStyle.class)
				.setParameter("historyId", histId)
				.getSingle(c -> c.toDomainHisItem()); 
		return domainHisItem;
	}

	@Override
	public Optional<EmpMedicalWorkStyleHistory> getHistBySidDesc(String cid, String sid) {
		Optional<EmpMedicalWorkStyleHistory> domainHis = this.queryProxy().query(GET_BY_SID_DESC, KshmtMedicalWorkStyle.class)
				.setParameter("cid", cid)
				.setParameter("empId", sid)
				.getSingle(c ->c.toDomainHis());			
		return domainHis;
	}
	
	@Override
	public Optional<EmpMedicalWorkStyleHistory> getHistBySid(String cid, String sid) {
		Optional<EmpMedicalWorkStyleHistory> domainHis = this.queryProxy().query(GET_BY_SID, KshmtMedicalWorkStyle.class)
				.setParameter("cid", cid)
				.setParameter("empId", sid)
				.getSingle(c ->c.toDomainHis());			
		return domainHis;
	}

	@Override
	public List<EmpMedicalWorkStyleHistory> getHistBySidsAndCid(List<String> listEmpId, GeneralDate referenceDate,
			String cid) {
		List<EmpMedicalWorkStyleHistory> domainHisList = this.queryProxy().query(GET_BY_SIDS_AND_CID, KshmtMedicalWorkStyle.class)
				.setParameter("listEmpId", listEmpId)
				.setParameter("referenceDate", referenceDate)
				.setParameter("cid", cid)
				.getList( c -> c.toDomainHis()); 
		return domainHisList;
	}
	
	@Override
	public List<EmpMedicalWorkFormHisItem> getItemByHistIdList(List<String> histIdList) {
		List<EmpMedicalWorkFormHisItem> domainHisItemList = this.queryProxy().query(GET_BY_HISTIDLIST, KshmtMedicalWorkStyle.class)
				.setParameter("historyIdList", histIdList)
				.getList( c -> c.toDomainHisItem()); 
		return domainHisItemList;
	}

}
