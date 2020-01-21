package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface EmpSocialInsGradeRepository {
	
    void add(EmpSocialInsGradeHis history, EmpSocialInsGradeInfo info);
    
    void addAll(List<EmpSocialInsGradeHisInter> params);

    void update(EmpSocialInsGradeHis history, EmpSocialInsGradeInfo info);
    
    void updateAll(List<EmpSocialInsGradeHisInter> params);
    
    void updateAllInfo(List<EmpSocialInsGradeInfo> params);

    void delete(String cId, String sId, String histId);

    Optional<EmpSocialInsGrade> getByEmpIdAndBaseDate(String companyId, String employeeId, GeneralDate standardDate);

    Optional<EmpSocialInsGrade> getByKey(String companyId, String employeeId, String historyId);

    Optional<EmpSocialInsGrade> getByEmpId(String companyId, String employeeId);
    
    Map<String, EmpSocialInsGrade> getBySidsAndBaseDate(String cid, List<String> employeeId, GeneralDate standardDate);

    Map<String, EmpSocialInsGrade> getBySidsAndCid(String cid, List<String> sids);
}
