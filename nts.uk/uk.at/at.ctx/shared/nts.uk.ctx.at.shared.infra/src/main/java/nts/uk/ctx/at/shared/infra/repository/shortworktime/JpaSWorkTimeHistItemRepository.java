/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.shortworktime;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.shortworktime.SChildCareFrame;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHistItemPK;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHistItemPK_;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHistItem_;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.KshmtShorttimeHistItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.GeneralHistoryItem;
import nts.uk.shr.com.time.TimeWithDayAttr;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The Class JpaSWorkTimeHistItemRepository.
 */
@RequestScoped
public class JpaSWorkTimeHistItemRepository extends JpaRepository implements SWorkTimeHistItemRepository {

    /*
     * (non-Javadoc)
     *
     * @see
     * nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository#add(
     * nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem)
     */
    @Override
    @Transactional
    public void add(ShortWorkTimeHistoryItem domain) {
        this.commandProxy().insert(this.toEntity(domain));
        this.getEntityManager().flush();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository#update
     * (nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem)
     */
    @Override
    @Transactional
    public void update(ShortWorkTimeHistoryItem domain) {
//		this.delete(domain.getEmployeeId(), domain.getHistoryId());
//		
//		this.add(domain);
        this.commandProxy().update(toEntity(domain));
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository#
     * findByKey(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public Optional<ShortWorkTimeHistoryItem> findByKey(String empId, String histId) {
        EntityManager em = this.getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KshmtShorttimeHistItem> query = builder.createQuery(KshmtShorttimeHistItem.class);
        Root<KshmtShorttimeHistItem> root = query.from(KshmtShorttimeHistItem.class);

        List<Predicate> predicateList = new ArrayList<>();

        predicateList.add(builder.equal(root.get(BshmtWorktimeHistItem_.bshmtWorktimeHistItemPK)
                .get(BshmtWorktimeHistItemPK_.sid), empId));
        predicateList.add(builder.equal(root.get(BshmtWorktimeHistItem_.bshmtWorktimeHistItemPK)
                .get(BshmtWorktimeHistItemPK_.histId), histId));

        query.where(predicateList.toArray(new Predicate[]{}));

        return em.createQuery(query).getResultList().stream()
                .map(entity -> new ShortWorkTimeHistoryItem(new JpaSWorkTimeHistItemGetMemento(entity)))
                .findFirst();
    }

    /**
     * To entity.
     *
     * @param domain the domain
     * @return the bshmt worktime hist item
     */
    private KshmtShorttimeHistItem toEntity(ShortWorkTimeHistoryItem domain) {
        KshmtShorttimeHistItem entity = this.queryProxy()
                .find(new BshmtWorktimeHistItemPK(domain.getEmployeeId(), domain.getHistoryId()),
                        KshmtShorttimeHistItem.class)
                .orElse(new KshmtShorttimeHistItem(new BshmtWorktimeHistItemPK()));
        JpaSWorkTimeHistItemSetMemento memento = new JpaSWorkTimeHistItemSetMemento(entity);
        domain.saveToMemento(memento);
        return entity;
    }

    @Override
    @Transactional
    public void delete(String sid, String hist) {
        BshmtWorktimeHistItemPK key = new BshmtWorktimeHistItemPK(sid, hist);
        this.commandProxy().remove(KshmtShorttimeHistItem.class, key);
        this.getEntityManager().flush();
    }

    /* (non-Javadoc)
     * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository#findByHistIds(java.util.List)
     */
    @Override
    public List<ShortWorkTimeHistoryItem> findByHistIds(List<String> histIds) {
        // Check
        if (CollectionUtil.isEmpty(histIds)) {
            return Collections.emptyList();
        }

        EntityManager em = this.getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KshmtShorttimeHistItem> query = builder
                .createQuery(KshmtShorttimeHistItem.class);
        Root<KshmtShorttimeHistItem> root = query.from(KshmtShorttimeHistItem.class);

        List<KshmtShorttimeHistItem> result = new ArrayList<>();

        CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
            // Predicate where clause
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(root.get(BshmtWorktimeHistItem_.bshmtWorktimeHistItemPK)
                    .get(BshmtWorktimeHistItemPK_.histId).in(splitData));
            query.where(predicateList.toArray(new Predicate[]{}));

            result.addAll(em.createQuery(query).getResultList());
        });

        return result.stream().map(
                entity -> new ShortWorkTimeHistoryItem(new JpaSWorkTimeHistItemGetMemento(entity)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> findByHistIdsCPS013(List<String> histIds) {
        // Check
        if (CollectionUtil.isEmpty(histIds)) {
            return Collections.emptyList();
        }

        EntityManager em = this.getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KshmtShorttimeHistItem> query = builder
                .createQuery(KshmtShorttimeHistItem.class);
        Root<KshmtShorttimeHistItem> root = query.from(KshmtShorttimeHistItem.class);

        List<KshmtShorttimeHistItem> ListWorktimeHistItem = new ArrayList<>();
        List<Object[]> results = new ArrayList<>();

        CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
            // Predicate where clause
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(root.get(BshmtWorktimeHistItem_.bshmtWorktimeHistItemPK)
                    .get(BshmtWorktimeHistItemPK_.histId).in(splitData));
            query.where(predicateList.toArray(new Predicate[]{}));

            ListWorktimeHistItem.addAll(em.createQuery(query).getResultList());
        });

        ListWorktimeHistItem.forEach(entity -> {
            String historyId = entity.getBshmtWorktimeHistItemPK().getHistId();
            String sid = entity.getBshmtWorktimeHistItemPK().getSid();
            List<SChildCareFrame> lstTimeSlot = entity.getLstBshmtSchildCareFrame().stream().map(e -> {
                return SChildCareFrame.builder()
                        .timeSlot(e.getBshmtSchildCareFramePK().getTimeNo())
                        .startTime(new TimeWithDayAttr(e.getStrClock()))
                        .endTime(new TimeWithDayAttr(e.getEndClock()))
                        .build();
            }).collect(Collectors.toList());
            ShortWorkTimeHistoryItem domain = new ShortWorkTimeHistoryItem(sid, historyId, ChildCareAtr.CARE, lstTimeSlot);
            Map<String, Integer> mapListEnum = new HashMap<>();
            mapListEnum.put("IS00104", entity.getChildCareAtr());
            results.add(new Object[]{domain, mapListEnum});
        });

        return results;
    }

    @Override
    public void addAll(List<ShortWorkTimeHistoryItem> domains) {
        List<KshmtShorttimeHistItem> entities = domains.stream().map(c -> {
            return this.toEntity(c);
        }).collect(Collectors.toList());
        this.commandProxy().insertAll(entities);
    }

    @Override
    public void updateAll(List<ShortWorkTimeHistoryItem> domains) {
        List<KshmtShorttimeHistItem> entities = domains.stream().map(c -> {
            return this.toEntity(c);
        }).collect(Collectors.toList());
        this.commandProxy().updateAll(entities);
    }

    @Override
    public List<ShortWorkTimeHistoryItem> findWithSidDatePeriod(String companyId, List<String> employeeIds,
                                                                DatePeriod period) {

        List<DateHistoryItem> resultHist = new ArrayList<>();

        CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
            String sql = "SELECT * FROM KSHMT_SHORTTIME_HIST WHERE CID = ? AND  STR_YMD <= ? AND END_YMD >= ? AND SID IN ("
                    + NtsStatement.In.createParamsString(subList) + ")";

            try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
                stmt.setString(1, companyId);
                stmt.setDate(2, Date.valueOf(period.end().localDate()));
                stmt.setDate(3, Date.valueOf(period.start().localDate()));
                for (int i = 0; i < subList.size(); i++) {
                    stmt.setString(4 + i, subList.get(i));
                }

                List<DateHistoryItem> lstObj = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
                    return new DateHistoryItem(rec.getString("HIST_ID"),
                            new DatePeriod(rec.getGeneralDate("STR_YMD"), rec.getGeneralDate("END_YMD")));
                });
                resultHist.addAll(lstObj);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        if (resultHist.isEmpty())
            return Collections.emptyList();

        return findByHistIds(resultHist.stream().map(x -> x.identifier()).distinct().collect(Collectors.toList()));
    }

