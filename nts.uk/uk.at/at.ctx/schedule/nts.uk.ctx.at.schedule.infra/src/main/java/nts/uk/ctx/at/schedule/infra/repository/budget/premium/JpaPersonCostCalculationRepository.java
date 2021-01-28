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
import nts.uk.ctx.at.schedule.dom.budget.premium.service.PersonCostCalAndDateDto;
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

    private static final String SEL_BY_CID = "SELECT a FROM KscmtPerCostCalc a WHERE a.pk.companyID = :companyID ";
    private static final String SEL_BY_CID_HIST = "SELECT a FROM KscmtPerCostCalcHist a WHERE a.pk.companyID = :companyID " +
            "ORDER BY a.startDate ASC";
    private static final String SEL_BY_HIST_ = "SELECT a FROM KscmtPerCostCalcHist a WHERE a.pk.companyID = :companyID " +
            "AND a.pk.histID IN :historyIDs";
    private static final String SEL_ITEM_BY_HID = "SELECT a FROM KscmtPerCostCalc a WHERE a.pk.companyID = :companyID " +
            "AND a.pk.histID = :historyID";
    private static final String SEL_PREMIIUM = "SELECT a FROM KscmtPerCostPremium a " +
            " WHERE a.kscmtPerCostPremiumPk.companyID = :cid " +
            " AND a.kscmtPerCostPremiumPk.historyID = :historyID " +
            " AND a.kscmtPerCostPremiumPk.displayNumber IN :displayNumbers";
    private static final String SEL_PREMI_RATE = "SELECT a FROM KscmtPerCostPremiRate a " +
            " WHERE a.pk.companyID = :cid " +
            " AND a.pk.histID  =  :histIDs " +
            " AND a.pk.premiumNo  IN  :listItemNos ";
    private static final String SEL_PER_COST = "SELECT a FROM KscmtPerCostPremium a " +
            " WHERE a.kscmtPerCostPremiumPk.companyID = :cid " +
            " AND a.kscmtPerCostPremiumPk.historyID = :historyID " +
            " AND a.kscmtPerCostPremiumPk.displayNumber = :displayNumber ";

    private static final String SEL_PER_COST_IN_LIST = "SELECT a FROM KscmtPerCostPremiRate a " +
            " WHERE a.pk.companyID = :cid " +
            " AND a.pk.histID  IN  :histIDs ";

    private static final String SEL_HIST_BY_CID_AND_HISTID = "SELECT a FROM KscmtPerCostCalcHist a " +
            " WHERE a.pk.companyID = :cid " +
            " AND a.pk.histID = :histID ";

    private static final String SEL_PER_BY_CID_AND_HISTID = "SELECT a FROM KscmtPerCostCalc a " +
            " WHERE a.pk.companyID = :cid " +
            " AND a.pk.histID = :histID ";
    private static final String SEL_PER_RATE_BY_CID_AND_HISTID = "SELECT a FROM KscmtPerCostPremiRate a " +
            " WHERE a.pk.companyID = :cid " +
            " AND a.pk.histID = :histID ";

    private static final String SEL_HIST_BY_CID = "SELECT a FROM KscmtPerCostCalcHist a " +
            " WHERE a.pk.companyID = :cid " +
            "  ORDER BY a.startDate DESC ";

    @Override
    public void add(PersonCostCalculation personCostCalculation) {
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<PersonCostCalculation> findByCompanyID(String companyID) {
        List<PersonCostCalculation> rs = new ArrayList<>();
        val listEntity = this.queryProxy().query(SEL_BY_CID, KscmtPerCostCalc.class).setParameter("companyID", companyID)
                .getList();
        val listHistId = listEntity.stream().map(e -> e.getPk().histID).collect(Collectors.toList());
        val listRate = getPersonCostByListHistId(companyID, listHistId);
        for (KscmtPerCostCalc item : listEntity) {
            val listPremiumSetting = listRate.stream().filter(x -> x.getHistoryID().equals(item.pk.histID)).collect(Collectors.toList());
            val sub = toSimpleDomain(item, listPremiumSetting);
            rs.add(sub);
        }
        return rs;
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public Optional<PersonCostCalculation> findItemByHistoryID(String companyID, String historyID) {
        return this.queryProxy().query(SEL_ITEM_BY_HID, KscmtPerCostCalc.class).setParameter("companyID", companyID).setParameter("historyID", historyID)
                .getSingle().map(x -> toDomainPersonCostCalculation(x));
    }


    @Override
    public void delete(String companyId, String historyId) {
        this.commandProxy().remove(KscmtPerCostCalc.class, new KscmtPerCostCalcPk(companyId, historyId));
        this.commandProxy().remove(KscmtPerCostCalcHist.class, new KscmtPerCostCalcHistPk(companyId, historyId));
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
        String queryPremiumAttendance = "SELECT a FROM KscmtPerCostPremium a " +
                " WHERE a.kscmtPerCostPremiumPk.companyID = :cid " +
                " AND a.kscmtPerCostPremiumPk.historyID = :histID ";
        List<KscmtPerCostPremium> listEntityPremiumAttendance = this.queryProxy().query(queryPremiumAttendance, KscmtPerCostPremium.class)
                .setParameter("cid", companyId)
                .setParameter("histID", historyId)
                .getList();
        if (!listEntityRate.isEmpty()) {
            this.commandProxy().removeAll(listEntityPremiumAttendance);
        }

    }

    private PersonCostCalculation toSimpleDomain(KscmtPerCostCalc kscmtPerCostCalc, List<PremiumSetting> premiumSetting) {

        val roundingOfPremium = new UnitPriceRoundingSetting(EnumAdaptor.valueOf(kscmtPerCostCalc.unitPriceRounding, UnitPriceRounding.class));
        val amountRoundingSetting = new AmountRoundingSetting(
                AmountUnit.valueOf(converAmountRounding(kscmtPerCostCalc.getCostUnit())),
                AmountRounding.valueOf(kscmtPerCostCalc.costRounding)
        );
        val utr = kscmtPerCostCalc.getUnitPriceAtr();
        return new PersonCostCalculation(
                new PersonCostRoundingSetting(roundingOfPremium, amountRoundingSetting),
                kscmtPerCostCalc.getPk().companyID,
                new Remarks(kscmtPerCostCalc.getMemo()),
                premiumSetting,
                utr!=null?Optional.of(EnumAdaptor.valueOf(utr, UnitPrice.class)):Optional.empty(),
                EnumAdaptor.valueOf(kscmtPerCostCalc.getUnitPriceSettingMethod(), HowToSetUnitPrice.class),
                new WorkingHoursUnitPrice(kscmtPerCostCalc.getWorkingHoursUnitPriceAtr()),
                kscmtPerCostCalc.getPk().histID
        );

    }

    /**
     * convert PersonCostCalculation Entity Object to PersonCostCalculation Domain Object
     *
     * @return PersonCostCalculation Domain Object
     */
    private PersonCostCalculation toDomainPersonCostCalculation(KscmtPerCostCalc kscmtPerCostCalc) {
        val roundingOfPremium = new UnitPriceRoundingSetting(EnumAdaptor.valueOf(kscmtPerCostCalc.unitPriceRounding, UnitPriceRounding.class));
        val amountRoundingSetting = new AmountRoundingSetting(
                AmountUnit.valueOf(converAmountRounding(kscmtPerCostCalc.getCostUnit())),
                AmountRounding.valueOf(kscmtPerCostCalc.costRounding)
        );
        val cid = kscmtPerCostCalc.pk.companyID;
        val histID = kscmtPerCostCalc.pk.histID;
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
        val utr = kscmtPerCostCalc.getUnitPriceAtr();
        return new PersonCostCalculation(
                new PersonCostRoundingSetting(roundingOfPremium, amountRoundingSetting),
                kscmtPerCostCalc.getPk().companyID,
                new Remarks(kscmtPerCostCalc.getMemo()),
                premiumSettings,
                utr !=null ?Optional.of(EnumAdaptor.valueOf(utr, UnitPrice.class)):Optional.empty(),
                EnumAdaptor.valueOf(kscmtPerCostCalc.getUnitPriceSettingMethod(), HowToSetUnitPrice.class),
                new WorkingHoursUnitPrice(kscmtPerCostCalc.getWorkingHoursUnitPriceAtr()),
                kscmtPerCostCalc.getPk().histID
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

        List<KscmtPerCostCalcHist> listEntity = this.queryProxy().query(SEL_HIST_BY_CID, KscmtPerCostCalcHist.class)
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
    public Optional<PersonCostCalAndDateDto> getPersonCost(String cid, String histID) {
        val rs = new PersonCostCalAndDateDto();
        List<KscmtPerCostPremiRate> listEntityRate = this.queryProxy().query(SEL_PER_RATE_BY_CID_AND_HISTID, KscmtPerCostPremiRate.class)
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

        Optional<KscmtPerCostCalc> listEntityPerCostCal = this.queryProxy().query(SEL_PER_BY_CID_AND_HISTID, KscmtPerCostCalc.class)
                .setParameter("cid", cid)
                .setParameter("histID", histID)
                .getSingle();

        Optional<KscmtPerCostCalcHist> listEntityPerCostCalHist = this.queryProxy().query(SEL_HIST_BY_CID_AND_HISTID, KscmtPerCostCalcHist.class)
                .setParameter("cid", cid)
                .setParameter("histID", histID)
                .getSingle();
        if (listEntityPerCostCal.isPresent() && listEntityPerCostCalHist.isPresent()) {
            val entity = listEntityPerCostCal.get();
            val entityHist = listEntityPerCostCalHist.get();
            val roundingOfPremium = new UnitPriceRoundingSetting(EnumAdaptor.valueOf(entity.unitPriceRounding, UnitPriceRounding.class));
            val amountRoundingSetting = new AmountRoundingSetting(
                    AmountUnit.valueOf(converAmountRounding(entity.getCostUnit())),
                    AmountRounding.valueOf(entity.costRounding)
            );
            val utr = entity.getUnitPriceAtr();
            val per = new PersonCostCalculation(
                    new PersonCostRoundingSetting(roundingOfPremium, amountRoundingSetting),
                    entity.getPk().companyID,
                    new Remarks(entity.getMemo()),
                    premiumSettings,
                    utr != null?Optional.of(EnumAdaptor.valueOf(utr, UnitPrice.class)):Optional.empty(),
                    EnumAdaptor.valueOf(entity.getUnitPriceSettingMethod(), HowToSetUnitPrice.class),
                    new WorkingHoursUnitPrice(entity.getWorkingHoursUnitPriceAtr()),
                    entity.getPk().histID
            );
            rs.setPersonCostCalculation(per);
            rs.setStartDate(entityHist.startDate);
            rs.setEndDate(entityHist.endDate);
            return Optional.of(rs);
        }
        return Optional.empty();
    }

    private int converAmountRounding(int unit) {
        switch (unit) {
            case 0:
                return 1;
            case 1:
                return 10;
            case 2:
                return 100;
            case 3:
                return 1000;
            default:
                throw new RuntimeException("invalid value");
        }
    }

    @Override
    public void createHistPersonCl(PersonCostCalculation domain, GeneralDate startDate, GeneralDate endDate, String histId) {

        val entityPerCostCal = KscmtPerCostCalc.toEntity(domain, histId);
        val listEntityRate = KscmtPerCostPremiRate.toEntity(domain, histId);
        val listAtt = KscmtPerCostPremium.toEntity(domain.getPremiumSettings(), histId);
        val listHist = KscmtPerCostCalcHist.toEntity(startDate, endDate, histId, domain.getCompanyID());
        this.commandProxy().insertAll(listAtt);
        this.commandProxy().insertAll(listEntityRate);
        this.commandProxy().insert(entityPerCostCal);
        this.commandProxy().insert(listHist);

    }

    @Override
    public void updateHistPersonCl(HistPersonCostCalculation domain) {
        List<KscmtPerCostCalcHist> updateList = new ArrayList<>();
        domain.getHistoryItems().forEach(i -> {
            val old = this.queryProxy().find(new KscmtPerCostCalcHistPk(domain.getCompanyId(), i.identifier()), KscmtPerCostCalcHist.class);
            old.ifPresent(kscmtHist -> updateList.add(kscmtHist.update(i)));
        });
        this.commandProxy().updateAll(updateList);
    }

    @Override
    public void update(PersonCostCalculation domain, HistPersonCostCalculation domainHist) {
        val histId = domain.getHistoryID();
        val cid = domain.getCompanyID();
        List<KscmtPerCostCalcHist> updateListHist = new ArrayList<>();
        if (!domain.getPremiumSettings().isEmpty()) {
            updatePerCostPremiRate(domain, histId);
            updateKmldtPremiumAttendance(domain, histId);
            val entityPer = this.queryProxy().find(new KscmtPerCostCalcPk(domain.getCompanyID(), domain.getHistoryID()), KscmtPerCostCalc.class);
            val entity = KscmtPerCostCalc.toEntity(domain, domain.getHistoryID());
            if (entityPer.isPresent()) {

                this.commandProxy().update(entity);
            }else {
                this.commandProxy().insert(entity);
            }
        }
        if (!domainHist.getHistoryItems().isEmpty()) {
            val historyIDs = new ArrayList<>();
            domainHist.getHistoryItems().forEach(e -> {
                updateListHist.add(KscmtPerCostCalcHist.toEntity(e.start(), e.end(), e.identifier(), cid));
                historyIDs.add(e.identifier());
            });
            List<KscmtPerCostCalcHist> entityOld = new ArrayList<>();
            CollectionUtil.split(historyIDs, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
                entityOld.addAll(this.queryProxy().query(SEL_BY_HIST_, KscmtPerCostCalcHist.class)
                        .setParameter("companyID", cid)
                        .setParameter("historyIDs", splitData)
                        .getList());
            });
            if (!entityOld.isEmpty()) {
                this.commandProxy().removeAll(entityOld);
                this.getEntityManager().flush();
                this.commandProxy().insertAll(updateListHist);
            }
        }
    }

    @Override
    public List<PremiumSetting> getPersonCostByListHistId(String cid, List<String> histId) {
        List<KscmtPerCostPremiRate> listEntityRate = new ArrayList<>();
        CollectionUtil.split(histId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
            listEntityRate.addAll(this.queryProxy().query(SEL_PER_COST_IN_LIST, KscmtPerCostPremiRate.class)
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
        val listEntity = this.queryProxy().query(SEL_BY_CID, KscmtPerCostCalc.class).setParameter("companyID", companyID)
                .getList();
        val listEntityHist = this.queryProxy().query(SEL_BY_CID_HIST, KscmtPerCostCalcHist.class).setParameter("companyID", companyID)
                .getList();
        val listHistId = listEntity.stream().map(e -> e.getPk().histID).collect(Collectors.toList());
        val listRate = getPersonCostByListHistId(companyID, listHistId);
        for (KscmtPerCostCalc item : listEntity) {
            val listPremiumSetting = listRate.stream().filter(x -> x.getHistoryID().equals(item.pk.histID)).collect(Collectors.toList());
            if (listPremiumSetting.isEmpty()) continue;
            val sub = toSimpleDomain(item, listPremiumSetting);
            rs.add(sub);
        }
        result.setPersonCostCalculation(rs);
        if (!listEntityHist.isEmpty()) {
            List<DateHistoryItem> historyItems = new ArrayList<>();
            listEntityHist.forEach((item) -> {
                historyItems.add(new DateHistoryItem(item.pk.histID, new DatePeriod(item.startDate, item.endDate)));
            });
            HistPersonCostCalculation hist = new HistPersonCostCalculation(companyID, historyItems);
            result.setHistPersonCostCalculation(hist);
        }
        return result;
    }

    private List<Integer> listEntityAtt(String cid, String historyID, int displayNumber) {
        return this.queryProxy().query(SEL_PER_COST, KscmtPerCostPremium.class)
                .setParameter("cid", cid)
                .setParameter("historyID", historyID)
                .setParameter("displayNumber", displayNumber)
                .getList(e -> e.kscmtPerCostPremiumPk.attendanceID);
    }

    private void updatePerCostPremiRate(PersonCostCalculation domain, String histId) {
        val listItemNos = domain.getPremiumSettings().stream().map(e -> e.getID().value).collect(Collectors.toList());
        List<KscmtPerCostPremiRate> listEntityRate = new ArrayList<>();
        CollectionUtil.split(listItemNos, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
            listEntityRate.addAll(this.queryProxy().query(SEL_PREMI_RATE, KscmtPerCostPremiRate.class)
                    .setParameter("cid", domain.getCompanyID())
                    .setParameter("histIDs", histId)
                    .setParameter("listItemNos", splitData)
                    .getList());
        });
        if (!listEntityRate.isEmpty()) {
            this.commandProxy().removeAll(listEntityRate);
            this.getEntityManager().flush();
        }
        this.commandProxy().insertAll(KscmtPerCostPremiRate.toEntity(domain, histId));
    }

    private void updateKmldtPremiumAttendance(PersonCostCalculation domain, String histId) {
        val cid = domain.getCompanyID();
        val displayNumber = domain.getPremiumSettings().stream().map(e -> e.getID().value).collect(Collectors.toList());
        List<KscmtPerCostPremium> listEntity = new ArrayList<>();
        CollectionUtil.split(displayNumber, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
            listEntity.addAll(this.queryProxy().query(SEL_PREMIIUM, KscmtPerCostPremium.class)
                    .setParameter("cid", cid)
                    .setParameter("historyID", histId)
                    .setParameter("displayNumbers", displayNumber)
                    .getList());
        });
        if (!listEntity.isEmpty()) {
            this.commandProxy().removeAll(listEntity);
            this.getEntityManager().flush();
        }
        this.commandProxy().insertAll(KscmtPerCostPremium.toEntity(domain.getPremiumSettings(), histId));
    }
}
