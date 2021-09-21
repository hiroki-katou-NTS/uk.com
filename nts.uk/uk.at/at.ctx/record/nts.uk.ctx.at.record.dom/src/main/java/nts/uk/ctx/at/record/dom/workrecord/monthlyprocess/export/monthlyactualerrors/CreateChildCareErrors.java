package nts.uk.ctx.at.record.dom.workrecord.monthlyprocess.export.monthlyactualerrors;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.EmployeeMonthlyPerError;

/**
 * @author anhnm
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.月別実績処理.月別集計処理.月別実績集計処理.Export.休暇残数エラーから月別実績エラー一覧を作成する.子の看護エラーから月別実績エラー一覧を作成する.子の看護エラーから月別実績エラー一覧を作成する

 */
public interface CreateChildCareErrors {

    /**
     * 子の看護エラーから月別実績エラー一覧を作成する
     */
    List<EmployeeMonthlyPerError> createChildCareErrors(CreateChildCareErrorsParam param);
}
