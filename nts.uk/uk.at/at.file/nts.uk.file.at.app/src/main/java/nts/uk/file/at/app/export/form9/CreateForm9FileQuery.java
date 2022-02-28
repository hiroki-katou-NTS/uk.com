package nts.uk.file.at.app.export.form9;

import lombok.val;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.file.at.app.export.form9.dto.DisplayInfoRelatedToWorkplaceGroupDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 様式９ を作成する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU008_様式９.Ａ：様式９.Ａ：メニュー別OCD.様式９ を作成する.様式９を作成する
 */
@Stateless
public class CreateForm9FileQuery {
    @Inject
    private GetOutputLayoutInfoFileQuery outputLayoutInfoQuery;

    @Inject
    private GetDetailedOutputSettingInfoFileQuery detailOutSetInfoQuery;

    @Inject
    private GetWorkplaceGroupRelationshipInforFileQuery wkpGroupInfoQuery;

    @Inject
    private ManagedParallelWithContext parallel;

    public Form9ExcelByFormatDataSource get(Form9ExcelByFormatQuery query) {
        String cid = AppContexts.user().companyId();

        // 1.
        val outputLayoutInfo = this.outputLayoutInfoQuery.get(cid, query.getCode());

        // 2.
        val detailOutputSetting = this.detailOutSetInfoQuery.get(cid);

        // 3. 取得する(職場グループ, 期間, int)
        DatePeriod period = new DatePeriod(query.getStartDate(), query.getEndDate());

        List<DisplayInfoRelatedToWorkplaceGroupDto> wkpGroupOutputResults = Collections.synchronizedList(new ArrayList<>());
        this.parallel.forEach(query.getWkpGroupList(), workplaceGroup -> {
            wkpGroupOutputResults.add(wkpGroupInfoQuery.get(workplaceGroup, period, query.getAcquireTarget()));
        });

        return new Form9ExcelByFormatDataSource(outputLayoutInfo.getForm9Layout(), outputLayoutInfo.getFileName(),
                detailOutputSetting, new ArrayList<>(wkpGroupOutputResults));
    }
}
