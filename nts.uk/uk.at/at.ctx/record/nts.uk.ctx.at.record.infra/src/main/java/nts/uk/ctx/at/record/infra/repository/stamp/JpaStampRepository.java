/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.stamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.stamp.StampItem;
import nts.uk.ctx.at.record.dom.stamp.StampRepository;
import nts.uk.ctx.at.record.infra.entity.stamp.KwkdtStamp;
import nts.uk.ctx.at.record.infra.entity.stamp.KwkdtStampPK;

@Stateless
public class JpaStampRepository extends JpaRepository implements StampRepository {
	private static final String SELECT_STAMP = "SELECT c FROM KwkdtStamp c ";
	private static final String SELECT_NO_WHERE = "SELECT e.sid, d.workLocationName, c FROM KwkdtStamp c";

	private static final String SELECT_BY_LIST_CARD_NO = SELECT_STAMP
			+ " WHERE c.kwkdtStampPK.cardNumber IN :lstCardNumber";
	private static final String SELECT_BY_EMPPLOYEE_ID = SELECT_NO_WHERE
			+ " LEFT JOIN KwlmtWorkLocation d ON c.workLocationCd = d.kwlmtWorkLocationPK.workLocationCD"
			+ " AND d.kwlmtWorkLocationPK.companyID = :companyId"
			+ " INNER JOIN KwkdtStampCard e ON e.kwkdtStampCardPK.cardNumber = c.kwkdtStampPK.cardNumber"
			+ " WHERE c.kwkdtStampPK.stampDate >= :startDate" + " AND c.kwkdtStampPK.stampDate <= :endDate"
			+ " AND c.kwkdtStampPK.cardNumber IN :lstCardNumber";

	private static final String SELECT_BY_DATE_COMPANY = "SELECT  d.workLocationName, c FROM KwkdtStamp c "
			+ " LEFT JOIN KwlmtWorkLocation d ON c.workLocationCd = d.kwlmtWorkLocationPK.workLocationCD"
			+ " AND d.kwlmtWorkLocationPK.companyID = :companyId"
			+ " WHERE c.kwkdtStampPK.stampDate >= :startDate" + " AND c.kwkdtStampPK.stampDate <= :endDate"
			+ " ORDER BY c.kwkdtStampPK.cardNumber ASC, c.kwkdtStampPK.stampDate ASC, c.kwkdtStampPK.attendanceTime ASC ";

	private static final String SELECT_BY_LIST_CARD_NO_DATE = SELECT_STAMP
			+ " WHERE c.kwkdtStampPK.stampDate >= :startDate" + " AND c.kwkdtStampPK.stampDate <= :endDate"
			+ " AND c.kwkdtStampPK.cardNumber IN :lstCardNumber ";
	
	private static final String SELECT_BY_CARD_NO_DATE = SELECT_STAMP
			+ " WHERE c.kwkdtStampPK.stampDate >= :startDate" + " AND c.kwkdtStampPK.stampDate <= :endDate"
			+ " AND c.kwkdtStampPK.cardNumber = :cardNumber "
			+ " ORDER BY c.kwkdtStampPK.cardNumber ASC, c.kwkdtStampPK.stampDate ASC, c.kwkdtStampPK.attendanceTime ASC ";
	
	private static final String SELECT_BY_EMPPLOYEE_ID_FIX = SELECT_NO_WHERE
			+ " LEFT JOIN KwlmtWorkLocation d ON c.workLocationCd = d.kwlmtWorkLocationPK.workLocationCD"
			+ " AND d.kwlmtWorkLocationPK.companyID = :companyId"
			+ " INNER JOIN KwkdtStampCard e ON e.cardNo = c.kwkdtStampPK.cardNumber"
			+ " WHERE c.kwkdtStampPK.stampDate >= :startDate" + " AND c.kwkdtStampPK.stampDate <= :endDate"
			+ " AND c.kwkdtStampPK.cardNumber IN :lstCardNumber"
			+ " ORDER BY c.kwkdtStampPK.cardNumber ASC, c.kwkdtStampPK.stampDate ASC, c.kwkdtStampPK.attendanceTime ASC ";
	/**
	 * Convert to domain contain Stamp Entity only.
	 * 
	 * @param object
	 * @return
	 */
	private StampItem toDomainStampOnly(KwkdtStamp entity) {
		// Set empty value for 2 record not exist in Stamp Entity.
		StampItem domain = StampItem.createFromJavaType(entity.kwkdtStampPK.cardNumber,
				entity.kwkdtStampPK.attendanceTime, entity.stampCombinationAtr, entity.siftCd, entity.stampMethod,
				entity.kwkdtStampPK.stampAtr, entity.workLocationCd, "", entity.goOutReason,
				entity.kwkdtStampPK.stampDate, "", entity.reflectedAtr);
		return domain;
	}

	private static StampItem toDomain(Object[] object) {
		String personId = (String) object[0];
		String workLocationName = (String) object[1];
		KwkdtStamp entity = (KwkdtStamp) object[2];
		StampItem domain = StampItem.createFromJavaType(entity.kwkdtStampPK.cardNumber,
				entity.kwkdtStampPK.attendanceTime, entity.stampCombinationAtr, entity.siftCd, entity.stampMethod,
				entity.kwkdtStampPK.stampAtr, entity.workLocationCd, workLocationName, entity.goOutReason,
				entity.kwkdtStampPK.stampDate, personId, entity.reflectedAtr);
		return domain;
	}
	
