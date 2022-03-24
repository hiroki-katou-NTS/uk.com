using System;
using Newtonsoft.Json.Linq;

using WSISmile.Business.Enum;

namespace WSISmile.Business.Link.Parameter.Closure
{
    /// <summary>
    /// 月別実績の締め情報
    /// </summary>
    public class MonthlyClosureInfo
    {
        #region コンストラクタ
        public MonthlyClosureInfo() { }

        /// <summary>
        /// GetMonthlyClosureInfoのレスポンス(JObject)からキャストする
        /// </summary>
        /// <param name="aJObject"></param>
        public MonthlyClosureInfo(JObject aJObject)
        {
            if (aJObject["closureId"] != null)
            {
                this._closureId = aJObject.GetValue("closureId").ToObject<int>();
            }
            if (aJObject["closureDay"] != null)
            {
                this._closureDay = aJObject.GetValue("closureDay").ToObject<int>();
            }
            if (aJObject["lastDay"] != null)
            {
                this._lastDay = aJObject.GetValue("lastDay").ToObject<bool>();
            }
        }
        #endregion コンストラクタ

        #region 内部変数
        private int _closureId = 0;

        private int _closureDay = 0;

        private bool _lastDay = false;
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
        /// 締め日
        /// </summary>
        public int ClosureDay
        {
            get { return _closureDay; }
            set { _closureDay = value; }
        }

        /// <summary>
        /// 末日か
        /// </summary>
        public bool isLastDay
        {
            get { return _lastDay; }
            set { _lastDay = value; }
        }
        #endregion プロパティ
    }
}
