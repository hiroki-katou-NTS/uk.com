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
    /// �O���o�� Web�T�[�r�X
    /// </summary>
    public class OutputLinker
    {
        /// <summary>
        /// �O���o�͋N��
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="startDate">�O���o�͊J�n��</param>
        /// <param name="endDate">�O���o�͏I����</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>�N�������^���s</returns>
        public static void OutputStarting(TaskInfo TI, DateTime startDate, DateTime endDate, ref string errorMsg)
        {
            #region �O���o�͋N�� : OutputStarting
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
        /// �O���o�͂̐ݒ�����擾
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="conditionSetCd">�O���o�͏����R�[�h</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>�O���o�͂̐ݒ���</returns>
        public static OutputSettingInfo GetOutputSetting(TaskInfo TI, string conditionSetCd, ref string errorMsg)
        {
            #region �O���o�͂̐ݒ�����擾 : GetOutputSetting
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
        /// �O���o�͏�����ԃ`�F�b�N
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>�O���o�͏������Object</returns>
        public static OutputStatusInfo OutputStatusCheck(TaskInfo TI, ref string errorMsg)
        {
            #region �O���o�͏�����ԃ`�F�b�N : OutputStatusCheck
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
        /// �O���o�͈ꎞ�t�@�C����FileID���擾
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>�擾�����^���s</returns>
        public static void GetOutputTempFileId(TaskInfo TI, ref string errorMsg)
        {
            #region �O���o�̓t�@�C����FileID���擾 : GetOutputTempFileId
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
        /// �O���o��File���_�E�����[�h���A�_��P�ʃt�H���_�ɂɕۑ�
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        public static void OutputFileDownload(TaskInfo TI, ref string errorMsg)
        {
            #region �O���o��File���_�E�����[�h���� : OutputFileDownload
            UKCommunicator ukApi = new UKCommunicator();

            string response = ukApi.WebMethod_FileDownload(TI, TI.Output.FileId, ref errorMsg);
            if (response != string.Empty)
            {
                TI.Output.File = Path.Combine(TI.Contract.Folder, string.Format("�����Α�_{0}.csv", DateTime.Now.ToString(Format.DateAndTime)));

                try
                {
                    // �_��P�ʃt�H���_�ɏo�͌��ʂ�ۑ�����
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
