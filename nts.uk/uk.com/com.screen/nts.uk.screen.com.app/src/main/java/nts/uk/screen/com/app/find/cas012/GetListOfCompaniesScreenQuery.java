package nts.uk.screen.com.app.find.cas012;


import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyImport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * ScreenQuery: ロール個人別付与を検索する
 * UKDesign.UniversalK.共通.CAS_権限設定.CAS012_管理者ロールの付与.A:管理者ロールの付与.Ａ：メニュー別OCD.会社一覧を取得する
 *@author chinh.hm
 */
@Stateless
public class GetListOfCompaniesScreenQuery {
    @Inject
    private CompanyAdapter companyAdapter;
    public List<CompanyImport> getListCompany(){
        return companyAdapter.findAllCompanyImport();
    }
}
