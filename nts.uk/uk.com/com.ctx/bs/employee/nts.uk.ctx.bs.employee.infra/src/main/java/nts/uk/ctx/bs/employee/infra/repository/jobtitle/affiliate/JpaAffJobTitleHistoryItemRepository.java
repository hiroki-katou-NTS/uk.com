package nts.uk.ctx.bs.employee.infra.repository.jobtitle.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItemRepository;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate.BsymtAffJobTitleHistItem;

@Stateless
public class JpaAffJobTitleHistoryItemRepository extends JpaRepository
		implements AffJobTitleHistoryItemRepository {
	
	private static final String GET_BY_SID_DATE = "select hi from BsymtAffJobTitleHistItem hi"
			+ " inner join BsymtAffJobTitleHist h on hi.hisId = h.hisId"
			+ " where hi.sid = :sid and h.strDate <= :referDate and h.endDate >= :referDate";
	
	private static final String GET_BY_JID_DATE = "select hi from BsymtAffJobTitleHistItem hi"
			+ " inner join BsymtAffJobTitleHist h on hi.hisId = h.hisId"
			+ " where hi.jobTitleId = :jobTitleId and h.strDate <= :referDate and h.endDate >= :referDate";
	
	private static final String GET_ALL_BY_SID = "select hi from BsymtAffJobTitleHistItem hi"
			+ " where hi.sid = :sid";
	
	private static final String GET_BY_LIST_EID_DATE = "select hi from BsymtAffJobTitleHistItem hi"
			+ " inner join BsymtAffJobTitleHist h on hi.hisId = h.hisId"
			+ " where h.sid IN :lstSid and h.strDate <= :referDate and h.endDate >= :referDate";
	
	private static final String GET_ALL_BY_HISTID = "select hi from BsymtAffJobTitleHistItem hi"
			+ " where hi.hisId IN :histIds";
	
	private static final String GET_BY_LIST_JOB = "select hi from BsymtAffJobTitleHistItem hi"
			+ " where hi.hisId = :histId AND  hi.jobTitleId IN :jobTitleIds";
	
	/**
	 * Convert from domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private BsymtAffJobTitleHistItem toEntity(AffJobTitleHistoryItem domain) {
		return new BsymtAffJobTitleHistItem(domain.getHistoryId(), domain.getEmployeeId(), domain.getJobTitleId(),
				domain.getNote().v());
	}

	/**
	 * Update entity
	 * 
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(AffJobTitleHistoryItem domain, BsymtAffJobTitleHistItem entity) {
		entity.jobTitleId = domain.getJobTitleId();
		entity.note = domain.getNote().v();
	}

	@Override
	public void add(AffJobTitleHistoryItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(AffJobTitleHistoryItem domain) {

		Optional<BsymtAffJobTitleHistItem> existItem = this.queryProxy().find(domain.getHistoryId(),
				BsymtAffJobTitleHistItem.class);

		if (!existItem.isPresent()) {
			throw new RuntimeException("invalid BsymtAffJobTitleHistItem");
		}
		updateEntity(domain, existItem.get());

		this.commandProxy().update(existItem.get());
	}

	@Override
	public void delete(String histId) {
		Optional<BsymtAffJobTitleHistItem> existItem = this.queryProxy().find(histId, BsymtAffJobTitleHistItem.class);

		if (!existItem.isPresent()) {
			throw new RuntimeException("invalid BsymtAffJobTitleHistItem");
		}

		this.commandProxy().remove(BsymtAffJobTitleHistItem.class, histId);
	}

	private AffJobTitleHistoryItem toDomain(BsymtAffJobTitleHistItem ent) {
		return AffJobTitleHistoryItem.createFromJavaType(ent.hisId, ent.sid, ent.jobTitleId,
				ent.note);
	}

	@Override
	public Optional<AffJobTitleHistoryItem> findByHitoryId(String historyId) {
		Optional<BsymtAffJobTitleHistItem> optionData = this.queryProxy().find(historyId,
				BsymtAffJobTitleHistItem.class);
		if (optionData.isPresent()) {
			return Optional.of(toDomain(optionData.get()));
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<AffJobTitleHistoryItem> getByEmpIdAndReferDate(String employeeId, GeneralDate referDate) {
		Optional<BsymtAffJobTitleHistItem> optionData = this.queryProxy()
				.query(GET_BY_SID_DATE, BsymtAffJobTitleHistItem.class).setParameter("sid", employeeId)
				.setParameter("referDate", referDate).getSingle();
		if (optionData.isPresent()) {
			BsymtAffJobTitleHistItem ent = optionData.get();
			return Optional.of(AffJobTitleHistoryItem.createFromJavaType(ent.hisId, ent.sid,
					ent.jobTitleId, ent.note));
		}
		return Optional.empty();
	}

	@Override
	public List<AffJobTitleHistoryItem> getByJobIdAndReferDate(String jobId, GeneralDate referDate) {
		List<BsymtAffJobTitleHistItem> lstData = this.queryProxy()
				.query(GET_BY_JID_DATE, BsymtAffJobTitleHistItem.class).setParameter("jobTitleId", jobId)
				.setParameter("referDate", referDate).getList();
		List<AffJobTitleHistoryItem> lstObj = new ArrayList<>();
		if (!lstData.isEmpty()) {
			for (BsymtAffJobTitleHistItem data: lstData) {
				BsymtAffJobTitleHistItem ent = data;
				lstObj.add(AffJobTitleHistoryItem.createFromJavaType(ent.hisId, ent.sid, ent.jobTitleId, ent.note));
			}
			return lstObj;
		}
		return null;
	}

	@Override
	public List<AffJobTitleHistoryItem> getAllBySid(String sid) {
		List<BsymtAffJobTitleHistItem> optionData = this.queryProxy()
				.query(GET_ALL_BY_SID, BsymtAffJobTitleHistItem.class)
				.setParameter("sid", sid).getList();
		
		List<AffJobTitleHistoryItem> lstAffJobTitleHistoryItems = new ArrayList<>();
		
		if (optionData != null && !optionData.isEmpty()) {
			optionData.stream().forEach((item) -> {
				lstAffJobTitleHistoryItems.add(AffJobTitleHistoryItem.createFromJavaType(item.hisId, item.sid, item.jobTitleId, item.note));
			});
		}
		
		if (lstAffJobTitleHistoryItems != null && !lstAffJobTitleHistoryItems.isEmpty()) {
			return lstAffJobTitleHistoryItems;
		}
		return null;
	}

	@Override
	public List<AffJobTitleHistoryItem> getAllByListSidDate(List<String> lstSid, GeneralDate referDate) {
		List<BsymtAffJobTitleHistItem> data = this.queryProxy()
				.query(GET_BY_LIST_EID_DATE, BsymtAffJobTitleHistItem.class)
				.setParameter("lstSid", lstSid)
				.setParameter("referDate", referDate).getList();
		
		List<AffJobTitleHistoryItem> lstAffJobTitleHistoryItems = new ArrayList<>();
		
		if (data != null && !data.isEmpty()) {
			data.stream().forEach((item) -> {
				lstAffJobTitleHistoryItems.add(AffJobTitleHistoryItem.createFromJavaType(item.hisId, item.sid, item.jobTitleId, item.note));
			});
		}
		
		if (lstAffJobTitleHistoryItems != null && !lstAffJobTitleHistoryItems.isEmpty()) {
			return lstAffJobTitleHistoryItems;
		}
		return null;
	}

	@Override
	public List<AffJobTitleHistoryItem> findByHitoryIds(List<String> historyIds) {
		if (historyIds.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<AffJobTitleHistoryItem> results = new ArrayList<>();
		CollectionUtil.split(historyIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subIds -> {
			List<AffJobTitleHistoryItem> subResults = this.queryProxy()
				.query(GET_ALL_BY_HISTID, BsymtAffJobTitleHistItem.class).setParameter("histIds", subIds)
				.getList(ent -> toDomain(ent));
			
			results.addAll(subResults);
		});

		return results;
	}
	
	// request list 515
	@Override
	public List<AffJobTitleHistoryItem> findHistJob(String historyId, List<String> jobIds) {
		return this.queryProxy().query(GET_BY_LIST_JOB, BsymtAffJobTitleHistItem.class)
				.setParameter("histId", historyId)
				.setParameter("jobTitleIds", jobIds).getList().stream().map(x -> toDomain(x)).collect(Collectors.toList());
	}

}
