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
    /// 締め情報 Webサービス
    /// </summary>
    public class ClosureLinker
    {
        /// <summary>
        /// 締め情報を取得する
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>締め情報List.</returns>
        public static Dictionary<int, ClosureInfo> GetClosureInfo(TaskInfo TI, ref string errorMsg)
        {
            #region 締め情報を取得する : GetClosureInfo
            Dictionary<int, ClosureInfo> list = new Dictionary<int, ClosureInfo>();

            UKCommunicator ukApi = new UKCommunicator();
            object response = ukApi.WebMethod_Post(TI, WebApi.GetClosureInfo, TI.CompCid, string.Empty, ResponseType.JArray, ref errorMsg);
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
        /// 雇用／締め情報を取得する
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>雇用／締め情報List.</returns>
        public static Dictionary<string, EmploymentClosureInfo> GetEmploymentClosureInfo(TaskInfo TI, ref string errorMsg)
        {
            #region 雇用／締め情報を取得する : GetEmploymentClosureInfo
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
        /// 指定年月の締め期間(*締めID毎)を取得する
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="yearMonth">年月</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>締め期間情報List.(*締めID毎)</returns>
        public static Dictionary<int, ClosurePeriodInfo> GetClosurePeriod(TaskInfo TI, int yearMonth, ref string errorMsg)
        {
            #region 指定年月の締め期間(*締めID毎)を取得する : GetClosurePeriod
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
        /// 月別実績の締め情報を取得する
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="employeeCd">社員コード</param>
        /// <param name="yearMonth">年月</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <returns>月別実績の締め情報List.</returns>
        public static List<MonthlyClosureInfo> GetMonthlyClosureInfo(TaskInfo TI, string employeeCd, int yearMonth, ref string errorMsg)
        {
            #region 月別実績の締め情報を取得する : GetMonthlyClosureInfo
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
