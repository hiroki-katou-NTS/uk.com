package nts.uk.ctx.at.record.infra.repository.supportmanagement.supportableemployee;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.infra.entity.supportmanagement.supportableemployee.KshdtSupportTableEmployee;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployeeRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.time.TimeWithDayAttr;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaSupportableEmployeeRepository extends JpaRepository implements SupportableEmployeeRepository {

    private static final String SELECT_BY_LIST_ID;
    private static final String SELECT_BY_SID;
    private static final String SELECT_BY_SIDS;
    private static final String SELECT_BY_SID_AND_DATEPERIOD;
    private static final String SELECT_BY_SIDS_AND_DATEPERIOD;
    private static final String SELECT_BY_RECIPIENT_AND_DATEPERIOD;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT ");
        builderString.append(" FROM KshdtSupportTableEmployee a ");
        builderString.append(" WHERE a.Id IN :listId ");
        SELECT_BY_LIST_ID = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" SELECT ");
        builderString.append(" FROM KshdtSupportTableEmployee a ");
        builderString.append(" WHERE a.employeeId  =:employeeId ");
        SELECT_BY_SID = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" SELECT ");
        builderString.append(" FROM KshdtSupportTableEmployee a ");
        builderString.append(" WHERE a.employeeId IN =:employeeIds ");
        SELECT_BY_SIDS = builderString.toString();

        //$期間.開始日 between 期間.開始日 and 期間.終了日
        //|| $.期間.終了日 between 期間.開始日 and 期間.終了日
        //|| 期間.開始日 between $.期間.開始日 and $.期間.終了日
        //|| 期間.終了日 between $.期間.開始日 and $.期間.終了日
        builderString = new StringBuilder();
        builderString.append(" SELECT ");
        builderString.append(" FROM KshdtSupportTableEmployee a ");
        builderString.append(" WHERE a.employeeId  =:employeeId ");
        builderString.append(" AND ");
        builderString.append(" (( a.startDate  >= :startDate ");
        builderString.append(" AND a.startDate  <= :endDate )");
        builderString.append(" OR  " );
        builderString.append("( a.endDate  >= :startDate  ");
        builderString.append(" AND  a.endDate  <= :endDate )");
        builderString.append(" OR  " );
        builderString.append(" ( a.startDate  <= :startDate ");
        builderString.append(" AND a.endDate  >= :startDate )");
        builderString.append(" OR  " );
        builderString.append("( a.startDate  <= :endDate  ");
        builderString.append(" AND  a.endDate  >= :endDate ))");
        SELECT_BY_SID_AND_DATEPERIOD = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" SELECT ");
        builderString.append(" FROM KshdtSupportTableEmployee a ");
        builderString.append(" WHERE a.employeeId IN  =:employeeIds ");
        builderString.append(" AND ");
        builderString.append(" (( a.startDate  >= :startDate ");
        builderString.append(" AND a.startDate  <= :endDate )");
        builderString.append(" OR  " );
        builderString.append("( a.endDate  >= :startDate  ");
        builderString.append(" AND  a.endDate  <= :endDate )");
        builderString.append(" OR  " );
        builderString.append(" ( a.startDate  <= :startDate ");
        builderString.append(" AND a.endDate  >= :startDate )");
        builderString.append(" OR  " );
        builderString.append("( a.startDate  <= :endDate  ");
        builderString.append(" AND  a.endDate  >= :endDate ))");
        SELECT_BY_SIDS_AND_DATEPERIOD = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" SELECT ");
        builderString.append(" FROM KshdtSupportTableEmployee a ");
        builderString.append(" WHERE a.recipientTargetUnit   =:recipientTargetUnit ");
        builderString.append(" WHERE a.recipientTargetId   =:recipientTargetId ");
        builderString.append(" AND ");
        builderString.append(" (( a.startDate  >= :startDate ");
        builderString.append(" AND a.startDate  <= :endDate )");
        builderString.append(" OR  " );
        builderString.append("( a.endDate  >= :startDate  ");
        builderString.append(" AND  a.endDate  <= :endDate )");
        builderString.append(" OR  " );
        builderString.append(" ( a.startDate  <= :startDate ");
        builderString.append(" AND a.endDate  >= :startDate )");
        builderString.append(" OR  " );
        builderString.append("( a.startDate  <= :endDate  ");
        builderString.append(" AND  a.endDate  >= :endDate ))");
        SELECT_BY_RECIPIENT_AND_DATEPERIOD = builderString.toString();
    }


    @Override
    public void insert(String cid, SupportableEmployee domain) {
        val entity = KshdtSupportTableEmployee.toEntity(domain, cid);
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(String cid, SupportableEmployee domain) {
        val entity = KshdtSupportTableEmployee.toEntity(domain, cid);
        this.commandProxy().update(entity);
    }

    @Override
    public void delete(String id) {
        this.commandProxy().remove(KshdtSupportTableEmployee.class, id);
        this.getEntityManager().flush();

    }

    @Override
    public Optional<SupportableEmployee> get(String id) {
        return this.queryProxy().find(id, KshdtSupportTableEmployee.class)
                .map(this::toDomain);

    }

    @Override
    public List<SupportableEmployee> get(List<String> ids) {
        List<SupportableEmployee> resultList = new ArrayList<>();
        CollectionUtil.split(ids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
            resultList.addAll(this.queryProxy().query(SELECT_BY_LIST_ID, KshdtSupportTableEmployee.class)
                    .setParameter("listId", subList)
                    .getList(this::toDomain));
        });
        return resultList;
    }

    @Override
    public boolean exists(String id) {
        return this.get(id).isPresent();
    }

    @Override
    public List<SupportableEmployee> findByEmployeeId(EmployeeId employeeId) {
        return this.queryProxy().query(SELECT_BY_SID, KshdtSupportTableEmployee.class)
                .setParameter("employeeId", employeeId.v())
                .getList(this::toDomain);
    }

    @Override
    public List<SupportableEmployee> findByEmployeeId(List<EmployeeId> employeeIds) {
        val sids = employeeIds.stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
        List<SupportableEmployee> resultList = new ArrayList<>();
        CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
            resultList.addAll(this.queryProxy().query(SELECT_BY_SIDS, KshdtSupportTableEmployee.class)
                    .setParameter("employeeIds", subList)
                    .getList(this::toDomain));
        });
        return resultList;
    }

    @Override
    public List<SupportableEmployee> findByEmployeeIdWithPeriod(EmployeeId employeeId, DatePeriod period) {
        return this.queryProxy().query(SELECT_BY_SID_AND_DATEPERIOD, KshdtSupportTableEmployee.class)
                .setParameter("employeeId", employeeId.v())
                .setParameter("startDate", period.start())
                .setParameter("endDate", period.end())
                .getList(this::toDomain);
    }

    @Override
    public List<SupportableEmployee> findByEmployeeIdWithPeriod(List<EmployeeId> employeeIds, DatePeriod period) {
        val sids = employeeIds.stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
        List<SupportableEmployee> resultList = new ArrayList<>();
        CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
            resultList.addAll(this.queryProxy().query(SELECT_BY_SIDS_AND_DATEPERIOD, KshdtSupportTableEmployee.class)
                    .setParameter("employeeIds", subList)
                    .setParameter("startDate", period.start())
                    .setParameter("endDate", period.end())
                    .getList(this::toDomain));
        });
        return resultList;
    }

    @Override
    public List<SupportableEmployee> findByRecipientWithPeriod(TargetOrgIdenInfor recipient, DatePeriod period) {
        val unit = recipient.getUnit().value;
        val workplaceId = recipient.getWorkplaceId().isPresent() ? recipient.getWorkplaceId().get() : null;
        val workplaceGroupId = recipient.getWorkplaceGroupId().isPresent() ? recipient.getWorkplaceGroupId().get() : null;
        val isWPLGroup = recipient.getUnit().value == TargetOrganizationUnit.WORKPLACE_GROUP.value;
        return this.queryProxy().query(SELECT_BY_RECIPIENT_AND_DATEPERIOD, KshdtSupportTableEmployee.class)
                .setParameter("recipientTargetUnit", unit)
                .setParameter("recipientTargetId", isWPLGroup ? workplaceGroupId : workplaceId)
                .setParameter("startDate", period.start())
                .setParameter("endDate", period.end())
                .getList(this::toDomain);
    }

    private SupportableEmployee toDomain(KshdtSupportTableEmployee entity) {
        String recipientId = entity.getRecipientTargetId();
        val recipientUnit = EnumAdaptor.valueOf(entity.getRecipientTargetUnit(), TargetOrganizationUnit.class);
        TargetOrgIdenInfor targetRecipient = TargetOrgIdenInfor.createFromTargetUnit(recipientUnit, recipientId);
        Optional<TimeSpanForCalc> timespan = Optional.empty();
        if (entity.endTs != null && entity.startTs != null) {
            timespan = Optional.of(new TimeSpanForCalc(new TimeWithDayAttr(entity.startTs), new TimeWithDayAttr(entity.endTs)));
        }
        return new SupportableEmployee(
                entity.getId(),
                new EmployeeId(entity.getEmployeeId()),
                targetRecipient,
                EnumAdaptor.valueOf(entity.getSupportType(), SupportType.class),
                new DatePeriod(entity.startDate, entity.endDate),
                timespan
        );


    }

}
