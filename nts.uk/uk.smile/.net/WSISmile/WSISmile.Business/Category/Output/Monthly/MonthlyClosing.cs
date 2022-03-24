using System;
using System.Collections.Generic;
using System.Data;

using WSISmile.Business.Common;
using WSISmile.Business.Enum;
using WSISmile.Business.Link.Linker;
using WSISmile.Business.Link.Parameter.Closure;
using WSISmile.Business.Link.Parameter.Output;
using WSISmile.Business.Link.Parameter.Setting;
using WSISmile.Business.Log;
using WSISmile.Business.Task;

namespace WSISmile.Business.Category.Output
{
    /// <summary>
    /// 月次締め処理関連
    /// </summary>
    public class MonthlyClosing
    {
        public MonthlyClosing() { }

        /// <summary>
        /// 支払日区分に該当する雇用／締め／処理年月／締め期間情報リストを取得する
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <returns></returns>
        public List<MonthlyClosingEmployInfo> LoadMonthlyClosingEmployInfo(TaskInfo TI)
        {
            #region 支払日区分に該当する雇用／締め／処理年月／締め期間情報リストを取得する
            List<MonthlyClosingEmployInfo> list = new List<MonthlyClosingEmployInfo>();

            string errMsg = "";

            /*
             * 締め情報List.
            */
            Dictionary<int, ClosureInfo> closureList = ClosureLinker.GetClosureInfo(TI, ref errMsg);
            if (errMsg != "")
            {
                TI.ErrorMsgList.Add(errMsg);
                return list;
            }

            /*
             * 雇用／締め情報List.
            */
            Dictionary<string, EmploymentClosureInfo> employClosureList = ClosureLinker.GetEmploymentClosureInfo(TI, ref errMsg);
            if (errMsg != "")
            {
                TI.ErrorMsgList.Add(errMsg);
                return list;
            }

            /*
             * 処理年月／締めID／締め期間情報List.
            */
            Dictionary<int, Dictionary<int, ClosurePeriodInfo>> monthlyClosurePeriodList = new Dictionary<int, Dictionary<int, ClosurePeriodInfo>>();

            /*
             * 支払日区分に該当する雇用／対象月(連動月の設定)情報List.
            */
             List<PayEmployTargetMonthInfo> payEmployList = new List<PayEmployTargetMonthInfo>();

            foreach (PaymentInfo paymentInfo in TI.Setting.SmileOutputSetting.PaymentInfoList)
            {
                if (paymentInfo.Payment == TI.Output.Payment)
                {
                    payEmployList = paymentInfo.PayEmployTargetMonthInfoList;
                }
            }
            if (payEmployList.Count == 0)
            {
                TI.ErrorMsgList.Add("支払日区分に該当する雇用／対象月情報が設定されていません。支払日区分:" + TI.Output.Payment.ToString());
                return list;
            }

            foreach (PayEmployTargetMonthInfo payEmployInfo in payEmployList)
            {
                MonthlyClosingEmployInfo info = new MonthlyClosingEmployInfo();

                #region 雇用／締め／処理年月／締め期間情報
                // 雇用CD
                info.EmploymentCd = payEmployInfo.EmploymentCd;

                // 締めID
                if (employClosureList.ContainsKey(info.EmploymentCd))
                {
                    info.ClosureId = employClosureList[payEmployInfo.EmploymentCd].ClosureId;
                }
                else
                {
                    TI.ErrorMsgList.Add("雇用に該当する締めIDが設定されていません。雇用CD:" + info.EmploymentCd);
                    return list;
                }

                if (TI.Setting.SmileOutputSetting.CheckLockStatus)
                {
                    #region 月別実績のロック状態のチェック
                    bool monthlyLock = MonthlyCheckLinker.LockStatusCheck(TI, info.ClosureId, ref errMsg);
                    if (errMsg != "")
                    {
                        TI.ErrorMsgList.Add("月別実績のロック状態のチェック処理に失敗しました。" + Environment.NewLine + errMsg);
                        return list;
                    }
                    if (!monthlyLock)
                    {
                        TI.ErrorMsgList.Add("月別実績修正のロックがされていません。勤次郎にてご確認ください。" + Environment.NewLine + "締めID：" + info.ClosureId.ToString());
                        return list;
                    }
                    #endregion
                }

                // 処理年月(締め月の当月 or 前月)
                int processMonth = closureList[info.ClosureId].CurrentMonth;
                if (payEmployInfo.TargetMonth == TargetMonth.Previous) // 前月
                {
                    processMonth = int.Parse(Toolbox.yyyyMMtoDateTime(processMonth).AddMonths(-1).ToString("yyyyMM"));
                }
                info.ProcessMonth = processMonth;

                // 締め期間(締め開始日～締め終了日)

                // 締めID／締め期間情報List.
                Dictionary<int, ClosurePeriodInfo> closurePeriodList = new Dictionary<int, ClosurePeriodInfo>();
                if (monthlyClosurePeriodList.ContainsKey(processMonth))
                {
                    closurePeriodList = monthlyClosurePeriodList[processMonth];
                }
                else
                {
                    closurePeriodList = ClosureLinker.GetClosurePeriod(TI, processMonth, ref errMsg);
                    monthlyClosurePeriodList.Add(processMonth, closurePeriodList);
                }

                if (closurePeriodList.ContainsKey(info.ClosureId))
                {
                    info.StartDate = closurePeriodList[info.ClosureId].StartDate;
                    info.EndDate   = closurePeriodList[info.ClosureId].EndDate;
                }
                else
                {
                    TI.ErrorMsgList.Add("締めIDに該当する締め期間情報を取得できません。締めID:" + info.ClosureId.ToString());
                    return list;
                }
                #endregion 雇用／締め／処理年月／締め期間情報

                list.Add(info);
            }

            return list;
            #endregion
        }

        /// <summary>
        /// 雇用／締め／処理年月／締め期間情報List.より外部出力の出力期間を求める
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="startDate">外部出力期間-開始日</param>
        /// <param name="endDate">外部出力期間-終了日</param>
        public void CalculateOutputPeriod(TaskInfo TI, ref DateTime startDate, ref DateTime endDate)
        {
            #region 雇用／締め／処理年月／締め期間情報List.より外部出力の出力期間を求める
            // 処理年月の昇順List.
            SortedList<int, int> processMonthList = new SortedList<int, int>();

            foreach (MonthlyClosingEmployInfo monthlyClosingEmployInfo in TI.Output.MonthlyClosingEmployInfoList)
            {
                // 処理年月を追加
                processMonthList.Add(monthlyClosingEmployInfo.ProcessMonth, monthlyClosingEmployInfo.ProcessMonth);
            }

            /*
             * Full期間(先頭～末尾)で外部出力を出力し、結果に対して支払日区分に該当する雇用情報(対象年月)に基づき、対象社員を絞り込む
            */

            // 先頭を開始日
            startDate = Toolbox.yyyyMMtoDateTime(processMonthList.Keys[0]);
            // 末尾を終了日
            endDate = Toolbox.yyyyMMtoDateTime(processMonthList.Keys[processMonthList.Count - 1]);
            #endregion
        }
    }
}
