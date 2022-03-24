using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Data;
using System.IO;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Common;
using WSISmile.Business.Enum;
using WSISmile.Business.Link.Parameter.Accept;
using WSISmile.Business.Task;

namespace WSISmile.Business.Link.Linker
{
    /// <summary>
    /// 外部受入 Webサービス
    /// </summary>
    public class AcceptLinker
    {
        /// <summary>
        /// 外部受入Fileをアップロードする
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="file">Upload File.(Path)</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        public static List<UploadFileInfo> AcceptFileUpload(TaskInfo TI, string file, ref string errorMsg)
        {
            #region 外部受入Fileをアップロードする : AcceptFileUpload
            List<string> files = new List<string>();
            files.Add(file);

            NameValueCollection formFields = new NameValueCollection();
            formFields.Add("stereotype", "");
            formFields.Add("filename", Path.GetFileName(file));
            formFields.Add("userfile", file);

            List<UploadFileInfo> uploadedFiles = new List<UploadFileInfo>();

            UKCommunicator ukApi = new UKCommunicator();
            JArray response = ukApi.WebMethod_FileUpload(TI, files, formFields, ref errorMsg);
            if (response != null)
            {
                foreach (JObject fileInfo in response)
                {
                    uploadedFiles.Add(new UploadFileInfo(fileInfo));
                }
            }

            return uploadedFiles;
            #endregion
        }

        /// <summary>
        /// 外部受入前準備
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns></returns>
        public static AcceptTaskInfo AcceptPrepare(TaskInfo TI, ref string errorMsg)
        {
            #region 外部受入前準備 : AcceptPrepare
            AcceptPrepareParam param = new AcceptPrepareParam();
            param.settingCode = TI.Accept.ConditionSetCd;
            param.uploadedCsvFileId = TI.Accept.FileId;

            string postParameter = JsonConvert.SerializeObject(param);

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.AcceptPrepare, string.Empty, postParameter, ResponseType.JObject, ref errorMsg);
            if (response != null)
            {
                JObject jobject = response as JObject;

                if (jobject["id"] != null)
                {
                    TI.Accept.PrepareTaskId = jobject.GetValue("id").ToObject<string>();
                }

                AcceptTaskInfo acceptTaskInfo = new AcceptTaskInfo(jobject);
                return acceptTaskInfo;
            }

            return null;
            #endregion
        }

        /// <summary>
        /// 外部受入実行
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns></returns>
        public static AcceptTaskInfo AcceptStarting(TaskInfo TI, ref string errorMsg)
        {
            #region 外部受入実行 : AcceptStarting
            Dictionary<string, string> param = new Dictionary<string, string>();
            param.Add("settingCode", TI.Accept.ConditionSetCd);

            string postParameter = JsonConvert.SerializeObject(param);

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.AcceptStarting, string.Empty, postParameter, ResponseType.JObject, ref errorMsg);
            if (response != null)
            {
                JObject jobject = response as JObject;

                if (jobject["id"] != null)
                {
                    TI.Accept.ExecuteTaskId = jobject.GetValue("id").ToObject<string>();
                }

                AcceptTaskInfo acceptTaskInfo = new AcceptTaskInfo(jobject);
                return acceptTaskInfo;
            }

            return null;
            #endregion
        }

        /// <summary>
        /// 外部受入処理完了待ち
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="taskId">タスクID.</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns></returns>
        public static bool AwaitTaskCompleted(TaskInfo TI, string taskId, ref string errorMsg)
        {
            #region 外部受入処理完了待ち : AwaitTaskCompleted
            AcceptTaskInfo acceptTaskInfo = null;

            while (true)
            {
                acceptTaskInfo = AcceptStatusCheck(TI, taskId, ref errorMsg);
                if (acceptTaskInfo.Pending == false && acceptTaskInfo.Running == false)
                {
                    break;
                }
                System.Threading.Thread.Sleep(1000);
            }

            if (acceptTaskInfo == null)
            {
                // 異常なケース
                return false;
            }

            if (acceptTaskInfo.Succeeded == false)
            {
                // 処理終了したが、成功ではない(内部エラーが発生した場合など)
                errorMsg = acceptTaskInfo.GetAcceptTaskError();
                return false;
            }

            return true;
            #endregion
        }

        /// <summary>
        /// 外部受入処理状態チェック
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="taskId">タスクID.</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns></returns>
        public static AcceptTaskInfo AcceptStatusCheck(TaskInfo TI, string taskId, ref string errorMsg)
        {
            #region 外部受入処理状態チェック : AcceptStatusCheck
            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.AcceptStatusCheck, taskId, string.Empty, ResponseType.JObject, ref errorMsg);
            if (response != null)
            {
                AcceptTaskInfo acceptTaskInfo = new AcceptTaskInfo(response as JObject);
                return acceptTaskInfo;
            }

            return null;
            #endregion
        }

        /// <summary>
        /// 外部受入処理のエラーチェック
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns></returns>
        public static List<string> HasProcessErrors(TaskInfo TI, ref string errorMsg)
        {
            #region 外部受入処理のエラーチェック : HasProcessErrors
            List<AcceptErrorInfo> list = new List<AcceptErrorInfo>();

            int requestCount = 1;
            while (true)
            {
                AcceptErrorInfo acceptErrorInfo = AcceptErrorInfoCheck(TI, requestCount, ref errorMsg);
                if (acceptErrorInfo != null)
                {
                    if (acceptErrorInfo.ErrorsCount == 0)
                    {
                        break;
                    }

                    list.Add(acceptErrorInfo);
                }
                else
                {
                    break;
                }

                requestCount++;
            }

            // エラー情報リストを返す
            List<string> errorMsgList = new List<string>();
            foreach (AcceptErrorInfo acceptErrorInfo in list)
            {
                errorMsgList.Add(acceptErrorInfo.Text + "　エラー数：" + acceptErrorInfo.ErrorsCount.ToString());
            }

            return errorMsgList;
            #endregion
        }

        /// <summary>
        /// 外部受入エラー情報チェック
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="requestCount">連番No.</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns></returns>
        public static AcceptErrorInfo AcceptErrorInfoCheck(TaskInfo TI, int requestCount, ref string errorMsg)
        {
            #region 外部受入エラー情報チェック : AcceptErrorInfoCheck
            string param = TI.Accept.ConditionSetCd + "/" + requestCount.ToString();

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.AcceptErrorInfoCheck, param, string.Empty, ResponseType.JObject, ref errorMsg);
            if (response != null)
            {
                AcceptErrorInfo acceptErrorInfo = new AcceptErrorInfo(response as JObject);
                return acceptErrorInfo;
            }

            return null;
            #endregion
        }
    }
}
