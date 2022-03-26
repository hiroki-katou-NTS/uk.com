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
    /// 社員情報 Webサービス
    /// </summary>
    public class EmployeeLinker
    {
        /// <summary>
        /// 指定期間に指定雇用で在職する社員一覧を取得する (RequestList-No.335ベース)
        /// </summary>
        /// <remarks>『全職場が対象』 職場取得基準日は *締め終了日
        /// 　　　　　指定期間内に１日でも在籍している社員が抽出対象
        /// </remarks>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>社員CDList.</returns>
        public static List<string> SelectEmployeesByEmployment(TaskInfo TI, SelectEmployeesByEmpParam param, ref string errorMsg)
        {
            #region 指定期間に指定雇用で在職する社員一覧を取得する : SelectEmployeesByEmployment
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
