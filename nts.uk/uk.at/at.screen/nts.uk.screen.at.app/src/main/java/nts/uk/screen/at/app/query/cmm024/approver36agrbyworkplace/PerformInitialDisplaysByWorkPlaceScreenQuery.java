package nts.uk.screen.at.app.query.cmm024.approver36agrbyworkplace;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.EmployeeInfor;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoDto;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByWorkplaceRepo;
import nts.uk.screen.at.app.query.cmm024.approver36agrbycompany.PerformInitialDetail;
import nts.uk.screen.at.app.query.cmm024.approver36agrbycompany.PersonInfor;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Inject
    private WorkplaceAdapter workplaceAdapter;
    @Inject
    private CompanyAdapter company;
    public PerformInitialDisplaysByWorkPlaceScreenDto getApprove36AerByWorkplace(String workplaceId ){
        val listAgrByWorkplaces = repo.getByWorkplaceId(workplaceId);
        val cid = AppContexts.user().companyId();
        // [RQ622]会社IDから会社情報を取得する
        val companyName = company.getCurrentCompany().orElseGet(() -> {
            throw new RuntimeException("System Error: Company Info");
        }).getCompanyName();
        val baseDate = GeneralDate.today();
        val listWpl = workplaceAdapter.getWorkplaceInforByWkpIds(cid, Arrays.asList(workplaceId),baseDate);
        String workplaceName = "";
        if(!listWpl.isEmpty()){
            workplaceName = listWpl.get(0).getWorkplaceName();
        }
        List<String> listEmployeeId = new ArrayList<>();
        listAgrByWorkplaces.forEach(e->{
            listEmployeeId.addAll(e.getApproverIds());
            listEmployeeId.addAll(e.getConfirmerIds());
        });
        //社員IDから個人基本情報を取得
        // ドメインモデル「社員データ管理情報」を取得する
        val listIdDistinct = listEmployeeId.stream().distinct().collect(Collectors.toList());
        //社員IDから個人基本情報を取得
        // ドメインモデル「社員データ管理情報」を取得する
        val personInfo = getPersonInfo(listIdDistinct);
        val listDetail = new ArrayList<PerformInitialDetail>();
        listAgrByWorkplaces.forEach(e-> {
            val item = new PerformInitialDetail();
            item.setEndDate(e.getPeriod().end());
            item.setStartDate(e.getPeriod().start());
            item.setPersonalInfoApprove(e.getApproverIds().stream().map(i->checkPersonInfor(personInfo,i))
                    .collect(Collectors.toList()));
            item.setPersonalInfoConfirm(e.getConfirmerIds().stream().map(i->checkPersonInfor(personInfo,i))
                    .collect(Collectors.toList()));
            listDetail.add(item);
        });

        return new PerformInitialDisplaysByWorkPlaceScreenDto(cid,companyName,workplaceId,workplaceName,listDetail);

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