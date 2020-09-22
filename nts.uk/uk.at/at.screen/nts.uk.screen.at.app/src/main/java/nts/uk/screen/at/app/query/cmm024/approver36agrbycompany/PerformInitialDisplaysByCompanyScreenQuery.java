package nts.uk.screen.at.app.query.cmm024.approver36agrbycompany;

import lombok.val;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoDto;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompanyRepo;
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

    //【input】 ・ログイン会社ID：会社ID
    //【output】・会社別の承認者（36協定）
    //         ・List<個人基本情報>

    public PerformInitialDisplaysByCompanyScreenDto getApprove36AerByCompany(){


        // Get companyId;
        val cid = AppContexts.user().companyId();

        // Get 会社別の承認者（36協定）by company id;
        val byCompanyList = repo.getByCompanyId(cid);

        //Get ApproveList 承認者リスト
        List<String> approveList = new ArrayList<>();
        //Get ConfirmedList  確認者リスト
        List<String> confirmedList = new ArrayList<>();
        byCompanyList.forEach(e->{
            approveList.addAll(e.getApproverList());
            confirmedList.addAll(e.getConfirmerList());
        });
        //社員IDから個人基本情報を取得
        // ドメインモデル「社員データ管理情報」を取得する
        val rsApproveList = getPersonInfo(approveList);

        val rsConfirmedList = getPersonInfo(confirmedList);


        return new PerformInitialDisplaysByCompanyScreenDto(byCompanyList,rsApproveList,rsConfirmedList);
    }


    private List<PersonEmpBasicInfoDto> getPersonInfo(List<String> listEmployIds){
        if(listEmployIds.isEmpty()){
            return Collections.emptyList();
        }
        // Get list Person by list employId;
        val listPerson = infoAdapter.getPerEmpBasicInfo(listEmployIds);
        if(!listPerson.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> listPersonId = listPerson.stream().map(PersonEmpBasicInfoDto::getPersonId).collect(Collectors.toList());
        val personInfo = personInfoAdapter.getListPersonInfo(listPersonId);

        if(personInfo.isEmpty()){
            return   Collections.emptyList();
        }

        return personInfo.stream().map(e->new PersonEmpBasicInfoDto()).collect(Collectors.toList());

    }



}
