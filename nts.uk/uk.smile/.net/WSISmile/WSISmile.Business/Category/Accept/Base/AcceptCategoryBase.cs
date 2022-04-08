using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Text;

using WSISmile.Business.Common;
using WSISmile.Business.Task;

namespace WSISmile.Business.Category.Accept
{
    /// <summary>
    /// 外部受入-基底クラス
    /// </summary>
    public class AcceptCategoryBase
    {
        /// <summary>
        /// Smile側からもらった連携データを編集
        /// </summary>
        /// <param name="dtSmile">Smile側の連携データ</param>
        public virtual void SmileDataEditing(DataTable dtSmile) { }

        /// <summary>
        /// Smile側の連携データを契約単位フォルダにに保存(CSVファイルに書き込み)
        /// </summary>
        /// <param name="TI">タスク情報</param>
        /// <param name="data">Smile側からもらった連携データ</param>
        /// <param name="category">受入カテゴリ</param>
        /// <param name="append">新規:false/追加:true</param>
        /// <param name="errorMsg">エラーメッセージ</param>
        public void AcceptFileCreate(TaskInfo TI, DataTable data, string category, bool append, ref string errorMsg)
        {
            #region Smile側の連携データを契約単位フォルダにに保存 : AcceptFileCreate
            // 契約単位フォルダに受入対象ファイルを保存する
            if (!append) // 新規の場合
            {
                TI.Accept.File = Path.Combine(TI.Contract.Folder, string.Format(category + "_{0}.csv", DateTime.Now.ToString(Format.DateAndTime)));
            }

            try
            {
                using (StreamWriter streamWriter = new StreamWriter(TI.Accept.File, append, Encode.Shift_JIS))
                {
                    if (!append) // 新規の場合
                    {
                        // Csv Headerを書き出し
                        streamWriter.Write(CsvWriter.ToCsvString_Header(data));
                        // Csv Dataを書き出し
                        streamWriter.Write(CsvWriter.ToCsvString_Data(data));
                    }
                    else
                    {
                        // Csv Dataを書き出し
                        streamWriter.Write(CsvWriter.ToCsvString_Data(data));
                    }
                }
            }
            catch
            {
                TI.Accept.File = "";
            }
            #endregion
        }
    }
}
