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
    /// Smile�A�g�ݒ� Web�T�[�r�X
    /// </summary>
    public class SettingLinker
    {
        /// <summary>
        /// Smile�A�g-�O���o�͐ݒ�����擾
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>Smile�A�g-�O���o�͐ݒ���</returns>
        public static SmileOutputSetting GetSmileOutputSetting(TaskInfo TI, ref string errorMsg)
        {
            #region Smile�A�g-�O���o�͐ݒ�����擾 : GetSmileOutputSetting
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
        /// Smile�A�g-�O������ݒ�����擾
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>Smile�A�g-�O������ݒ���</returns>
        public static SmileAcceptSetting GetSmileAcceptSetting(TaskInfo TI, ref string errorMsg)
        {
            #region Smile�A�g-�O������ݒ�����擾 : GetSmileAcceptSetting
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
