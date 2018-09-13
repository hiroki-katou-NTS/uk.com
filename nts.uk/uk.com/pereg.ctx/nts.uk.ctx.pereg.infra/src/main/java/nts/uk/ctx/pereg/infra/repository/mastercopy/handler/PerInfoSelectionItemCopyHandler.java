package nts.uk.ctx.pereg.infra.repository.mastercopy.handler;

import nts.uk.ctx.pereg.dom.mastercopy.DataCopyHandler;
import nts.uk.shr.com.context.AppContexts;

import javax.persistence.EntityManager;

/**
 * @author locph
 */
public class PerInfoSelectionItemCopyHandler extends DataCopyHandler {

    private static final String QUERY_DATA_BY_COMPANYID = "SELECT l FROM PpemtNewLayout l WHERE l.companyId = :companyId";

    public PerInfoSelectionItemCopyHandler(int copyMethod, String companyId, EntityManager em) {
        this.copyMethod = copyMethod;
        this.companyId = companyId;
        this.entityManager = em;
    }

    @Override
    public void doCopy() {
        String sourceCid = AppContexts.user().zeroCompanyIdInContract();
        String targetCid = companyId;

        switch (copyMethod) {
            case REPLACE_ALL:
                // Delete all old data
                copyMasterData(sourceCid, targetCid, false);
                break;
            case ADD_NEW:
                // Insert Data
            case DO_NOTHING:
                // Do nothing
            default:
                break;
        }
    }

    private void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
//        アルゴリズム「指定会社のく選択項目定義を全て削除する」を実行する
//        Lấy domain [PerInfoSelectionItem], Điều kiện :contractCode　＝　login contractCodePPEMT_HISTORY_SELECTION
//        Delete domain [HistorySelection], Điều kiện: companyID　＝　Input．companyID, selectionItemID　＝　「PerInfoSelectionItem」．ID đa lấy
//        Delete doman [Selection], ĐK: historyID　＝　「HistorySelection」．history．historyID
//        Delete domain [OrderSelectionAndDefaultValues], ĐK: historyID　＝　「HistorySelection」．history．historyID

//        指定会社の選択項目定義を全て取得する
//        Acquire domain model "Personal information selection item", Contract code = Logged in contract code
//        Acquire the domain model [HistorySelection], Selection item ID = "Personal information selection item" acquired earlier. All matching ID
//        Acquire domain model [Selection],
//        Acquire domain model [OrderSelectionAndDefaultValues], History ID = "Option history" acquired earlier. History. History ID


    }
}
