using System;
using System.Collections.Generic;
using System.Data;
using System.IO;

using WSISmile.Business.Common;
using WSISmile.Business.Enum;
using WSISmile.Business.Link.Linker;
using WSISmile.Business.Link.Parameter.Closure;
using WSISmile.Business.Link.Parameter.Output;
using WSISmile.Business.Task;

namespace WSISmile.Business.Category.Output
{
    /// <summary>
    /// 外部出力の結果ファイルをSmile側のデータフォーマットに変換
    /// </summary>
    public class SmileConverter
    {
        #region 外部出力の結果ファイルをSmile側のデータフォーマットに変換
        /// <summary>
        /// 外部出力の出力項目ListからSmile側連携DataTableのスキーマを作成する
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <returns>Smile側連携DataTableのスキーマ</returns>
        public DataTable CreateSmileDataTableSchema(TaskInfo TI)
        {
            #region 外部出力の出力項目ListからSmile側連携DataTableのスキーマを作成する
            // Smile側連携DataTable
            DataTable dtSchema = new DataTable();

            foreach (string itemCode in TI.Output.Setting.ItemCodeList)
            {
                dtSchema.Columns.Add(itemCode.PadLeft(5, '0'), System.Type.GetType(DataType.String));
            }

            // Smile勤怠連携データレイアウトをチェック(必須項目)
            if (!dtSchema.Columns.Contains(SmileRequiredItem.EMPLOYEE_CD) ||
                !dtSchema.Columns.Contains(SmileRequiredItem.YEAR) ||
                !dtSchema.Columns.Contains(SmileRequiredItem.COMPANY_CD) ||
                !dtSchema.Columns.Contains(SmileRequiredItem.MONTH))
            {
                TI.ErrorMsgList.Add("連携データフォーマットが正しくありません。勤次郎－外部出力画面をご確認ください。");
                return null;
            }

            return dtSchema;
            #endregion
        }

        /// <summary>
        /// 外部出力の結果ファイルを読み込み、Smile側連携DataTableに追加する
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="dtSmile">Smile側連携DataTableのスキーマ</param>
        /// <param name="intStart">取得開始インデックス</param>
        /// <param name="intCnt">取得件数</param>
        /// <returns></returns>
        public void ReadOutputCsvFile(TaskInfo TI, DataTable dtSmile, int intStart, ref int intCnt)
        {
            #region 外部出力の結果ファイルを読み込み、Smile側連携DataTableに追加する
            if (TI.Output.File == "" || !new FileInfo(TI.Output.File).Exists)
            {
                TI.ErrorMsgList.Add("月次勤怠情報を取得できませんでした。一時的なサーバーの問題が発生しています。" + Environment.NewLine + TI.Output.File);
                return;
            }

            try
            {
                #region 外部出力結果ファイル読み込み処理
                using (StreamReader sr = new StreamReader(TI.Output.File, Encode.Text))
                {
                    // 1行のデータ
                    string line = null;
                    // ファイル行数
                    int rowCount = 0;

                    while ((line = sr.ReadLine()) != null)
                    {
                        // 条件名出力区分
                        if (TI.Output.Setting.CondNameDisplay)
                        {
                            TI.Output.Setting.CondNameDisplay = false;
                            continue;
                        }
                        // 項目名出力区分
                        if (TI.Output.Setting.ItemNameDisplay)
                        {
                            TI.Output.Setting.ItemNameDisplay = false;
                            continue;
                        }

                        // 月次データ件数のカウント
                        rowCount++;

                        // 開始インデックスからデータを読み込む
                        if (intStart > rowCount)
                        {
                            continue;
                        }
                        // 取得件数は0件になった場合は中断する。
                        if (intCnt == 0)
                        {
                            break;
                        }

                        // 1行の項目配列
                        string[] valueList = null;

                        #region 区切り文字
                        switch (TI.Output.Setting.Delimiter)
                        {
                            case Delimiter.Comma:
                                valueList = line.Split(',');
                                break;
                            case Delimiter.Semicolon:
                                valueList = line.Split(':');
                                break;
                            case Delimiter.Space:
                                valueList = line.Split(' ');
                                break;
                            case Delimiter.Tab:
                                valueList = line.Split('?');
                                break;
                            default:
                                break;
                        }
                        #endregion 区切り文字

                        if (line.ToString() != "") // 空白行はカウントしない
                        {
                            DataRow row = dtSmile.NewRow();

                            for (int i = 0; i < valueList.Length; i++)
                            {
                                string value = valueList[i].ToString();

                                #region 文字列形式
                                switch (TI.Output.Setting.StringFormat)
                                {
                                    case StringFormat.None:
                                        break;
                                    case StringFormat.SingleQuotes:
                                        value = value.Replace("'", "");
                                        break;
                                    case StringFormat.DoubleQuotes:
                                        value = value.Replace("\"", "");
                                        break;
                                    default:
                                        break;
                                }
                                #endregion 文字列形式

                                row[i] = value;
                            }

                            dtSmile.Rows.Add(row);
                            intCnt--;
                        }
                    }
                }
                #endregion
            }
            catch (Exception ex)
            {
                TI.ErrorMsgList.Add("外部出力結果ファイル読み込み処理が失敗しました。" + Environment.NewLine + ex.Message);
            }
            #endregion
        }

