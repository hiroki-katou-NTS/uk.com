using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;
using WSISmile.Business.Link.Parameter.Closure;
using WSISmile.Business.Task;

namespace WSISmile.Business.Link.Linker
{
    /// <summary>
    /// ���ߏ�� Web�T�[�r�X
    /// </summary>
    public class ClosureLinker
    {
        /// <summary>
        /// ���ߏ����擾����
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>���ߏ��List.</returns>
        public static Dictionary<int, ClosureInfo> GetClosureInfo(TaskInfo TI, ref string errorMsg)
        {
            #region ���ߏ����擾���� : GetClosureInfo
            Dictionary<int, ClosureInfo> list = new Dictionary<int, ClosureInfo>();

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.GetClosureInfo, string.Empty, string.Empty, ResponseType.JArray, ref errorMsg);
            if (response != null)
            {
                JArray jarray = response as JArray;
                foreach (JObject closureInfo in jarray)
                {
                    ClosureInfo info = new ClosureInfo(closureInfo);
                    list.Add(info.ClosureId, info);
                }
            }

            return list;
            #endregion
        }

        /// <summary>
        /// �ٗp�^���ߏ����擾����
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>�ٗp�^���ߏ��List.</returns>
        public static Dictionary<string, EmploymentClosureInfo> GetEmploymentClosureInfo(TaskInfo TI, ref string errorMsg)
        {
            #region �ٗp�^���ߏ����擾���� : GetEmploymentClosureInfo
            Dictionary<string, EmploymentClosureInfo> list = new Dictionary<string, EmploymentClosureInfo>();

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.GetEmploymentClosureInfo, string.Empty, string.Empty, ResponseType.JArray, ref errorMsg);
            if (response != null)
            {
                JArray jarray = response as JArray;
                foreach (JObject employClosureInfo in jarray)
                {
                    EmploymentClosureInfo info = new EmploymentClosureInfo(employClosureInfo);
                    list.Add(info.EmploymentCd, info);
                }
            }

            return list;
            #endregion
        }

        /// <summary>
        /// �w��N���̒��ߊ���(*����ID��)���擾����
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="yearMonth">�N��</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>���ߊ��ԏ��List.(*����ID��)</returns>
        public static Dictionary<int, ClosurePeriodInfo> GetClosurePeriod(TaskInfo TI, int yearMonth, ref string errorMsg)
        {
            #region �w��N���̒��ߊ���(*����ID��)���擾���� : GetClosurePeriod
            Dictionary<int, ClosurePeriodInfo> list = new Dictionary<int, ClosurePeriodInfo>();

            Dictionary<string, int> param = new Dictionary<string, int>();
            param.Add("yearmonth", yearMonth);

            string postParameter = JsonConvert.SerializeObject(param);

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.GetClosurePeriod, string.Empty, postParameter, ResponseType.JArray, ref errorMsg);
            if (response != null)
            {
                JArray jarray = response as JArray;
                foreach (JObject closurePeriodInfo in jarray)
                {
                    ClosurePeriodInfo info = new ClosurePeriodInfo(closurePeriodInfo);
                    list.Add(info.ClosureId, info);
                }
            }

            return list;
            #endregion
        }

        /// <summary>
        /// ���ʎ��т̒��ߏ����擾����
        /// </summary>
        /// <param name="TI">�^�X�N���</param>
        /// <param name="employeeCd">�Ј��R�[�h</param>
        /// <param name="yearMonth">�N��</param>
        /// <param name="errorMsg">�G���[���b�Z�[�W</param>
        /// <returns>���ʎ��т̒��ߏ��List.</returns>
        public static List<MonthlyClosureInfo> GetMonthlyClosureInfo(TaskInfo TI, string employeeCd, int yearMonth, ref string errorMsg)
        {
            #region ���ʎ��т̒��ߏ����擾���� : GetMonthlyClosureInfo
            List<MonthlyClosureInfo> list = new List<MonthlyClosureInfo>();

            MonthlyClosureParam param = new MonthlyClosureParam();
            param.employeeCd = employeeCd;
            param.yearMonth = yearMonth;

            string postParameter = JsonConvert.SerializeObject(param);

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.GetMonthlyClosureInfo, string.Empty, postParameter, ResponseType.JArray, ref errorMsg);
            if (response != null)
            {
                JArray jarray = response as JArray;
                foreach (JObject monthlyClosureInfo in jarray)
                {
                    MonthlyClosureInfo info = new MonthlyClosureInfo(monthlyClosureInfo);
                    list.Add(info);
                }
            }

            return list;
            #endregion
        }
    }
}
