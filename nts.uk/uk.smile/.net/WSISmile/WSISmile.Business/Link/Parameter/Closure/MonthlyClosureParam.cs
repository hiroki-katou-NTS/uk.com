using System;
using Newtonsoft.Json;

namespace WSISmile.Business.Link.Parameter.Closure
{
    /// <summary>
    /// 月別実績の締め情報パラメータ
    /// </summary>
    [JsonObject]
    public class MonthlyClosureParam
    {
        #region 内部変数
        private string _employeeCd = "";

        private int _yearMonth = 0;
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
        /// 処理年月
        /// </summary>
        [JsonProperty("yearMonth")]
        public int yearMonth
        {
            get { return _yearMonth; }
            set { _yearMonth = value; }
        }
        #endregion
    }
}
