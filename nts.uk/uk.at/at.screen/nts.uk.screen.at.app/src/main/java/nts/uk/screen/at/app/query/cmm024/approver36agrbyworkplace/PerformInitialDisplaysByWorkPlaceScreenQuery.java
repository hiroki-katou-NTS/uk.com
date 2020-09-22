package nts.uk.screen.at.app.query.cmm024.approver36agrbyworkplace;

import lombok.val;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoDto;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplaceRepo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Screen B: 初期表示を行う Init screen
 * @author chinh.hm
 */
@Stateless
public class PerformInitialDisplaysByWorkPlaceScreenQuery {

    @Inject
    private Approver36AgrByWorkplaceRepo repo;
    @Inject
    private PersonEmpBasicInfoAdapter infoAdapter;

    @Inject
    private PersonInfoAdapter personInfoAdapter;
    public PerformInitialDisplaysByWorkPlaceScreenDto getApprover36AgrByWorkplace(String workplaceiD ){
        val listAgrByWorkplaces = repo.getByWorkplaceId(workplaceiD);

        List<String> approveIdList = new ArrayList<>();
        List<String> confirmIdList = new ArrayList<>();
        listAgrByWorkplaces.forEach(e->{
            approveIdList.addAll(e.getApproverIds());
            confirmIdList.addAll(e.getConfirmerIds());
        });
        //社員IDから個人基本情報を取得
        // ドメインモデル「社員データ管理情報」を取得する
        val rsApproveList = getPersonInfo(approveIdList);

        val rsConfirmedList = getPersonInfo(confirmIdList);

        return new PerformInitialDisplaysByWorkPlaceScreenDto(listAgrByWorkplaces,rsApproveList,rsConfirmedList);

    }
    private List<PersonEmpBasicInfoDto> getPersonInfo(List<String> listEmployIds){
        if(listEmployIds.isEmpty()){
            return Collections.emptyList();
        }
        // Get list Person by list employId;
        val listPerson = infoAdapter.getPerEmpBasicInfo(listEmployIds);
        if(!listPerson.isEmpty()){
            List<String> listPersonId = listPerson.stream().map(e->e.getPersonId()).collect(Collectors.toList());
            val personInfo = personInfoAdapter.getListPersonInfo(listPersonId);
            if(personInfo.isEmpty()){
                return   Collections.emptyList();
            }
            return personInfo.stream().map(e->new PersonEmpBasicInfoDto()).collect(Collectors.toList());
        }
        return Collections.emptyList();

    }

}