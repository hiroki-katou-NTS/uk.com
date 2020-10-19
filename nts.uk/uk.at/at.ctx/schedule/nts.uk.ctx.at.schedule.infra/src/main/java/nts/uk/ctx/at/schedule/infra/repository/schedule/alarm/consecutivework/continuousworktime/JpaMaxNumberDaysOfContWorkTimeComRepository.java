package nts.uk.ctx.at.schedule.infra.repository.schedule.alarm.consecutivework.continuousworktime;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompany;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompanyRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.continuousworktime.KscmtAlchkConsecutiveWktmCmp;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.continuousworktime.KscmtAlchkConsecutiveWktmCmpDtl;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hiroko_miura
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaMaxNumberDaysOfContWorkTimeComRepository extends JpaRepository implements MaxDaysOfContinuousWorkTimeCompanyRepository {

    private static String SELECT_HEADER_WHERE_CID = "SELECT * FROM KSCMT_ALCHK_CONSECUTIVE_WKTM_CMP"
            + " WHERE CID = @companyId";

    private static String SELECT_HEADER_WHERE_CID_AND_CODE = SELECT_HEADER_WHERE_CID + " AND CD = @code";

    private static String SELECT_DETAIL_WHERE_CID = "SELECT * FROM KSCMT_ALCHK_CONSECUTIVE_WKTM_CMP_DTL"
            + " WHERE CID = @companyId";

    private static String SELECT_DETAIL_WHERE_CID_AND_CODE = SELECT_DETAIL_WHERE_CID + " AND CD = @code";


    @Override
    public void insert(String companyId, MaxDaysOfContinuousWorkTimeCompany domain) {
        this.commandProxy().insert(KscmtAlchkConsecutiveWktmCmp.of(companyId, domain));
        this.commandProxy().insertAll(KscmtAlchkConsecutiveWktmCmpDtl.toDetailEntityList(companyId, domain));
    }

    @Override
    public void update(String companyId, MaxDaysOfContinuousWorkTimeCompany domain) {
        KscmtAlchkConsecutiveWktmCmp entity = KscmtAlchkConsecutiveWktmCmp.of(companyId, domain);
        entity.contractCd = AppContexts.user().contractCode();
        List<KscmtAlchkConsecutiveWktmCmpDtl> toDetailEntityLis = KscmtAlchkConsecutiveWktmCmpDtl
                .toDetailEntityList(companyId, domain);
        toDetailEntityLis.forEach(item -> item.setContractCd(AppContexts.user().contractCode()));
        this.commandProxy().update(entity);
        this.commandProxy().updateAll(toDetailEntityLis);
    }

    @Override
    public void delete(String companyId, ConsecutiveWorkTimeCode code) {
        Optional<MaxDaysOfContinuousWorkTimeCompany> domain = this.get(companyId, code);

        if (domain.isPresent()) {
            KscmtAlchkConsecutiveWktmCmp entity = KscmtAlchkConsecutiveWktmCmp.of(companyId, domain.get());
            List<KscmtAlchkConsecutiveWktmCmpDtl> dtlEntity = KscmtAlchkConsecutiveWktmCmpDtl.toDetailEntityList(companyId, domain.get());
            this.commandProxy().remove(KscmtAlchkConsecutiveWktmCmp.class, entity.pk);
            dtlEntity.forEach(item -> {
                this.commandProxy().remove(KscmtAlchkConsecutiveWktmCmpDtl.class, item.pk);
            });
        }
    }

    @Override
    public boolean exists(String companyId, ConsecutiveWorkTimeCode code) {
        return this.get(companyId, code).isPresent();
    }

    @Override
    public Optional<MaxDaysOfContinuousWorkTimeCompany> get(String companyId, ConsecutiveWorkTimeCode code) {

        Optional<KscmtAlchkConsecutiveWktmCmp> header = new NtsStatement(SELECT_HEADER_WHERE_CID_AND_CODE, this.jdbcProxy())
                .paramString("companyId", companyId)
                .paramString("code", code.v())
                .getSingle(x -> KscmtAlchkConsecutiveWktmCmp.MAPPER.toEntity(x));

        if (!header.isPresent())
            return Optional.empty();

        List<KscmtAlchkConsecutiveWktmCmpDtl> details = new NtsStatement(SELECT_DETAIL_WHERE_CID_AND_CODE, this.jdbcProxy())
                .paramString("companyId", companyId)
                .paramString("code", code.v())
                .getList(x -> KscmtAlchkConsecutiveWktmCmpDtl.MAPPER.toEntity(x));

        return Optional.of(header.get().toDomain(details));
    }

    @Override
    public List<MaxDaysOfContinuousWorkTimeCompany> getAll(String companyId) {
        List<KscmtAlchkConsecutiveWktmCmp> headers = new NtsStatement(SELECT_HEADER_WHERE_CID, this.jdbcProxy())
                .paramString("companyId", companyId)
                .getList(x -> KscmtAlchkConsecutiveWktmCmp.MAPPER.toEntity(x));

        if (headers.isEmpty())
            return Collections.emptyList();

        List<KscmtAlchkConsecutiveWktmCmpDtl> alldetails = new NtsStatement(SELECT_DETAIL_WHERE_CID, this.jdbcProxy())
                .paramString("companyId", companyId)
                .getList(x -> KscmtAlchkConsecutiveWktmCmpDtl.MAPPER.toEntity(x));


        return headers.stream().map(head -> {

            List<KscmtAlchkConsecutiveWktmCmpDtl> details = alldetails.stream()
                    .filter(d -> d.pk.code == head.pk.code)
                    .collect(Collectors.toList());

            return head.toDomain(details);

        }).collect(Collectors.toList());
    }

}