	private static StampItem toDomainDate(Object[] object) {
		String workLocationName = (String) object[0];
		KwkdtStamp entity = (KwkdtStamp) object[1];
		StampItem domain = StampItem.createFromJavaType(entity.kwkdtStampPK.cardNumber,
				entity.kwkdtStampPK.attendanceTime, entity.stampCombinationAtr, entity.siftCd, entity.stampMethod,
				entity.kwkdtStampPK.stampAtr, entity.workLocationCd, workLocationName, entity.goOutReason,
				entity.kwkdtStampPK.stampDate, "", entity.reflectedAtr);
		return domain;
	}


	/**
	 * Get list StampItem by List Card Number.
	 */
	@Override
	public List<StampItem> findByListCardNo(List<String> lstCardNumber) {
		List<StampItem> list = this.queryProxy().query(SELECT_BY_LIST_CARD_NO, KwkdtStamp.class)
				.setParameter("lstCardNumber", lstCardNumber).getList(c -> toDomainStampOnly(c));
		return list;
	}

	@Override
	public List<StampItem> findByEmployeeID(String companyId, List<String> stampCards, String startDate,
			String endDate) {
		List<StampItem> list = this.queryProxy().query(SELECT_BY_EMPPLOYEE_ID, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("startDate", GeneralDate.fromString(startDate, "yyyyMMdd"))
				.setParameter("endDate", GeneralDate.fromString(endDate, "yyyyMMdd"))
				.setParameter("lstCardNumber", stampCards).getList(c -> toDomain(c));
		return list;
	}

	@Override
	public List<StampItem> findByDate(String cardNumber, GeneralDateTime startDate, GeneralDateTime endDate) {
		return this.queryProxy().query(SELECT_BY_CARD_NO_DATE, KwkdtStamp.class)
					.setParameter("cardNumber", cardNumber)
					 .setParameter("startDate", startDate)
					 .setParameter("endDate",endDate)
					 .getList(c -> toDomainStampOnly(c));
	}

	@Override
	public void updateStampItem(StampItem stampItem) {
		Optional<KwkdtStamp> kwkdtStampOptional = this.queryProxy().find(new KwkdtStampPK(stampItem.getCardNumber().v(),
				stampItem.getAttendanceTime().v().intValue(), stampItem.getStampAtr().value, stampItem.getDate()),
				KwkdtStamp.class);
		if (kwkdtStampOptional.isPresent()) {
			KwkdtStamp kwkdtStamp = kwkdtStampOptional.get();
			kwkdtStamp.goOutReason = stampItem.getGoOutReason().value;
			kwkdtStamp.reflectedAtr = stampItem.getReflectedAtr().value;
			kwkdtStamp.siftCd = stampItem.getSiftCd().v();
			kwkdtStamp.stampCombinationAtr = stampItem.getStampCombinationAtr().value;
			kwkdtStamp.stampMethod = stampItem.getStampMethod().value;
			kwkdtStamp.workLocationCd = stampItem.getWorkLocationCd().v();
			this.commandProxy().update(kwkdtStamp);
		} else {
			this.commandProxy().insert(new KwkdtStamp(
					new KwkdtStampPK(stampItem.getCardNumber().v(), stampItem.getAttendanceTime().v().intValue(),
							stampItem.getStampAtr().value, stampItem.getDate()),
					stampItem.getSiftCd().v(), stampItem.getStampCombinationAtr().value,
					stampItem.getWorkLocationCd().v(), stampItem.getStampMethod().value,
					stampItem.getGoOutReason().value, stampItem.getReflectedAtr().value));
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.stamp.StampRepository#findByDateCompany(java.lang.String, nts.arc.time.GeneralDate, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<StampItem> findByDateCompany(String companyId, GeneralDateTime startDate, GeneralDateTime endDate) {
		List<StampItem> list = this.queryProxy().query(SELECT_BY_DATE_COMPANY, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("startDate", startDate)
				.setParameter("endDate",endDate)
				.getList(c -> toDomainDate(c));
		return list;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.stamp.StampRepository#findByCardsDate(java.lang.String, java.util.List, nts.arc.time.GeneralDateTime, nts.arc.time.GeneralDateTime)
	 */
	@Override
	public List<StampItem> findByCardsDate(String companyId, List<String> lstCardNumber, GeneralDateTime startDate,
			GeneralDateTime endDate) {
		List<StampItem> lstData = new ArrayList<StampItem>();
		
		if (lstCardNumber.isEmpty()) {
			return Collections.emptyList();
		}
		
		CollectionUtil.split(lstCardNumber, 1000, subLstCardNumber -> {
			lstData.addAll(this.queryProxy().query(SELECT_BY_LIST_CARD_NO_DATE, KwkdtStamp.class)
					.setParameter("lstCardNumber", subLstCardNumber)
					 .setParameter("startDate", startDate)
					 .setParameter("endDate",endDate)
					 .getList(c -> toDomainStampOnly(c)));
		});
		return lstData;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.stamp.StampRepository#findByEmployeeID_Fix(java.lang.String, java.util.List, nts.arc.time.GeneralDateTime, nts.arc.time.GeneralDateTime)
	 */
	@Override
	public List<StampItem> findByEmployeeID_Fix(String companyId, List<String> stampCards, GeneralDateTime startDate,
			GeneralDateTime endDate) {
		if (CollectionUtil.isEmpty(stampCards)) {
			return Collections.emptyList();
		}
		
		List<StampItem> list = this.queryProxy().query(SELECT_BY_EMPPLOYEE_ID_FIX, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.setParameter("lstCardNumber", stampCards).getList(c -> toDomain(c));
		return list;
	}
}