        /// <summary>
        /// 支払日区分に該当する雇用情報(対象年月)に基づき、対象社員を絞り込む
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="dtSmile">Smile側連携DataTable</param>
        public void FilterByPayEmployment(TaskInfo TI, DataTable dtSmile)
        {
            #region 支払日区分に該当する雇用情報(対象年月)に基づき、対象社員を絞り込む
            // 出力対象者List.
            List<OutputEmployeeInfo> targetList = new List<OutputEmployeeInfo>();

            // 支払日区分の雇用／締め／処理年月／締め期間情報
            foreach (MonthlyClosingEmployInfo monthlyClosingEmployInfo in TI.Output.MonthlyClosingEmployInfoList)
            {
                // 当該雇用・締め期間の社員を抽出する

                // 雇用CD
                string employmentCd = monthlyClosingEmployInfo.EmploymentCd;
                // 処理年月
                int yearMonth = monthlyClosingEmployInfo.ProcessMonth;
                // 締め開始日
                DateTime startDate = monthlyClosingEmployInfo.StartDate;
                // 締め終了日
                DateTime endDate = monthlyClosingEmployInfo.EndDate;

                // 指定期間に指定雇用で在職する社員一覧を取得する (在職者・休業者が対象)
                string errMsg = "";
                SelectEmployeesByEmpParam paramEmployeesByEmp = new SelectEmployeesByEmpParam(employmentCd, startDate, endDate);
                List<string> employeeList = EmployeeLinker.SelectEmployeesByEmployment(TI, paramEmployeesByEmp, ref errMsg);
                if (errMsg != "")
                {
                    TI.ErrorMsgList.Add("該当雇用の社員取得処理に失敗しました。。" + errMsg);
                    return;
                }

                foreach (string employeeCd in employeeList)
                {
                    #region 月別実績の承認状態のチェック
                    if (TI.Setting.SmileOutputSetting.CheckApproveStatus)
                    {
                        // 月別実績の締め情報を取得する
                        List<MonthlyClosureInfo> listMonthlyClosureInfo = ClosureLinker.GetMonthlyClosureInfo(TI, employeeCd, yearMonth, ref errMsg);
                        if (errMsg != "")
                        {
                            TI.ErrorMsgList.Add("月別実績の締め情報の取得処理に失敗しました。" + Environment.NewLine + errMsg);
                            return;
                        }
                        if (listMonthlyClosureInfo.Count > 0)
                        {
                            // 該当社員の締め情報
                            MonthlyClosureInfo monthlyClosureInfo = listMonthlyClosureInfo[0]; // 締め一つのみ

                            ApproveStatusCheckParam paramStatusCheck = new ApproveStatusCheckParam();

                            paramStatusCheck.employeeCd = employeeCd;
                            paramStatusCheck.startDate = startDate;
                            paramStatusCheck.endDate = endDate;
                            paramStatusCheck.yearMonth = yearMonth;
                            paramStatusCheck.closureID = monthlyClosureInfo.ClosureId;
                            paramStatusCheck.closureDay = monthlyClosureInfo.ClosureDay;
                            paramStatusCheck.lastDayOfMonth = monthlyClosureInfo.isLastDay;
                            paramStatusCheck.baseDate = endDate; // 基準日は締め終了日

                            bool monthlyApprove = MonthlyCheckLinker.ApproveStatusCheck(TI, paramStatusCheck, ref errMsg);
                            if (errMsg != "")
                            {
                                TI.ErrorMsgList.Add("月別実績の承認状態のチェック処理に失敗しました。" + Environment.NewLine + errMsg);
                                return;
                            }
                            if (!monthlyApprove)
                            {
                                TI.ErrorMsgList.Add("月別実績に未確認のデータが存在します。勤次郎にてご確認ください。" + Environment.NewLine + "対象社員CD：" + employeeCd);
                                return;
                            }
                        }
                    }
                    #endregion 月別実績の承認状態のチェック

                    targetList.Add(new OutputEmployeeInfo(employeeCd, monthlyClosingEmployInfo.ProcessMonth.ToString()));
                }
            }

            DataTable dtWork = dtSmile.Copy();
            dtSmile.Clear();

            foreach (DataRow drWork in dtWork.Rows)
            {
                // 社員CD
                string employeeCd = drWork[SmileRequiredItem.EMPLOYEE_CD].ToString();
                // 処理年月(年・月には同じく年月で出力される)
                string processMonth = drWork[SmileRequiredItem.YEAR].ToString();

                OutputEmployeeInfo employeeInfo = new OutputEmployeeInfo(employeeCd, processMonth);

                if (targetList.Contains(employeeInfo))
                {
                    dtSmile.ImportRow(drWork);
                }
            }
            #endregion
        }

