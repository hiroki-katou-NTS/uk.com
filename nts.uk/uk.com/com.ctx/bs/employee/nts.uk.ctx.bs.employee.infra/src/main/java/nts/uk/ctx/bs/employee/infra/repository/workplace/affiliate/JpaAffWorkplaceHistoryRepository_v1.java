/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository_v1;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory_ver1;
import nts.uk.ctx.bs.employee.infra.entity.workplace.affiliate.BsymtAffiWorkplaceHist;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaAffWorkplaceHistoryRepository.
 */
@Stateless
public class JpaAffWorkplaceHistoryRepository_v1 extends JpaRepository implements AffWorkplaceHistoryRepository_v1 {
	private final String QUERY_GET_AFFWORKPLACEHIST_BYSID = "SELECT aw FROM BsymtAffiWorkplaceHist aw "
			+ "WHERE aw.sid = :sid ORDER BY aw.strDate";
	private static final String SELECT_BY_EMPID_STANDDATE = "SELECT aw FROM BsymtAffiWorkplaceHist aw"
			+ " WHERE aw.sid = :employeeId AND aw.strDate <= :standDate <= aw.endDate";
	
	private static final String SELECT_BY_HISTID = "SELECT aw FROM BsymtAffiWorkplaceHist aw"
			+ " WHERE aw.hisId = :histId";
	
	/**
	 * Convert from domain to entity
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private BsymtAffiWorkplaceHist toEntity(String cid, String employeeID, DateHistoryItem item){
		return new BsymtAffiWorkplaceHist(cid,item.identifier(),employeeID,item.start(),item.end());
	}
	
	/**
	 * Update entity from domain
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private void updateEntity(DateHistoryItem item,BsymtAffiWorkplaceHist entity){	
		entity.setStrDate(item.start());
		entity.setEndDate(item.end());
	}
	/**
	 * Convert from entity to domain
	 * @param entity
	 * @return
	 */
	private AffWorkplaceHistory_ver1 toDomainTemp(List<BsymtAffiWorkplaceHist> listHist){
		AffWorkplaceHistory_ver1 domain = new AffWorkplaceHistory_ver1(listHist.get(0).getCid(), listHist.get(0).getSid(), new ArrayList<DateHistoryItem>());
		for (BsymtAffiWorkplaceHist item : listHist){
			DateHistoryItem dateItem = new DateHistoryItem(item.getHisId(), new DatePeriod(item.getStrDate(), item.getEndDate()));
			domain.getHistoryItems().add(dateItem);
		}
		return domain;
	}
	@Override
	public Optional<AffWorkplaceHistory_ver1> getAffWorkplaceHistByEmployeeId(String employeeId) {
		List<BsymtAffiWorkplaceHist> listHist = this.queryProxy().query(QUERY_GET_AFFWORKPLACEHIST_BYSID,BsymtAffiWorkplaceHist.class)
				.setParameter("sid", employeeId).getList();
		if (listHist != null && !listHist.isEmpty()){
			return Optional.of(toDomainTemp(listHist));
		}
		return Optional.empty();
	}

	@Override
	public void add(String cid, String sid, DateHistoryItem item) {
		this.commandProxy().insert(toEntity(cid, sid,item));
	}

	@Override
	public void delete(String histId) {
		
		Optional<BsymtAffiWorkplaceHist> histItem = this.queryProxy().find(histId, BsymtAffiWorkplaceHist.class);
		if (!histItem.isPresent()){
			throw new RuntimeException("invalid BsymtAffiWorkplaceHist");
		}
		this.commandProxy().remove(BsymtAffiWorkplaceHist.class, histId);
	}

	@Override
	public void update(DateHistoryItem item) {
		Optional<BsymtAffiWorkplaceHist> histItem = this.queryProxy().find(item.identifier(), BsymtAffiWorkplaceHist.class);
		if (!histItem.isPresent()){
			throw new RuntimeException("invalid BsymtAffiWorkplaceHist");
		}
		updateEntity(item, histItem.get());
		this.commandProxy().update(histItem.get());
	}
	@Override
	public Optional<AffWorkplaceHistory_ver1> getByEmpIdAndStandDate(String employeeId, GeneralDate standDate) {
		List<BsymtAffiWorkplaceHist> listHist = this.queryProxy().query(SELECT_BY_EMPID_STANDDATE,BsymtAffiWorkplaceHist.class)
				.setParameter("employeeId", employeeId)
				.setParameter("standDate", standDate).getList();
		if (!listHist.isEmpty()){
			return Optional.of(toDomainTemp(listHist));
		}
		return Optional.empty();
	}

	@Override
	public Optional<AffWorkplaceHistory_ver1> getByHistId(String histId) {
		List<BsymtAffiWorkplaceHist> listHist = this.queryProxy().query(SELECT_BY_HISTID,BsymtAffiWorkplaceHist.class)
				.setParameter("histId", histId).getList();
		if (!listHist.isEmpty()){
			return Optional.of(toDomainTemp(listHist));
		}
		return Optional.empty();
	}
	
}
