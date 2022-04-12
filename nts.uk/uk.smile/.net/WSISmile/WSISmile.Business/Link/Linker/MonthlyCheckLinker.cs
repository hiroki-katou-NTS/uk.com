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
    /// ���ʎ��т̊m�F�֘A Web�T�[�r�X
    /// </summary>
    public class MonthlyCheckLinker
    {
        /// <summary>
        /// ���ʎ��т̃��b�N��Ԃ̃`�F�b�N
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="closureId">����ID</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns></returns>
        public static bool LockStatusCheck(TaskInfo TI, int closureId, ref string errorMsg)
        {
            #region ���ʎ��т̃��b�N��Ԃ̃`�F�b�N : LockStatusCheck
            Dictionary<string, int> param = new Dictionary<string, int>();
            param.Add("closureId", closureId);

            string postParameter = JsonConvert.SerializeObject(param);

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.MonthlyLockStatusCheck, string.Empty, postParameter, ResponseType.String, ref errorMsg);

            if (response != null)
            {
                return response.ToString() == "0" ? false : true;
            }

            // �����̎��у��b�N�����݂��Ȃ��ꍇ
            return false;
            #endregion
        }

        /// <summary>
        /// ���ʎ��т̏��F��Ԃ̃`�F�b�N
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="param">�`�F�b�N�p�p�����[�^</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns></returns>
        public static bool ApproveStatusCheck(TaskInfo TI, ApproveStatusCheckParam param, ref string errorMsg)
        {
            #region ���ʎ��т̏��F��Ԃ̃`�F�b�N : ApproveStatusCheck
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
