package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import java.util.List;

public interface EmpAddChangeInfoExReposity {
    List<EmpFamilySocialInsCtgInfo> getEmpFamilySocialInsCtgInfoList(List<String> empIds, String cid);
    List<EmpHealInsurQInfo> getEmpHealInsurQInfoList(List<String> empIds, String cid);
    List<EmpWelfarePenInsQualiInfo> getEmpWelfarePenInsQualiInfoList(List<String> empIds, String cid);
    List<EmpCorpOffHisInfo> getEmpCorpOffHisInfo(List<String> empIds, String cid);
    List<EmPensionFundPartiPeriodInfo> getEmPensionFundPartiPeriodInfo(List<String> empIds, String cid);
    List<HealInsurPortPerIntellInfo> getHealInsurPortPerIntellInfo(List<String> empIds, String cid);
}
