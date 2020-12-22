package nts.uk.screen.at.app.query.cmm024.approver36agrbycompany;

import lombok.val;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.EmployeeInfor;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoDto;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompanyRepo;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Screen A: 初期表示を行う Case start
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PerformInitialDisplaysByCompanyScreenQuery {
    @Inject
    private Approver36AgrByCompanyRepo repo;

    @Inject
    private PersonEmpBasicInfoAdapter infoAdapter;

    @Inject
    private PersonInfoAdapter personInfoAdapter;

    @Inject
    private CompanyAdapter company;

    //【input】 ・ログイン会社ID：会社ID
    //【output】・会社別の承認者（36協定）
    //         ・List<個人基本情報>

    public PerformInitialDisplaysByCompanyScreenDto getApprove36AerByCompany(){

        // Get companyId;
        val cid = AppContexts.user().companyId();
        // [RQ622]会社IDから会社情報を取得する
        val companyName = company.getCurrentCompany().orElseGet(() -> {
            throw new RuntimeException("System Error: Company Info");
        }).getCompanyName();

        // Get 会社別の承認者（36協定）by company id;
        val byCompanyList = repo.getByCompanyId(cid);

        //Get ApproveList 承認者リスト
        List<String> listEmployeeId = new ArrayList<>();
        byCompanyList.forEach(e->{
            listEmployeeId.addAll(e.getApproverList());
            listEmployeeId.addAll(e.getConfirmerList());
        });
        val listIdDistinct = listEmployeeId.stream().distinct().collect(Collectors.toList());
        //社員IDから個人基本情報を取得
        // ドメインモデル「社員データ管理情報」を取得する
        val personInfo = getPersonInfo(listIdDistinct);

        val listDetail = new ArrayList<PerformInitialDetail>();
        byCompanyList.forEach(e-> {
            val item = new PerformInitialDetail();
            item.setEndDate(e.getPeriod().end());
            item.setStartDate(e.getPeriod().start());
            item.setPersonalInfoApprove(e.getApproverList().stream().map(i->checkPersonInfor(personInfo,i))
                    .collect(Collectors.toList()));
            item.setPersonalInfoConfirm(e.getConfirmerList().stream().map(i->checkPersonInfor(personInfo,i))
                    .collect(Collectors.toList()));
            listDetail.add(item);
        });

        return new PerformInitialDisplaysByCompanyScreenDto(cid,companyName,listDetail);
    }


    private List<PersonInfor> getPersonInfo(List<String> listEmployIds){
        if(listEmployIds.isEmpty()){
            return Collections.emptyList();
        }
        val personInfo = personInfoAdapter.getListPersonInfo(listEmployIds);
        if(personInfo.isEmpty()){
            return   Collections.emptyList();
        }

        return personInfo.stream().map(e->new PersonInfor(e.getPId(),e.getEmployeeCode(),e.getEmployeeId(),e.getNamePerson())).collect(Collectors.toList());
    }

    private PersonInfor checkPersonInfor(List<PersonInfor> listPersonInfor,String employeeId){
        val rs = listPersonInfor.stream().filter(e->e.getEmployeeId().equals(employeeId)).findFirst();
        return rs.orElseGet(PersonInfor::new);
    }

}
