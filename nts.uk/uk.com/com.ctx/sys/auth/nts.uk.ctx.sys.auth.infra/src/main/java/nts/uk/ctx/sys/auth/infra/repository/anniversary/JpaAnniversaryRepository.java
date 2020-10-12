package nts.uk.ctx.sys.auth.infra.repository.anniversary;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryNotice;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryRepository;
import nts.uk.ctx.sys.auth.infra.entity.anniversary.BpsdtPsAnniversaryInfo;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaAnniversaryRepository extends JpaRepository implements AnniversaryRepository {

    //select by personal ID
    private static final String SELECT_BY_PERSONAL_ID = "SELECT a FROM BpsdtPsAnniversaryInfo a WHERE a.bpsdtPsAnniversaryInfoPK.personalId =: personalId";

    //select by anniversary
    private static final String SELECT_BY_ANNIVERSARY = "SELECT a FROM BpsdtPsAnniversaryInfo a "
            + " WHERE a.bpsdtPsAnniversaryInfoPK.personalId =: personalId"
            + " AND (CAST(CONCAT(:todayYear,　a.bpsdtPsAnniversaryInfoPK.anniversary)AS datetime2 >= :anniversary AND　CAST(CONCAT(:todayYear,　a.bpsdtPsAnniversaryInfoPK.anniversary)AS datetime2) <= DATEADD(day, a.noticeDay, :anniversary)))"
            + " OR ((CAST(CONCAT(:todayNextYear,　a.bpsdtPsAnniversaryInfoPK.anniversary)AS datetime2) >= anniversary AND　CAST(CONCAT(:todayNextYear,　a.bpsdtPsAnniversaryInfoPK.anniversary)AS datetime2) <= DATEADD(day, a.noticeDay, :anniversary)))";

    //select by date period
    private static final String SELECT_BY_DATE_PERIOD = "SELECT a FROM BpsdtPsAnniversaryInfo a"
            + " WHERE a.bpsdtPsAnniversaryInfoPK.personalId =: personalId"
            + " AND a.bpsdtPsAnniversaryInfoPK.anniversary IN: datePeriod";

    //select by person ID and anniversary
    private static final String SELECT_BY_PERSONAL_ID_AND_ANNIVERSARY = "SELECT a FROM BpsdtPsAnniversaryInfo a"
            + " WHERE a.bpsdtPsAnniversaryInfoPK.personalId =: personalId"
            + " AND a.bpsdtPsAnniversaryInfoPK.anniversary =: anniversary";

    private static BpsdtPsAnniversaryInfo toEntity(AnniversaryNotice domain) {
        BpsdtPsAnniversaryInfo entity = new BpsdtPsAnniversaryInfo();
        domain.setMemento(entity);
        return entity;
    }

    @Override
    public void insert(AnniversaryNotice anniversaryNotice) {
        BpsdtPsAnniversaryInfo entity = JpaAnniversaryRepository.toEntity(anniversaryNotice);
        this.commandProxy().insert(entity);
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
    public void delete(AnniversaryNotice anniversaryNotice) {
        BpsdtPsAnniversaryInfo entity = JpaAnniversaryRepository.toEntity(anniversaryNotice);
        Optional<BpsdtPsAnniversaryInfo> oldEntity = this.queryProxy().find(entity.getBpsdtPsAnniversaryInfoPK(), BpsdtPsAnniversaryInfo.class);
        if (oldEntity.isPresent()) {
            this.commandProxy().remove(oldEntity);
        }
    }

    @Override
    public Optional<AnniversaryNotice> getByPersonalIdAndAnniversary(String personalId, GeneralDate anniversary) {
        return this.queryProxy()
                .query(SELECT_BY_PERSONAL_ID_AND_ANNIVERSARY, BpsdtPsAnniversaryInfo.class)
                .setParameter("personalId", personalId)
                .setParameter("anniversary", anniversary)
                .getSingle(AnniversaryNotice::createFromMemento);
    }

    @Override
    public List<AnniversaryNotice> getByPersonalId(String personalId) {
        return this.queryProxy()
                .query(SELECT_BY_PERSONAL_ID, BpsdtPsAnniversaryInfo.class)
                .setParameter("personalId", personalId)
                .getList(AnniversaryNotice::createFromMemento);
    }

    @Override
    public List<AnniversaryNotice> getTodayAnniversary(GeneralDate anniversary, String loginPersonalId) {
        return this.queryProxy()
                .query(SELECT_BY_ANNIVERSARY, BpsdtPsAnniversaryInfo.class)
                .setParameter("personalId", loginPersonalId)
                .setParameter("anniversary", anniversary)
                .setParameter("todayYear",anniversary.year())
                .setParameter("todayNextYear",anniversary.year()+1)
                .getList(AnniversaryNotice::createFromMemento);
    }

    @Override
    public List<AnniversaryNotice> getByDatePeriod(DatePeriod datePeriod, String loginPersonalId) {
        return this.queryProxy()
                .query(SELECT_BY_DATE_PERIOD, BpsdtPsAnniversaryInfo.class)
                .setParameter("personalId", loginPersonalId)
                .setParameter("datePeriod", datePeriod)
                .getList(AnniversaryNotice::createFromMemento);
    }
}
