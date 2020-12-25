package nts.uk.ctx.at.schedule.infra.repository.budget.premium;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.budget.premium.*;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.HistAnPerCost;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.ExtraTimeItemNo;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.*;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountUnit;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import java.util.ArrayList;

/**
 * @author Doan Duy Hung
 */

@Stateless
public class JpaPersonCostCalculationRepository extends JpaRepository implements PersonCostCalculationRepository {

    private static final String SEL_BY_CID = "SELECT a FROM KscmtPerCostCal a WHERE a.pk.companyID = :companyID ORDER BY a.startDate ASC";

    private static final String SEL_ITEM_BY_DATE = "SELECT a FROM KscmtPerCostCal a WHERE a.pk.companyID = :companyID AND a.startDate = :startDate";

    private static final String SEL_ITEM_BY_HID = "SELECT a FROM KscmtPerCostCal a WHERE a.pk.companyID = :companyID AND a.pk.histID = :historyID";

    private static final String SEL_ITEM_BEFORE = "SELECT a FROM KscmtPerCostCal a WHERE a.pk.companyID = :companyID AND a.startDate < :startDate ORDER BY a.startDate DESC";

    private static final String SEL_ITEM_AFTER = "SELECT a FROM KscmtPerCostCal a WHERE a.pk.companyID = :companyID AND a.startDate > :startDate";

    private static final String SEL_PRE_ATTEND = "SELECT a FROM KmldtPremiumAttendance a "
            + "WHERE a.kmldpPremiumAttendancePK.companyID = :companyID "
            + "AND a.kmldpPremiumAttendancePK.historyID = :historyID "
            + "AND a.kmldpPremiumAttendancePK.displayNumber = :displayNumber "
            + "AND a.kmldpPremiumAttendancePK.attendanceID = :attendanceID ";