        /// <summary>
        /// Smile側のデータフォーマットに合わせて、出力データを調整する
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="dtSmile">Smile側連携DataTable</param>
        public void ReadyforSmileData(TaskInfo TI, DataTable dtSmile)
        {
            #region Smile側のデータフォーマットに合わせて、出力データを調整する
            foreach (DataRow drSmile in dtSmile.Rows)
            {
                // 社員CD *6桁へ調整 TODO
                drSmile[SmileRequiredItem.EMPLOYEE_CD] = drSmile[SmileRequiredItem.EMPLOYEE_CD].ToString().Substring(2, 6);

                // 年
                drSmile[SmileRequiredItem.YEAR] = TI.Output.SmileYear;

                // 会社CD *固定【1】
                drSmile[SmileRequiredItem.COMPANY_CD] = "1";

                // 月
                drSmile[SmileRequiredItem.MONTH] = TI.Output.SmileMonth;
            }
            #endregion
        }
        #endregion
    }

    /// <summary>
    /// Smile勤怠連携データレイアウトの必須項目
    /// </summary>
    public class SmileRequiredItem
    {
        #region Smile勤怠連携データレイアウトの必須項目
        /// <summary>
        /// Smile 社員CD
        /// </summary>
        public const string EMPLOYEE_CD = "00001";
        /// <summary>
        /// Smile 処理年
        /// </summary>
        public const string YEAR = "00002";
        /// <summary>
        /// Smile 会社CD
        /// </summary>
        public const string COMPANY_CD = "00003";
        /// <summary>
        /// Smile 処理月
        /// </summary>
        public const string MONTH = "00004";
        #endregion
    }
}
