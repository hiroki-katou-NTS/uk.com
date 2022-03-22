using System;
using System.Data;
using System.Text;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// CSVファイル書き込み
    /// </summary>
    public class CsvWriter
    {
        /// <summary>
        /// DataTable型に入っているデータのカラム名を、CSV形式の文字列に変換して返す
        /// </summary>
        /// <param name="dataTable">変換元データ(DataTable型オブジェクト)</param>
        /// <returns>変換後データ(CSV形式の文字列)</returns>
        public static string ToCsvString_Header(DataTable dataTable)
        {
            #region DataTable型に入っているデータのカラム名を、CSV形式の文字列に変換して返す
            string str = "";
            StringBuilder sbCsv = new StringBuilder();

            // カラム名をCSV形式文字列へ追加
            for (int i = 0; i < dataTable.Columns.Count; i++)
            {
                str = dataTable.Columns[i].ColumnName;
                str = TransCsvCell(str);

                if (sbCsv.Length != 0)
                {
                    sbCsv.Append(",");
                }

                sbCsv.Append(str);
            }

            sbCsv.Append("\r\n");

            return sbCsv.ToString();
            #endregion
        }

        /// <summary>
        /// DataTable型に入っているデータ(データのみ)を、CSV形式の文字列に変換して返す
        /// </summary>
        /// <param name="dataTable">変換元データ(DataTable型オブジェクト)</param>
        /// <returns>変換後データ(CSV形式の文字列)</returns>
        public static string ToCsvString_Data(DataTable dataTable)
        {
            #region DataTable型に入っているデータ(データのみ)を、CSV形式の文字列に変換して返す
            string str = string.Empty;
            StringBuilder sbCsv = new StringBuilder();

            StringBuilder sbLine = null;

            // テーブルの各データをCSV形式文字列へ追加
            foreach (DataRow aRow in dataTable.Rows)
            {
                sbLine = new StringBuilder();

                for (int i = 0; i < dataTable.Columns.Count; i++)
                {
                    str = aRow[i].ToString();
                    str = TransCsvCell(str);

                    if (sbLine.Length != 0)
                    {
                        sbLine.Append(",");
                    }
                    sbLine.Append(str);
                }

                sbCsv.Append(sbLine.ToString());

                sbCsv.Append("\r\n");
            }

            return sbCsv.ToString();
            #endregion
        }

        /// <summary>
        /// １つの文字列をCSV形式出力用に変換する 　
        /// </summary>
        /// <param name="str">文字列</param>
        /// <returns>CSV形式出力用に変換した文字列</returns>
        public static string TransCsvCell(string str)
        {
            #region １つの文字列をCSV形式出力用に変換する
            /*
             * ①「"」を「""」へ変換する
             * ②文字列を「"」で囲む
            */

            return "\"" + str.Replace("\"", "\"\"") + "\"";
            #endregion
        }
    }
}