    @Override
    public void add(PersonCostCalculation personCostCalculation) {
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<PersonCostCalculation> findByCompanyID(String companyID) {
        List<PersonCostCalculation> rs = new ArrayList<>();
        val listEntity = this.queryProxy().query(SEL_BY_CID, KscmtPerCostCal.class).setParameter("companyID", companyID)
                .getList();
        val listHistId = listEntity.stream().map(e -> e.getPk().histID).collect(Collectors.toList());
        val listRate = getPersonCostByListHistId(companyID, listHistId);
        for (KscmtPerCostCal item : listEntity) {
            val listPremiumSetting = listRate.stream().filter(x -> x.getHistoryID().equals(item.pk.histID)).collect(Collectors.toList());
            val sub = toSimpleDomain(item, listPremiumSetting);
            rs.add(sub);
        }
        return rs;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public Optional<PersonCostCalculation> findItemByDate(String companyID, GeneralDate startDate) {
        return this.queryProxy().query(SEL_ITEM_BY_DATE, KscmtPerCostCal.class).setParameter("companyID", companyID).setParameter("startDate", startDate)
                .getSingle().map(x -> toDomainPersonCostCalculation(x));
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public Optional<PersonCostCalculation> findItemByHistoryID(String companyID, String historyID) {
        return this.queryProxy().query(SEL_ITEM_BY_HID, KscmtPerCostCal.class).setParameter("companyID", companyID).setParameter("historyID", historyID)
                .getSingle().map(x -> toDomainPersonCostCalculation(x));
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public Optional<PersonCostCalculation> findItemBefore(String companyID, GeneralDate startDate) {
        List<KscmtPerCostCal> result = this.queryProxy().query(SEL_ITEM_BEFORE, KscmtPerCostCal.class)
                .setParameter("companyID", companyID).setParameter("startDate", startDate).getList();
        if (result.isEmpty()) return Optional.ofNullable(null);
        return Optional.of(toDomainPersonCostCalculation(result.get(0)));
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public Optional<PersonCostCalculation> findItemAfter(String companyID, GeneralDate startDate) {
        List<KscmtPerCostCal> result = this.queryProxy().query(SEL_ITEM_AFTER, KscmtPerCostCal.class)
                .setParameter("companyID", companyID).setParameter("startDate", startDate).getList();
        if (result.isEmpty()) return Optional.empty();
        return Optional.of(toDomainPersonCostCalculation(result.get(0)));
    }

    @Override
    public void update(PersonCostCalculation personCostCalculation) {

    }

    @Override
    public void delete(String companyId, String historyId) {

        val entityPerCosst = this.queryProxy().query(SEL_ITEM_BY_HID, KscmtPerCostCal.class).setParameter("companyID", companyId).setParameter("historyID", historyId)
                .getSingle();

        entityPerCosst.ifPresent(kscmtPerCostCal -> this.commandProxy().remove(kscmtPerCostCal));
        String queryRate = "SELECT a FROM KscmtPerCostPremiRate a " +
                " WHERE a.pk.companyID = :cid " +
                " AND a.pk.histID = :histID ";
        List<KscmtPerCostPremiRate> listEntityRate = this.queryProxy().query(queryRate, KscmtPerCostPremiRate.class)
                .setParameter("cid", companyId)
                .setParameter("histID", historyId)
                .getList();
        if (!listEntityRate.isEmpty()) {
            this.commandProxy().removeAll(listEntityRate);
        }

        String queryPremiumAttendance = "SELECT a FROM KmldtPremiumAttendance a " +
                " WHERE a.kmldpPremiumAttendancePK.companyID = :cid " +
                " AND a.kmldpPremiumAttendancePK.historyID = :histID ";
        List<KmldtPremiumAttendance> listEntityPremiumAttendance = this.queryProxy().query(queryPremiumAttendance, KmldtPremiumAttendance.class)
                .setParameter("cid", companyId)
                .setParameter("histID", historyId)
                .getList();
        if (!listEntityRate.isEmpty()) {
            this.commandProxy().removeAll(listEntityPremiumAttendance);
        }

    }

    private PersonCostCalculation toSimpleDomain(KscmtPerCostCal kscmtPerCostCal, List<PremiumSetting> premiumSetting) {


        val roundingOfPremium = new UnitPriceRoundingSetting(EnumAdaptor.valueOf(kscmtPerCostCal.costRounding, UnitPriceRounding.class));
        val amountRoundingSetting = new AmountRoundingSetting(
                AmountUnit.valueOf(kscmtPerCostCal.getCostUnit()),
                AmountRounding.valueOf(kscmtPerCostCal.costRounding)
        );

        return new PersonCostCalculation(
                new PersonCostRoundingSetting(roundingOfPremium, amountRoundingSetting),
                kscmtPerCostCal.getPk().companyID,
                new Remarks(kscmtPerCostCal.getMemo()),
                premiumSetting,
                Optional.of(EnumAdaptor.valueOf(kscmtPerCostCal.getUnitPriceAtr(), UnitPrice.class)),
                EnumAdaptor.valueOf(kscmtPerCostCal.getUnitPriceSettingMethod(), HowToSetUnitPrice.class),
                new WorkingHoursUnitPrice(kscmtPerCostCal.getWokkingHoursUnitPriceAtr()),
                kscmtPerCostCal.getPk().histID
        );

    }

    /**
     * convert PersonCostCalculation Entity Object to PersonCostCalculation Domain Object
     *
     * @return PersonCostCalculation Domain Object
     */
    private PersonCostCalculation toDomainPersonCostCalculation(KscmtPerCostCal kscmtPerCostCal) {
        val roundingOfPremium = new UnitPriceRoundingSetting(EnumAdaptor.valueOf(kscmtPerCostCal.costRounding, UnitPriceRounding.class));
        val amountRoundingSetting = new AmountRoundingSetting(
                AmountUnit.valueOf(kscmtPerCostCal.getCostUnit()),
                AmountRounding.valueOf(kscmtPerCostCal.costRounding)
        );
        val cid = kscmtPerCostCal.pk.companyID;
        val histID = kscmtPerCostCal.pk.histID;
        String queryRate = "SELECT a FROM KscmtPerCostPremiRate a " +
                " WHERE a.pk.companyID = :cid " +
                " AND a.pk.histID = :histID ";
        List<KscmtPerCostPremiRate> listEntityRate = this.queryProxy().query(queryRate, KscmtPerCostPremiRate.class)
                .setParameter("cid", cid)
                .setParameter("histID", histID)
                .getList();

        List<PremiumSetting> premiumSettings = new ArrayList<>();
        if (!listEntityRate.isEmpty()) {
            premiumSettings = listEntityRate.stream().map(e -> new PremiumSetting(
                    e.getPk().companyID,
                    e.getPk().histID,
                    ExtraTimeItemNo.valueOf(e.getPk().premiumNo),
                    new PremiumRate(e.getPremiumRate()),
                    EnumAdaptor.valueOf(e.getUnitPrice(), UnitPrice.class), listEntityAtt(e.getPk().companyID, e.getPk().histID, e.getPk().premiumNo))
            ).collect(Collectors.toList());
        }

        return new PersonCostCalculation(
                new PersonCostRoundingSetting(roundingOfPremium, amountRoundingSetting),
                kscmtPerCostCal.getPk().companyID,
                new Remarks(kscmtPerCostCal.getMemo()),
                premiumSettings,
                Optional.of(EnumAdaptor.valueOf(kscmtPerCostCal.getUnitPriceAtr(), UnitPrice.class)),
                EnumAdaptor.valueOf(kscmtPerCostCal.getUnitPriceSettingMethod(), HowToSetUnitPrice.class),
                new WorkingHoursUnitPrice(kscmtPerCostCal.getWokkingHoursUnitPriceAtr()),
                kscmtPerCostCal.getPk().histID
        );
    }

    /**
     * convert PremiumSetting Entity Object to PremiumSetting Domain Object
     *
     * @return PremiumSetting Domain Object
     */
    private PremiumSetting toDomainPremiumSetting(KscmtPerCostPremiRate kscmtPerCostPremiRate) {
        return new PremiumSetting(
                kscmtPerCostPremiRate.getPk().companyID,
                kscmtPerCostPremiRate.getPk().histID,
                ExtraTimeItemNo.valueOf(kscmtPerCostPremiRate.getPk().premiumNo),
                new PremiumRate(kscmtPerCostPremiRate.getPremiumRate()),
                EnumAdaptor.valueOf(kscmtPerCostPremiRate.getUnitPrice(), UnitPrice.class),
                listEntityAtt(kscmtPerCostPremiRate.getPk().companyID, kscmtPerCostPremiRate.getPk().histID,
                        kscmtPerCostPremiRate.getPk().premiumNo));
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<PersonCostCalculation> findByCompanyIDAndDisplayNumber(String companyID, GeneralDate date) {
        return null;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<PersonCostCalculation> findByCompanyIDAndDisplayNumberNotFull(String companyID, DatePeriod date, List<Integer> itemNos) {
        return null;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<PremiumSetting> findPremiumSettingBy(String companyID, GeneralDate date) {
        return null;
    }


    private List<KmnmtPremiumItem> getPremiumItems(String comId) {
        try (PreparedStatement statement = this.connection().prepareStatement(
                "select * FROM KMNMT_PREMIUM_ITEM where CID = ? ")) {

            statement.setString(1, comId);
            List<KmnmtPremiumItem> krcdtTimeLeaveWorks = new NtsResultSet(statement.executeQuery()).getList(rec -> {
                val entity = new KmnmtPremiumItem();
                entity.kmnmpPremiumItemPK = new KmnmpPremiumItemPK();
                entity.kmnmpPremiumItemPK.companyID = comId;
                entity.kmnmpPremiumItemPK.displayNumber = rec.getInt("PREMIUM_NO");
                entity.name = rec.getString("PREMIUM_NAME");
                entity.useAtr = rec.getInt("USE_ATR");
                return entity;
            });

            return krcdtTimeLeaveWorks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ============================================Update kml001
    @Override
    public Optional<HistPersonCostCalculation> getHistPersonCostCalculation(String cid) {
        String query = "SELECT a FROM KscmtPerCostCal a " +
                " WHERE a.pk.companyID = :cid " +
                "  ORDER BY a.startDate DESC ";
        List<KscmtPerCostCal> listEntity = this.queryProxy().query(query, KscmtPerCostCal.class)
                .setParameter("cid", cid)
                .getList();
        if (!listEntity.isEmpty()) {
            List<DateHistoryItem> historyItems = new ArrayList<>();
            listEntity.forEach((item) -> {
                historyItems.add(new DateHistoryItem(item.pk.histID, new DatePeriod(item.startDate, item.endDate)));
            });
            HistPersonCostCalculation result = new HistPersonCostCalculation(cid, historyItems);
            return Optional.of(result);
        }
        return Optional.empty();
    }

    @Override
    public Optional<PersonCostCalculation> getPersonCost(String cid, String histID) {
        String queryRate = "SELECT a FROM KscmtPerCostPremiRate a " +
                " WHERE a.pk.companyID = :cid " +
                " AND a.pk.histID = :histID ";
        List<KscmtPerCostPremiRate> listEntityRate = this.queryProxy().query(queryRate, KscmtPerCostPremiRate.class)
                .setParameter("cid", cid)
                .setParameter("histID", histID)
                .getList();

        List<PremiumSetting> premiumSettings = new ArrayList<>();
        if (!listEntityRate.isEmpty()) {
            premiumSettings = listEntityRate.stream().map(e -> new PremiumSetting(
                    e.getPk().companyID,
                    e.getPk().histID,
                    ExtraTimeItemNo.valueOf(e.getPk().premiumNo),
                    new PremiumRate(e.getPremiumRate()),
                    EnumAdaptor.valueOf(e.getUnitPrice(), UnitPrice.class), listEntityAtt(e.getPk().companyID, e.getPk().histID, e.getPk().premiumNo))
            ).collect(Collectors.toList());
        }

        String query = "SELECT a FROM KscmtPerCostCal a " +
                " WHERE a.pk.companyID = :cid " +
                " AND a.pk.histID = :histID ";
        Optional<KscmtPerCostCal> listEntityPerCostCal = this.queryProxy().query(query, KscmtPerCostCal.class)
                .setParameter("cid", cid)
                .setParameter("histID", histID)
                .getSingle();
        if (listEntityPerCostCal.isPresent()) {
            val entity = listEntityPerCostCal.get();
            val roundingOfPremium = new UnitPriceRoundingSetting(EnumAdaptor.valueOf(entity.costRounding, UnitPriceRounding.class));
            val amountRoundingSetting = new AmountRoundingSetting(
                    AmountUnit.valueOf(entity.getCostUnit()),
                    AmountRounding.valueOf(entity.costRounding)
            );
            val rs = new PersonCostCalculation(
                    new PersonCostRoundingSetting(roundingOfPremium, amountRoundingSetting),
                    entity.getPk().companyID,
                    new Remarks(entity.getMemo()),
                    premiumSettings,
                    Optional.of(EnumAdaptor.valueOf(entity.getUnitPriceAtr(), UnitPrice.class)),
                    EnumAdaptor.valueOf(entity.getUnitPriceSettingMethod(), HowToSetUnitPrice.class),
                    new WorkingHoursUnitPrice(entity.getWokkingHoursUnitPriceAtr()),
                    entity.getPk().histID
            );
            return Optional.of(rs);
        }
        return Optional.empty();
    }

    @Override
    public void createHistPersonCl(PersonCostCalculation domain, GeneralDate startDate, GeneralDate endDate, String histId) {
        val entityPerCostCal = KscmtPerCostCal.toEntity(domain, startDate, endDate, histId);
        val listEntityRate = KscmtPerCostPremiRate.toEntity(domain, histId);
        val listAtt = KmldtPremiumAttendance.toEntity(domain.getPremiumSettings(), histId);
        this.commandProxy().insertAll(listAtt);
        this.commandProxy().insertAll(listEntityRate);
        this.commandProxy().insert(entityPerCostCal);


    }

    @Override
    public void updateHistPersonCl(HistPersonCostCalculation domain) {
        List<KscmtPerCostCal> updateList = new ArrayList<>();
        domain.getHistoryItems().forEach(i -> {
            val old = this.queryProxy().find(new KscmtPerCostCalPk(domain.getCompanyId(), i.identifier()), KscmtPerCostCal.class);
            old.ifPresent(kscmtHistPersonCostCalculation -> updateList.add(kscmtHistPersonCostCalculation.update(i)));
        });
        this.commandProxy().updateAll(updateList);
    }

    @Override
    public void update(PersonCostCalculation domain, HistPersonCostCalculation domainHist) {
        List<KscmtPerCostCal> updateList = new ArrayList<>();
        domainHist.getHistoryItems().forEach(i -> {
            val oldPerCostCal = this.queryProxy().find(new KscmtPerCostCalPk(domainHist.getCompanyId(), i.identifier()), KscmtPerCostCal.class);
            if (oldPerCostCal.isPresent()) {
                val item = oldPerCostCal.get();
                if (item.getPk().histID.equals(domain.getHistoryID())) {
                    item.setStartDate(i.end());
                    item.setEndDate(i.start());
                    val entity = KscmtPerCostCal.toEntity(domain, i.start(), i.end(), i.identifier());
                    updateList.add(entity);
                }
                item.setEndDate(i.end());
                item.setStartDate(i.start());
                updateList.add(item);
            }
        });
        this.commandProxy().updateAll(updateList);
    }

    @Override
    public List<PremiumSetting> getPersonCostByListHistId(String cid, List<String> histId) {
        String queryRate = "SELECT a FROM KscmtPerCostPremiRate a " +
                " WHERE a.pk.companyID = :cid " +
                " AND a.pk.histID  IN  :histIDs ";
        List<KscmtPerCostPremiRate> listEntityRate = new ArrayList<>();
        CollectionUtil.split(histId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
            listEntityRate.addAll(this.queryProxy().query(queryRate, KscmtPerCostPremiRate.class)
                    .setParameter("cid", cid)
                    .setParameter("histIDs", splitData)
                    .getList());
        });
        List<PremiumSetting> premiumSettings = new ArrayList<>();
        if (!listEntityRate.isEmpty()) {
            premiumSettings = listEntityRate.stream().map(e -> new PremiumSetting(
                    e.getPk().companyID,
                    e.getPk().histID,
                    ExtraTimeItemNo.valueOf(e.getPk().premiumNo),
                    new PremiumRate(e.getPremiumRate()),
                    EnumAdaptor.valueOf(e.getUnitPrice(), UnitPrice.class), listEntityAtt(e.getPk().companyID, e.getPk().histID, e.getPk().premiumNo))
            ).collect(Collectors.toList());
        }
        return premiumSettings;
    }

    @Override
    public HistAnPerCost getHistAnPerCost(String companyID) {
        val result = new HistAnPerCost();
        List<PersonCostCalculation> rs = new ArrayList<>();
        val listEntity = this.queryProxy().query(SEL_BY_CID, KscmtPerCostCal.class).setParameter("companyID", companyID)
                .getList();
        val listHistId = listEntity.stream().map(e -> e.getPk().histID).collect(Collectors.toList());
        val listRate = getPersonCostByListHistId(companyID, listHistId);
        for (KscmtPerCostCal item : listEntity) {
            val listPremiumSetting = listRate.stream().filter(x -> x.getHistoryID().equals(item.pk.histID)).collect(Collectors.toList());
            val sub = toSimpleDomain(item, listPremiumSetting);
            rs.add(sub);
        }
        result.setPersonCostCalculation(rs);
        if (!listEntity.isEmpty()) {
            List<DateHistoryItem> historyItems = new ArrayList<>();
            listEntity.forEach((item) -> {
                historyItems.add(new DateHistoryItem(item.pk.histID, new DatePeriod(item.startDate, item.endDate)));
            });
            HistPersonCostCalculation hist = new HistPersonCostCalculation(companyID, historyItems);
            result.setHistPersonCostCalculation(hist);
            ;
        }

        return result;
    }

    private List<Integer> listEntityAtt(String cid, String historyID, int displayNumber) {
        String queryAtt = "SELECT a FROM KmldtPremiumAttendance a " +
                " WHERE a.kmldpPremiumAttendancePK.companyID = :cid " +
                " AND a.kmldpPremiumAttendancePK.historyID = :historyID " +
                " AND a.kmldpPremiumAttendancePK.displayNumber = :displayNumber ";
        return this.queryProxy().query(queryAtt, KmldtPremiumAttendance.class)
                .setParameter("cid", cid)
                .setParameter("historyID", historyID)
                .setParameter("displayNumber", displayNumber)
                .getList(e -> e.kmldpPremiumAttendancePK.attendanceID);
    }

}
