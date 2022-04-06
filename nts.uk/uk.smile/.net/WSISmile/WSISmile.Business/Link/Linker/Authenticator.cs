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
    /// ���O�C��(�F��)���� Web�T�[�r�X
    /// </summary>
    public class Authenticator
    {
        /// <summary>
        /// ���O�C������
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>true:���O�C�������^false:���O�C�����s</returns>
        public static bool Login(TaskInfo TI, ref string errorMsg)
        {
            LoginParam param = new LoginParam();

            param.contractCode     = TI.Contract.Code;
            param.contractPassword = TI.Contract.Password;
            param.companyCode      = TI.CompCode;
            /*
             * �r���g�C�����[�U
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

            string failMessage = "���O�C�����������s���܂����B�_��F�؏��܂��͎Ј��F�؏��Ɍ�肪����܂��B";

            if (response != null)
            {
                JObject jobject = response as JObject;

                if (jobject["errorMessage"] != null)
                {
                    // �r�W�l�X�G���[����������ꍇ�A�D�揈��
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
                        // msgErrorId = null�̏ꍇ�ɁAsuccess�ƌ��Ȃ�
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
