using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Text.RegularExpressions;

using WSISmile.Business.Common;

namespace WSISmile.Business.Common
{
    public class Toolbox
    {
        /// <summary>
        /// 数値チェック
        /// </summary>
        /// <param name="text">判定文字列</param>
        /// <returns>true：チェックOK，false：チェックNG</returns>
        public static bool IsNumber(string text)
        {
            #region 数値チェック
            // 0～9の数字で構成される文字列の場合→true
            return Regex.IsMatch(text, @"^[0-9]+$", RegexOptions.ECMAScript);
            #endregion
        }

        /// <summary>
        /// Nullチェック
        /// </summary>
        /// <param name="obj">判定オブジェクト</param>
        /// <returns></returns>
        public static bool IsNull(object obj)
        {
            #region Nullチェック
            if (obj == DBNull.Value || obj == null)
            {
                return true;
            }
            return false;
            #endregion
        }

        /// <summary>
        /// 処理年月(yyyyMM)をDateTimeへ変換する(*日は01固定)
        /// </summary>
        /// <param name="yyyyMM">処理年月</param>
        /// <returns></returns>
        public static DateTime yyyyMMtoDateTime(int yyyyMM)
        {
            #region 処理年月(yyyyMM)をDateTimeへ変換する(*日は01固定)
            string strDate = yyyyMM.ToString().Substring(0, 4) + "/" + yyyyMM.ToString().Substring(4, 2) + "/01";

            DateTime date = DateTime.Today;

            if (DateTime.TryParse(strDate, out date))
            {
                return date;
            }
            else
            {
                // TODO 例外処理
                return DateTime.Today;
            }
            #endregion
        }

        /// <summary>
        /// 不正な記号を除く
        /// </summary>
        /// <param name="strValue">対象値</param>
        /// <returns>不正な記号を除いた後</returns>
        public static string RemoveInvalidSymbol(object obj)
        {
            #region 不正な記号を除く
            if (Toolbox.IsNull(obj))
            {
                return string.Empty;
            }

            string strValue = obj.ToString();

            // シングルコーテーション
            strValue = strValue.Replace("'", "");
            // ダブルコーテーション
            strValue = strValue.Replace("\"", "");
            // アンパサンド
            strValue = strValue.Replace("&", "");
            // セミコロン
            strValue = strValue.Replace(";", "");
            // チルダ
            strValue = strValue.Replace("~", "");
            // カンマ
            strValue = strValue.Replace(",", "");
            // シルコンフレクス(^)
            strValue = strValue.Replace("^", "");
            // <
            strValue = strValue.Replace("<", "");
            // >
            strValue = strValue.Replace(">", "");

            // *** Trim ***
            strValue = strValue.Trim();
            // *** Trim ***

            return strValue;

            #endregion
        }
    }
}
