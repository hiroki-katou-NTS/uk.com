using System;
using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;
using WSISmile.Business.Link.Parameter.Output;
using WSISmile.Business.Task;

namespace WSISmile.Business.Link.Linker
{
    /// <summary>
    /// �Ј���� Web�T�[�r�X
    /// </summary>
    public class EmployeeLinker
    {
        /// <summary>
        /// �w����ԂɎw��ٗp�ōݐE����Ј��ꗗ���擾���� (RequestList-No.335�x�[�X)
        /// </summary>
        /// <remarks>�w�S�E�ꂪ�Ώہx �E��擾����� *���ߏI����
        /// �@�@�@�@�@�w����ԓ��ɂP���ł��ݐЂ��Ă���Ј������o�Ώ�
        /// </remarks>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>�Ј�CDList.</returns>
        public static List<string> SelectEmployeesByEmployment(TaskInfo TI, SelectEmployeesByEmpParam param, ref string errorMsg)
        {
            #region �w����ԂɎw��ٗp�ōݐE����Ј��ꗗ���擾���� : SelectEmployeesByEmployment
            List<string> employeeList = new List<string>();

            string postParameter = JsonConvert.SerializeObject(param);

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.SelectEmployeesByEmployment, string.Empty, postParameter, ResponseType.JArray, ref errorMsg);
            if (response != null)
            {
                JArray jarray = response as JArray;
                foreach (string employeeCd in jarray)
                {
                    employeeList.Add(employeeCd);
                }
            }

            return employeeList;
            #endregion
        }
    }
}