    @Override
    public Optional<ShortWorkTimeHistoryItem> getShortWorkTimeHistoryItemBySidAndBaseDate(String sid,
                                                                                          GeneralDate baseDate) {
        // TODO dev code
        return null;
    }

    @Override
    public List<ShortWorkTimeHistoryItem> getShortWorkTimeHistoryItemBySidsAndBaseDate(List<String> sids,
                                                                                       GeneralDate baseDate) {
        // TODO dev code
        return null;
    }

    private final static String SELECT_BY_EMP_DATE = new StringBuilder("SELECT item FROM KshmtShorttimeHistItem item ")
            .append(" JOIN  item.KshmtShorttimeHist hst ON item.bshmtWorktimeHistItemPK.sid=hst.bshmtWorktimeHistPK.sid")
            .append(" AND item.bshmtWorktimeHistItemPK.histId=hst.bshmtWorktimeHistPK.histId")
            .append(" WHERE hst.bshmtWorktimeHistPK.sid = :employeeId ")
            .append(" AND hst.bshmtWorktimeHistPK.strYmd >= :strYmd ")
            .append(" AND hst.bshmtWorktimeHistPK.endYmd <= :endYmd ")
            .toString();

    /**
     * 社員を指定して年月日時点の履歴項目を取得する ( 社員ID, 年月日 ) ver2
     *
     * @param employeeId 社員ID
     * @param baseDate   年月日
     * @return the optional
     */
    @Override
    public Optional<ShortWorkTimeHistoryItem> findByEmployeeIdAndDate(String employeeId, GeneralDate baseDate) {
        List<DateHistoryItem> resultHist = new ArrayList<>();
        String sql = "SELECT * FROM KSHMT_SHORTTIME_HIST WHERE CID = ? AND  STR_YMD <= ? AND END_YMD >= ? AND SID = ?";
        try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
            stmt.setString(1, AppContexts.user().companyId());
            stmt.setDate(2, Date.valueOf(baseDate.localDate()));
            stmt.setDate(3, Date.valueOf(baseDate.localDate()));
            stmt.setString(4, employeeId);

            Optional<DateHistoryItem> histOpt = new NtsResultSet(stmt.executeQuery()).getSingle(rec -> {
                return new DateHistoryItem(rec.getString("HIST_ID"),
                        new DatePeriod(rec.getGeneralDate("STR_YMD"), rec.getGeneralDate("END_YMD")));
            });
            histOpt.ifPresent(resultHist::add);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (resultHist.isEmpty())
            return Optional.empty();

        return Optional.of(findByHistIds(resultHist.stream().map(GeneralHistoryItem::identifier).distinct().collect(Collectors.toList())).get(0));
    }

