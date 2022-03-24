using System;
using Newtonsoft.Json;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// 月別実績の承認状態のチェック用パラメータ
    /// </summary>
    [JsonObject]
    public class ApproveStatusCheckParam
    {
        #region 内部変数
        private string _employeeCd = "";

        private DateTime _startDate = DateTime.Today;

        private DateTime _endDate = DateTime.Today;

        private int _yearMonth = 0;

        private int _closureID = 0;

        private int _closureDay = 0;

        private bool _lastDayOfMonth = false;

        private DateTime _baseDate = DateTime.Today;
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 社員CD
        /// </summary>
        [JsonProperty("employeeCd")]
        public string employeeCd
        {
            get { return _employeeCd; }
            set { _employeeCd = value; }
        }

        /// <summary>
        /// 開始日
        /// </summary>
        [JsonProperty("startDate")]
        public DateTime startDate
        {
            get { return _startDate; }
            set { _startDate = value; }
        }

        /// <summary>
        /// 終了日
        /// </summary>
        [JsonProperty("endDate")]
        public DateTime endDate
        {
            get { return _endDate; }
            set { _endDate = value; }
        }

        /// <summary>
        /// 年月
        /// </summary>
        [JsonProperty("yearMonth")]
        public int yearMonth
        {
            get { return _yearMonth; }
            set { _yearMonth = value; }
        }

        /// <summary>
        /// 締めID
        /// </summary>
        [JsonProperty("closureID")]
        public int closureID
        {
            get { return _closureID; }
            set { _closureID = value; }
        }

        /// <summary>
        /// 締め日
        /// </summary>
        [JsonProperty("closureDay")]
        public int closureDay
        {
            get { return _closureDay; }
            set { _closureDay = value; }
        }

        /// <summary>
        /// 末日か
        /// </summary>
        [JsonProperty("lastDayOfMonth")]
        public bool lastDayOfMonth
        {
            get { return _lastDayOfMonth; }
            set { _lastDayOfMonth = value; }
        }

        /// <summary>
        /// 基準日
        /// </summary>
        [JsonProperty("baseDate")]
        public DateTime baseDate
        {
            get { return _baseDate; }
            set { _baseDate = value; }
        }
        #endregion プロパティ
    }
}
