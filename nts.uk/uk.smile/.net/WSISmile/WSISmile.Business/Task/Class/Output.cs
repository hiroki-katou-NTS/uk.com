using System;
using System.Collections.Generic;

using WSISmile.Business.Enum;
using WSISmile.Business.Link.Parameter.Output;

namespace WSISmile.Business.Task
{
    /// <summary>
    /// 外部出力関連情報
    /// </summary>
    public class Output
    {
        public Output() { }

        /// <summary>
        /// 外部出力の設定情報
        /// </summary>
        public OutputSettingInfo Setting = new OutputSettingInfo();

        /// <summary>
        /// 外部出力TaskID
        /// </summary>
        public string TaskId = "";

        /// <summary>
        /// 外部出力FileID
        /// </summary>
        public string FileId = "";

        /// <summary>
        /// 外部出力File(Path)
        /// </summary>
        public string File = "";

        /// <summary>
        /// 支給日区分
        /// </summary>
        public int Payment = 0;

        /// <summary>
        /// SMILEの処理年
        /// </summary>
        public int SmileYear = 0;

        /// <summary>
        /// SMILEの処理月
        /// </summary>
        public int SmileMonth = 0;

        /// <summary>
        /// 支払日区分に該当する雇用／締め／処理年月／締め期間情報リスト
        /// </summary>
        public List<MonthlyClosingEmployInfo> MonthlyClosingEmployInfoList = new List<MonthlyClosingEmployInfo>();
    }
}
