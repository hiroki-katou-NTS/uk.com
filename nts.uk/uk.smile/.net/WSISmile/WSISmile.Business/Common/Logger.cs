using System;
using System.Collections;
using System.Collections.Generic;
using System.Configuration;
using System.Globalization;
using System.IO;
using System.Text;
using System.Web;

using WSISmile.Business.Common;
using WSISmile.Business.Link.Parameter.Accept;
using WSISmile.Business.Task;

namespace WSISmile.Business.Log
{
    /// <summary>
    /// ログファイル操作関連
    /// </summary>
    public class Logger
    {
        /// <summary>
        /// ログフォルダ
        /// </summary>
        public const string LOG_FOLDER = "Log";

        /// <summary>
        /// ログ内容を書き込み
        /// </summary>
        /// <param name="errorMsg">エラーメッセージ</param>
        /// <param name="processName">エラーが発生した連携名</param>
        /// <param name="TI">タスク情報</param>
        public static void WriteLog(string errorMsg, string processName, TaskInfo TI)
        {
            #region ログ内容を書き込み : WriteLog
            try
            {
                // ログファイルのパスを取得
                DirectoryInfo logFolder = new DirectoryInfo(Path.Combine(TI.Contract.Folder, LOG_FOLDER));
                if (!logFolder.Exists)
                {
                    Directory.CreateDirectory(logFolder.FullName);
                }

                string logFilePath = Path.Combine(logFolder.FullName, "Smile" + DateTime.Now.ToString(Format.Date, DateTimeFormatInfo.InvariantInfo) + ".log");

                // ログ内容を書き込む
                using (StreamWriter sw = new StreamWriter(logFilePath, true, Encode.Text))
                {
                    sw.WriteLine("[" + DateTime.Now.ToString(Format.DateAndTime_Log) + "]　[" + processName + "]");
                    sw.WriteLine(errorMsg);
                }
            }
            catch (Exception ex)
            {
                string temp = ex.Message; // TODO
            }
            #endregion
        }

        /// <summary>
        /// エラーメッセージの最大表示件数
        /// </summary>
        /// <param name="lstError">エラーメッセージList.</param>
        /// <returns>エラーメッセージList.上限</returns>
        public static List<string> ErrorMsgLimit(List<string> lstError)
        {
            #region エラーメッセージの最大表示件数 : ErrorMsgLimit
            int limit = int.Parse(ConfigurationManager.AppSettings["ErrorMsgLimit"]);
            if (lstError.Count > limit)
            {
                return lstError.GetRange(0, limit);
            }
            return lstError;
            #endregion
        }

        /// <summary>
        /// 保管期間期限切れの情報ファイルを削除
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        public static void RemoveExpiredTaskFiles(TaskInfo TI, ref string errorMsg)
        {
            #region 保管期間期限切れの情報ファイルを削除 : RemoveExpiredTaskFiles
            // 契約単位情報フォルダ
            DirectoryInfo contractFolder = new DirectoryInfo(TI.Contract.Folder);

            try
            {
                foreach (FileInfo file in contractFolder.GetFiles())
                {
                    /*
                     * <<削除対象情報ファイル>>
                     *  1) data.xml
                     *  2) 月次勤怠情報
                     *  3) 各種受入情報
                    */

                    // 情報ファイル保管期間(日数)
                    int expiredDays = int.Parse(ConfigurationManager.AppSettings["ExpiredDays"]);

                    if (file.Name.Contains("_"))
                    {
                        string[] fileNames = file.Name.Split('_');
                        if (fileNames.Length > 1)
                        {
                            string dateFormatOrNot = fileNames[1].Replace(file.Extension, "");

                            DateTime fileDate = DateTime.Today;
                            if (DateTime.TryParseExact(dateFormatOrNot, Format.DateAndTime, DateTimeFormatInfo.InvariantInfo, DateTimeStyles.None, out fileDate))
                            {
                                TimeSpan span = DateTime.Now - fileDate;
                                if (span.Days >= expiredDays)
                                {
                                    file.Delete();
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                errorMsg = ex.Message;
            }
            #endregion
        }
    }
}
