using System;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Closure
{
    /// <summary>
    /// 締め期間情報
    /// </summary>
    public class ClosurePeriodInfo
    {
        #region コンストラクタ
        public ClosurePeriodInfo() { }

        /// <summary>
        /// GetClosurePeriodのレスポンス(JObject)からキャストする
        /// </summary>
        /// <param name="aJObject"></param>
        public ClosurePeriodInfo(JObject aJObject)
        {
            if (aJObject["closureId"] != null)
            {
                this._closureId = aJObject.GetValue("closureId").ToObject<int>();
            }
            if (aJObject["startDate"] != null)
            {
                this._startDate = aJObject.GetValue("startDate").ToObject<DateTime>();
            }
            if (aJObject["endDate"] != null)
            {
                this._endDate = aJObject.GetValue("endDate").ToObject<DateTime>();
            }
        }
        #endregion コンストラクタ

        #region 内部変数
        private int _closureId = 0;

        private DateTime _startDate = DateTime.Today;

        private DateTime _endDate = DateTime.Today;
        #endregion 内部変数

        #region プロパティ
        /// <summary>
        /// 締めID
        /// </summary>
        public int ClosureId
        {
            get { return _closureId; }
            set { _closureId = value; }
        }

        /// <summary>
        /// 締め開始日
        /// </summary>
        public DateTime StartDate
        {
            get { return _startDate; }
            set { _startDate = value; }
        }

        /// <summary>
        /// 締め終了日
        /// </summary>
        public DateTime EndDate
        {
            get { return _endDate; }
            set { _endDate = value; }
        }
        #endregion プロパティ
    }
}
