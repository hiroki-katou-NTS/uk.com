package nts.uk.ctx.bs.employee.infra.repository.jobtitle.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistory_ver1;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate.BsymtAffJobTitleHist;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaAffJobTitleHistoryRepository_v1 extends JpaRepository implements AffJobTitleHistoryRepository_ver1 {

	private final String QUERY_GET_AFFJOBTITLEHIST_BYSID = "SELECT jb FROM BsymtAffJobTitleHist jb"
			+ " WHERE jb.sid = :sid and jb.cid = :cid ORDER BY jb.strDate";
	
	private final String QUERY_GET_AFFJOBTITLEHIST_BYSID_DESC = QUERY_GET_AFFJOBTITLEHIST_BYSID + " DESC";
	
	private final String GET_BY_SID_DATE = "select h from BsymtAffJobTitleHist h"
			+ " where h.sid = :sid and h.strDate <= :standardDate and h.endDate >= :standardDate";
	
	private final String GET_BY_LISTSID_DATE = "SELECT h FROM BsymtAffJobTitleHist h"
			+ " where h.sid IN :lstSid AND h.strDate <= :standardDate and h.endDate >= :standardDate";
	private final String GET_BY_HID_SID = "select h from BsymtAffJobTitleHist h"
			+ " where h.sid = :sid and h.hisId = :hisId";

	/**
	 * Convert from domain to entity
	 * @param employeeId
	 * @param listHist
	 * @return
	 */
	private AffJobTitleHistory_ver1 toAffJobTitleHist(List<BsymtAffJobTitleHist> listHist) {
		AffJobTitleHistory_ver1 domain = new AffJobTitleHistory_ver1(listHist.get(0).getCid(), listHist.get(0).getSid(), new ArrayList<>());
		DateHistoryItem dateItem = null;
		for (BsymtAffJobTitleHist item : listHist) {
			dateItem = new DateHistoryItem(item.getHisId(), new DatePeriod(item.getStrDate(), item.getEndDate()));
			domain.getHistoryItems().add(dateItem);
		}
		return domain;
	}

	/**
	 * Convert from domain to BsymtAffJobTitleHist entity
	 * @param sId
	 * @param domain
	 * @return
	 */
	private BsymtAffJobTitleHist toEntity(String cid, String sId, DateHistoryItem domain) {
		return new BsymtAffJobTitleHist(domain.identifier(), sId, cid, domain.start(), domain.end());
	}

	@Override
	public Optional<AffJobTitleHistory_ver1> getListBySid(String cid, String sid) {
		List<BsymtAffJobTitleHist> listHist = this.queryProxy()
				.query(QUERY_GET_AFFJOBTITLEHIST_BYSID, BsymtAffJobTitleHist.class)
				.setParameter("cid", cid).setParameter("sid", sid).getList();
		if (listHist != null && !listHist.isEmpty()) {
			return Optional.of(toAffJobTitleHist(listHist));
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<AffJobTitleHistory_ver1> getListBySidDesc(String cid, String sid) {
		List<BsymtAffJobTitleHist> listHist = this.queryProxy()
				.query(QUERY_GET_AFFJOBTITLEHIST_BYSID_DESC, BsymtAffJobTitleHist.class)
				.setParameter("cid", cid).setParameter("sid", sid).getList();
		if (listHist != null && !listHist.isEmpty()) {
			return Optional.of(toAffJobTitleHist(listHist));
		}
		return Optional.empty();
	}

	@Override
	public void add(String cid, String sid, DateHistoryItem item) {
		this.commandProxy().insert(toEntity(cid, sid, item));
	}

	@Override
	public void update(DateHistoryItem item) {
		Optional<BsymtAffJobTitleHist> itemToBeUpdated = this.queryProxy().find(item.identifier(),
				BsymtAffJobTitleHist.class);

		if (!itemToBeUpdated.isPresent()) {
			throw new RuntimeException("Invalid BsymtAffJobTitleHist");
		}
		// Update entity
		updateEntity(item, itemToBeUpdated.get());
		this.commandProxy().update(itemToBeUpdated.get());
	}

	@Override
	public void delete(String histId) {
		Optional<BsymtAffJobTitleHist> itemToBeDeleted = this.queryProxy().find(histId, BsymtAffJobTitleHist.class);

		if (!itemToBeDeleted.isPresent()) {
			throw new RuntimeException("Invalid BsymtAffJobTitleHist");
		}
		this.commandProxy().remove(BsymtAffJobTitleHist.class, histId);
	}

	/**
	 * Update entity from domain
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private void updateEntity(DateHistoryItem item, BsymtAffJobTitleHist entity) {
		entity.setStrDate(item.start());
		entity.setEndDate(item.end());
	}

	@Override
	public Optional<AffJobTitleHistory_ver1> getByHistoryId(String historyId) {
		Optional<BsymtAffJobTitleHist> optionData = this.queryProxy().find(historyId, BsymtAffJobTitleHist.class);
		if (optionData.isPresent()) {
			return Optional.of(toDomain(optionData.get()));
		}
		return Optional.empty();
	}

	private AffJobTitleHistory_ver1 toDomain(BsymtAffJobTitleHist ent) {
		AffJobTitleHistory_ver1 domain = new AffJobTitleHistory_ver1(ent.getCid(), ent.getSid(), new ArrayList<>());
		DateHistoryItem dateItem = new DateHistoryItem(ent.getHisId(),
				new DatePeriod(ent.getStrDate(), ent.getEndDate()));

		domain.getHistoryItems().add(dateItem);

		return domain;
	}

	@Override
	public Optional<AffJobTitleHistory_ver1> getByEmpIdAndStandardDate(String employeeId, GeneralDate standardDate) {
		Optional<BsymtAffJobTitleHist> optionaData = this.queryProxy()
				.query(GET_BY_SID_DATE, BsymtAffJobTitleHist.class)
				.setParameter("sid", employeeId).setParameter("standardDate", standardDate).getSingle();
		if ( optionaData.isPresent()) {
			return Optional.of(toDomain(optionaData.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<AffJobTitleHistory_ver1> getAllBySid(String sid) {
		String cid = AppContexts.user().companyId();
		List<AffJobTitleHistory_ver1> lstAffJobTitleHistory = new ArrayList<>();
		List<BsymtAffJobTitleHist> listHist = this.queryProxy()
				.query(QUERY_GET_AFFJOBTITLEHIST_BYSID, BsymtAffJobTitleHist.class)
				.setParameter("cid", cid).setParameter("sid", sid).getList();
		if (listHist != null && !listHist.isEmpty()) {
			for (BsymtAffJobTitleHist item : listHist) {
				AffJobTitleHistory_ver1 domain = new AffJobTitleHistory_ver1(item.getCid(), item.getSid(), new ArrayList<>());
				DateHistoryItem dateItem = null;
				dateItem = new DateHistoryItem(item.getHisId(), new DatePeriod(item.getStrDate(), item.getEndDate()));
				domain.getHistoryItems().add(dateItem);
				lstAffJobTitleHistory.add(domain);
			}
		}
		if (lstAffJobTitleHistory != null && !lstAffJobTitleHistory.isEmpty()) {
			return lstAffJobTitleHistory;
		}
		return null;
	}

	// TODO: HoangDD check lại, không nhất thiết phải truyền cả SID
	@Override
	public Optional<AffJobTitleHistory_ver1> getListByHidSid(String hid, String sid) {
		Optional<BsymtAffJobTitleHist> optHist = this.queryProxy()
				.query(GET_BY_HID_SID, BsymtAffJobTitleHist.class)
				.setParameter("hisId", hid).setParameter("sid", sid).getSingle();
		if (optHist.isPresent()) {
			BsymtAffJobTitleHist affJobTitleHist = optHist.get();
			List<BsymtAffJobTitleHist> listHist = new ArrayList<>();
			listHist.add(affJobTitleHist);
			return Optional.of(toAffJobTitleHist(listHist)); 
		}
		return Optional.empty();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.
	 * AffJobTitleHistoryRepository_ver1#searchJobTitleHistory(nts.arc.time.
	 * GeneralDate, java.util.List)
	 */
	@Override
	public List<AffJobTitleHistory_ver1> searchJobTitleHistory(GeneralDate baseDate, List<String> employeeIds) {
		if (CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}
		
		// Split employee id list.
		List<BsymtAffJobTitleHist> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, 1000, subList -> {
			resultList.addAll(this.queryProxy().query(GET_BY_LISTSID_DATE, BsymtAffJobTitleHist.class)
					.setParameter("lstSid", subList).setParameter("standardDate", baseDate).getList());
		});
		return resultList.stream().map(entity -> this.toDomain(entity)).collect(Collectors.toList());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.
	 * AffJobTitleHistoryRepository_ver1#findAllJobTitleHistory(nts.arc.time.
	 * GeneralDate, java.util.List)
	 */
	@Override
	public List<AffJobTitleHistory_ver1> findAllJobTitleHistory(GeneralDate baseDate, List<String> employeeIds) {
		if (CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}
		
		// Split employee id list.
		List<BsymtAffJobTitleHist> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, 1000, subList -> {
			resultList.addAll(this.queryProxy().query(GET_BY_LISTSID_DATE, BsymtAffJobTitleHist.class)
					.setParameter("lstSid", subList).setParameter("standardDate", baseDate).getList());
		});
		return resultList.stream().map(entity -> this.toDomain(entity)).collect(Collectors.toList());
	}

}
