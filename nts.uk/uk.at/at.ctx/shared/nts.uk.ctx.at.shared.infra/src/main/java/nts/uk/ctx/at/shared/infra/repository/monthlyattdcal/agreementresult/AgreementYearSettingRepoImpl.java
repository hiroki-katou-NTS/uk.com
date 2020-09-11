package nts.uk.ctx.at.shared.infra.repository.monthlyattdcal.agreementresult;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.specialsetting.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.specialsetting.AgreementYearSettingRepo;
import nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.agreementresult.KmkmtAgreementMonthSetPK;
import nts.uk.ctx.at.shared.infra.entity.monthlyattdcal.agreementresult.KmkmtAgreementYearSet;

import java.util.List;
import java.util.Optional;

public class AgreementYearSettingRepoImpl extends JpaRepository implements AgreementYearSettingRepo {
    private static final String SELECT;
    private static final String FIND_BY_ID;
    private static final String FIND_BY_ID_YEAR;

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
        builderString.append("WHERE s.sId = :employeeId AND s.year = :year");
        FIND_BY_ID_YEAR = builderString.toString();
    }

    @Override
    public void insert(AgreementYearSetting domain) {
        commandProxy().insert(KmkmtAgreementYearSet.fromDomain(domain));
    }

    @Override
    public void update(AgreementYearSetting domain) {
        commandProxy().update(KmkmtAgreementYearSet.fromDomain(domain));
    }

    @Override
    public void delete(AgreementYearSetting domain) {
        KmkmtAgreementMonthSetPK pk = new KmkmtAgreementMonthSetPK(domain.getEmployeeId(),domain.getYear().v());
        this.commandProxy().remove(KmkmtAgreementMonthSetPK.class, pk);
    }

    @Override
    public List<AgreementYearSetting> getByEmployeeId(String employeeId) {
        return this.queryProxy()
                .query(FIND_BY_ID, KmkmtAgreementYearSet.class)
                .setParameter("employeeId", employeeId).getList(KmkmtAgreementYearSet::toDomain);
    }

    @Override
    public Optional<AgreementYearSetting> getByEmployeeIdAndYm(String employeeId, Year year) {
        return this.queryProxy()
                .query(FIND_BY_ID_YEAR, KmkmtAgreementYearSet.class)
                .setParameter("employeeId", employeeId)
                .setParameter("year", year).getSingle(KmkmtAgreementYearSet::toDomain);
    }
}
