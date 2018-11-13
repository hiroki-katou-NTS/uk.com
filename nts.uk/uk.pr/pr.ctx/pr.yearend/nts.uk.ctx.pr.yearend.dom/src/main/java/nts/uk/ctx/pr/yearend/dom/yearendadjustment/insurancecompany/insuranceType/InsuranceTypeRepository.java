package nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.insuranceType;

import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsurance;

import java.util.Optional;
import java.util.List;

/**
* 保険種類情報
*/
public interface InsuranceTypeRepository
{

    List<InsuranceType> getAllInsuranceType();

    List<InsuranceType> getInsuranceTypeBycId(String cid, String lifeInsuranceCode);

    Optional<InsuranceType> getInsuranceTypeById(String cid, String lifeInsuranceCode, String insuranceTypeCode);

    void add(InsuranceType domain);

    void update(InsuranceType domain);

    void remove(String cid, String lifeInsuranceCode, String insuranceTypeCode);

}
