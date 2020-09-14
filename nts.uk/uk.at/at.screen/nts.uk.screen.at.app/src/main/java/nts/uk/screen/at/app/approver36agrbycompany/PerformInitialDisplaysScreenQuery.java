package nts.uk.screen.at.app.approver36agrbycompany;

import lombok.val;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompanyRepo;;

import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * ScreenQuery :初期表示を行う
 */
@Stateless
public class PerformInitialDisplaysScreenQuery {
    @Inject
    private Approver36AgrByCompanyRepo repo;

    @Inject
    // Lấy từ pub
    private EmployeeAdapter employeeAdapter;
    // Lấy từ pub
    @Inject
    private PersonInfoAdapter personInfoAdapter;
    //【input】 ・ログイン会社ID：会社ID
    //【output】・会社別の承認者（36協定）
    //         ・List<個人基本情報>

    public List<Object> getApprover36AgrByCompany(){
        val cid = AppContexts.user().companyId();
        val byCompanyList = repo.getByCompanyId(cid);
        // 承認者リスト
        val approveList = new ArrayList<>();
        // 確認者リスト
        val confirmedList = new ArrayList<>();
        byCompanyList.stream().forEach(e->{
            approveList.addAll(e.getApproverList());
            confirmedList.addAll(e.getConfirmerList());
        });
        List<String> listSid = new ArrayList<>();
        List<String> personIds = new ArrayList<>();
        //ドメインモデル「社員データ管理情報」を取得する
        //List<EmployeeDataMngInfo> employeeDataMngInfos = new ArrayList<>();

        //ドメインモデル「社員データ管理情報」が取得できたかどうかチェックする
        //if(!CollectionUtil.isEmpty(employeeDataMngInfos)){
            //ドメインモデル「所属会社履歴（社員別）」を取得する
            //personIds = employeeDataMngInfos.stream()
            //        .map(EmployeeDataMngInfo::getPersonId).collect(Collectors.toList());

            val employee = personInfoAdapter.getListPersonInfo(personIds);
        //}
            return null;

    }


}
