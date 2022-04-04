using System;
using System.IO;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;
using WSISmile.Business.Link.Parameter.Login;
using WSISmile.Business.Task;

namespace WSISmile.Business.Link.Linker
{
    /// <summary>
    /// ログイン(認証)処理 Webサービス
    /// </summary>
    public class Authenticator
    {
        /// <summary>
        /// ログイン処理
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>true:ログイン成功／false:ログイン失敗</returns>
        public static bool Login(TaskInfo TI, ref string errorMsg)
        {
            LoginParam param = new LoginParam();

            param.contractCode     = TI.Contract.Code;
            param.contractPassword = TI.Contract.Password;
            param.companyCode      = TI.CompCode;
            /*
             * ビルトインユーザ
            */
            param.employeeCode = "system";
            param.password     = "kinjirou";

            string postParameter = JsonConvert.SerializeObject(param);

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.Login, string.Empty, postParameter, ResponseType.JObject, ref errorMsg);

            /*
             * Response memo.
             * "showChangePass": false
             * "msgErrorId": null
             * "showContract": false
             * "successMsg": null
             * "changePassReason": null
             * "spanDays": 0
            */

            string failMessage = "ログイン処理が失敗しました。契約認証情報または社員認証情報に誤りがあります。";

            if (response != null)
            {
                JObject jobject = response as JObject;

                if (jobject["errorMessage"] != null)
                {
                    // ビジネスエラーが発生する場合、優先処理
                    object errorLogin = jobject.GetValue("errorMessage").ToObject<object>();
                    if (errorLogin != null)
                    {
                        errorMsg = failMessage + Environment.NewLine;
                        errorMsg += errorLogin.ToString();
                        return false;
                    }
                }

                if (jobject["msgErrorId"] != null)
                {
                    object errorId = jobject.GetValue("msgErrorId").ToObject<object>();
                    if (errorId == null)
                    {
                        // msgErrorId = nullの場合に、successと見なす
                        return true;
                    }
                    else
                    {
                        errorMsg = failMessage + Environment.NewLine;
                        errorMsg += errorId.ToString();
                    }
                }
            }

            return false;
        }
    }
}
