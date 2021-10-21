package nts.uk.screen.com.app.find.cas012;


import lombok.val;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.Cas013aDto;
import nts.uk.ctx.sys.auth.app.query.GetEmployeeIDFromUserIDQuery;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyImport;
import nts.uk.ctx.sys.auth.dom.adapter.employee.EmployeeInfoImport;
import nts.uk.ctx.sys.auth.dom.adapter.employee.PersonalEmployeeInfoImport;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ScreenQuery: ロール個人別付与を検索する
 * UKDesign.UniversalK.共通.CAS_権限設定.CAS012_管理者ロールの付与.A:管理者ロールの付与.Ａ：メニュー別OCD.ロール個人別付与を検索する
 * @author : chinh.hm
 */
@Stateless
public class SearchForRolePersonalizedGrantScreenQuery {
    @Inject
    private RoleIndividualGrantRepository roleIndividualGrantRepository;
    @Inject
    private CompanyAdapter companyAdapter;
    @Inject
    private GetEmployeeIDFromUserIDQuery userIDQuery;
    public List<Cas012aDto> getListPersonalizedGrant(Integer roleType,String cid){
        List<RoleIndividualGrant> roleIndividualGrants = new ArrayList<>();
        List<Cas012aDto> rGrants = new ArrayList<>();
        if(cid == null){
            roleIndividualGrants = roleIndividualGrantRepository.findByRoleType(roleType);
        }else {
            roleIndividualGrants = roleIndividualGrantRepository.findByCompanyIdAndRoleType(cid,roleType);
        }
        for (val grant : roleIndividualGrants) {
            val uid = grant.getUserId();
            Optional<PersonalEmployeeInfoImport> optEmployeeInfoImport =
                    userIDQuery.getEmployeeIDFromUserID(uid);
            CompanyImport companyInfo = companyAdapter.findCompanyByCid(grant.getCompanyId());
            if (optEmployeeInfoImport.isPresent()) {
                PersonalEmployeeInfoImport employeeInfoImport = optEmployeeInfoImport.get();
                List<EmployeeInfoImport> employeeInfos = employeeInfoImport.getEmployeeInfos();
                employeeInfos.forEach(e -> {
                    rGrants.add(Cas012aDto.fromDomain(
                            grant,
                            companyInfo.getCompanyCode(),
                            companyInfo.getCompanyName(),
                            e.getEmployeeId(),
                            e.getEmployeeCode(),
                            employeeInfoImport.getBussinessName()));
                });
            }
        }
        return rGrants.stream().sorted(Comparator.comparing(Cas012aDto::getEmployeeCode))
                .collect(Collectors.toList());

    }
}
