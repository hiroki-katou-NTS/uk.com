package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 社員健康保険資格情報
 */
public interface EmplHealInsurQualifiInforRepository {

    boolean checkEmplHealInsurQualifiInforEndDate(GeneralDate start, GeneralDate end, List<String> empIds);

    boolean checkEmplHealInsurQualifiInforStartDate(GeneralDate start, GeneralDate end, List<String> empIds);

    Optional<HealInsurNumberInfor> getEmplHealInsurQualifiInforByEmpId(String cid, String empId, GeneralDate date);

    Optional<HealInsurNumberInfor> getEmplHealInsurQualifiInforByEmpId(String cid, String empId, GeneralDate date, String historyId);

    Optional<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInfor(String cid, String empId, GeneralDate date);

    List<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInfor(String cid, List<String> empIds, GeneralDate date);

    List<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInfor(String cid, List<String> empIds);
    
    List<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInforDesc(String cid, List<String> empIds);

    Optional<EmplHealInsurQualifiInfor> getEmpHealInsQualifiinfoById(String empId);

    Optional<EmplHealInsurQualifiInfor> getEmpHealInsQualifiinfoById(String empId, String hisId);

    EmplHealInsurQualifiInfor getEmpHealInsQualifiInfoOfEmp(String empId);

    Optional<EmplHealInsurQualifiInfor> getByHistoryId(String historyId);

    Optional<EmplHealInsurQualifiInfor> getByEmpIdAndBaseDate(String empId, GeneralDate baseDate);

    void add(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemAdded, HealInsurNumberInfor hisItem);
    
    void addAll(List<EmplHealInsurQualifiInforParams> params);

    void update(EmpHealthInsurBenefits benefits);
    
    void updateAllStartEndDate(List<EmpHealthInsurBenefits> benefits);
    
    void update(EmpHealthInsurBenefits domain, HealInsurNumberInfor item, String sid);
    
    void updateAllDomain(List<EmplHealInsurQualifiInforParams> params);

    void remove(String employeeId, String hisId);

	List<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInforDescCps003(String cid, List<String> empIds);
}
