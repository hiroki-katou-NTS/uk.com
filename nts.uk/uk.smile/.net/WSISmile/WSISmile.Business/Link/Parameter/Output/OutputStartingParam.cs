using System;
using Newtonsoft.Json;

namespace WSISmile.Business.Link.Parameter.Output
{
    /// <summary>
    /// 外部出力起動用パラメータ
    /// </summary>
    [JsonObject]
    public class OutputStartingParam
    {
        #region 内部変数
        private string _conditionSetCd = "";

        private DateTime _startDate = DateTime.Today;

        private DateTime _endDate = DateTime.Today;
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 出力条件No.
        /// </summary>
        [JsonProperty("conditionSetCd")]
        public string conditionSetCd
        {
            get { return _conditionSetCd; }
            set { _conditionSetCd = value; }
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
        #endregion プロパティ
    }
}
