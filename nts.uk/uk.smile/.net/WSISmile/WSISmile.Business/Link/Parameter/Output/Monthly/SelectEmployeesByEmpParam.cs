using System;
using System.Collections.Generic;

using Newtonsoft.Json;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// 指定雇用(締め期間)による社員抽出用パラメータ
    /// </summary>
    [JsonObject]
    public class SelectEmployeesByEmpParam
    {
        #region コンストラクタ
        /// <summary>
        /// 指定雇用(締め期間)による社員抽出用パラメータ
        /// </summary>
        /// <param name="employmentCd">雇用CD</param>
        /// <param name="startDate">締め開始日</param>
        /// <param name="endDate">締め終了日</param>
        public SelectEmployeesByEmpParam(string employmentCd, DateTime startDate, DateTime endDate)
        {
            this._employmentCd = new List<string>();
            this._employmentCd.Add(employmentCd);

            this._startDate = startDate;
            this._endDate = endDate;
        }
        #endregion コンストラクタ

        #region 内部変数
        private List<string> _employmentCd = new List<string>();

        private DateTime _startDate = DateTime.Today;

        private DateTime _endDate = DateTime.Today;
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 雇用CD List.
        /// </summary>
        [JsonProperty("employmentCd")]
        public List<string> employmentCd
        {
            get { return _employmentCd; }
            set { _employmentCd = value; }
        }

        /// <summary>
        /// 締め開始日
        /// </summary>
        [JsonProperty("startDate")]
        public DateTime startDate
        {
            get { return _startDate; }
            set { _startDate = value; }
        }

        /// <summary>
        /// 締め終了日
        /// </summary>
        [JsonProperty("endDate")]
        public DateTime endDate
        {
            get { return _endDate; }
            set { _endDate = value; }
        }
        #endregion プロパティ
    }
}
