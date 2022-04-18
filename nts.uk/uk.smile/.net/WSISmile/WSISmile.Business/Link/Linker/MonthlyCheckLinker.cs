using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;
using WSISmile.Business.Link.Parameter.Output;
using WSISmile.Business.Task;

namespace WSISmile.Business.Link.Linker
{
    /// <summary>
    /// 月別実績の確認関連 Webサービス
    /// </summary>
    public class MonthlyCheckLinker
    {
        /// <summary>
        /// 月別実績のロック状態のチェック
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="closureId">締めID</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns></returns>
        public static bool LockStatusCheck(TaskInfo TI, int closureId, ref string errorMsg)
        {
            #region 月別実績のロック状態のチェック : LockStatusCheck
            Dictionary<string, int> param = new Dictionary<string, int>();
            param.Add("closureId", closureId);

            string postParameter = JsonConvert.SerializeObject(param);

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.MonthlyLockStatusCheck, string.Empty, postParameter, ResponseType.String, ref errorMsg);

            if (response != null)
            {
                return response.ToString() == "0" ? false : true;
            }

            // 当月の実績ロックが存在しない場合
            return false;
            #endregion
        }

        /// <summary>
        /// 月別実績の承認状態のチェック
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="param">チェック用パラメータ</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns></returns>
        public static bool ApproveStatusCheck(TaskInfo TI, ApproveStatusCheckParam param, ref string errorMsg)
        {
            #region 月別実績の承認状態のチェック : ApproveStatusCheck
            string postParameter = JsonConvert.SerializeObject(param);

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.MonthlyApproveStatusCheck, string.Empty, postParameter, ResponseType.JObject, ref errorMsg);
            if (response != null)
            {
                JObject jobject = response as JObject;

                if (jobject["listApprovalPhaseState"] != null)
                {
                    JArray listApprovalPhaseState = jobject.GetValue("listApprovalPhaseState").ToObject<JArray>();
                    foreach (JObject _state in listApprovalPhaseState)
                    {
                        ApprovalPhaseStateInfo approvalPhaseState = new ApprovalPhaseStateInfo(_state);
                        if (!approvalPhaseState.ApprovalAtr)
                        {
                            return false;
                        }
                    }
                    return true;
                }
            }

            return false;
            #endregion
        }
    }
}
