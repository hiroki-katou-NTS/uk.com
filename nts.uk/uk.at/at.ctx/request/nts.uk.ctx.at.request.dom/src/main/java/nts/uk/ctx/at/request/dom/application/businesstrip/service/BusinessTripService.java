package nts.uk.ctx.at.request.dom.application.businesstrip.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfo;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.BusinessTripAppWorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

import java.util.List;
import java.util.Optional;

public interface BusinessTripService {

    /**
     * アルゴリズム「出張申請未承認申請を取得」を実行する
     *
     * @param sid                       社員ID
     * @param appDate                   申請対象日リスト
     * @param opActualContentDisplayLst 申請表示情報.申請表示情報(基準日関係あり).表示する実績内容
     */
    List<ActualContentDisplay> getBusinessTripNotApproved(String sid, List<GeneralDate> appDate, Optional<List<ActualContentDisplay>> opActualContentDisplayLst);

    DetailScreenB getDataDetail(String companyId, String appId, AppDispInfoStartupOutput appDispInfoStartupOutput);

    /**
     * 出張申請勤務種類を取得する
     *
     * @param appEmploymentSet       ドメインモデル「雇用別申請承認設定」
     * @param workStyle              出勤休日区分
     * @param workTypeClassification 勤務分類(LIST)
     */
    List<WorkType> getBusinessAppWorkType(AppEmploymentSet appEmploymentSet, BusinessTripAppWorkType workStyle, List<WorkTypeClassification> workTypeClassification);

    void checkInputWorkCode(String wkTypeCd, String wkTimeCd, GeneralDate inputDate);

    /**
     * アルゴリズム「出張申請勤務種類分類内容取得」を実行する
     * @param workType
     * @return
     */
    boolean getBusinessTripClsContent(WorkType workType);

    /**
     * アルゴリズム「出張申請個別エラーチェック」を実行する
     * @param infos
     */
    void businessTripIndividualCheck(List<BusinessTripInfo> infos);

}
