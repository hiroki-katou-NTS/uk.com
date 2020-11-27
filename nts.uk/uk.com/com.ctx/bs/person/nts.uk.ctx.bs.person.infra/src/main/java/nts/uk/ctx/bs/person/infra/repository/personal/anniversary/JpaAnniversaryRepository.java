package nts.uk.ctx.bs.person.infra.repository.personal.anniversary;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.person.dom.person.personal.anniversary.AnniversaryNotice;
import nts.uk.ctx.bs.person.dom.person.personal.anniversary.AnniversaryRepository;
import nts.uk.ctx.bs.person.infra.entity.person.anniversary.BpsdtPsAnniversaryInfo;
import nts.uk.ctx.bs.person.infra.entity.person.anniversary.BpsdtPsAnniversaryInfoPK;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaAnniversaryRepository extends JpaRepository implements AnniversaryRepository {

    //select by personal ID
    private static final String SELECT_BY_PERSONAL_ID = "SELECT a FROM BpsdtPsAnniversaryInfo a WHERE a.bpsdtPsAnniversaryInfoPK.personalId = :personalId";

    //select by anniversary
    private static final String NATIVE_SELECT_BY_ANNIVERSARY = "SELECT * FROM BPSDT_PS_ANNIVERSARY_INFO as a"
            + " WHERE a.PID ='{:personId}'"
            + " AND CAST(CONCAT('{:todayYear}',a.ANNIVERSARY_DATE) as datetime2) >= CAST('{:anniversary}' as datetime2)"
            + " AND CAST(CONCAT('{:todayYear}',a.ANNIVERSARY_DATE) as datetime2) <= DATEADD(day,　a.NOTIFICATION_DAYS,　CAST('{:anniversary}' as datetime2))"
            + " OR("
            + " CAST(CONCAT('{:todayNextYear}',　a.ANNIVERSARY_DATE) AS datetime2) >= CAST('{:anniversary}' as datetime2)"
            + " AND CAST(CONCAT('{:todayNextYear}',a.ANNIVERSARY_DATE) AS datetime2) <= DATEADD(day,　a.NOTIFICATION_DAYS,　CAST('{:anniversary}' as datetime2))"
            + " )";

    //select by date period
    private static final String SELECT_BY_DATE_PERIOD = "SELECT a FROM BpsdtPsAnniversaryInfo a"
            + " WHERE a.bpsdtPsAnniversaryInfoPK.personalId = :personalId"
            + " AND :start <= a.bpsdtPsAnniversaryInfoPK.anniversary"
            + " AND a.bpsdtPsAnniversaryInfoPK.anniversary <= :end";

    //select by person ID and anniversary
    private static final String SELECT_BY_PERSONAL_ID_AND_ANNIVERSARY = "SELECT a FROM BpsdtPsAnniversaryInfo a"
            + " WHERE a.bpsdtPsAnniversaryInfoPK.personalId = :personalId"
            + " AND a.bpsdtPsAnniversaryInfoPK.anniversary = :anniversary";

    private static BpsdtPsAnniversaryInfo toEntity(AnniversaryNotice domain) {
        BpsdtPsAnniversaryInfo entity = new BpsdtPsAnniversaryInfo();
        domain.setMemento(entity);
        return entity;
    }

    @Override
    public void insert(AnniversaryNotice anniversaryNotice) {
        BpsdtPsAnniversaryInfo entity = JpaAnniversaryRepository.toEntity(anniversaryNotice);
        entity.setContractCd(AppContexts.user().contractCode());
        this.commandProxy().insert(entity);
    }

    @Override
    public void insertAll(List<AnniversaryNotice> anniversaryNotice) {
        List<BpsdtPsAnniversaryInfo> entities = anniversaryNotice.stream()
                .map(item -> {
                    BpsdtPsAnniversaryInfo entity = JpaAnniversaryRepository.toEntity(item);
                    entity.setContractCd(AppContexts.user().contractCode());
                    return entity;
                })
                .collect(Collectors.toList());
        this.commandProxy().insertAll(entities);
    }

    @Override
    public void update(AnniversaryNotice anniversaryNotice) {
        BpsdtPsAnniversaryInfo entity = JpaAnniversaryRepository.toEntity(anniversaryNotice);
        Optional<BpsdtPsAnniversaryInfo> oldEntity = this.queryProxy().find(entity.getBpsdtPsAnniversaryInfoPK(), BpsdtPsAnniversaryInfo.class);
        if (oldEntity.isPresent()) {
            BpsdtPsAnniversaryInfo updateEntity = oldEntity.get();
            updateEntity.setNoticeDay(entity.getNoticeDay());
            updateEntity.setSeenDate(entity.getSeenDate());
            updateEntity.setAnniversaryTitle(entity.getAnniversaryTitle());
            updateEntity.setNotificationMessage(entity.getNotificationMessage());
            this.commandProxy().update(updateEntity);
        }
    }

    @Override
    public void updateAll(List<AnniversaryNotice> anniversaryNotice) {
        List<BpsdtPsAnniversaryInfo> entities = anniversaryNotice.stream()
                .map(item -> {
                    BpsdtPsAnniversaryInfo entity = JpaAnniversaryRepository.toEntity(item);
                    Optional<BpsdtPsAnniversaryInfo> oldEntity = this.queryProxy().find(entity.getBpsdtPsAnniversaryInfoPK(), BpsdtPsAnniversaryInfo.class);
                    if (oldEntity.isPresent()) {
                        BpsdtPsAnniversaryInfo updateEntity = oldEntity.get();
                        updateEntity.setNoticeDay(entity.getNoticeDay());
                        updateEntity.setSeenDate(entity.getSeenDate());
                        updateEntity.setAnniversaryTitle(entity.getAnniversaryTitle());
                        updateEntity.setNotificationMessage(entity.getNotificationMessage());
                        return updateEntity;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        this.commandProxy().updateAll(entities);
    }

    @Override
    public void delete(AnniversaryNotice anniversaryNotice) {
        BpsdtPsAnniversaryInfo entity = JpaAnniversaryRepository.toEntity(anniversaryNotice);
        Optional<BpsdtPsAnniversaryInfo> oldEntity = this.queryProxy().find(entity.getBpsdtPsAnniversaryInfoPK(), BpsdtPsAnniversaryInfo.class);
        oldEntity.ifPresent(e -> this.commandProxy().remove(e));
    }

    @Override
    public void deleteAll(List<AnniversaryNotice> anniversaryNotice) {
        List<BpsdtPsAnniversaryInfo> entities = anniversaryNotice.stream()
                .map(item -> {
                    BpsdtPsAnniversaryInfo entity = JpaAnniversaryRepository.toEntity(item);
                    Optional<BpsdtPsAnniversaryInfo> oldEntity = this.queryProxy().find(entity.getBpsdtPsAnniversaryInfoPK(), BpsdtPsAnniversaryInfo.class);
                    return oldEntity.orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        this.commandProxy().removeAll(entities);
    }

    @Override
    public Optional<AnniversaryNotice> getByPersonalIdAndAnniversary(String personalId, String anniversary) {
        return this.queryProxy()
                .query(SELECT_BY_PERSONAL_ID_AND_ANNIVERSARY, BpsdtPsAnniversaryInfo.class)
                .setParameter("personalId", personalId)
                .setParameter("anniversary", anniversary)
                .getSingle(AnniversaryNotice::createFromMemento);
    }

    @Override
    public List<AnniversaryNotice> getByPersonalId(String personalId) {
    	List<AnniversaryNotice> list = this.queryProxy()
                .query(SELECT_BY_PERSONAL_ID, BpsdtPsAnniversaryInfo.class)
                .setParameter("personalId", personalId)
                .getList(AnniversaryNotice::createFromMemento);
        list.sort(Comparator.comparing(AnniversaryNotice::getAnniversary));
        return list;
    }

    @Override
    public List<AnniversaryNotice> getTodayAnniversary(GeneralDate anniversary) {
        String loginPersonalId = AppContexts.user().personId();
        String query = NATIVE_SELECT_BY_ANNIVERSARY
                .replace("{:personId}", loginPersonalId)
                .replace("{:anniversary}", anniversary.toString())
                .replace("{:todayYear}", String.valueOf(anniversary.year()))
                .replace("{:todayNextYear}", String.valueOf(anniversary.year() + 1));

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = getEntityManager().createNativeQuery(query).getResultList();
        List<AnniversaryNotice> list = resultList.stream()
                .map(item -> {
                    //fill entity
                    BpsdtPsAnniversaryInfo entity = new BpsdtPsAnniversaryInfo();
                    entity.setVersion(Long.parseLong(item[8].toString()));
                    entity.setContractCd(item[9].toString());
                    entity.setBpsdtPsAnniversaryInfoPK(new BpsdtPsAnniversaryInfoPK(item[10].toString(),item[11].toString()));
                    entity.setAnniversaryTitle(item[12].toString());
                    entity.setNotificationMessage(item[13].toString());
                    entity.setNoticeDay(Integer.parseInt(item[14].toString()));
                    entity.setSeenDate(GeneralDate.fromString(item[15].toString(), "yyyy-MM-dd hh:mm:ss.S"));
                    //create domain
                    AnniversaryNotice domain = new AnniversaryNotice();
                    domain.getMemento(entity);
                    return domain;
                })
                .collect(Collectors.toList());
        list.sort(Comparator.comparing(AnniversaryNotice::getAnniversary));
        return list;
    }

    @Override
    public List<AnniversaryNotice> getByDatePeriod(DatePeriod datePeriod) {
        String loginPersonalId = AppContexts.user().personId();
        List<AnniversaryNotice> list = this.queryProxy()
                .query(SELECT_BY_DATE_PERIOD, BpsdtPsAnniversaryInfo.class)
                .setParameter("personalId", loginPersonalId)
                .setParameter("start", datePeriod.start().month() + "" + datePeriod.start().day())
                .setParameter("end", datePeriod.end().month() + "" + datePeriod.end().day())
                .getList(AnniversaryNotice::createFromMemento);
        list.sort(Comparator.comparing(AnniversaryNotice::getAnniversary));
        return list;
    }
}
