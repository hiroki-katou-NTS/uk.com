using System;
using System.IO;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Common;
using WSISmile.Business.Enum;
using WSISmile.Business.Link.Parameter.Output;
using WSISmile.Business.Task;

namespace WSISmile.Business.Link.Linker
{
    /// <summary>
    /// 外部出力 Webサービス
    /// </summary>
    public class OutputLinker
    {
        /// <summary>
        /// 外部出力起動
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="startDate">外部出力開始日</param>
        /// <param name="endDate">外部出力終了日</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>起動成功／失敗</returns>
        public static void OutputStarting(TaskInfo TI, DateTime startDate, DateTime endDate, ref string errorMsg)
        {
            #region 外部出力起動 : OutputStarting
            OutputStartingParam param = new OutputStartingParam();

            param.conditionSetCd = TI.Output.Setting.ConditionSetCd;
            param.startDate      = startDate;
            param.endDate        = endDate;

            string postParameter = JsonConvert.SerializeObject(param);

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.OutputStarting, string.Empty, postParameter, ResponseType.JObject, ref errorMsg);
            if (response != null)
            {
                JObject jobject = response as JObject;
                bool result = jobject.GetValue("commandResult").ToObject<bool>();
                if (result)
                {
                    TI.Output.TaskId = jobject.GetValue("value").ToObject<string>();
                }
            }
            #endregion
        }

        /// <summary>
        /// 外部出力の設定情報を取得
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="conditionSetCd">外部出力条件コード</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>外部出力の設定情報</returns>
        public static OutputSettingInfo GetOutputSetting(TaskInfo TI, string conditionSetCd, ref string errorMsg)
        {
            #region 外部出力の設定情報を取得 : GetOutputSetting
            OutputSettingInfo outputSetting = new OutputSettingInfo();

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.GetOutputSetting, string.Empty, conditionSetCd, ResponseType.JObject, ref errorMsg);
            if (response != null)
            {
                JObject jobject = response as JObject;
                bool exist = jobject.GetValue("settingExist").ToObject<bool>();
                if (exist)
                {
                    outputSetting = new OutputSettingInfo(jobject);
                }
            }

            outputSetting.ConditionSetCd = conditionSetCd;

            return outputSetting;
            #endregion
        }

        /// <summary>
        /// 外部出力処理状態チェック
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>外部出力処理状態Object</returns>
        public static OutputStatusInfo OutputStatusCheck(TaskInfo TI, ref string errorMsg)
        {
            #region 外部出力処理状態チェック : OutputStatusCheck
            OutputStatusInfo outputStatusInfo = new OutputStatusInfo();
            outputStatusInfo.OpCond = OutputStatus.None;

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.OutputStatusCheck, TI.Output.TaskId, string.Empty, ResponseType.JObject, ref errorMsg);
            if (response != null)
            {
                JObject jobject = response as JObject;
                outputStatusInfo = new OutputStatusInfo(jobject);
            }

            return outputStatusInfo;
            #endregion
        }

        /// <summary>
        /// 外部出力一時ファイルのFileIDを取得
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>取得成功／失敗</returns>
        public static void GetOutputTempFileId(TaskInfo TI, ref string errorMsg)
        {
            #region 外部出力ファイルのFileIDを取得 : GetOutputTempFileId
            UKCommunicator ukApi = new UKCommunicator();

            object response = ukApi.WebMethod_Post(TI, WebApi.GetOutputTempFileId, string.Empty, TI.Output.TaskId, ResponseType.JObject, ref errorMsg);
            if (response != null)
            {
                JObject jobject = response as JObject;
                bool result = jobject.GetValue("commandResult").ToObject<bool>();
                if (result)
                {
                    TI.Output.FileId = jobject.GetValue("value").ToObject<string>();
                }
            }
            #endregion
        }

        /// <summary>
        /// 外部出力Fileをダウンロードし、契約単位フォルダにに保存
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        public static void OutputFileDownload(TaskInfo TI, ref string errorMsg)
        {
            #region 外部出力Fileをダウンロードする : OutputFileDownload
            UKCommunicator ukApi = new UKCommunicator();

            string response = ukApi.WebMethod_FileDownload(TI, TI.Output.FileId, ref errorMsg);
            if (response != string.Empty)
            {
                TI.Output.File = Path.Combine(TI.Contract.Folder, string.Format("月次勤怠_{0}.csv", DateTime.Now.ToString(Format.DateAndTime)));

                try
                {
                    // 契約単位フォルダに出力結果を保存する
                    using (StreamWriter streamWriter = new StreamWriter(TI.Output.File, false, Encoding.GetEncoding("Shift_JIS")))
                    {
                        streamWriter.Write(response);
                    }

                    return;
                }
                catch
                {
                    TI.Output.File = "";
                }
            }

            TI.Output.File = "";
            #endregion
        }
    }
}
