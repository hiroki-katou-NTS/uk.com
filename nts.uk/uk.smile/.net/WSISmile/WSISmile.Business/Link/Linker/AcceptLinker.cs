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
    /// �O����� Web�T�[�r�X
    /// </summary>
    public class AcceptLinker
    {
        /// <summary>
        /// �O�����File���A�b�v���[�h����
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="file">Upload File.(Path)</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        public static List<UploadFileInfo> AcceptFileUpload(TaskInfo TI, string file, ref string errorMsg)
        {
            #region �O�����File���A�b�v���[�h���� : AcceptFileUpload
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
        /// �O������O����
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns></returns>
        public static AcceptTaskInfo AcceptPrepare(TaskInfo TI, ref string errorMsg)
        {
            #region �O������O���� : AcceptPrepare
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
        /// �O��������s
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns></returns>
        public static AcceptTaskInfo AcceptStarting(TaskInfo TI, ref string errorMsg)
        {
            #region �O��������s : AcceptStarting
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
        /// �O��������������҂�
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="taskId">�^�X�NID.</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns></returns>
        public static bool AwaitTaskCompleted(TaskInfo TI, string taskId, ref string errorMsg)
        {
            #region �O��������������҂� : AwaitTaskCompleted
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
                // �ُ�ȃP�[�X
                return false;
            }

            if (acceptTaskInfo.Succeeded == false)
            {
                // �����I���������A�����ł͂Ȃ�(�����G���[�����������ꍇ�Ȃ�)
                errorMsg = acceptTaskInfo.GetAcceptTaskError();
                return false;
            }

            return true;
            #endregion
        }

        /// <summary>
        /// �O�����������ԃ`�F�b�N
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="taskId">�^�X�NID.</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns></returns>
        public static AcceptTaskInfo AcceptStatusCheck(TaskInfo TI, string taskId, ref string errorMsg)
        {
            #region �O�����������ԃ`�F�b�N : AcceptStatusCheck
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
        /// �O����������̃G���[�`�F�b�N
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns></returns>
        public static List<string> HasProcessErrors(TaskInfo TI, ref string errorMsg)
        {
            #region �O����������̃G���[�`�F�b�N : HasProcessErrors
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

            // �G���[��񃊃X�g��Ԃ�
            List<string> errorMsgList = new List<string>();
            foreach (AcceptErrorInfo acceptErrorInfo in list)
            {
                errorMsgList.Add(acceptErrorInfo.Text + "�@�G���[���F" + acceptErrorInfo.ErrorsCount.ToString());
            }

            return errorMsgList;
            #endregion
        }

        /// <summary>
        /// �O������G���[���`�F�b�N
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="requestCount">�A��No.</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns></returns>
        public static AcceptErrorInfo AcceptErrorInfoCheck(TaskInfo TI, int requestCount, ref string errorMsg)
        {
            #region �O������G���[���`�F�b�N : AcceptErrorInfoCheck
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
