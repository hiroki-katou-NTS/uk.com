package nts.uk.ctx.at.shared.infra.repository.monthlyattdcal.agreementresult;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.specialsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.specialsetting.AgreementMonthSettingRepo;
import nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.agreementresult.KmkmtAgreementMonthSet;
import nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.agreementresult.KmkmtAgreementMonthSetPK;

import java.util.List;
import java.util.Optional;

public class AgreementMonthSettingRepoImpl extends JpaRepository implements AgreementMonthSettingRepo {
    private static final String SELECT;
    private static final String FIND_BY_ID;
    private static final String FIND_BY_ID_YEAR_MONTH;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT s FROM KmkmtAgreementMonthSet s ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append("WHERE s.sId = :employeeId");
        FIND_BY_ID = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append("WHERE s.sId = :employeeId AND s.yearMonth = :yearMonth");
        FIND_BY_ID_YEAR_MONTH = builderString.toString();
    }

    @Override
    public void insert(AgreementMonthSetting domain) {
        commandProxy().insert(KmkmtAgreementMonthSet.fromDomain(domain));
    }

    @Override
    public void update(AgreementMonthSetting domain) {
        commandProxy().update(KmkmtAgreementMonthSet.fromDomain(domain));
    }

    @Override
    public void delete(AgreementMonthSetting domain) {
        KmkmtAgreementMonthSetPK pk = new KmkmtAgreementMonthSetPK(domain.getEmployeeId(),domain.getYearMonth().v());
        this.commandProxy().remove(KmkmtAgreementMonthSetPK.class, pk);
    }

    @Override
    public List<AgreementMonthSetting> getByEmployeeId(String employeeId) {
        return this.queryProxy()
                .query(FIND_BY_ID, KmkmtAgreementMonthSet.class)
                .setParameter("employeeId", employeeId).getList(KmkmtAgreementMonthSet::toDomain);
    }

    @Override
    public Optional<AgreementMonthSetting> getByEmployeeIdAndYm(String employeeId, YearMonth yearMonth) {
        return this.queryProxy()
                .query(FIND_BY_ID_YEAR_MONTH, KmkmtAgreementMonthSet.class)
                .setParameter("employeeId", employeeId)
                .setParameter("yearMonth", yearMonth).getSingle(KmkmtAgreementMonthSet::toDomain);
    }
}
