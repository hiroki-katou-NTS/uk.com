package nts.uk.ctx.at.request.infra.repository.application.optional;


import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplicationRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemApplicationTypeCode;
import nts.uk.ctx.at.request.infra.entity.application.optional.KrqdtAppAnyv;
import nts.uk.ctx.at.request.infra.entity.application.optional.KrqdtAppAnyvPk;
import nts.uk.ctx.at.shared.dom.scherec.anyitem.AnyItemNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemAmount;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class JpaOptionalItemApplicationRepository extends JpaRepository implements OptionalItemApplicationRepository {

    private static final String FIND_BY_APP_ID = "SELECT *  "
            + "FROM KRQDT_APP_ANYV as a INNER JOIN KRQDT_APPLICATION as b ON a.APP_ID = b.APP_ID"
            + " WHERE a.APP_ID = @appID AND a.CID = @companyId";

    private static final String FIND_ENTITY = "SELECT a FROM KrqdtAppAnyv a where a.KrqdtAppAnyvPk.companyId = :cid" +
            " and a.KrqdtAppAnyvPk.appId = :appId";

    @Override
    public void save(OptionalItemApplication optItemApp) {
        List<KrqdtAppAnyv> entities = toEntity(optItemApp);
        this.commandProxy().insertAll(entities);
    }

    @Override
    public void update(OptionalItemApplication domain) {
        String cid = AppContexts.user().companyId();
        Map<Integer, KrqdtAppAnyv> entityMap = this.queryProxy().query(FIND_ENTITY, KrqdtAppAnyv.class)
                .setParameter("cid", cid)
                .setParameter("appId", domain.getAppID())
                .getList().stream().collect(Collectors.toMap(x -> x.getKrqdtAppAnyvPk().anyvNo, x -> x));
        System.out.println("appId " + domain.getAppID());
        System.out.println("anyvCd " + domain.getCode().v());
        System.out.println("size ---------------" + entityMap.size());
        domain.getOptionalItems().forEach(item -> {
            KrqdtAppAnyv krqdtAppAnyv = entityMap.get(item.getItemNo().v() + 640);
            krqdtAppAnyv.setTimes(item.getTimes().isPresent() ? item.getTimes().get().v() : null);
            krqdtAppAnyv.setTime(item.getTime().isPresent() ? item.getTime().get().v() : null);
            krqdtAppAnyv.setMoneyValue(item.getAmount().isPresent() ? item.getAmount().get().v() : null);
        });
        this.commandProxy().updateAll(entityMap.values());
    }

    @Override
    public Optional<OptionalItemApplication> getByAppId(String companyId, String appId) {
        List<OptionalItemApplication> optionalItemApplications = new NtsStatement(FIND_BY_APP_ID, this.jdbcProxy())
                .paramString("appID", appId)
                .paramString("companyId", companyId)
                .getList(c -> toDomain(c));
        Optional<OptionalItemApplication> optionalItemApplication = optionalItemApplications.stream().findFirst();
        if (optionalItemApplication.isPresent()) {
            List<AnyItemValue> collect = optionalItemApplications.stream().map(item -> item.getOptionalItems().get(0)).collect(Collectors.toList());
            optionalItemApplication.get().setOptionalItems(collect);
        }
        return optionalItemApplication;
    }

    private OptionalItemApplication toDomain(NtsResultRecord res) {
        AnyItemValue anyItemValue = new AnyItemValue(new AnyItemNo(res.getInt("ANYV_NO")),
                Optional.ofNullable(res.getBigDecimal("COUNT_VAL") != null ? new AnyItemTimes(res.getBigDecimal("COUNT_VAL")) : null),
                Optional.ofNullable(res.getInt("MONEY_VAL") != null ? new AnyItemAmount(res.getInt("MONEY_VAL")) : null),
                Optional.ofNullable(res.getInt("TIME_VAL") != null ? new AnyItemTime(res.getInt("TIME_VAL")) : null)
        );
        OptionalItemApplication application = new OptionalItemApplication(new OptionalItemApplicationTypeCode(res.getString("ANYV_CD")), Arrays.asList(anyItemValue));
        application.setAppType(EnumAdaptor.valueOf(res.getInt("APP_TYPE"), ApplicationType.class));
        return application;
    }

    private List<KrqdtAppAnyv> toEntity(OptionalItemApplication domain) {
        String cid = AppContexts.user().companyId();
        List<KrqdtAppAnyv> entities = new ArrayList<>();
        domain.getOptionalItems().forEach(anyItemValue -> {
            KrqdtAppAnyv entity = new KrqdtAppAnyv();
            entity.setKrqdtAppAnyvPk(new KrqdtAppAnyvPk(cid, domain.getAppID(), domain.getCode().v(), anyItemValue.getItemNo().v() + 640));
            entity.setTimes(anyItemValue.getTimes().isPresent() ? anyItemValue.getTimes().get().v() : null);
            entity.setTime(anyItemValue.getTime().isPresent() ? anyItemValue.getTime().get().v() : null);
            entity.setMoneyValue(anyItemValue.getAmount().isPresent() ? anyItemValue.getAmount().get().v() : null);
            entities.add(entity);
        });
        return entities;
    }

}
