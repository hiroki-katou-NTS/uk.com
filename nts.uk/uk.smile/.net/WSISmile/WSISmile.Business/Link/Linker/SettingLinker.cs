using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;
using WSISmile.Business.Link.Parameter.Setting;
using WSISmile.Business.Task;

namespace WSISmile.Business.Link.Linker
{
    /// <summary>
    /// Smile連携設定 Webサービス
    /// </summary>
    public class SettingLinker
    {
        /// <summary>
        /// Smile連携-外部出力設定情報を取得
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>Smile連携-外部出力設定情報</returns>
        public static SmileOutputSetting GetSmileOutputSetting(TaskInfo TI, ref string errorMsg)
        {
            #region Smile連携-外部出力設定情報を取得 : GetSmileOutputSetting
            SmileOutputSetting smileOutputSetting = new SmileOutputSetting();

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.GetSmileOutputSetting, string.Empty, string.Empty, ResponseType.JObject, ref errorMsg);
            if (response != null)
            {
                smileOutputSetting = new SmileOutputSetting(response as JObject);
            }

            return smileOutputSetting;
            #endregion
        }

        /// <summary>
        /// Smile連携-外部受入設定情報を取得
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>Smile連携-外部受入設定情報</returns>
        public static SmileAcceptSetting GetSmileAcceptSetting(TaskInfo TI, ref string errorMsg)
        {
            #region Smile連携-外部受入設定情報を取得 : GetSmileAcceptSetting
            SmileAcceptSetting smileAcceptSetting = new SmileAcceptSetting();

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.GetSmileAcceptSetting, string.Empty, string.Empty, ResponseType.JObject, ref errorMsg);
            if (response != null)
            {
                JObject jobject = response as JObject;
                if (jobject["listAcceptSet"] != null)
                {
                    smileAcceptSetting = new SmileAcceptSetting(jobject.GetValue("listAcceptSet").ToObject<JArray>());
                }
            }

            return smileAcceptSetting;
            #endregion
        }
    }
}
