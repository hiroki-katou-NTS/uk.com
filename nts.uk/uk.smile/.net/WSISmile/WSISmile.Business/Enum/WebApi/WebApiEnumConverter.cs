using System;
using System.Collections.Generic;

namespace WSISmile.Business.Enum
{
    public class WebApiEnumConverter
    {
        /// <summary>
        /// Webサービスの名称一覧
        /// </summary>
        private static Dictionary<WebApi, string> webApiName = new Dictionary<WebApi, string>();

        /// <summary>
        /// Webサービスの名称を取得する
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static string GetWebApiName(WebApi value)
        {
            if (webApiName.Count == 0)
            {
                webApiName.Add(WebApi.Login, "ctx/sys/gateway/login/password/");

                webApiName.Add(WebApi.ContractCheck, "exio/exo/getinforsmile/check/");

                webApiName.Add(WebApi.OutputStarting, "exio/exo/condset/smileCreateExOutText");
                webApiName.Add(WebApi.GetOutputSetting, "exio/exo/condset/getExOutSummarySetting/");
                webApiName.Add(WebApi.OutputStatusCheck, "exio/exo/execlog/findExOutOpMng/");
                webApiName.Add(WebApi.GetOutputTempFileId, "exio/exo/execlog/smileGetExterOutExecLog/");

                webApiName.Add(WebApi.SelectEmployeesByEmployment, "exio/exo/getinforsmile/gettarget-employee");

                webApiName.Add(WebApi.AcceptPrepare, "exio/input/prepare");
                webApiName.Add(WebApi.AcceptStarting, "exio/input/execute");
                webApiName.Add(WebApi.AcceptStatusCheck, "ntscommons/arc/task/async/info/");
                webApiName.Add(WebApi.AcceptErrorInfoCheck, "exio/input/errors/");

                webApiName.Add(WebApi.FileDownload, "shr/infra/file/storage/get/");
                webApiName.Add(WebApi.FileUpload, "ntscommons/arc/filegate/upload/");

                webApiName.Add(WebApi.GetClosureInfo, "exio/exo/getinforsmile/get-current-closure-by-cid/");
                webApiName.Add(WebApi.GetEmploymentClosureInfo, "exio/exo/getinforsmile/getemployment-relate-closure/");
                webApiName.Add(WebApi.GetClosurePeriod, "exio/exo/getinforsmile/getclosing-period");
                webApiName.Add(WebApi.GetMonthlyClosureInfo, "exio/exo/getinforsmile/getmonthly-performance-closing-infor");

                webApiName.Add(WebApi.MonthlyLockStatusCheck, "exio/exo/getinforsmile/get-monthly-lock-status");
                webApiName.Add(WebApi.MonthlyApproveStatusCheck, "exio/exo/getinforsmile/getmonth-state");

                webApiName.Add(WebApi.GetSmileOutputSetting, "ctx/link/smile/smilelink/get-outlink-setting");
                webApiName.Add(WebApi.GetSmileAcceptSetting, "ctx/link/smile/smilelink/get-accept-setting");
            }

            return webApiName[value];
        }
    }
}