    private final static String SELECT_BY_EMP_LIST_DATE = new StringBuilder("SELECT item FROM KshmtShorttimeHistItem item ")
            .append(" JOIN  item.KshmtShorttimeHist hst ON item.bshmtWorktimeHistItemPK.sid=hst.bshmtWorktimeHistPK.sid")
            .append(" AND item.bshmtWorktimeHistItemPK.histId=hst.bshmtWorktimeHistPK.histId")
            .append(" WHERE hst.bshmtWorktimeHistPK.sid IN :employeeIdList ")
            .append(" AND hst.bshmtWorktimeHistPK.strYmd >= :strYmd ")
            .append(" AND hst.bshmtWorktimeHistPK.endYmd <= :endYmd ")
            .toString();

    /**
     * 社員IDリストを指定して年月日時点の履歴項目を取得する ( List<社員ID>, 年月日 ) ver2
     *
     * @param employeeIdList <社員ID>
     * @param baseDate       年月日
     * @return the optional
     */
    @Override
    public List<ShortWorkTimeHistoryItem> findByEmployeeIdListAndDate(List<String> employeeIdList, GeneralDate baseDate) {
        List<DateHistoryItem> resultHist = new ArrayList<>();
        CollectionUtil.split(employeeIdList, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
            String sql = "SELECT * FROM KSHMT_SHORTTIME_HIST WHERE CID = ? AND  STR_YMD <= ? AND END_YMD >= ? AND SID IN ("
                    + NtsStatement.In.createParamsString(subList) + ")";

            try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
                stmt.setString(1, AppContexts.user().companyId());
                stmt.setDate(2, Date.valueOf(baseDate.localDate()));
                stmt.setDate(3, Date.valueOf(baseDate.localDate()));
                for (int i = 0; i < subList.size(); i++) {
                    stmt.setString(4 + i, subList.get(i));
                }

                List<DateHistoryItem> lstObj = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
                    return new DateHistoryItem(rec.getString("HIST_ID"),
                            new DatePeriod(rec.getGeneralDate("STR_YMD"), rec.getGeneralDate("END_YMD")));
                });
                resultHist.addAll(lstObj);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        if (resultHist.isEmpty())
            return Collections.emptyList();

        return findByHistIds(resultHist.stream().map(GeneralHistoryItem::identifier).distinct().collect(Collectors.toList()));
    }
}
